/**
 * 
 */
package org.tools.doc.traceability.common.exceptions;

/**
 * Common class for exceptions for this project.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractTraceabilityException extends Exception {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 7693040828471409439L;

    /**
     * Constructor.
     * 
     * @param pMessage exception message.
     * @param pCause   embedded cause.
     */
    public AbstractTraceabilityException(final String pMessage, final Throwable pCause) {
        super(pMessage, pCause);
    }

    /**
     * Constructor.
     * 
     * @param pMessage exception message.
     */
    public AbstractTraceabilityException(final String pMessage) {
        super(pMessage);
    }
}
