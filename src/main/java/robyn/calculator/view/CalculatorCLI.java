package robyn.calculator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import robyn.calculator.model.Calculator;
import robyn.calculator.view.cli.commands.ArithmeticsContainer;
import robyn.cliTools.CLI;
import robyn.cliTools.CommandContainer;

public class CalculatorCLI extends CLI {

    public Calculator calc;

    public CalculatorCLI() {
        this(System.in, System.out);
    }

    public CalculatorCLI(InputStream in, PrintStream out) {
        this(in, Arrays.asList(out));
    }

    public CalculatorCLI(InputStream input, List<PrintStream> output) {
        super(input, output);
        calc = new Calculator();
        CommandContainer arithmeticsContainer = new ArithmeticsContainer(calc, this);
        arithmeticsContainer.registerAll(this);
    }

    public static void main(String[] args) throws FileNotFoundException {
        boolean log = args.length > 0;
        PrintStream logger = null;
        List<PrintStream> printers = new ArrayList<PrintStream>();
        printers.add(System.out);
        if (log) {
            logger = new PrintStream(new File(args[0]));
            printers.add(logger);
        }

        CalculatorCLI calcCLI = new CalculatorCLI(System.in, printers);
        calcCLI.start();

        if (logger != null) {
            logger.close();
        }
    }
}
