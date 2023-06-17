package party.iroiro.luajava;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.widget.AutopairWidgets;
import party.iroiro.luajava.value.LuaValueSuite;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import static party.iroiro.luajava.LuaVersion.forEachTest;

public class Console implements Callable<Integer> {
    private final LineReader reader;
    private final Terminal terminal;
    @CommandLine.Option(names = {"-t", "--test"}, help = true, description = "Run built-in tests")
    boolean test;
    @CommandLine.Option(names = {"-g", "--global"}, help = true, description = "Make Lua symbol global")
    boolean global;
    @CommandLine.Option(names = {"-l", "--lua"}, description = "Specify the Lua version")
    String lua;
    @SuppressWarnings("DefaultAnnotationParam")
    @CommandLine.ArgGroup(exclusive = true, multiplicity = "0..1")
    Command command;

    public Console(LineReader reader, Terminal terminal) {
        this.reader = reader;
        this.terminal = terminal;
    }

    public static void main(String[] args) {
        try (Terminal terminal = TerminalBuilder.builder()
                .jansi(true)
                .nativeSignals(true)
                .build()
        ) {
            LineReader reader = LineReaderBuilder.builder()
                    .appName("lua")
                    .terminal(terminal)
                    .build();
            new CommandLine(new Console(reader, terminal)).execute(args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startInteractive(Lua l) {
        try (Lua L = l) {
            LineReader reader = LineReaderBuilder.builder()
                    .appName("lua")
                    .terminal(terminal)
                    .highlighter(LuaHighlighter.get())
                    .parser(new LuaConsoleParser())
                    .variable(LineReader.SECONDARY_PROMPT_PATTERN, "  > ")
                    .variable(LineReader.INDENTATION, 2)
                    .build();
            AutopairWidgets autopairWidgets = new AutopairWidgets(reader, false);
            autopairWidgets.enable();
            L.openLibraries();
            L.setExternalLoader(new ClassPathLoader());
            L.run("print('Running ' .. _VERSION)");
            injectLicense(L, reader);
            Pattern varName = Pattern.compile("^[a-zA-Z_]\\w+$");
            while (true) {
                String s;
                try {
                    s = reader.readLine(">>> ");
                    if (varName.matcher(s).matches()) {
                        prettyPrint(L, s);
                        s = "print(" + s + ")";
                    }
                } catch (EndOfFileException ignored) {
                    break;
                } catch (UserInterruptException ignored) {
                    reader.printAbove("UserInterrupt");
                    continue;
                } catch (Throwable ignored) {
                    s = "";
                }
                synchronized (L.getMainState()) {
                    if (L.run(s) != Lua.LuaError.OK) {
                        if (L.getTop() != 0 && L.isString(-1)) {
                            reader.printAbove(L.toString(-1));
                        }
                        L.setTop(0);
                        Throwable e = L.getJavaError();
                        if (e != null) {
                            reader.printAbove("Last Java side exception:");
                            ByteArrayOutputStream output = new ByteArrayOutputStream();
                            PrintStream print = new PrintStream(output);
                            e.printStackTrace(print);
                            print.flush();
                            reader.printAbove(output.toString());
                            L.error((Throwable) null);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void prettyPrint(Lua L, String varName) {
        synchronized (L.getMainState()) {
            L.getGlobal(varName);
            if (L.isTable(-1)) {
                L.run("require(\"pretty\")(" + varName + ")");
            }
            L.pop(1);
        }
    }

    private static void injectLicense(Lua L, LineReader reader) {
        reader.printAbove("Use license() to print licensing info.");
        L.push(l -> {
            reader.printAbove("This appliation is licensed under GPL 3.0.");
            reader.printAbove("Use license_gpl() to read full text of GPL 3.0.");
            printAll(Lua.class, reader, "/LICENSE-luajava-console");
            printAll(Lua.class, reader, "/META-INF/LICENSE-luajava");
            return 0;
        });
        L.setGlobal("license");
        L.push(l -> {
            reader.printAbove("This appliation is licensed under GPL 3.0.");
            reader.printAbove("Use license_gpl() to read full text of GPL 3.0.");
            printAll(Console.class, reader, "/gpl-3.0.txt");
            return 0;
        });
        L.setGlobal("license_gpl");
    }

    private static void printAll(Class<?> c, LineReader reader, String resource) {
        try (InputStream s = c.getResourceAsStream(resource)) {
            Scanner scanner = new Scanner(Objects.requireNonNull(s));
            while (scanner.hasNextLine()) {
                reader.printAbove(scanner.nextLine());
            }
        } catch (Exception e) {
            reader.printAbove("IOException during license reading");
        }
    }

    private Lua getLua(String version) {
        LuaVersion luaVersion = LuaVersion.from(
                version == null ? requestLuaVersion() : version.toLowerCase()
        );
        if (luaVersion == null) {
            throw new RuntimeException("Unable to find matching version");
        } else {
            Lua L = luaVersion.supplier.get();
            if (global) {
                L.getLuaNative().loadAsGlobal();
            }
            return L;
        }
    }

    private String requestLuaVersion() {
        String version;
        do {
            version = reader.readLine("Lua Version: ");
        } while (LuaVersion.from(version.toLowerCase()) == null);
        return version;
    }

    @Override
    public Integer call() {
        if (test) {
            reader.printAbove("----- Running LuaTestSuite -----");
            forEachTest((L, v) -> {
                reader.printAbove("----- Testing " + L.getClass().getSimpleName() + " -----");
                new LuaTestSuite<>(L, v.supplier).test();
            });
            reader.printAbove("----- Running LuaScriptSuite -----");
            forEachTest((L, v) -> {
                reader.printAbove("----- Testing " + L.getClass().getSimpleName() + " -----");
                new LuaScriptSuite<>(L).test();
            });
            reader.printAbove("----- Running LuaValueSuite -----");
            forEachTest((L, v) -> {
                reader.printAbove("----- Testing " + L.getClass().getSimpleName() + " -----");
                new LuaValueSuite<>(L).test();
            });
            reader.printAbove("----- Completed -----");
        } else if (command == null) {
            startInteractive(getLua(lua));
        } else {
            try (Lua L = getLua(lua)) {
                L.openLibraries();
                L.setExternalLoader(new ClassPathLoader());
                if (command.file != null) {
                    Path path = Paths.get(command.file);
                    byte[] bytes = Files.readAllBytes(path);
                    ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
                    okOrFail(L.load(buffer.put(bytes).flip(), path.getFileName().toString()),
                            L, "loading file buffer");
                    okOrFail(L.pCall(0, Consts.LUA_MULTRET),
                            L, "running file");
                } else if (command.expression != null) {
                    okOrFail(L.load(command.expression),
                            L, "loading expression");
                    okOrFail(L.pCall(0, Consts.LUA_MULTRET),
                            L, "running expression");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }

    private void okOrFail(Lua.LuaError error, Lua L, String message) {
        if (error != Lua.LuaError.OK) {
            reader.printAbove("Error " + error + " when " + message);
            throw new RuntimeException(L.toString(-1));
        }
    }

    private static class Command {
        @CommandLine.Option(names = {"-f", "--file"}, description = "The Lua file to run")
        String file;

        @CommandLine.Option(names = {"-e", "--expr"}, description = "The Lua expression to run")
        String expression;
    }
}
