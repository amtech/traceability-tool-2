/**
 * 
 */
package org.tools.doc.traceability.covmatrixgen;

import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.common.executor.AbtsractExecutionResultObject;
import org.tools.doc.traceability.common.model.Requirement;
import org.tools.doc.traceability.covmatrixgen.model.IRequirementCoveringData;
import org.tools.doc.traceability.covmatrixgen.model.RequirementTestCovering;

/**
 * Result for Coverage Matrix Generator.
 * 
 * @author Yann Leglise
 *
 */
public class CoverageMatrixGeneratorResultObject extends AbtsractExecutionResultObject {

    /**
     * The requirement test covering.
     */
    private final RequirementTestCovering requirementTestCovering;

    /**
     * The list of not covered requirements.
     */
    private final List<Requirement> notCoveredRequirementList;

    /**
     * Constructor.
     */
    public CoverageMatrixGeneratorResultObject() {
        requirementTestCovering = new RequirementTestCovering();
        notCoveredRequirementList = new ArrayList<Requirement>();
    }

    /**
     * Adds an association between the given requirement and the covering data.
     * 
     * @param pRequirement the covered requirement.
     * @param lRequirementCoveringData the covering data covering this
     * requirement.
     */
    public void addRequirementCovering(final Requirement pRequirement,
            final IRequirementCoveringData lRequirementCoveringData) {
        requirementTestCovering.addRequirementCovering(pRequirement, lRequirementCoveringData);
    }

    /**
     * Add a not covered requirement.
     * 
     * @param pRequirement the not covered requirement.
     */
    public void addNotCoveredRequirement(final Requirement pRequirement) {
        notCoveredRequirementList.add(pRequirement);
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
     * Getter of the list of not covered requirements.
     * 
     * @return the notCoveredRequirementList
     */
    public List<Requirement> getNotCoveredRequirementList() {
        return notCoveredRequirementList;
    }

}
