package robyn.cliTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * CLI
 */
public class CLI {
    private CommandRegister commandRegister;
    private String prompt = "$ ";

    private Scanner scanner;

    private InputStream in;
    private List<PrintStream> out;

    private void print(String message, Object... objects) {
        out.stream().forEach(o -> {
            o.print(String.format(message, objects));
            o.flush();
        });
    }

    private void println(String message, Object... objects) {
        out.stream().forEach(o -> {
            o.println(String.format(message, objects));
            o.flush();
        });
    }

    public String readLine() {
        return scanner.nextLine();
    }

    // TODO incomplete implementation. its late...
    private String parseCommandString(String commandString) {
        boolean inString = false;
        String currentArg = "";
        List<String> args = new ArrayList<>();
        for (int i = 0; i < commandString.length(); i++) {
            Character c = commandString.charAt(i);
            if (c == '"') {
                if (inString) {
                    args.add(currentArg);
                    currentArg = "";
                    inString = false;
                } else {
                    inString = true;
                }

            }

        }
        return null;
    }

    /**
     * CLI
     * Creates a CLI using stdin and stdout as the input and output
     */
    public CLI() {
        this(System.in, Arrays.asList(System.out));
    }

    /**
     * Creates a CLI given
     */
    public CLI(InputStream in, List<PrintStream> out) {
        this.in = in;
        // make sure out is not a generic List
        this.out = new ArrayList<PrintStream>(out);
        this.scanner = new Scanner(in);
        commandRegister = new CommandRegister();
    }

    public static void main(String[] args) throws FileNotFoundException {
        PrintStream out = new PrintStream(new File("data/cliTools/testInput1.txt"));
        List<PrintStream> outs = new ArrayList<>();
        outs.add(out);
        new CLI(System.in, outs);
    }

}
