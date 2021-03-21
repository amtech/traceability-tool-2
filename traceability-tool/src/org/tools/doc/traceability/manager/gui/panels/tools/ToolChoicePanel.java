/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools;

import javax.swing.ButtonGroup;

import org.tools.doc.traceability.common.ui.AbstractPanel;
import org.tools.doc.traceability.manager.gui.GuiConstants;
import org.tools.doc.traceability.manager.gui.components.radiobutton.DisplayableDataRadioButtonWrapper;
import org.tools.doc.traceability.manager.gui.components.radiobutton.ToolRadioButton;
import org.tools.doc.traceability.manager.gui.data.tools.data.ToolData;
import org.tools.doc.traceability.manager.gui.manager.data.DataManager;

/**
 * The panel to choose the tool.
 * 
 * @author Yann Leglise
 *
 */
public class ToolChoicePanel extends AbstractPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -2258746514805515966L;

    /**
     * The radio button for the Coverage Matrix Generator Tool.
     */
    private ToolRadioButton coverageMatrixGeneratorToolArb;

    /**
     * The group for the radio buttons.
     */
    private ButtonGroup toolRadioButtonGroup;

    /**
     * Constructor.
     */
    public ToolChoicePanel() {
        coverageMatrixGeneratorToolArb = null;
        toolRadioButtonGroup = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doBuildPanel() {
        toolRadioButtonGroup = new ButtonGroup();
        coverageMatrixGeneratorToolArb = new ToolRadioButton(DataManager.getInstance()
                .getCoverageMatrixGeneratorToolData(), toolRadioButtonGroup);

        DisplayableDataRadioButtonWrapper lRbWrapper;

        lRbWrapper = new DisplayableDataRadioButtonWrapper(coverageMatrixGeneratorToolArb);
        lRbWrapper.build();
        addComponent(lRbWrapper, 1, 0, 1, 1, 50, 10, ANCHOR_RIGHT, FILL_HORIZONTAL, 0, GuiConstants.PANEL_INNER_MARGIN,
                GuiConstants.PANEL_INNER_MARGIN, GuiConstants.PANEL_INNER_MARGIN);
    }

    /**
     * Make the radio button corresponding to the given tool data selected.
     * 
     * @param pToolData the tool data, or null to unselect all.
     */
    public void setToolSelected(final ToolData pToolData) {
        if (pToolData == null) {
            toolRadioButtonGroup.clearSelection();
        } else {
            if (coverageMatrixGeneratorToolArb.matches(pToolData)) {
                coverageMatrixGeneratorToolArb.setSelected(true);
                coverageMatrixGeneratorToolArb.notifyController();
            }
        }
    }

    /**
     * Set the availability of visible radio buttons.
     * 
     * @param pEnabled true to enable, false to disable.
     */
    public void setVisibleRadioButtonsEnabled(final boolean pEnabled) {
        coverageMatrixGeneratorToolArb.setEnabled(pEnabled);
    }
}
