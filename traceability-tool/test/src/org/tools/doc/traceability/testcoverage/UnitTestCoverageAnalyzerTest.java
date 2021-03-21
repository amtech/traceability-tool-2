package org.tools.doc.traceability.testcoverage;

import java.io.File;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.tools.doc.traceability.analyzer.unittests.csharp.CSharpUnitTestCoverageAnalyserResult;
import org.tools.doc.traceability.analyzer.unittests.csharp.CSharpUnitTestCoverageAnalyzer;
import org.tools.doc.traceability.analyzer.unittests.csharp.model.CSharpUnitTestFileData;
import org.tools.doc.traceability.common.exceptions.InvalidSimpleRegexpException;
import org.tools.doc.traceability.common.executor.ExecutionStatus;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.sregex.SimpleRegex;
import org.tools.doc.traceability.common.test.AbstractTester;

/**
 * JUnit for {@link CSharpUnitTestCoverageAnalyzer}.
 * 
 * @author Yann Leglise
 *
 */
public class UnitTestCoverageAnalyzerTest extends AbstractTester {

    /**
     * Test the call to the tool when providing an invalid test root directory.
     */
    @Test
    public void testWithInvalidTestRootDirectory() {
        File lRootTestDirectory = null;

        SimpleRegex lXmlFilenameRegexp = null;
        SimpleRegex lTestNameRegexp = null;

        try {
            lXmlFilenameRegexp = new SimpleRegex("*");
            lTestNameRegexp = new SimpleRegex("*");
        } catch (InvalidSimpleRegexpException e) {
            Assert.fail("Error creating Simple regexp " + e.getMessage());
        }

        ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult> lExecutorExecStatus = new ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult>();

        CSharpUnitTestCoverageAnalyzer lSut = new CSharpUnitTestCoverageAnalyzer(lRootTestDirectory,
                lXmlFilenameRegexp, lTestNameRegexp, lExecutorExecStatus);

        try {
            lSut.run();

            Assert.assertEquals("The execution shall have ended with error with null test root directory",
                    ExecutionStatus.ENDED_WITH_ERROR, lExecutorExecStatus.getCurrentExecutionStatus());
        } catch (Exception e) {
            Assert.fail("No exception was expected");
        }

        lRootTestDirectory = new File("C:\\notexistingdirectory\\notexistingsubdirectory");
        lSut = new CSharpUnitTestCoverageAnalyzer(lRootTestDirectory, lXmlFilenameRegexp, lTestNameRegexp,
                lExecutorExecStatus);

        try {
            lSut.run();

            Assert.assertEquals("The execution shall have ended with error with a not existing test root directory",
                    ExecutionStatus.ENDED_WITH_ERROR, lExecutorExecStatus.getCurrentExecutionStatus());
        } catch (Exception e) {
            Assert.fail("No exception was expected");
        }
    }

    /**
     * Test the call to the tool when providing an invalid XML filename regular
     * expression.
     */
    @Test
    public void testWithInvalidXmlFilenameRegexp() {
        File lRootTestDirectory = new File(getInputFileDirectory(), "test-input-dir");

        SimpleRegex lXmlFilenameRegexp = null;
        SimpleRegex lTestNameRegexp = null;

        try {
            lTestNameRegexp = new SimpleRegex("*");
        } catch (InvalidSimpleRegexpException e) {
            Assert.fail("Error creating Simple regexp " + e.getMessage());
        }

        ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult> lExecutorExecStatus = new ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult>();

        CSharpUnitTestCoverageAnalyzer lSut = new CSharpUnitTestCoverageAnalyzer(lRootTestDirectory,
                lXmlFilenameRegexp, lTestNameRegexp, lExecutorExecStatus);

        try {
            lSut.run();

            Assert.assertEquals("The execution shall have ended with error with null XML filename regular expression",
                    ExecutionStatus.ENDED_WITH_ERROR, lExecutorExecStatus.getCurrentExecutionStatus());
        } catch (Exception e) {
            Assert.fail("No exception was expected");
        }
    }

    /**
     * Test the call to the tool when providing an invalid test name regular
     * expression.
     */
    @Test
    public void testWithInvalidTestNameRegexp() {
        File lRootTestDirectory = new File(getInputFileDirectory(), "test-input-dir");

        SimpleRegex lXmlFilenameRegexp = null;
        SimpleRegex lTestNameRegexp = null;

        try {
            lXmlFilenameRegexp = new SimpleRegex("*");
        } catch (InvalidSimpleRegexpException e) {
            Assert.fail("Error creating Simple regexp " + e.getMessage());
        }

        ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult> lExecutorExecStatus = new ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult>();

        CSharpUnitTestCoverageAnalyzer lSut = new CSharpUnitTestCoverageAnalyzer(lRootTestDirectory,
                lXmlFilenameRegexp, lTestNameRegexp, lExecutorExecStatus);

        try {
            lSut.run();

            Assert.assertEquals("The execution shall have ended with error with null test name regular expression",
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

        SimpleRegex lXmlFilenameRegexp = null;
        SimpleRegex lTestNameRegexp = null;

        try {
            lXmlFilenameRegexp = new SimpleRegex("*");
            lTestNameRegexp = new SimpleRegex("VTP_*");
        } catch (InvalidSimpleRegexpException e) {
            Assert.fail("Error creating Simple regexp " + e.getMessage());
        }

        ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult> lExecutorExecStatus = new ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult>();

        CSharpUnitTestCoverageAnalyzer lSut = new CSharpUnitTestCoverageAnalyzer(lRootTestDirectory,
                lXmlFilenameRegexp, lTestNameRegexp, lExecutorExecStatus);

        try {
            lSut.run();

            CSharpUnitTestCoverageAnalyserResult lExecutionResult = lExecutorExecStatus.getExecutionResult();

            if (lExecutionResult == null) {
                Assert.fail("The execution result is null while the execution ended in success");
            } else {
                Map<File, CSharpUnitTestFileData> lFileUnitTestDataMap = lExecutionResult.getFileUnitTestDataMap();

                for (File lParsedFile : lFileUnitTestDataMap.keySet()) {
                    System.out.println("Data for file " + lParsedFile.getAbsolutePath() + " : "
                            + lFileUnitTestDataMap.get(lParsedFile));
                }
            }
        } catch (Exception e) {
            Assert.fail("No exception was expected but this one occurred : " + e.getMessage());
        }
    }

    /**
     * Test the call to the tool when providing valid arguments, with a bunch of
     * xml files.
     */
    @Test
    public void testValidCaseRobustness() {
        File lRootTestDirectory = new File(getInputFileDirectory(), "xml");

        SimpleRegex lXmlFilenameRegexp = null;
        SimpleRegex lTestNameRegexp = null;

        try {
            lXmlFilenameRegexp = new SimpleRegex("*");
            lTestNameRegexp = new SimpleRegex("VTP_*");
        } catch (InvalidSimpleRegexpException e) {
            Assert.fail("Error creating Simple regexp " + e.getMessage());
        }

        ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult> lExecutorExecStatus = new ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult>();

        CSharpUnitTestCoverageAnalyzer lSut = new CSharpUnitTestCoverageAnalyzer(lRootTestDirectory,
                lXmlFilenameRegexp, lTestNameRegexp, lExecutorExecStatus);

        try {
            lSut.run();
            CSharpUnitTestCoverageAnalyserResult lExecutionResult = lExecutorExecStatus.getExecutionResult();

            if (lExecutionResult == null) {
                Assert.fail("The execution result is null while the execution ended in success");
            } else {
                Map<File, CSharpUnitTestFileData> lFileUnitTestDataMap = lExecutionResult.getFileUnitTestDataMap();

                for (File lParsedFile : lFileUnitTestDataMap.keySet()) {
                    System.out.println("Data for file " + lParsedFile.getAbsolutePath() + " : "
                            + lFileUnitTestDataMap.get(lParsedFile));
                }
            }
        } catch (Exception e) {
            Assert.fail("No exception was expected but this one occurred : " + e.getMessage());
        }
    }

    /**
     * Test the call to the tool with a specific file for analysis.
     */
    @Test
    public void testAnalysis() {
        File lRootTestDirectory = new File(getInputFileDirectory(), "test-coverage");

        SimpleRegex lXmlFilenameRegexp = null;
        SimpleRegex lTestNameRegexp = null;

        try {
            lXmlFilenameRegexp = new SimpleRegex("*");
            lTestNameRegexp = new SimpleRegex("VTP_*");
        } catch (InvalidSimpleRegexpException e) {
            Assert.fail("Error creating Simple regexp " + e.getMessage());
        }

        ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult> lExecutorExecStatus = new ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult>();

        CSharpUnitTestCoverageAnalyzer lSut = new CSharpUnitTestCoverageAnalyzer(lRootTestDirectory,
                lXmlFilenameRegexp, lTestNameRegexp, lExecutorExecStatus);

        try {
            lSut.run();
            CSharpUnitTestCoverageAnalyserResult lExecutionResult = lExecutorExecStatus.getExecutionResult();

            if (lExecutionResult == null) {
                Assert.fail("The execution result is null while the execution ended in success");
            } else {
                Map<File, CSharpUnitTestFileData> lFileUnitTestDataMap = lExecutionResult.getFileUnitTestDataMap();

                for (File lParsedFile : lFileUnitTestDataMap.keySet()) {
                    System.out.println("Data for file " + lParsedFile.getAbsolutePath() + " : "
                            + lFileUnitTestDataMap.get(lParsedFile));
                }
            }
        } catch (Exception e) {
            Assert.fail("No exception was expected but this one occurred : " + e.getMessage());
        }
    }
}
