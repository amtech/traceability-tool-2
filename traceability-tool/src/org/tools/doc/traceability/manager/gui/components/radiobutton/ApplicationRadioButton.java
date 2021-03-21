/**
 * 
 */
package org.tools.doc.traceability.manager.gui.components.radiobutton;

import javax.swing.ButtonGroup;

import org.tools.doc.traceability.manager.gui.controller.TraceabilityToolController;
import org.tools.doc.traceability.manager.gui.data.app.ApplicationData;

/**
 * A JButton representing an application.
 * 
 * @author Yann Leglise
 *
 */
public class ApplicationRadioButton extends AbstractDisplayableDataRadioButton {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -7596330183586038101L;

    /**
     * The associated application data.
     */
    private final ApplicationData applicationData;

    /**
     * Constructor.
     * 
     * @param pApplicationData the associated application data.
     * @param pGroup the group this radio button belongs to.
     */
    public ApplicationRadioButton(final ApplicationData pApplicationData, final ButtonGroup pGroup) {
        super(pApplicationData, pGroup, (pApplicationData == null ? "?" : pApplicationData.getDisplayName()));
        applicationData = pApplicationData;
    }

    /**
     * Getter of the application data.
     * 
     * @return the applicationData
     */
    public ApplicationData getApplicationData() {
        return applicationData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void handleSelectionChange(final ButtonGroup pButtonGroup) {
        notifyController();
        if (!applicationData.isAvailable()) {
            if (pButtonGroup != null) {
                pButtonGroup.clearSelection();
            }
        }
    }

    /**
     * Notify the controller of the current state.
     */
    public void notifyController() {
        if (applicationData.isAvailable()) {
            TraceabilityToolController.getInstance().handleApplicationSelection(applicationData);
        } else {
            TraceabilityToolController.getInstance().handleApplicationSelection(null);
        }
    }

    /**
     * Update availability from application data.
     */
    public void updateFromApplicationData() {
        boolean lIsAvailable = false;
        if (applicationData != null) {
            lIsAvailable = applicationData.isAvailable();
        }
        setEnabled(lIsAvailable);
    }

}
