/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration.appmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Class modeling the relative path to different elements inside a project.
 * 
 * @author Yann Leglise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectRelativePathConfiguration extends AbstractConfigurationModel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -3524052975920403026L;

    /**
     * The relative path to the SD files.
     */
    @XmlElement(name = "ToSdFiles")
    private String relativePathToSpecificationDocumentFiles;

    /**
     * The relative path to the ALM extration files.
     */
    @XmlElement(name = "ToAlmExtractedFiles")
    private String relativePathToAlmExtrationFiles;

    /**
     * The relative path to the file containing the requirements extracted from
     * the SD.
     */
    @XmlElement(name = "ToExtractedReqFiles")
    private String relativePathToExtractedRequirementsFiles;

    /**
     * The relative path to the VTP files.
     */
    @XmlElement(name = "ToVtpFiles")
    private String relativePathToVtpFiles;

    /**
     * The relative path to the VTP files.
     */
    @XmlElement(name = "ToTmFiles")
    private String relativePathToTraceabilityMatrixFiles;

    /**
     * Constructor.
     */
    public ProjectRelativePathConfiguration() {
        relativePathToSpecificationDocumentFiles = null;
        relativePathToAlmExtrationFiles = null;
        relativePathToExtractedRequirementsFiles = null;
        relativePathToVtpFiles = null;
        relativePathToTraceabilityMatrixFiles = null;
    }

    /**
     * Getter of the relativePathToSpecificationDocumentFiles.
     * 
     * @return the relativePathToSpecificationDocumentFiles
     */
    public String getRelativePathToSpecificationDocumentFiles() {
        return relativePathToSpecificationDocumentFiles;
    }

    /**
     * Setter of the relativePathToSpecificationDocumentFiles.
     * 
     * @param pRelativePathToSpecificationDocumentFiles the
     * relativePathToSpecificationDocumentFiles to set
     */
    public void setRelativePathToSpecificationDocumentFiles(final String pRelativePathToSpecificationDocumentFiles) {
        relativePathToSpecificationDocumentFiles = pRelativePathToSpecificationDocumentFiles;
    }

    /**
     * Getter of the relativePathToAlmExtrationFiles.
     * 
     * @return the relativePathToAlmExtrationFiles
     */
    public String getRelativePathToAlmExtrationFiles() {
        return relativePathToAlmExtrationFiles;
    }

    /**
     * Setter of the relativePathToAlmExtrationFiles.
     * 
     * @param pRelativePathToAlmExtrationFiles the
     * relativePathToAlmExtrationFiles to set
     */
    public void setRelativePathToAlmExtrationFiles(final String pRelativePathToAlmExtrationFiles) {
        relativePathToAlmExtrationFiles = pRelativePathToAlmExtrationFiles;
    }

    /**
     * Getter of the relativePathToExtractedRequirementsFiles.
     * 
     * @return the relativePathToExtractedRequirementsFiles
     */
    public String getRelativePathToExtractedRequirementsFiles() {
        return relativePathToExtractedRequirementsFiles;
    }

    /**
     * Setter of the relativePathToExtractedRequirementsFiles.
     * 
     * @param pRelativePathToExtractedRequirementsFiles the
     * relativePathToExtractedRequirementsFiles to set
     */
    public void setRelativePathToExtractedRequirementsFiles(final String pRelativePathToExtractedRequirementsFiles) {
        relativePathToExtractedRequirementsFiles = pRelativePathToExtractedRequirementsFiles;
    }

    /**
     * Getter of the relativePathToVtpFiles.
     * 
     * @return the relativePathToVtpFiles
     */
    public String getRelativePathToVtpFiles() {
        return relativePathToVtpFiles;
    }

    /**
     * Setter of the relativePathToVtpFiles.
     * 
     * @param pRelativePathToVtpFiles the relativePathToVtpFiles to set
     */
    public void setRelativePathToVtpFiles(final String pRelativePathToVtpFiles) {
        relativePathToVtpFiles = pRelativePathToVtpFiles;
    }

    /**
     * Getter of the relativePathToTraceabilityMatrixFiles.
     * 
     * @return the relativePathToTraceabilityMatrixFiles
     */
    public String getRelativePathToTraceabilityMatrixFiles() {
        return relativePathToTraceabilityMatrixFiles;
    }

    /**
     * Setter of the relativePathToTraceabilityMatrixFiles.
     * 
     * @param pRelativePathToTraceabilityMatrixFiles the
     * relativePathToTraceabilityMatrixFiles to set
     */
    public void setRelativePathToTraceabilityMatrixFiles(final String pRelativePathToTraceabilityMatrixFiles) {
        relativePathToTraceabilityMatrixFiles = pRelativePathToTraceabilityMatrixFiles;
    }

}
