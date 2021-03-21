/**
 * 
 */
package org.tools.doc.traceability.reqextraction.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tools.doc.traceability.common.model.Requirement;

/**
 * Structure used to help detecting which requirements are duplicated.
 * 
 * @author Yann Leglise
 *
 */
public class RequirementDuplicationAnalysisData {

    /**
     * A map that associates with a requirement a map associating with each SD
     * file the number of occurrences this requirement has been defined.
     */
    private final Map<Requirement, Map<File, Integer>> requirementFileCountMap;

    /**
     * Constructor.
     */
    public RequirementDuplicationAnalysisData() {
        requirementFileCountMap = new HashMap<Requirement, Map<File, Integer>>();
    }

    /**
     * Add a reference indicating that the given requirement has been defined
     * once in the given SD file.
     * 
     * @param pRequirement the defined requirement.
     * @param pSdFile the SD file in which the requirement was defined.
     */
    public void referenceRequirement(final Requirement pRequirement, final File pSdFile) {
        if ((pRequirement != null) && (pSdFile != null)) {
            Map<File, Integer> lFileCountMap;

            if (requirementFileCountMap.containsKey(pRequirement)) {
                lFileCountMap = requirementFileCountMap.get(pRequirement);
            } else {
                lFileCountMap = new HashMap<File, Integer>();
                requirementFileCountMap.put(pRequirement, lFileCountMap);
            }

            int lCurrentCount = 0;
            if (lFileCountMap.containsKey(pSdFile)) {
                lCurrentCount = lFileCountMap.get(pSdFile);
            }

            lCurrentCount++;

            lFileCountMap.put(pSdFile, lCurrentCount);
        }
    }

    /**
     * Detect if there are duplicated requirements, and return their
     * description.
     * 
     * @return the list of duplicated requirements.
     */
    public List<RequirementDuplicationItem> getDuplicatedRequirements() {
        List<RequirementDuplicationItem> lRequirementDuplicationItemList = new ArrayList<RequirementDuplicationItem>();

        int lNbDefinitionsInFile;
        int lNbReqDefinitionCount;
        RequirementDuplicationItem lRequirementDuplicationItem;

        for (Requirement lRequirement : requirementFileCountMap.keySet()) {
            // Count how many time the requirement is defined
            lNbReqDefinitionCount = 0;

            Map<File, Integer> lFileCountMap = requirementFileCountMap.get(lRequirement);
            for (File lSdFile : lFileCountMap.keySet()) {
                lNbDefinitionsInFile = lFileCountMap.get(lSdFile);
                lNbReqDefinitionCount += lNbDefinitionsInFile;
            }

            if (lNbReqDefinitionCount > 1) {
                // Create a duplication item
                StringBuilder lDuplicationDescriptionSb = new StringBuilder();

                boolean isFirst = true;

                for (File lSdFile : lFileCountMap.keySet()) {
                    lNbDefinitionsInFile = lFileCountMap.get(lSdFile);

                    if (isFirst) {
                        isFirst = false;
                    } else {
                        lDuplicationDescriptionSb.append(" and ");
                    }

                    lDuplicationDescriptionSb.append(lNbDefinitionsInFile);
                    if (lNbDefinitionsInFile > 1) {
                        lDuplicationDescriptionSb.append(" times");
                    } else {
                        lDuplicationDescriptionSb.append(" time");
                    }

                    lDuplicationDescriptionSb.append(" in ");
                    lDuplicationDescriptionSb.append(lSdFile.getName());
                }

                lRequirementDuplicationItem = new RequirementDuplicationItem(lRequirement,
                        lDuplicationDescriptionSb.toString());

                lRequirementDuplicationItemList.add(lRequirementDuplicationItem);
            }
        }

        return lRequirementDuplicationItemList;
    }
}
