/**
 * 
 */
package org.tools.doc.traceability.common.exceptions;

/**
 * Exception thrown when a configuration is invalid.
 * 
 * @author Yann Leglise
 *
 */
public class InvalidConfigurationException extends AbstractTraceabilityException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -5970086770705162568L;

    /**
     * Constructor.
     * 
     * @param pInvalidityDescription the description of the invalidity.
     */
    public InvalidConfigurationException(final String pInvalidityDescription) {
        super(pInvalidityDescription);
    }
}
