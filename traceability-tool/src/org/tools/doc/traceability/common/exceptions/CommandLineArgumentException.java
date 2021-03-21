/**
 * 
 */
package org.tools.doc.traceability.common.exceptions;

/**
 * Exception thrown when a command line argument was missing or was wrong.
 * 
 * @author Yann Leglise
 *
 */
public class CommandLineArgumentException extends AbstractTraceabilityException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 3557069704937687174L;

    /**
     * Constructor.
     * 
     * @param pCmdLineToolName          the name of the command line tool.
     * @param pArgumentName             the argument name.
     * @param pArgumentErrorDescription the reason why there is a problem.
     */
    public CommandLineArgumentException(final String pCmdLineToolName, final String pArgumentName,
            final String pArgumentErrorDescription) {
        super("Error for argument " + pArgumentName + " of " + pCmdLineToolName + " : " + pArgumentErrorDescription);
    }

}
