/**
 * 
 */
package org.tools.doc.traceability.common.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.tools.doc.traceability.common.exceptions.AbstractTraceabilityException;

/**
 * Common superclass for executors.
 * 
 * @param <R> the type of the object set in the {@link ExecutorExecutionStatus}
 * as a result of the execution (if the executor produce no result,
 * {@link VoidExecutionResultObject} can be used).
 * @author Yann Leglise
 *
 */
public abstract class AbstractExecutor<R extends AbtsractExecutionResultObject> implements Runnable {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LogManager.getLogger(AbstractExecutor.class);

    /**
     * The execution status.
     */
    private final ExecutorExecutionStatus<R> executionStatus;

    /**
     * The optional description for the successful status details.
     */
    private String successfulStatusDetails;

    /**
     * The execution result. Depends on each executor.
     */
    private R executionResult;

    /**
     * The current progression percentage.
     */
    private double currentPercentage;

    /**
     * Set if this executor runs as a sub-executor, and needs to update the
     * parent executor status.
     */
    private AbstractExecutor<? extends AbtsractExecutionResultObject> parentExecutor;

    /**
     * The context for this instance as sub-executor.
     */
    private SubExecutorContext contextAsSubExecutor;

    /**
     * Constructor.
     * 
     * @param pExecutionStatus the execution status.
     */
    public AbstractExecutor(final ExecutorExecutionStatus<R> pExecutionStatus) {
        executionStatus = pExecutionStatus;
        successfulStatusDetails = "";
        executionResult = null;
        currentPercentage = 0;
        parentExecutor = null;
        contextAsSubExecutor = null;
    }

    /**
     * Runs this executor as a sub-executor of the given executor.
     * <p>
     * This allows the parent executor current operation and progress percentage
     * to be updated while this (sub)executor runs.
     * </p>
     * 
     * @param pParentExecutor the parent executor.
     * @param pFinalPercentage the final percentage to be reached for the parent
     * executor when this sub-executor ends.
     */
    public void runAsSubExecutor(final AbstractExecutor<? extends AbtsractExecutionResultObject> pParentExecutor,
            final double pFinalPercentage) {
        if (pParentExecutor == null) {
            executionStatus.handleError("Parent executor provided is null");
        } else {
            parentExecutor = pParentExecutor;
            // Keep track of the initial percentage and operation from parent
            // status
            contextAsSubExecutor = new SubExecutorContext(parentExecutor.getExecutionStatus().getCompletionPercentage(),
                    pFinalPercentage, parentExecutor.getExecutionStatus().getCurrentOperation());

            // Run normally
            try {
                run();
            } finally {
                // Make sure to unreference the attributes
                parentExecutor = null;
                contextAsSubExecutor = null;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        currentPercentage = 0;
        if (executionStatus == null) {
            LOGGER.error("Execution of " + getClass().getSimpleName()
                    + " impossible as the provided executor execution status was null");
        } else {
            try {
                // Update the execution status to PENDING
                executionStatus.handleStart();

                // Simply perform the task
                performTask();

                // Only if execution was performed without exception is the
                // execution successful
                executionStatus.handleSuccessfulExecution(successfulStatusDetails, executionResult);
            } catch (AbstractTraceabilityException ate) {
                LOGGER.error("Error during execution of " + getClass().getSimpleName() + " : " + ate.getMessage());
                executionStatus.handleException(ate);
            } catch (Exception e) {
                String lDescription = e.getMessage();

                if (lDescription == null) {
                    lDescription = "Exception of type " + e.getClass().getSimpleName();
                }

                LOGGER.error(
                        "Unexpected error during execution of " + getClass().getSimpleName() + " : " + lDescription);
                executionStatus.handleException(e);
            }
        }
    }

    /**
     * Setter of the successfulStatusDetails.
     * 
     * @param pSuccessfulStatusDetails the successfulStatusDetails to set
     */
    public void setSuccessfulStatusDetails(final String pSuccessfulStatusDetails) {
        successfulStatusDetails = pSuccessfulStatusDetails;
    }

    /**
     * Actually performs the task.
     * <p>
     * In case of success, a call to {@link #setExecutionResult(Object)} must be
     * done if a result is to be returned, and a call to
     * {@link #setSuccessfulStatusDetails(String)} can be done to add details
     * about the execution.
     * </p>
     * 
     * @throws AbstractTraceabilityException if an error occurs.
     */
    protected abstract void performTask() throws AbstractTraceabilityException;

    /**
     * Getter of the execution status.
     * 
     * @return the executionStatus
     */
    public ExecutorExecutionStatus<R> getExecutionStatus() {
        return executionStatus;
    }

    /**
     * Setter of the execution result.
     * 
     * @param pExecutionResult the executionResult to set
     */
    protected void setExecutionResult(final R pExecutionResult) {
        executionResult = pExecutionResult;
    }

    /**
     * Getter of the current percentage.
     * 
     * @return the currentPercentage
     */
    protected final double getCurrentPercentage() {
        return currentPercentage;
    }

    /**
     * Setter of the current percentage.
     * 
     * @param pCurrentPercentage the currentPercentage to set
     */
    protected final void setCurrentPercentage(final double pCurrentPercentage) {
        currentPercentage = pCurrentPercentage;
        updateParentExecutorStatusIfRelevant();
    }

    /**
     * Update the current operation, using the currently set percentage.
     * 
     * @param pCurrentOperation description of the current operation.
     */
    protected final void setCurrentOperation(final String pCurrentOperation) {
        getExecutionStatus().setCurrentOperation(pCurrentOperation, currentPercentage);
        updateParentExecutorStatusIfRelevant();
    }

    /**
     * Update the current operation using the given percentage.
     * 
     * @param pCurrentOperation the description of the current operation.
     * @param pCurrentPercentage the current percentage to set.
     */
    protected final void setCurrentOperation(final String pCurrentOperation, final double pCurrentPercentage) {
        setCurrentPercentage(pCurrentPercentage);
        setCurrentOperation(pCurrentOperation);
        updateParentExecutorStatusIfRelevant();
    }

    /**
     * If there is a parent executor (i.e. this executor runs as a
     * sub-executor), updates the parent executor current operation and progress
     * percentage from this.
     */
    private void updateParentExecutorStatusIfRelevant() {
        if (parentExecutor != null) {
            if (contextAsSubExecutor != null) {
                contextAsSubExecutor.updateFrom(executionStatus);
                // Update parent executor status
                parentExecutor.setCurrentOperation(contextAsSubExecutor.getCurrentStepDescription(),
                        contextAsSubExecutor.getCurrentPercentage());
            }
        }
    }
}
