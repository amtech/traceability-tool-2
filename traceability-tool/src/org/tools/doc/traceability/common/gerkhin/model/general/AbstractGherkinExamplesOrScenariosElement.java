/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

import java.io.PrintStream;

/**
 * Common superclass for Examples: and Scenarios: elements.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractGherkinExamplesOrScenariosElement extends AbstractGherkinContainerWithDataTableElement {

    /**
     * Constructor.
     * 
     * @param pContainerKeyword     The keyword associated with the container
     *                              (without the semicolon).
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pContainerDescription the description after the container keyword.
     */
    public AbstractGherkinExamplesOrScenariosElement(final String pContainerKeyword, final int pSourceFileLineNumber,
            final String pContainerDescription) {
        super(pContainerKeyword, pSourceFileLineNumber, pContainerDescription);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printOn(final PrintStream pPrintStream, final int pIndentationLevel) {
        // Print container line
        printIndented(getContainerLine(), pIndentationLevel, pPrintStream);

        // Add the potential associated comment lines
        printComments(pPrintStream, pIndentationLevel + 1);

        // Print the contained data table if any
        if (getDataTable() != null) {
            getDataTable().printOn(pPrintStream, pIndentationLevel + 1);
        }
    }
}
