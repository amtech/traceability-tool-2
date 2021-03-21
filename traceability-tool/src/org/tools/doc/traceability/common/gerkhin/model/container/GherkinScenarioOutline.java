/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.container;

import org.tools.doc.traceability.common.gerkhin.GherkinConstants;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinScenarioOutlineOrTemplateElement;

/**
 * Class modeling a Gherkin Scenario Outline: container.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinScenarioOutline extends AbstractGherkinScenarioOutlineOrTemplateElement {

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pDescription          the description of element after the Scenario
     *                              Outline: keyword.
     */
    public GherkinScenarioOutline(final int pSourceFileLineNumber, final String pDescription) {
        super(GherkinConstants.SCENARIO_OUTLINE_KEYWORD, pSourceFileLineNumber, pDescription);
    }

}
