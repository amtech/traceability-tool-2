/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.execution;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.ui.AbstractPanel;
import org.tools.doc.traceability.manager.gui.GuiConstants;

/**
 * The progress panel.
 * 
 * @author Yann Leglise
 *
 */
public class ProgressPanel extends AbstractPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 5384593634509588657L;

    /**
     * The progress bar.
     */
    private JProgressBar progressBar;

    /**
     * The text field for the current state.
     */
    private JTextArea currentStateTextArea;

    /**
     * Constructor.
     */
    public ProgressPanel() {
        progressBar = null;
        currentStateTextArea = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doBuildPanel() {
        currentStateTextArea = new JTextArea(4, 80);
        currentStateTextArea.setEditable(false);
        currentStateTextArea.setFont(GuiConstants.TEXT_FIELD_FONT);
        currentStateTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        currentStateTextArea.setLineWrap(true);

        progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);

        JLabel lCurrentStateLabel = new JLabel("Current operation:");
        addComponent(lCurrentStateLabel, 0, 0, 1, 1, 10, 0, ANCHOR_TOPLEFT, FILL_HORIZONTAL, 10, 5, 10, 1);
        addComponent(currentStateTextArea, 1, 0, 1, 1, 90, 0, ANCHOR_TOP, FILL_HORIZONTAL, 10, 10, 1, 10);

        JLabel lProgressionLabel = new JLabel("Progress:");
        addComponent(lProgressionLabel, 0, 1, 1, 1, 10, 0, ANCHOR_TOPLEFT, FILL_HORIZONTAL, 10, 5, 10, 1);

        addComponent(progressBar, 1, 1, 2, 1, 90, 0, ANCHOR_CENTER, FILL_HORIZONTAL, 10, 10, 10, 10);
    }

    /**
     * Update the panel from the given executor execution status.
     * 
     * @param pExecutorExecutionStatus the executor execution status to reflect.
     */
    public void updateFrom(final ExecutorExecutionStatus<?> pExecutorExecutionStatus) {
        if (pExecutorExecutionStatus != null) {
            progressBar.setValue((int) pExecutorExecutionStatus.getCompletionPercentage());
            currentStateTextArea.setText(pExecutorExecutionStatus.getCurrentOperation());
        } else {
            progressBar.setValue(0);
            currentStateTextArea.setText("?");
        }
    }
}
