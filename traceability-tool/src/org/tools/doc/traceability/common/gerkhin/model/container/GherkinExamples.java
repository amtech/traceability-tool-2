/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.container;

import org.tools.doc.traceability.common.gerkhin.GherkinConstants;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinExamplesOrScenariosElement;

/**
 * Class modeling an Examples: Gherkin element.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinExamples extends AbstractGherkinExamplesOrScenariosElement {

    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pDescription          the description after the Examples: keyword.
     */
    public GherkinExamples(final int pSourceFileLineNumber, final String pDescription) {
        super(GherkinConstants.EXAMPLES_KEYWORD, pSourceFileLineNumber, pDescription);
    }
}
