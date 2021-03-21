/**
 * 
 */
package org.tools.doc.traceability.common.exceptions;

/**
 * Exception thrown if an invalid Gherkin element has been found.
 * 
 * @author Yann Leglise
 *
 */
public class InvalidGherkinContentsException extends AbstractTraceabilityException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 120706438649213930L;

    /**
     * Constructor.
     * 
     * @param pMessage the Gherkin error description.
     */
    public InvalidGherkinContentsException(final String pMessage) {
        super(pMessage);
    }

}
