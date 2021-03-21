/**
 * 
 */
package org.tools.doc.traceability.manager.gui.components.checkbox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

/**
 * Simplify the change notification of a check box.
 * 
 * @author Yann Leglise
 *
 */
public class CheckBox extends JCheckBox implements ActionListener {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -6317947127718953136L;

    /**
     * Counter for instance identifiers.
     */
    private static int InstanceIdentifier = 0;

    /**
     * Get the next available instance identifier.
     * 
     * @return the next available instance identifier.
     */
    private static synchronized int getNextInstanceIdentifier() {
        return InstanceIdentifier++;
    }

    /**
     * The unique identifier associated with this instance.
     */
    private final int identifier;

    /**
     * The change listener to notify.
     */
    private final ICheckBoxChangeListener changeListener;

    /**
     * Constructor.
     * 
     * @param pChangeListener the change listener to warn when the value is changed.
     * @param pLabel          the checkbox label.
     * @param pTooltip        the tool tip.
     */
    public CheckBox(final ICheckBoxChangeListener pChangeListener, final String pLabel, final String pTooltip) {
        super(pLabel);
        setToolTipText(pTooltip);
        identifier = getNextInstanceIdentifier();
        changeListener = pChangeListener;
        addActionListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(final ActionEvent pE) {
        if (changeListener != null) {
            changeListener.handleCheckBoxChange(identifier);
        }

    }

    /**
     * Getter of the identifier.
     * 
     * @return the identifier
     */
    public int getIdentifier() {
        return identifier;
    }

}
