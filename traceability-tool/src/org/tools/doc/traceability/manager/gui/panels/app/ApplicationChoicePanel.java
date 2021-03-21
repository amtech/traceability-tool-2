/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.app;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import org.tools.doc.traceability.common.ui.AbstractPanel;
import org.tools.doc.traceability.manager.gui.GuiConstants;
import org.tools.doc.traceability.manager.gui.components.radiobutton.ApplicationRadioButton;
import org.tools.doc.traceability.manager.gui.components.radiobutton.DisplayableDataRadioButtonWrapper;
import org.tools.doc.traceability.manager.gui.data.app.ApplicationData;
import org.tools.doc.traceability.manager.gui.manager.data.DataManager;

/**
 * The panel allowing to select an application.
 * 
 * @author Yann Leglise
 *
 */
public class ApplicationChoicePanel extends AbstractPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -1998233488814877158L;

    /**
     * The group for mutual exclusion of radio buttons.
     */
    private ButtonGroup appRadioButtonGroup;

    /**
     * The list of radio button corresponding to each application.
     */
    private List<ApplicationRadioButton> applicationRadioButtonList;

    /**
     * Constructor.
     */
    public ApplicationChoicePanel() {
        applicationRadioButtonList = new ArrayList<ApplicationRadioButton>();
        appRadioButtonGroup = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doBuildPanel() {
        appRadioButtonGroup = new ButtonGroup();
        ApplicationRadioButton lAppRb;

        for (ApplicationData lAppData : DataManager.getInstance().getApplicationDataList()) {
            // Create radio button for this app
            lAppRb = new ApplicationRadioButton(lAppData, appRadioButtonGroup);

            // Add it to the list
            applicationRadioButtonList.add(lAppRb);
        }

        DisplayableDataRadioButtonWrapper lRbWrapper;
        int lRowIdx = 0;
        for (ApplicationRadioButton lApplicationRb : applicationRadioButtonList) {
            lRbWrapper = new DisplayableDataRadioButtonWrapper(lApplicationRb);
            lRbWrapper.build();
            addComponent(lRbWrapper, 0, lRowIdx++, 1, 1, 50, 10, ANCHOR_LEFT, FILL_HORIZONTAL,
                    GuiConstants.PANEL_INNER_MARGIN, GuiConstants.PANEL_INNER_MARGIN, 0,
                    GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);
        }
    }

    /**
     * Update the availability of each radio button depending on the one of the
     * associated application data.
     * 
     */
    public void updateApplicationChoiceAvailability() {
        for (ApplicationRadioButton lApplicationRb : applicationRadioButtonList) {
            lApplicationRb.updateFromApplicationData();
        }
    }

    /**
     * Clear any application selection.
     */
    public void clearApplicationSelection() {
        appRadioButtonGroup.clearSelection();
    }

    /**
     * Try and select the default application if available, or else the first
     * available app if any.
     * 
     * @return the ApplicationData corresponding to the actually selected
     * application, or <tt>null</tt> if none was available.
     */
    public ApplicationData tryAndSelectDefaultOrFirstApplication() {
        ApplicationData lSelectedApplicationData = null;

        String lDefaultAppId = DataManager.getInstance().getDefaultApplicationDataId();

        Enumeration<AbstractButton> lButtons = appRadioButtonGroup.getElements();
        AbstractButton lFirstEnabledButton = null;
        AbstractButton lDefaultAppEnabledButton = null;
        while (lButtons.hasMoreElements()) {
            AbstractButton lButton = lButtons.nextElement();
            if (lButton.isEnabled()) {
                if (lFirstEnabledButton == null) {
                    lFirstEnabledButton = lButton;
                }

                // Try and see if this enabled button corresponds to the default
                // app
                if (lDefaultAppId != null) {
                    ApplicationRadioButton lAppRb = (ApplicationRadioButton) lButton;
                    ApplicationData lAppData = lAppRb.getApplicationData();
                    if (lDefaultAppId.compareTo(lAppData.getApplicationIdentifier()) == 0) {
                        lDefaultAppEnabledButton = lButton;
                    }
                }
            }
        }

        // Use the default app, if available
        if (lDefaultAppEnabledButton != null) {
            ApplicationRadioButton lAppRb = (ApplicationRadioButton) lDefaultAppEnabledButton;
            lAppRb.setSelected(true);
            lSelectedApplicationData = lAppRb.getApplicationData();
        }
        // Otherwise try and select the first available app
        else if (lFirstEnabledButton != null) {
            ApplicationRadioButton lAppRb = (ApplicationRadioButton) lFirstEnabledButton;
            lAppRb.setSelected(true);
            lSelectedApplicationData = lAppRb.getApplicationData();
        } else {
            lSelectedApplicationData = null;
        }

        return lSelectedApplicationData;
    }

    /**
     * Make the radio button corresponding to the given application data
     * selected.
     * 
     * @param pApplicationData the application data, or null to unselect all.
     */
    public void setApplicationSelected(final ApplicationData pApplicationData) {
        if (pApplicationData == null) {
            appRadioButtonGroup.clearSelection();
        } else {

            for (ApplicationRadioButton lApplicationRb : applicationRadioButtonList) {
                if (lApplicationRb.matches(pApplicationData)) {
                    lApplicationRb.setSelected(true);
                    break;
                }
            }
        }
    }

}
