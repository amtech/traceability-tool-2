/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools;

import javax.swing.JLabel;

import org.tools.doc.traceability.common.ui.AbstractPanel;

/**
 * The panel displayed when no tool is selected.
 * 
 * @author Yann Leglise
 *
 */
public class NoToolPanel extends AbstractPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -2356339112629373224L;

    /**
     * Constructor.
     */
    public NoToolPanel() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doBuildPanel() {
        JLabel lLabel = new JLabel("<html><font color=\"red\">Select a tool</font></html>");

        addComponent(lLabel, 0, 0, 1, 1, 0, 0, ANCHOR_CENTER, FILL_NONE, 1, 1, 1, 1);

    }

}
