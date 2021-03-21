/**
 * 
 */
package org.tools.doc.traceability.vtpupdator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.tools.doc.traceability.analyzer.almcoverage.AlmCoverageAnalyserResult;
import org.tools.doc.traceability.analyzer.almcoverage.model.AlmStepTestData;
import org.tools.doc.traceability.analyzer.cucumbertests.CucumberTestCoverageAnalyzerResult;
import org.tools.doc.traceability.analyzer.cucumbertests.model.CucumberTestData;
import org.tools.doc.traceability.analyzer.cucumbertests.model.CucumberTestsFileData;
import org.tools.doc.traceability.analyzer.unittests.common.UnitTestCaseData;
import org.tools.doc.traceability.analyzer.unittests.csharp.CSharpUnitTestCoverageAnalyserResult;
import org.tools.doc.traceability.analyzer.unittests.csharp.model.CSharpUnitTestFileData;
import org.tools.doc.traceability.analyzer.unittests.java.JavaUnitTestCoverageAnalyserResult;
import org.tools.doc.traceability.analyzer.unittests.java.model.JavaUnitTestFileData;
import org.tools.doc.traceability.common.Constants;
import org.tools.doc.traceability.common.exceptions.AbstractTraceabilityException;
import org.tools.doc.traceability.common.exceptions.ExecutorExecutionException;
import org.tools.doc.traceability.common.exceptions.InvalidParameterException;
import org.tools.doc.traceability.common.executor.AbstractExecutor;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.model.TestSetType;
import org.tools.doc.traceability.common.model.TestCaseData;
import org.tools.doc.traceability.common.model.TestSet;
import org.tools.doc.traceability.common.model.Requirement;
import org.tools.doc.traceability.vtpupdator.helper.VtpWorkbookStyle;

/**
 * Tool for updating a VTP from tests of any kind.
 * 
 * @author Yann Leglise
 *
 */
public class VtpUpdater extends AbstractExecutor<VtpUpdaterResult> {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LogManager.getLogger(VtpUpdater.class);

    /**
     * The target VTP file to update.
     */
    private final File targetVtpFile;

    /**
     * The result of ALM coverage analysis.
     */
    private final AlmCoverageAnalyserResult almCoverageAnalyzerResult;

    /**
     * The result of cucumber coverage analysis.
     */
    private final CucumberTestCoverageAnalyzerResult cucumberTestCoverageAnalysisResult;

    /**
     * The result of C# unit test coverage analysis.
     */
    private final CSharpUnitTestCoverageAnalyserResult cSharpUnitTestCoverageAnalysisResult;

    /**
     * The result of Java unit test coverage analysis.
     */
    private final JavaUnitTestCoverageAnalyserResult javaUnitTestCoverageAnalysisResult;

    /**
     * The list of ALM covering test set.
     */
    private final List<TestSet> almCoveringTestSetList;

    /**
     * The list of cucumber covering test set.
     */
    private final List<TestSet> cucumberCoveringTestSetList;

    /**
     * The list of C# unit test set.
     */
    private final List<TestSet> cSharpCoveringTestSetList;

    /**
     * The list of java unit test set.
     */
    private final List<TestSet> javaCoveringTestSetList;

    /**
     * The style to use in the VTP workbook.
     */
    private VtpWorkbookStyle vtpWorkbookStyle;

    /**
     * The execution result object.
     */
    private VtpUpdaterResult executionResult;

    /**
     * The pattern for searching for covered requirements in "Expected Result"
     * part of ALM test.
     */
    private Pattern expectedResultWithReqPattern;

    /**
     * Constructor.
     * 
     * @param pTargetVtpFile the target VTP file to update.
     * @param pAlmCoverageAnalyzerResult the result of ALM coverage analysis
     * (can be <tt>null</tt> if none).
     * @param pCucumberTestCoverageAnalysisResult the result of cucumber
     * coverage analysis (can be <tt>null</tt> if none).
     * @param pCSharpUnitTestCoverageAnalysisResult the result of C# unit test
     * coverage analysis (can be <tt>null</tt> if none).
     * @param pJavaUnitTestCoverageAnalysisResult the result of Java unit test
     * coverage analysis (can be <tt>null</tt> if none).
     * @param pExecutionStatus the execution status.
     */
    public VtpUpdater(final File pTargetVtpFile, final AlmCoverageAnalyserResult pAlmCoverageAnalyzerResult,
            final CucumberTestCoverageAnalyzerResult pCucumberTestCoverageAnalysisResult,
            final CSharpUnitTestCoverageAnalyserResult pCSharpUnitTestCoverageAnalysisResult,
            final JavaUnitTestCoverageAnalyserResult pJavaUnitTestCoverageAnalysisResult,
            final ExecutorExecutionStatus<VtpUpdaterResult> pExecutionStatus) {
        super(pExecutionStatus);
        targetVtpFile = pTargetVtpFile;
        almCoverageAnalyzerResult = pAlmCoverageAnalyzerResult;
        cucumberTestCoverageAnalysisResult = pCucumberTestCoverageAnalysisResult;
        cSharpUnitTestCoverageAnalysisResult = pCSharpUnitTestCoverageAnalysisResult;
        javaUnitTestCoverageAnalysisResult = pJavaUnitTestCoverageAnalysisResult;

        almCoveringTestSetList = new ArrayList<TestSet>();
        cucumberCoveringTestSetList = new ArrayList<TestSet>();
        cSharpCoveringTestSetList = new ArrayList<TestSet>();
        javaCoveringTestSetList = new ArrayList<TestSet>();

        vtpWorkbookStyle = null;
        executionResult = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performTask() throws AbstractTraceabilityException {
        // First check the arguments
        checkArguments();

        try {
            expectedResultWithReqPattern = Pattern.compile(Constants.EXPECTED_RESULT_COLUMN_WITH_REQUIREMENTS_REGEXP);
        } catch (PatternSyntaxException pse) {
            throw new ExecutorExecutionException(
                    "Could not compile the regexp for finding requirements in Expected Result columns ("
                            + Constants.EXPECTED_RESULT_COLUMN_WITH_REQUIREMENTS_REGEXP + ") : " + pse.getDescription());
        }

        // Fill the covering test sets
        fillCoveringTestSets();

        // Get the contents of the target VTP file
        Workbook lVtpWorkbook = readTargetVtpFileContents();

        // Create the workbook style
        vtpWorkbookStyle = new VtpWorkbookStyle((XSSFWorkbook) lVtpWorkbook);

        // Remove all existing Sheet but the identification one
        clearAllWorkbookSheetButIndentification(lVtpWorkbook);

        // Add Sheet for ALM tests if relevant
        addAlmTestSheet(lVtpWorkbook);

        // Add Sheet for cucumber tests if relevant
        addCucumberTestSheet(lVtpWorkbook);

        // Add Sheet for C# unit tests if relevant
        addCSharpUnitTestSheet(lVtpWorkbook);

        // Add Sheet for java unit tests if relevant
        addJavaUnitTestSheet(lVtpWorkbook);

        // Write the new contents to the target VTP file
        writeVtpFile(lVtpWorkbook);

        // Update the execution result object
        executionResult = new VtpUpdaterResult();
        if (!almCoveringTestSetList.isEmpty()) {
            executionResult.setAlmCoveringTestSetList(almCoveringTestSetList);
        }
        if (!cucumberCoveringTestSetList.isEmpty()) {
            executionResult.setCucumberCoveringTestSetList(cucumberCoveringTestSetList);
        }
        if (!cSharpCoveringTestSetList.isEmpty()) {
            executionResult.setcSharpCoveringTestSetList(cSharpCoveringTestSetList);
        }
        if (!javaCoveringTestSetList.isEmpty()) {
            executionResult.setJavaCoveringTestSetList(javaCoveringTestSetList);
        }

        setExecutionResult(executionResult);

    }

    /**
     * Writes the given workbook contents into the target VTP file.
     * 
     * @param pVtpWorkbook the workbook corresponding to the updated VTP.
     * @throws ExecutorExecutionException if an error occurs writing the file
     */
    private void writeVtpFile(final Workbook pVtpWorkbook) throws ExecutorExecutionException {

        FileOutputStream lOutputFileInputStream = null;

        try {
            lOutputFileInputStream = new FileOutputStream(targetVtpFile);

            // Write the workbook to the file
            pVtpWorkbook.write(lOutputFileInputStream);
        } catch (Exception e) {
            LOGGER.error("Could not write file " + targetVtpFile.getAbsolutePath() + " : " + e.getMessage());
            throw new ExecutorExecutionException("Could not write file " + targetVtpFile.getAbsolutePath() + " : "
                    + e.getMessage());
        } finally {
            try {
                lOutputFileInputStream.close();
            } catch (Exception e) {
                LOGGER.error("Error closing file " + targetVtpFile.getAbsolutePath() + " : " + e.getMessage());
            }
        }

    }

    /**
     * Add a Sheet for ALM tests if relevant.
     * 
     * @param pVtpWorkbook the destination workbook.
     */
    private void addAlmTestSheet(final Workbook pVtpWorkbook) {
        if (!almCoveringTestSetList.isEmpty()) {
            // Add a Sheet for ALM tests with all the associated covering test
            // elements
            addSheetInWorkbook(TestSetType.AlmTest.getAssociatedVtpSheetName(), pVtpWorkbook, almCoveringTestSetList);
        }
    }

    /**
     * Add a Sheet for cucumber tests if relevant.
     * 
     * @param pVtpWorkbook the destination workbook.
     */
    private void addCucumberTestSheet(final Workbook pVtpWorkbook) {
        if (!cucumberCoveringTestSetList.isEmpty()) {
            // Add a Sheet for cucumber tests with all the associated covering
            // test elements
            addSheetInWorkbook(TestSetType.CucumberTest.getAssociatedVtpSheetName(), pVtpWorkbook,
                    cucumberCoveringTestSetList);
        }
    }

    /**
     * Add a Sheet for C# unit tests if relevant.
     * 
     * @param pVtpWorkbook the destination workbook.
     */
    private void addCSharpUnitTestSheet(final Workbook pVtpWorkbook) {
        if (!cSharpCoveringTestSetList.isEmpty()) {
            // Add a Sheet for C# unit tests with all the associated covering
            // test elements
            addSheetInWorkbook(TestSetType.CSharpUnitTest.getAssociatedVtpSheetName(), pVtpWorkbook,
                    cSharpCoveringTestSetList);
        }
    }

    /**
     * Add a Sheet for java unit tests if relevant.
     * 
     * @param pVtpWorkbook the destination workbook.
     */
    private void addJavaUnitTestSheet(final Workbook pVtpWorkbook) {
        if (!javaCoveringTestSetList.isEmpty()) {
            // Add a Sheet for java unit tests with all the associated covering
            // test elements
            addSheetInWorkbook(TestSetType.JavaUnitTest.getAssociatedVtpSheetName(), pVtpWorkbook,
                    javaCoveringTestSetList);
        }
    }

    /**
     * Add a Sheet with the given name into the workbook, and add all the
     * associated covering test data.
     * 
     * @param pSheetName the name of the sheet to add.
     * @param pVtpWorkbook the workbook where to add the Sheet.
     * @param pCoveringTestSetList the list covering test set containing the
     * data to add in this Sheet
     */
    private void addSheetInWorkbook(final String pSheetName, final Workbook pVtpWorkbook,
            final List<TestSet> pCoveringTestSetList) {

        XSSFSheet lSheet;

        lSheet = (XSSFSheet) pVtpWorkbook.createSheet(pSheetName);

        // Size the columns
        lSheet.setColumnWidth(Constants.VTPUDATER_TEST_SET_NAME_COL_IDX, Constants.VTPUDATER_TEST_SET_NAME_COL_WIDTH
                * Constants.CHARCTER_WIDTH_FACTOR);
        lSheet.setColumnWidth(Constants.VTPUDATER_TEST_CASE_IDENTIFIER_COL_IDX,
                Constants.VTPUDATER_TEST_CASE_IDENTIFIER_COL_WIDTH * Constants.CHARCTER_WIDTH_FACTOR);
        lSheet.setColumnWidth(Constants.VTPUDATER_TEST_CASE_NAME_COL_IDX, Constants.VTPUDATER_TEST_CASE_NAME_COL_WIDTH
                * Constants.CHARCTER_WIDTH_FACTOR);
        lSheet.setColumnWidth(Constants.VTPUDATER_PROCEDURE_COL_IDX, Constants.VTPUDATER_PROCEDURE_COL_WIDTH
                * Constants.CHARCTER_WIDTH_FACTOR);
        lSheet.setColumnWidth(Constants.VTPUDATER_EXPECTED_RESULT_COL_IDX,
                Constants.VTPUDATER_EXPECTED_RESULT_COL_WIDTH * Constants.CHARCTER_WIDTH_FACTOR);
        lSheet.setColumnWidth(Constants.VTPUDATER_COVERED_REQUIREMENTS_COL_IDX,
                Constants.VTPUDATER_COVERED_REQUIREMENTS_COL_WIDTH * Constants.CHARCTER_WIDTH_FACTOR);

        // Add the header row
        addHeaderRowFor(lSheet);

        // Add one line per data row
        int lRowIdx = 1;
        for (TestSet lCoveringTestSet : pCoveringTestSetList) {
            lRowIdx = addContentsRowsFor(lCoveringTestSet, lSheet, lRowIdx);
        }
    }

    /**
     * Add a contents rows from the given index in the given sheet for the given
     * covering test set.
     * 
     * @param pCoveringTestSet the covering test set to consider.
     * @param pSheet the destination sheet in which to write contents.
     * @param pRowIdx the row index where to start adding contents.
     * @return the last written row index.
     */
    private int addContentsRowsFor(final TestSet pCoveringTestSet, final XSSFSheet pSheet, final int pRowIdx) {
        int lRowIdx = pRowIdx;

        // Iterate on the test set covering test cases
        for (TestCaseData lCoveringTestCaseData : pCoveringTestSet.getTestDataList()) {
            addRowFor(pCoveringTestSet, lCoveringTestCaseData, pSheet, lRowIdx++);
        }

        return lRowIdx;
    }

    /**
     * Add a row for the given test case of the given test set.
     * <p>
     * Add it to the given sheet a the given row index.
     * </p>
     * 
     * @param pCoveringTestSet the test set to consider.
     * @param pCoveringTestCaseData the covering test case to add.
     * @param pSheet the sheet in which to write.
     * @param pRowIdx the index in which to add the new row.
     */
    private void addRowFor(final TestSet pCoveringTestSet, final TestCaseData pCoveringTestCaseData,
            final XSSFSheet pSheet, final int pRowIdx) {

        // Create the data row at the given index
        Row lHeaderRow = pSheet.createRow(pRowIdx);

        Cell lTestSetNameCell = lHeaderRow.createCell(Constants.VTPUDATER_TEST_SET_NAME_COL_IDX);
        lTestSetNameCell.setCellValue(pCoveringTestSet.getTestSetName());
        
        lTestSetNameCell.setCellStyle(vtpWorkbookStyle.getNormalStyle());

        Cell lTestCaseIdentifierCell = lHeaderRow.createCell(Constants.VTPUDATER_TEST_CASE_IDENTIFIER_COL_IDX);
        lTestCaseIdentifierCell.setCellValue(pCoveringTestCaseData.getTestCaseIdentifier());
        lTestCaseIdentifierCell.setCellStyle(vtpWorkbookStyle.getNormalStyle());

        Cell lTestCaseNameCell = lHeaderRow.createCell(Constants.VTPUDATER_TEST_CASE_NAME_COL_IDX);
        lTestCaseNameCell.setCellValue(pCoveringTestCaseData.getTestCaseName());
        lTestCaseNameCell.setCellStyle(vtpWorkbookStyle.getNormalStyle());

        Cell lProcedureDescriptionCell = lHeaderRow.createCell(Constants.VTPUDATER_PROCEDURE_COL_IDX);
        lProcedureDescriptionCell.setCellValue(pCoveringTestCaseData.getProcedureDescription());
        lProcedureDescriptionCell.setCellStyle(vtpWorkbookStyle.getNormalStyle());

        Cell lExpcetedResultCell = lHeaderRow.createCell(Constants.VTPUDATER_EXPECTED_RESULT_COL_IDX);
        lExpcetedResultCell.setCellValue(pCoveringTestCaseData.getExpectedResults());
        lExpcetedResultCell.setCellStyle(vtpWorkbookStyle.getNormalStyle());

        StringBuilder lCoveredReqsSb = new StringBuilder();
        boolean lIsFirst = true;
        for (Requirement lCoveredReq : pCoveringTestCaseData.getCoveredRequirementList()) {
            if (lIsFirst) {
                lIsFirst = false;
            } else {
                lCoveredReqsSb.append(Constants.VTPUPDATER_COVERED_REQUIREMENTS_SEPARATOR);
            }
            lCoveredReqsSb.append(lCoveredReq.toString());
        }

        Cell lCoveredRequirementsCell = lHeaderRow.createCell(Constants.VTPUDATER_COVERED_REQUIREMENTS_COL_IDX);
        lCoveredRequirementsCell.setCellValue(lCoveredReqsSb.toString());
        lCoveredRequirementsCell.setCellStyle(vtpWorkbookStyle.getNormalStyle());
    }

    /**
     * Create and add the header row in the given sheet.
     * 
     * @param pSheet the sheet where to add the row.
     */
    private void addHeaderRowFor(final Sheet pSheet) {
        // Row header is the first
        Row lHeaderRow = pSheet.createRow(0);

        Cell lTestSetNameCell = lHeaderRow.createCell(Constants.VTPUDATER_TEST_SET_NAME_COL_IDX);
        lTestSetNameCell.setCellValue(Constants.VTPUDATER_TEST_SET_NAME_COL_LABEL);
        lTestSetNameCell.setCellStyle(vtpWorkbookStyle.getHeaderStyle());

        Cell lTestCaseIdentifierCell = lHeaderRow.createCell(Constants.VTPUDATER_TEST_CASE_IDENTIFIER_COL_IDX);
        lTestCaseIdentifierCell.setCellValue(Constants.VTPUDATER_TEST_CASE_IDENTIFIER_COL_LABEL);
        lTestCaseIdentifierCell.setCellStyle(vtpWorkbookStyle.getHeaderStyle());

        Cell lTestCaseNameCell = lHeaderRow.createCell(Constants.VTPUDATER_TEST_CASE_NAME_COL_IDX);
        lTestCaseNameCell.setCellValue(Constants.VTPUDATER_TEST_CASE_NAME_COL_LABEL);
        lTestCaseNameCell.setCellStyle(vtpWorkbookStyle.getHeaderStyle());

        Cell lActionDescriptionCell = lHeaderRow.createCell(Constants.VTPUDATER_PROCEDURE_COL_IDX);
        lActionDescriptionCell.setCellValue(Constants.VTPUDATER_PROCEDURE_COL_LABEL);
        lActionDescriptionCell.setCellStyle(vtpWorkbookStyle.getHeaderStyle());

        Cell lExpectedResultCell = lHeaderRow.createCell(Constants.VTPUDATER_EXPECTED_RESULT_COL_IDX);
        lExpectedResultCell.setCellValue(Constants.VTPUDATER_EXPECTED_RESULT_COL_LABEL);
        lExpectedResultCell.setCellStyle(vtpWorkbookStyle.getHeaderStyle());

        Cell lCoveredReqCell = lHeaderRow.createCell(Constants.VTPUDATER_COVERED_REQUIREMENTS_COL_IDX);
        lCoveredReqCell.setCellValue(Constants.VTPUDATER_COVERED_REQUIREMENTS_COL_LABEL);
        lCoveredReqCell.setCellStyle(vtpWorkbookStyle.getHeaderStyle());
    }

    /**
     * Remove any existing Sheet from the given workbook except the one
     * corresponding to the identification.
     * 
     * @param pVtpWorkbook the VTP workbook to work on.
     * @throws ExecutorExecutionException if an error occurs while processing
     * the workbook.
     */
    private void clearAllWorkbookSheetButIndentification(final Workbook pVtpWorkbook) throws ExecutorExecutionException {
        // Get an iterator on the sheets
        Iterator<Sheet> lSheetIterator = pVtpWorkbook.sheetIterator();

        // Search for the sheet corresponding identification
        Sheet lIdentificationSheet = null;

        // Iterate on the sheets
        Sheet lSheet;
        while (lSheetIterator.hasNext()) {
            lSheet = lSheetIterator.next();
            if (lSheet.getSheetName().compareTo(Constants.VTP_IDENTTIFIICATION_SHEET_NAME) == 0) {
                lIdentificationSheet = lSheet;
                break;
            }
        }

        // Delete all sheets but restore identification sheet
        if (lIdentificationSheet != null) {
            while (pVtpWorkbook.getNumberOfSheets() > 1) {
                // Delete the next that is not Identification
                for (int lSheetIdx = 0; lSheetIdx < pVtpWorkbook.getNumberOfSheets(); lSheetIdx++) {
                    lSheet = pVtpWorkbook.getSheetAt(lSheetIdx);
                    if (lSheet.getSheetName().compareTo(Constants.VTP_IDENTTIFIICATION_SHEET_NAME) != 0) {
                        pVtpWorkbook.removeSheetAt(lSheetIdx);
                        break;
                    }
                }
            }
        } else {
            throw new ExecutorExecutionException("File " + targetVtpFile.getAbsolutePath() + " did not contain an "
                    + Constants.VTP_IDENTTIFIICATION_SHEET_NAME + " sheet");
        }
    }

    /**
     * Fill the different CoveringTestSets.
     */
    private void fillCoveringTestSets() {

        // ALM covering test set
        fillAlmCoveringTestSet();

        // Cucumber covering test set
        fillCucumberCoveringTestSet();

        // C# unit test set
        fillCSharpCoveringTestSet();

        // Java unit test set
        fillJavaCoveringTestSet();
    }

    /**
     * Fill the java CoveringTestSet from the java unit test result (if
     * defined).
     */
    private void fillJavaCoveringTestSet() {
        if (javaUnitTestCoverageAnalysisResult != null) {
            Map<File, JavaUnitTestFileData> lFileUtfdListMap = javaUnitTestCoverageAnalysisResult
                    .getFileUnitTestDataMap();

            // Gather test data per java class name
            String lJavaFilename;
            List<UnitTestCaseData> lUnitTestCaseDataList;
            Map<String, List<UnitTestCaseData>> lJavaClassUtcdListMap = new HashMap<String, List<UnitTestCaseData>>();
            for (Map.Entry<File, JavaUnitTestFileData> lEntry : lFileUtfdListMap.entrySet()) {
                JavaUnitTestFileData lUtfd = lEntry.getValue();
                lJavaFilename = lEntry.getKey().getName();

                if (lJavaClassUtcdListMap.containsKey(lJavaFilename)) {
                    lUnitTestCaseDataList = lJavaClassUtcdListMap.get(lJavaFilename);
                } else {
                    lUnitTestCaseDataList = new ArrayList<UnitTestCaseData>();

                    lJavaClassUtcdListMap.put(lJavaFilename, lUnitTestCaseDataList);
                }

                lUnitTestCaseDataList.addAll(lUtfd.getUnitTestDataList());
            }

            // Iterate on java file
            for (Map.Entry<String, List<UnitTestCaseData>> lEntry : lJavaClassUtcdListMap.entrySet()) {
                // Create a new set
                TestSet lJavaUnitTestCoveringTestSet = new TestSet(TestSetType.JavaUnitTest, lEntry.getKey());

                // Iterate on java step test data
                for (UnitTestCaseData lUnitTestCaseData : lEntry.getValue()) {
                    TestCaseData lAddedTestCaseData = lJavaUnitTestCoveringTestSet.addTestData(
                            lUnitTestCaseData.getTestCaseIdentifier(), lUnitTestCaseData.getTestCaseName(),
                            lUnitTestCaseData.getActionDescription(), lUnitTestCaseData.getExpectedResult());
                    for (Requirement lRequirement : lUnitTestCaseData.getCoveredRequirements()) {
                        lAddedTestCaseData.addCoveredRequirement(lRequirement);
                    }
                }

                // Add this test set
                javaCoveringTestSetList.add(lJavaUnitTestCoveringTestSet);
            }
        }
    }

    /**
     * Fill the C# CoveringTestSet from the C# unit test result (if defined).
     */
    private void fillCSharpCoveringTestSet() {
        if (cSharpUnitTestCoverageAnalysisResult != null) {
            Map<File, CSharpUnitTestFileData> lFileUtfdListMap = cSharpUnitTestCoverageAnalysisResult
                    .getFileUnitTestDataMap();

            // Gather test data per assembly name
            Map<String, List<UnitTestCaseData>> lAssemblyUtcdListMap = new HashMap<String, List<UnitTestCaseData>>();

            String lAssemblyName;
            List<UnitTestCaseData> lUnitTestCaseDataList;
            for (Map.Entry<File, CSharpUnitTestFileData> lEntry : lFileUtfdListMap.entrySet()) {
                CSharpUnitTestFileData lUtfd = lEntry.getValue();
                lAssemblyName = lUtfd.getAssemblyName();

                if (lAssemblyUtcdListMap.containsKey(lAssemblyName)) {
                    lUnitTestCaseDataList = lAssemblyUtcdListMap.get(lAssemblyName);
                } else {
                    lUnitTestCaseDataList = new ArrayList<UnitTestCaseData>();
                    lAssemblyUtcdListMap.put(lAssemblyName, lUnitTestCaseDataList);
                }

                lUnitTestCaseDataList.addAll(lUtfd.getUnitTestDataList());
            }

            // Now iterate on assembly names
            for (Map.Entry<String, List<UnitTestCaseData>> lEntry : lAssemblyUtcdListMap.entrySet()) {
                // Create a new set
                TestSet lCSharpUnitTestCoveringTestSet = new TestSet(TestSetType.CSharpUnitTest, lEntry.getKey());

                // Iterate on C# step test data
                for (UnitTestCaseData lUnitTestCaseData : lEntry.getValue()) {
                    TestCaseData lAddedTestCaseData = lCSharpUnitTestCoveringTestSet.addTestData(
                            lUnitTestCaseData.getTestCaseIdentifier(), lUnitTestCaseData.getTestCaseName(),
                            lUnitTestCaseData.getActionDescription(), lUnitTestCaseData.getExpectedResult());
                    for (Requirement lRequirement : lUnitTestCaseData.getCoveredRequirements()) {
                        lAddedTestCaseData.addCoveredRequirement(lRequirement);
                    }
                }

                // Add this test set
                cSharpCoveringTestSetList.add(lCSharpUnitTestCoveringTestSet);
            }
        }
    }

    /**
     * Fill the cucumber CoveringTestSet from the cucumber result (if defined).
     */
    private void fillCucumberCoveringTestSet() {
        if (cucumberTestCoverageAnalysisResult != null) {
            List<CucumberTestsFileData> lCucumberTestsFileDataList = cucumberTestCoverageAnalysisResult
                    .getCucumberTestsFileDataList();

            // Gather the test data per feature name
            Map<String, List<CucumberTestData>> lFeatureTestDataListMap = new HashMap<String, List<CucumberTestData>>();
            String lFeatureName;
            List<CucumberTestData> lLocalCucumberTestDataList;
            for (CucumberTestsFileData lCucumberTestsFileData : lCucumberTestsFileDataList) {
                List<CucumberTestData> lCucumberTestDataList = lCucumberTestsFileData.getCucumberTestDataList();

                for (CucumberTestData lCucumberTestData : lCucumberTestDataList) {

                    lFeatureName = lCucumberTestData.getFeatureName();

                    if (lFeatureTestDataListMap.containsKey(lFeatureName)) {
                        lLocalCucumberTestDataList = lFeatureTestDataListMap.get(lFeatureName);
                    } else {
                        lLocalCucumberTestDataList = new ArrayList<CucumberTestData>();
                        lFeatureTestDataListMap.put(lFeatureName, lLocalCucumberTestDataList);
                    }
                    lLocalCucumberTestDataList.add(lCucumberTestData);
                }
            }

            // Iterate on features
            for (Map.Entry<String, List<CucumberTestData>> lEntry : lFeatureTestDataListMap.entrySet()) {
                // Create a new set
                TestSet lCucumberCoveringTestSet = new TestSet(TestSetType.CucumberTest, lEntry.getKey());

                // Iterate on Cucumber step test data
                String lTestCaseIdentifier;
                for (CucumberTestData lCucumberTestData : lEntry.getValue()) {
                    lTestCaseIdentifier = lCucumberTestData.getScenarioPartName() + "_"
                            + lCucumberTestData.getScenarioPartIdentifier();
                    TestCaseData lAddedTestCaseData = lCucumberCoveringTestSet.addTestData(lTestCaseIdentifier,
                            lCucumberTestData.getScenarioPartName(), lCucumberTestData.getActionDescription(),
                            lCucumberTestData.getExpectedResultDescription());
                    for (Requirement lRequirement : lCucumberTestData.getCoveredRequirements()) {
                        lAddedTestCaseData.addCoveredRequirement(lRequirement);
                    }
                }

                // Add this test set
                cucumberCoveringTestSetList.add(lCucumberCoveringTestSet);

            }
        }
    }

    /**
     * Fill the ALM CoveringTestSet from the analyzer result (if defined).
     */
    private void fillAlmCoveringTestSet() {
        if (almCoverageAnalyzerResult != null) {

            List<AlmStepTestData> lAlmStepTestDataList = almCoverageAnalyzerResult.getAlmStepTestDataList();

            // Gather all the elements per test suite name
            Map<String, List<AlmStepTestData>> lTestSuiteNameMap = new HashMap<String, List<AlmStepTestData>>();

            String lTestSuiteName;
            List<AlmStepTestData> lLocalAlmStepTestDataList;
            for (AlmStepTestData lAlmStepTestData : lAlmStepTestDataList) {
                lTestSuiteName = lAlmStepTestData.getTestSuite();
                if (!lTestSuiteNameMap.containsKey(lTestSuiteName)) {
                    lLocalAlmStepTestDataList = new ArrayList<AlmStepTestData>();
                    lTestSuiteNameMap.put(lTestSuiteName, lLocalAlmStepTestDataList);
                } else {
                    lLocalAlmStepTestDataList = lTestSuiteNameMap.get(lTestSuiteName);
                }
                lLocalAlmStepTestDataList.add(lAlmStepTestData);
            }

            // Iterate on test suites
            for (Map.Entry<String, List<AlmStepTestData>> lEntry : lTestSuiteNameMap.entrySet()) {
                // Create a new set
                TestSet lAlmCoveringTestSet = new TestSet(TestSetType.AlmTest, lEntry.getKey());

                // Iterate on ALM step test data
                for (AlmStepTestData lAlmStepTestData : lEntry.getValue()) {
                    TestCaseData lAddedTestCaseData = lAlmCoveringTestSet.addTestData(
                            Integer.toString(lAlmStepTestData.getTestCaseNumber()),
                            lAlmStepTestData.getTestCaseTitle(), lAlmStepTestData.getAction(),
                            lAlmStepTestData.getExpectedResult());

                    List<Requirement> lCoveredRequirements = getRequirementsFrom(lAlmStepTestData.getExpectedResult());
                    for (Requirement lCoveredReq : lCoveredRequirements) {
                        lAddedTestCaseData.addCoveredRequirement(lCoveredReq);
                    }
                }

                // Add this test set
                almCoveringTestSetList.add(lAlmCoveringTestSet);
            }
        }
    }

    /**
     * Extract the references to requirements in the given text from the
     * expected result cell.
     * <p>
     * The requirements are strings starting with "SD" and located between
     * square brackets.
     * </p>
     * 
     * @param pExpectedResult the text from "Expected result" cell from manual
     * test sheet.
     * @return the list of found requirement references.
     */
    private List<Requirement> getRequirementsFrom(final String pExpectedResult) {
        List<Requirement> lFoundReqList = new ArrayList<Requirement>();

        Matcher lMatcher = expectedResultWithReqPattern.matcher(pExpectedResult);

        while (lMatcher.find()) {
            String lReqText = lMatcher.group(1);
            Requirement lReq = new Requirement(lReqText);
            lFoundReqList.add(lReq);
        }

        return lFoundReqList;
    }

    /**
     * Checks the provided arguments and throw an exception if one is invalid.
     * 
     * @throws InvalidParameterException if an argument is invalid.
     */
    private void checkArguments() throws InvalidParameterException {

        setCurrentOperation("Checking arguments", 5.);

        if (targetVtpFile == null) {
            throw new InvalidParameterException("VtpUpdater.checkArguments", "Target VTP file", "null");
        } else {
            // Ensure the target VTP file exists
            if (!targetVtpFile.isFile()) {
                throw new InvalidParameterException("VtpUpdater.checkArguments", "Target VTP file", "Not existing file");
            }
        }
    }

    /**
     * Read the target VTP file contents and return it.
     * 
     * @return the Workbook representing the contents of the target VTP file
     * @throws ExecutorExecutionException if an error occurs.
     */
    private Workbook readTargetVtpFileContents() throws ExecutorExecutionException {
        setCurrentOperation("Reading the contents of the target VTP file " + targetVtpFile.getAbsolutePath(), 50.);

        Workbook lWorkbook = null;
        FileInputStream lFis = null;
        // Try and open an input stream on the target file
        try {
            lFis = new FileInputStream(targetVtpFile);
        } catch (Exception e) {
            LOGGER.error("Error opening an input stream on file " + targetVtpFile.getAbsolutePath() + " : "
                    + e.getMessage());
            throw new ExecutorExecutionException("Could not read target VTP file (" + e.getMessage() + ")");
        }

        if (lFis != null) {
            try {
                lWorkbook = WorkbookFactory.create(lFis);
            } catch (IOException e) {
                LOGGER.error("Error opening file " + targetVtpFile.getAbsolutePath());
            } finally {
                // Try and close the stream
                try {
                    lFis.close();
                } catch (Exception e) {
                    LOGGER.error("Error closing the input stream on file " + targetVtpFile.getAbsolutePath() + " : "
                            + e.getMessage());
                }
            }

            if (lWorkbook == null) {
                throw new ExecutorExecutionException("Could not open file " + targetVtpFile.getAbsolutePath());
            }
        }

        return lWorkbook;
    }

}
