package robyn.cliTools.exceptions;

/**
 * A custom Exception type
 */
public class CommandFailureException extends Exception {

    public CommandFailureException(String message) {
        super(message);
    }

}
