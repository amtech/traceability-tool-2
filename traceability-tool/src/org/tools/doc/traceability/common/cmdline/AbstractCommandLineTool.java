/**
 * 
 */
package org.tools.doc.traceability.common.cmdline;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.tools.doc.traceability.common.exceptions.CommandLineArgumentException;
import org.tools.doc.traceability.common.executor.AbstractExecutor;
import org.tools.doc.traceability.common.executor.AbtsractExecutionResultObject;
import org.tools.doc.traceability.common.executor.ExecutionStatus;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;

/**
 * Common frame for a command line based tool.
 * <p>
 * To be used, the tool shall declare a main. In the main method, Instantiate
 * the concrete class with as parameters the command line arguments of the main,
 * and then call {@link #execute()}, and return the return value.
 * </p>
 * 
 * @param <R> the execution result object type.
 * @author Yann Leglise
 *
 */
public abstract class AbstractCommandLineTool<R extends AbtsractExecutionResultObject> {

    /**
     * The status returned when the execution went OK.
     */
    protected static final int SUCCESSFUL_STATUS = 0;

    /**
     * The status returned when the command line arguments were wrong.
     */
    protected static final int INVALID_CMD_LINE_ARGUMENT_STATUS = 1;

    /**
     * The status returned when the execution went wrong.
     */
    protected static final int EXECUTION_ERROR_STATUS = 2;

    /**
     * The character used to separate log level and log message in progression logs.
     */
    protected static final String PROGRESSION_LOG_SEPARATOR = ">";

    /**
     * The command line options.
     */
    private Options cmdLineOptions;

    /**
     * The command line arguments.
     */
    private final String[] cmdLineArguments;

    /**
     * The executor.
     */
    private AbstractExecutor<R> executor;

    /**
     * The command line arguments.
     */
    private CommandLineArguments commandLineArguments;

    /**
     * Constructor.
     * 
     * @param pCmdLineArgs the command line arguments.
     */
    protected AbstractCommandLineTool(final String[] pCmdLineArgs) {
        cmdLineArguments = pCmdLineArgs;

        commandLineArguments = new CommandLineArguments(getToolName());

        cmdLineOptions = null;
        executor = null;
    }

    /**
     * Entry point for executing the tool.
     * 
     * @return the execution status.
     */
    public int execute() {
        int lExecutionStatus = SUCCESSFUL_STATUS;

        // Log the command line arguments
        logCommandLineArguments();

        executor = null;

        // Define the command line arguments if not done yet
        if (!commandLineArguments.isDefined()) {
            fillCommandLineArguments(commandLineArguments);
            cmdLineOptions = commandLineArguments.toOptions();
        }

        // Parse the command line
        CommandLineParser lCmdLineParser = new DefaultParser();
        try {
            // Parse the command line through the options
            CommandLine lCmdLine = lCmdLineParser.parse(cmdLineOptions, cmdLineArguments);

            // Check the basic validity
            commandLineArguments.fillAndCheckFrom(lCmdLine);

            // Let the subclass check more in details
            checkCommandLineArguments(commandLineArguments);

            // Get the executor initialized with the command line arguments
            executor = getExecutorForCommandLine(lCmdLine);

            ExecutorExecutionStatus<R> lExecutorExecutionStatus = executor.getExecutionStatus();

            // Run it in a different thread
            Thread lOperatingThread = new Thread(executor);

            try {
                lOperatingThread.start();
            } catch (Exception e) {
                logError("Could not perform the task : " + e.getMessage());
            }

            // Wait for the thread to finish
            while (true) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    // Ignore
                }

                // Write the status
                logInfo("Execution " + lExecutorExecutionStatus.getCompletionPercentage() + " % : "
                        + lExecutorExecutionStatus.getCurrentOperation());
                if (!lOperatingThread.isAlive()) {
                    break;
                }
            }

            // Wait for the thread to join
            try {
                lOperatingThread.join(1000);
            } catch (InterruptedException e) {
                logError("Thread could not join");
            }

            // Get the result object
            R lExecutionResult = lExecutorExecutionStatus.getExecutionResult();

            if (lExecutionResult != null) {
                if (lExecutorExecutionStatus.getCurrentExecutionStatus() == ExecutionStatus.ENDED_WITH_ERROR) {
                    logError("Execution finished with errors : "
                            + lExecutorExecutionStatus.getExecutionStatusDescription());
                    lExecutionStatus = EXECUTION_ERROR_STATUS;
                } else {
                    // Decide on the execution status from the execution result
                    lExecutionStatus = handleExecutionResult(lExecutionResult);
                }
            } else {
                logError("No result was obtained : " + lExecutorExecutionStatus.getExecutionStatusDescription());
                lExecutionStatus = EXECUTION_ERROR_STATUS;
            }

        } catch (ParseException pe) {
            logError("Parsing exception : " + pe.getMessage());
            lExecutionStatus = INVALID_CMD_LINE_ARGUMENT_STATUS;
            printUsage();
        } catch (CommandLineArgumentException clpe) {
            logError(clpe.getMessage());
            lExecutionStatus = INVALID_CMD_LINE_ARGUMENT_STATUS;
            printUsage();
        }

        logInfo("Leaving with status " + lExecutionStatus);

        return lExecutionStatus;
    }

    /**
     * Log the command line arguments.
     */
    private void logCommandLineArguments() {
        StringBuilder lSb = new StringBuilder();
        for (String lArg : cmdLineArguments) {
            lSb.append(" ");
            lSb.append(lArg);
        }
        logInfo("Running " + getToolName() + " with arguments : " + lSb.toString());
    }

    /**
     * Prints the command tool usage on the error output.
     */
    protected final void printUsage() {
        System.err.println(commandLineArguments.getUsage());
    }

    /**
     * Log an information.
     * 
     * @param pInfo the information to log.
     */
    protected void logInfo(final String pInfo) {
        System.out.println("INFO   " + PROGRESSION_LOG_SEPARATOR + " " + pInfo);
    }

    /**
     * Log a warning.
     * 
     * @param pWarning the warning to log.
     */
    protected void logWarning(final String pWarning) {
        System.out.println("WARN   " + PROGRESSION_LOG_SEPARATOR + " " + pWarning);
    }

    /**
     * Log an error.
     * 
     * @param pError the error to log.
     */
    protected void logError(final String pError) {
        System.out.println("ERROR " + PROGRESSION_LOG_SEPARATOR + " " + pError);
    }

    /**
     * Getter of the executor.
     * 
     * @return the executor
     */
    protected AbstractExecutor<R> getExecutor() {
        return executor;
    }

    /**
     * Gets the command line tool name.
     * 
     * @return the tool name.
     */
    protected abstract String getToolName();

    /**
     * Let the subclass check the validity of the command line arguments (other than
     * the presence of mandatory arguments and the number of provided arguments).
     * 
     * @param pCommandLineArguments the command line arguments filled from the
     *                              command line.
     * @throws CommandLineArgumentException if an argument is invalid.
     */
    protected abstract void checkCommandLineArguments(CommandLineArguments pCommandLineArguments)
            throws CommandLineArgumentException;

    /**
     * Let the concrete class return the executor initialized from the given command
     * line arguments.
     * 
     * @param pCmdLine the parsed command line.
     * @return the executor initialzed from command line arguments.
     * @throws CommandLineArgumentException if there was an error with the
     *                                      arguments.
     */
    protected abstract AbstractExecutor<R> getExecutorForCommandLine(CommandLine pCmdLine)
            throws CommandLineArgumentException;

    /**
     * Let the subclass decide on the execution status from the execution result of
     * the executor.
     * 
     * @param pExecutionResult the execution result object produced by the executor.
     * @return the execution status of the command line tool.
     */
    protected abstract int handleExecutionResult(R pExecutionResult);

    /**
     * Let the subclass define the command line arguments.
     * 
     * @param pCommandLineArguments the command line arguments to configure.
     */
    protected abstract void fillCommandLineArguments(CommandLineArguments pCommandLineArguments);

}
