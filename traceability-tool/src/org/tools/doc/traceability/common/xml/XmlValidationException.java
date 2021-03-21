/**
 * 
 */
package org.tools.doc.traceability.common.xml;

import org.xml.sax.SAXParseException;

/**
 * Exception in relation with XML validation problem.
 * 
 * @author Yann Leglise
 *
 */
public class XmlValidationException extends SAXParseException {

    /**
     * Serial id.
     */
    private static final long serialVersionUID = -5414883464613007893L;

    /**
     * Create a new SAXParseException.
     * 
     * 
     * @param pMessage      The error or warning message.
     * @param pPublicId     The public identifier of the entity that generated the
     *                      error or warning.
     * @param pSystemId     The system identifier of the entity that generated the
     *                      error or warning.
     * @param pLineNumber   The line number of the end of the text that caused the
     *                      error or warning.
     * @param pColumnNumber The column number of the end of the text that cause the
     *                      error or warning.
     */
    public XmlValidationException(final String pMessage, final String pPublicId, final String pSystemId,
            final int pLineNumber, final int pColumnNumber) {
        super(pMessage, pPublicId, pSystemId, pLineNumber, pColumnNumber);
    }

}
