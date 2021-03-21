/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration.appmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Class modeling the general configuration.
 * 
 * @author Yann Leglise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GeneralConfiguration extends AbstractConfigurationModel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 5818162353598164399L;

    /**
     * The base Git directory path.
     */
    @XmlElement(name = "BaseGitDirectory")
    private String baseGitDirectory;

    /**
     * The data about the project relative directories for a project.
     */
    @XmlElement(name = "ProjectRelativePathConfiguration")
    private ProjectRelativePathConfiguration projectRelativePathConfiguration;

    /**
     * The relative path between the base Git directory and the directory
     * containing the justification files.RelativePathToJustificationFiles.
     */
    @XmlElement(name = "RelativePathToJustificationFiles")
    private String relativePathToJustificationFiles;

    /**
     * The regular expression for the unit tests method name.
     */
    @XmlElement(name = "UnitTestMethodSimpleRegexp")
    private String unitTestMethodSimpleRegexp;

    /**
     * Constructor.
     */
    public GeneralConfiguration() {
        baseGitDirectory = null;
        projectRelativePathConfiguration = null;
        relativePathToJustificationFiles = null;
        unitTestMethodSimpleRegexp = null;
    }

    /**
     * Getter of the base Git directory.
     * 
     * @return the baseGitDirectory
     */
    public String getBaseGitDirectory() {
        return baseGitDirectory;
    }

    /**
     * Getter of the unitTestMethodSimpleRegexp.
     * 
     * @return the unitTestMethodSimpleRegexp
     */
    public String getUnitTestMethodSimpleRegexp() {
        return unitTestMethodSimpleRegexp;
    }

    /**
     * Setter of the unitTestMethodSimpleRegexp.
     * 
     * @param pUnitTestMethodSimpleRegexp the unitTestMethodSimpleRegexp to set
     */
    public void setUnitTestMethodSimpleRegexp(final String pUnitTestMethodSimpleRegexp) {
        unitTestMethodSimpleRegexp = pUnitTestMethodSimpleRegexp;
    }

    /**
     * Setter of the baseGitDirectory.
     * 
     * @param pBaseGitDirectory the baseGitDirectory to set
     */
    public void setBaseGitDirectory(final String pBaseGitDirectory) {
        baseGitDirectory = pBaseGitDirectory;
    }

    /**
     * Getter of the projectRelativePathConfiguration.
     * 
     * @return the projectRelativePathConfiguration
     */
    public ProjectRelativePathConfiguration getProjectRelativePathConfiguration() {
        return projectRelativePathConfiguration;
    }

    /**
     * Setter of the projectRelativePathConfiguration.
     * 
     * @param pProjectRelativePathConfiguration the
     * projectRelativePathConfiguration to set
     */
    public void setProjectRelativePathConfiguration(
            final ProjectRelativePathConfiguration pProjectRelativePathConfiguration) {
        projectRelativePathConfiguration = pProjectRelativePathConfiguration;
    }

    /**
     * Getter of the relative path between the base Git directory and the
     * directory containing the justification files.
     * 
     * @return the relativePathToJustificationFiles
     */
    public String getRelativePathToJustificationFiles() {
        return relativePathToJustificationFiles;
    }

    /**
     * Setter of the relative path between the base Git directory and the
     * directory containing the justification files.
     * 
     * @param pRelativePathToJustificationFiles the
     * relativePathToJustificationFiles to set
     */
    public void setRelativePathToJustificationFiles(final String pRelativePathToJustificationFiles) {
        relativePathToJustificationFiles = pRelativePathToJustificationFiles;
    }

}
