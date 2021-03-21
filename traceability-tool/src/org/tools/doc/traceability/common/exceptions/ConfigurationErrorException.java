/**
 * 
 */
package org.tools.doc.traceability.common.exceptions;

/**
 * Exception linked to configuration issue.
 * 
 * @author Yann Leglise
 *
 */
public class ConfigurationErrorException extends AbstractTraceabilityException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -5501507923230871423L;

    /**
     * Constructor.
     * 
     * @param pMessage the error message.
     */
    public ConfigurationErrorException(final String pMessage) {
        super(pMessage);
    }

}
