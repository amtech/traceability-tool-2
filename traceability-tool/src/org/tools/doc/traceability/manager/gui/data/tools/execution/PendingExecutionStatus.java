/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.tools.execution;

/**
 * Status for pending execution status.
 * 
 * @author Yann Leglise
 *
 */
public class PendingExecutionStatus extends AbstractExecutionStatus {

    /**
     * Constructor.
     */
    public PendingExecutionStatus() {
        super(ExecutionStatusType.Pending, null);
    }
}
