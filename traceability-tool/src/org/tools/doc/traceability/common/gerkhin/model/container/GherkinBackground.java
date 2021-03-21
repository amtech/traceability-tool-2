/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.container;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.common.gerkhin.GherkinConstants;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinContainerWithAdditionalCommentsElement;
import org.tools.doc.traceability.common.gerkhin.model.step.GherkinStepGiven;

/**
 * Class modeling a Gherkin Background.
 * <p>
 * It has only Given steps.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class GherkinBackground extends AbstractGherkinContainerWithAdditionalCommentsElement {

    /**
     * The list of Given steps.
     */
    private final List<GherkinStepGiven> givenSteps;

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pDescription          the description after the Background: keyword
     *                              (<tt>null</tt> if none).
     */
    public GherkinBackground(final int pSourceFileLineNumber, final String pDescription) {
        super(GherkinConstants.BACKGROUND_KEYWORD, pSourceFileLineNumber, pDescription);
        givenSteps = new ArrayList<GherkinStepGiven>();
    }

    /**
     * Getter of the given steps.
     * 
     * @return the givenSteps
     */
    public List<GherkinStepGiven> getGivenSteps() {
        return givenSteps;
    }

    /**
     * Add a Given step.
     * 
     * @param pGivenStep the given step to add.
     */
    public void addGivenStep(final GherkinStepGiven pGivenStep) {
        givenSteps.add(pGivenStep);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printOn(final PrintStream pPrintStream, final int pIndentationLevel) {
        // Add the container description
        printIndented(getContainerLine(), pIndentationLevel, pPrintStream);

        // Add the potential additional descriptions
        printAdditionalDescriptionOn(pPrintStream, pIndentationLevel + 1);

        // Print the Given steps
        for (GherkinStepGiven lGiven : givenSteps) {
            lGiven.printOn(pPrintStream, pIndentationLevel + 1);
        }
    }
}
