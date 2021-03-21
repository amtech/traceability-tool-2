/**
 * 
 */
package org.tools.doc.traceability.manager.cmdline;

import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tools.doc.traceability.analyzer.almcoverage.AlmCoverageAnalyzer;
import org.tools.doc.traceability.common.cmdline.AbstractCommandLineTool;
import org.tools.doc.traceability.common.cmdline.CommandLineArguments;
import org.tools.doc.traceability.common.exceptions.CommandLineArgumentException;
import org.tools.doc.traceability.common.executor.AbstractExecutor;
import org.tools.doc.traceability.manager.processor.TraceabilityManagerResultObject;

/**
 * The command line version of the traceability manager tool.
 * 
 * <p>
 * This tool can be launched with two possible sets of parameters.
 * <ol>
 * <li>First set is based on the application configuration file and the name of
 * the application to target. Optionally it is possible to override the value
 * for the base Git directory and the JogetPlugin level presence flag.
 * <li>Second set is specifying all the needed information.
 * </ol>
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class TraceabilityManagerCmdLineTool extends AbstractCommandLineTool<TraceabilityManagerResultObject> {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LogManager.getLogger(TraceabilityManagerCmdLineTool.class);

    ///////////////////////////////////////////////////////////////////////////
    // First set of options based on the application configuration file
    ///////////////////////////////////////////////////////////////////////////

    /**
     * The short option for specifying the path of the application configuration
     * file.
     */
    private static final char CONFIG_FILE_PATH_SHORT_OPTION = 'p';

    /**
     * The short option for name of the application to consider in the
     * application configuration file.
     */
    private static final char APP_NAME_IN_CONFIG_FILE_SHORT_OPTION = 'n';

    /**
     * The short option for specifying the Git base directory path.
     */
    private static final char GIT_BASE_DIR_PATH_SHORT_OPTION = 'g';

    /**
     * The short option for indicating that the JogetPlugin level is present.
     * <p>
     * If not mentioned, it is supposed not to be there.
     * </p>
     */
    private static final char JOGETPLUGIN_LEVEL_PRESENT_SHORT_OPTION = 'l';

    /**
     * Constructor.
     * 
     * @param pCmdLineArgs the command line arguments.
     */
    protected TraceabilityManagerCmdLineTool(final String[] pCmdLineArgs) {
        super(pCmdLineArgs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getToolName() {
        return "Traceability manager";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void checkCommandLineArguments(final CommandLineArguments pCommandLineArguments)
            throws CommandLineArgumentException {
        // TODO Auto-generated method stub
        LOGGER.debug("Checking arguments");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractExecutor<TraceabilityManagerResultObject> getExecutorForCommandLine(final CommandLine pCmdLine)
            throws CommandLineArgumentException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int handleExecutionResult(final TraceabilityManagerResultObject pExecutionResult) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillCommandLineArguments(final CommandLineArguments pCommandLineArguments) {
        // TODO Auto-generated method stub

    }

}
