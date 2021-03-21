/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.tools.config;

/**
 * Models a tests coverage configuration.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractTestCoverageConfiguration implements ICheckableToolConfiguration {

    /**
     * Whether the coverage is active or not.
     */
    private boolean isActive;

    /**
     * Specify the context for this instance.
     */
    private final String configurationContext;

    /**
     * Constructor.
     * 
     * @param pConfigurationContext the context description.
     */
    public AbstractTestCoverageConfiguration(final String pConfigurationContext) {
        isActive = false;
        configurationContext = pConfigurationContext;
    }

    /**
     * Getter of the flag indicating whether the configuration is active or not.
     * 
     * @return the isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Setter of the flag indicating whether the coverage is active or not.
     * 
     * @param pIsActive the isActive to set
     */
    public void setActive(final boolean pIsActive) {
        isActive = pIsActive;
    }

    /**
     * Reset the attributes to default.
     */
    public void reset() {
        isActive = false;
    }

    /**
     * Getter of the configuration context.
     * 
     * @return the configurationContext
     */
    public String getConfigurationContext() {
        return configurationContext;
    }
}
