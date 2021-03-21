/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels;

import org.tools.doc.traceability.common.ui.AbstractPanel;
import org.tools.doc.traceability.common.ui.TitleBorderedPanel;
import org.tools.doc.traceability.manager.gui.GuiConstants;
import org.tools.doc.traceability.manager.gui.data.tools.data.ToolData;
import org.tools.doc.traceability.manager.gui.manager.data.DataManager;
import org.tools.doc.traceability.manager.gui.panels.app.ApplicationChoicePanel;
import org.tools.doc.traceability.manager.gui.panels.config.ConfigurationPanel;
import org.tools.doc.traceability.manager.gui.panels.control.ControlPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.CoverageMatrixGeneratorToolConfigurationPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.NoToolPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.ToolChoicePanel;
import org.tools.doc.traceability.manager.gui.panels.tools.ToolConfigurationPanel;

/**
 * The main panel.
 * 
 * @author Yann Leglise
 *
 */
public class MainPanel extends AbstractPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 7160529242276879317L;

    /**
     * The configuration panel.
     */
    private ConfigurationPanel configurationPanel;

    /**
     * The application choice panel.
     */
    private ApplicationChoicePanel applicationChoicePanel;

    /**
     * The tool choice panel.
     */
    private ToolChoicePanel toolChoicePanel;

    /**
     * The panel containing the relevant tool configuration panel.
     */
    private ToolConfigurationPanel toolConfigurationPanel;

    /**
     * The panel displayed in the configuration area when no tool is selected.
     */
    private NoToolPanel noToolPanel;

    /**
     * The panel for the configuration of the Coverage Matrix Generator tool.
     */
    private CoverageMatrixGeneratorToolConfigurationPanel coverageMatrixGeneratorToolConfigurationPanel;

    /**
     * The panel with the controls.
     */
    private ControlPanel controlPanel;

    /**
     * Constructor.
     */
    public MainPanel() {
        configurationPanel = null;
        applicationChoicePanel = null;
        toolChoicePanel = null;
        toolConfigurationPanel = null;
        coverageMatrixGeneratorToolConfigurationPanel = null;
        noToolPanel = null;
        controlPanel = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doBuildPanel() {
        configurationPanel = new ConfigurationPanel();
        configurationPanel.build();

        applicationChoicePanel = new ApplicationChoicePanel();
        applicationChoicePanel.build();

        toolChoicePanel = new ToolChoicePanel();
        toolChoicePanel.build();

        coverageMatrixGeneratorToolConfigurationPanel = new CoverageMatrixGeneratorToolConfigurationPanel();
        coverageMatrixGeneratorToolConfigurationPanel.build();

        noToolPanel = new NoToolPanel();
        noToolPanel.build();

        toolConfigurationPanel = new ToolConfigurationPanel();
        toolConfigurationPanel.build();
        toolConfigurationPanel.setInnerPanel(noToolPanel);

        controlPanel = new ControlPanel();
        controlPanel.build();

        addComponent(configurationPanel, 0, 0, 2, 1, 100, 10, ANCHOR_TOP, FILL_BOTH, GuiConstants.PANEL_INNER_MARGIN,
                GuiConstants.PANEL_INNER_MARGIN, GuiConstants.PANEL_INNER_MARGIN,
                GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        TitleBorderedPanel lApplicationChoicePanel = new TitleBorderedPanel("Target application",
                applicationChoicePanel);
        lApplicationChoicePanel.build();
        addComponent(lApplicationChoicePanel, 0, 1, 1, 2, 10, 80, ANCHOR_TOP, FILL_BOTH,
                GuiConstants.PANEL_INNER_MARGIN, GuiConstants.INTER_COMPONENENT_HORIZONTAL_MARGIN, 0,
                GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        TitleBorderedPanel lToolChoicePanel = new TitleBorderedPanel("Tool selection", toolChoicePanel);
        lToolChoicePanel.build();
        addComponent(lToolChoicePanel, 1, 1, 1, 1, 90, 10, ANCHOR_TOP, FILL_BOTH, 0, GuiConstants.PANEL_INNER_MARGIN,
                0, GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        addComponent(toolConfigurationPanel, 1, 2, 1, 1, 90, 70, ANCHOR_TOP, FILL_BOTH, 0,
                GuiConstants.PANEL_INNER_MARGIN, 0, GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        addComponent(controlPanel, 0, 3, 2, 1, 100, 10, ANCHOR_BOTTOM, FILL_BOTH, GuiConstants.PANEL_INNER_MARGIN,
                GuiConstants.PANEL_INNER_MARGIN, 0, GuiConstants.PANEL_INNER_MARGIN);
    }

    /**
     * Set the configuration panel for the given tool.
     * 
     * @param pToolData the tool data (null for none).
     */
    public void displayConfigurationPanelFor(final ToolData pToolData) {
        if (pToolData == null) {
            toolConfigurationPanel.setInnerPanel(noToolPanel);
            toolChoicePanel.setToolSelected(null);
        } else {
            if (pToolData.matches(DataManager.getInstance().getCoverageMatrixGeneratorToolData())) {
                toolConfigurationPanel.setInnerPanel(coverageMatrixGeneratorToolConfigurationPanel);
            }
        }
    }

    /**
     * Getter of the configuration panel.
     * 
     * @return the configurationPanel
     */
    public ConfigurationPanel getConfigurationPanel() {
        return configurationPanel;
    }

    /**
     * Getter of the application choice panel.
     * 
     * @return the applicationChoicePanel
     */
    public ApplicationChoicePanel getApplicationChoicePanel() {
        return applicationChoicePanel;
    }

    /**
     * Getter of the control panel.
     * 
     * @return the controlPanel
     */
    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    /**
     * Getter of the tool choice panel.
     * 
     * @return the toolChoicePanel
     */
    public ToolChoicePanel getToolChoicePanel() {
        return toolChoicePanel;
    }

    /**
     * Getter of the coverageMatrixGeneratorToolConfigurationPanel.
     * 
     * @return the coverageMatrixGeneratorToolConfigurationPanel
     */
    public CoverageMatrixGeneratorToolConfigurationPanel getCoverageMatrixGeneratorToolConfigurationPanel() {
        return coverageMatrixGeneratorToolConfigurationPanel;
    }

}
