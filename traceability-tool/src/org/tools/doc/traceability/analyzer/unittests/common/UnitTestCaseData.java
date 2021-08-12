/**
 * 
 */
package org.tools.doc.traceability.analyzer.unittests.common;

import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.common.Constants;
import org.tools.doc.traceability.common.model.Requirement;

/**
 * A class modeling a unit test case data.
 * <p>
 * It defines:
 * <ul>
 * <li>The <b>identifier</b> of the test case (shall be unique, and is used to
 * sort).
 * <li>The <b>name</b> which is the testing method name.
 * <li>The <b>description</b> which describes the test case (initial conditions,
 * inputs, actions).
 * <li>The <b>expected result</b> which describes what is expected after the
 * test case is played.
 * <li>The <b>list of covered requirements</b> if this test case covers some
 * requirements.
 * </ul>
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class UnitTestCaseData implements Comparable<UnitTestCaseData> {

    /**
     * The test case identifier.
     */
    private final String testCaseIdentifier;

    /**
     * The test case name (i.e. method name).
     */
    private final String testCaseName;

    /**
     * The test action description (initial conditions, inputs, actions
     * performed).
     */
    private String actionDescription;

    /**
     * The description of the the expected result.
     */
    private final String expectedResult;

    /**
     * The list of covered requirements.
     */
    private List<Requirement> coveredRequirements;

    /**
     * Constructor.
     * 
     * @param pTestCaseIdentifier the test case identifier.
     * @param pTestCaseName the test case name (i.e. the name of the method).
     * @param pActionDescription the test action description.
     * @param pExpectedResult the expected result description.
     */
    public UnitTestCaseData(final String pTestCaseIdentifier, final String pTestCaseName,
            final String pActionDescription, final String pExpectedResult) {
        testCaseIdentifier = pTestCaseIdentifier;
        testCaseName = pTestCaseName;
        actionDescription = pActionDescription;
        expectedResult = pExpectedResult;
        coveredRequirements = new ArrayList<Requirement>();
    }

    /**
     * Add the given covered requirement.
     * 
     * @param pRequirement the requirement to add.
     * @return true if the requirement was added, false if it was already
     * present.
     */
    public boolean addCoveredRequirement(final Requirement pRequirement) {
        boolean lReqAdded = false;
        if (!coveredRequirements.contains(pRequirement)) {
            coveredRequirements.add(pRequirement);
            lReqAdded = true;
        }
        return lReqAdded;
    }

    /**
     * Gets the test case identifier.
     * 
     * @return the test identifier.
     */
    public String getTestCaseIdentifier() {
        return testCaseIdentifier;
    }

    /**
     * Gets the test case name (i.e. the function name).
     * 
     * @return the test case name.
     */
    public String getTestCaseName() {
        return testCaseName;
    }

    /**
     * Gets the test action description.
     * 
     * @return the test description.
     */
    public String getActionDescription() {
        return actionDescription;
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
     * Gets the list of covered requirements.
     * 
     * @return the list of covered requirements.
     */
    public List<Requirement> getCoveredRequirements() {
        return coveredRequirements;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder lSb = new StringBuilder("");
        printUnitTestCaseDataOn(lSb, 0);
        return lSb.toString();
    }

    /**
     * Writes a description of this instance on the given string builder.
     * 
     * @param pSb the string builder where to write.
     * @param leadingIndentLevel the leading indentation level.
     */
    public void printUnitTestCaseDataOn(final StringBuilder pSb, final int leadingIndentLevel) {
        pSb.append('\n');
        for (int i = 0; i < leadingIndentLevel; i++) {
            pSb.append(Constants.LEADING_INDENTATION_TEXT);
        }
        pSb.append("UnitTestData\n");
        for (int i = 0; i < leadingIndentLevel+1; i++) {
            pSb.append(Constants.LEADING_INDENTATION_TEXT);
        }
        pSb.append("ID=");
        pSb.append(testCaseIdentifier);
        pSb.append('\n');
        for (int i = 0; i < leadingIndentLevel+1; i++) {
            pSb.append(Constants.LEADING_INDENTATION_TEXT);
        }
        pSb.append("name=");
        pSb.append(testCaseName);
        pSb.append('\n');
        for (int i = 0; i < leadingIndentLevel+1; i++) {
            pSb.append(Constants.LEADING_INDENTATION_TEXT);
        }
        pSb.append("covered requirements=");
        boolean lIsFirst = true;
        for (Requirement lReq : coveredRequirements) {
            if (lIsFirst) {
                lIsFirst = false;
            } else {
                pSb.append(", ");
            }
            pSb.append(lReq.toString());
        }
        pSb.append('\n');
        for (int i = 0; i < leadingIndentLevel+1; i++) {
            pSb.append(Constants.LEADING_INDENTATION_TEXT);
        }
        pSb.append("description=");
        pSb.append(actionDescription);
        pSb.append('\n');
        for (int i = 0; i < leadingIndentLevel+1; i++) {
            pSb.append(Constants.LEADING_INDENTATION_TEXT);
        }
        pSb.append("expected results=");
        pSb.append(expectedResult);
        pSb.append('\n');
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final UnitTestCaseData pOtherUtd) {
        // Sort on the identifier
        return testCaseIdentifier.compareTo(pOtherUtd.testCaseIdentifier);
    }
}
