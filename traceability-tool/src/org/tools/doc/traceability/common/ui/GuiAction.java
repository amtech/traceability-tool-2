/**
 * 
 */
package org.tools.doc.traceability.common.ui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import org.tools.doc.traceability.manager.gui.controller.TraceabilityToolController;

/**
 * Define a GUI action.
 * 
 * @author Yann Leglise
 *
 */
public class GuiAction extends AbstractAction {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 2704069404132509197L;

    /**
     * Counter for action identifiers.
     */
    private static long LastUsedActionIdentifier = 0;

    /**
     * Get the next available action identifier.
     * 
     * @return the next available action identifier.
     */
    private static synchronized long getNextAvailableActionIdentifier() {
        long lNextId = LastUsedActionIdentifier++;

        return lNextId;
    }

    /**
     * The action identifier.
     */
    private final long actionIdentifier;

    /**
     * Constructor.
     * 
     * @param pActionLabel              the label of the action.
     * @param pActionToolTip            the tooltip.
     * @param pAcceleratorKeyEventValue the accelerator value (Like KeyEvent.VK_A).
     */
    public GuiAction(final String pActionLabel, final String pActionToolTip, final int pAcceleratorKeyEventValue) {
        actionIdentifier = getNextAvailableActionIdentifier();
        putValue(Action.NAME, pActionLabel);
        putValue(Action.SHORT_DESCRIPTION, pActionToolTip);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(pAcceleratorKeyEventValue, KeyEvent.CTRL_DOWN_MASK));
        putValue(Action.MNEMONIC_KEY, pAcceleratorKeyEventValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(final ActionEvent pE) {
        // Forward to the controller
        TraceabilityToolController.getInstance().handleAction(actionIdentifier);
    }

    /**
     * Getter of the action identifier.
     * 
     * @return the actionIdentifier
     */
    public long getActionIdentifier() {
        return actionIdentifier;
    }

}
