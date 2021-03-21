/**
 * 
 */
package org.tools.doc.traceability.analyzer.almcoverage.model;

import java.util.List;

/**
 * Models the contents of a row in a sheet of a workbook produced by HP ALM for
 * extracting test data.
 * 
 * @author Yann Leglise
 *
 */
public class AlmWorkbookSheetRowContents {

    /**
     * The value of the "Test Suite" column.
     */
    private String testSuite;

    /**
     * The value of the "TC No" column.
     */
    private String testNumber;

    /**
     * The value of the "Test Case Title" column.
     */
    private String testCaseTitle;

    /**
     * The value of the "Step Name" column.
     */
    private String stepName;

    /**
     * The value of the "Action" column.
     */
    private String action;

    /**
     * The value of the "Expected Result" column.
     */
    private String expectedResult;

    /**
     * Constructor.
     * 
     * @param pRowCellContents the list of cell contents read from the row.
     */
    public AlmWorkbookSheetRowContents(final List<String> pRowCellContents) {
        testSuite = "";
        testNumber = "";
        testCaseTitle = "";
        stepName = "";
        action = "";
        expectedResult = "";
        if (pRowCellContents != null) {
            int lCellNum = pRowCellContents.size();
            if (lCellNum > 0) {
                testSuite = pRowCellContents.get(0).trim();
                if (lCellNum > 1) {
                    testNumber = pRowCellContents.get(1).trim();
                    if (lCellNum > 2) {
                        testCaseTitle = pRowCellContents.get(2).trim();
                        if (lCellNum > 3) {
                            stepName = pRowCellContents.get(3).trim();
                            if (lCellNum > 4) {
                                action = pRowCellContents.get(4).trim();
                                if (lCellNum > 5) {
                                    expectedResult = pRowCellContents.get(5).trim();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Getter of the test suite.
     * 
     * @return the testSuite
     */
    public String getTestSuite() {
        return testSuite;
    }

    /**
     * Getter of the test number.
     * 
     * @return the testNumber
     */
    public String getTestNumber() {
        return testNumber;
    }

    /**
     * Getter of the test case title.
     * 
     * @return the testCaseTitle
     */
    public String getTestCaseTitle() {
        return testCaseTitle;
    }

    /**
     * Getter of the step name.
     * 
     * @return the stepName
     */
    public String getStepName() {
        return stepName;
    }

    /**
     * Getter of the action.
     * 
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * Getter of the expected result.
     * 
     * @return the expectedResult
     */
    public String getExpectedResult() {
        return expectedResult;
    }

    /**
     * Checks whether this row correspond to the header.
     * 
     * @return true if it is a header, false otherwise.
     */
    public boolean isHeader() {
        boolean lIsheader = false;

        if (testSuite.compareTo("Test Suite") == 0) {
            if (testNumber.compareTo("TC No") == 0) {
                if (testCaseTitle.compareTo("Test Case Title") == 0) {
                    if (stepName.compareTo("Step Name") == 0) {
                        if (action.compareTo("Action") == 0) {
                            lIsheader = expectedResult.compareTo("Expected Result") == 0;
                        }
                    }
                }
            }
        }

        return lIsheader;
    }

    /**
     * Convert this instance in a {@link AlmStepTestData}.
     * 
     * @return the {@link AlmStepTestData} created from this instance.
     */
    public AlmStepTestData toAlmStepTestData() {

        int lTestCaseNumber = 0;

        try {
            lTestCaseNumber = Integer.parseInt(testNumber);
        } catch (NumberFormatException nfe) {
            lTestCaseNumber = 0;
        }

        AlmStepTestData lAlmStepTestData = new AlmStepTestData(testSuite, lTestCaseNumber, testCaseTitle, stepName,
                action, expectedResult);

        return lAlmStepTestData;
    }
}
