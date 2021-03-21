/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools;

import javax.swing.JScrollPane;

import org.tools.doc.traceability.common.ui.AbstractPanel;
import org.tools.doc.traceability.common.ui.TitleBorderedPanel;
import org.tools.doc.traceability.manager.gui.GuiConstants;

/**
 * The panel containing the configuration sub-panel for the selected tool.
 * 
 * @author Yann Leglise
 *
 */
public class ToolConfigurationPanel extends TitleBorderedPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 7957905257352782908L;

    /**
     * Constructor.
     */
    public ToolConfigurationPanel() {
        super("Configuration", null);
    }

    /**
     * Set the contents of the panel with the given one.
     * 
     * @param pInnerPanel the inner panel to set.
     */
    public void setInnerPanel(final AbstractPanel pInnerPanel) {
        removeAll();

        // Place the tool configuration panel in a scroll pane
        JScrollPane lScrollPane = new JScrollPane(pInnerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        addComponent(lScrollPane, 0, 0, 1, 1, 100, 100, ANCHOR_CENTER, FILL_BOTH, GuiConstants.PANEL_INNER_MARGIN,
                GuiConstants.PANEL_INNER_MARGIN, GuiConstants.PANEL_INNER_MARGIN, GuiConstants.PANEL_INNER_MARGIN);
        revalidate();
    }
}
