/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.tools.execution;

/**
 * Execution status for successful execution.
 * 
 * @author Yann Leglise
 *
 */
public class EndedSuccessfullyExecutionStatus extends AbstractExecutionStatus {

    /**
     * Constructor.
     * 
     * @param pAdditionalInfo additional information about the execution.
     */
    public EndedSuccessfullyExecutionStatus(final String pAdditionalInfo) {
        super(ExecutionStatusType.EndedSuccessfully, pAdditionalInfo);
    }
}
