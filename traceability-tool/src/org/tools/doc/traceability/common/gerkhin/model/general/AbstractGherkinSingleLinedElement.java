/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

/**
 * Class modeling a Gherkin element with one single line.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractGherkinSingleLinedElement extends AbstractGherkinElement {

    /**
     * The text of the only line of the final element.
     */
    private final String stepText;

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pElementCategory      the element category.
     * @param pStepText             The text of the only line of the final element.
     */
    public AbstractGherkinSingleLinedElement(final GherkinElementCategoryType pElementCategory,
            final int pSourceFileLineNumber, final String pStepText) {
        super(pElementCategory, pSourceFileLineNumber);
        stepText = pStepText;
    }

    /**
     * Getter of the text of the only line of the final element.
     * 
     * @return the stepText
     */
    public String getStepText() {
        return stepText;
    }
}
