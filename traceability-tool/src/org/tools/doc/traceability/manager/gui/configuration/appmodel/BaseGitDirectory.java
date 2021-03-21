/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration.appmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Models the information about the base Git directory.
 * 
 * @author Yann Leglise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseGitDirectory extends AbstractConfigurationModel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 362154766231923811L;

    /**
     * The location of the Git base directory.
     */
    @XmlElement(name = "Location")
    private String location;

    /**
     * Whether the JogetPlugin level is present or not.
     */
    @XmlElement(name = "JogetPluginLevelPresent")
    private boolean isJogetPluginLevelPresent;

    /**
     * Constructor.
     */
    public BaseGitDirectory() {
        location = null;
        isJogetPluginLevelPresent = false;
    }

    /**
     * Getter of the location of the Git base directory.
     * 
     * @return the gitBaseDirectory
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter of the flag indicating whether the JogetPlugin level is present or
     * not.
     * 
     * @return the isJogetPluginLevelPresent
     */
    public boolean isJogetPluginLevelPresent() {
        return isJogetPluginLevelPresent;
    }

}
