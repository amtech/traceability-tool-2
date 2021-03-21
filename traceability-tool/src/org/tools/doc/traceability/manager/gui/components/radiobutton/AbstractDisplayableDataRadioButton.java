/**
 * 
 */
package org.tools.doc.traceability.manager.gui.components.radiobutton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import org.tools.doc.traceability.manager.gui.manager.data.IdentifiedData;

/**
 * A radio button associated with a {@link #IdentifiedData}.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractDisplayableDataRadioButton extends JRadioButton implements ActionListener {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -7548647431713651637L;

    /**
     * The identified data.
     */
    private final IdentifiedData identifiedData;

    /**
     * The parent button group.
     */
    private final ButtonGroup buttonGroup;

    /**
     * Constructor.
     * 
     * @param pIdentifiedData the associated identified data.
     * @param pGroup          the group this radio button belongs to.
     * @param pLabel          the radio button label
     */
    public AbstractDisplayableDataRadioButton(final IdentifiedData pIdentifiedData, final ButtonGroup pGroup,
            final String pLabel) {
        super(pLabel);
        identifiedData = pIdentifiedData;
        buttonGroup = pGroup;
        if (buttonGroup != null) {
            buttonGroup.add(this);
        }
        addActionListener(this);
    }

    /**
     * Checks that the given data matches the one associated with this instance.
     * 
     * @param pIdentifiedData the identified data to compare.
     * @return true if it matches, false otherwise.
     */
    public final boolean matches(final IdentifiedData pIdentifiedData) {
        boolean lMatches = false;

        if (pIdentifiedData != null) {
            lMatches = identifiedData.getIdentifier() == pIdentifiedData.getIdentifier();
        }

        return lMatches;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(final ActionEvent pE) {
        handleSelectionChange(buttonGroup);
    }

    /**
     * Called when the radio button selection is changed.
     * 
     * @param pButtonGroup the group the button belongs to.
     */
    protected abstract void handleSelectionChange(ButtonGroup pButtonGroup);
}
