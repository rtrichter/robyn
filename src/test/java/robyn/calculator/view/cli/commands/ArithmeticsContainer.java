package robyn.calculator.view.cli.commands;

import java.util.Map;

import robyn.calculator.model.Calculator;
import robyn.cliTools.CLI;
import robyn.cliTools.CommandContainer;
import robyn.cliTools.exceptions.CommandIdentifierException;

/**
 * ArithmeticsContainer
 */
public class ArithmeticsContainer extends CommandContainer {

    private Calculator calculator;
    private CLI cli;

    public ArithmeticsContainer(Calculator calculator, CLI calculatorCLI) {
        this.calculator = calculator;
        this.cli = calculatorCLI;

        // prepare all command for registration
        commands.put("add", this::add);
        commands.put("sub", this::subtract);
        commands.put("mult", this::multiply);
        commands.put("div", this::divide);
        commands.put("quit", this::quit);
        commands.put("alias", this::alias);
        commands.put("unalias", this::unalias);

        // prepare all aliases for registration
        aliases.put("exit", "quit");
    }

    public void add(String[] args) {
        double previous = calculator.getAns();
        double operand = Double.parseDouble(args[1]);
        calculator.add(operand);
        cli.println("\nAdded: %.2f + %.2f = %.2f", previous, operand, calculator.getAns());
    }

    public void subtract(String[] args) {
        double previous = calculator.getAns();
        double operand = Double.parseDouble(args[1]);
        calculator.subtract(operand);
        cli.println("\nSubtracted: %.2f - %.2f = %.2f", previous, operand, calculator.getAns());
    }

    public void multiply(String[] args) {
        double previous = calculator.getAns();
        double operand = Double.parseDouble(args[1]);
        calculator.multiply(operand);
        cli.println("\nMultiplied: %.2f * %.2f = %.2f", previous, operand, calculator.getAns());
    }

    public void divide(String[] args) {
        double previous = calculator.getAns();
        double operand = Double.parseDouble(args[1]);
        calculator.divide(operand);
        cli.println("\nDivided: %.2f / %.2f = %.2f", previous, operand, calculator.getAns());
    }

    // TODO make a command for ALL cli instances
    public void quit(String[] args) {
        System.exit(0);
    }

    // TODO make a command for ALL cli instances
    public void alias(String[] args) {
        // no args
        if (args.length == 1) {
            cli.println("Aliases: ");
            Map<String, String> aliases = cli.reg.getAliases();
            aliases.keySet().stream().forEach(a -> cli.println("\t%s: %s", a, aliases.get(a)));
            cli.println("");
            return;
        }
        if (args.length != 3) {
            cli.println("Incorrect number of arguments for command: alias");
            return;
        }
        try {
            cli.reg.registerAlias(args[1], args[2]);
        } catch (CommandIdentifierException e) {
            cli.println(e.getMessage());
        }
    }

    // TODO make a command for ALL cli instances
    public void unalias(String[] args) {
        if (args.length != 2) {
            cli.println("Incorrect number of arguments for command: unalias");
        }
        try {
            cli.reg.deregisterAlias(args[1]);
        } catch (CommandIdentifierException e) {
            cli.println(e.getMessage());
        }
    }
}
