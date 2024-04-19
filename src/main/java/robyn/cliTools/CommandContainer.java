package robyn.cliTools;

import java.util.HashMap;
import java.util.Map;

import robyn.cliTools.exceptions.CommandIdentifierException;

public abstract class CommandContainer {
    public Map<String, Command> commands = new HashMap<>();
    public Map<String, String> aliases = new HashMap<>();

    public void registerAll(CLI cli) {
        // commands
        for (String key : commands.keySet()) {
            try {
                cli.reg.registerCommand(key, commands.get(key));
            } catch (CommandIdentifierException e) {
                cli.println(e.getMessage());
            }
        }
        // aliases
        for (String key : aliases.keySet()) {
            try {
                cli.reg.registerAlias(key, aliases.get(key));
            } catch (CommandIdentifierException e) {
                cli.println(e.getMessage());
            }
        }
    }

    public void deregisterAll(CLI cli) {
        // commands
        for (String key : commands.keySet()) {
            try {
                cli.reg.deregisterCommand(key);
            } catch (CommandIdentifierException e) {
                cli.println(e.getMessage());
            }
        }
        // aliases
        // most aliases will be
        for (String key : aliases.keySet()) {
            try {
                if (aliases.keySet().contains(key)) {
                    cli.reg.registerAlias(key, aliases.get(key));
                }
                // else the alias was automatically removed because it was
                // linked to a command which was removed earlier in this method
            } catch (CommandIdentifierException e) {
                cli.println(e.getMessage());
            }
        }
    }
}
