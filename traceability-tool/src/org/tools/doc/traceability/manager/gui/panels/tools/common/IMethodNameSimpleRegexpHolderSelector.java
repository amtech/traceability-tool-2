/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.common;

import org.tools.doc.traceability.manager.gui.data.tools.config.AbstractFileSelectionFilterAndMethodRegexpTestCoverageConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;

/**
 * Interface to implement for selecting the holder of a method name SimpleRegexp
 * from a configuration.
 * 
 * @author Yann Leglise
 *
 */
public interface IMethodNameSimpleRegexpHolderSelector {

    /**
     * Get the relevant holder of the method name SimpleRegexp from the given
     * tool configuration.
     * 
     * @param pTmgtConfig the Traceability manager tool configuration (not
     * <tt>null</tt>).
     * @return the
     * {@link AbstractFileSelectionFilterAndMethodRegexpTestCoverageConfiguration}
     * for the method name that corresponds to the implementor.
     */
    AbstractFileSelectionFilterAndMethodRegexpTestCoverageConfiguration getMethodNameSimpleRegexpHolderFrom(
            TraceabilityManagerToolConfiguration pTmgtConfig);

}
