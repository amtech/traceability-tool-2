/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.sub;

import java.io.File;

import javax.swing.JLabel;

import org.tools.doc.traceability.manager.gui.components.inputfield.InputField;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;
import org.tools.doc.traceability.manager.gui.panels.tools.CoverageMatrixGeneratorToolConfigurationPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.common.AbstractTraceabilityManagerToolConfigurationUpdatablePanel;

/**
 * Sub panel for configuring ALM tests covering.
 * 
 * @author Yann Leglise
 *
 */
public class AlmTestsCoveringConfigSubPanel extends AbstractTraceabilityManagerToolConfigurationUpdatablePanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -2976290644640019977L;

    /**
     * The text field for the ALM extracted file.
     */
    private InputField almExtractedFileIf;

    /**
     * Constructor.
     * 
     * @param pParentPanel the parent panel.
     */
    public AlmTestsCoveringConfigSubPanel(final CoverageMatrixGeneratorToolConfigurationPanel pParentPanel) {
        super(pParentPanel);
        almExtractedFileIf = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateToolConfigFromChangedCheckBox(final TraceabilityManagerToolConfiguration pTmgtConfig,
            final int pCheckBoxIdentifier) {
        // Nothing to do
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateToolConfigFromChangedInputField(final TraceabilityManagerToolConfiguration pTmgtConfig,
            final int pInputFieldIdentifier) {
        if (almExtractedFileIf.getIdentifier() == pInputFieldIdentifier) {
            File lAlmExtractedFile = new File(almExtractedFileIf.getText());
            pTmgtConfig.getAlmTestsCoverageConfiguration().setAlmExtractionFile(lAlmExtractedFile);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doBuildPanel() {
        JLabel lAlmExtractedFilenameLabel = new JLabel("ALM extracted file");
        addComponent(lAlmExtractedFilenameLabel, 0, 0, 1, 1, LABEL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_NONE, 10, 0,
                1, 1);

        almExtractedFileIf = new InputField(150, getParentPanel());
        addComponent(almExtractedFileIf, 1, 0, 1, 1, CONTROL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_HORIZONTAL, 10, 10,
                1, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFromToolConfiguration(final TraceabilityManagerToolConfiguration pTmgtConfig) {
        File lAlmExtractedFile = pTmgtConfig.getAlmTestsCoverageConfiguration().getAlmExtractionFile();
        if (lAlmExtractedFile == null) {
            almExtractedFileIf.setText("");
        } else {
            almExtractedFileIf.setText(lAlmExtractedFile.getAbsolutePath());
        }
    }

}
