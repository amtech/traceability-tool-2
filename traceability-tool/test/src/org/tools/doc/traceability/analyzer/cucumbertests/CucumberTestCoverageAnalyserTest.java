/**
 * 
 */
package org.tools.doc.traceability.analyzer.cucumbertests;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.tools.doc.traceability.analyzer.cucumbertests.model.CucumberTestsFileData;
import org.tools.doc.traceability.common.exceptions.InvalidFileSearchFilterException;
import org.tools.doc.traceability.common.exceptions.InvalidSimpleRegexpException;
import org.tools.doc.traceability.common.executor.ExecutionStatus;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.filesearch.FileSearchFilterSet;
import org.tools.doc.traceability.common.sregex.SimpleRegex;
import org.tools.doc.traceability.common.test.AbstractTester;

/**
 * JUnt for {@link CucumberTestCoverageAnalyser}.
 * 
 * @author Yann Leglise
 *
 */
public class CucumberTestCoverageAnalyserTest extends AbstractTester {

    /**
     * Test the call to the tool when providing an invalid test root directory.
     */
    @Test
    public void testWithInvalidTestRootDirectory() {
        ExecutorExecutionStatus<CucumberTestCoverageAnalyzerResult> lExecutorExecStatus = new ExecutorExecutionStatus<CucumberTestCoverageAnalyzerResult>();

        CucumberTestCoverageAnalyser lSut = new CucumberTestCoverageAnalyser(null, lExecutorExecStatus);

        try {
            lSut.run();

            Assert.assertEquals("The execution shall have ended with error with null file search filter set",
                    ExecutionStatus.ENDED_WITH_ERROR, lExecutorExecStatus.getCurrentExecutionStatus());
        } catch (Exception e) {
            Assert.fail("No exception was expected");
        }
    }

    /**
     * Test the call to the tool when providing valid arguments.
     */
    @Test
    public void testValidCase() {
        File lRootTestDirectory = new File(getInputFileDirectory(), "test-input-dir");

        ExecutorExecutionStatus<CucumberTestCoverageAnalyzerResult> lExecutorExecStatus = new ExecutorExecutionStatus<CucumberTestCoverageAnalyzerResult>();

        SimpleRegex lFeatureFilenameRegexp = null;
        try {
            lFeatureFilenameRegexp = new SimpleRegex("*");
        } catch (InvalidSimpleRegexpException e) {
            Assert.fail("Error creating simple regexp " + e.getMessage());
        }

        FileSearchFilterSet lFileSearchFilterSet = new FileSearchFilterSet();
        try {
            lFileSearchFilterSet.addFilter(lRootTestDirectory, false, lFeatureFilenameRegexp);
        } catch (InvalidFileSearchFilterException e) {
            Assert.fail("Error creating a file search filter set : " + e.getMessage());
        }

        CucumberTestCoverageAnalyser lSut = new CucumberTestCoverageAnalyser(lFileSearchFilterSet, lExecutorExecStatus);

        try {
            lSut.run();

            CucumberTestCoverageAnalyzerResult lExecutionResult = lExecutorExecStatus.getExecutionResult();

            if (lExecutionResult == null) {
                Assert.fail("The execution result is null while the execution ended in success");
            } else {
                List<CucumberTestsFileData> lAutomaticTestsFileDataList = lExecutionResult
                        .getCucumberTestsFileDataList();

                if (lAutomaticTestsFileDataList.size() == 0) {
                    System.out.println("No automatic file found");
                } else {
                    System.out.println(lAutomaticTestsFileDataList.toString());
                    for (CucumberTestsFileData lAtfd : lAutomaticTestsFileDataList) {
                        System.out.println("Data for file " + lAtfd.getSourceFile().getAbsolutePath() + " : " + lAtfd);
                    }
                }

                // Make sure the output file has been generated
                // if (!lOutputFile.isFile()) {
                // Assert.fail("No output file was generated");
                // }
            }
        } catch (Exception e) {
            Assert.fail("No exception was expected but this one occurred : " + e.getMessage());
        }
    }

}
