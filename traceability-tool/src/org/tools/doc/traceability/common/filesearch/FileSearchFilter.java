/**
 * 
 */
package org.tools.doc.traceability.common.filesearch;

import java.io.File;

import org.tools.doc.traceability.common.exceptions.InvalidFileSearchFilterException;
import org.tools.doc.traceability.common.sregex.SimpleRegex;

/**
 * Models a basic filter to search for files.
 * <p>
 * It consists of:
 * <ul>
 * <li>The root directory to start the search
 * <li>A flag indicating whether the search shall recurse in sub directories or
 * not
 * <li>A regular expression for matching the names of the files to select.
 * </ul>
 * </p>
 * <p>
 * Once the instance created, a call to {@link #checkIsValid()} must be made to
 * ensure it is valid.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class FileSearchFilter {

    /**
     * The directory where to start the search.
     */
    private final File searchRootDirectory;

    /**
     * Whether the search shall crawl in sub directories recursively or not.
     */
    private final boolean crawlRecursively;

    /**
     * The regular expression to match files to select.
     * <p>
     * The <tt>*</tt> matches for any series of character, and <tt>?</tt>
     * matches for one single character.
     * </p>
     */
    private final SimpleRegex fileNameRegexp;

    /**
     * Constructor.
     * 
     * @param pSearchRootDirectory The directory where to start the search.
     * @param pCrawlRecursively Whether the search shall crawl in sub
     * directories recursively or not.
     * @param pFileNameRegexp The simple regular expression to match files to
     * select (The <tt>*</tt> matches for any series of character, and
     * <tt>?</tt> matches for one single character).
     */
    public FileSearchFilter(final File pSearchRootDirectory, final boolean pCrawlRecursively,
            final SimpleRegex pFileNameRegexp) {
        searchRootDirectory = pSearchRootDirectory;
        crawlRecursively = pCrawlRecursively;
        fileNameRegexp = pFileNameRegexp;
    }

    /**
     * Ensures that the instance is valid.
     * 
     * @throws InvalidFileSearchFilterException if one of the parameters given
     * in constructor was not correct, and the instance could not be initialized
     * properly.
     */
    public void checkIsValid() throws InvalidFileSearchFilterException {
        // Check the validity of the search root directory
        if (searchRootDirectory == null) {
            throw new InvalidFileSearchFilterException("The given search root directory is null");
        }
        if (!searchRootDirectory.isDirectory()) {
            throw new InvalidFileSearchFilterException("The given search root directory does not exist ("
                    + searchRootDirectory.getAbsolutePath() + ")");
        }

        // Check the validity of the file name regexp
        if (fileNameRegexp == null) {
            throw new InvalidFileSearchFilterException("The given file name regexp is null");
        }
    }

    /**
     * Getter of the search root directory.
     * 
     * @return the searchRootDirectory
     */
    public File getSearchRootDirectory() {
        return searchRootDirectory;
    }

    /**
     * Getter of the flag indicating whether the search shall recurse in sub
     * directories or not.
     * 
     * @return the crawlRecursively
     */
    public boolean isCrawlRecursively() {
        return crawlRecursively;
    }

    /**
     * Check whether the given file matches this filter or not.
     * <p>
     * It only check the file name against the pattern (no check is done about
     * whether the given file is located in the search directory or not.
     * </p>
     * 
     * @param pFile the file to test.
     * @return <tt>true</tt> if the file matches, <tt>false</tt> otherwise.
     */
    public boolean doesFileMatch(final File pFile) {
        boolean lFileMatches = false;

        if (pFile != null) {
            lFileMatches = fileNameRegexp.matches(pFile.getName().toLowerCase());
        }

        return lFileMatches;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder lSb = new StringBuilder();

        lSb.append("Root dir: ");
        lSb.append(searchRootDirectory.getAbsolutePath());
        lSb.append(", recursive: ");
        if (crawlRecursively) {
            lSb.append("yes");
        } else {
            lSb.append("no");
        }
        lSb.append(", regex: ");
        lSb.append(fileNameRegexp);
        return lSb.toString();
    }
}
