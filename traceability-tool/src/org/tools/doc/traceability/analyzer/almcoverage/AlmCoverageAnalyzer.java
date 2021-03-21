/**
 * 
 */
package org.tools.doc.traceability.analyzer.almcoverage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.tools.doc.traceability.analyzer.almcoverage.model.AlmStepTestData;
import org.tools.doc.traceability.analyzer.almcoverage.model.AlmWorkbookSheetRowContents;
import org.tools.doc.traceability.common.exceptions.AbstractTraceabilityException;
import org.tools.doc.traceability.common.exceptions.ExecutorExecutionException;
import org.tools.doc.traceability.common.exceptions.InvalidParameterException;
import org.tools.doc.traceability.common.executor.AbstractExecutor;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;

/**
 * A tool that reads the test extract from HP ALM that updates the relevant
 * sheet of the destination VTP with its contents.
 * 
 * @author Yann Leglise
 *
 */
public class AlmCoverageAnalyzer extends AbstractExecutor<AlmCoverageAnalyserResult> {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LogManager.getLogger(AlmCoverageAnalyzer.class);

    /**
     * The input file where the extract from HP ALM has been done.
     */
    private File inpuAlmExtractFile;

    /**
     * The result object.
     */
    private AlmCoverageAnalyserResult resultObject;

    /**
     * 
     * @param pInpuAlmExtractFile the input file where the extract from HP ALM
     * has been done.
     * @param pExecutionStatus the executor execution status.
     */
    public AlmCoverageAnalyzer(final File pInpuAlmExtractFile,
            final ExecutorExecutionStatus<AlmCoverageAnalyserResult> pExecutionStatus) {
        super(pExecutionStatus);
        inpuAlmExtractFile = pInpuAlmExtractFile;
        resultObject = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performTask() throws AbstractTraceabilityException {
        // First, check the provided arguments
        checkArguments();

        // Create the result object
        resultObject = new AlmCoverageAnalyserResult();

        // Read the ALM test data from the input file
        readAlmTestFile();

        // Set the execution result
        setExecutionResult(resultObject);
    }

    /**
     * Read the input .xlsx file containing the extract from ALM tests.
     * 
     * @throws ExecutorExecutionException if an error occurs.
     * 
     */
    private void readAlmTestFile() throws ExecutorExecutionException {
        setCurrentOperation("Reading the contents of the input file " + inpuAlmExtractFile.getAbsolutePath(), 5.);

        FileInputStream lFis = null;

        // Try and open an input stream on the file
        try {
            lFis = new FileInputStream(inpuAlmExtractFile);
        } catch (Exception e) {
            LOGGER.error("Error opening an input stream on file " + inpuAlmExtractFile.getAbsolutePath() + " : "
                    + e.getMessage());
            throw new ExecutorExecutionException("Could not read input file (" + e.getMessage() + ")");
        }

        if (lFis != null) {
            Workbook lWorkbook = null;
            try {
                lWorkbook = WorkbookFactory.create(lFis);
            } catch (IOException e) {
                LOGGER.error("Error opening file " + inpuAlmExtractFile.getAbsolutePath());
            } finally {
                // Try and close the stream
                try {
                    lFis.close();
                } catch (Exception e) {
                    LOGGER.error("Error closing the input stream on file " + inpuAlmExtractFile.getAbsolutePath()
                            + " : " + e.getMessage());
                }
            }

            if (lWorkbook == null) {
                throw new ExecutorExecutionException("Could not open file " + inpuAlmExtractFile.getAbsolutePath());
            } else {
                // Get an iterator on the sheets
                Iterator<Sheet> lSheetIterator = lWorkbook.sheetIterator();

                // Iterate on the sheets
                Sheet lSheet;
                while (lSheetIterator.hasNext()) {
                    lSheet = lSheetIterator.next();
                    extractTestDataFrom(lSheet);
                }
            }
        }
    }

    /**
     * Extract the test data from the given sheet.
     * 
     * @param pSheet the sheet from which to try and read.
     * @throws ExecutorExecutionException if an error occurs during the
     * execution.
     */
    private void extractTestDataFrom(final Sheet pSheet) throws ExecutorExecutionException {
        // Get the first row
        Iterator<Row> lRowIt = pSheet.rowIterator();

        if (lRowIt.hasNext()) {
            Row lHeaderRow = lRowIt.next();

            if (isHeaderRow(lHeaderRow)) {

                // Process all the next lines
                Row lDataRow;
                List<String> lCellValues;
                AlmWorkbookSheetRowContents lRowContents;
                AlmStepTestData lAlmStepTestData;

                while (lRowIt.hasNext()) {
                    lDataRow = lRowIt.next();

                    lCellValues = getRowCellValues(lDataRow);
                    lRowContents = new AlmWorkbookSheetRowContents(lCellValues);
                    lAlmStepTestData = lRowContents.toAlmStepTestData();

                    // Prevent adding empty elements
                    if (lAlmStepTestData.isDefined()) {
                        resultObject.addAlmStepData(lAlmStepTestData);
                    }
                }
            }
        }
    }

    /**
     * Check whether the given header row corresponds to the first line of a
     * sheet generated by ALM.
     * 
     * @param pHeaderRow the header row to consider.
     * @return true if this header row matches the one generated by ALM, false
     * if not.
     */
    private boolean isHeaderRow(final Row pHeaderRow) {

        List<String> lHeaderCellValues = getRowCellValues(pHeaderRow);

        AlmWorkbookSheetRowContents lRowContents = new AlmWorkbookSheetRowContents(lHeaderCellValues);

        return lRowContents.isHeader();
    }

    /**
     * Extract the values from each cell of the row and return them.
     * 
     * @param pRow the row to consider.
     * @return the list of cell values.
     */
    private List<String> getRowCellValues(final Row pRow) {
        List<String> lRowCellValues = new ArrayList<String>();

        Iterator<Cell> lCellIterator = pRow.cellIterator();

        while (lCellIterator.hasNext()) {
            Cell lCell = lCellIterator.next();
            String lCellContents;
            switch (lCell.getCellType()) {
                case NUMERIC:
                    long lNumVal = (long) lCell.getNumericCellValue();
                    lCellContents = Long.toString(lNumVal);
                    break;
                case STRING:
                default:
                    lCellContents = lCell.getRichStringCellValue().getString();
                    break;
            }
            lRowCellValues.add(lCellContents);
        }

        return lRowCellValues;
    }

    /**
     * Checks the provided arguments and throw an exception if one is invalid.
     * 
     * @throws InvalidParameterException if an argument is invalid.
     */
    private void checkArguments() throws InvalidParameterException {
        getExecutionStatus().setCurrentOperation("Checking arguments", 5.);
        // Ensure the input file is defined
        if (inpuAlmExtractFile == null) {
            throw new InvalidParameterException("VtpUpdater.checkArguments", "input HP ALM extract file", "null value");
        } else {
            // Ensure the file exists
            if (!inpuAlmExtractFile.isFile())

            {
                throw new InvalidParameterException("VtpUpdater.checkArguments", "input HP ALM extract file",
                        "not existing file" + inpuAlmExtractFile.getAbsolutePath());
            }
        }
    }
}
