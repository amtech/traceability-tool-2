/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.control;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.tools.doc.traceability.common.exceptions.InvalidConfigurationException;
import org.tools.doc.traceability.common.sregex.SimpleRegex;
import org.tools.doc.traceability.common.ui.AbstractPanel;
import org.tools.doc.traceability.manager.gui.GuiConstants;
import org.tools.doc.traceability.manager.gui.controller.TraceabilityToolController;
import org.tools.doc.traceability.manager.gui.data.app.ApplicationData;
import org.tools.doc.traceability.manager.gui.data.tools.config.ICheckableToolConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.data.ToolData;

/**
 * The panel with the actions.
 * 
 * @author Yann Leglise
 *
 */
public class ControlPanel extends AbstractPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -733951405528960497L;

    /**
     * Checks whether the given simple regexp is valid or not.
     * 
     * @param pSimpleRegex the simple regexp to check (shall not be
     * <tt>null</tt>).
     * @throws InvalidConfigurationException if the simple regexp is invaild
     */
    public static void checkSimpleRegexIsValid(final SimpleRegex pSimpleRegex) throws InvalidConfigurationException {
        String lValue = pSimpleRegex.getSimpleRegexValue();
        if (lValue == null) {
            throw new InvalidConfigurationException("Define the value");
        } else {
            if (lValue.isEmpty()) {
                throw new InvalidConfigurationException("Define a not empty value");
            } else {
                if (pSimpleRegex.getSimpleRegexPattern() == null) {
                    throw new InvalidConfigurationException("Define a valid value");
                }
            }
        }
    }

    /**
     * The label to give tips about what to do.
     */
    private JLabel tipLabel;

    /**
     * The button for launching the process.
     */
    private JButton launchButton;

    /**
     * The exit button.
     */
    private JButton exitButton;

    /**
     * Constructor.
     */
    public ControlPanel() {
        super();
        tipLabel = null;
        launchButton = null;
        exitButton = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doBuildPanel() {

        tipLabel = new JLabel();
        launchButton = new JButton(TraceabilityToolController.getInstance().getLaunchProcessAction());
        exitButton = new JButton(TraceabilityToolController.getInstance().getExitAction());

        addComponent(tipLabel, 0, 0, 1, 1, 90, 1, ANCHOR_LEFT, FILL_HORIZONTAL, 20,
                GuiConstants.INTER_COMPONENENT_HORIZONTAL_MARGIN, GuiConstants.PANEL_INNER_MARGIN,
                GuiConstants.PANEL_INNER_MARGIN);
        addComponent(launchButton, 1, 0, 1, 1, 5, 1, ANCHOR_RIGHT, FILL_NONE, 0,
                GuiConstants.INTER_COMPONENENT_HORIZONTAL_MARGIN, GuiConstants.PANEL_INNER_MARGIN,
                GuiConstants.PANEL_INNER_MARGIN);
        addComponent(exitButton, 2, 0, 1, 1, 5, 1, ANCHOR_RIGHT, FILL_NONE, 0, 20, GuiConstants.PANEL_INNER_MARGIN,
                GuiConstants.PANEL_INNER_MARGIN);
    }

    /**
     * Update the tip label given the current state.
     * 
     * @param pCurrentGitBaseDirectory the selected GIT base directory.
     * @param pSelectedApplicationData the selected application (may be null).
     * @param pSelectedToolData the selected tool.
     * @param pSelectedToolConfiguration the configuration of the selected tool.
     */
    public void updateTipLabel(final File pCurrentGitBaseDirectory, final ApplicationData pSelectedApplicationData,
            final ToolData pSelectedToolData, final ICheckableToolConfiguration pSelectedToolConfiguration) {
        boolean lCanStart = false;
        StringBuilder lTipSb = new StringBuilder("<html>");
        if (pCurrentGitBaseDirectory == null) {
            lTipSb.append("<font color=\"red\">Select the GIT repository directory</font>");
        } else {
            if (pCurrentGitBaseDirectory.isDirectory()) {
                if (pSelectedApplicationData == null) {
                    lTipSb.append("<font color=\"red\">Select an application</font>");
                } else {

                    if (pSelectedToolConfiguration != null) {
                        try {
                            pSelectedToolConfiguration.checkIsValid();
                            lTipSb.append("Click <font color=\"blue\">Start</font> to start the <font color=\"blue\">");
                            lTipSb.append(pSelectedToolData.getDisplayName());
                            lTipSb.append("</font> to the application <font color=\"blue\">");
                            lTipSb.append(pSelectedApplicationData.getDisplayName());
                            lTipSb.append("</font>");
                            lCanStart = true;
                        } catch (InvalidConfigurationException ice) {
                            lTipSb.append("<font color=\"red\">The tool configuration is incorrrect : "
                                    + ice.getMessage() + "</font>");
                        }
                    } else {
                        lTipSb.append("<font color=\"red\">Select a tool</font>");
                    }
                }
            } else {
                lTipSb.append("<font color=\"red\">Select a GIT repository directory that exists</font>");
            }
        }

        lTipSb.append("</html>");

        tipLabel.setText(lTipSb.toString());

        launchButton.setEnabled(lCanStart);
    }

}
