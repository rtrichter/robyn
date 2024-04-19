package robyn.calculator.view.cli.commands;

import java.util.Map;

import robyn.calculator.model.Calculator;
import robyn.cliTools.CLI;
import robyn.cliTools.Command;
import robyn.cliTools.CommandContainer;
import robyn.cliTools.exceptions.CommandIdentifierException;

/**
 * ArithmeticsContainer
 */
public class ArithmeticsContainer extends CommandContainer {

    private Calculator calc;
    private CLI cli;

    public ArithmeticsContainer(Calculator calculator, CLI calculatorCLI) {
        this.calc = calculator;
        this.cli = calculatorCLI;

        // prepare all command for registration
        commands.put("add", add);
        commands.put("sub", subtract);
        commands.put("mult", multiply);
        commands.put("div", divide);
        commands.put("quit", quit);
        commands.put("alias", alias);
        commands.put("unalias", unalias);
        commands.put("clear", clear);
        commands.put("ans", ans);
        commands.put("pow", pow);

        // prepare all aliases for registration
        aliases.put("exit", "quit");
        aliases.put("+", "add");
        aliases.put("-", "sub");
        aliases.put("*", "mult");
        aliases.put("/", "div");
        aliases.put("**", "pow");
    }

    public Command add = (args) -> {
        double previous = calc.getAns();
        double operand = Double.parseDouble(args[1]);
        calc.add(operand);
        cli.println("Added: %.2f + %.2f = %.2f", previous, operand, calc.getAns());
    };

    public Command subtract = (args) -> {
        double previous = calc.getAns();
        double operand = Double.parseDouble(args[1]);
        calc.subtract(operand);
        cli.println("Subtracted: %.2f - %.2f = %.2f", previous, operand, calc.getAns());
    };

    public Command multiply = (args) -> {
        double previous = calc.getAns();
        double operand = Double.parseDouble(args[1]);
        calc.multiply(operand);
        cli.println("Multiplied: %.2f * %.2f = %.2f", previous, operand, calc.getAns());
    };

    public Command divide = (args) -> {
        double previous = calc.getAns();
        double operand = Double.parseDouble(args[1]);
        calc.divide(operand);
        cli.println("Divided: %.2f / %.2f = %.2f", previous, operand, calc.getAns());
    };

    public Command clear = (args) -> {
        calc.resetAns();
        cli.println("Answer reset to %.2f", calc.getAns());
    };

    public Command ans = (args) -> {
        cli.println("%.2f", calc.getAns());
    };

    public Command pow = (args) -> {
        double previous = calc.getAns();
        double operand = Double.parseDouble(args[1]);
        calc.raise(operand);
        cli.println("Divided: %.2f ** %.2f = %.2f", previous, operand, calc.getAns());
    };

    // TODO make a command for ALL cli instances
    public Command quit = (args) -> {
        System.exit(0);
    };

    // TODO make a command for ALL cli instances
    public Command alias = (args) -> {
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
    };

    // TODO make a command for ALL cli instances
    public Command unalias = (args) -> {
        if (args.length != 2) {
            cli.println("Incorrect number of arguments for command: unalias");
        }
        try {
            cli.reg.deregisterAlias(args[1]);
        } catch (CommandIdentifierException e) {
            cli.println(e.getMessage());
        }
    };
}
