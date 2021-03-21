/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.tools.doc.traceability.manager.gui.GuiConstants;
import org.tools.doc.traceability.manager.gui.components.checkbox.ICheckBoxChangeListener;
import org.tools.doc.traceability.manager.gui.components.inputfield.IInputFieldChangeListener;
import org.tools.doc.traceability.manager.gui.components.inputfield.InputField;
import org.tools.doc.traceability.manager.gui.controller.TraceabilityToolController;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;
import org.tools.doc.traceability.manager.gui.panels.tools.common.AbstractToolConfigurationPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.covering.AlmTestsCoveringConfigPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.covering.CSharpUnitTestsCoveringPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.covering.CucumberTestsCoveringConfigPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.covering.JavaUnitTestsCoveringPanel;

/**
 * @author Yann Leglise
 *
 */
public class CoverageMatrixGeneratorToolConfigurationPanel extends AbstractToolConfigurationPanel
        implements IInputFieldChangeListener, ICheckBoxChangeListener {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -1924674845970980310L;

    /**
     * The text field for the path of the input SD file.
     */
    private InputField inputSdFileIf;

    /**
     * The text field for the requirement prefixes.
     */
    private InputField requirementPrefixesIf;

    /**
     * The text field for the justification file.
     */
    private InputField justificationFileIf;

    /**
     * The panel for ALM tests covering configuration panel.
     */
    private AlmTestsCoveringConfigPanel almTestsPanel;

    /**
     * The panel for cucumber tests covering configuration panel.
     */
    private CucumberTestsCoveringConfigPanel cucumberTestsPanel;

    /**
     * The panel for C# unit tests covering configuration panel.
     */
    private CSharpUnitTestsCoveringPanel cSharpUnitTestsPanel;

    /**
     * The panel for java unit tests covering configuration panel.
     */
    private JavaUnitTestsCoveringPanel javaUnitTestsPanel;

    /**
     * The input field for the output extracted requirement file.
     */
    private InputField outputExtractedRequirementsFileIf;

    /**
     * The input field for the output VTP file.
     */
    private InputField outputVtpFileIf;

    /**
     * The input field for the output traceability matrix file.
     */
    private InputField outputTraceabilityMatrixFileIf;

    /**
     * Constructor.
     */
    public CoverageMatrixGeneratorToolConfigurationPanel() {
        super();
        inputSdFileIf = null;
        requirementPrefixesIf = null;
        justificationFileIf = null;
        almTestsPanel = null;
        cucumberTestsPanel = null;
        cSharpUnitTestsPanel = null;
        javaUnitTestsPanel = null;
        outputExtractedRequirementsFileIf = null;
        outputVtpFileIf = null;
        outputTraceabilityMatrixFileIf = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doBuildPanel() {
        final int lPathInputFieldCharNum = 100;

        inputSdFileIf = new InputField(lPathInputFieldCharNum, this);

        int lRowIdx = 0;

        JLabel lInputSdFileLablel = new JLabel("Input SD file:");
        addComponent(lInputSdFileLablel, 0, lRowIdx, 1, 1, LABEL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_HORIZONTAL,
                GuiConstants.PANEL_INNER_MARGIN, GuiConstants.INTER_COMPONENENT_HORIZONTAL_MARGIN,
                GuiConstants.PANEL_INNER_MARGIN, GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);
        addComponent(inputSdFileIf, 1, lRowIdx, 1, 1, CONTROL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_HORIZONTAL, 0,
                GuiConstants.PANEL_INNER_MARGIN, GuiConstants.PANEL_INNER_MARGIN,
                GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        lRowIdx++;
        JLabel lRequirementPrefixesLablel = new JLabel("Requirement prefixe(s):");
        addComponent(lRequirementPrefixesLablel, 0, lRowIdx, 1, 1, LABEL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT,
                FILL_HORIZONTAL, GuiConstants.PANEL_INNER_MARGIN, GuiConstants.INTER_COMPONENENT_HORIZONTAL_MARGIN, 0,
                GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);
        requirementPrefixesIf = new InputField(lPathInputFieldCharNum, this);
        requirementPrefixesIf.setToolTipText("<html>You can indicate several requirement prefixes "
                + "by separating them with <tt><b>;</b></tt> <tt><b>,</b></tt> or <tt><b>/</b></tt></html>");
        addComponent(requirementPrefixesIf, 1, lRowIdx, 1, 1, CONTROL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_HORIZONTAL,
                0, GuiConstants.PANEL_INNER_MARGIN, 0, GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        lRowIdx++;
        justificationFileIf = new InputField(lPathInputFieldCharNum, this);
        JLabel lJustificaitonFileLabel = new JLabel("Justification file:");
        addComponent(lJustificaitonFileLabel, 0, lRowIdx, 1, 1, LABEL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_HORIZONTAL,
                GuiConstants.PANEL_INNER_MARGIN, GuiConstants.INTER_COMPONENENT_HORIZONTAL_MARGIN, 0,
                GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);
        addComponent(justificationFileIf, 1, lRowIdx, 1, 1, CONTROL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_HORIZONTAL,
                0, GuiConstants.PANEL_INNER_MARGIN, 0, GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        lRowIdx++;
        almTestsPanel = new AlmTestsCoveringConfigPanel(this);
        almTestsPanel.build();
        addComponent(almTestsPanel, 0, lRowIdx, 2, 1, CONTROL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_HORIZONTAL, 0,
                GuiConstants.PANEL_INNER_MARGIN, 0, GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        lRowIdx++;
        cucumberTestsPanel = new CucumberTestsCoveringConfigPanel(this);
        cucumberTestsPanel.build();
        addComponent(cucumberTestsPanel, 0, lRowIdx, 2, 1, CONTROL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_HORIZONTAL, 0,
                GuiConstants.PANEL_INNER_MARGIN, 0, GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        lRowIdx++;
        cSharpUnitTestsPanel = new CSharpUnitTestsCoveringPanel(this);
        cSharpUnitTestsPanel.build();
        addComponent(cSharpUnitTestsPanel, 0, lRowIdx, 2, 1, CONTROL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_HORIZONTAL,
                0, GuiConstants.PANEL_INNER_MARGIN, 0, GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        lRowIdx++;
        javaUnitTestsPanel = new JavaUnitTestsCoveringPanel(this);
        javaUnitTestsPanel.build();
        addComponent(javaUnitTestsPanel, 0, lRowIdx, 2, 1, CONTROL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_HORIZONTAL, 0,
                GuiConstants.PANEL_INNER_MARGIN, 0, GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        lRowIdx++;
        outputExtractedRequirementsFileIf = new InputField(lPathInputFieldCharNum, this);
        JLabel lOutputExtractedRequirementsFileLabel = new JLabel("Output extracted requirements file:");
        addComponent(lOutputExtractedRequirementsFileLabel, 0, lRowIdx, 1, 1, LABEL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT,
                FILL_HORIZONTAL, GuiConstants.PANEL_INNER_MARGIN, GuiConstants.INTER_COMPONENENT_HORIZONTAL_MARGIN, 0,
                GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);
        addComponent(outputExtractedRequirementsFileIf, 1, lRowIdx, 1, 1, CONTROL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT,
                FILL_HORIZONTAL, 0, GuiConstants.PANEL_INNER_MARGIN, 0, GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        lRowIdx++;
        outputVtpFileIf = new InputField(lPathInputFieldCharNum, this);
        JLabel lOutputVtpFileLabel = new JLabel("Output VTP file:");
        addComponent(lOutputVtpFileLabel, 0, lRowIdx, 1, 1, LABEL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_HORIZONTAL,
                GuiConstants.PANEL_INNER_MARGIN, GuiConstants.INTER_COMPONENENT_HORIZONTAL_MARGIN, 0,
                GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);
        addComponent(outputVtpFileIf, 1, lRowIdx, 1, 1, CONTROL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_HORIZONTAL, 0,
                GuiConstants.PANEL_INNER_MARGIN, 0, GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        lRowIdx++;
        outputTraceabilityMatrixFileIf = new InputField(lPathInputFieldCharNum, this);
        JLabel lOutputTraceabilityMatrixFileLabel = new JLabel("Output traceability matrix file:");
        addComponent(lOutputTraceabilityMatrixFileLabel, 0, lRowIdx, 1, 1, LABEL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT,
                FILL_HORIZONTAL, GuiConstants.PANEL_INNER_MARGIN, GuiConstants.INTER_COMPONENENT_HORIZONTAL_MARGIN, 0,
                GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);
        addComponent(outputTraceabilityMatrixFileIf, 1, lRowIdx, 1, 1, CONTROL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT,
                FILL_HORIZONTAL, 0, GuiConstants.PANEL_INNER_MARGIN, 0, GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        lRowIdx++;
        // Add an empty panel to get the rest stuck on the top
        JPanel lEmptyPanel = new JPanel();
        addComponent(lEmptyPanel, 0, lRowIdx, 2, 1, CONTROL_WIDTH_PERCENTAGE, CONTROL_WIDTH_PERCENTAGE, ANCHOR_LEFT,
                FILL_BOTH, GuiConstants.PANEL_INNER_MARGIN, GuiConstants.INTER_COMPONENENT_HORIZONTAL_MARGIN, 0,
                GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFromToolConfiguration() {
        TraceabilityManagerToolConfiguration lCmgtConfig = TraceabilityToolController.getInstance()
                .getCoverageMatrixGeneratorToolConfiguration();

        if (lCmgtConfig.getSdFiles().isEmpty()) {
            inputSdFileIf.setText("");
        } else {
            inputSdFileIf.setText(lCmgtConfig.getSdFiles().get(0).getAbsolutePath());
        }
        StringBuilder lReqPrefixesSb = new StringBuilder();
        boolean lIsfirst = true;
        if (lCmgtConfig.getRequirementPrefixes() != null) {
            for (String lReqPrefix : lCmgtConfig.getRequirementPrefixes()) {
                if (lIsfirst) {
                    lIsfirst = false;
                } else {
                    lReqPrefixesSb.append(", ");
                }
                lReqPrefixesSb.append(lReqPrefix);
            }
        }
        requirementPrefixesIf.setText(lReqPrefixesSb.toString());

        if (lCmgtConfig.getJustificationFile() == null) {
            justificationFileIf.setText("");
        } else {
            justificationFileIf.setText(lCmgtConfig.getJustificationFile().getAbsolutePath());
        }

        almTestsPanel.updateFromToolConfiguration(lCmgtConfig);
        cucumberTestsPanel.updateFromToolConfiguration(lCmgtConfig);
        cSharpUnitTestsPanel.updateFromToolConfiguration(lCmgtConfig);
        javaUnitTestsPanel.updateFromToolConfiguration(lCmgtConfig);

        if (lCmgtConfig.getOutputExtractedRequirementsFile() == null) {
            outputExtractedRequirementsFileIf.setText("");
        } else {
            outputExtractedRequirementsFileIf
                    .setText(lCmgtConfig.getOutputExtractedRequirementsFile().getAbsolutePath());
        }

        if (lCmgtConfig.getOutputVtpFile() == null) {
            outputVtpFileIf.setText("");
        } else {
            outputVtpFileIf.setText(lCmgtConfig.getOutputVtpFile().getAbsolutePath());
        }

        if (lCmgtConfig.getOutputTraceabilityMatrixFile() == null) {
            outputTraceabilityMatrixFileIf.setText("");
        } else {
            outputTraceabilityMatrixFileIf.setText(lCmgtConfig.getOutputTraceabilityMatrixFile().getAbsolutePath());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleInputFieldChange(final int pInputFieldIdentifier) {

        if (inputSdFileIf.getIdentifier() == pInputFieldIdentifier) {
            handleInputSdFileIfChanged();
        } else if (requirementPrefixesIf.getIdentifier() == pInputFieldIdentifier) {
            handleRequirementPrefixesIfChanged();
        } else if (justificationFileIf.getIdentifier() == pInputFieldIdentifier) {
            handleJustificationFileIfChanged();
        } else if (outputExtractedRequirementsFileIf.getIdentifier() == pInputFieldIdentifier) {
            handleOutputExtractedRequirementsFileIfChanged();
        } else if (outputTraceabilityMatrixFileIf.getIdentifier() == pInputFieldIdentifier) {
            handleOutputTraceabilityMatrixFileIfChanged();
        } else if (outputVtpFileIf.getIdentifier() == pInputFieldIdentifier) {
            handleOutputVtpFileIfChanged();
        } else {
            TraceabilityManagerToolConfiguration lCmgtConfig = TraceabilityToolController.getInstance()
                    .getCoverageMatrixGeneratorToolConfiguration();
            almTestsPanel.updateToolConfigFromChangedInputField(lCmgtConfig, pInputFieldIdentifier);
            cucumberTestsPanel.updateToolConfigFromChangedInputField(lCmgtConfig, pInputFieldIdentifier);
            cSharpUnitTestsPanel.updateToolConfigFromChangedInputField(lCmgtConfig, pInputFieldIdentifier);
            javaUnitTestsPanel.updateToolConfigFromChangedInputField(lCmgtConfig, pInputFieldIdentifier);
        }

        TraceabilityToolController.getInstance().updateLaunchingStatus();
    }

    /**
     * Handle the change in the input field for input SD.
     */
    private void handleInputSdFileIfChanged() {
        TraceabilityManagerToolConfiguration lCmgtConfig = TraceabilityToolController.getInstance()
                .getCoverageMatrixGeneratorToolConfiguration();
        File lSdFile = new File(inputSdFileIf.getText());
        lCmgtConfig.setSdFiles(lSdFile);
    }

    /**
     * Handle the change in the input field for requirement prefixes.
     */
    private void handleRequirementPrefixesIfChanged() {
        TraceabilityManagerToolConfiguration lCmgtConfig = TraceabilityToolController.getInstance()
                .getCoverageMatrixGeneratorToolConfiguration();
        String lReqPrefixesRaw = requirementPrefixesIf.getText();
        // Split on ;, , or /
        String lReqPrefixes[] = lReqPrefixesRaw.split("[;,/]");
        List<String> lReqPrefixeList = new ArrayList<String>();
        for (String lReqPrefix : lReqPrefixes) {
            lReqPrefixeList.add(lReqPrefix.trim());
        }
        lCmgtConfig.setRequirementPrefixes(lReqPrefixeList);
    }

    /**
     * Handle the change in the input field for justification file.
     */
    private void handleJustificationFileIfChanged() {
        TraceabilityManagerToolConfiguration lCmgtConfig = TraceabilityToolController.getInstance()
                .getCoverageMatrixGeneratorToolConfiguration();
        String lJustifFilePath = justificationFileIf.getText().trim();
        File lJustificationFile;
        if (lJustifFilePath.isEmpty()) {
            lJustificationFile = null;
        } else {
            lJustificationFile = new File(justificationFileIf.getText());
        }
        lCmgtConfig.setJustificationFile(lJustificationFile);
    }

    /**
     * Handle the change in the input field for output extracted requirements
     * file.
     */
    private void handleOutputExtractedRequirementsFileIfChanged() {
        TraceabilityManagerToolConfiguration lCmgtConfig = TraceabilityToolController.getInstance()
                .getCoverageMatrixGeneratorToolConfiguration();
        File lOutputExtractedReqFile = new File(outputExtractedRequirementsFileIf.getText());
        lCmgtConfig.setOutputExtractedRequirementsFile(lOutputExtractedReqFile);
    }

    /**
     * Handle the change in the input field for output traceability matrix file.
     */
    private void handleOutputTraceabilityMatrixFileIfChanged() {
        TraceabilityManagerToolConfiguration lCmgtConfig = TraceabilityToolController.getInstance()
                .getCoverageMatrixGeneratorToolConfiguration();
        File lOutputTraceabilityMatrixFile = new File(outputTraceabilityMatrixFileIf.getText());
        lCmgtConfig.setOutputTraceabilityMatrixFile(lOutputTraceabilityMatrixFile);
    }

    /**
     * Handle the change in the input field for output VTP file.
     */
    private void handleOutputVtpFileIfChanged() {
        TraceabilityManagerToolConfiguration lCmgtConfig = TraceabilityToolController.getInstance()
                .getCoverageMatrixGeneratorToolConfiguration();
        File lOutputVtpFile = new File(outputVtpFileIf.getText());
        lCmgtConfig.setOutputVtpFile(lOutputVtpFile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCheckBoxChange(final int pCheckBoxIdentifier) {
        TraceabilityManagerToolConfiguration lCmgtConfig = TraceabilityToolController.getInstance()
                .getCoverageMatrixGeneratorToolConfiguration();

        almTestsPanel.updateToolConfigFromChangedCheckBox(lCmgtConfig, pCheckBoxIdentifier);
        cucumberTestsPanel.updateToolConfigFromChangedCheckBox(lCmgtConfig, pCheckBoxIdentifier);
        cSharpUnitTestsPanel.updateToolConfigFromChangedCheckBox(lCmgtConfig, pCheckBoxIdentifier);
        javaUnitTestsPanel.updateToolConfigFromChangedCheckBox(lCmgtConfig, pCheckBoxIdentifier);

        TraceabilityToolController.getInstance().updateLaunchingStatus();
    }
}
