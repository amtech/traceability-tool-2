/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class modeling a Gherking container that can have additional comments
 * appended to it and that can contain steps.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractGherkinContainerWithAdditionalCommentsAndStepsElement
        extends AbstractGherkinContainerWithAdditionalCommentsElement {

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
    public AbstractGherkinContainerWithAdditionalCommentsAndStepsElement(final String pContainerKeyword,
            final int pSourceFileLineNumber, final String pContainerDescription) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void printOn(final PrintStream pPrintStream, final int pIndentationLevel) {
        // Add the container line
        printIndented(getContainerLine(), pIndentationLevel, pPrintStream);

        // Add potential additional description lines
        printAdditionalDescriptionOn(pPrintStream, pIndentationLevel + 1);

        // Add the potential associated comment lines
        printComments(pPrintStream, pIndentationLevel + 1);

        // Print all steps
        for (AbstractGherkinStep lStep : steps) {
            lStep.printOn(pPrintStream, pIndentationLevel + 1);
        }
    }
}
