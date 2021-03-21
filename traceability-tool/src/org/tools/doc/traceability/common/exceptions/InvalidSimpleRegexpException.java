/**
 * 
 */
package org.tools.doc.traceability.common.exceptions;

/**
 * If a simple regular expression was invalid.
 * 
 * @author Yann Leglise
 *
 */
public class InvalidSimpleRegexpException extends AbstractTraceabilityException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 4939294983865655966L;

    /**
     * Constructor.
     * 
     * @param pSimpleRegexp the value of the simple regular expression.
     * @param pInvalidityReason the reason why it is invalid.
     */
    public InvalidSimpleRegexpException(final String pSimpleRegexp, final String pInvalidityReason) {
        super("Could not use \"" + pSimpleRegexp + "\" for a simple regex : " + pInvalidityReason);
    }

}
