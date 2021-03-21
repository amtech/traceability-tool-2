/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

import java.io.PrintStream;

/**
 * Class modeling Gherkin data table.
 * <p>
 * It contains all the lines starting with |.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class GherkinDataTable extends AbstractGherkinMultipleLinedElement {

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     */
    public GherkinDataTable(final int pSourceFileLineNumber) {
        super(GherkinElementCategoryType.Data, pSourceFileLineNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printOn(final PrintStream pPrintStream, final int pIndentationLevel) {

        // Simply print each line.
        for (String lLine : getTextLines()) {
            printIndented(lLine, pIndentationLevel, pPrintStream);
        }

        // Add the potential associated comment lines
        printComments(pPrintStream, pIndentationLevel + 1);

    }
}
