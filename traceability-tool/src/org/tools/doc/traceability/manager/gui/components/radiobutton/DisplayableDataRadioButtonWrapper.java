/**
 * 
 */
package org.tools.doc.traceability.manager.gui.components.radiobutton;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.tools.doc.traceability.common.ui.AbstractPanel;
import org.tools.doc.traceability.manager.gui.GuiConstants;

/**
 * A simple panel containing a displayable data radio button, to make it visible
 * when selected.
 * 
 * @author Yann Leglise
 *
 */
public class DisplayableDataRadioButtonWrapper extends AbstractPanel implements ItemListener {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 3365177063602404381L;

    /**
     * The normal font for radio buttons.
     */
    private static final Font NORMAL_FONT = GuiConstants.RADIO_BUTTON_FONT;

    /**
     * The selected radio button font.
     */
    private static final Font SELECTED_FONT = new Font(NORMAL_FONT.getName(), Font.BOLD,
            NORMAL_FONT.getSize() + GuiConstants.SELECTED_RADIO_BUTTON_FONT_SIZE_INCREMENT);

    /**
     * The wrapped displayable data radio button.
     */
    private final AbstractDisplayableDataRadioButton displayableDataRadioButton;

    /**
     * Constructor.
     * 
     * @param pDisplayableDataRadioButton the wrapped displayable data radio button.
     */
    public DisplayableDataRadioButtonWrapper(final AbstractDisplayableDataRadioButton pDisplayableDataRadioButton) {
        super();
        displayableDataRadioButton = pDisplayableDataRadioButton;
        if (displayableDataRadioButton != null) {
            displayableDataRadioButton.addItemListener(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doBuildPanel() {
        if (displayableDataRadioButton != null) {
            addComponent(displayableDataRadioButton, 1, 1, 1, 1, 1, 1, ANCHOR_LEFT, FILL_NONE, 1, 1, 1, 1);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void itemStateChanged(final ItemEvent pE) {
        if (pE.getStateChange() == ItemEvent.SELECTED) {
            displayableDataRadioButton.setForeground(GuiConstants.SELECTED_RADIO_BUTTON_TEXT_COLOR);
            displayableDataRadioButton.setFont(SELECTED_FONT);
        } else {
            displayableDataRadioButton.setForeground(Color.black);
            displayableDataRadioButton.setFont(NORMAL_FONT);
        }
    }

}
