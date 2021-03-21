/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

/**
 * Common superclass for Example: and Scenario: elements.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractGherkinExampleOrScenarioElement
        extends AbstractGherkinContainerWithAdditionalCommentsAndStepsElement {

    /**
     * Constructor.
     * 
     * @param pContainerKeyword     The keyword associated with the container
     *                              (without the semicolon).
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pContainerDescription the description after the container keyword.
     */
    public AbstractGherkinExampleOrScenarioElement(final String pContainerKeyword, final int pSourceFileLineNumber,
            final String pContainerDescription) {
        super(pContainerKeyword, pSourceFileLineNumber, pContainerDescription);
    }

}
