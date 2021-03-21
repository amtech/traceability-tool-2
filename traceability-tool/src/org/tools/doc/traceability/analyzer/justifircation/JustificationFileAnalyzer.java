/**
 * 
 */
package org.tools.doc.traceability.analyzer.justifircation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.tools.doc.traceability.analyzer.justifircation.model.NotCoveredRequirementJustification;
import org.tools.doc.traceability.common.exceptions.AbstractTraceabilityException;
import org.tools.doc.traceability.common.exceptions.ExecutorExecutionException;
import org.tools.doc.traceability.common.exceptions.InvalidParameterException;
import org.tools.doc.traceability.common.executor.AbstractExecutor;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.model.Requirement;

/**
 * A tool to parse a justification file and extract the requirement not covering
 * justification reasons.
 * 
 * @author Yann Leglise
 *
 */
public class JustificationFileAnalyzer extends AbstractExecutor<JustificationFileAnalyzerResult> {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LogManager.getLogger(JustificationFileAnalyzer.class);

    /**
     * The justification file to analyze.
     */
    private final File justificationFile;

    /**
     * The result object.
     */
    private JustificationFileAnalyzerResult resultObject;

    /**
     * Constructor.
     * 
     * @param pJustificationFile the justification file ta analyze.
     * @param pExecutionStatus the execution status.
     */
    public JustificationFileAnalyzer(final File pJustificationFile,
            final ExecutorExecutionStatus<JustificationFileAnalyzerResult> pExecutionStatus) {
        super(pExecutionStatus);
        justificationFile = pJustificationFile;

        resultObject = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performTask() throws AbstractTraceabilityException {
        LOGGER.debug("Starting justification file analysis");

        // First, check the provided arguments
        checkArguments();

        // Parse the justifification file
        parseJustificationFile();
    }

    /**
     * Parses the justification file and extract the not covered requirement
     * justifications.
     * 
     * @throws ExecutorExecutionException if an error occurs during the
     * extraction.
     */
    private void parseJustificationFile() throws ExecutorExecutionException {
        resultObject = new JustificationFileAnalyzerResult();

        FileInputStream lJustifFis = null;

        try {
            lJustifFis = new FileInputStream(justificationFile);

        } catch (FileNotFoundException fnfe) {
            LOGGER.error("Error opening justification file " + justificationFile.getAbsolutePath() + " : "
                    + fnfe.getMessage());
            throw new ExecutorExecutionException("Justification file could not be found : " + fnfe.getMessage());
        }

        if (lJustifFis != null) {
            XSSFWorkbook lJustifcatioWorkbook = null;
            try {
                lJustifcatioWorkbook = new XSSFWorkbook(lJustifFis);

                // Iterate on sheets
                int lNbSheets = lJustifcatioWorkbook.getNumberOfSheets();
                for (int lSheetIdx = 0; lSheetIdx < lNbSheets; lSheetIdx++) {
                    XSSFSheet lSheet = lJustifcatioWorkbook.getSheetAt(lSheetIdx);

                    // Get iterator to all the rows in current sheet
                    Iterator<Row> lRowIt = lSheet.iterator();

                    // Get the first row
                    if (lRowIt.hasNext()) {
                        Row lHeaderRow = lRowIt.next();
                        // Make sure it corresponds to a justification header
                        // row

                        Iterator<Cell> lCellIt = lHeaderRow.cellIterator();

                        boolean lReqHeaderCellPresent = false;
                        boolean lJustifCellHeaderPresent = false;

                        if (lCellIt.hasNext()) {
                            Cell lFirstHeaderCell = lCellIt.next();

                            String lFirstHeaderCellText = getNormalizedCellText(lFirstHeaderCell);

                            if (lFirstHeaderCellText.contentEquals("req")) {
                                lReqHeaderCellPresent = true;

                                if (lCellIt.hasNext()) {
                                    Cell lSecondHeaderCell = lCellIt.next();

                                    String lSecondHeaderCellText = getNormalizedCellText(lSecondHeaderCell);

                                    if (lSecondHeaderCellText.contentEquals("justification")) {
                                        lJustifCellHeaderPresent = true;
                                    }
                                }
                            }
                        }

                        // If it is a justification sheet, process next lines
                        if (lReqHeaderCellPresent && lJustifCellHeaderPresent) {
                            while (lRowIt.hasNext()) {
                                Row lDataRow = lRowIt.next();

                                // Extract the requirement name and
                                // justification from the row
                                String lReqName = null;
                                String lJustificationText = null;

                                lCellIt = lDataRow.cellIterator();

                                if (lCellIt.hasNext()) {
                                    Cell lFirstHeaderCell = lCellIt.next();

                                    lReqName = lFirstHeaderCell.getStringCellValue().trim();

                                    if (lCellIt.hasNext()) {
                                        Cell lSecondHeaderCell = lCellIt.next();

                                        lJustificationText = lSecondHeaderCell.getStringCellValue().trim();
                                    }
                                }

                                // If the req name and justification were found,
                                // add the data
                                if ((lReqName != null) && (lJustificationText != null)) {
                                    Requirement lRequirement = new Requirement(lReqName);

                                    // Create the not covered requirement
                                    // justification item
                                    NotCoveredRequirementJustification lNcrJustif = new NotCoveredRequirementJustification(
                                            lRequirement, lJustificationText);

                                    // Add the element in the result object (if
                                    // defined)
                                    if (lNcrJustif.isDefined()) {
                                        resultObject.addNotCoveredRequirementJustification(lNcrJustif);
                                    }
                                }
                            }

                        } else {
                            LOGGER.debug("Ignoring sheet \"" + lSheet.getSheetName()
                                    + "\" from justifcation file as it does not correspond to a justification sheet");
                        }
                    }
                }
            } catch (IOException ioe) {
                LOGGER.error("Error reading justification file " + justificationFile.getAbsolutePath() + " : "
                        + ioe.getMessage());
                throw new ExecutorExecutionException("Justification file could not be read properly : "
                        + ioe.getMessage());
            } finally {

                if (lJustifcatioWorkbook != null) {
                    try {
                        lJustifcatioWorkbook.close();
                    } catch (IOException ioe) {
                        LOGGER.error("Error closing justification work book " + justificationFile.getAbsolutePath()
                                + " : " + ioe.getMessage());
                        // Do not throw exception as not fatal
                    }
                }

                if (lJustifFis != null) {
                    try {
                        lJustifFis.close();
                    } catch (IOException ioe) {
                        LOGGER.error("Error closing justification file " + justificationFile.getAbsolutePath() + " : "
                                + ioe.getMessage());
                        // Do not throw exception as not fatal
                    }
                }
            }
        }

        setExecutionResult(resultObject);
    }

    /**
     * Returns the text contained in the given cell, with all spaces removed,
     * and all in lower case.
     * 
     * @param pCell the cell to consider.
     * @return the cell text in lower case with no space characters.
     */
    private String getNormalizedCellText(final Cell pCell) {
        String lNormalizedText = "";

        try {
            switch (pCell.getCellType()) {
                case STRING:
                    lNormalizedText = pCell.getStringCellValue().replace(" ", "").toLowerCase();
                    break;

                case NUMERIC:
                    double lDoubleVal = pCell.getNumericCellValue();
                    lNormalizedText = Integer.toString((int) lDoubleVal);
                    break;
                default:
                    lNormalizedText = "";
                    break;
            }
        } catch (Exception e) {
            LOGGER.warn("Error trying to get normalized cell text : " + e.getMessage());
        }

        return lNormalizedText;
    }

    /**
     * Checks the provided arguments and throw an exception if one is invalid.
     * 
     * @throws InvalidParameterException if an argument is invalid.
     */
    private void checkArguments() throws InvalidParameterException {
        setCurrentOperation("Checking arguments", 5.);
        // Ensure the justification file is defined and exists
        if (justificationFile == null) {
            throw new InvalidParameterException("JustificationFileAnalyzer.checkArguments", "justification file",
                    "null value");
        } else {
            // Ensure the directory exists
            if (!justificationFile.isFile()) {
                throw new InvalidParameterException("JustificationFileAnalyzer.checkArguments", "justification file",
                        "not existing");
            }
        }
    }
}
