/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

import java.util.ArrayList;
import java.util.List;

/**
 * Class modeling a Gherkin element with multiple lines.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractGherkinMultipleLinedElement extends AbstractGherkinElement {

    /**
     * The list of text for all the lines of the element.
     */
    private List<String> textLines;

    /**
     * Constructor.
     * 
     * @param pElementCategory      the element category.
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     */
    public AbstractGherkinMultipleLinedElement(final GherkinElementCategoryType pElementCategory,
            final int pSourceFileLineNumber) {
        super(pElementCategory, pSourceFileLineNumber);
        textLines = new ArrayList<String>();
    }

    /**
     * Add the given line text.
     * 
     * @param pLineText the text of the line to add.
     */
    public void addLineText(final String pLineText) {
        textLines.add(pLineText);
    }

    /**
     * Getter of the text lines.
     * 
     * @return the textLines
     */
    public List<String> getTextLines() {
        return textLines;
    }

}
