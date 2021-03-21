/**
 * 
 */
package org.tools.doc.traceability.manager.gui.controller;

import java.io.File;

import org.tools.doc.traceability.manager.gui.data.app.ApplicationData;
import org.tools.doc.traceability.manager.gui.data.tools.data.ToolData;

/**
 * Models the state of the GUI application.
 * 
 * <p>
 * It holds at the same time the current state and the new state, in order to
 * analyze the changes made. Once the new state changes have been handled, a
 * call to {@link #makeNewStateCurrent()} will make the new state becomes the
 * current one.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class TraceabilityToolState {

    /**
     * The ApplicationData corresponding to the currently selected application.
     * <p>
     * It is <tt>null</tt> when no application is selected.
     * </p>
     */
    private ApplicationData currentlySelectedApplicationData;

    /**
     * The ApplicationData corresponding to the newly selected application.
     * <p>
     * It is <tt>null</tt> when no application is selected.
     * </p>
     */
    private ApplicationData newlySelectedApplicationData;

    /**
     * The ToolData corresponding to the currently selected tool.
     * <p>
     * It is <tt>null</tt> when no tool is selected.
     * </p>
     */
    private ToolData currentlySelectedToolData;

    /**
     * The ToolData corresponding to the newly selected tool.
     * <p>
     * It is <tt>null</tt> when no tool is selected.
     * </p>
     */
    private ToolData newlySelectedToolData;

    /**
     * The currently set GIT repository base directory.
     */
    private File currentlySetGitBaseDirectory;

    /**
     * The newly set GIT repository base directory.
     */
    private File newlySetGitBaseDirectory;

    /**
     * Constructor.
     */
    public TraceabilityToolState() {
        currentlySelectedApplicationData = null;
        currentlySelectedToolData = null;
        currentlySetGitBaseDirectory = null;

        newlySelectedApplicationData = null;
        newlySelectedToolData = null;
        newlySetGitBaseDirectory = null;
    }

    /**
     * Make the new state become the current one.
     */
    public void makeNewStateCurrent() {
        currentlySelectedApplicationData = newlySelectedApplicationData;
        currentlySelectedToolData = newlySelectedToolData;
        currentlySetGitBaseDirectory = newlySetGitBaseDirectory;
    }

    /**
     * Getter of the currently selected application.
     * 
     * @return the currently selected application (<tt>null</tt> if none).
     */
    public ApplicationData getCurrentlySelectedApplicationData() {
        return currentlySelectedApplicationData;
    }

    /**
     * Setter of the newly selected ApplicationData.
     * 
     * @param pNewApplicationData the newly selected ApplicationData to set (
     * <tt>null</tt> if none).
     */
    public void setNewlySelectedApplicationData(final ApplicationData pNewApplicationData) {
        newlySelectedApplicationData = pNewApplicationData;
    }

    /**
     * Getter of the currently selected ToolData.
     * 
     * @return the currently selected ToolData (<tt>null</tt> if none).
     */
    public ToolData getCurrentlySelectedToolData() {
        return currentlySelectedToolData;
    }

    /**
     * Setter of the newly selected ToolData.
     * 
     * @param pSelectedToolData the newly selected ToolData to set (
     * <tt>null</tt> if none).
     */
    public void setNewlySelectedToolData(final ToolData pSelectedToolData) {
        newlySelectedToolData = pSelectedToolData;
    }

    /**
     * Getter of the currently set Git base directory.
     * 
     * @return the currently set Git base directory.
     */
    public File getCurrentlySetGitBaseDirectory() {
        return currentlySetGitBaseDirectory;
    }

    /**
     * Setter of the newly set Git base directory.
     * 
     * @param pNewGitBaseDirectory the new Git base directory to set (
     * <tt>null</tt> if none).
     */
    public void setNewlySetGitBaseDirectory(final File pNewGitBaseDirectory) {
        newlySetGitBaseDirectory = pNewGitBaseDirectory;
    }

    /**
     * Indicates whether the selected application changed since last call to
     * {@link #makeNewStateCurrent()}.
     * 
     * @return <tt>true</tt> if the selected application changed, <tt>false</tt>
     * otherwise.
     */
    public boolean didSelectedApplicationChange() {
        return currentlySelectedApplicationData != newlySelectedApplicationData;
    }

    /**
     * Indicates whether the selected tool changed since last call to
     * {@link #makeNewStateCurrent()}.
     * 
     * @return <tt>true</tt> if the selected tool changed, <tt>false</tt>
     * otherwise.
     */
    public boolean didSelectedToolChange() {
        return currentlySelectedToolData != newlySelectedToolData;
    }

    /**
     * Indicates whether the Git base directory changed since last call to
     * {@link #makeNewStateCurrent()}.
     * 
     * @return <tt>true</tt> if the Git base directory changed, <tt>false</tt>
     * otherwise.
     */
    public boolean didGitBaseDirectoryChange() {
        return currentlySetGitBaseDirectory != newlySetGitBaseDirectory;
    }

    /**
     * Checks if an application is currently selected.
     * 
     * @return <tt>true</tt> if an application is selected, <tt>false</tt> if
     * not.
     */
    public boolean isApplicationSelected() {
        return currentlySelectedApplicationData != null;
    }

    /**
     * Checks if a tool is currently selected.
     * 
     * @return <tt>true</tt> if a tool is selected, <tt>false</tt> if not.
     */
    public boolean isToolSelected() {
        return currentlySelectedToolData != null;
    }

    /**
     * Checks whether the currently selected tool matches the given one.
     * 
     * @param pToolData the tool data to consider.
     * @return <tt>true</tt> if the currently selected tool matches the given
     * parameter, <tt>false</tt> otherwise.
     */
    public boolean selectedToolMatches(final ToolData pToolData) {
        boolean lDoMatch = false;

        if (currentlySelectedToolData == null) {
            lDoMatch = pToolData == null;
        } else {
            lDoMatch = currentlySelectedToolData.matches(pToolData);
        }

        return lDoMatch;
    }

}
