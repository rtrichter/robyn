package robyn.cliTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import robyn.cliTools.exceptions.CommandIdentifierException;

// TODO delete later
@SuppressWarnings("unused")

/**
 * CLI
 */
public class CLI {
    public CommandRegister reg;
    private String prompt = "$ ";

    private Scanner scanner;

    private InputStream in;
    private List<PrintStream> out;

    /**
     * prints a formatted message to all print streams
     */
    public void print(String message, Object... objects) {
        out.stream().forEach(o -> {
            o.print(String.format(message, objects));
            o.flush();
        });
    }

    /**
     * prints a formatted message to all print streams with a new line at the end
     */
    public void println(String message, Object... objects) {
        out.stream().forEach(o -> {
            o.println(String.format(message, objects));
            o.flush();
        });
    }

    /**
     * reads the next line of input
     */
    protected String readLine() {
        return scanner.nextLine();
    }

    /**
     * parses a command string
     * splits commands by spaces
     * ends a set of arguments at the end of the string (typically a newline)
     * anything in quotes is kept together
     * - cmd hello world -> [cmd, hello, world]
     * - cmd "hello world" -> [cmd, hello world]
     *
     * @param commandString
     */
    private String[] parseCommandString(String commandString) {
        // allows us to bypass spaces in an argument
        boolean inString = false;
        // stores the argument that is currently being parsed
        String currentArg = "";
        // stores a list of arguments as they are parsed
        List<String> args = new ArrayList<>();
        // iterate through characters in the input string
        for (int i = 0; i < commandString.length(); i++) {
            // grab current character
            Character c = commandString.charAt(i);
            // check if you are entering or leaving a string
            if (c == '"') {
                if (inString) {
                    inString = false;
                } else {
                    inString = true;
                }
                // skip the rest of the logic if c == '"'
                continue;
                // if c is a space (and not a ") then you are at an argument boundary
            } else if (c == ' ') {
                // protects against multiple spaces
                if (currentArg.length() > 0) {
                    // add to argument list
                    args.add(currentArg);
                    // reset current argument buffer
                    currentArg = "";
                }
                continue;
            }
            // if c != '"' and c != ' '...
            // append the current character to the current argument buffer
            currentArg += c;
        }
        args.add(currentArg);
        // return a string array of the parsed arguments
        return args.toArray(new String[0]);
    }

    /**
     * CLI
     * Creates a CLI using stdin and stdout as the input and output
     */
    public CLI() {
        this(System.in, Arrays.asList(System.out));
    }

    /**
     * Creates a CLI given an input stream and a single output stream
     */
    public CLI(InputStream in, PrintStream out) {
        this(in, Arrays.asList(out));
    }

    /**
     * Creates a CLI given
     */
    public CLI(InputStream in, List<PrintStream> out) {
        this.in = in;
        // make sure out is not a generic List
        this.out = new ArrayList<PrintStream>(out);
        this.scanner = new Scanner(in);
        reg = new CommandRegister();
    }

    public void runCommand(String[] args) throws CommandIdentifierException {
        reg.runCommand(args);
    }

    public void start() {
        boolean running = true;
        // TODO load configuration file (ie aliases)
        while (running) {
            print(prompt);
            String inputString = readLine();
            String[] args = parseCommandString(inputString);
            try {
                runCommand(args);
            } catch (CommandIdentifierException e) {
                println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        PrintStream out = new PrintStream(new File("data/cliTools/testInput1.txt"));
        List<PrintStream> outs = new ArrayList<>();
        outs.add(out);
        new CLI(System.in, outs);
    }

}
