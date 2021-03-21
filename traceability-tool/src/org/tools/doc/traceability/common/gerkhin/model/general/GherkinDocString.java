/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

import java.io.PrintStream;

import org.tools.doc.traceability.common.gerkhin.GherkinConstants;

/**
 * Class modeling Gherkin doc string.
 * <p>
 * It contains all the lines between the two """.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class GherkinDocString extends AbstractGherkinMultipleLinedElement {

    /**
     * The actually used DocString keyword.
     */
    private final String actualDocStringKeyword;

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber   the number of the line in the source file this
     *                                line corresponds to (first is 1).
     * @param pActualDocStringKeyword The actually used DocString keyword (can be
     *                                {@link GherkinConstants#DOC_STRING_KEYWORD} or
     *                                {@link GherkinConstants#DOC_STRING_ALT_KEYWORD}).
     */
    public GherkinDocString(final int pSourceFileLineNumber, final String pActualDocStringKeyword) {
        super(GherkinElementCategoryType.Data, pSourceFileLineNumber);
        actualDocStringKeyword = pActualDocStringKeyword;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printOn(final PrintStream pPrintStream, final int pIndentationLevel) {

        // Print the opening DocString keyword
        printIndented(actualDocStringKeyword, pIndentationLevel, pPrintStream);

        // Print each line.
        for (String lLine : getTextLines()) {
            printIndented(lLine, pIndentationLevel + 1, pPrintStream);
        }

        // Print the closing DocString keyword
        printIndented(actualDocStringKeyword, pIndentationLevel, pPrintStream);

        // Add the potential associated comment lines
        printComments(pPrintStream, pIndentationLevel + 1);

    }
}
