/**
 * 
 */
package org.tools.doc.traceability.analyzer.unittests.java.model;

import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.common.model.Requirement;

/**
 * Models a JUnit method and the traceability information found in its javadoc.
 * 
 * @author Yann Leglise
 *
 */
public class JUnitMethodData {

    /**
     * The JUNit method name.
     */
    private final String methodName;

    /**
     * The JUnit test description.
     */
    public String testDescription;

    /**
     * The test identifier.
     */
    private String testIdentifier;

    /**
     * The expected result.
     */
    private String expectedResult;

    /**
     * The list of covered requirements.
     */
    private List<Requirement> coveredRequirements;

    /**
     * Constructor.
     * 
     * @param pMethodName the JUnint method name.
     */
    public JUnitMethodData(final String pMethodName) {
        methodName = pMethodName;
        testDescription = null;
        testIdentifier = null;
        expectedResult = null;
        coveredRequirements = new ArrayList<Requirement>();
    }

    /**
     * Checks whether this instance is valid or not.
     * 
     * @return <tt>true</tt> if valid, <tt>false</tt> if not.
     */
    public boolean isValid() {
        boolean lIsValid = true;

        if (methodName == null) {
            lIsValid = false;
        } else {
            if (methodName.trim().isEmpty()) {
                lIsValid = false;
            } else {
                if (testDescription == null) {
                    lIsValid = false;
                } else {
                    if (testIdentifier == null) {
                        lIsValid = false;
                    } else {
                        if (expectedResult == null) {
                            lIsValid = false;
                        }
                    }
                }
            }
        }

        return lIsValid;
    }

    /**
     * Getter of the test identifier.
     * 
     * @return the testIdentifier
     */
    public String getTestIdentifier() {
        return testIdentifier;
    }

    /**
     * Setter of the test identifier.
     * 
     * @param pTestIdentifier the testIdentifier to set
     */
    public void setTestIdentifier(final String pTestIdentifier) {
        testIdentifier = pTestIdentifier;
    }

    /**
     * Getter of the test description.
     * 
     * @return the testDescription
     */
    public String getTestDescription() {
        return testDescription;
    }

    /**
     * Setter of the test description.
     * 
     * @param pTestDescription the testDescription to set
     */
    public void setTestDescription(final String pTestDescription) {
        testDescription = pTestDescription;
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
     * Setter of the expected result description.
     * 
     * @param pExpectedResult the expectedResult to set
     */
    public void setExpectedResult(final String pExpectedResult) {
        expectedResult = pExpectedResult;
    }

    /**
     * Getter of the JUNit method name.
     * 
     * @return the methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Getter of the covered requirement list.
     * 
     * @return the coveredRequirements
     */
    public List<Requirement> getCoveredRequirements() {
        return coveredRequirements;
    }

    /**
     * Add a covered requirement.
     * 
     * @param lCoveredRequirement the covered requirement to add.
     */
    public void addCoveredRequirement(final Requirement lCoveredRequirement) {
        coveredRequirements.add(lCoveredRequirement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder lSb = new StringBuilder();

        lSb.append(methodName);
        lSb.append("\n\tTest identifier : ");
        lSb.append(testIdentifier);
        lSb.append("\n\tExpected result : ");
        lSb.append(expectedResult);
        if (coveredRequirements.isEmpty()) {
            lSb.append("\n\tNo covered requirement");
        } else {
            lSb.append("\n\tCovered requirements (");
            lSb.append(coveredRequirements.size());
            lSb.append(") : ");
            for (Requirement lReq : coveredRequirements) {
                lSb.append(" ");
                lSb.append(lReq);
            }
        }
        lSb.append("\n\tDescription : ");
        lSb.append(testDescription);
        
        return lSb.toString();
    }
}
