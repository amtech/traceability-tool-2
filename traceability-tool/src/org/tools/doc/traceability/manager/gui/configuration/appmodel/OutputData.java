/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration.appmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Class modeling the output data.
 * 
 * @author Yann Leglise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OutputData extends AbstractConfigurationModel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -5914740384575698270L;

    /**
     * The name of the target VTP file.
     */
    @XmlElement(name = "TargetVtpFileName")
    private String targetVtpFilename;

    /**
     * The name of the target traceability matrix file.
     */
    @XmlElement(name = "TargetMatrixFileName")
    private String targetMatrixFilename;

    /**
     * The name of the target extracted requirements file.
     */
    @XmlElement(name = "TargetExtractedRequirementsFileName")
    private String targetExtractedRequirementsFilename;

    /**
     * Constructor.
     */
    public OutputData() {
        targetVtpFilename = "";
        targetMatrixFilename = "";
        targetExtractedRequirementsFilename = "";
    }

    /**
     * Getter of the target VTP file name.
     * 
     * @return the targetVtpFilename
     */
    public String getTargetVtpFilename() {
        return targetVtpFilename;
    }
    
    /**
     * Getter of the target traceability matrix file name.
     * 
     * @return the targetMatrixFilename
     */
    public String getTargetMatrixFilename() {
        return targetMatrixFilename;
    }

    /**
     * Getter of the target extracted requirements file name.
     * 
     * @return the targetExtractedRequirementsFilename
     */
    public String getTargetExtractedRequirementsFilename() {
        return targetExtractedRequirementsFilename;
    }

}
