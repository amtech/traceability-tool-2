/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.tools.config;

import java.io.File;

import org.tools.doc.traceability.common.exceptions.InvalidConfigurationException;
import org.tools.doc.traceability.common.exceptions.InvalidSimpleRegexpException;
import org.tools.doc.traceability.common.sregex.SimpleRegex;
import org.tools.doc.traceability.manager.gui.data.app.FileSelectionFilterData;
import org.tools.doc.traceability.manager.gui.panels.control.ControlPanel;

/**
 * Models a file selection filter.
 * 
 * @author Yann Leglise
 *
 */
public class FileSelectionFilter implements ICheckableToolConfiguration {

    /**
     * The path to the root directory from where to start the search.
     */
    private File rootDirectory;

    /**
     * Whether the search shall search recursively in sub-directories or not.
     */
    private boolean searchRecursivelyInSubDirectories;

    /**
     * The simple regexp for matching file names.
     */
    private SimpleRegex fileNameSimpleRegexp;

    /**
     * Constructor.
     * 
     */
    public FileSelectionFilter() {

        rootDirectory = null;
        searchRecursivelyInSubDirectories = false;
        fileNameSimpleRegexp = null;
    }

    /**
     * Configure the instance from the given parameters.
     * 
     * @param pApplicationRootDirectory the application root directory (shall
     * not be <tt>null</tt>).
     * @param pFileSelectionFilterData the data for configuring the instance
     * (shall not be <tt>null</tt>).
     * @throws InvalidSimpleRegexpException if the simple regexp for the file
     * name was invalid.
     */
    public void configureFrom(final File pApplicationRootDirectory,
            final FileSelectionFilterData pFileSelectionFilterData) throws InvalidSimpleRegexpException {

        rootDirectory = new File(pApplicationRootDirectory, pFileSelectionFilterData.getRelativeRootDirectoryPath());

        searchRecursivelyInSubDirectories = pFileSelectionFilterData.isSearchRecursivelyInSubDirectories();

        fileNameSimpleRegexp = new SimpleRegex(pFileSelectionFilterData.getFileNameSimpleRegexp());
    }

    /**
     * Getter of the root directory.
     * 
     * @return the rootDirectory
     */
    public File getRootDirectory() {
        return rootDirectory;
    }

    /**
     * Setter of the rootDirectory.
     * 
     * @param pRootDirectory the rootDirectory to set
     */
    public void setRootDirectory(final File pRootDirectory) {
        rootDirectory = pRootDirectory;
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
     * Setter of the searchRecursivelyInSubDirectories.
     * 
     * @param pSearchRecursivelyInSubDirectories the
     * searchRecursivelyInSubDirectories to set
     */
    public void setSearchRecursivelyInSubDirectories(final boolean pSearchRecursivelyInSubDirectories) {
        searchRecursivelyInSubDirectories = pSearchRecursivelyInSubDirectories;
    }

    /**
     * Getter of the file name simple Regexp.
     * 
     * @return the fileNameSimpleRegexp
     */
    public SimpleRegex getFileNameSimpleRegexp() {
        return fileNameSimpleRegexp;
    }

    /**
     * Setter of the fileNameSimpleRegexp.
     * 
     * @param pFileNameSimpleRegexp the fileNameSimpleRegexp to set
     */
    public void setFileNameSimpleRegexp(final SimpleRegex pFileNameSimpleRegexp) {
        fileNameSimpleRegexp = pFileNameSimpleRegexp;
    }

    /**
     * Reset the instance to default.
     */
    public void reset() {
        rootDirectory = null;
        searchRecursivelyInSubDirectories = false;
        fileNameSimpleRegexp = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkIsValid() throws InvalidConfigurationException {
        if (rootDirectory == null) {
            throw new InvalidConfigurationException("Define the root directory");
        } else {
            if (!rootDirectory.isDirectory()) {
                throw new InvalidConfigurationException("Define a root directory that exists");
            }
        }

        if (fileNameSimpleRegexp == null) {
            throw new InvalidConfigurationException("Define the file name simple regexp");
        } else {
            try {
                ControlPanel.checkSimpleRegexIsValid(fileNameSimpleRegexp);
            } catch (InvalidConfigurationException ice) {
                throw new InvalidConfigurationException(ice.getMessage() + " for the file name simple regexp");
            }
        }
    }

}
