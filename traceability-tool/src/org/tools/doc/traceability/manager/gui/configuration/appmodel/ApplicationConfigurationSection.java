/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration.appmodel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * The node of the configuration representing the application configuration
 * section..
 * 
 * @author Yann Leglise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplicationConfigurationSection extends AbstractConfigurationModel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 242575061165470850L;
    /**
     * The identifier of the default application configuration.
     */
    @XmlAttribute(name = "default", required = true)
    private String defaultApplicationConfigurationIdentifier;

    /**
     * The list of associated application configurations.
     */
    @XmlElement(name = "ApplicationConfiguration")
    private List<ApplicationConfiguration> applicationConfigurationList;

    /**
     * Constructor.
     */
    public ApplicationConfigurationSection() {
        defaultApplicationConfigurationIdentifier = null;
        applicationConfigurationList = null;
    }

    /**
     * Getter of the defaultApplicationConfigurationIdentifier.
     * 
     * @return the defaultApplicationConfigurationIdentifier
     */
    public String getDefaultApplicationConfigurationIdentifier() {
        return defaultApplicationConfigurationIdentifier;
    }

    /**
     * Getter of the applicationConfigurationList.
     * 
     * @return the applicationConfigurationList
     */
    public List<ApplicationConfiguration> getApplicationConfigurationList() {
        return applicationConfigurationList;
    }
}
