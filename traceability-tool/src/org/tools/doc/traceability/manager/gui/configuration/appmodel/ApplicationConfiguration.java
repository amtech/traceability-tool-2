/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration.appmodel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.tools.doc.traceability.manager.gui.data.app.ApplicationData;
import org.tools.doc.traceability.manager.gui.data.app.coverage.AlmTestsCoverage;
import org.tools.doc.traceability.manager.gui.data.app.coverage.CSharpUnitTestsCoverage;
import org.tools.doc.traceability.manager.gui.data.app.coverage.CucumberTestsCoverage;
import org.tools.doc.traceability.manager.gui.data.app.coverage.JavaUnitTestsCoverage;

/**
 * A class corresponding to the configuration of a given application.
 * 
 * @author Yann Leglise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplicationConfiguration extends AbstractConfigurationModel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 2634386407173778187L;

    /**
     * The application identifier.
     */
    @XmlAttribute(name = "id")
    private String applicationIdentifier;

    /**
     * The display name.
     */
    @XmlElement(name = "GeneralCharacteristics")
    private GeneralCharacteristics generalCharacteristics;

    /**
     * The specification data.
     */
    @XmlElement(name = "SpecificationData")
    private SpecificationData specificationData;

    /**
     * The coverage data.
     */
    @XmlElement(name = "CoverageData")
    private CoverageData coverageData;

    /**
     * The output data.
     */
    @XmlElement(name = "OutputData")
    private OutputData outputData;

    /**
     * Constructor.
     */
    public ApplicationConfiguration() {
        applicationIdentifier = null;
        generalCharacteristics = null;
        specificationData = null;
        coverageData = null;
        outputData = null;
    }

    /**
     * Getter of the general characteristics.
     * 
     * @return the generalCharacteristics
     */
    public GeneralCharacteristics getGeneralCharacteristics() {
        return generalCharacteristics;
    }

    /**
     * Setter of the general characteristics.
     * 
     * @param pGeneralCharacteristics the generalCharacteristics to set
     */
    public void setGeneralCharacteristics(final GeneralCharacteristics pGeneralCharacteristics) {
        generalCharacteristics = pGeneralCharacteristics;
    }

    /**
     * Getter of the specification data.
     * 
     * @return the specificationData
     */
    public SpecificationData getSpecificationData() {
        return specificationData;
    }

    /**
     * Getter of the coverage data.
     * 
     * @return the coverageData
     */
    public CoverageData getCoverageData() {
        return coverageData;
    }

    /**
     * Getter of the output data.
     * 
     * @return the outputData
     */
    public OutputData getOutputData() {
        return outputData;
    }

    /**
     * Getter of the application identifier.
     * 
     * @return the applicationIdentifier
     */
    public String getApplicationIdentifier() {
        return applicationIdentifier;
    }

    /**
     * Convert an instance of this class to a {@link ApplicationData}.
     * 
     * @return the converted instance.
     */
    public ApplicationData toApplicationData() {
        ApplicationData lApplicationData = null;

        String lDisplayName = null;
        String lGitDirName = null;
        String lSdFileName = null;
        String lRequirementPrefixes = null;
        String lJustificationFileName = null;
        String lTargetVtpFileName = null;
        String lTargetMatrixFileName = null;
        String lTargetExtractedRequirementsFileName = null;
        AlmTestsCoverage lAlmTestsCoverage = null;
        CucumberTestsCoverage lCucumberTestsCoverage = null;
        CSharpUnitTestsCoverage lCSharpUnitTestsCoverage = null;
        JavaUnitTestsCoverage lJavaUnitTestsCoverage = null;

        if (generalCharacteristics != null) {
            lDisplayName = getNotNullOrEmptyValue(generalCharacteristics.getDisplayName());
            lGitDirName = getNotNullOrEmptyValue(generalCharacteristics.getGitRelativeDirectoryPath());
        }

        if (specificationData != null) {
            lSdFileName = getNotNullOrEmptyValue(specificationData.getSpecificationDocumentFilename());
            List<String> lReqPrefixes = specificationData.getRequirementPrefixes();
            if (lReqPrefixes != null) {
                StringBuilder lPrefixesSb = new StringBuilder();
                boolean lIsFirst = true;
                for (String lReqPrefix : lReqPrefixes) {
                    if (lIsFirst) {
                        lIsFirst = false;
                    } else {
                        lPrefixesSb.append(",");
                    }
                    lPrefixesSb.append(lReqPrefix);
                }
                lRequirementPrefixes = lPrefixesSb.toString();
            }
        }

        if (coverageData != null) {
            lJustificationFileName = getNotNullOrEmptyValue(coverageData.getJustificationFilename());

            lAlmTestsCoverage = new AlmTestsCoverage(coverageData.getAlmTestsCoverage().isActive(), coverageData
                    .getAlmTestsCoverage().getAlmExtractedFilename());

            lCucumberTestsCoverage = new CucumberTestsCoverage(coverageData.getCucumberTestsCoverage().isActive(),
                    coverageData.getCucumberTestsCoverage().getFileSelectionFilter());

            lCSharpUnitTestsCoverage = new CSharpUnitTestsCoverage(
                    coverageData.getcSharpUnitTestsCoverage().isActive(), coverageData.getcSharpUnitTestsCoverage()
                            .getFileSelectionFilter(), coverageData.getcSharpUnitTestsCoverage()
                            .getMethodNameSimpleRegexp());

            lJavaUnitTestsCoverage = new JavaUnitTestsCoverage(coverageData.getJavaUnitTestsCoverage().isActive(),
                    coverageData.getJavaUnitTestsCoverage().getFileSelectionFilter(), coverageData
                            .getJavaUnitTestsCoverage().getMethodNameSimpleRegexp());
        }

        if (outputData != null) {
            lTargetVtpFileName = getNotNullOrEmptyValue(outputData.getTargetVtpFilename());
            lTargetMatrixFileName = getNotNullOrEmptyValue(outputData.getTargetMatrixFilename());
            lTargetExtractedRequirementsFileName = getNotNullOrEmptyValue(outputData
                    .getTargetExtractedRequirementsFilename());
        }

        lApplicationData = new ApplicationData(applicationIdentifier, lDisplayName, lGitDirName,
                 lSdFileName, lRequirementPrefixes, lJustificationFileName,
                lAlmTestsCoverage, lCucumberTestsCoverage, lCSharpUnitTestsCoverage, lJavaUnitTestsCoverage,
                lTargetVtpFileName, lTargetMatrixFileName, lTargetExtractedRequirementsFileName);

        return lApplicationData;
    }

    /**
     * Get the trimmed parameter value, or null if null or empty when trimmed.
     * 
     * @param pValue the value to consider.
     * @return the trimmed value or null.
     */
    private String getNotNullOrEmptyValue(final String pValue) {
        String lValue = null;

        if (pValue != null) {
            if (pValue.trim().length() > 0) {
                lValue = pValue.trim();
            }
        }

        return lValue;
    }
}
