/**
 * 
 */
package org.tools.doc.traceability.manager.gui.components.radiobutton;

import javax.swing.ButtonGroup;

import org.tools.doc.traceability.manager.gui.controller.TraceabilityToolController;
import org.tools.doc.traceability.manager.gui.data.tools.data.ToolData;

/**
 * A JButton representing a tool.
 * 
 * @author Yann Leglise
 *
 */
public class ToolRadioButton extends AbstractDisplayableDataRadioButton {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 7873356991889088618L;

    /**
     * The associated tool data.
     */
    private final ToolData toolData;

    /**
     * Constructor.
     * 
     * @param pToolData the associated tool data.
     * @param pGroup    the group this radio button belongs to.
     */
    public ToolRadioButton(final ToolData pToolData, final ButtonGroup pGroup) {
        super(pToolData, pGroup, pToolData.getDisplayName());
        toolData = pToolData;
    }

    /**
     * Getter of the tool dor ata.
     * 
     * @return the toolData
     */
    public ToolData getToolData() {
        return toolData;
    }

    /**
     * Notify the controller of the current state.
     */
    public void notifyController() {
        TraceabilityToolController.getInstance().handleToolSelection(toolData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void handleSelectionChange(final ButtonGroup pButtonGroup) {
        notifyController();
    }
}
