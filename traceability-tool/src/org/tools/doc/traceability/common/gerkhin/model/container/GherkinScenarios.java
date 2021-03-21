/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.container;

import org.tools.doc.traceability.common.gerkhin.GherkinConstants;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinExamplesOrScenariosElement;

/**
 * Class modeling an Scenarios: Gherkin element.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinScenarios extends AbstractGherkinExamplesOrScenariosElement {

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pDescription the description after the Scenarios: keyword.
     */
    public GherkinScenarios(final int pSourceFileLineNumber, final String pDescription) {
        super(GherkinConstants.SCENARIOS_KEYWORD, pSourceFileLineNumber, pDescription);
    }
}
