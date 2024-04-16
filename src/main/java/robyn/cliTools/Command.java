package robyn.cliTools;

/**
 * A functional interface for a Command
 *
 * @author Ryan Richter
 */
public interface Command {
    /**
     * Returns a string to be printed by the CLI
     */
    public String run(String[] args);
}
