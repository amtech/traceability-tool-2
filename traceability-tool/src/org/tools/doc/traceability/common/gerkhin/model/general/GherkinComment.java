/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

import java.io.PrintStream;

import org.tools.doc.traceability.common.gerkhin.GherkinConstants;

/**
 * Class modeling an Gherkin comment line.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinComment extends AbstractGherkinSingleLinedElement {

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pText                 the comment text.
     */
    public GherkinComment(final int pSourceFileLineNumber, final String pText) {
        super(GherkinElementCategoryType.Comment, pSourceFileLineNumber, pText);
    }

    /**
     * Getter of the comment text.
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
        // Print the comment text
        printIndented(GherkinConstants.COMMENT_KEYWORD + getText(), pIndentationLevel, pPrintStream);
    }

}
