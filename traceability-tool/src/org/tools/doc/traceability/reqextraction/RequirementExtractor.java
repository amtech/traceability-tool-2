/**
 * 
 */
package org.tools.doc.traceability.reqextraction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.tools.doc.traceability.common.Constants;
import org.tools.doc.traceability.common.exceptions.AbstractTraceabilityException;
import org.tools.doc.traceability.common.exceptions.ExecutorExecutionException;
import org.tools.doc.traceability.common.exceptions.InvalidParameterException;
import org.tools.doc.traceability.common.executor.AbstractExecutor;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.model.Requirement;
import org.tools.doc.traceability.reqextraction.helper.RequirementDuplicationAnalysisData;
import org.tools.doc.traceability.reqextraction.helper.RequirementDuplicationItem;

/**
 * Tool for extracting requirements from a docx file.
 * 
 * @author Yann Leglise
 *
 */
public class RequirementExtractor extends AbstractExecutor<RequirementExtractorResult> {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LogManager.getLogger(RequirementExtractor.class);

    /**
     * The list of input files to parse.
     */
    private List<File> inputFileList;

    /**
     * The file where to output the extracted requirements.
     */
    private final File outputFile;

    /**
     * Flag indicating whether the requirements shall be sorted or not in the
     * output file.
     */
    private final boolean sortRequirements;

    /**
     * The structure used to detect duplicated requirements.
     */
    private RequirementDuplicationAnalysisData requirementDuplicationAnalysisData;

    /**
     * The list of prefixes for requirements.
     */
    private List<String> requirementPrefixList;

    /**
     * The execution result.
     */
    private RequirementExtractorResult executionResult;

    /**
     * Constructor.
     * 
     * @param pInputFileList the list of input files to parse.
     * @param pOutputFile the file where to write the extracted requirements.
     * @param pSortRequirements Flag indicating whether the requirements shall
     * be sorted or not in the output file.
     * @param pRequirementPrefixList the list of prefixes for requirements.
     * @param pExecutionStatus the execution status object.
     */
    public RequirementExtractor(final List<File> pInputFileList, final File pOutputFile,
            final boolean pSortRequirements, final List<String> pRequirementPrefixList,
            final ExecutorExecutionStatus<RequirementExtractorResult> pExecutionStatus) {
        super(pExecutionStatus);
        executionResult = null;
        inputFileList = new ArrayList<File>();
        if (pInputFileList != null) {
            inputFileList.addAll(pInputFileList);
        }
        outputFile = pOutputFile;
        sortRequirements = pSortRequirements;
        requirementPrefixList = new ArrayList<String>();
        if (pRequirementPrefixList != null) {
            // Only add prefixes that are not empty once trimmed from blanks
            String lTrimmedPrefix;
            for (String reqPrefix : pRequirementPrefixList) {
                lTrimmedPrefix = reqPrefix.trim();
                if (lTrimmedPrefix.length() > 0) {
                    requirementPrefixList.add(lTrimmedPrefix);
                }
            }
        }

        requirementDuplicationAnalysisData = new RequirementDuplicationAnalysisData();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performTask() throws AbstractTraceabilityException {
        // First, check the argument validity
        checkArguments();

        // Actually extract requirements
        extractRequirements();

        // Detect potential requirement duplicates
        identifyDuplicatedRequirements();
    }

    /**
     * Identify the potentially duplicated requirements.
     */
    private void identifyDuplicatedRequirements() {

        // Get the duplicated requirement list
        List<RequirementDuplicationItem> lDuplicatedRequirementList = requirementDuplicationAnalysisData
                .getDuplicatedRequirements();

        // Set it in the result object
        executionResult.setDuplicatedRequirements(lDuplicatedRequirementList);
    }

    /**
     * Checks the provided arguments and throw an exception if one is invalid.
     * 
     * @throws InvalidParameterException if an argument is invalid.
     */
    private void checkArguments() throws InvalidParameterException {
        // Ensure the parent of the output file exists if it is defined
        if (outputFile != null) {
            // Ensure that the parent directory of the output file exists
            File lOutputFileParentDir = outputFile.getParentFile();

            if (lOutputFileParentDir == null) {
                throw new InvalidParameterException("RequirementExtractor.performTask", "output directory "
                        + outputFile.getAbsolutePath(), "does not exist");
            } else {
                if (!lOutputFileParentDir.isDirectory()) {
                    throw new InvalidParameterException("RequirementExtractor.performTask", "output file",
                            "Parent directory " + lOutputFileParentDir.getAbsolutePath() + " does not exist");
                }
            }
        }

        // Ensure there are input files
        if (inputFileList.size() == 0) {
            throw new InvalidParameterException("RequirementExtractor.performTask", "input file list", "empty");
        } else {
            // Check that all the input files exist
            for (File inputFile : inputFileList) {
                if (!inputFile.isFile()) {
                    throw new InvalidParameterException("RequirementExtractor.performTask", "input file list",
                            "Input file " + inputFile.getAbsolutePath() + " does not exist");
                }
            }

            // Check that the requirement prefix list is not empty
            if (requirementPrefixList.size() == 0) {
                throw new InvalidParameterException("RequirementExtractor.performTask", "requirement prefix list",
                        "empty");
            }
        }
    }

    /**
     * Actually extract the requirements after parameter checks.
     * <p>
     * This means that the output file is defined, the input file list is not
     * empty and the requirement prefix list is not empty
     * </p>
     * 
     * @throws ExecutorExecutionException if an error occurs.
     */
    private void extractRequirements() throws ExecutorExecutionException {
        // Initialize the execution result
        executionResult = new RequirementExtractorResult();

        double lPercentage = 0.;
        double lPercentagePerFile = 70. / inputFileList.size();
        // Iterate on input file list
        for (File lInputFile : inputFileList) {
            setCurrentOperation("Processing file " + lInputFile.getAbsolutePath(), lPercentage);

            extractRequirementsFrom(lInputFile);

            lPercentage += lPercentagePerFile;
            setCurrentOperation("File processed : " + lInputFile.getAbsolutePath(), lPercentage);
        }

        // Write the output file with the requirements (if requested)
        if (outputFile != null) {
            setCurrentOperation("Writing output file " + outputFile.getAbsolutePath(), lPercentage);
            writeOutputFile();
        }

        // Set the execution result
        setExecutionResult(executionResult);

        // Set the number of found requirements in the execution status
        setSuccessfulStatusDetails("Found " + executionResult.getRequirementCount() + " requirements");
    }

    /**
     * Write the output file with the extracted requirements.
     * 
     * @throws ExecutorExecutionException if an error occurs.
     */
    private void writeOutputFile() throws ExecutorExecutionException {

        // First create a list with all the extracted requirements
        List<Requirement> lReqList = new ArrayList<Requirement>();
        for (File lInputFile : inputFileList) {
            FileRequirementExtractorResult lFileReqRes = executionResult
                    .getFileRequirementExtractionResultFor(lInputFile);
            lReqList.addAll(lFileReqRes.getRequirements());
        }

        if (sortRequirements) {
            // Sort the requirements in alphabetical order
            Collections.sort(lReqList);
        }

        FileOutputStream lFos = null;
        OutputStreamWriter lOsw = null;
        try {
            lFos = new FileOutputStream(outputFile);

            lOsw = new OutputStreamWriter(lFos);

            // Write all the requirements
            boolean lIsFirst = true;
            for (Requirement lReq : lReqList) {
                if (lIsFirst) {
                    lIsFirst = false;
                } else {
                    lOsw.write('\n');
                }
                lOsw.write(lReq.toString());
            }

        } catch (FileNotFoundException fnfe) {
            LOGGER.error("Error writing output file " + outputFile.getAbsolutePath() + " : " + fnfe.getMessage());
            throw new ExecutorExecutionException("Could not write output file");
        } catch (IOException ioe) {
            LOGGER.error("Error writing output file " + outputFile.getAbsolutePath() + " : " + ioe.getMessage());
            throw new ExecutorExecutionException("Could not write output file properly");
        } finally {
            if (lOsw != null) {
                try {
                    lOsw.close();
                } catch (Exception e) {
                    LOGGER.error("Error closing writer on output file " + outputFile.getAbsolutePath() + " : "
                            + e.getMessage());
                    // Don't throw an exception
                }
            }
            if (lFos != null) {
                try {
                    lFos.close();
                } catch (Exception e) {
                    LOGGER.error("Error closing output file " + outputFile.getAbsolutePath() + " : " + e.getMessage());
                    // Don't throw an exception
                }
            }
        }

    }

    /**
     * Extract the requirements from the given input file.
     * 
     * @param pInputFile the input file from where to extract requirements.
     * @throws ExecutorExecutionException if an error occurs
     */
    private void extractRequirementsFrom(final File pInputFile) throws ExecutorExecutionException {
        FileInputStream lFis = null;

        LOGGER.debug("Extracting requirements from file " + pInputFile.getAbsolutePath());

        // Try and open an input stream on the file
        try {
            lFis = new FileInputStream(pInputFile);
        } catch (Exception e) {
            LOGGER.error("Error opening an input stream on file " + pInputFile.getAbsolutePath() + " : "
                    + e.getMessage());
        }

        if (lFis != null) {
            XWPFDocument lDocx = null;

            try {
                // Deactivate the protection against zip bombing
                ZipSecureFile.setMinInflateRatio(0);

                // Open the document
                lDocx = new XWPFDocument(lFis);
            } catch (IOException e) {
                LOGGER.error("Error opening file " + pInputFile.getAbsolutePath() + " : " + e.getMessage());
            } finally {
                // Try and close the stream
                try {
                    lFis.close();
                } catch (Exception e) {
                    LOGGER.error("Error closing the input stream on file " + pInputFile.getAbsolutePath() + " : "
                            + e.getMessage());
                }
            }

            if (lDocx == null) {
                throw new ExecutorExecutionException("Could not open file " + pInputFile.getAbsolutePath());
            } else {
                // Get an iterator on document body elements
                Iterator<IBodyElement> lBodyEltIterator = lDocx.getBodyElementsIterator();
                IBodyElement lBodyElt;
                List<Requirement> lReqSet = new ArrayList<Requirement>();

                // Iterate on the document body elements
                while (lBodyEltIterator.hasNext()) {
                    lBodyElt = lBodyEltIterator.next();
                    // Only consider tables
                    if (lBodyElt instanceof XWPFTable) {
                        XWPFTable lTable = (XWPFTable) lBodyElt;

                        // Extract the requirements from this table
                        extractRequirementsFromTable(lTable, lReqSet, pInputFile);
                    }
                }

                // Add all the found requirements
                FileRequirementExtractorResult lFileRequirementExtractionResult = new FileRequirementExtractorResult(
                        pInputFile);
                for (Requirement req : lReqSet) {
                    lFileRequirementExtractionResult.addRequirement(req);

                    // Add the reference found of this requirement in the file
                    requirementDuplicationAnalysisData.referenceRequirement(req, pInputFile);
                }
                executionResult.addSdFileRequirementExtractionResult(lFileRequirementExtractionResult);

                StringBuilder lPrefixListDescripionSb = new StringBuilder();
                boolean lIsFirst = true;
                for (String lPrefix : requirementPrefixList) {
                    if (lIsFirst) {
                        lIsFirst = false;
                    } else {
                        lPrefixListDescripionSb.append(", ");
                    }
                    lPrefixListDescripionSb.append(lPrefix);
                }
                LOGGER.debug("File " + pInputFile.getName() + " contained "
                        + lFileRequirementExtractionResult.getRequirementCount() + " requirements matching prefixes ("
                        + lPrefixListDescripionSb.toString() + ")");
            }
        }

    }

    /**
     * Extract the requirements from the given table.
     * 
     * @param pTable the table to scan.
     * @param pReqSet the set where to store the extracted requirements.
     * @param pInputFile the input file
     */
    private void extractRequirementsFromTable(final XWPFTable pTable, final Collection<Requirement> pReqSet,
            final File pInputFile) {
        // Get the table rows
        List<XWPFTableRow> lRows = pTable.getRows();

        String lCellText;

        for (XWPFTableRow lRow : lRows) {
            // Get the row cells
            List<XWPFTableCell> lCells = lRow.getTableCells();

            if (lCells.size() >= Constants.REQ_EXTRACTION_TABLE_COL_IDX) {
                XWPFTableCell lCell = lCells.get(Constants.REQ_EXTRACTION_TABLE_COL_IDX - 1);
                if (lCell != null) {
                    lCellText = lCell.getText().trim();

                    if (lCellText.length() > 0) {
                        if (textMatchesReqPrefix(lCellText)) {
                            // Create and add the requirement
                            Requirement lReq = new Requirement(lCellText);
                            if (pReqSet.contains(lReq)) {
                                LOGGER.warn("Warning : duplicated requirement \"" + lReq + "\" found in "
                                        + pInputFile.getAbsolutePath());
                            }
                            pReqSet.add(lReq);
                        }
                    }
                }
            }
        }
    }

    /**
     * Check whether the given text matches a requirement.
     * 
     * @param pCellText the text to test.
     * @return true of the text matches a requirement, false otherwise.
     */
    private boolean textMatchesReqPrefix(final String pCellText) {
        boolean lMatchesReq = false;

        for (String lReqPrefix : requirementPrefixList) {
            if (pCellText.startsWith(lReqPrefix)) {
                lMatchesReq = true;
                break;
            }
        }

        return lMatchesReq;
    }

}
