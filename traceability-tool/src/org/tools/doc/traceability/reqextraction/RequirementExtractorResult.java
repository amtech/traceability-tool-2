/**
 * 
 */
package org.tools.doc.traceability.reqextraction;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tools.doc.traceability.common.executor.AbtsractExecutionResultObject;
import org.tools.doc.traceability.common.model.Requirement;
import org.tools.doc.traceability.reqextraction.helper.RequirementDuplicationItem;

/**
 * The result produced by the requirement extraction tool.
 * 
 * @author Yann Leglise
 *
 */
public class RequirementExtractorResult extends AbtsractExecutionResultObject {

    /**
     * The map associating to each input SD file the detailed requirement
     * extraction result.
     */
    private final Map<File, FileRequirementExtractorResult> sdFileReqExtractionResultMap;

    /**
     * The list of duplicated requirements.
     */
    private List<RequirementDuplicationItem> duplicatedRequirements;

    /**
     * Constructor.
     */
    public RequirementExtractorResult() {
        sdFileReqExtractionResultMap = new HashMap<File, FileRequirementExtractorResult>();
        duplicatedRequirements = new ArrayList<RequirementDuplicationItem>();
    }

    /**
     * Add the given SD file requirement extraction result.
     * 
     * @param pFileRequirementExtractionResult the SD file result to add.
     */
    public void addSdFileRequirementExtractionResult(
            final FileRequirementExtractorResult pFileRequirementExtractionResult) {
        if (pFileRequirementExtractionResult != null) {
            sdFileReqExtractionResultMap.put(pFileRequirementExtractionResult.getInputFile(),
                    pFileRequirementExtractionResult);
        }
    }

    /**
     * Get the specific requirement extraction result for the given input file.
     * 
     * @param pInputFile the input file.
     * @return the associated requirement extraction result, or <tt>null</tt> if
     * there was none for this file.
     */
    public FileRequirementExtractorResult getFileRequirementExtractionResultFor(final File pInputFile) {
        return sdFileReqExtractionResultMap.get(pInputFile);
    }

    /**
     * Collect all the requirements for all SD files.
     * 
     * @return the list of all the requirements.
     */
    public List<Requirement> getAllRequirements() {
        List<Requirement> lAllRequirements = new ArrayList<Requirement>();

        for (Map.Entry<File, FileRequirementExtractorResult> lEntry : sdFileReqExtractionResultMap.entrySet()) {
            lAllRequirements.addAll(lEntry.getValue().getRequirements());
        }

        return lAllRequirements;
    }

    /**
     * Compute how many requirements were found among all the input files.
     * 
     * @return the number of extracted requirements among all the input files.
     */
    public long getRequirementCount() {
        long lReqCount = 0;

        for (Map.Entry<File, FileRequirementExtractorResult> lEntry : sdFileReqExtractionResultMap.entrySet()) {
            lReqCount += lEntry.getValue().getRequirementCount();
        }

        return lReqCount;
    }

    /**
     * Getter of the duplicated requirement list.
     * 
     * @return the duplicatedRequirements
     */
    public List<RequirementDuplicationItem> getDuplicatedRequirements() {
        return duplicatedRequirements;
    }

    /**
     * Setter of the duplicated requirements.
     * 
     * @param pDuplicatedRequirements the duplicatedRequirements to set
     */
    public void setDuplicatedRequirements(final List<RequirementDuplicationItem> pDuplicatedRequirements) {
        if (pDuplicatedRequirements != null) {
            duplicatedRequirements.addAll(pDuplicatedRequirements);
        }
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

}
