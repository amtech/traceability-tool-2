/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration.appmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Class modeling the configuration for cucumber coverage.
 * 
 * @author Yann Leglise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CucumberTestsCoverage extends AbstractConfigurationModel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -2530116466069767635L;

    /**
     * Whether the coverage is active or not.
     */
    @XmlAttribute(name = "isActive")
    private boolean isActive;

    /**
     * The file selection filter.
     */
    @XmlElement(name = "FileSelectionFilter")
    private FileSelectionFilter fileSelectionFilter;

    /**
     * Constructor.
     */
    public CucumberTestsCoverage() {
        isActive = false;
        fileSelectionFilter = null;
    }

    /**
     * Getter of the isActive.
     * 
     * @return the isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Setter of the isActive.
     * 
     * @param pIsActive the isActive to set
     */
    public void setActive(final boolean pIsActive) {
        isActive = pIsActive;
    }

    /**
     * Getter of the fileSelectionFilter.
     * 
     * @return the fileSelectionFilter
     */
    public FileSelectionFilter getFileSelectionFilter() {
        return fileSelectionFilter;
    }

    /**
     * Setter of the fileSelectionFilter.
     * 
     * @param pFileSelectionFilter the fileSelectionFilter to set
     */
    public void setFileSelectionFilter(final FileSelectionFilter pFileSelectionFilter) {
        fileSelectionFilter = pFileSelectionFilter;
    }

}
