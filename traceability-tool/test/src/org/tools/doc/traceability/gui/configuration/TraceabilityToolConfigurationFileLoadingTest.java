/**
 * 
 */
package org.tools.doc.traceability.gui.configuration;

import java.io.File;

import org.apache.xmlbeans.XmlException;
import org.junit.Assert;
import org.junit.Test;

import org.tools.doc.traceability.common.exceptions.ConfigurationErrorException;
import org.tools.doc.traceability.common.xml.XmlManager;
import org.tools.doc.traceability.manager.gui.configuration.ConfigurationManager;

/**
 * @author Yann Leglise
 *
 */
public class TraceabilityToolConfigurationFileLoadingTest {

    /**
     * Test the validation of the configuration file against the XSD.
     */
    @Test
    public void testConfigFileValidation() {
        XmlManager lXmlManager = XmlManager.getInstance();

        File lXmlFile = new File("./generation/config/app-config.xml");

        String lXsdFilename = "app-config.xsd";

        try {
            lXmlManager.validateXmlAgainstXsd(lXmlFile, lXsdFilename);
        } catch (IllegalArgumentException | XmlException e) {
            System.err.println(e);
            Assert.fail("Error validating file : " + e);
        }
    }

    /**
     * Test the loading of configuration file.
     */
    @Test
    public void testConfigurationFileLoading() {
        ConfigurationManager lSut = ConfigurationManager.getInstance();

        try {
            lSut.loadFromConfigurationFile();
        } catch (ConfigurationErrorException e) {
            Assert.fail("Error loading the configuration file : " + e);
        }
    }

}
