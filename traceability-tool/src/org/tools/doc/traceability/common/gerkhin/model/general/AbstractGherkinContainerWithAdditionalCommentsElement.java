/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class modeling a Gerking container that can have additional comments appended
 * to it.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractGherkinContainerWithAdditionalCommentsElement extends AbstractGherkinContainerElement {

    /**
     * The list of attached additional description lines.
     */
    private final List<GherkinAdditionalDescription> additionalDescriptions;

    /**
     * Constructor.
     * 
     * @param pContainerKeyword     The keyword associated with the container
     *                              (without the semicolon).
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pContainerDescription the description after the container keyword.
     * 
     */
    protected AbstractGherkinContainerWithAdditionalCommentsElement(final String pContainerKeyword,
            final int pSourceFileLineNumber, final String pContainerDescription) {
        super(pContainerKeyword, pSourceFileLineNumber, pContainerDescription);
        additionalDescriptions = new ArrayList<GherkinAdditionalDescription>();
    }

    /**
     * Getter of the additional descriptions.
     * 
     * @return the additionalDescriptions
     */
    public List<GherkinAdditionalDescription> getAdditionalDescriptions() {
        return additionalDescriptions;
    }

    /**
     * Add an attached additional description element.
     * 
     * @param pAdditionalDescription the additional description element to add.
     */
    public void addAdditionalDescription(final GherkinAdditionalDescription pAdditionalDescription) {
        if (pAdditionalDescription != null) {
            additionalDescriptions.add(pAdditionalDescription);
        }
    }

    /**
     * Add the potential additional comments.
     * 
     * @param pPrintStream      the target print stream.
     * @param pIndentationLevel the indentation level for the comments
     */
    protected void printAdditionalDescriptionOn(final PrintStream pPrintStream, final int pIndentationLevel) {
        // Add potential additional comments
        for (GherkinAdditionalDescription lGherkinAdditionalDescription : additionalDescriptions) {
            lGherkinAdditionalDescription.printOn(pPrintStream, pIndentationLevel);
        }
    }

}
