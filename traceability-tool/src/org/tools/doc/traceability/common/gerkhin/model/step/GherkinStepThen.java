/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.step;

import org.tools.doc.traceability.common.gerkhin.GherkinConstants;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinStep;
import org.tools.doc.traceability.common.gerkhin.model.general.GherkinStepType;

/**
 * Class modeling a Gherkin Then step.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinStepThen extends AbstractGherkinStep {

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pStepText             The text of the final element.
     */
    public GherkinStepThen(final int pSourceFileLineNumber, final String pStepText) {
        super(GherkinConstants.THEN_KEYWORD, pSourceFileLineNumber, GherkinStepType.Then, pStepText);
    }
}
