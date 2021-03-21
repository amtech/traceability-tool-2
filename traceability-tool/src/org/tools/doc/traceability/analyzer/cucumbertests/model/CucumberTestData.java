/**
 * 
 */
package org.tools.doc.traceability.analyzer.cucumbertests.model;

import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.common.model.Requirement;

/**
 * A class containing the data about cucumber tests.
 * 
 * @author Yann Leglise
 *
 */
public class CucumberTestData {

    /**
     * The associated feature name.
     */
    private final String featureName;

    /**
     * The associated scenario name.
     */
    private final String scenarioName;

    /**
     * The identifier of the part in the scenario.
     */
    private final String scenarioPartIdentifier;

    /**
     * The description of the actions to perform.
     */
    private final String actionDescription;

    /**
     * The expected results description.
     */
    private final String expectedResultDescription;

    /**
     * The list of covered requirements.
     */
    private final List<Requirement> coveredRequirements;

    /**
     * Constructor.
     * 
     * @param pFeatureName The associated feature name.
     * @param pScenarioName The associated scenario name.
     * @param pScenarioPartIdentifier The identifier of the part in the
     * scenario.
     * @param pActionDescription The description of the actions to perform.
     * @param pExpectedResultDescription The expected results description.
     */
    public CucumberTestData(final String pFeatureName, final String pScenarioName,
            final String pScenarioPartIdentifier, final String pActionDescription,
            final String pExpectedResultDescription) {
        featureName = pFeatureName;
        scenarioName = pScenarioName;
        scenarioPartIdentifier = pScenarioPartIdentifier;
        actionDescription = pActionDescription;
        expectedResultDescription = pExpectedResultDescription;
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
     * Gets the list of covered requirements.
     * 
     * @return the list of covered requirements.
     */
    public List<Requirement> getCoveredRequirements() {
        return coveredRequirements;
    }

    /**
     * Getter of the feature name.
     * 
     * @return the featureName
     */
    public String getFeatureName() {
        return featureName;
    }

    /**
     * Getter of the scenario name.
     * 
     * @return the scenarioName
     */
    public String getScenarioPartName() {
        return scenarioName;
    }

    /**
     * Getter of the description of the action.
     * 
     * @return the actionDescription
     */
    public String getActionDescription() {
        return actionDescription;
    }

    /**
     * Getter of the expected result description.
     * 
     * @return the expectedResultDescription
     */
    public String getExpectedResultDescription() {
        return expectedResultDescription;
    }

    /**
     * Getter of the identifier of the part in the scenario.
     * 
     * @return the scenario part identifer.
     */
    public String getScenarioPartIdentifier() {
        return scenarioPartIdentifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cucumber test\n\tfeature:\t\t");
        sb.append(featureName);
        sb.append("\n\tscenario:\t\t");
        sb.append(scenarioName);
        sb.append("\n\tscenario part ID:\t");
        sb.append(scenarioPartIdentifier);
        sb.append("\n\tsteps description:\n\t\t");
        sb.append(actionDescription.replaceAll("\n", "\n\t\t"));
        sb.append("\n\texpected:\n\t\t");
        sb.append(expectedResultDescription.replaceAll("\n", "\n\t\t"));

        if (coveredRequirements.isEmpty()) {
            sb.append("\n\tNo covered reqs");
        } else {
            sb.append("\n\tCovered reqs:\t{");
            boolean lIsFirst = true;
            for (Requirement lReq : coveredRequirements) {
                if (lIsFirst) {
                    lIsFirst = false;
                } else {
                    sb.append("; ");
                }
                sb.append(lReq.toString());
            }
            sb.append("}");
        }

        return sb.toString();
    }

}
