/**
 * 
 */
package org.tools.doc.traceability.reqextraction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.tools.doc.traceability.common.cmdline.AbstractCommandLineArgument;
import org.tools.doc.traceability.common.cmdline.AbstractCommandLineTool;
import org.tools.doc.traceability.common.cmdline.CommandLineArguments;
import org.tools.doc.traceability.common.cmdline.FlagArgument;
import org.tools.doc.traceability.common.cmdline.ParameteredArgument;
import org.tools.doc.traceability.common.exceptions.CommandLineArgumentException;
import org.tools.doc.traceability.common.executor.AbstractExecutor;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;

/**
 * The command line version of the requirement extractor tool.
 * 
 * @author Yann Leglise
 *
 */
public class RequirementExtractorCmdLineTool extends AbstractCommandLineTool<RequirementExtractorResult> {

    /**
     * The short option for the input files.
     */
    private static final char INPUT_FILES_SHORT_OPTION = 'i';

    /**
     * The short option for the requirement prefixes.
     */
    private static final char REQUIREMENT_PREFIXES_SHORT_OPTION = 'p';

    /**
     * The short option for the output file.
     */
    private static final char OUTPUT_FILE_SHORT_OPTION = 'o';

    /**
     * The short option for the requirement sorting.
     */
    private static final char REQ_SORTING_SHORT_OPTION = 's';

    /**
     * The list of input files.
     */
    private List<File> inputFiles;

    /**
     * The list of requirement prefixes.
     */
    private List<String> requirementPrefixes;

    /**
     * The output file.
     */
    private File outputFile;

    /**
     * The flag for requirement sorting.
     */
    private boolean sortRequirements;

    /**
     * Constructor.
     * 
     * @param pCmdLineArgs the command line arguments from the main.
     */
    protected RequirementExtractorCmdLineTool(final String[] pCmdLineArgs) {
        super(pCmdLineArgs);
        inputFiles = new ArrayList<File>();
        requirementPrefixes = new ArrayList<String>();
        outputFile = null;
        sortRequirements = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getToolName() {
        return "Requirement Extractor";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractExecutor<RequirementExtractorResult> getExecutorForCommandLine(final CommandLine pCmdLine)
            throws CommandLineArgumentException {
        // Create the executor execution status
        ExecutorExecutionStatus<RequirementExtractorResult> lExecutorExecutionStatus = new ExecutorExecutionStatus<RequirementExtractorResult>();

        // Create the executor
        RequirementExtractor lReqextractor = new RequirementExtractor(inputFiles, outputFile, sortRequirements,
                requirementPrefixes, lExecutorExecutionStatus);

        return lReqextractor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int handleExecutionResult(final RequirementExtractorResult pExecutionResult) {

        int lExecutionStatus = SUCCESSFUL_STATUS;

        if (pExecutionResult == null) {
            logError("No result object obtained");
            lExecutionStatus = EXECUTION_ERROR_STATUS;
        } else {
            logInfo("Number of extracted requirements : " + pExecutionResult.getRequirementCount());
            lExecutionStatus = SUCCESSFUL_STATUS;
        }

        return lExecutionStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void checkCommandLineArguments(final CommandLineArguments pCommandLineArguments)
            throws CommandLineArgumentException {

        // Initialize parameters
        inputFiles.clear();
        requirementPrefixes.clear();
        outputFile = null;
        sortRequirements = false;

        // Check that if the option for output file is present, the output file
        // path is
        // mentioned
        AbstractCommandLineArgument lArg = pCommandLineArguments.getArgumentForShortOption(OUTPUT_FILE_SHORT_OPTION);
        if (lArg.isPresent()) {
            // Set the output file
            outputFile = new File(lArg.getArgumentParameters().get(0));
        }

        // Fill the list of input files
        lArg = pCommandLineArguments.getArgumentForShortOption(INPUT_FILES_SHORT_OPTION);
        for (String lInputFilePath : lArg.getArgumentParameters()) {
            inputFiles.add(new File(lInputFilePath));
        }

        // If the list of requirement prefixes is mentioned, use them
        lArg = pCommandLineArguments.getArgumentForShortOption(REQUIREMENT_PREFIXES_SHORT_OPTION);
        if (lArg.isPresent()) {
            for (String lPrefix : lArg.getArgumentParameters()) {
                requirementPrefixes.add(lPrefix);
            }
        } else {
            // Error
            throw new CommandLineArgumentException(getToolName(), "requirement-prefixes", "empty");
        }

        // Set the sorting option
        lArg = pCommandLineArguments.getArgumentForShortOption(REQ_SORTING_SHORT_OPTION);
        if (lArg.isPresent()) {
            sortRequirements = true;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillCommandLineArguments(final CommandLineArguments pCommandLineArguments) {
        pCommandLineArguments.addArgument(new ParameteredArgument(INPUT_FILES_SHORT_OPTION, "input-files", true,
                "the input files", 1, AbstractCommandLineArgument.UNLIMITED_NUMBER_OF_PARAMETERS,
                "the list of input files to parse"));

        pCommandLineArguments.addArgument(new ParameteredArgument(OUTPUT_FILE_SHORT_OPTION, "output-file", true,
                "the output file", 1, 1, "the file where to write the extracted requirements"));

        pCommandLineArguments.addArgument(new ParameteredArgument(REQUIREMENT_PREFIXES_SHORT_OPTION,
                "requirement-prefixes", false, "the requirement prefixes", 1,
                AbstractCommandLineArgument.UNLIMITED_NUMBER_OF_PARAMETERS,
                "the list of prefixes a requirement must start with to be taken into account"));

        pCommandLineArguments.addArgument(new FlagArgument(REQ_SORTING_SHORT_OPTION, "sort-reqs",
                "makes the requirements sorted alphabetically in the output file"));

    }

    /**
     * Entry point.
     * 
     * @param pArgs the command line arguments
     */
    public static void main(final String[] pArgs) {
        RequirementExtractorCmdLineTool lCmdLineTool = new RequirementExtractorCmdLineTool(pArgs);

        int lStatus = lCmdLineTool.execute();

        System.exit(lStatus);
    }

}
