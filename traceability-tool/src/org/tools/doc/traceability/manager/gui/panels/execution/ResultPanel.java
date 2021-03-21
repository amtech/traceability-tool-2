/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.execution;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.tools.doc.traceability.common.ui.AbstractPanel;
import org.tools.doc.traceability.manager.gui.data.tools.execution.AbstractExecutionStatus;

/**
 * The panel to display the result of an applicaiton execution.
 * 
 * @author Yann Leglise
 *
 */
public class ResultPanel extends AbstractPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -4711271671315020785L;

    /**
     * The label giving the current execution status.
     */
    private JLabel executionStatusLbl;

    /**
     * The editor pane for the execution status.
     */
    private JEditorPane statusPane;

    /**
     * Constructor.
     */
    public ResultPanel() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doBuildPanel() {
        executionStatusLbl = new JLabel();

        statusPane = new JEditorPane();
        statusPane.setContentType("text/html");
        statusPane.setEditable(false);

        JLabel lStatusLabel = new JLabel("Execution status:");

        addComponent(lStatusLabel, 0, 0, 1, 1, 10, 0, ANCHOR_LEFT, FILL_HORIZONTAL, 10, 5, 10, 5);
        addComponent(executionStatusLbl, 1, 0, 1, 1, 90, 0, ANCHOR_CENTER, FILL_HORIZONTAL, 10, 5, 10, 5);

        JLabel lResultLabel = new JLabel("Execution result:");
        addComponent(lResultLabel, 0, 1, 2, 1, 100, 0, ANCHOR_LEFT, FILL_HORIZONTAL, 10, 5, 10, 5);
        JScrollPane lStatusTpSp = new JScrollPane(statusPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        addComponent(lStatusTpSp, 0, 2, 2, 1, 100, 90, ANCHOR_CENTER, FILL_BOTH, 10, 5, 10, 5);

    }

    /**
     * The the current execution status.
     * 
     * @param pExecutionStatus the current execution status.
     */
    public void setExecutionStatus(final AbstractExecutionStatus pExecutionStatus) {
        if (executionStatusLbl != null) {
            executionStatusLbl.setText(pExecutionStatus.getHmiDescription());
        }
    }

    /**
     * The the execution result.
     * 
     * @param pExecutionResult the execution result.
     */
    public void setExecutionResult(final String pExecutionResult) {
        if (statusPane != null) {
            statusPane.setText(pExecutionResult);
            // Display the first part
            statusPane.setCaretPosition(0);
        }
    }

}
