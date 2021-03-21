/**
 * 
 */
package org.tools.doc.traceability.manager.gui.components.inputfield;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.tools.doc.traceability.manager.gui.GuiConstants;

/**
 * Simplify the change notification of JTextField.
 * 
 * @author Yann Leglise
 *
 */
public class InputField extends JTextField implements DocumentListener {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 3854263892999501857L;

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
     * The change listener.
     */
    private final IInputFieldChangeListener changeListener;

    /**
     * Constructor.
     * 
     * @param pNbColumns      the number of columns.
     * @param pChangeListener the change listener.
     */
    public InputField(final int pNbColumns, final IInputFieldChangeListener pChangeListener) {
        super(pNbColumns);
        identifier = getNextInstanceIdentifier();
        changeListener = pChangeListener;
        getDocument().addDocumentListener(this);
        setFont(GuiConstants.TEXT_FIELD_FONT);
    }

    /**
     * Getter of the instance identifier.
     * 
     * @return the identifier
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertUpdate(final DocumentEvent pE) {
        handleDocumentEvent(pE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUpdate(final DocumentEvent pE) {
        handleDocumentEvent(pE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changedUpdate(final DocumentEvent pE) {
        handleDocumentEvent(pE);
    }

    /**
     * Called when the text field for the GIT base repository directory path is
     * modified.
     * 
     * @param pEvt the document event.
     */
    private void handleDocumentEvent(final DocumentEvent pEvt) {
        if (changeListener != null) {
            changeListener.handleInputFieldChange(identifier);
        }
    }

}
