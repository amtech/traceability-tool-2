/**
 * 
 */
package org.tools.doc.traceability.manager.gui.frame;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.tools.doc.traceability.manager.gui.GuiConstants;
import org.tools.doc.traceability.manager.gui.panels.MainPanel;

/**
 * The main frame.
 * 
 * @author Yann Leglise
 *
 */
public class TraceabilityToolFrame extends AbstractTraceabilityToolFrame {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -5659687747865197971L;

    /**
     * The main panel.
     */
    private MainPanel mainPanel;

    /**
     * Constructor.
     */
    public TraceabilityToolFrame() {
        super(GuiConstants.APPLICATION_NAME);

        Dimension lPreferredDim = new Dimension(GuiConstants.PREFERRED_WIDTH, GuiConstants.PREFERRED_HEIGHT);
        setPreferredSize(lPreferredDim);
        setMinimumSize(lPreferredDim);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new MainPanel();
        mainPanel.build();

        getContentPane().add(mainPanel);

        setToolIcon();
    }

    /**
     * Getter of the main panel.
     * 
     * @return the mainPanel
     */
    public MainPanel getMainPanel() {
        return mainPanel;
    }
}
