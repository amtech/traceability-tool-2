/**
 * 
 */
package org.tools.doc.traceability.common.executor;

/**
 * Gives the status of execution for the executor.
 * 
 * @param <R> the type of the result of the execution (if the executor produce
 *            no result, {@link VoidExecutionResultObject} can be used).
 * @author Yann Leglise
 *
 */
public class ExecutorExecutionStatus<R extends AbtsractExecutionResultObject> {

    /**
     * The current execution status.
     */
    private ExecutionStatus currentExecutionStatus;

    /**
     * A value between 0.0 and 100.0 giving the percentage of completion.
     */
    private double completionPercentage;

    /**
     * The description of the current operation.
     */
    private String currentOperation;

    /**
     * The description of the execution status. May contain the error description if
     * the execution ended with an error.
     */
    private String executionStatusDescription;

    /**
     * An execution result, available if the status is
     * {@link ExecutionStatus#ENDED_SUCCESS}. The actual type of the object shall be
     * mentioned in the documentation of the executor.
     */
    private R executionResult;

    /**
     * Constructor.
     */
    public ExecutorExecutionStatus() {
        currentExecutionStatus = ExecutionStatus.NOT_STARTED;
        completionPercentage = 0.;
        currentOperation = "";
        executionStatusDescription = "";
        executionResult = null;
    }

    /**
     * Getter of the currentExecutionStatus.
     * 
     * @return the currentExecutionStatus
     */
    public ExecutionStatus getCurrentExecutionStatus() {
        return currentExecutionStatus;
    }

    /**
     * Getter of the completionPercentage.
     * 
     * @return the completionPercentage
     */
    public double getCompletionPercentage() {
        return completionPercentage;
    }

    /**
     * Getter of the currentOperation.
     * 
     * @return the currentOperation
     */
    public String getCurrentOperation() {
        return currentOperation;
    }

    /**
     * Getter of the executionStatusDescription.
     * 
     * @return the executionStatusDescription
     */
    public String getExecutionStatusDescription() {
        return executionStatusDescription;
    }

    /**
     * Mark the execution status to PENDING.
     */
    public void handleStart() {
        currentExecutionStatus = ExecutionStatus.PENDING;
    }

    /**
     * Sets the status to KO and extract the execution status description from the
     * exception.
     * 
     * @param pException the raised exception.
     */
    public void handleException(final Exception pException) {
        String lMessage = pException.getMessage();
        if (lMessage == null) {
            handleError("No message from " + pException.getClass().getSimpleName());
        } else {
            handleError(pException.getMessage());
        }
    }

    /**
     * Sets the status to KO and extract the execution status description from the
     * exception.
     * 
     * @param pError the error description.
     */
    public void handleError(final String pError) {
        executionStatusDescription = "An error occurred : " + pError;
        currentExecutionStatus = ExecutionStatus.ENDED_WITH_ERROR;
    }

    /**
     * Sets the status to OK and set the execution status description with the given
     * details.
     * 
     * @param pStatusDetails   the execution details.
     * @param pExecutionResult the execution result.
     */
    public void handleSuccessfulExecution(final String pStatusDetails, final R pExecutionResult) {
        executionStatusDescription = "Execution successful : " + pStatusDetails;
        currentExecutionStatus = ExecutionStatus.ENDED_SUCCESS;
        currentOperation = "None (Execution finished)";
        completionPercentage = 100.;
        executionResult = pExecutionResult;
    }

    /**
     * Set both the current operation label and the execution percentage.
     * 
     * @param pCurrentOperation  the description of the current operation.
     * @param pCurrentPercentage the execution percentage.
     */
    public void setCurrentOperation(final String pCurrentOperation, final double pCurrentPercentage) {
        currentOperation = pCurrentOperation;
        completionPercentage = pCurrentPercentage;
    }

    /**
     * Getter of the execution result.
     * 
     * @return the executionResult
     */
    public R getExecutionResult() {
        return executionResult;
    }

}
