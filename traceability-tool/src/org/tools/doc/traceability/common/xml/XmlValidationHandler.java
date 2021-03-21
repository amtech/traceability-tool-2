/**
 * 
 */
package org.tools.doc.traceability.common.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Specific handler for XML validation.
 * 
 * @author Yann Leglise
 *
 */
public class XmlValidationHandler extends DefaultHandler {
    /**
     * XSD file extension.
     */
    private static final String XSD_EXTENSION = ".xsd";

    /**
     * The reference to the parsed file.
     */
    private File parsedFile;

    /**
     * Description of the error when an XSD is requested but not found.
     * <p>
     * When this happen (in {@link #resolveEntity(String, String)}) it is useless to
     * throw an exception as it will be caught by caller and will not go through the
     * application. However, the consequence of not finding the XSD will result in
     * an error, so in {@link #error(SAXParseException)} we can check if
     * {@link #xsdNotFoundError} is <tt>null</tt> or not, and if not use it to throw
     * a more appropriate exception.
     * </p>
     */
    private String xsdNotFoundError;

    /**
     * Constructor.
     */
    public XmlValidationHandler() {
        parsedFile = null;
        xsdNotFoundError = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fatalError(final SAXParseException pSaxParserEx) throws SAXException {
        String lPublicId = pSaxParserEx.getPublicId();
        if (lPublicId == null) {
            lPublicId = "";
        }
        String lSystemId = pSaxParserEx.getSystemId();
        if (lSystemId == null) {
            lSystemId = "";
        }
        String lErrorDescription = "";
        if (xsdNotFoundError != null) {
            lErrorDescription = xsdNotFoundError;
        } else {

            lErrorDescription = MessageFormat.format("{0} file ({1}) is invalid : line {2}, column {3} ({4} {5}) : {6}",
                    "Traceability configuration", getParsedFileName(), pSaxParserEx.getLineNumber(),
                    pSaxParserEx.getColumnNumber(), lPublicId, lSystemId, pSaxParserEx.getMessage());
        }
        throw new XmlValidationException(lErrorDescription, pSaxParserEx.getPublicId(), pSaxParserEx.getSystemId(),
                pSaxParserEx.getLineNumber(), pSaxParserEx.getColumnNumber());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final SAXParseException pSaxParserEx) throws SAXException {
        String lPublicId = pSaxParserEx.getPublicId();
        if (lPublicId == null) {
            lPublicId = "";
        }
        String lSystemId = pSaxParserEx.getSystemId();
        if (lSystemId == null) {
            lSystemId = "";
        }
        String lErrorDescription = "";
        if (xsdNotFoundError != null) {
            lErrorDescription = xsdNotFoundError;
        } else {
            lErrorDescription = MessageFormat.format("{0} file ({1}) is invalid : line {2}, column {3} ({4} {5}) : {6}",
                    "Traceability configuration file", getParsedFileName(), pSaxParserEx.getLineNumber(),
                    pSaxParserEx.getColumnNumber(), lPublicId, lSystemId, pSaxParserEx.getMessage());
        }
        throw new XmlValidationException(lErrorDescription, pSaxParserEx.getPublicId(), pSaxParserEx.getSystemId(),
                pSaxParserEx.getLineNumber(), pSaxParserEx.getColumnNumber());
    }

    @Override
    public void warning(final SAXParseException pSaxPe) throws SAXException {
        // Do not mention
    }

    /**
     * Getter of the parsed file name.
     * 
     * @return the parsed file name.
     */
    private String getParsedFileName() {
        String lParsedFileName = "<not defined>";
        if (parsedFile != null) {
            lParsedFileName = parsedFile.getName();
        }
        return lParsedFileName;
    }

    /**
     * Setter of the reference to the parsed file. Shall be called prior to any use.
     * 
     * @param pParsedFile the parsedFile to set.
     */
    protected void setParsedFile(final File pParsedFile) {
        parsedFile = pParsedFile;
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public InputSource resolveEntity(final String pPublicId, final String pSystemId) throws IOException, SAXException {
        InputSource lSchemaSource = null;
        String lXsdFile = null;
        if (pSystemId != null) {
            if (pSystemId.endsWith(XSD_EXTENSION)) {
                // XSD filename will probably prepended by path : strip it
                String[] lSplittedParts = pSystemId.split("\\/");
                if (lSplittedParts != null) {
                    lXsdFile = lSplittedParts[lSplittedParts.length - 1];
                }
            }
        }
        if (lXsdFile != null) {
            InputStream lXsdInputStream = ClassLoader.getSystemResourceAsStream(lXsdFile);
            if (lXsdInputStream == null) {
                String lError = MessageFormat.format(
                        "Validation file ({0}) could not be found. Check it is present in CLASSPATH.", lXsdFile);

                xsdNotFoundError = lError;
            } else {
                lSchemaSource = new InputSource(lXsdInputStream);
            }
        }
        return lSchemaSource;
    }

}
