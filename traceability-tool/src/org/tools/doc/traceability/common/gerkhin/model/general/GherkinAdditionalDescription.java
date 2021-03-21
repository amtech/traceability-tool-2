/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

import java.io.PrintStream;

/**
 * Class modeling an Gherkin additional description line.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinAdditionalDescription extends AbstractGherkinSingleLinedElement {

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pText                 the comment text.
     */
    public GherkinAdditionalDescription(final int pSourceFileLineNumber, final String pText) {
        super(GherkinElementCategoryType.AdditionalDescription, pSourceFileLineNumber, pText);
    }

    /**
     * Getter of the description text.
     * 
     * @return the text
     */
    public String getText() {
        return getStepText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printOn(final PrintStream pPrintStream, final int pIndentationLevel) {
        // Print the description text
        printIndented(getText(), pIndentationLevel, pPrintStream);
    }

}
