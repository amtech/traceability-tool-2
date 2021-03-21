/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration.appmodel;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The root node of the configuration.
 * 
 * @author Yann Leglise
 *
 */
@XmlRootElement(name = "TraceablitiyToolsConfiguration")
public class TraceablitiyToolsConfiguration extends AbstractConfigurationModel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -8678415193113868858L;

    /**
     * The version of the configuration.
     */
    @XmlAttribute(name = "version", required = true)
    private int version;

    /**
     * The general configuration.
     */
    @XmlElement(name = "GeneralConfigurationSection")
    private GeneralConfiguration generalConfiguration;

    /**
     * The configuration of the Coverage Matrix Generator.
     */
    @XmlElement(name = "CoverageMatrixConfigurationSection")
    private CoverageMatrixConfiguration coverageMatrixConfiguration;

    /**
     * The application configuration section.
     */
    @XmlElement(name = "ApplicationConfigurationSection")
    private ApplicationConfigurationSection applicationConfigurationSection;

    /**
     * Constructor.
     */
    public TraceablitiyToolsConfiguration() {
        version = 0;
        generalConfiguration = null;
        coverageMatrixConfiguration = null;
        applicationConfigurationSection = null;
    }

    /**
     * Getter of the version.
     * 
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * Getter of the general configuration.
     * 
     * @return the generalConfiguration
     */
    public GeneralConfiguration getGeneralConfiguration() {
        return generalConfiguration;
    }

    /**
     * Getter of the coverage matrix tool configuration.
     * 
     * @return the coverageMatrixConfiguration
     */
    public CoverageMatrixConfiguration getCoverageMatrixConfiguration() {
        return coverageMatrixConfiguration;
    }

    /**
     * Getter of the applicationConfigurationSection.
     * 
     * @return the applicationConfigurationSection
     */
    public ApplicationConfigurationSection getApplicationConfigurationSection() {
        return applicationConfigurationSection;
    }
}
