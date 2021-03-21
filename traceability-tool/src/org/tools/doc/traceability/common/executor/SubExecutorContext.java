/**
 * 
 */
package org.tools.doc.traceability.common.executor;

/**
 * Models the context for executing a sub executor.
 * <p>
 * We have the progress percentage to consider when the sub executor is
 * starting, and the progress percentage when it has ended. We also have the
 * current step description of the root executor.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class SubExecutorContext {

    /**
     * The percentage at the beginning of the sub-executor execution.
     */
    private final double initialPercentage;

    /**
     * The percentage at the end of the sub-executor execution.
     */
    private final double finalPercentage;

    /**
     * The description of the main step in the root executor.
     */
    private final String mainStepDescription;

    /**
     * The current progression percentage for the root executor.
     */
    private double currentPercentage;

    /**
     * The current step description for the root executor.
     */
    private String currentStepDescription;

    /**
     * Constructor.
     * 
     * @param pInitialPercentage The percentage at the beginning of the
     * sub-executor execution.
     * @param pFinalPercentage The percentage at the end of the sub-executor
     * execution.
     * @param pMainStepDescription The description of the main step in the root
     * executor.
     */
    public SubExecutorContext(final double pInitialPercentage, final double pFinalPercentage,
            final String pMainStepDescription) {
        super();
        double lInitialPercentage = pInitialPercentage;

        // Make sure the initial percentage is between 0 and 100
        if (lInitialPercentage < 0.0) {
            lInitialPercentage = 0.0;
        } else if (100.0 < lInitialPercentage) {
            lInitialPercentage = 100.0;
        }

        double lFinalPercentage = pFinalPercentage;
        // Make sure the final percentage is between 0 and 100
        if (lFinalPercentage < 0.0) {
            lFinalPercentage = 0.0;
        } else if (100.0 < lFinalPercentage) {
            lFinalPercentage = 100.0;
        }

        // Make sure the initial percentage is smaller or equal to the final
        // percentage
        if (lFinalPercentage < lInitialPercentage) {
            lFinalPercentage = lInitialPercentage;
        }

        initialPercentage = lInitialPercentage;
        finalPercentage = lFinalPercentage;
        mainStepDescription = pMainStepDescription;

        currentPercentage = initialPercentage;
        currentStepDescription = mainStepDescription;
    }

    /**
     * Update the instance from the given sub-executor execution status.
     * 
     * @param pExecutorExecutionStatus the execution status to consider.
     */
    public void updateFrom(
            final ExecutorExecutionStatus<? extends AbtsractExecutionResultObject> pExecutorExecutionStatus) {
        currentStepDescription = mainStepDescription + "\n" + pExecutorExecutionStatus.getCurrentOperation();

        double lSubExecutorProgressPercentage = pExecutorExecutionStatus.getCompletionPercentage();
        currentPercentage = initialPercentage
                + ((finalPercentage - initialPercentage) * lSubExecutorProgressPercentage) / 100.;
    }

    /**
     * Getter of the current execution percentage for the root executor.
     * 
     * @return the currentPercentage
     */
    public double getCurrentPercentage() {
        return currentPercentage;
    }

    /**
     * Getter of the current step description for the root executor.
     * 
     * @return the currentStepDescription
     */
    public String getCurrentStepDescription() {
        return currentStepDescription;
    }

}
