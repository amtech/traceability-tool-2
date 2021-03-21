/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.common;

import org.tools.doc.traceability.manager.gui.GuiConstants;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;
import org.tools.doc.traceability.manager.gui.panels.tools.CoverageMatrixGeneratorToolConfigurationPanel;

/**
 * A class for a covering tests having a file selection filter part.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractCoveringTestWithFileSelectionFilterPanel extends
        AbstractTraceabilityManagerToolConfigurationUpdatablePanel implements IFileSelectionFilterSelector {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -5519423301199770458L;

    /**
     * The panel for file selection filter configuration.
     */
    private FileSelectionFilterPanel fileSelectionFilterPanel;

    /**
     * Constructor.
     * 
     * @param pParentPanel the parent panel.
     */
    public AbstractCoveringTestWithFileSelectionFilterPanel(
            final CoverageMatrixGeneratorToolConfigurationPanel pParentPanel) {
        super(pParentPanel);
        fileSelectionFilterPanel = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doBuildPanel() {
        fileSelectionFilterPanel = new FileSelectionFilterPanel(getParentPanel(), this);
        fileSelectionFilterPanel.build();

        addComponent(fileSelectionFilterPanel, 0, 0, 2, 1, CONTROL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_HORIZONTAL, 1,
                1, 1, GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        // Let subclasses add some more widgets if ever
        completeBuildPannel(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateToolConfigFromChangedCheckBox(final TraceabilityManagerToolConfiguration pTmgtConfig,
            final int pCheckBoxIdentifier) {
        fileSelectionFilterPanel.updateToolConfigFromChangedCheckBox(pTmgtConfig, pCheckBoxIdentifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateToolConfigFromChangedInputField(final TraceabilityManagerToolConfiguration pTmgtConfig,
            final int pInputFieldIdentifier) {
        fileSelectionFilterPanel.updateToolConfigFromChangedInputField(pTmgtConfig, pInputFieldIdentifier);
    }

    /**
     * Let the subclass complete the panel, if needed.
     * 
     * @param pNextRow the next row to add element.
     */
    protected abstract void completeBuildPannel(int pNextRow);

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFromToolConfiguration(final TraceabilityManagerToolConfiguration pTmgtConfig) {
        // Propagate to file selection filter panel
        if (fileSelectionFilterPanel != null) {
            fileSelectionFilterPanel.updateFromToolConfiguration(pTmgtConfig);
        }
    }
}
