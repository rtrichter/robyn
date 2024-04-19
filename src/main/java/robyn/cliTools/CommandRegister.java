package robyn.cliTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import robyn.cliTools.exceptions.*;

/**
 * CommandRegister
 *
 * Keeps track of registered commands
 *
 * This class is not responsible for catching any of its own exceptions
 *
 * @author Ryan Richter
 */
public class CommandRegister {

    private Map<String, Command> commandMap = new HashMap<>();
    private Map<String, String> aliasMap = new HashMap<>();
    private Map<String, List<String>> reverseAliasMap = new HashMap<>();

    public Map<String, String> getAliases() {
        // return copy to protect against mutations
        return new HashMap<>(aliasMap);
    }

    /**
     * Adds a command to the register
     *
     * @param commandIdentifier
     * @param command
     *
     */
    public void registerCommand(String commandIdentifier, Command command) throws CommandIdentifierException {
        if (commandMap.keySet().contains(commandIdentifier)) {
            throw new CommandIdentifierException(
                    String.format("Command registration failed: %s is already a command"));
        }
        commandMap.put(commandIdentifier, command);
    }

    /**
     * Adds an alias (aliasIdentifier) that points to commandIdentifier
     *
     * @param aliasIdentifier
     * @param commandIdentifier
     */
    public void registerAlias(String aliasIdentifier, String commandIdentifier)
            throws CommandIdentifierException {
        String identifier = getCommandIdentifier(commandIdentifier);
        if (aliasMap.keySet().contains(aliasIdentifier)) {
            throw new CommandIdentifierException(
                    String.format("{%s} is already an alias"));
        }
        aliasMap.put(aliasIdentifier, identifier);
        List<String> aliases = reverseAliasMap.get(identifier);
        if (aliases == null) {
            reverseAliasMap.put(identifier, new ArrayList<>());
            aliases = reverseAliasMap.get(identifier);
        }
        aliases.add(aliasIdentifier);
    }

    /**
     * Removes a command from the register
     * works with either alias or command identifiers
     *
     * @param identifier
     */
    public void deregisterCommand(String identifier) throws CommandIdentifierException {
        // get commandIdentifier
        String commandIdentifier = getCommandIdentifier(identifier);

        // remove all aliases
        for (String alias : reverseAliasMap.get(commandIdentifier)) {
            aliasMap.remove(alias);
        }

        // remove reverse alias
        reverseAliasMap.remove(commandIdentifier);

        // remove command
        commandMap.remove(commandIdentifier);
    }

    /**
     * removes an alias from aliasMap (and reverseAliasMap)
     *
     * @param aliasIdentifier
     * @throws CommandIdentifierException if the identifier is invalid
     */
    public void deregisterAlias(String aliasIdentifier) throws CommandIdentifierException {
        // throws an error if the identifier is invalid
        String commandIdentifier = getCommandIdentifier(aliasIdentifier);
        aliasMap.remove(aliasIdentifier);
        reverseAliasMap.get(commandIdentifier).remove(aliasIdentifier);
    }

    /**
     * Returns the commandIdentifier for a given alias
     * Will also return the passed identifier if it is a commandIdentifier
     *
     * @param identifier
     *
     * @return commandIdentifier if it exists
     */
    public String getCommandIdentifier(String identifier) throws CommandIdentifierException {
        if (commandMap.keySet().contains(identifier)) {
            return identifier;
        }
        String commandIdentifier = aliasMap.get(identifier);
        if (commandIdentifier == null) {
            throw new CommandIdentifierException(
                    String.format("Invalid identifier: {%s}", identifier));
        }
        return commandIdentifier;
    }

    /**
     * Returns a command for a given identifier
     * Works with either command or alias identifiers
     *
     * @param identifier
     *
     * @return The Command associated with an identifier
     */
    public Command getCommand(String identifier) throws CommandIdentifierException {
        String commandIdentifier = getCommandIdentifier(identifier);
        // no need to check for a null command
        // getCommandidentifier ensures null cannot be returned
        return commandMap.get(commandIdentifier);
    }

    /**
     * Runs a command with no arguments just by calling with the identifier
     *
     * @param identifier
     */
    public void runCommand(String identifier) throws CommandIdentifierException {
        String[] args = { identifier };
        runCommand(args);
    }

    /**
     * Runs a command with the given arguments
     *
     * @param args
     * @throws CommandIdentifierException
     * 
     */
    public void runCommand(String[] args) throws CommandIdentifierException {
        // command cannot be null because of internal checks
        Command command = getCommand(args[0]);
        try {
            command.run(args);
        } catch (Exception e) {
            // this shouldn't happen but we don't want the cli to crash
            e.printStackTrace();
        }
    }

}
