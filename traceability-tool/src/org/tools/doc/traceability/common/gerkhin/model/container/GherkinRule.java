/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.container;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.common.gerkhin.GherkinConstants;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinContainerWithAdditionalCommentsElement;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinExampleOrScenarioElement;

/**
 * Class modeling a Gherking Rule: container.
 * <p>
 * It may have a Background:container, and a series of Example/Scenario.
 * </p>
 * 
 * <p>
 * As per documentation (https://www.bddtesting.com/gherkin-syntaxreference/) a
 * <b>Rule</b> cannot contain a <b>Background</b>.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class GherkinRule extends AbstractGherkinContainerWithAdditionalCommentsElement {

    /**
     * The list of Example/Scenario sub-elements.
     */
    private final List<AbstractGherkinExampleOrScenarioElement> exampleOrScenarioList;

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file
     * this line corresponds to (first is 1).
     * @param pDescription the description of the rule, after the Rule: keyword.
     */
    public GherkinRule(final int pSourceFileLineNumber, final String pDescription) {
        super(GherkinConstants.RULE_KEYWORD, pSourceFileLineNumber, pDescription);
        exampleOrScenarioList = new ArrayList<AbstractGherkinExampleOrScenarioElement>();
    }

    /**
     * Getter of the example or scenario sub-elements List.
     * 
     * @return the exampleOrScenarioList
     */
    public List<AbstractGherkinExampleOrScenarioElement> getExampleOrScenarioList() {
        return exampleOrScenarioList;
    }

    /**
     * Add an Example: sub-element.
     * 
     * @param pGherkinExample the Example: sub-element to add.
     */
    public void addExample(final GherkinExample pGherkinExample) {
        exampleOrScenarioList.add(pGherkinExample);
    }

    /**
     * Add an Scenario: sub-element.
     * 
     * @param pGherkinScenario the Scenario: sub-element to add.
     */
    public void addScenario(final GherkinScenario pGherkinScenario) {
        exampleOrScenarioList.add(pGherkinScenario);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printOn(final PrintStream pPrintStream, final int pIndentationLevel) {
        // Print the container description
        printIndented(getContainerLine(), pIndentationLevel, pPrintStream);

        // Print the potential additional description
        printAdditionalDescriptionOn(pPrintStream, pIndentationLevel + 1);

        // Add the potential associated comment lines
        printComments(pPrintStream, pIndentationLevel + 1);

        // Print the Example or Scenario elements
        for (AbstractGherkinExampleOrScenarioElement lExampleOrScenario : exampleOrScenarioList) {
            lExampleOrScenario.printOn(pPrintStream, pIndentationLevel + 1);
        }
    }
}
