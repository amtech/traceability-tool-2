/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.container;

import org.tools.doc.traceability.common.gerkhin.GherkinConstants;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinContainerWithAdditionalCommentsAndStepsElement;

/**
 * Class modeling a Gherkin Background.
 * <p>
 * It has only Given steps along with potential And, But, Star steps.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class GherkinBackground extends AbstractGherkinContainerWithAdditionalCommentsAndStepsElement {


    /**
     * Constructor.
     * 
     * @param pSourceFileLineNumber the number of the line in the source file this
     *                              line corresponds to (first is 1).
     * @param pDescription          the description after the Background: keyword
     *                              (<tt>null</tt> if none).
     */
    public GherkinBackground(final int pSourceFileLineNumber, final String pDescription) {
        super(GherkinConstants.BACKGROUND_KEYWORD, pSourceFileLineNumber, pDescription);
    }
}
