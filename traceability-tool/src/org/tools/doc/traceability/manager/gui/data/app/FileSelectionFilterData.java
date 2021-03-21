/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.app;

/**
 * Models a file selection filter.
 * 
 * @author Yann Leglise
 *
 */
public class FileSelectionFilterData {

    /**
     * The relative path (from the application directory) to the root directory
     * from where to start the search.
     */
    private final String relativeRootDirectoryPath;

    /**
     * Whether the search shall search recursively in sub-directories or not.
     */
    private final boolean searchRecursivelyInSubDirectories;

    /**
     * The simple regexp for matching file names.
     */
    private final String fileNameSimpleRegexp;

    /**
     * 
     * Constructor.
     * 
     * @param pRelativeRootDirectoryPath the relative path (from the application
     * directory) to the root directory from where to start the search.
     * @param pSearchRecursivelyInSubDirectories the flag indicating whether the
     * search shall search recursively in sub-directories or not.
     * @param pFileNameSimpleRegexp the simple regexp for matching file names.
     */
    public FileSelectionFilterData(final String pRelativeRootDirectoryPath,
            final boolean pSearchRecursivelyInSubDirectories, final String pFileNameSimpleRegexp) {
        super();
        relativeRootDirectoryPath = pRelativeRootDirectoryPath;
        searchRecursivelyInSubDirectories = pSearchRecursivelyInSubDirectories;
        fileNameSimpleRegexp = pFileNameSimpleRegexp;
    }

    /**
     * Getter of the relativeRootDirectoryPath.
     * 
     * @return the relativeRootDirectoryPath
     */
    public String getRelativeRootDirectoryPath() {
        return relativeRootDirectoryPath;
    }

    /**
     * Getter of the searchRecursivelyInSubDirectories.
     * 
     * @return the searchRecursivelyInSubDirectories
     */
    public boolean isSearchRecursivelyInSubDirectories() {
        return searchRecursivelyInSubDirectories;
    }

    /**
     * Getter of the fileNameSimpleRegexp.
     * 
     * @return the fileNameSimpleRegexp
     */
    public String getFileNameSimpleRegexp() {
        return fileNameSimpleRegexp;
    }

}
