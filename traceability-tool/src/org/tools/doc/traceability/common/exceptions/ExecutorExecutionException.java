/**
 * 
 */
package org.tools.doc.traceability.common.exceptions;

/**
 * Exception thrown when a problem occurred during the execution of an executor.
 * 
 * @author Yann Leglise
 *
 */
public class ExecutorExecutionException extends AbstractTraceabilityException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 2038348019587800299L;

    /**
     * Constructor.
     * 
     * @param pMessage the error description.
     */
    public ExecutorExecutionException(final String pMessage) {
        super(pMessage);
    }

}
