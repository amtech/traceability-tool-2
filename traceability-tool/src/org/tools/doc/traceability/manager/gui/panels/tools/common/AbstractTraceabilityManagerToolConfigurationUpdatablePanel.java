/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.common;

import org.tools.doc.traceability.common.ui.AbstractPanel;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;
import org.tools.doc.traceability.manager.gui.panels.tools.CoverageMatrixGeneratorToolConfigurationPanel;

/**
 * A panel that can be updated or can update a
 * TraceabilityManagerToolConfiguration.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractTraceabilityManagerToolConfigurationUpdatablePanel extends AbstractPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -6865163021264884318L;

    /**
     * The parent panel.
     */
    private final CoverageMatrixGeneratorToolConfigurationPanel parentPanel;

    /**
     * Constructor.
     * 
     * @param pParentPanel the parent panel.
     */
    public AbstractTraceabilityManagerToolConfigurationUpdatablePanel(
            final CoverageMatrixGeneratorToolConfigurationPanel pParentPanel) {
        parentPanel = pParentPanel;
    }

    /**
     * Handle the change of a check box and update the traceability manage tool
     * configuration accordingly.
     * 
     * @param pTmgtConfig the Traceability Manager tool configuration to update.
     * @param pCheckBoxIdentifier the identifier of the modified check box.
     */
    public abstract void updateToolConfigFromChangedCheckBox(TraceabilityManagerToolConfiguration pTmgtConfig,
            int pCheckBoxIdentifier);

    /**
     * Handle the change of an input field and update the traceability manage
     * tool configuration accordingly.
     * 
     * @param pTmgtConfig the Traceability Manager tool configuration to update.
     * @param pInputFieldIdentifier the identifier of the modified input field.
     */
    public abstract void updateToolConfigFromChangedInputField(TraceabilityManagerToolConfiguration pTmgtConfig,
            int pInputFieldIdentifier);

    /**
     * Update the panel from the updated configuration.
     * 
     * @param pTmgtConfig the tool configuration.
     */
    public abstract void updateFromToolConfiguration(TraceabilityManagerToolConfiguration pTmgtConfig);

    /**
     * Getter of the parent panel.
     * 
     * @return the parentPanel
     */
    public CoverageMatrixGeneratorToolConfigurationPanel getParentPanel() {
        return parentPanel;
    }

}
