package org.tools.doc.traceability.analyzer.cucumbertests.helper;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tools.doc.traceability.common.Constants;
import org.tools.doc.traceability.common.exceptions.InvalidGherkinContentsException;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinStep;
import org.tools.doc.traceability.common.gerkhin.model.general.GherkinStepType;

/**
 * Helper that transforms a list of Gherkin steps in a testing scenario
 * breakdown.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinStepBreakdownManager {

    /**
     * The regular expression to match the Then or And steps that reference SD
     * requirements.
     */
    private final Pattern reqStepPattern;

    /**
     * The regular expression to match the requirements themselves in the Then
     * or And steps that reference SD requirements.
     */
    private final Pattern reqStepReqPattern;

    /**
     * Constructor.
     */
    public GherkinStepBreakdownManager() {
        reqStepPattern = Pattern.compile(Constants.GHERKIN_STEP_REQ_SD_REGEXP);
        reqStepReqPattern = Pattern.compile(Constants.GHERKIN_STEP_REQ_SD_REQ_REGEXP);
    }

    /**
     * Break down the given Gherkin steps in a list of testing scenario parts.
     * 
     * @param pGherkinSteps the Gherkin steps to consider.
     * @return the result of the break down.
     * @throws InvalidGherkinContentsException if an invalid Gherkin contents is
     * found
     */
    public TestingScenarioBreakdown breakDownSteps(final List<AbstractGherkinStep> pGherkinSteps)
            throws InvalidGherkinContentsException {
        TestingScenarioBreakdown lTestingScenarioBreakdown = new TestingScenarioBreakdown();

        List<AbstractGherkinStep> lActionSteps = new ArrayList<AbstractGherkinStep>();
        List<AbstractGherkinStep> lExpectedResultsSteps = new ArrayList<AbstractGherkinStep>();

        boolean lIsInActionPhase = true;
        GherkinStepType lStepType;
        boolean lIsInThenAndSequence = false;
        TestingScenarioPart lTestingScenarioPart;
        int lPartNumber = 1;
        String lPartIdentifier;

        List<String> lCoveredRequirementIdList = new ArrayList<String>();

        for (AbstractGherkinStep lStep : pGherkinSteps) {
            lStepType = lStep.getStepType();
            if (lStepType == GherkinStepType.Then) {
                lIsInThenAndSequence = true;
                // We first need to check if this step refers to SD requirements
                // or not
                Matcher lMatcher = reqStepPattern.matcher(lStep.getStepText());
                if (lMatcher.matches()) {
                    String lRemainingPart = lMatcher.group(1);

                    // Fill the list of covered requirements from this step
                    fillCoveredRequirementsFrom(lRemainingPart, lCoveredRequirementIdList);
                } else {
                    // If not a step referencing SD requirements, add it to the
                    // expected result description
                    lExpectedResultsSteps.add(lStep);
                }

                lIsInActionPhase = false;
            } else if (lStepType == GherkinStepType.And) {
                // This instruction can be part of the Then sequence so we let
                // lIsInThenAndSequence as it is

                // If we are still in a Then sequence, we include this And in it
                if (lIsInThenAndSequence) {
                    // We first need to check if this step refers to SD
                    // requirements
                    // or not
                    Matcher lMatcher = reqStepPattern.matcher(lStep.getStepText());
                    if (lMatcher.matches()) {
                        String lRemainingPart = lMatcher.group(1);

                        // Fill the list of covered requirements from this step
                        fillCoveredRequirementsFrom(lRemainingPart, lCoveredRequirementIdList);
                    } else {
                        // If not a step referencing SD requirements, add it to
                        // the
                        // expected result description
                        lExpectedResultsSteps.add(lStep);
                    }
                    lIsInActionPhase = false;
                } else {
                    // Otherwise add it to the action steps
                    lActionSteps.add(lStep);
                    lIsInActionPhase = true;
                }
            } else {
                // If not a Then or a And, we are not in the Then sequence
                // anymore
                lIsInThenAndSequence = false;

                // We must handle the "And" steps following a Then

                if (!lIsInActionPhase) {
                    // We have finished the actions and the expected results

                    // Create a part
                    lPartIdentifier = MessageFormat.format("#{0,number}", lPartNumber++);
                    lTestingScenarioPart = new TestingScenarioPart(lPartIdentifier);
                    lTestingScenarioPart.addActionSteps(lActionSteps);
                    lTestingScenarioPart.addExpectedSteps(lExpectedResultsSteps);

                    // Add the potential covered requirements
                    for (String lReqId : lCoveredRequirementIdList) {
                        lTestingScenarioPart.addCoveredRequirement(lReqId);
                    }

                    lTestingScenarioBreakdown.addPart(lTestingScenarioPart);

                    // Reinitialize all lists
                    lActionSteps.clear();
                    lExpectedResultsSteps.clear();
                    lCoveredRequirementIdList.clear();
                }
                lIsInActionPhase = true;

                lActionSteps.add(lStep);
            }
        }

        // Test if the action step contains an unexpected step
        for (AbstractGherkinStep lStep : lActionSteps) {
            Matcher lMatcher = reqStepPattern.matcher(lStep.getStepText());
            if (lMatcher.matches()) {
                throw new InvalidGherkinContentsException("Found a " + lStep.getStepType()
                        + " step corresponding to a reference to a covered requirement at line "
                        + lStep.getSourceFileLineNumber() + " that is not located after a "
                        + GherkinStepType.Then.toString() + ".");
            }
        }

        // Create a last part if one of the lists is not empty
        if (!lActionSteps.isEmpty() || !lExpectedResultsSteps.isEmpty()) {
            // Create a part
            lPartIdentifier = MessageFormat.format("#{0,number}", lPartNumber);
            lTestingScenarioPart = new TestingScenarioPart(lPartIdentifier);
            lTestingScenarioPart.addActionSteps(lActionSteps);
            lTestingScenarioPart.addExpectedSteps(lExpectedResultsSteps);

            // Add the potential covered requirements
            for (String lReqId : lCoveredRequirementIdList) {
                lTestingScenarioPart.addCoveredRequirement(lReqId);
            }

            lTestingScenarioBreakdown.addPart(lTestingScenarioPart);
        }

        return lTestingScenarioBreakdown;
    }

    /**
     * Extract the list of covered requirements from the provided list of
     * covered requirements representation.
     * 
     * @param pCoveredReqs the part after the "Reference SD" part.
     * @param pCoveredRequirementIdList the target list to add covered
     * requirements.
     */
    private void fillCoveredRequirementsFrom(final String pCoveredReqs, final List<String> pCoveredRequirementIdList) {
        boolean lContinueSearching = true;

        // Remove any double quote that may be surrounding the list
        // of requirements
        String lRemainingPart = pCoveredReqs.replace("\"", "");

        while (lContinueSearching) {
            if (lRemainingPart != null) {
                Matcher lReqMatcher = reqStepReqPattern.matcher(lRemainingPart);
                if (lReqMatcher.matches()) {
                    // Add the requirement located in group 1
                    pCoveredRequirementIdList.add(lReqMatcher.group(1));
                    // Update the remaining string in group 3
                    lRemainingPart = lReqMatcher.group(3);
                } else {
                    lContinueSearching = false;
                }
            } else {
                lContinueSearching = false;
            }
        }
    }
}
