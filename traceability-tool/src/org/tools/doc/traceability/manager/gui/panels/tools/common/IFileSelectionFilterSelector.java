/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.common;

import org.tools.doc.traceability.manager.gui.data.tools.config.FileSelectionFilter;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;

/**
 * Interface to implement for selecting a FileSelectionFilter from a
 * configuration.
 * 
 * @author Yann Leglise
 *
 */
public interface IFileSelectionFilterSelector {

    /**
     * Get the relevant FileSelectionFilter from the given tool configuration.
     * 
     * @param pTmgtConfig the Traceability manager tool configuration (not
     * <tt>null</tt>).
     * @return the FileSelectionFilter that corresponds to the implementor.
     */
    FileSelectionFilter getFileSelectionFilterFrom(TraceabilityManagerToolConfiguration pTmgtConfig);
}
