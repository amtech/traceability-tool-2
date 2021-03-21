/**
 * 
 */
package org.tools.doc.traceability.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Models a generic representation of a single test case.
 * <p>
 * It is characterized with:
 * <ul>
 * <li>A test type (Manual test, Cucumber test, C# unit test, java unit test).
 * <li>The covering test set this test case belongs to.
 * <li>The test case identifier.
 * <li>The test case name.
 * <li>The description of the test procedure.
 * <li>The description of the expected results.
 * <li>The list of potentially coverend requirements.
 * </ul>
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class TestCaseData {

    /**
     * The set this test case belongs to.
     */
    private final TestSet parentSet;

    /**
     * The test case identifier.
     */
    private final String testCaseIdentifier;

    /**
     * The test case name.
     */
    private final String testCaseName;

    /**
     * The description of the test case procedure description.
     */
    private final String procedureDescription;

    /**
     * The description of the test case expected results.
     */
    private final String expectedResults;

    /**
     * The list of covered requirements.
     */
    private final List<Requirement> coveredRequirementList;

    /**
     * Constructor.
     * 
     * @param pParentSet the set this test case belongs to.
     * @param pTestCaseIdentifier the test case identifier.
     * @param pTestCaseName the test case name.
     * @param pProcedureDescripion the description of the test case procedure
     * description.
     * @param pExpectedResults the description of the test case expected
     * results.
     */
    public TestCaseData(final TestSet pParentSet, final String pTestCaseIdentifier, final String pTestCaseName,
            final String pProcedureDescripion, final String pExpectedResults) {
        super();
        parentSet = pParentSet;
        testCaseIdentifier = pTestCaseIdentifier;
        testCaseName = pTestCaseName;
        procedureDescription = pProcedureDescripion;
        expectedResults = pExpectedResults;

        coveredRequirementList = new ArrayList<Requirement>();
    }

    /**
     * Getter of the test set name.
     * 
     * @return the test set name.
     */
    public String getTestSetName() {
        return parentSet.getTestSetName();
    }

    /**
     * Getter of the test set type.
     * 
     * @return the test set type.
     */
    public TestSetType getTestSetType() {
        return parentSet.getTestSetType();
    }

    /**
     * Getter of the test case identifier.
     * 
     * @return the testCaseIdentifier
     */
    public String getTestCaseIdentifier() {
        return testCaseIdentifier;
    }

    /**
     * Getter of the test case name.
     * 
     * @return the testCaseName
     */
    public String getTestCaseName() {
        return testCaseName;
    }

    /**
     * Getter of the procedure description.
     * 
     * @return the procedureDescription
     */
    public String getProcedureDescription() {
        return procedureDescription;
    }

    /**
     * Getter of the expected results description.
     * 
     * @return the expectedResults
     */
    public String getExpectedResults() {
        return expectedResults;
    }

    /**
     * Getter of the covered requirement list.
     * 
     * @return the coveredRequirementList
     */
    public List<Requirement> getCoveredRequirementList() {
        return coveredRequirementList;
    }

    /**
     * Add a new covered requirement.
     * 
     * @param pRequirement the covered requirement to add.
     */
    public void addCoveredRequirement(final Requirement pRequirement) {
        coveredRequirementList.add(pRequirement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        StringBuilder lSb = new StringBuilder("TestCaseData [");
        lSb.append(" ID=");
        lSb.append(testCaseIdentifier);
        lSb.append(", name=");
        lSb.append(testCaseName);
        lSb.append(", covered requirements=");
        boolean lIsFirst = true;
        for (Requirement lReq : coveredRequirementList) {
            if (lIsFirst) {
                lIsFirst = false;
            } else {
                lSb.append(", ");
            }
            lSb.append(lReq.toString());
        }
        lSb.append(", procedure=");
        lSb.append(procedureDescription);
        lSb.append(", expected results=");
        lSb.append(expectedResults);
        lSb.append("]");
        return lSb.toString();
    }

}
