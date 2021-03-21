/**
 * 
 */
package org.tools.doc.traceability.common.filesearch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.common.exceptions.FileSearchException;

/**
 * Utility class to search for some files from one or several directories.
 * 
 * <p>
 * The search is performed for one or more filters, a filter defining:
 * <ul>
 * <li>A root directory,
 * <li>A flag indicating if the search shall include sub-directories or not,
 * <li>A regular expression to select the files.
 * </ul>
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class FileSearcher {

    /**
     * The set of file search filters to consider.
     */
    private final FileSearchFilterSet fileSearchFilterSet;

    /**
     * Constructor.
     * 
     * @param pFileSearchFilterSet the set of file search filters to consider.
     */
    public FileSearcher(final FileSearchFilterSet pFileSearchFilterSet) {
        fileSearchFilterSet = pFileSearchFilterSet;
    }

    /**
     * Search for matches through all the added filters.
     * 
     * @return the list of files that matched one of the filters.
     * @throws FileSearchException if an error prevented performing the search
     * properly.
     */
    public List<File> search() throws FileSearchException {
        List<File> lFoundFiles = new ArrayList<File>();

        // Iterate on filters
        for (FileSearchFilter lFilter : fileSearchFilterSet) {
            performSearchForFilter(lFilter, lFoundFiles);
        }

        return lFoundFiles;
    }

    /**
     * Handle the search for the given filter, and add the matching files in the
     * given list.
     * 
     * @param pFilter the filter to process (shall no be <tt>null</tt>).
     * @param pFoundFiles the list where to add the matching files (shall not be
     * <tt>null</tt>).
     * @throws FileSearchException if an error prevented performing the search
     * properly.
     */
    private void performSearchForFilter(final FileSearchFilter pFilter, final List<File> pFoundFiles)
            throws FileSearchException {

        File lRootDir = pFilter.getSearchRootDirectory();

        // Start the search at the root directory level
        searchMatchingFilesFrom(lRootDir, pFilter, pFoundFiles);
    }

    /**
     * Search for files matching the given filter in the given directory.
     * <p>
     * Depending on how the filter is configured, call this method recursively
     * or not.
     * </p>
     * 
     * @param pSearchDirectory the directory where to search for matching files.
     * @param pFilter the filter to use.
     * @param pFoundFiles the list where to add matching files.
     * @throws FileSearchException if an error prevented performing the search
     * properly.
     */
    private void searchMatchingFilesFrom(final File pSearchDirectory, final FileSearchFilter pFilter,
            final List<File> pFoundFiles) throws FileSearchException {
        if (pSearchDirectory.isDirectory()) {

            // Get the contents of the directory
            File[] lDirectoryContents = pSearchDirectory.listFiles();

            // Create a list to store the sub-directories while iterating
            List<File> lSubDirectories = new ArrayList<File>();

            // Iterate on the directory elements
            for (int lElementIdx = 0; lElementIdx < lDirectoryContents.length; lElementIdx++) {
                File lElement = lDirectoryContents[lElementIdx];

                if (lElement.isDirectory()) {
                    // It's a directory : add it to the sub-directory list
                    lSubDirectories.add(lElement);
                } else {
                    // It is a file : check if it matches the filter
                    if (pFilter.doesFileMatch(lElement)) {
                        // Add the matching file to the list
                        pFoundFiles.add(lElement);
                    }
                }
            }

            // If the filter indicates to recurse, iterate on sub-directories
            if (pFilter.isCrawlRecursively()) {
                for (File lSubdir : lSubDirectories) {
                    // Call recursively for the sub-directory
                    searchMatchingFilesFrom(lSubdir, pFilter, pFoundFiles);
                }
            }

        } else {
            throw new FileSearchException("Cannot perform search in non existing (or non reachable) directory "
                    + pSearchDirectory.getAbsolutePath());
        }

    }

}
