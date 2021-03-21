/**
 * 
 */
package org.tools.doc.traceability.common.exceptions;

/**
 * Execption thrown when an invalid parameter is found.
 * 
 * @author Yann Leglise
 *
 */
public class InvalidParameterException extends AbstractTraceabilityException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -6944419618294549804L;

    /**
     * Constructor.
     * 
     * @param pLocation              the location where the error occurred
     *                               (class/method).
     * @param pParamDescription      the description of the parameter in question.
     * @param pInvalidityDescription the parameter problem description.
     */
    public InvalidParameterException(final String pLocation, final String pParamDescription,
            final String pInvalidityDescription) {
        super("Parameter " + pParamDescription + " provided for " + pLocation + " is invalid : "
                + pInvalidityDescription);
    }

}
