/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration.appmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Models the configuration for ALM coverage.
 * 
 * @author Yann Leglise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AlmTestsCoverage extends AbstractConfigurationModel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 410444497460962596L;

    /**
     * Whether the coverage is active or not.
     */
    @XmlAttribute(name = "isActive")
    private boolean isActive;

    /**
     * The name of the extracted ALM file.
     */
    @XmlElement(name = "AlmExtractedFile")
    private String almExtractedFilename;

    /**
     * Constructor.
     */
    public AlmTestsCoverage() {
        isActive = false;
        almExtractedFilename = null;
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
     * Getter of the almExtractedFilename.
     * 
     * @return the almExtractedFilename
     */
    public String getAlmExtractedFilename() {
        return almExtractedFilename;
    }

    /**
     * Setter of the almExtractedFilename.
     * 
     * @param pAlmExtractedFilename the almExtractedFilename to set
     */
    public void setAlmExtractedFilename(final String pAlmExtractedFilename) {
        almExtractedFilename = pAlmExtractedFilename;
    }

}
