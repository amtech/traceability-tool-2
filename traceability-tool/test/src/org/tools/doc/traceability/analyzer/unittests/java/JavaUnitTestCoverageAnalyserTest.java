/**
 * 
 */
package org.tools.doc.traceability.analyzer.unittests.java;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import org.tools.doc.traceability.common.exceptions.InvalidFileSearchFilterException;
import org.tools.doc.traceability.common.exceptions.InvalidSimpleRegexpException;
import org.tools.doc.traceability.common.executor.ExecutionStatus;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.filesearch.FileSearchFilter;
import org.tools.doc.traceability.common.filesearch.FileSearchFilterSet;
import org.tools.doc.traceability.common.sregex.SimpleRegex;
import org.tools.doc.traceability.common.test.AbstractTester;

/**
 * JUnit for {@link JavaUnitTestCoverageAnalyser}.
 * 
 * @author Yann Leglise
 *
 */
public class JavaUnitTestCoverageAnalyserTest extends AbstractTester {

    /**
     * Test method for
     * {@link org.tools.doc.traceability.analyzer.unittests.java.JavaUnitTestCoverageAnalyser#performTask()}
     * .
     */
    @Test
    public void testPerformTask() {

        File lUnitTestsDir = new File(getInputFileDirectory(), "unittests");
        File lJavaUnitTestsDir = new File(lUnitTestsDir, "java");

        FileSearchFilterSet lJavaFileSearchFilterSet = new FileSearchFilterSet();

        SimpleRegex lJavaMethodNameRegexp;
        try {
            lJavaMethodNameRegexp = new SimpleRegex("*");
        } catch (InvalidSimpleRegexpException e) {
            Assert.fail("Failed creating SimpleRegex for method name : " + e.getMessage());
            lJavaMethodNameRegexp = null;
        }
        FileSearchFilter lJavaFileSearchFilter = new FileSearchFilter(lJavaUnitTestsDir, true, lJavaMethodNameRegexp);
        try {
            lJavaFileSearchFilterSet.addFilter(lJavaFileSearchFilter);
        } catch (InvalidFileSearchFilterException e) {
            Assert.fail("Failed adding file filter : " + e.getMessage());
        }

        ExecutorExecutionStatus<JavaUnitTestCoverageAnalyserResult> lExecutionStatus = new ExecutorExecutionStatus<JavaUnitTestCoverageAnalyserResult>();
        SimpleRegex lTestMethodNameRegexpValue;
        try {
            lTestMethodNameRegexpValue = new SimpleRegex("*");
        } catch (InvalidSimpleRegexpException e) {
            Assert.fail("Could not create valid SimpleRegex for method name : " + e.getMessage());
            lTestMethodNameRegexpValue = null;
        }

        JavaUnitTestCoverageAnalyser lSut = new JavaUnitTestCoverageAnalyser(lJavaFileSearchFilterSet,
                lTestMethodNameRegexpValue, lExecutionStatus);

        lSut.run();

        Assert.assertEquals("Execution went wrong : " + lExecutionStatus.getExecutionStatusDescription(),
                ExecutionStatus.ENDED_SUCCESS, lExecutionStatus.getCurrentExecutionStatus());

        JavaUnitTestCoverageAnalyserResult lJavaTcaResult = lExecutionStatus.getExecutionResult();
        Assert.assertEquals("The number of test count is not as expected", 2, lJavaTcaResult.getFoundTestCount());

    }

}
