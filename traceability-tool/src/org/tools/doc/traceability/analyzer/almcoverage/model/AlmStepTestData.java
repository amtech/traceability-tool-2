/**
 * 
 */
package org.tools.doc.traceability.analyzer.almcoverage.model;

/**
 * Models a step from HP ALM test case.
 * 
 * @author Yann Leglise
 *
 */
public class AlmStepTestData {

    /**
     * The name of the test suite.
     */
    private final String testSuite;

    /**
     * The number of the test case.
     */
    private final int testCaseNumber;

    /**
     * The title of the test case.
     */
    private final String testCaseTitle;

    /**
     * The name of the step.
     */
    private final String stepName;

    /**
     * The action to play in the step.
     */
    private final String action;

    /**
     * The expected result for the step to be valid.
     */
    private final String expectedResult;

    /**
     * Constructor.
     * 
     * @param pTestSuite the name of the test suite.
     * @param pTestCaseNumber the number of the test case.
     * @param pTestCaseTitle the title of the test case.
     * @param pStepName the name of the step.
     * @param pAction the action to play in the step.
     * @param pExpectedResult the expected result for the step to be valid.
     */
    public AlmStepTestData(final String pTestSuite, final int pTestCaseNumber, final String pTestCaseTitle,
            final String pStepName, final String pAction, final String pExpectedResult) {
        super();
        testSuite = pTestSuite;
        testCaseNumber = pTestCaseNumber;
        testCaseTitle = pTestCaseTitle;
        stepName = pStepName;
        action = pAction;
        expectedResult = pExpectedResult;
    }

    /**
     * Getter of the the name of the test suite.
     * 
     * @return the testSuite
     */
    public String getTestSuite() {
        return testSuite;
    }

    /**
     * Getter of the number of the test case.
     * 
     * @return the testCaseNumber
     */
    public int getTestCaseNumber() {
        return testCaseNumber;
    }

    /**
     * Getter of the title of the test case.
     * 
     * @return the testCaseTitle
     */
    public String getTestCaseTitle() {
        return testCaseTitle;
    }

    /**
     * Getter of the name of the step.
     * 
     * @return the stepName
     */
    public String getStepName() {
        return stepName;
    }

    /**
     * Getter of the action to play in the step.
     * 
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * Getter of the expected result for the step to be valid.
     * 
     * @return the expectedResult
     */
    public String getExpectedResult() {
        return expectedResult;
    }

    /**
     * Check whether the instance is defined, i.e. at least one field is not
     * null and empty.
     * 
     * @return <tt>true</tt> if at least one field is defined, <tt>false</tt>
     * otherwise.
     */
    public boolean isDefined() {
        boolean lIsDefined = false;

        if (testSuite != null && !testSuite.trim().isEmpty()) {
            lIsDefined = true;
        } else if (testCaseTitle != null && !testCaseTitle.trim().isEmpty()) {
            lIsDefined = true;
        } else if (stepName != null && !stepName.trim().isEmpty()) {
            lIsDefined = true;
        } else if (action != null && !action.trim().isEmpty()) {
            lIsDefined = true;
        } else if (expectedResult != null && !expectedResult.trim().isEmpty()) {

            lIsDefined = true;
        }

        return lIsDefined;
    }

}
