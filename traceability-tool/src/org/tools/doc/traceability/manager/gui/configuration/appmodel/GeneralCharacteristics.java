/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration.appmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * A class corresponding to the general characteristics of an application.
 * 
 * @author Yann Leglise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GeneralCharacteristics extends AbstractConfigurationModel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 6997590927527132047L;

    /**
     * The display name.
     */
    @XmlElement(name = "DisplayName")
    private String displayName;

    /**
     * The associated GIT relative directory path.
     */
    @XmlElement(name = "GitRelativeDirPath")
    private String gitRelativeDirectoryPath;

    /**
     * Constructor.
     */
    public GeneralCharacteristics() {
        displayName = "";
        gitRelativeDirectoryPath = "";
    }

    /**
     * Getter of the display name.
     * 
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Getter of the GIT relative directory path.
     * 
     * @return the gitRelativeDirectoryPath
     */
    public String getGitRelativeDirectoryPath() {
        return gitRelativeDirectoryPath;
    }
}
