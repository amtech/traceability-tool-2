/**
 * 
 */
package org.tools.doc.traceability.common.exceptions;

/**
 * Exception thrown when a Traceability manager context is invalid.
 * 
 * @author Yann Leglise
 *
 */
public class InvalidTraceabilityManagerContextException extends AbstractTraceabilityException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 8535316059875773202L;

    /**
     * Constructor.
     * 
     * @param pContextCharacteristic the characteristic of the context that is
     * wrong.
     * @param pInvalidityDescription the reason why this characteristic is
     * wrong.
     */
    public InvalidTraceabilityManagerContextException(final String pContextCharacteristic,
            final String pInvalidityDescription) {
        super("The traceability manager context is invalid for " + pContextCharacteristic + " : "
                + pInvalidityDescription);
    }

}
