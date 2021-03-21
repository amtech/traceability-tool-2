/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.container;

import org.tools.doc.traceability.common.gerkhin.GherkinConstants;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinScenarioOutlineOrTemplateElement;

/**
 * Class models a Gherking Scenario Template: element.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinScenarioTemplate extends AbstractGherkinScenarioOutlineOrTemplateElement {

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pDescription          the description after the Scenario Template:
     *                              keyword.
     */
    public GherkinScenarioTemplate(final int pSourceFileLineNumber, final String pDescription) {
        super(GherkinConstants.SCENARIO_TEMPLATE_KEYWORD, pSourceFileLineNumber, pDescription);
    }

}
