/**
 * 
 */
package org.tools.doc.traceability.manager.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.tools.doc.traceability.common.executor.AbtsractExecutionResultObject;
import org.tools.doc.traceability.common.model.Requirement;
import org.tools.doc.traceability.covmatrixgen.model.RequirementTestCovering;
import org.tools.doc.traceability.reqextraction.FileRequirementExtractorResult;
import org.tools.doc.traceability.reqextraction.helper.RequirementDuplicationItem;

/**
 * The result obtained by the Traceability manager tool.
 * 
 * @author Yann Leglise
 *
 */
public class TraceabilityManagerResultObject extends AbtsractExecutionResultObject {

    /**
     * The requirement test covering.
     */
    private RequirementTestCovering requirementTestCovering;

    /**
     * The list of not covered requirements.
     */
    private List<Requirement> notCoveredRequirementList;

    /**
     * The map associating to each input SD file the detailed requirement
     * extraction result.
     */
    private Map<File, FileRequirementExtractorResult> sdFileReqExtractionResultMap;

    /**
     * The list of duplicated requirements.
     */
    private List<RequirementDuplicationItem> duplicatedRequirements;

    /**
     * Constructor.
     */
    public TraceabilityManagerResultObject() {
        requirementTestCovering = null;
        notCoveredRequirementList = null;
        sdFileReqExtractionResultMap = null;
        duplicatedRequirements = null;
    }

    /**
     * Getter of the requirement test covering.
     * 
     * @return the requirementTestCovering
     */
    public RequirementTestCovering getRequirementTestCovering() {
        return requirementTestCovering;
    }

    /**
     * Setter of the requirement test covering.
     * 
     * @param pRequirementTestCovering the requirementTestCovering to set
     */
    public void setRequirementTestCovering(final RequirementTestCovering pRequirementTestCovering) {
        requirementTestCovering = pRequirementTestCovering;
    }

    /**
     * Getter of the list of not covered requirements.
     * 
     * @return the notCoveredRequirementList
     */
    public List<Requirement> getNotCoveredRequirementList() {
        return notCoveredRequirementList;
    }

    /**
     * Setter of the list of not covered requirements.
     * 
     * @param pNotCoveredRequirementList the notCoveredRequirementList to set
     */
    public void setNotCoveredRequirementList(final List<Requirement> pNotCoveredRequirementList) {
        notCoveredRequirementList = pNotCoveredRequirementList;
    }

    /**
     * Getter of the map associating to each input SD file the detailed
     * requirement extraction result..
     * 
     * @return the sdFileReqExtractionResultMap
     */
    public Map<File, FileRequirementExtractorResult> getSdFileReqExtractionResultMap() {
        return sdFileReqExtractionResultMap;
    }

    /**
     * Setter of the map associating to each input SD file the detailed
     * requirement extraction result..
     * 
     * @param pSdFileReqExtractionResultMap the sdFileReqExtractionResultMap to
     * set
     */
    public void setSdFileReqExtractionResultMap(
            final Map<File, FileRequirementExtractorResult> pSdFileReqExtractionResultMap) {
        sdFileReqExtractionResultMap = pSdFileReqExtractionResultMap;
    }

    /**
     * Getter of the list of duplicated requirements.
     * 
     * @return the duplicatedRequirements
     */
    public List<RequirementDuplicationItem> getDuplicatedRequirements() {
        return duplicatedRequirements;
    }

    /**
     * Setter of the list of duplicated requirements.
     * 
     * @param pDuplicatedRequirements the duplicatedRequirements to set
     */
    public void setDuplicatedRequirements(final List<RequirementDuplicationItem> pDuplicatedRequirements) {
        duplicatedRequirements = pDuplicatedRequirements;
    }

    /**
     * Builds the list of all the SD requirements.
     * 
     * @return the list of all requirements.
     */
    public List<Requirement> getAllSdRequirements() {
        List<Requirement> lAllRequirements = new ArrayList<Requirement>();

        for (FileRequirementExtractorResult lFrer : sdFileReqExtractionResultMap.values()) {
            lAllRequirements.addAll(lFrer.getRequirements());
        }

        return lAllRequirements;
    }

}
