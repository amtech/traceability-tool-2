/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.common;

import org.tools.doc.traceability.manager.gui.data.tools.config.AbstractTestCoverageConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;

/**
 * Interface to implement for selecting the relevant test coverage configuration
 * from a configuration.
 * 
 * @author Yann Leglise
 *
 */
public interface ITestCoverageConfigurationSelector {

    /**
     * Get the relevant test coverage configuration from the given tool
     * configuration.
     * 
     * @param pTmgtConfig the Traceability manager tool configuration (not
     * <tt>null</tt>).
     * @return the {@link AbstractTestCoverageConfiguration} that corresponds to
     * the implementor.
     */
    AbstractTestCoverageConfiguration getTestCoverageConfigurationFrom(TraceabilityManagerToolConfiguration pTmgtConfig);
}
