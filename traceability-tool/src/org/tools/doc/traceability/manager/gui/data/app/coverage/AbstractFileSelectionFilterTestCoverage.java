/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.app.coverage;

import org.tools.doc.traceability.manager.gui.configuration.appmodel.FileSelectionFilter;
import org.tools.doc.traceability.manager.gui.data.app.FileSelectionFilterData;

/**
 * Class modeling a coverage that has a file selection filter.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractFileSelectionFilterTestCoverage extends AbstractTestCoverage {

    /**
     * The file selection filter data.
     */
    private final FileSelectionFilterData fileSelectionFilerData;

    /**
     * Constructor.
     * 
     * @param pIsActive whether the coverage is active.
     * @param pFileSelectionFiler the file selection filter.
     */
    public AbstractFileSelectionFilterTestCoverage(final boolean pIsActive,
            final FileSelectionFilter pFileSelectionFiler) {
        super(pIsActive);
        fileSelectionFilerData = new FileSelectionFilterData(pFileSelectionFiler.getRootDirectoryRelativePath(),
                pFileSelectionFiler.isSearchInSubDirectoriesRecursively(), pFileSelectionFiler.getFilnameSimpleRegexp());
    }

    /**
     * Getter of the fileSelectionFiler.
     * 
     * @return the fileSelectionFiler
     */
    public FileSelectionFilterData getFileSelectionFiler() {
        return fileSelectionFilerData;
    }

}
