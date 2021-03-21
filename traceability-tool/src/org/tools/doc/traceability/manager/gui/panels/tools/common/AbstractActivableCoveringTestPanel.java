/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.common;

import javax.swing.JPanel;

import org.tools.doc.traceability.manager.gui.components.checkbox.CheckBox;
import org.tools.doc.traceability.manager.gui.data.tools.config.AbstractTestCoverageConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;
import org.tools.doc.traceability.manager.gui.panels.tools.CoverageMatrixGeneratorToolConfigurationPanel;

/**
 * Panel that models a test covering that can be activated or deactivated.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractActivableCoveringTestPanel extends
        AbstractTraceabilityManagerToolConfigurationUpdatablePanel implements ITestCoverageConfigurationSelector {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -6656582290784805571L;

    /**
     * The checkbox to choose whether the test coverage is active or not.
     */
    private CheckBox isActiveCb;

    /**
     * The label representing the covering test.
     */
    private final String coveringTestLabel;

    /**
     * The specific oontents panel.
     */
    private AbstractTraceabilityManagerToolConfigurationUpdatablePanel contentsPanel;

    /**
     * Constructor.
     * 
     * @param pParentPanel the parent panel
     * @param pCoveringTestLabel the label for the check box.
     */
    public AbstractActivableCoveringTestPanel(final CoverageMatrixGeneratorToolConfigurationPanel pParentPanel,
            final String pCoveringTestLabel) {
        super(pParentPanel);
        coveringTestLabel = pCoveringTestLabel;

        isActiveCb = null;
        contentsPanel = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doBuildPanel() {

        // Set a border
        setBorder(createTitleBorder(coveringTestLabel));

        isActiveCb = new CheckBox(getParentPanel(), "Activate " + coveringTestLabel, "Tick to include "
                + coveringTestLabel + " in the analysis");

        addComponent(isActiveCb, 0, 0, 1, 1, 10, 0, ANCHOR_TOPLEFT, FILL_NONE, 1, 1, 1, 1);

        contentsPanel = createContentsPanel();
        contentsPanel.build();

        addComponent(contentsPanel, 1, 0, 1, 1, 10, 0, ANCHOR_LEFT, FILL_NONE, 1, 1, 1, 1);
        contentsPanel.setVisible(false);

        // Add an empty panel on the right to keep the other components stuck to
        // the left
        JPanel lEmptyPanel = new JPanel();
        addComponent(lEmptyPanel, 2, 0, 1, 1, 80, 0, ANCHOR_RIGHT, FILL_HORIZONTAL, 1, 1, 1, 1);

    }

    /**
     * Create the sub-panel with the specific contents.
     * 
     * @return the specific oontents panel.
     */
    protected abstract AbstractTraceabilityManagerToolConfigurationUpdatablePanel createContentsPanel();

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateToolConfigFromChangedCheckBox(final TraceabilityManagerToolConfiguration pTmgtConfig,
            final int pCheckBoxIdentifier) {
        if (contentsPanel != null) {
            if (isActiveCb.getIdentifier() == pCheckBoxIdentifier) {

                boolean lIsActive = isActiveCb.isSelected();

                // Update the model
                AbstractTestCoverageConfiguration lTestCoverageConfig = getTestCoverageConfigurationFrom(pTmgtConfig);
                lTestCoverageConfig.setActive(lIsActive);

                contentsPanel.setVisible(lIsActive);
            } else {
                contentsPanel.updateToolConfigFromChangedCheckBox(pTmgtConfig, pCheckBoxIdentifier);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateToolConfigFromChangedInputField(final TraceabilityManagerToolConfiguration pTmgtConfig,
            final int pInputFieldIdentifier) {
        if (contentsPanel != null) {
            contentsPanel.updateToolConfigFromChangedInputField(pTmgtConfig, pInputFieldIdentifier);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFromToolConfiguration(final TraceabilityManagerToolConfiguration pTmgtConfig) {
        // Change the check box at this level
        isActiveCb.setSelected(isActivatedInConfiguration(pTmgtConfig));
        updateToolConfigFromChangedCheckBox(pTmgtConfig, isActiveCb.getIdentifier());

        // Propagate to content panel if defined
        if (contentsPanel != null) {
            contentsPanel.updateFromToolConfiguration(pTmgtConfig);
        }
    }

    /**
     * Let concrete class indicate whether the test covering is activated in the
     * configuration or not.
     * 
     * @param pTmgtConfig the tool configuration.
     * @return <tt>true</tt> if the test covering corresponding to this type is
     * activated, <tt>false</tt> otherwise.
     */
    protected abstract boolean isActivatedInConfiguration(TraceabilityManagerToolConfiguration pTmgtConfig);

}
