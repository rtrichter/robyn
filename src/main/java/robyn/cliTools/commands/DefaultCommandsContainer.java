package robyn.cliTools.commands;

import robyn.cliTools.*;
import robyn.cliTools.exceptions.*;
import java.util.Map;

/**
 * DefaultCommandsContainer
 */
public class DefaultCommandsContainer extends CommandContainer {

    private CLI cli;

    public DefaultCommandsContainer(CLI cli) {
        this.cli = cli;

        // prepare commands for registration
        commands.put("quit", quit);
        commands.put("alias", alias);
        commands.put("unalias", unalias);

        // prepare aliases for registration
        commands.put("q", quit);
        commands.put("exit", quit);
    }

    public Command quit = (args) -> {
        System.exit(0);
    };

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
