/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.app.coverage;

/**
 * Common super-class for elements configuring test coverage.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractTestCoverage {

    /**
     * Whether the coverage is active or not.
     */
    private final boolean isActive;

    /**
     * Constructor.
     * 
     * @param pIsActive whether the configuration is active or not.
     */
    public AbstractTestCoverage(final boolean pIsActive) {
        isActive = pIsActive;
    }

    /**
     * Getter of the flag indicating whether the configuration is active or not.
     * 
     * @return the isActive
     */
    public boolean isActive() {
        return isActive;
    }
}
