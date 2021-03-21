/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.container;

import org.tools.doc.traceability.common.gerkhin.GherkinConstants;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinExampleOrScenarioElement;

/**
 * A Scenario container.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinScenario extends AbstractGherkinExampleOrScenarioElement {

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pDescription          the description after the Scenario: keyword.
     */
    public GherkinScenario(final int pSourceFileLineNumber, final String pDescription) {
        super(GherkinConstants.SCENARIO_KEYWORD, pSourceFileLineNumber, pDescription);
    }
}
