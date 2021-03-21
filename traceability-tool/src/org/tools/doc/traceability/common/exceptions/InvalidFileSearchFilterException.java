/**
 * 
 */
package org.tools.doc.traceability.common.exceptions;

/**
 * Exception thrown when a FileSearchFilter is invalid.
 * 
 * @author Yann Leglise
 *
 */
public class InvalidFileSearchFilterException extends AbstractTraceabilityException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -5624402732536022382L;

    /**
     * Constructor.
     * @param pMessage the reason why the filter is invalid.
     */
    public InvalidFileSearchFilterException(final String pMessage) {
        super(pMessage);
    }

}
