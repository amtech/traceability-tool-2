/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.common;

import java.io.File;

import javax.swing.JLabel;

import org.tools.doc.traceability.common.exceptions.InvalidSimpleRegexpException;
import org.tools.doc.traceability.common.sregex.SimpleRegex;
import org.tools.doc.traceability.manager.gui.GuiConstants;
import org.tools.doc.traceability.manager.gui.components.checkbox.CheckBox;
import org.tools.doc.traceability.manager.gui.components.inputfield.InputField;
import org.tools.doc.traceability.manager.gui.data.tools.config.FileSelectionFilter;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;
import org.tools.doc.traceability.manager.gui.panels.tools.CoverageMatrixGeneratorToolConfigurationPanel;

/**
 * Panel modeling a file selection filter.
 * 
 * @author Yann Leglise
 *
 */
public class FileSelectionFilterPanel extends AbstractTraceabilityManagerToolConfigurationUpdatablePanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -1930353245633071933L;

    /**
     * The implementation to extract the relevant FileSelectionFilter from the
     * tool context.
     */
    private final IFileSelectionFilterSelector fileSelectionFilterSelector;

    /**
     * The text field for the root directory.
     */
    private InputField rootDirectoryIf;

    /**
     * The checkbox to choose whether the to search in sub-directories
     * recursively or not.
     */
    private CheckBox searchInSubDirectoriesCb;

    /**
     * The text field for the file name simple regex.
     */
    private InputField fileNameRegexpIf;

    /**
     * Constructor.
     * 
     * @param pParentPanel the parent panel.
     * @param pFileSelectionFilterSelector the implementation to extract the
     * relevant FileSelectionFilter from the tool context.
     */
    public FileSelectionFilterPanel(final CoverageMatrixGeneratorToolConfigurationPanel pParentPanel,
            final IFileSelectionFilterSelector pFileSelectionFilterSelector) {
        super(pParentPanel);

        fileSelectionFilterSelector = pFileSelectionFilterSelector;

        rootDirectoryIf = null;
        searchInSubDirectoriesCb = null;
        fileNameRegexpIf = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doBuildPanel() {
        // Set a border
        setBorder(createTitleBorder("File selection filter"));

        JLabel lRootDirectoryLabel = new JLabel("Root directory");
        addComponent(lRootDirectoryLabel, 0, 0, 1, 1, LABEL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_NONE,
                GuiConstants.PANEL_INNER_MARGIN, 0, GuiConstants.PANEL_INNER_MARGIN, 1);
        rootDirectoryIf = new InputField(150, getParentPanel());
        rootDirectoryIf.setToolTipText("The directory from which the file search will start from.");
        addComponent(rootDirectoryIf, 1, 0, 3, 1, LABEL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_HORIZONTAL, 20,
                GuiConstants.PANEL_INNER_MARGIN, GuiConstants.PANEL_INNER_MARGIN, 1);

        searchInSubDirectoriesCb = new CheckBox(getParentPanel(), "Search in sub-directories",
                "If selected, search recursively in sub-directorise");
        addComponent(searchInSubDirectoriesCb, 0, 1, 1, 1, LABEL_WIDTH_PERCENTAGE,
                GuiConstants.INTER_COMPONENENT_HORIZONTAL_MARGIN, ANCHOR_LEFT, FILL_HORIZONTAL,
                GuiConstants.INTER_COMPONENENT_HORIZONTAL_MARGIN, GuiConstants.PANEL_INNER_MARGIN, 1, 1);

        JLabel lFileNameRegexpLabel = new JLabel("File name simple regexp");
        addComponent(lFileNameRegexpLabel, 2, 1, 1, 1, LABEL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_NONE, 20, 5, 1, 1);
        fileNameRegexpIf = new InputField(40, getParentPanel());
        fileNameRegexpIf.setToolTipText("<html>A regular expression to match files. <ul>"
                + "<li><tt><b>?</b></tt> is replaced by a character."
                + "<li><tt><b>*</b></tt> represents zero or more characters.</ul></html>");
        addComponent(fileNameRegexpIf, 3, 1, 1, 1, LABEL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_HORIZONTAL,
                GuiConstants.INTER_COMPONENENT_HORIZONTAL_MARGIN, 20, 10, GuiConstants.PANEL_INNER_MARGIN);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateToolConfigFromChangedCheckBox(final TraceabilityManagerToolConfiguration pTmgtConfig,
            final int pCheckBoxIdentifier) {
        if (searchInSubDirectoriesCb.getIdentifier() == pCheckBoxIdentifier) {
            FileSelectionFilter lFileSelectionFilter = fileSelectionFilterSelector
                    .getFileSelectionFilterFrom(pTmgtConfig);
            lFileSelectionFilter.setSearchRecursivelyInSubDirectories(searchInSubDirectoriesCb.isSelected());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateToolConfigFromChangedInputField(final TraceabilityManagerToolConfiguration pTmgtConfig,
            final int pInputFieldIdentifier) {
        if (rootDirectoryIf.getIdentifier() == pInputFieldIdentifier) {
            FileSelectionFilter lFileSelectionFilter = fileSelectionFilterSelector
                    .getFileSelectionFilterFrom(pTmgtConfig);
            File lRootDirectory = new File(rootDirectoryIf.getText());
            lFileSelectionFilter.setRootDirectory(lRootDirectory);
        } else if (fileNameRegexpIf.getIdentifier() == pInputFieldIdentifier) {
            FileSelectionFilter lFileSelectionFilter = fileSelectionFilterSelector
                    .getFileSelectionFilterFrom(pTmgtConfig);
            SimpleRegex lFileNameSimpleRegexp = null;
            {
                try {
                    lFileNameSimpleRegexp = new SimpleRegex(fileNameRegexpIf.getText());
                } catch (InvalidSimpleRegexpException e) {
                    lFileNameSimpleRegexp = null;
                }
            }
            lFileSelectionFilter.setFileNameSimpleRegexp(lFileNameSimpleRegexp);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFromToolConfiguration(final TraceabilityManagerToolConfiguration pTmgtConfig) {
        FileSelectionFilter lFileSelectionFilter = null;
        if (fileSelectionFilterSelector != null) {
            lFileSelectionFilter = fileSelectionFilterSelector.getFileSelectionFilterFrom(pTmgtConfig);
        }

        File lRootDirectory = null;
        boolean lSearchInSubDirectorise = false;
        String lFileNameSimpleRegex = "";

        if (lFileSelectionFilter != null) {
            lRootDirectory = lFileSelectionFilter.getRootDirectory();
            lSearchInSubDirectorise = lFileSelectionFilter.isSearchRecursivelyInSubDirectories();
            if (lFileSelectionFilter.getFileNameSimpleRegexp() == null) {
                lFileNameSimpleRegex = "";
            } else {
                lFileNameSimpleRegex = lFileSelectionFilter.getFileNameSimpleRegexp().getTextualRepresentation();
            }
        }

        if (lRootDirectory == null) {
            rootDirectoryIf.setText("");
        } else {
            rootDirectoryIf.setText(lRootDirectory.getAbsolutePath());
        }

        searchInSubDirectoriesCb.setSelected(lSearchInSubDirectorise);
        fileNameRegexpIf.setText(lFileNameSimpleRegex);

    }

}
