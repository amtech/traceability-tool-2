/**
 * 
 */
package org.tools.doc.traceability.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a test set.
 * <p>
 * It is characterized by a name and it contains a series of
 * {@link TestCaseData}.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class TestSet {

    /**
     * The name of the test set.
     */
    private final String testSetName;

    /**
     * The type of the test set.
     */
    private final TestSetType testSetType;

    /**
     * The list of attached {@link TestCaseData}.
     */
    private final List<TestCaseData> testDataList;

    /**
     * Constructor.
     * 
     * @param pTestSetType the type of the test set.
     * @param pTestSetName the name of the test set.
     */
    public TestSet(final TestSetType pTestSetType, final String pTestSetName) {
        testSetType = pTestSetType;
        testSetName = pTestSetName;
        testDataList = new ArrayList<TestCaseData>();
    }

    /**
     * Getter of the testSetType.
     * 
     * @return the testSetType
     */
    public TestSetType getTestSetType() {
        return testSetType;
    }

    /**
     * Getter of the test set name.
     * 
     * @return the testSetName
     */
    public String getTestSetName() {
        return testSetName;
    }

    /**
     * Add a new test data to the set.
     * 
     * @param pTestCaseIdentifier the test case identifier.
     * @param pTestCaseName the test case name.
     * @param pProcedureDescripion the description of the test case procedure
     * description.
     * @param pExpectedResults the description of the test case expected
     * results.
     * @return the added covering test data.
     */
    public TestCaseData addTestData(final String pTestCaseIdentifier, final String pTestCaseName,
            final String pProcedureDescripion, final String pExpectedResults) {

        // Create the new covering test case data
        TestCaseData lCoveringTestCaseData = new TestCaseData(this, pTestCaseIdentifier, pTestCaseName,
                pProcedureDescripion, pExpectedResults);

        // Add it in the set
        testDataList.add(lCoveringTestCaseData);

        // Return the added element
        return lCoveringTestCaseData;
    }

    /**
     * Getter of the test data list.
     * 
     * @return the coveringTestDataList
     */
    public List<TestCaseData> getTestDataList() {
        return testDataList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder lSb = new StringBuilder();
        lSb.append("TestSet name=");
        lSb.append(testSetName);
        lSb.append(" (type ");
        lSb.append(testSetType.getHmiLabel());
        lSb.append(") :");

        for (TestCaseData lCtcd : testDataList) {
            lSb.append("\n\t");
            lSb.append(lCtcd);
        }

        return lSb.toString();
    }

}
