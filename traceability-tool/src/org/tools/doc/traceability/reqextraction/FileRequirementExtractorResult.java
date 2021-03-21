/**
 * 
 */
package org.tools.doc.traceability.reqextraction;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tools.doc.traceability.common.model.Requirement;

/**
 * Represents the requirement extraction result for one specific input file.
 * 
 * @author Yann Leglise
 *
 */
public class FileRequirementExtractorResult {

    /**
     * The input file from which the requirements were extracted.
     */
    private File inputFile;

    /**
     * The list of requirements extracted from the file.
     */
    private final List<Requirement> requirements;

    /**
     * The dictionary listing the requirements that are found more than once.
     */
    private final Map<Requirement, Integer> duplicatedRequirements;

    /**
     * Constructor.
     * 
     * @param pInputFile the input file.
     */
    public FileRequirementExtractorResult(final File pInputFile) {
        inputFile = pInputFile;
        requirements = new ArrayList<Requirement>();
        duplicatedRequirements = new HashMap<Requirement, Integer>();
    }

    /**
     * Add a requirement to the list.
     * 
     * @param pRequirement the requirement to add.
     */
    public void addRequirement(final Requirement pRequirement) {
        // Check whether this requirement has already been added or not
        if (requirements.contains(pRequirement)) {
            // If it was already added, it's a duplicate

            // Add the entry for this requirement if not already present
            if (!duplicatedRequirements.containsKey(pRequirement)) {
                duplicatedRequirements.put(pRequirement, 1);
            }
            // Increment the number of duplicate for this requirement.
            duplicatedRequirements.put(pRequirement, duplicatedRequirements.get(pRequirement) + 1);
        } else {
            // If it was not added yet, add it
            requirements.add(pRequirement);
        }
    }

    /**
     * Getter of the inputFile.
     * 
     * @return the inputFile
     */
    public File getInputFile() {
        return inputFile;
    }

    /**
     * Getter of the requirements.
     * 
     * @return the requirements
     */
    public List<Requirement> getRequirements() {
        return requirements;
    }

    /**
     * Get the number of extracted requirements for this file.
     * 
     * @return the extracted requirement number.
     */
    public int getRequirementCount() {
        return requirements.size();
    }

    /**
     * Getter of the map giving the duplicated requirements found in the file.
     * 
     * @return the duplicatedRequirements
     */
    public Map<Requirement, Integer> getDuplicatedRequirements() {
        return duplicatedRequirements;
    }
}
