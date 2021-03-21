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
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinScenarioOutlineOrTemplateElement;

/**
 * Class modeling a Gherkin feature.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinFeature extends AbstractGherkinContainerWithAdditionalCommentsElement {

    /**
     * The potential background element.
     */
    private GherkinBackground background;

    /**
     * The list of Rule: elements.
     */
    private final List<GherkinRule> rules;

    /**
     * The list of Example: or Scenario: elements.
     */
    private final List<AbstractGherkinExampleOrScenarioElement> exampleOrScenarioElements;

    /**
     * The list of Scenario Outline: or Scenario Template: elements.
     */
    private final List<AbstractGherkinScenarioOutlineOrTemplateElement> scenarioOutlineOrTemplateElements;

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pDescription          the description after the Feature: keyword.
     */
    public GherkinFeature(final int pSourceFileLineNumber, final String pDescription) {
        super(GherkinConstants.FEATURE_KEYWORD, pSourceFileLineNumber, pDescription);
        background = null;
        rules = new ArrayList<GherkinRule>();
        exampleOrScenarioElements = new ArrayList<AbstractGherkinExampleOrScenarioElement>();
        scenarioOutlineOrTemplateElements = new ArrayList<AbstractGherkinScenarioOutlineOrTemplateElement>();
    }

    /**
     * Getter of the potential Background: element.
     * 
     * @return the background (can be <tt>null</tt> if none).
     */
    public GherkinBackground getBackground() {
        return background;
    }

    /**
     * Setter of the Background: element.
     * 
     * @param pBackground the background to set
     */
    public void setBackground(final GherkinBackground pBackground) {
        background = pBackground;
    }

    /**
     * Getter of the Rule: elements.
     * 
     * @return the rules
     */
    public List<GherkinRule> getRules() {
        return rules;
    }

    /**
     * Add a Rule: element.
     * 
     * @param pRule the Rule: element to add.
     */
    public void addRule(final GherkinRule pRule) {
        if (pRule != null) {
            rules.add(pRule);
        }
    }

    /**
     * Getter of the Example: or Scenario: elements.
     * 
     * @return the exampleOrScenarioElements
     */
    public List<AbstractGherkinExampleOrScenarioElement> getExampleOrScenarioElements() {
        return exampleOrScenarioElements;
    }

    /**
     * Add an Example: element.
     * 
     * @param pExample the Example: element to add.
     */
    public void addExample(final GherkinExample pExample) {
        if (pExample != null) {
            exampleOrScenarioElements.add(pExample);
        }
    }

    /**
     * Add a Scenario: element.
     * 
     * @param pScenario the Scenario: element to add.
     */
    public void addScenario(final GherkinScenario pScenario) {
        if (pScenario != null) {
            exampleOrScenarioElements.add(pScenario);
        }
    }

    /**
     * Getter of the Scenario Outline: or Scenario Template: elements.
     * 
     * @return the scenarioOutlineOrTemplatesElements
     */
    public List<AbstractGherkinScenarioOutlineOrTemplateElement> getScenarioOutlineOrTemplateElements() {
        return scenarioOutlineOrTemplateElements;
    }

    /**
     * Add a Scenario Outline: element.
     * 
     * @param pScenarioOutline the Scenario Outline: element to add.
     */
    public void addScenarioOutline(final GherkinScenarioOutline pScenarioOutline) {
        if (pScenarioOutline != null) {
            scenarioOutlineOrTemplateElements.add(pScenarioOutline);
        }
    }

    /**
     * Add a Scenario Template: element.
     * 
     * @param pScenarioTemplate the Scenario Template: element to add.
     */
    public void addScenarioTemplate(final GherkinScenarioTemplate pScenarioTemplate) {
        if (pScenarioTemplate != null) {
            scenarioOutlineOrTemplateElements.add(pScenarioTemplate);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printOn(final PrintStream pPrintStream, final int pIndentationLevel) {
        StringBuilder lLineSb = new StringBuilder();
        lLineSb.append(GherkinConstants.FEATURE_KEYWORD);
        lLineSb.append(GherkinConstants.CONTAINER_COLON_KEYWORD);
        if (!getContainerDescription().isEmpty()) {
            lLineSb.append(" ");
            lLineSb.append(getContainerDescription());
        }
        printIndented(lLineSb.toString(), pIndentationLevel, pPrintStream);

        // Add the potential additional description lines
        printAdditionalDescriptionOn(pPrintStream, pIndentationLevel);

        // Add the potential associated comment lines
        printComments(pPrintStream, pIndentationLevel + 1);

        // Add the potential background
        if (background != null) {
            background.printOn(pPrintStream, pIndentationLevel + 1);
        }

        // Add the potential Rules
        for (GherkinRule lRule : rules) {
            lRule.printOn(pPrintStream, pIndentationLevel + 1);
        }

        // Add the potential Example or Scenario
        for (AbstractGherkinExampleOrScenarioElement lExampleOrScenario : exampleOrScenarioElements) {
            lExampleOrScenario.printOn(pPrintStream, pIndentationLevel + 1);
        }

        // Add the potential Scenario Outline or Scenario Template elements
        for (AbstractGherkinScenarioOutlineOrTemplateElement lScenarioOutlineOrTemplate : scenarioOutlineOrTemplateElements) {
            lScenarioOutlineOrTemplate.printOn(pPrintStream, pIndentationLevel + 1);
        }

    }
}
