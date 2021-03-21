/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.step;

import org.tools.doc.traceability.common.gerkhin.GherkinConstants;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinStep;
import org.tools.doc.traceability.common.gerkhin.model.general.GherkinStepType;

/**
 * Class modeling a Gherkin When step.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinStepWhen extends AbstractGherkinStep {

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pStepText             The text of the final element.
     */
    public GherkinStepWhen(final int pSourceFileLineNumber, final String pStepText) {
        super(GherkinConstants.WHEN_KEYWORD, pSourceFileLineNumber, GherkinStepType.When, pStepText);
    }
}
