/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.config;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.tools.doc.traceability.common.ui.AbstractPanel;
import org.tools.doc.traceability.manager.gui.GuiConstants;
import org.tools.doc.traceability.manager.gui.components.inputfield.IInputFieldChangeListener;
import org.tools.doc.traceability.manager.gui.components.inputfield.InputField;
import org.tools.doc.traceability.manager.gui.controller.TraceabilityToolController;

/**
 * The configuration panel.
 * 
 * @author Yann Leglise
 *
 */
public class ConfigurationPanel extends AbstractPanel implements IInputFieldChangeListener {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -427360680258727661L;

    /**
     * THe directory for Git repository.
     */
    private InputField gitBaseRepositoryDirIf;

    /**
     * The button to select the Git repository directory.
     */
    private JButton selectGitBaseRepositoryDirButton;

    /**
     * Constructor.
     */
    public ConfigurationPanel() {
        super();
        gitBaseRepositoryDirIf = null;
        selectGitBaseRepositoryDirButton = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doBuildPanel() {
        setBorder(createTitleBorder("Configuration"));

        JLabel lGitBasReporLbl = new JLabel("Base Git directory");
        addComponent(lGitBasReporLbl, 0, 0, 1, 1, 5, 0, ANCHOR_LEFT, FILL_NONE, GuiConstants.PANEL_INNER_MARGIN,
                GuiConstants.INTER_COMPONENENT_HORIZONTAL_MARGIN, GuiConstants.PANEL_INNER_MARGIN,
                GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        gitBaseRepositoryDirIf = new InputField(100, this);
        addComponent(gitBaseRepositoryDirIf, 1, 0, 1, 1, 90, 0, ANCHOR_LEFT, FILL_HORIZONTAL, 0,
                GuiConstants.INTER_COMPONENENT_HORIZONTAL_MARGIN, GuiConstants.PANEL_INNER_MARGIN,
                GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);

        selectGitBaseRepositoryDirButton = new JButton(TraceabilityToolController.getInstance()
                .getSelectGitBaseDirectoryAction());
        addComponent(selectGitBaseRepositoryDirButton, 2, 0, 1, 1, 5, 0, ANCHOR_RIGHT, FILL_NONE, 0,
                GuiConstants.PANEL_INNER_MARGIN, GuiConstants.PANEL_INNER_MARGIN,
                GuiConstants.INTER_COMPONENT_VERTICAL_MARGIN);
    }

    /**
     * Set the directory for the base repository.
     * 
     * @param pGitBaseRepositoryDirectory the GIT base directory.
     */
    public void setGitBaseRepositoryDirectory(final File pGitBaseRepositoryDirectory) {
        if (pGitBaseRepositoryDirectory == null) {
            gitBaseRepositoryDirIf.setText("");
        } else {
            gitBaseRepositoryDirIf.setText(pGitBaseRepositoryDirectory.getAbsolutePath());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleInputFieldChange(final int pInputFieldIdentifier) {
        if (gitBaseRepositoryDirIf.getIdentifier() == pInputFieldIdentifier) {
            File lGitBaseRepoDir = new File(gitBaseRepositoryDirIf.getText());
            TraceabilityToolController.getInstance().setGitBaseDirectory(lGitBaseRepoDir);
        }
    }
}
