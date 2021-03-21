/**
 * 
 */
package org.tools.doc.traceability.manager.gui.frame;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;

import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.ui.GuiAction;
import org.tools.doc.traceability.manager.gui.GuiConstants;
import org.tools.doc.traceability.manager.gui.data.tools.execution.AbstractExecutionStatus;
import org.tools.doc.traceability.manager.gui.panels.execution.ExecutionPanel;

/**
 * The frame for the tool execution.
 * 
 * @author Yann Leglise
 *
 */
public class ToolExecutionFrame extends AbstractTraceabilityToolFrame {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 1427828330344352001L;

    /**
     * The execution panel.
     */
    private ExecutionPanel executionPanel;

    /**
     * The parent frame.
     */
    private final JFrame parentFrame;

    /**
     * The action associated with the close button of the execution panel.
     */
    private final GuiAction closeFrameAction;

    /**
     * Constructor.
     * 
     * @param pParentFrame      the parent frame.
     * @param pCloseFrameAction the action to close the frame.
     */
    public ToolExecutionFrame(final JFrame pParentFrame, final GuiAction pCloseFrameAction) {
        super(GuiConstants.PROGRESS_DIALOG_FRAME_TITLE);

        // Prevent closing with the cross icon
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        parentFrame = pParentFrame;

        Dimension lPreferredDim = new Dimension(GuiConstants.PREFERRED_WIDTH, GuiConstants.PREFERRED_HEIGHT);
        setPreferredSize(lPreferredDim);
        setMinimumSize(lPreferredDim);

        closeFrameAction = pCloseFrameAction;

        executionPanel = new ExecutionPanel(closeFrameAction);
        executionPanel.build();

        getContentPane().add(executionPanel);
        
        setToolIcon();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean pB) {
        super.setVisible(pB);

        if (pB) {
            // Recenter on the parent frame
            Rectangle lParentFrameBounds = parentFrame.getBounds();
            Point lScreenCenter = new Point(lParentFrameBounds.x + (lParentFrameBounds.width) / 2,
                    lParentFrameBounds.y + (lParentFrameBounds.height) / 2);
            setLocation(lScreenCenter.x - ((int) getPreferredSize().getWidth() / 2),
                    lScreenCenter.y - ((int) getPreferredSize().getHeight()) / 2);

            // Disable the parent frame
            parentFrame.setEnabled(false);
        } else {
            // Enable the parent frame
            parentFrame.setEnabled(true);
            // Bring it back to front
            parentFrame.toFront();
            parentFrame.repaint();
        }
    }

    /**
     * Update the title.
     * 
     * @param pTitle the title to set.
     */
    public void setTitle(final String pTitle) {
        if (executionPanel != null) {
            executionPanel.setTitle(pTitle);
        }
    }

    /**
     * Update the panel from the given executor execution status.
     * 
     * @param pExecutorExecutionStatus the executor execution status to reflect.
     */
    public void updateFrom(final ExecutorExecutionStatus<?> pExecutorExecutionStatus) {
        if (executionPanel != null) {
            executionPanel.updateFrom(pExecutorExecutionStatus);
        }
    }

    /**
     * The the current execution status.
     * 
     * @param pExecutionStatus the current execution status.
     */
    public void setExecutionStatus(final AbstractExecutionStatus pExecutionStatus) {
        if (executionPanel != null) {
            executionPanel.setExecutionStatus(pExecutionStatus);
        }
    }

    /**
     * The the execution result.
     * 
     * @param pExecutionResult the execution result.
     */
    public void setExecutionResult(final String pExecutionResult) {
        if (executionPanel != null) {
            executionPanel.setExecutionResult(pExecutionResult);
        }
    }
}
