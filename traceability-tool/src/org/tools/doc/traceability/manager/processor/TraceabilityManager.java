/**
 * 
 */
package org.tools.doc.traceability.manager.processor;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.tools.doc.traceability.analyzer.almcoverage.AlmCoverageAnalyserResult;
import org.tools.doc.traceability.analyzer.almcoverage.AlmCoverageAnalyzer;
import org.tools.doc.traceability.analyzer.cucumbertests.CucumberTestCoverageAnalyser;
import org.tools.doc.traceability.analyzer.cucumbertests.CucumberTestCoverageAnalyzerResult;
import org.tools.doc.traceability.analyzer.justifircation.JustificationFileAnalyzer;
import org.tools.doc.traceability.analyzer.justifircation.JustificationFileAnalyzerResult;
import org.tools.doc.traceability.analyzer.justifircation.model.NotCoveredRequirementJustification;
import org.tools.doc.traceability.analyzer.unittests.csharp.CSharpUnitTestCoverageAnalyserResult;
import org.tools.doc.traceability.analyzer.unittests.csharp.CSharpUnitTestCoverageAnalyzer;
import org.tools.doc.traceability.analyzer.unittests.java.JavaUnitTestCoverageAnalyser;
import org.tools.doc.traceability.analyzer.unittests.java.JavaUnitTestCoverageAnalyserResult;
import org.tools.doc.traceability.common.exceptions.AbstractTraceabilityException;
import org.tools.doc.traceability.common.exceptions.ExecutorExecutionException;
import org.tools.doc.traceability.common.exceptions.InvalidParameterException;
import org.tools.doc.traceability.common.exceptions.InvalidTraceabilityManagerContextException;
import org.tools.doc.traceability.common.executor.AbstractExecutor;
import org.tools.doc.traceability.common.executor.ExecutionStatus;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.filesearch.FileSearchFilterSet;
import org.tools.doc.traceability.common.model.TestSet;
import org.tools.doc.traceability.covmatrixgen.CoverageMatrixGenerator;
import org.tools.doc.traceability.covmatrixgen.CoverageMatrixGeneratorResultObject;
import org.tools.doc.traceability.reqextraction.RequirementExtractor;
import org.tools.doc.traceability.reqextraction.RequirementExtractorResult;
import org.tools.doc.traceability.vtpupdator.VtpUpdater;
import org.tools.doc.traceability.vtpupdator.VtpUpdaterResult;

/**
 * Central traceability manager.
 * 
 * @author Yann Leglise
 *
 */
public class TraceabilityManager extends AbstractExecutor<TraceabilityManagerResultObject> {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LogManager.getLogger(TraceabilityManager.class);

    /**
     * The traceability context to work on.
     */
    private final TraceabilityManagerContext context;

    /**
     * The result of SD requirement extractor.
     */
    private RequirementExtractorResult sdFilesRequirementExtractorResult;

    /**
     * The result of ALM coverage result.
     */
    private AlmCoverageAnalyserResult almCoverageAnalyserResult;

    /**
     * The result of justification file analysis result.
     */
    private JustificationFileAnalyzerResult justificationFileAnalyzerResult;

    /**
     * The result of cucumber coverage result.
     */
    private CucumberTestCoverageAnalyzerResult cucumberCoverageAnalyserResult;

    /**
     * The result of C# unit tests coverage result.
     */
    private CSharpUnitTestCoverageAnalyserResult cSharpUnitTestsCoverageAnalyserResult;

    /**
     * The result of java unit tests coverage result.
     */
    private JavaUnitTestCoverageAnalyserResult javaUnitTestsCoverageAnalyserResult;

    /**
     * The result of VTP updater.
     */
    private VtpUpdaterResult vtpUpdaterResult;

    /**
     * The execution result of this tool.
     */
    private TraceabilityManagerResultObject executionResult;

    /**
     * Constructor.
     * 
     * @param pContext the context containing all the needed information to
     * handle the traceability work.
     * @param pExecutionStatus the execution status
     */
    public TraceabilityManager(final TraceabilityManagerContext pContext,
            final ExecutorExecutionStatus<TraceabilityManagerResultObject> pExecutionStatus) {
        super(pExecutionStatus);
        context = pContext;

        sdFilesRequirementExtractorResult = null;
        almCoverageAnalyserResult = null;
        justificationFileAnalyzerResult = null;
        cucumberCoverageAnalyserResult = null;
        cSharpUnitTestsCoverageAnalyserResult = null;
        javaUnitTestsCoverageAnalyserResult = null;
        vtpUpdaterResult = null;

        executionResult = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performTask() throws AbstractTraceabilityException {

        LOGGER.info("Start of Traceability Manager with context " + context);

        // First check the arguments
        checkArguments();

        executionResult = new TraceabilityManagerResultObject();

        // Extract requirements from SD
        extractSdRequirements();

        // Extract the justifications for requirements that are not covered by
        // tests
        extractJustifications();

        // Extract ALM tests (if any)
        extractAlmTests();

        // Extract cucumber tests (if any)
        extractCucumberTests();

        // Extract C# unit tests (if any)
        extractCSharpUnitTests();

        // Extract java unit tests (if any)
        extractJavaUnitTests();

        // Update the VTP file
        updateVtp();

        // Generate the coverage matrix
        generateCoverageMatrix();

        // Set the execution object
        setExecutionResult(executionResult);

        LOGGER.info("End of Traceability Manager");
    }

    /**
     * Generates the coverage matrix.
     * 
     * @throws ExecutorExecutionException if an error occurs.
     */
    private void generateCoverageMatrix() throws ExecutorExecutionException {
        setCurrentOperation("Generating traceability matrix", 80);

        File lCoveringMatrixFile = context.getOutputTraceabilityMatrixFile();

        ExecutorExecutionStatus<CoverageMatrixGeneratorResultObject> lExecutionStatus = new ExecutorExecutionStatus<CoverageMatrixGeneratorResultObject>();

        List<TestSet> lAlmTestSetList = vtpUpdaterResult.getAlmCoveringTestSetList();
        List<TestSet> lCucumberTestSetList = vtpUpdaterResult.getCucumberCoveringTestSetList();
        List<TestSet> lCSharpTestSetList = vtpUpdaterResult.getcSharpCoveringTestSetList();
        List<TestSet> lJavaTestSetList = vtpUpdaterResult.getJavaCoveringTestSetList();

        List<NotCoveredRequirementJustification> lNotCoveredReqJustificationList = null;
        if (justificationFileAnalyzerResult != null) {
            lNotCoveredReqJustificationList = justificationFileAnalyzerResult
                    .getNotCoveredRequirementJustificationList();
        }

        CoverageMatrixGenerator lCoverageMatrixGenerator = new CoverageMatrixGenerator(
                sdFilesRequirementExtractorResult.getAllRequirements(), lAlmTestSetList, lCucumberTestSetList,
                lCSharpTestSetList, lJavaTestSetList, lNotCoveredReqJustificationList, lCoveringMatrixFile,
                lExecutionStatus);

        lCoverageMatrixGenerator.runAsSubExecutor(this, 100);

        if (lExecutionStatus.getCurrentExecutionStatus() == ExecutionStatus.ENDED_SUCCESS) {
            CoverageMatrixGeneratorResultObject lCoverageMatrixGenerationResultObject = lExecutionStatus
                    .getExecutionResult();

            // Store in the execution result requirement test covering
            executionResult.setRequirementTestCovering(lCoverageMatrixGenerationResultObject
                    .getRequirementTestCovering());
            // Also add the list of not covered requirements
            executionResult.setNotCoveredRequirementList(lCoverageMatrixGenerationResultObject
                    .getNotCoveredRequirementList());

            LOGGER.info("Successful generation of the covering matrix " + lCoveringMatrixFile.getAbsolutePath());
        } else {
            LOGGER.error("Error generating covering matrix file " + lCoveringMatrixFile.getAbsolutePath()
                    + " status : " + lExecutionStatus.getCurrentExecutionStatus().getDescription() + " ("
                    + lExecutionStatus.getExecutionStatusDescription() + ")");
            throw new ExecutorExecutionException("Failed to generate the covering matrix file ("
                    + lExecutionStatus.getExecutionStatusDescription() + ")");
        }

    }

    /**
     * Update the VTP file with all the covering tests.
     * 
     * @throws ExecutorExecutionException if an error occurs.
     */
    private void updateVtp() throws ExecutorExecutionException {
        setCurrentOperation("Updating VTP", 70);

        ExecutorExecutionStatus<VtpUpdaterResult> lExecutionStatus = new ExecutorExecutionStatus<VtpUpdaterResult>();

        File lVtpFile = context.getOutputVtpFile();

        VtpUpdater lVtpUpdater = new VtpUpdater(lVtpFile, almCoverageAnalyserResult, cucumberCoverageAnalyserResult,
                cSharpUnitTestsCoverageAnalyserResult, javaUnitTestsCoverageAnalyserResult, lExecutionStatus);

        lVtpUpdater.runAsSubExecutor(this, 80);

        if (lExecutionStatus.getCurrentExecutionStatus() == ExecutionStatus.ENDED_SUCCESS) {
            vtpUpdaterResult = lExecutionStatus.getExecutionResult();

            LOGGER.info("Successful updating of VTP file " + lVtpFile.getAbsolutePath());
        } else {
            LOGGER.error("Error updating VTP file " + lVtpFile.getAbsolutePath() + " status : "
                    + lExecutionStatus.getCurrentExecutionStatus().getDescription() + " ("
                    + lExecutionStatus.getExecutionStatusDescription() + ")");
            throw new ExecutorExecutionException("Failed to update VTP file ("
                    + lExecutionStatus.getExecutionStatusDescription() + ")");
        }
    }

    /**
     * Extract the java unit tests (if any).
     * 
     * @throws ExecutorExecutionException if an error occurs.
     */
    private void extractJavaUnitTests() throws ExecutorExecutionException {

        setCurrentOperation("Extracting java unit tests coverage", 60);

        FileSearchFilterSet lFileSearchFilterSet = context.getJavaFileSearchFilterList();

        if (lFileSearchFilterSet.isEmpty()) {
            LOGGER.info("No java unit tests");
        } else {
            LOGGER.info("Extracting java unit tests from file filter " + lFileSearchFilterSet.toString());

            ExecutorExecutionStatus<JavaUnitTestCoverageAnalyserResult> lExecutionStatus = new ExecutorExecutionStatus<JavaUnitTestCoverageAnalyserResult>();
            JavaUnitTestCoverageAnalyser lJavaUnitTestCoverageAnalyser = new JavaUnitTestCoverageAnalyser(
                    lFileSearchFilterSet, context.getJavaMethodRegexp(), lExecutionStatus);

            lJavaUnitTestCoverageAnalyser.runAsSubExecutor(this, 70);

            if (lExecutionStatus.getCurrentExecutionStatus() == ExecutionStatus.ENDED_SUCCESS) {
                javaUnitTestsCoverageAnalyserResult = lExecutionStatus.getExecutionResult();
                LOGGER.info("Extracting java unit tests successful with "
                        + javaUnitTestsCoverageAnalyserResult.getFoundTestCount() + " tests");
            } else {
                LOGGER.error("Error extracting java unit tests : status "
                        + lExecutionStatus.getCurrentExecutionStatus().getDescription() + " ("
                        + lExecutionStatus.getExecutionStatusDescription() + ")");
                throw new ExecutorExecutionException("Could not extract java unit tests ("
                        + lExecutionStatus.getExecutionStatusDescription() + ")");
            }
        }
    }

    /**
     * Extract the C# unit tests (if any).
     * 
     * @throws ExecutorExecutionException if an error occurs.
     */
    private void extractCSharpUnitTests() throws ExecutorExecutionException {
        FileSearchFilterSet lFileSearchFilterSet = context.getCSharpFileSearchFilterList();

        setCurrentOperation("Extracting C# unit tests coverage", 50);

        if (lFileSearchFilterSet.isEmpty()) {
            LOGGER.info("No C# unit tests");
        } else {
            LOGGER.info("Extracting C# unit tests from file filter " + lFileSearchFilterSet.toString());

            ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult> lExecutionStatus = new ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult>();

            CSharpUnitTestCoverageAnalyzer lCSharpUnitTestCoverageAnalyzer = new CSharpUnitTestCoverageAnalyzer(
                    lFileSearchFilterSet, context.getcSharpMethodRegexp(), lExecutionStatus);

            lCSharpUnitTestCoverageAnalyzer.runAsSubExecutor(this, 60);

            /*
             * lCSharpUnitTestCoverageAnalyzer.run();
             */

            if (lExecutionStatus.getCurrentExecutionStatus() == ExecutionStatus.ENDED_SUCCESS) {
                cSharpUnitTestsCoverageAnalyserResult = lExecutionStatus.getExecutionResult();
                LOGGER.info("Extracting C# unit tests successful with "
                        + cSharpUnitTestsCoverageAnalyserResult.getFoundTestCount() + " tests");
            } else {
                LOGGER.error("Error extracting C# unit tests : status "
                        + lExecutionStatus.getCurrentExecutionStatus().getDescription() + " ("
                        + lExecutionStatus.getExecutionStatusDescription() + ")");
                throw new ExecutorExecutionException("Could not extract C# unit tests ("
                        + lExecutionStatus.getExecutionStatusDescription() + ")");
            }
        }
    }

    /**
     * Extract cucumber tests (if any).
     * 
     * @throws ExecutorExecutionException if an error occurs.
     */
    private void extractCucumberTests() throws ExecutorExecutionException {

        setCurrentOperation("Extracting cucumber tests coverage", 40);

        FileSearchFilterSet lFileSearchFilterSet = context.getCucumberFileSearchFilterList();

        if (lFileSearchFilterSet.isEmpty()) {
            LOGGER.info("No cucumber tests");
        } else {
            LOGGER.info("Extracting cucumber tests from file filter " + lFileSearchFilterSet.toString());

            ExecutorExecutionStatus<CucumberTestCoverageAnalyzerResult> lExecutionStatus = new ExecutorExecutionStatus<CucumberTestCoverageAnalyzerResult>();

            CucumberTestCoverageAnalyser lCucumberTestCoverageAnalyser = new CucumberTestCoverageAnalyser(
                    lFileSearchFilterSet, lExecutionStatus);

            lCucumberTestCoverageAnalyser.runAsSubExecutor(this, 50);

            if (lExecutionStatus.getCurrentExecutionStatus() == ExecutionStatus.ENDED_SUCCESS) {
                cucumberCoverageAnalyserResult = lExecutionStatus.getExecutionResult();
                LOGGER.info("Extracting cucumber tests successful with "
                        + cucumberCoverageAnalyserResult.getCucumberTestsFileDataList().size() + " tests");
            } else {
                LOGGER.error("Error extracting cucumber tests : status "
                        + lExecutionStatus.getCurrentExecutionStatus().getDescription() + " ("
                        + lExecutionStatus.getExecutionStatusDescription() + ")");
                throw new ExecutorExecutionException("Could not extract cucumber tests ("
                        + lExecutionStatus.getExecutionStatusDescription() + ")");
            }
        }
    }

    /**
     * Extract the tests from ALM (if any).
     * 
     * @throws ExecutorExecutionException if an error occurs.
     */
    private void extractAlmTests() throws ExecutorExecutionException {

        setCurrentOperation("Extracting ALM tests coverage", 30);
        // Only proceed if an ALM file is defined
        File lAlmTestFile = context.getAlmTestsExtractFile();

        if (lAlmTestFile == null) {
            LOGGER.info("No ALM tests");
        } else {
            LOGGER.info("Extrating ALM tests from " + lAlmTestFile.getAbsolutePath());

            ExecutorExecutionStatus<AlmCoverageAnalyserResult> lExecutionStatus = new ExecutorExecutionStatus<AlmCoverageAnalyserResult>();

            AlmCoverageAnalyzer lAlmCoverageAnalyzer = new AlmCoverageAnalyzer(lAlmTestFile, lExecutionStatus);

            lAlmCoverageAnalyzer.runAsSubExecutor(this, 40);

            if (lExecutionStatus.getCurrentExecutionStatus() == ExecutionStatus.ENDED_SUCCESS) {
                almCoverageAnalyserResult = lExecutionStatus.getExecutionResult();
                LOGGER.info("Extracting ALM tests successful with "
                        + almCoverageAnalyserResult.getAlmStepTestDataList().size() + " tests");
            } else {
                LOGGER.error("Error extracting ALM tests : status "
                        + lExecutionStatus.getCurrentExecutionStatus().getDescription() + " ("
                        + lExecutionStatus.getExecutionStatusDescription() + ")");
                throw new ExecutorExecutionException("Could not extract ALM tests ("
                        + lExecutionStatus.getExecutionStatusDescription() + ")");
            }
        }
    }

    /**
     * Extract the justifications for requirements that are not covered by
     * tests.
     * 
     * @throws ExecutorExecutionException if an error occurs.
     */
    private void extractJustifications() throws ExecutorExecutionException {

        setCurrentOperation("Extracting not covered requirements justification", 15);
        File lJustificationFile = context.getJustificationFile();

        if (lJustificationFile == null) {
            LOGGER.info("No justification file to process");
        } else {
            LOGGER.info("Extracting justifications from file " + lJustificationFile.getAbsolutePath());

            ExecutorExecutionStatus<JustificationFileAnalyzerResult> lExecutionStatus = new ExecutorExecutionStatus<JustificationFileAnalyzerResult>();

            JustificationFileAnalyzer lJustificationFileAnalyzer = new JustificationFileAnalyzer(
                    context.getJustificationFile(), lExecutionStatus);

            lJustificationFileAnalyzer.runAsSubExecutor(this, 30);

            if (lExecutionStatus.getCurrentExecutionStatus() == ExecutionStatus.ENDED_SUCCESS) {
                justificationFileAnalyzerResult = lExecutionStatus.getExecutionResult();
                LOGGER.info("Extracting justifications successful with "
                        + justificationFileAnalyzerResult.getNotCoveredRequirementJustificationList().size()
                        + " justifications");
            } else {
                LOGGER.error("Error extracting justifications : status "
                        + lExecutionStatus.getCurrentExecutionStatus().getDescription() + " ("
                        + lExecutionStatus.getExecutionStatusDescription() + ")");
                throw new ExecutorExecutionException("Could not extract justifications ("
                        + lExecutionStatus.getExecutionStatusDescription() + ")");
            }
        }
    }

    /**
     * Extract requirements from the SD files.
     * 
     * @throws ExecutorExecutionException if an error occurs.
     */
    private void extractSdRequirements() throws ExecutorExecutionException {
        ExecutorExecutionStatus<RequirementExtractorResult> lExecutionStatus = new ExecutorExecutionStatus<RequirementExtractorResult>();
        RequirementExtractor lRequirementExtractor = new RequirementExtractor(context.getSdFileList(),
                context.getOutputExtractedRequirementsFile(), true, context.getRequirementPrefixList(),
                lExecutionStatus);

        LOGGER.info("Extracting SD requirements");
        setCurrentOperation("Extracting SD requirements", 5);

        lRequirementExtractor.run();

        if (lExecutionStatus.getCurrentExecutionStatus() == ExecutionStatus.ENDED_SUCCESS) {
            sdFilesRequirementExtractorResult = lExecutionStatus.getExecutionResult();

            // Store in the execution result the map between SD and requirements
            executionResult.setSdFileReqExtractionResultMap(sdFilesRequirementExtractorResult
                    .getSdFileReqExtractionResultMap());
            // Also add the list of duplicated requirements
            executionResult.setDuplicatedRequirements(sdFilesRequirementExtractorResult.getDuplicatedRequirements());

            LOGGER.info("Extraction of SD requirements successful with "
                    + sdFilesRequirementExtractorResult.getRequirementCount() + " requirements");
        } else {
            LOGGER.error("Error extracting SD requirements : status "
                    + lExecutionStatus.getCurrentExecutionStatus().getDescription() + " ("
                    + lExecutionStatus.getExecutionStatusDescription() + ")");
            throw new ExecutorExecutionException("Could not extract SD requirements ("
                    + lExecutionStatus.getExecutionStatusDescription() + ")");
        }
    }

    /**
     * Checks the provided arguments and throw an exception if one is invalid.
     * 
     * @throws InvalidParameterException if an argument is invalid.
     */
    private void checkArguments() throws InvalidParameterException {

        final String lExceptionLocation = "TraceabilityManager.checkArgument";

        setCurrentOperation("Checking arguments", 5.0);

        if (context == null) {
            throw new InvalidParameterException(lExceptionLocation, "Traceability manager context", "null");
        } else {
            try {
                context.checkValidity();
            } catch (InvalidTraceabilityManagerContextException e) {
                throw new InvalidParameterException(lExceptionLocation, "Traceability manager context", e.getMessage());
            }

        }
    }
}
