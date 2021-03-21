/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.tools.config;

import org.tools.doc.traceability.manager.gui.data.app.ApplicationData;

/**
 * Class modeling a specific tool configuration.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractToolConfiguration implements ICheckableToolConfiguration {

    /**
     * The active application data.
     */
    private ApplicationData currentApplicationData;

    /**
     * Constructor.
     */
    public AbstractToolConfiguration() {
        currentApplicationData = null;
    }

    /**
     * Getter of the current application data.
     * 
     * @return the currentApplicationData
     */
    public ApplicationData getCurrentApplicationData() {
        return currentApplicationData;
    }

    /**
     * Setter of the current application aata.
     * 
     * @param pCurrentApplicationData the currentApplicationData to set
     */
    public void setCurrentApplicationData(final ApplicationData pCurrentApplicationData) {
        currentApplicationData = pCurrentApplicationData;
        handleNewCurrentApplicationData(currentApplicationData);
    }

    /**
     * Called when the current application data changed.
     * 
     * @param pCurrentApplicationData the new current application data.
     */
    protected abstract void handleNewCurrentApplicationData(ApplicationData pCurrentApplicationData);

}
