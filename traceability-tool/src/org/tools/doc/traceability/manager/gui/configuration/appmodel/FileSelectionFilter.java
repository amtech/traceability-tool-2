/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration.appmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Class modeling a file selection filter.
 * 
 * @author Yann Leglise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FileSelectionFilter extends AbstractConfigurationModel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 8761122546777076347L;

    /**
     * The root directory to start the search from, relative to the application
     * base directory.
     */
    @XmlElement(name = "RootDirectory")
    private String rootDirectoryRelativePath;

    /**
     * Whether to search recursively in sub-directories or not.
     */
    @XmlElement(name = "SearchInSubDirectories")
    private boolean searchInSubDirectoriesRecursively;

    /**
     * The simple regular expression to match the file names..
     */
    @XmlElement(name = "FilenameSimpleRegexp")
    private String filnameSimpleRegexp;

    /**
     * Constructor.
     */
    public FileSelectionFilter() {
        rootDirectoryRelativePath = "";
        searchInSubDirectoriesRecursively = false;
        filnameSimpleRegexp = "";
    }

    /**
     * Getter of the rootDirectoryRelativePath.
     * 
     * @return the rootDirectoryRelativePath
     */
    public String getRootDirectoryRelativePath() {
        return rootDirectoryRelativePath;
    }

    /**
     * Setter of the rootDirectoryRelativePath.
     * 
     * @param pRootDirectoryRelativePath the rootDirectoryRelativePath to set
     */
    public void setRootDirectoryRelativePath(final String pRootDirectoryRelativePath) {
        rootDirectoryRelativePath = pRootDirectoryRelativePath;
    }

    /**
     * Getter of the searchInSubDirectoriesRecursively.
     * 
     * @return the searchInSubDirectoriesRecursively
     */
    public boolean isSearchInSubDirectoriesRecursively() {
        return searchInSubDirectoriesRecursively;
    }

    /**
     * Setter of the searchInSubDirectoriesRecursively.
     * 
     * @param pSearchInSubDirectoriesRecursively the
     * searchInSubDirectoriesRecursively to set
     */
    public void setSearchInSubDirectoriesRecursively(final boolean pSearchInSubDirectoriesRecursively) {
        searchInSubDirectoriesRecursively = pSearchInSubDirectoriesRecursively;
    }

    /**
     * Getter of the filnameSimpleRegexp.
     * 
     * @return the filnameSimpleRegexp
     */
    public String getFilnameSimpleRegexp() {
        return filnameSimpleRegexp;
    }

    /**
     * Setter of the filnameSimpleRegexp.
     * 
     * @param pFilnameSimpleRegexp the filnameSimpleRegexp to set
     */
    public void setFilnameSimpleRegexp(final String pFilnameSimpleRegexp) {
        filnameSimpleRegexp = pFilnameSimpleRegexp;
    }

}
