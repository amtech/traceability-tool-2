package org.tools.doc.traceability.analyzer.cucumbertests.helper;

import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinStep;
import org.tools.doc.traceability.common.model.Requirement;

/**
 * Models a part of a testing scenario.
 * 
 * <p>
 * It contains steps (Given, And, But, *, When) corresponding to the action to
 * take, and the Then steps corresponding to the expected results.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class TestingScenarioPart {

    /**
     * The identifier of the part.
     */
    private final String partIdentifier;

    /**
     * The list of steps corresponding to the actions to perform in the testing
     * scenario part.
     */
    private final List<AbstractGherkinStep> actionSteps;

    /**
     * The list of steps corresponding to the expected results in the testing
     * scenario part.
     */
    private final List<AbstractGherkinStep> expectedResultSteps;

    /**
     * The list of covered requirements.
     */
    private final List<Requirement> coveredRequirements;

    /**
     * Constructor.
     * 
     * @param pPartIdentifier the identifier of the part.
     */
    public TestingScenarioPart(final String pPartIdentifier) {
        partIdentifier = pPartIdentifier;
        actionSteps = new ArrayList<AbstractGherkinStep>();
        expectedResultSteps = new ArrayList<AbstractGherkinStep>();
        coveredRequirements = new ArrayList<Requirement>();
    }

    /**
     * Add the step to the action step list.
     * 
     * @param pActionStep the action step to add.
     */
    public void addActionStep(final AbstractGherkinStep pActionStep) {
        actionSteps.add(pActionStep);
    }

    /**
     * Add the given steps to the action step list.
     * 
     * @param pActionSteps the action steps to add.
     */
    public void addActionSteps(final List<AbstractGherkinStep> pActionSteps) {
        actionSteps.addAll(pActionSteps);
    }

    /**
     * Add the step to the expected result step list.
     * 
     * @param pExpectedResultStep the action step to add.
     */
    public void addExpectedStep(final AbstractGherkinStep pExpectedResultStep) {
        expectedResultSteps.add(pExpectedResultStep);
    }

    /**
     * Add the steps to the expected result step list.
     * 
     * @param pExpectedResultSteps the action steps to add.
     */
    public void addExpectedSteps(final List<AbstractGherkinStep> pExpectedResultSteps) {
        expectedResultSteps.addAll(pExpectedResultSteps);
    }

    /**
     * Getter of the part identifier.
     * 
     * @return the part identifier.
     */
    public String getPartIdentifier() {
        return partIdentifier;
    }

    /**
     * Getter of the action steps.
     * 
     * @return the action steps.
     */
    public List<AbstractGherkinStep> getActionSteps() {
        return actionSteps;
    }

    /**
     * Getter of the expected result steps.
     * 
     * @return the expected result steps.
     */
    public List<AbstractGherkinStep> getExpectedResultSteps() {
        return expectedResultSteps;
    }

    /**
     * Getter of the covered requirement list.
     * 
     * @return the list of covered requirements.
     */
    public List<Requirement> getCoveredRequirements() {
        return coveredRequirements;
    }

    /**
     * Add a covered requirement.
     * 
     * @param pReqId the requirement identifier.
     */
    public void addCoveredRequirement(final String pReqId) {
        Requirement lRequirement = new Requirement(pReqId);
        coveredRequirements.add(lRequirement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder lSb = new StringBuilder();
        lSb.append(partIdentifier);
        lSb.append("\n\t");
        if (actionSteps.isEmpty()) {
            lSb.append("No action step.");
        } else {
            lSb.append("Action steps:");
            for (AbstractGherkinStep lActionStep : actionSteps) {
                lSb.append("\n\t\t");
                lSb.append(lActionStep.getStepLine());
            }
        }
        lSb.append("\n\t");
        if (expectedResultSteps.isEmpty()) {
            lSb.append("No expected result step.");
        } else {
            lSb.append("Expected result steps:");
            for (AbstractGherkinStep lExpectedResultStep : expectedResultSteps) {
                lSb.append("\n\t\t");
                lSb.append(lExpectedResultStep.getStepLine());
            }
        }

        lSb.append("\n\t");
        if (coveredRequirements.isEmpty()) {
            lSb.append("No covered requirements.");
        } else {
            lSb.append("Covered requirements : ");
            for (Requirement lRequirement : coveredRequirements) {
                lSb.append("\n\t\t");
                lSb.append(lRequirement.toString());
            }
        }

        return lSb.toString();
    }
}
