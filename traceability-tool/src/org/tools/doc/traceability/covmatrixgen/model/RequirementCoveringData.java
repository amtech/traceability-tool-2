/**
 * 
 */
package org.tools.doc.traceability.covmatrixgen.model;

import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.common.model.Requirement;
import org.tools.doc.traceability.common.model.TestSetType;

/**
 * Implementation of {@link IRequirementCoveringData}.
 * 
 * @author Yann Leglise
 *
 */
public class RequirementCoveringData implements IRequirementCoveringData {

    /**
     * The type of the test.
     */
    private final TestSetType testType;

    /**
     * The test case identifier.
     */
    private final String testCaseIdentifier;

    /**
     * The test case name.
     */
    private final String testCaseName;

    /**
     * The details about the origin of the test.
     */
    private final String originDetails;

    /**
     * The list of potentially covered requirements.
     */
    private final List<Requirement> coveredRequirementList;

    /**
     * Constructor.
     * 
     * @param pTestType the type of the test.
     * @param pTestCaseIdentifier the test case identifier.
     * @param pTestCaseName the test case name.
     * @param pOriginDetails the details about the origin of the test.
     */
    public RequirementCoveringData(final TestSetType pTestType, final String pTestCaseIdentifier,
            final String pTestCaseName, final String pOriginDetails) {
        testType = pTestType;
        testCaseIdentifier = pTestCaseIdentifier;
        testCaseName = pTestCaseName;
        originDetails = pOriginDetails;
        coveredRequirementList = new ArrayList<Requirement>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestSetType getType() {
        return testType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTestCaseIdentifier() {
        return testCaseIdentifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTestCaseName() {
        return testCaseName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOriginDetails() {
        return originDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Requirement> getCoveredRequirementList() {
        return coveredRequirementList;
    }

    /**
     * Add the covered requirements.
     * 
     * @param pCoveredRequirements the list of covered requirements.
     */
    public void addCoveredRequirements(final List<Requirement> pCoveredRequirements) {
        if (pCoveredRequirements != null) {
            coveredRequirementList.addAll(pCoveredRequirements);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder lSb = new StringBuilder();

        lSb.append(testType.getHmiLabel());
        lSb.append(" Tests case [id=");
        lSb.append(testCaseIdentifier);
        lSb.append(", name=");
        lSb.append(testCaseName);
        lSb.append("] origin details [");
        lSb.append(originDetails);
        lSb.append("] covered reqs [");

        for (Requirement lCoveredReq : coveredRequirementList) {
            lSb.append(lCoveredReq);
            lSb.append(" ");
        }

        lSb.append("]");

        return lSb.toString();
    }
}
