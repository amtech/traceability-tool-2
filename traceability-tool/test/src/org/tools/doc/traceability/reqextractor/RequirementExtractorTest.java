/**
 * 
 */
package org.tools.doc.traceability.reqextractor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.tools.doc.traceability.common.executor.ExecutionStatus;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.test.AbstractTester;
import org.tools.doc.traceability.reqextraction.RequirementExtractorResult;
import org.tools.doc.traceability.reqextraction.RequirementExtractor;

/**
 * JUnit test for {@link RequirementExtractorTest}.
 * 
 * @author Yann Leglise
 *
 */
public class RequirementExtractorTest extends AbstractTester {

    /**
     * Test the case where the input file list parameter is invalid (null or empty).
     */
    @Test
    public void testInvalidInputFileList() {
        File lOutputFile = new File("C:\\outfile.txt");
        List<String> lReqPrefixList = new ArrayList<String>();
        lReqPrefixList.add("REQ");
        ExecutorExecutionStatus<RequirementExtractorResult> lExecutorExecStatus = new ExecutorExecutionStatus<RequirementExtractorResult>();

        // Instantiate the system under test
        RequirementExtractor lSut = new RequirementExtractor(null, lOutputFile, false, lReqPrefixList,
                lExecutorExecStatus);

        try {
            lSut.run();

            Assert.assertEquals("The execution shall have ended with error with null input file list",
                    ExecutionStatus.ENDED_WITH_ERROR, lExecutorExecStatus.getCurrentExecutionStatus());
        } catch (Exception e) {
            Assert.fail("No exception was expected");
        }

        lSut = new RequirementExtractor(new ArrayList<File>(), lOutputFile, false, lReqPrefixList, lExecutorExecStatus);

        try {
            lSut.run();

            Assert.assertEquals("The execution shall have ended with error with null input file list",
                    ExecutionStatus.ENDED_WITH_ERROR, lExecutorExecStatus.getCurrentExecutionStatus());
        } catch (Exception e) {
            Assert.fail("No exception was expected");
        }
    }

    /**
     * Test the case where the output file parameter is invalid (null or parent
     * directory does not exist).
     */
    @Test
    public void testInvalidOutputFile() {
        List<File> lInputFileList = new ArrayList<File>();
        File lSdPs = new File(getInputFileDirectory(), "SD_PS.docx");
        lInputFileList.add(lSdPs);
        List<String> lReqPrefixList = new ArrayList<String>();
        lReqPrefixList.add("REQ");
        ExecutorExecutionStatus<RequirementExtractorResult> lExecutorExecStatus = new ExecutorExecutionStatus<RequirementExtractorResult>();

        // Instantiate the system under test
        RequirementExtractor lSut = new RequirementExtractor(lInputFileList, null, false, lReqPrefixList,
                lExecutorExecStatus);

        try {
            lSut.run();

            Assert.assertEquals(
                    "The execution shall have ended successfully null output file (simply means no output is wanted)",
                    ExecutionStatus.ENDED_SUCCESS, lExecutorExecStatus.getCurrentExecutionStatus());
        } catch (Exception e) {
            Assert.fail("No exception was expected");
        }

        File lOutputFile = new File("C:\\notexistingparentdirectory\\outfile.txt");
        lSut = new RequirementExtractor(lInputFileList, lOutputFile, false, lReqPrefixList, lExecutorExecStatus);

        try {
            lSut.run();

            Assert.assertEquals(
                    "The execution shall have ended with error with an output file whose parent directory does not exist",
                    ExecutionStatus.ENDED_WITH_ERROR, lExecutorExecStatus.getCurrentExecutionStatus());
        } catch (Exception e) {
            Assert.fail("No exception was expected");
        }
    }

    /**
     * Test the case where the requirement prefix list parameter is invalid (null or
     * empty).
     */
    @Test
    public void testInvalidReqPrefixLIst() {
        List<File> lInputFileList = new ArrayList<File>();
        File lSdPs = new File(getInputFileDirectory(), "SD_PS.docx");
        lInputFileList.add(lSdPs);
        File lOutputFile = new File("C:\\outfile.txt");
        ExecutorExecutionStatus<RequirementExtractorResult> lExecutorExecStatus = new ExecutorExecutionStatus<RequirementExtractorResult>();

        // Instantiate the system under test
        RequirementExtractor lSut = new RequirementExtractor(lInputFileList, lOutputFile, false, null,
                lExecutorExecStatus);

        try {
            lSut.run();

            Assert.assertEquals("The execution shall have ended with error with null requirement prefix list",
                    ExecutionStatus.ENDED_WITH_ERROR, lExecutorExecStatus.getCurrentExecutionStatus());
        } catch (Exception e) {
            Assert.fail("No exception was expected");
        }

        List<String> lReqPrefixList = new ArrayList<String>();
        lSut = new RequirementExtractor(lInputFileList, lOutputFile, false, lReqPrefixList, lExecutorExecStatus);

        try {
            lSut.run();

            Assert.assertEquals("The execution shall have ended with error with empty requirement prefix list",
                    ExecutionStatus.ENDED_WITH_ERROR, lExecutorExecStatus.getCurrentExecutionStatus());
        } catch (Exception e) {
            Assert.fail("No exception was expected");
        }
    }

    /**
     * Test the case where the executor execution status parameter is invalid
     * (null).
     */
    @Test
    public void testInvalidExecutorExecutionStatus() {
        List<File> lInputFileList = new ArrayList<File>();
        File lSdPs = new File(getInputFileDirectory(), "SD_PS.docx");
        lInputFileList.add(lSdPs);
        File lOutputFile = new File("C:\\outfile.txt");
        List<String> lReqPrefixList = new ArrayList<String>();
        lReqPrefixList.add("REQ");

        // Instantiate the system under test
        RequirementExtractor lSut = new RequirementExtractor(lInputFileList, lOutputFile, false, lReqPrefixList, null);

        try {
            lSut.run();

            // No way to do differently
        } catch (Exception e) {
            // Fine :)
        }
    }

    /**
     * Test a valid case.
     */
    @Test
    public void testValidCase() {

        File lSdPs = new File(getInputFileDirectory(), "SD_PS.docx");
        File lSdTst = new File(getInputFileDirectory(), "SD_TST.docx");

        List<File> lInputFileList = new ArrayList<File>();
        lInputFileList.add(lSdPs);
        lInputFileList.add(lSdTst);

        File lOutputDir = new File(getOutputFileDirectory(), "reqextractor");
        File lOutputFile = new File(lOutputDir, "extracted_req.txt");
        List<String> lReqPrefixList = new ArrayList<String>();
        lReqPrefixList.add("SD-ALB");
        lReqPrefixList.add("REQ-FIRST");
        lReqPrefixList.add("REQ-SECOND");
        lReqPrefixList.add("REQ-THIRD");
        ExecutorExecutionStatus<RequirementExtractorResult> lExecutorExecStatus = new ExecutorExecutionStatus<RequirementExtractorResult>();

        // Instantiate the system under test
        RequirementExtractor lSut = new RequirementExtractor(lInputFileList, lOutputFile, false, lReqPrefixList,
                lExecutorExecStatus);

        try {
            lSut.run();

            // Ensure the status is OK
            Assert.assertEquals("The execution shall have ended with success", ExecutionStatus.ENDED_SUCCESS,
                    lExecutorExecStatus.getCurrentExecutionStatus());

            Assert.assertEquals("The number of extracted requirements is incorret", 601 + 12,
                    lExecutorExecStatus.getExecutionResult().getRequirementCount());

            // Check the details
            Assert.assertEquals(
                    "The number of requirements for " + lSdPs.getAbsolutePath() + " is incorrect", lExecutorExecStatus
                            .getExecutionResult().getFileRequirementExtractionResultFor(lSdPs).getRequirementCount(),
                    601);

            // Check the details
            Assert.assertEquals(
                    "The number of requirements for " + lSdTst.getAbsolutePath() + " is incorrect", lExecutorExecStatus
                            .getExecutionResult().getFileRequirementExtractionResultFor(lSdTst).getRequirementCount(),
                    12);
        } catch (Exception e) {
            Assert.fail("No exception was expected in valid case : " + e.getMessage());
        }
    }
}
