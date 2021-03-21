/**
 * 
 */
package org.tools.doc.traceability.common.filesearch;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tools.doc.traceability.common.exceptions.InvalidFileSearchFilterException;
import org.tools.doc.traceability.common.sregex.SimpleRegex;

/**
 * Models a set of file search filters.
 * 
 * * To use this class, instantiate it, call
 * {@link #addFilter(File, boolean, String)} or
 * {@link #addFilter(FileSearchFilter)} as many time as needed.
 * 
 * @author Yann Leglise
 *
 */
public class FileSearchFilterSet implements Iterable<FileSearchFilter> {

    /**
     * The list of flie search filters.
     */
    private final List<FileSearchFilter> fileSearchFilterList;

    /**
     * Constructor.
     */
    public FileSearchFilterSet() {
        fileSearchFilterList = new ArrayList<FileSearchFilter>();
    }

    /**
     * Add a file search filter built from the parameters.
     * 
     * @param pSearchRootDirectory The directory where to start the search.
     * @param pCrawlRecursively Whether the search shall crawl in sub
     * directories recursively or not.
     * @param pFileNameRegexp The simple regular expression to match files to select
     * (The <tt>*</tt> matches for any series of character, and <tt>?</tt>
     * matches for one single character).
     * @throws InvalidFileSearchFilterException if the file search filter
     * created from the given parameters is not valid.
     */
    public void addFilter(final File pSearchRootDirectory, final boolean pCrawlRecursively, final SimpleRegex pFileNameRegexp)
            throws InvalidFileSearchFilterException {

        // Create the file search filter
        FileSearchFilter lFileSearchFilter = new FileSearchFilter(pSearchRootDirectory, pCrawlRecursively,
                pFileNameRegexp);

        // Make sure it is valid
        lFileSearchFilter.checkIsValid();

        // If it is, add it to the list
        fileSearchFilterList.add(lFileSearchFilter);
    }

    /**
     * Add the given file search filter to the set.
     * 
     * @param pFileSearchFilter the file search filter to add.
     * @throws InvalidFileSearchFilterException if the parameter is
     * <tt>null</tt> or invalid.
     */
    public void addFilter(final FileSearchFilter pFileSearchFilter) throws InvalidFileSearchFilterException {
        // Check the parameter is defined
        if (pFileSearchFilter == null) {
            throw new InvalidFileSearchFilterException("The given file search filter is null");
        } else {
            // Make sure the filter is valid
            pFileSearchFilter.checkIsValid();

            // If it is, add it to the list
            fileSearchFilterList.add(pFileSearchFilter);
        }
    }

    /**
     * Indicates whether the set contains at least one filter or not.
     * 
     * @return <tt>true</tt> if the set contains at least one filter,
     * <tt>false</tt> otherwise.
     */
    public boolean isEmpty() {
        return fileSearchFilterList.isEmpty();
    }

    /**
     * Getter of the file search filter list contained in the set.
     * 
     * @return the fileSearchFilterList
     */
    public List<FileSearchFilter> getFileSearchFilterList() {
        return fileSearchFilterList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<FileSearchFilter> iterator() {
        return fileSearchFilterList.iterator();
    }

    /**
     * Ensures that the instances in the set are all valid.
     * 
     * @throws InvalidFileSearchFilterException if one of the filter is invalid.
     */
    public void checkIsValid() throws InvalidFileSearchFilterException {
        for (FileSearchFilter lFileSearchFilter : fileSearchFilterList) {
            lFileSearchFilter.checkIsValid();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder lSb = new StringBuilder();

        String lInvalidityDescription;
        try {
            checkIsValid();
            lInvalidityDescription = null;
        } catch (InvalidFileSearchFilterException e) {
            lInvalidityDescription = e.getMessage();
        }

        if (lInvalidityDescription == null) {
            int lFilterCount = fileSearchFilterList.size();
            if (lFilterCount == 0) {
                lSb.append("Empty file search filter set ");
            } else {
                lSb.append("File search filter set with ");
                lSb.append(Integer.toString(lFilterCount));
                lSb.append(" elements :");
                int lElementNum = 1;
                for (FileSearchFilter lFilter : fileSearchFilterList) {
                    lSb.append(Integer.toBinaryString(lElementNum++));
                    lSb.append(" : [");
                    lSb.append(lFilter.toString());
                    lSb.append("] ");
                }
            }
        } else {
            lSb.append("Invalid file search filter set : ");
            lSb.append(lInvalidityDescription);
        }

        return lSb.toString();
    }
}
