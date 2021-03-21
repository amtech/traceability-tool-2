/**
 * 
 */
package org.tools.doc.traceability.common.xml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.xmlbeans.XmlException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotSupportedException;

/**
 * Class to deal with XML.
 * 
 * @author Yann Leglise
 *
 */
public final class XmlManager {

    /**
     * Unique class instance.
     */
    private static XmlManager Instance;

    /**
     * Getter of the unique class instance.
     * <p>
     * If not created yet, then it is instantiated.
     * </p>
     * 
     * @return the unique class instance.
     */
    public static XmlManager getInstance() {
        if (Instance == null) {
            Instance = new XmlManager();
        }
        return Instance;
    }

    /**
     * Reference to the validation handler.
     */
    private final XmlValidationHandler validationHandler;

    /**
     * Reference SAX Parser.
     */
    private SAXParser saxParser;

    /**
     * Hidden constructor.
     */
    private XmlManager() {
        validationHandler = new XmlValidationHandler();
    }

    /**
     * Build (if not defined) and set the SAX parser.
     * 
     * @param pXsdFilename the name of the validating XSD file (will be searched
     *                     from within jar or classpath).
     * 
     * @throws ParserConfigurationException if an error prevents building the SAX
     *                                      parser.
     * @throws SAXException                 if an error prevents building the SAX
     *                                      parser.
     */
    private void setSaxParser(final String pXsdFilename) throws ParserConfigurationException, SAXException {
        SAXParserFactory lSaxParserFactory = SAXParserFactory.newInstance();

        // Enable the factory method to aware about schema features.
        lSaxParserFactory.setFeature("http://xml.org/sax/features/validation", true);

        // Enable the factory method to aware about validation features.
        lSaxParserFactory.setFeature("http://apache.org/xml/features/validation/schema", true);

        // Enable the factory method to aware about namespaces features.
        lSaxParserFactory.setFeature("http://xml.org/sax/features/namespaces", true);

        lSaxParserFactory.setNamespaceAware(true);

        // Enable the parser factory to validate XML contents against XSD.
        lSaxParserFactory.setValidating(true);

        SchemaFactory lSchemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        URL lXsdUrl = ClassLoader.getSystemResource(pXsdFilename);
        if (lXsdUrl == null) {
            throw new IllegalArgumentException(
                    "XSD file " + pXsdFilename + " could not be found. Check it is in the CLASSPATH");
        }

        Schema lSchema = lSchemaFactory.newSchema(lXsdUrl);
        lSaxParserFactory.setSchema(lSchema);

        saxParser = lSaxParserFactory.newSAXParser();

        // Associated a specificic SAXParser
        saxParser.getXMLReader().setErrorHandler(validationHandler);
    }

    /**
     * Validate a XML file against a XML schema.
     * 
     * <p>
     * The XSD file will be searched from witing jar or classpath.
     * </p>
     * 
     * @param pXmlFile     the XML file to validate.
     * @param pXsdFilename the XSD schema name.
     * @throws XmlException             if a XML validation error occurred (if none
     *                                  is thrown, then the XML file is valid
     *                                  against the XSD).
     * @throws XmlException             if the XML file is invalid.
     * @throws IllegalArgumentException if one of the parameter is invalid.
     */
    public void validateXmlAgainstXsd(final File pXmlFile, final String pXsdFilename)
            throws XmlException, IllegalArgumentException {
        // Check arguments
        if (pXmlFile == null) {
            throw new IllegalArgumentException("In XmlManager.validateXmlAgainstXsd, pXmlFile is null");
        }
        if (!pXmlFile.isFile()) {
            throw new IllegalArgumentException(
                    "In XmlManager.validateXmlAgainstXsd, pXmlFile " + "refers to a not existing file or a directory");
        }
        if (pXsdFilename == null) {
            throw new IllegalArgumentException("In XmlManager.validateXmlAgainstXsd, pXsdFilename is null");
        }
        // Set parsed file at validation handler level
        validationHandler.setParsedFile(pXmlFile);

        try {
            // Set the SAX parser
            setSaxParser(pXsdFilename);
            // Validate the XML file.
            saxParser.parse(pXmlFile, validationHandler);
        } catch (SAXNotSupportedException snse) {
            // Raised if XML is not well formed.
            throw new XmlException(snse);
        } catch (SAXException se) {
            /*
             * Raised if XML is not validated against XSD OR if XML is not well formed.
             */
            if (se instanceof XmlValidationException) {
                throw new XmlException(se.getMessage());
            }
            String lError = MessageFormat.format("File {0} is not well formed; ({1}).", pXmlFile.getAbsolutePath(),
                    se.getMessage());
            throw new XmlException(lError);
        } catch (IOException ioe) {
            // Raised if XML is not well formed.
            String lError = MessageFormat.format("File {0} is not well formed; ({1}).", pXmlFile.getAbsolutePath(),
                    ioe.getMessage());
            throw new XmlException(lError);
        } catch (ParserConfigurationException pce) {
            String lError = MessageFormat.format("File {0} is not well formed; ({1}).", pXmlFile.getAbsolutePath(),
                    pce.getMessage());
            throw new XmlException(lError);
        }
    }

}
