/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.common.gerkhin.model.container.GherkinExamples;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinScenarios;

/**
 * Common superclass for Scenario Outline: and Scenario Template: elements.
 * 
 * <p>
 * They contain a list of steps, plus a Examples: or Scenarios: sub-elements.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractGherkinScenarioOutlineOrTemplateElement extends
        AbstractGherkinContainerWithAdditionalCommentsElement {

    /**
     * The list of associated steps.
     */
    private final List<AbstractGherkinStep> steps;

    /**
     * The Examples: or Scenarios: element.
     */
    private AbstractGherkinExamplesOrScenariosElement examplesOrScenariosElement;

    /**
     * Constructor.
     * 
     * @param pContainerKeyword The keyword associated with the container
     * (without the semicolon).
     * @param pSourceFileLineNumber the number of the line in the source file
     * this line corresponds to (first is 1).
     * @param pContainerDescription the description after the container keyword.
     */
    public AbstractGherkinScenarioOutlineOrTemplateElement(final String pContainerKeyword,
            final int pSourceFileLineNumber, final String pContainerDescription) {
        super(pContainerKeyword, pSourceFileLineNumber, pContainerDescription);
        steps = new ArrayList<AbstractGherkinStep>();
        examplesOrScenariosElement = null;
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
     * Getter of the Examples: or Scenarios: element.
     * 
     * @return the examplesOrScenariosElement
     */
    public AbstractGherkinExamplesOrScenariosElement getExamplesOrScenariosElement() {
        return examplesOrScenariosElement;
    }

    /**
     * Set the Examples: element attached to this instance..
     * 
     * @param pExamplesElement the Examples: element to set.
     */
    public void setExamplesElement(final GherkinExamples pExamplesElement) {
        examplesOrScenariosElement = pExamplesElement;
    }

    /**
     * Set the Scenarios: element attached to this instance..
     * 
     * @param pScenariosElement the Scenarios: element to set.
     */
    public void setScenariosElement(final GherkinScenarios pScenariosElement) {
        examplesOrScenariosElement = pScenariosElement;
    }

    /**
     * Indicates whether an Examples or a Scenarios is already attached to this
     * instance.
     * 
     * @return <tt>true</tt> if an Examples or a Scenarios is already attached,
     * <tt>false</tt> otherwise.
     */
    public boolean alreadyHasAnExamplesOrScenariosSet() {
        return (examplesOrScenariosElement != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printOn(final PrintStream pPrintStream, final int pIndentationLevel) {
        // Print the container description
        printIndented(getContainerLine(), pIndentationLevel, pPrintStream);

        // Print the potential additional comments
        printAdditionalDescriptionOn(pPrintStream, pIndentationLevel + 1);

        // Add the potential associated comment lines
        printComments(pPrintStream, pIndentationLevel + 1);

        // Print all steps
        for (AbstractGherkinStep lStep : steps) {
            lStep.printOn(pPrintStream, pIndentationLevel + 1);
        }

        // Print Examples: or Scenarios: element
        if (examplesOrScenariosElement != null) {
            examplesOrScenariosElement.printOn(pPrintStream, pIndentationLevel + 1);
        }
    }

}
