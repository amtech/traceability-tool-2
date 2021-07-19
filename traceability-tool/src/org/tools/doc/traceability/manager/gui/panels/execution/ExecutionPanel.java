/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.execution;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.ui.AbstractPanel;
import org.tools.doc.traceability.common.ui.GuiAction;
import org.tools.doc.traceability.manager.gui.data.tools.execution.AbstractExecutionStatus;

/**
 * The execution panel.
 * 
 * @author Yann Leglise
 *
 */
public class ExecutionPanel extends AbstractPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -2059996463310265175L;

    /**
     * The title label.
     */
    private JLabel titleLabel;

    /**
     * The progress panel.
     */
    private ProgressPanel progressPanel;

    /**
     * The result panel.
     */
    private ResultPanel resultPanel;

    /**
     * The button to copy the status to clipboard.
     */
    private JButton copyStatusToClipboardButton;
    
    /**
     * The close button.
     */
    private JButton closeButton;
    
    /**
     * The action associated with the close button.
     */
    private final GuiAction closeAction;

    /**
     * The action to copy the status to clipboard.
     */
    private final GuiAction copyStatusToClipboardAction;

    /**
     * Constructor.
     * 
     * @param pCloseAction the close action.
     * @param pCopyStatusToClipboardAction the action to copy the status to clipboard.
     */
    public ExecutionPanel(final GuiAction pCloseAction, final GuiAction pCopyStatusToClipboardAction) {
        titleLabel = null;
        progressPanel = null;
        resultPanel = null;
        copyStatusToClipboardButton = null;
        closeButton = null;
        closeAction = pCloseAction;
        copyStatusToClipboardAction = pCopyStatusToClipboardAction;
    }

    /**
     * Update the progress panel from the given executor execution status.
     * 
     * @param pExecutorExecutionStatus the executor execution status to reflect.
     */
    public void updateFrom(final ExecutorExecutionStatus<?> pExecutorExecutionStatus) {
        if (progressPanel != null) {
            progressPanel.updateFrom(pExecutorExecutionStatus);
        }
    }

    /**
     * The the current execution status.
     * 
     * @param pExecutionStatus the current execution status.
     */
    public void setExecutionStatus(final AbstractExecutionStatus pExecutionStatus) {
        if (resultPanel != null) {
            resultPanel.setExecutionStatus(pExecutionStatus);
        }
    }

    /**
     * The the execution result.
     * 
     * @param pExecutionResult the execution result.
     */
    public void setExecutionResult(final String pExecutionResult) {
        if (resultPanel != null) {
            resultPanel.setExecutionResult(pExecutionResult);
        }
    }

    /**
     * Update the title.
     * 
     * @param pTitle the title to set.
     */
    public void setTitle(final String pTitle) {
        StringBuilder lTitleSb = new StringBuilder("<html><font color=\"#0000FF\">");

        if (pTitle != null) {
            lTitleSb.append(pTitle);
        } else {
            lTitleSb.append("?");
        }

        lTitleSb.append("</font></html>");

        titleLabel.setText(lTitleSb.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doBuildPanel() {
        titleLabel = new JLabel("<html><font color=\"blue\">Executing tool</font></html>");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        closeButton = new JButton(closeAction);
        copyStatusToClipboardButton = new JButton(copyStatusToClipboardAction);
        progressPanel = new ProgressPanel();
        progressPanel.build();
        resultPanel = new ResultPanel();
        resultPanel.build();

        addComponent(titleLabel, 0, 0, 2, 1, 100, 0, ANCHOR_CENTER, FILL_HORIZONTAL, 1, 1, 1, 1);
        addComponent(progressPanel, 0, 1, 2, 1, 100, 0, ANCHOR_CENTER, FILL_BOTH, 1, 1, 1, 1);
        addComponent(resultPanel, 0, 2, 2, 1, 100, 90, ANCHOR_CENTER, FILL_BOTH, 1, 1, 1, 1);
        addComponent(copyStatusToClipboardButton, 0, 3, 1, 1, 10, 0, ANCHOR_CENTER, FILL_NONE, 5, 10, 5, 10);
        addComponent(closeButton, 1, 3, 1, 1, 10, 0, ANCHOR_CENTER, FILL_NONE, 5, 10, 5, 10);
    }

}
