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
        for (String key : commands.keySet()) {
            try {
                cli.reg.deregisterCommand(key);
            } catch (CommandIdentifierException e) {
                cli.println(e.getMessage());
            }
        }
    }
    // /**
    // * register all commands and aliases in this container
    // * any conflicts are reported and skipped
    // *
    // * @param reg object to register commands to
    // * NOTE not all containers will have the cli to access its register
    // */
    // public void registerAll(CommandRegister reg);
    //
    // /**
    // * deregisters all commands
    // * aliases are automatically deregistered by the CommandRegister instance
    // *
    // * @param reg object to deregister commands from
    // */
    // public void deregisterAll(CommandRegister reg);
}
