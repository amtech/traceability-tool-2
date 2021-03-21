/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.app.coverage;

import org.tools.doc.traceability.manager.gui.configuration.appmodel.FileSelectionFilter;

/**
 * Test coverage with method name simple regexp.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractFileSelectionFilterAndMethodRegexpTestCoverage extends
        AbstractFileSelectionFilterTestCoverage {

    /**
     * The simple regexp for method names.
     */
    private final String methodNameSimpleRegexp;

    /**
     * Constructor.
     * 
     * @param pIsActive whether this coverage is active or not.
     * @param pFileSelectionFiler the associated file selection filter.
     * @param pMethodNameSimpleRegexp the method name simple regexp.
     */
    public AbstractFileSelectionFilterAndMethodRegexpTestCoverage(final boolean pIsActive,
            final FileSelectionFilter pFileSelectionFiler, final String pMethodNameSimpleRegexp) {
        super(pIsActive, pFileSelectionFiler);
        methodNameSimpleRegexp = pMethodNameSimpleRegexp;
    }

    /**
     * Getter of the method name simple regexp.
     * 
     * @return the methodNameSimpleRegexp
     */
    public String getMethodNameSimpleRegexp() {
        return methodNameSimpleRegexp;
    }

}
