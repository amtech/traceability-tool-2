/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.container;

import org.tools.doc.traceability.common.gerkhin.GherkinConstants;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinExampleOrScenarioElement;

/**
 * An Example container.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinExample extends AbstractGherkinExampleOrScenarioElement {

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pDescription          the description after the Example: keyword.
     */
    public GherkinExample(final int pSourceFileLineNumber, final String pDescription) {
        super(GherkinConstants.EXAMPLE_KEYWORD, pSourceFileLineNumber, pDescription);
    }
}
