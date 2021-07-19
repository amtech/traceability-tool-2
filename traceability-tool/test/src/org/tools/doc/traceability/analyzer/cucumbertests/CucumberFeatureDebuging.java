/**
 * 
 */
package org.tools.doc.traceability.analyzer.cucumbertests;

import java.io.File;
import java.util.List;

import org.tools.doc.traceability.analyzer.cucumbertests.model.CucumberTestsFileData;
import org.tools.doc.traceability.common.exceptions.InvalidFileSearchFilterException;
import org.tools.doc.traceability.common.exceptions.InvalidSimpleRegexpException;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.filesearch.FileSearchFilterSet;
import org.tools.doc.traceability.common.sregex.SimpleRegex;
import org.tools.doc.traceability.common.test.AbstractTester;

/**
 * Just an entry point to debug cucumber test feature file parsing.
 * 
 * @author Yann
 *
 */
public final class CucumberFeatureDebuging extends AbstractTester {

    /**
     * Constructor.
     */
    private CucumberFeatureDebuging() {
    }

    private void testParsing() {
        File lRootTestDirectory = new File(getInputFileDirectory(), "debug");

        ExecutorExecutionStatus<CucumberTestCoverageAnalyzerResult> lExecutorExecStatus = new ExecutorExecutionStatus<CucumberTestCoverageAnalyzerResult>();

        SimpleRegex lFeatureFilenameRegexp = null;
        try {
            lFeatureFilenameRegexp = new SimpleRegex("*buggy*.feature");
            FileSearchFilterSet lFileSearchFilterSet = new FileSearchFilterSet();
            try {
                lFileSearchFilterSet.addFilter(lRootTestDirectory, false, lFeatureFilenameRegexp);

                CucumberTestCoverageAnalyser lSut = new CucumberTestCoverageAnalyser(lFileSearchFilterSet,
                        lExecutorExecStatus);

                try {
                    lSut.run();

                    CucumberTestCoverageAnalyzerResult lExecutionResult = lExecutorExecStatus.getExecutionResult();

                    if (lExecutionResult == null) {
                        System.err.println("The execution result is null while the execution ended in success");
                    } else {
                        List<CucumberTestsFileData> lAutomaticTestsFileDataList = lExecutionResult
                                .getCucumberTestsFileDataList();

                        if (lAutomaticTestsFileDataList.size() == 0) {
                            System.out.println("No automatic file found");
                        } else {
                            for (CucumberTestsFileData lAtfd : lAutomaticTestsFileDataList) {
                                System.out.println("Data for file " + lAtfd.getSourceFile().getAbsolutePath() + " : "
                                        + lAtfd);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("No exception was expected but this one occurred : " + e.getMessage());
                }
            } catch (InvalidFileSearchFilterException e) {
                System.err.println("Error creating a file search filter set : " + e.getMessage());
            }
        } catch (InvalidSimpleRegexpException e) {
            System.err.println("Error creating simple regexp " + e.getMessage());
        }
    }

    /**
     * Main entry point.
     * 
     * @param args the arguments.
     */
    public static void main(final String[] args) {
        CucumberFeatureDebuging lCfd = new CucumberFeatureDebuging();
        lCfd.testParsing();
    }

}
