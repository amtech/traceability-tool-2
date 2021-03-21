/**
 * 
 */
package org.tools.doc.traceability.reqextraction.helper;

import org.tools.doc.traceability.common.model.Requirement;

/**
 * Models a requirement that is present more than once.
 * 
 * @author Yann Leglise
 *
 */
public class RequirementDuplicationItem {

    /**
     * The duplicated requirement.
     */
    private final Requirement requirement;

    /**
     * Description of how many time the requirement occurs and in which files.
     * <p>
     * Example : "1 time in [SD file 1] and 1 time in [SD file 2]", or
     * "2 times in [SD file 1]".
     * </p>
     */
    private final String duplicationDescription;

    /**
     * Constructor.
     * 
     * @param pRequirement the duplicated requirement.
     * @param pDuplicationDescription the description of how many time the
     * requirement occurs and in which files.
     */
    public RequirementDuplicationItem(final Requirement pRequirement, final String pDuplicationDescription) {
        requirement = pRequirement;
        duplicationDescription = pDuplicationDescription;
    }

    /**
     * Getter of the duplicated requirement.
     * 
     * @return the requirement
     */
    public Requirement getRequirement() {
        return requirement;
    }

    /**
     * Getter of the duplication description.
     * 
     * @return the duplicationDescription
     */
    public String getDuplicationDescription() {
        return duplicationDescription;
    }
}
