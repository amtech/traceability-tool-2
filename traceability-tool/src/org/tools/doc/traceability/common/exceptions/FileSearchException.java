/**
 * 
 */
package org.tools.doc.traceability.common.exceptions;

/**
 * Class thrown if an error prevented searching for files.
 * 
 * @author Yann Leglise
 *
 */
public class FileSearchException extends AbstractTraceabilityException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 5064323413523897352L;

    /**
     * Constructor.
     * 
     * @param pMessage the description of what went wrong.
     * @param pCause the cause of the error.
     */
    public FileSearchException(final String pMessage, final Throwable pCause) {
        super(pMessage, pCause);
    }

    /**
     * Constructor.
     * 
     * @param pMessage the description of what went wrong.
     */
    public FileSearchException(final String pMessage) {
        super(pMessage);
    }

}
