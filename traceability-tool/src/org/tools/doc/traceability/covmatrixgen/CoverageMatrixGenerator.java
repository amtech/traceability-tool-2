/**
 * 
 */
package org.tools.doc.traceability.covmatrixgen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.tools.doc.traceability.analyzer.justifircation.model.NotCoveredRequirementJustification;
import org.tools.doc.traceability.common.Constants;
import org.tools.doc.traceability.common.exceptions.AbstractTraceabilityException;
import org.tools.doc.traceability.common.exceptions.ExecutorExecutionException;
import org.tools.doc.traceability.common.executor.AbstractExecutor;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.model.Requirement;
import org.tools.doc.traceability.common.model.TestCaseData;
import org.tools.doc.traceability.common.model.TestSet;
import org.tools.doc.traceability.common.model.TestSetType;
import org.tools.doc.traceability.covmatrixgen.model.IRequirementCoveringData;
import org.tools.doc.traceability.covmatrixgen.model.RequirementCoveringData;
import org.tools.doc.traceability.covmatrixgen.model.RequirementTestCovering;

/**
 * Generates a coverage matrix given the SD requirements and the test coverage.
 * 
 * @author Yann Leglise
 *
 */
public class CoverageMatrixGenerator extends AbstractExecutor<CoverageMatrixGeneratorResultObject> {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LogManager.getLogger(CoverageMatrixGenerator.class);

    /**
     * Index for the requirement column.
     */
    private static final int REQUIREMENT_COL_IDX = 0;

    /**
     * Index for the test case identifier column.
     */
    private static final int TEST_CASE_IDENTIFIER_COL_IDX = 1;

    /**
     * Index for the test case name column.
     */
    private static final int TEST_CASE_NAME_COL_IDX = 2;

    /**
     * Index for the test origin details column.
     */
    private static final int TEST_ORIGIN_DETAILS_COL_IDX = 3;

    /**
     * Index for the justification column (in the sheet for not covered
     * requirements).
     */
    private static final int JUSTIFICATION_COL_IDX = 1;

    /**
     * The list of requirements from SD to be covered.
     */
    private final List<Requirement> sdRequirementList;

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
     * The list of justification for not covered requirements.
     */
    private final List<NotCoveredRequirementJustification> notCoveredRequirementJustificationList;

    /**
     * The structure to reference requirement covering from any test.
     */
    // private final RequirementTestCovering requirementTestCovering;

    /**
     * The structure to reference requirement covering from ALM.
     */
    private final RequirementTestCovering almRequirementTestCovering;

    /**
     * The structure to reference requirement covering from cucumber.
     */
    private final RequirementTestCovering cucumberRequirementTestCovering;

    /**
     * The structure to reference requirement covering from C# unit tests.
     */
    private final RequirementTestCovering cSharpRequirementTestCovering;

    /**
     * The structure to reference requirement covering from java unit tests.
     */
    private final RequirementTestCovering javaRequirementTestCovering;

    /**
     * The destination coverage matrix file.
     */
    private final File outputCoverageMatrixFile;

    /**
     * The result object.
     */
    private CoverageMatrixGeneratorResultObject executionResult;

    /**
     * The workbook style to use for the coverage matrix.
     */
    private CoverageMatrixWorkbookStyle workbookStyle;

    /**
     * The list of requirements from SD that are not covered by any test.
     */
    private final List<Requirement> notCoveredRequirementList;

    /**
     * Constructor.
     * 
     * @param pSdRequirementList the list of SD requirements.
     * @param pAlmCoveringTestSetList the test set list from ALM (can be
     * <tt>null</tt> if none).
     * @param pCucumberCoveringTestSetList the test set list from cucumber (can
     * be <tt>null</tt> if none).
     * @param pCSharpCoveringTestSetList the test set list from C# unit test
     * (can be <tt>null</tt> if none).
     * @param pJavaCoveringTestSetList the test set list from java unit test
     * (can be <tt>null</tt> if none).
     * @param pNotCoveredRequirementJustificationList the list of justifications
     * (can be <tt>null</tt> if none). for not covered requirements.
     * @param pOutputCoverageMatrixFile the destination coverage matrix file.
     * @param pExecutionStatus the execution status.
     */
    public CoverageMatrixGenerator(final List<Requirement> pSdRequirementList,
            final List<TestSet> pAlmCoveringTestSetList, final List<TestSet> pCucumberCoveringTestSetList,
            final List<TestSet> pCSharpCoveringTestSetList, final List<TestSet> pJavaCoveringTestSetList,
            final List<NotCoveredRequirementJustification> pNotCoveredRequirementJustificationList,
            final File pOutputCoverageMatrixFile,
            final ExecutorExecutionStatus<CoverageMatrixGeneratorResultObject> pExecutionStatus) {
        super(pExecutionStatus);
        sdRequirementList = pSdRequirementList;
        almCoveringTestSetList = pAlmCoveringTestSetList;
        cucumberCoveringTestSetList = pCucumberCoveringTestSetList;
        cSharpCoveringTestSetList = pCSharpCoveringTestSetList;
        javaCoveringTestSetList = pJavaCoveringTestSetList;
        notCoveredRequirementJustificationList = pNotCoveredRequirementJustificationList;

        outputCoverageMatrixFile = pOutputCoverageMatrixFile;

        almRequirementTestCovering = new RequirementTestCovering();
        cucumberRequirementTestCovering = new RequirementTestCovering();
        cSharpRequirementTestCovering = new RequirementTestCovering();
        javaRequirementTestCovering = new RequirementTestCovering();
        notCoveredRequirementList = new ArrayList<Requirement>();

        workbookStyle = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performTask() throws AbstractTraceabilityException {

        executionResult = new CoverageMatrixGeneratorResultObject();

        // Make sure there are requirements
        if (sdRequirementList != null) {

            // Fill the list of requirement covering data for each test type
            // and detect not covered requirements
            fillRequirementTestCoveringAndDetectNotCoveredRequirements();

            // Do write the covering matrix
            writeRequirementCoverageMatrix();
        }

        setExecutionResult(executionResult);
    }

    /**
     * Fill requirementTestCovering (the general and each specific) using the
     * provided list of all requirement covering data list.
     * <p>
     * Also detect the requirements that are not covered.
     * </p>
     * 
     */
    private void fillRequirementTestCoveringAndDetectNotCoveredRequirements() {

        // Add ALM covering test data (if any)
        List<IRequirementCoveringData> lAlmReqCovDataList = new ArrayList<IRequirementCoveringData>();
        if (almCoveringTestSetList != null) {
            addCoveringTestDataFrom(lAlmReqCovDataList, almCoveringTestSetList);
        }

        // Add cucumber covering test data (if any)
        List<IRequirementCoveringData> lCucumberReqCovDataList = new ArrayList<IRequirementCoveringData>();
        if (cucumberCoveringTestSetList != null) {
            addCoveringTestDataFrom(lCucumberReqCovDataList, cucumberCoveringTestSetList);
        }

        // Add C# unit test covering test data (if any)
        List<IRequirementCoveringData> lCSharpReqCovDataList = new ArrayList<IRequirementCoveringData>();
        if (cSharpCoveringTestSetList != null) {
            addCoveringTestDataFrom(lCSharpReqCovDataList, cSharpCoveringTestSetList);
        }

        // Add java unit test covering test data (if any)
        List<IRequirementCoveringData> lJavaReqCovDataList = new ArrayList<IRequirementCoveringData>();
        if (javaCoveringTestSetList != null) {
            if (javaCoveringTestSetList != null) {
                addCoveringTestDataFrom(lJavaReqCovDataList, javaCoveringTestSetList);
            }
        }

        // Iterate on requirements
        for (Requirement lRequirement : sdRequirementList) {

            boolean lRequirementIsCovered = false;

            // Add ALM covering test data (if any)
            for (IRequirementCoveringData lRequirementCoveringData : lAlmReqCovDataList) {
                if (lRequirementCoveringData.getCoveredRequirementList().contains(lRequirement)) {
                    // The requirement is covered
                    lRequirementIsCovered = true;

                    // Add to the execution result general structure
                    executionResult.addRequirementCovering(lRequirement, lRequirementCoveringData);

                    // Add in the ALM specific structure
                    almRequirementTestCovering.addRequirementCovering(lRequirement, lRequirementCoveringData);
                }
            }

            for (IRequirementCoveringData lRequirementCoveringData : lCucumberReqCovDataList) {

                if (lRequirementCoveringData.getCoveredRequirementList().contains(lRequirement)) {
                    // The requirement is covered
                    lRequirementIsCovered = true;

                    // Add to the execution result general structure
                    executionResult.addRequirementCovering(lRequirement, lRequirementCoveringData);

                    // Add in the cucumber specific structure
                    cucumberRequirementTestCovering.addRequirementCovering(lRequirement, lRequirementCoveringData);
                }
            }

            for (IRequirementCoveringData lRequirementCoveringData : lCSharpReqCovDataList) {
                if (lRequirementCoveringData.getCoveredRequirementList().contains(lRequirement)) {
                    // The requirement is covered
                    lRequirementIsCovered = true;

                    // Add to the execution result general structure
                    executionResult.addRequirementCovering(lRequirement, lRequirementCoveringData);

                    // Add in the C# unit test specific structure
                    cSharpRequirementTestCovering.addRequirementCovering(lRequirement, lRequirementCoveringData);
                }
            }

            for (IRequirementCoveringData lRequirementCoveringData : lJavaReqCovDataList) {
                if (lRequirementCoveringData.getCoveredRequirementList().contains(lRequirement)) {
                    // The requirement is covered
                    lRequirementIsCovered = true;

                    // Add to the execution result general structure
                    executionResult.addRequirementCovering(lRequirement, lRequirementCoveringData);

                    // Add in the java unit test specific structure
                    javaRequirementTestCovering.addRequirementCovering(lRequirement, lRequirementCoveringData);
                }
            }

            // If the requirement is not covered, add it to the list of covered
            // requirements
            if (!lRequirementIsCovered) {
                notCoveredRequirementList.add(lRequirement);
            }
        }
    }

    /**
     * Add in the list all the requirement covering data created from the
     * covering test set list.
     * 
     * @param pReqCovDataList the destination list to fill.
     * @param pCoveringTestSetList the list of covering test set to consider.
     */
    private void addCoveringTestDataFrom(final List<IRequirementCoveringData> pReqCovDataList,
            final List<TestSet> pCoveringTestSetList) {
        if (pReqCovDataList != null && pCoveringTestSetList != null) {
            String lOriginDetails;
            for (TestSet lTestSet : pCoveringTestSetList) {
                for (TestCaseData lTestCaseData : lTestSet.getTestDataList()) {
                    lOriginDetails = lTestSet.getTestSetName();
                    RequirementCoveringData lRcd = new RequirementCoveringData(lTestSet.getTestSetType(),
                            lTestCaseData.getTestCaseIdentifier(), lTestCaseData.getTestCaseName(), lOriginDetails);
                    lRcd.addCoveredRequirements(lTestCaseData.getCoveredRequirementList());
                    pReqCovDataList.add(lRcd);
                }
            }
        }
    }

    /**
     * Writes the requirement coverage matrix.
     * 
     * @throws ExecutorExecutionException if an error occurs.
     */
    private void writeRequirementCoverageMatrix() throws ExecutorExecutionException {

        setCurrentOperation("Writing coverage matrix to " + outputCoverageMatrixFile.getAbsolutePath(), 90.);

        FileOutputStream lOutputFileInputStream = null;

        try {
            lOutputFileInputStream = new FileOutputStream(outputCoverageMatrixFile);
        } catch (Exception e) {
            LOGGER.error("Could not write file " + outputCoverageMatrixFile.getAbsolutePath() + " : " + e.getMessage());
            throw new ExecutorExecutionException("Could not write file " + outputCoverageMatrixFile.getAbsolutePath()
                    + " : " + e.getMessage());
        }

        if (lOutputFileInputStream != null) {

            try {
                // Create the Excel workbook
                XSSFWorkbook lWorkbook = new XSSFWorkbook();

                // Initialize the styles
                workbookStyle = new CoverageMatrixWorkbookStyle(lWorkbook);

                // Write a sheet for ALM if relevant
                if (almRequirementTestCovering.containsCoveredRequirements()) {
                    addSheet(almRequirementTestCovering, TestSetType.AlmTest, lWorkbook);
                }

                // Write a sheet for cucumber if relevant
                if (cucumberRequirementTestCovering.containsCoveredRequirements()) {
                    addSheet(cucumberRequirementTestCovering, TestSetType.CucumberTest, lWorkbook);
                }

                // Write a sheet for C# unit test if relevant
                if (cSharpRequirementTestCovering.containsCoveredRequirements()) {
                    addSheet(cSharpRequirementTestCovering, TestSetType.CSharpUnitTest, lWorkbook);
                }

                // Write a sheet for java unit test if relevant
                if (javaRequirementTestCovering.containsCoveredRequirements()) {
                    addSheet(javaRequirementTestCovering, TestSetType.JavaUnitTest, lWorkbook);
                }

                // Write the sheet for the not covered requirements, if relevant
                if (!notCoveredRequirementList.isEmpty()) {
                    addNotCoveredRequirementsSheet(lWorkbook);
                }

                // Write the workbook to the file
                lWorkbook.write(lOutputFileInputStream);

            } catch (EncryptedDocumentException ede) {
                LOGGER.error("Could not write file " + outputCoverageMatrixFile.getAbsolutePath() + " : "
                        + ede.getMessage());
                throw new ExecutorExecutionException("Could not write file "
                        + outputCoverageMatrixFile.getAbsolutePath() + " : " + ede.getMessage());
            } catch (IOException ioe) {
                LOGGER.error("Could not write file " + outputCoverageMatrixFile.getAbsolutePath() + " : "
                        + ioe.getMessage());
                throw new ExecutorExecutionException("Could not write file "
                        + outputCoverageMatrixFile.getAbsolutePath() + " : " + ioe.getMessage());
            } finally {

                try {
                    lOutputFileInputStream.close();
                } catch (Exception e) {
                    LOGGER.error("Error closing file " + outputCoverageMatrixFile.getAbsolutePath() + " : "
                            + e.getMessage());
                }
            }
        }
    }

    /**
     * Add a sheet for the not covered requirements.
     * 
     * @param pWorkbook the destination workbook.
     */
    private void addNotCoveredRequirementsSheet(final XSSFWorkbook pWorkbook) {
        XSSFSheet lSheet = pWorkbook.createSheet("Not covered requirements");

        // Size the columns
        lSheet.setColumnWidth(REQUIREMENT_COL_IDX, Constants.COVMAT_NOT_COVERED_REQUIREMENT_COL_WIDTH_IN_OUTPUT_FILE
                * Constants.CHARCTER_WIDTH_FACTOR);
        lSheet.setColumnWidth(TEST_CASE_IDENTIFIER_COL_IDX, Constants.COVMAT_JUSTIFICATION_COL_WIDTH_IN_OUTPUT_FILE
                * Constants.CHARCTER_WIDTH_FACTOR);

        // Add the header row
        addNotCoveredHeaderRowFor(lSheet);

        // Sort not covered requirements
        Collections.sort(notCoveredRequirementList);

        // Iterate on SD requirements
        int lRowIdx = 1;
        for (Requirement lNotCoveredRequirement : notCoveredRequirementList) {
            // Create the data row at the given index
            Row lReqRow = lSheet.createRow(lRowIdx++);

            // Check whether there is a justification for this requirement or
            // not
            String lJustification = null;
            if (notCoveredRequirementJustificationList != null) {
                for (NotCoveredRequirementJustification lNcrJustification : notCoveredRequirementJustificationList) {
                    if (lNcrJustification.getRequirement().compareTo(lNotCoveredRequirement) == 0) {
                        lJustification = lNcrJustification.getJustification();
                        break;
                    }
                }
            }

            Cell lReqIdCell = lReqRow.createCell(REQUIREMENT_COL_IDX);
            lReqIdCell.setCellValue(lNotCoveredRequirement.toString());
            lReqIdCell.setCellStyle(workbookStyle.getRequirementStyle());

            Cell lJustificationCell = lReqRow.createCell(JUSTIFICATION_COL_IDX);
            if (lJustification == null) {
                lJustificationCell.setCellValue("Not covered");
                lJustificationCell.setCellStyle(workbookStyle.getMissingCoverageStyle());

                // Add this requirement in the result object as it is not
                // justified
                executionResult.addNotCoveredRequirement(lNotCoveredRequirement);
            } else {
                lJustificationCell.setCellValue(lJustification);
                lJustificationCell.setCellStyle(workbookStyle.getCoverageStyle());
            }
        }
    }

    /**
     * Add a new sheet to the workbook referencing the requirements covered by a
     * specific type of test.
     * 
     * @param pRequirementTestCovering the requirement test covering.
     * @param pTestType the type of test.
     * @param pWorkbook the destination workbook.
     */
    private void addSheet(final RequirementTestCovering pRequirementTestCovering, final TestSetType pTestType,
            final XSSFWorkbook pWorkbook) {
        XSSFSheet lSheet = pWorkbook.createSheet(pTestType.getAssociatedVtpSheetName());

        // Size the columns
        lSheet.setColumnWidth(REQUIREMENT_COL_IDX, Constants.COVMAT_REQUIREMENT_COL_WIDTH_IN_OUTPUT_FILE
                * Constants.CHARCTER_WIDTH_FACTOR);
        lSheet.setColumnWidth(TEST_CASE_IDENTIFIER_COL_IDX, Constants.COVMAT_TEST_ID_COL_IDX_IN_OUTPUT_FILE
                * Constants.CHARCTER_WIDTH_FACTOR);
        lSheet.setColumnWidth(TEST_CASE_NAME_COL_IDX, Constants.COVMAT_TEST_NAME_COL_IDX_IN_OUTPUT_FILE
                * Constants.CHARCTER_WIDTH_FACTOR);
        lSheet.setColumnWidth(TEST_ORIGIN_DETAILS_COL_IDX, Constants.COVMAT_TEST_ORIGIN_DETAIL_COL_IDX_IN_OUTPUT_FILE
                * Constants.CHARCTER_WIDTH_FACTOR);

        // Add the header row
        addHeaderRowFor(lSheet);

        int lRowIdx = 1;

        // Iterate on covered requirements
        for (Requirement lCoveredRequirement : pRequirementTestCovering.getSortedListOfCoveredRequirements()) {
            // Process this requirement coverage
            lRowIdx += addRequirementCoverageRowsFor(lSheet, lRowIdx, lCoveredRequirement,
                    pRequirementTestCovering.getRequirementCoveringDataListFor(lCoveredRequirement));
        }
    }

    /**
     * Add rows in the given sheet at the given index to indicate the coverage
     * of the given requirement.
     * 
     * @param pSheet the destination sheet.
     * @param pRowIdx the row index where to add rows.
     * @param pRequirement the covered requirement.
     * @param pList the associated coverage data.
     * @return the number of added rows.
     */
    private int addRequirementCoverageRowsFor(final XSSFSheet pSheet, final int pRowIdx,
            final Requirement pRequirement, final List<IRequirementCoveringData> pList) {
        int lNbAddeRows = 0;

        // Create the data row at the given index
        int lCurrentRowIdx = pRowIdx;
        Row lCurrentRow = pSheet.createRow(pRowIdx);

        Cell lReqIdCell = lCurrentRow.createCell(REQUIREMENT_COL_IDX);
        lReqIdCell.setCellValue(pRequirement.toString());
        lReqIdCell.setCellStyle(workbookStyle.getRequirementStyle());

        for (IRequirementCoveringData lReqCoverageData : pList) {

            if (lCurrentRowIdx > pRowIdx) {
                lCurrentRow = pSheet.createRow(lCurrentRowIdx);

                lReqIdCell = lCurrentRow.createCell(REQUIREMENT_COL_IDX);
                lReqIdCell.setCellStyle(workbookStyle.getRequirementStyle());
            }

            Cell lTestIdentifierCell = lCurrentRow.createCell(TEST_CASE_IDENTIFIER_COL_IDX);
            lTestIdentifierCell.setCellValue(lReqCoverageData.getTestCaseIdentifier());
            lTestIdentifierCell.setCellStyle(workbookStyle.getCoverageStyle());

            Cell lTestNameCell = lCurrentRow.createCell(TEST_CASE_NAME_COL_IDX);
            lTestNameCell.setCellValue(lReqCoverageData.getTestCaseName());
            lTestNameCell.setCellStyle(workbookStyle.getCoverageStyle());

            Cell lTestOriginDetailsCell = lCurrentRow.createCell(TEST_ORIGIN_DETAILS_COL_IDX);
            lTestOriginDetailsCell.setCellValue(lReqCoverageData.getOriginDetails());
            lTestOriginDetailsCell.setCellStyle(workbookStyle.getCoverageStyle());

            // Go to the next row next time
            lCurrentRowIdx += 1;
        }

        // If there were multiple coverage data, merge the added cells in
        // requirement
        // column
        if (pList.size() > 1) {
            pSheet.addMergedRegion(new CellRangeAddress(pRowIdx, lCurrentRowIdx - 1, REQUIREMENT_COL_IDX,
                    REQUIREMENT_COL_IDX));
        }

        lNbAddeRows = pList.size();
        return lNbAddeRows;
    }

    /**
     * Create and add the header row in the given sheet.
     * 
     * @param pSheet the sheet where to add the row.
     */
    private void addHeaderRowFor(final Sheet pSheet) {
        // Row header is the first
        Row lHeaderRow = pSheet.createRow(0);

        Cell lReqIdCell = lHeaderRow.createCell(REQUIREMENT_COL_IDX);
        lReqIdCell.setCellValue("Requirement");
        lReqIdCell.setCellStyle(workbookStyle.getHeaderStyle());

        Cell lTestCaseIdentifierCell = lHeaderRow.createCell(TEST_CASE_IDENTIFIER_COL_IDX);
        lTestCaseIdentifierCell.setCellValue("Test case ID");
        lTestCaseIdentifierCell.setCellStyle(workbookStyle.getHeaderStyle());

        Cell lTestCaseNameCell = lHeaderRow.createCell(TEST_CASE_NAME_COL_IDX);
        lTestCaseNameCell.setCellValue("Test case name");
        lTestCaseNameCell.setCellStyle(workbookStyle.getHeaderStyle());

        Cell lTestOriginDetailsCell = lHeaderRow.createCell(TEST_ORIGIN_DETAILS_COL_IDX);
        lTestOriginDetailsCell.setCellValue("Test origin details");
        lTestOriginDetailsCell.setCellStyle(workbookStyle.getHeaderStyle());
    }

    /**
     * Create and add the header row in the given sheet (the one for not covered
     * requirements).
     * 
     * @param pSheet the sheet where to add the row.
     */
    private void addNotCoveredHeaderRowFor(final Sheet pSheet) {
        // Row header is the first
        Row lHeaderRow = pSheet.createRow(0);

        Cell lReqIdCell = lHeaderRow.createCell(REQUIREMENT_COL_IDX);
        lReqIdCell.setCellValue("Requirement");
        lReqIdCell.setCellStyle(workbookStyle.getHeaderStyle());

        Cell lTestCaseIdentifierCell = lHeaderRow.createCell(JUSTIFICATION_COL_IDX);
        lTestCaseIdentifierCell.setCellValue("Justification");
        lTestCaseIdentifierCell.setCellStyle(workbookStyle.getHeaderStyle());
    }

}
