/**
 * 
 */
package org.tools.doc.traceability.common.exceptions;

/**
 * Exception thrown if an error occurs while reading a file.
 * 
 * @author Yann Leglise
 *
 */
public class FileReadingException extends AbstractTraceabilityException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -3913323118749583128L;

    /**
     * Constructor.
     * 
     * @param pMessage the error message.
     * @param pCause the underlying cause.
     */
    public FileReadingException(final String pMessage, final Throwable pCause) {
        super(pMessage, pCause);
    }

    /**
     * Constructor.
     * 
     * @param pMessage the error message.
     */
    public FileReadingException(final String pMessage) {
        super(pMessage);
    }

}
