/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

import java.util.ArrayList;
import java.util.List;

/**
 * Class modeling an Gherkin container that has steps.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractGherkinContainerWithStepsElement extends AbstractGherkinContainerElement {

    /**
     * The list of associated steps.
     */
    private final List<AbstractGherkinStep> steps;

    /**
     * Constructor.
     * 
     * @param pContainerKeyword     The keyword associated with the container
     *                              (without the semicolon).
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pContainerDescription the description after the container keyword.
     */
    public AbstractGherkinContainerWithStepsElement(final String pContainerKeyword, final int pSourceFileLineNumber,
            final String pContainerDescription) {
        super(pContainerKeyword, pSourceFileLineNumber, pContainerDescription);

        steps = new ArrayList<AbstractGherkinStep>();
    }

    /**
     * Add a step.
     * 
     * @param pStep the step to add.
     */
    public void addStep(final AbstractGherkinStep pStep) {
        steps.add(pStep);
    }

    /**
     * Getter of the steps.
     * 
     * @return the steps
     */
    public List<AbstractGherkinStep> getSteps() {
        return steps;
    }

}
