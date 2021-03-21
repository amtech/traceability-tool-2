/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.tools.execution;

/**
 * Execution status with termination in error.
 * 
 * @author Yann Leglise
 *
 */
public class EndeddWithErrorExecutionStatus extends AbstractExecutionStatus {

    /**
     * Constructor.
     * 
     * @param pAdditionalInfo additional information about the error.
     */
    public EndeddWithErrorExecutionStatus(final String pAdditionalInfo) {
        super(ExecutionStatusType.EndedWithError, pAdditionalInfo);
    }
}
