/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.app.coverage;

import org.tools.doc.traceability.manager.gui.configuration.appmodel.FileSelectionFilter;

/**
 * C# unit tests coverage.
 * 
 * @author Yann Leglise
 *
 */
public class CSharpUnitTestsCoverage extends AbstractFileSelectionFilterAndMethodRegexpTestCoverage {

    /**
     * Constructor.
     * 
     * @param pIsActive whether this coverage is active or not.
     * @param pFileSelectionFiler the associated file selection filter.
     * @param pMethodNameSimpleRegexp the method name simple regexp.
     */
    public CSharpUnitTestsCoverage(final boolean pIsActive, final FileSelectionFilter pFileSelectionFiler,
            final String pMethodNameSimpleRegexp) {
        super(pIsActive, pFileSelectionFiler, pMethodNameSimpleRegexp);
    }

}
