/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.app.coverage;

import org.tools.doc.traceability.manager.gui.configuration.appmodel.FileSelectionFilter;

/**
 * Cucumber tests coverage.
 * 
 * @author Yann Leglise
 *
 */
public class CucumberTestsCoverage extends AbstractFileSelectionFilterTestCoverage {

    /**
     * Constructor.
     * 
     * @param pIsActive whether this coverage is active or not.
     * @param pFileSelectionFiler the associated file selection filter.
     */
    public CucumberTestsCoverage(final boolean pIsActive, final FileSelectionFilter pFileSelectionFiler) {
        super(pIsActive, pFileSelectionFiler);
    }

}
