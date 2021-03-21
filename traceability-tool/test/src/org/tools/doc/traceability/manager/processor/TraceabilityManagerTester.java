/**
 * 
 */
package org.tools.doc.traceability.manager.processor;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import org.tools.doc.traceability.common.exceptions.InvalidFileSearchFilterException;
import org.tools.doc.traceability.common.exceptions.InvalidSimpleRegexpException;
import org.tools.doc.traceability.common.executor.ExecutionStatus;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.filesearch.FileSearchFilter;
import org.tools.doc.traceability.common.sregex.SimpleRegex;
import org.tools.doc.traceability.common.test.AbstractTester;

/**
 * JUnit for {@link TraceabilityManager}.
 * 
 * @author Yann Leglise
 *
 */
public class TraceabilityManagerTester extends AbstractTester {

    /**
     * Test method for
     * {@link org.tools.doc.traceability.common.executor.AbstractExecutor#run()}
     * .
     */
    @Test
    public void testRun() {

        File lGitRootDir = new File(getInputFileDirectory(), "git-base-dir");
        File lGeneralDir = new File(lGitRootDir, "general");
        File lJustificationsDir = new File(lGeneralDir, "justifications");
        File lJustificationFile = new File(lJustificationsDir, "justifications.xlsx");

        File lFullToolDir = new File(lGitRootDir, "full-app");

        File lFullToolCodeDir = new File(lFullToolDir, "code");

        File lCSharpRootDirectory = new File(lFullToolCodeDir, "CSharp");
        File lCucumberRootDirectory = new File(lFullToolCodeDir, "cucumber");
        File lJavaRootDirectory = new File(lFullToolCodeDir, "java");

        File lAlmDir = new File(lFullToolDir, "ALM_extract");
        File lAlmExtractFile = new File(lAlmDir, "VTP_FULL.xlsm");

        File lFullDocDir = new File(lFullToolDir, "Documentation");

        File lFullSdDir = new File(lFullDocDir, "SD");
        File lFullSd = new File(lFullSdDir, "SD-FULL.docx");

        File lFullVtpDir = new File(lFullDocDir, "VTP");
        File lFullVtp = new File(lFullVtpDir, "VTP-FULL.xlsx");

        File lFullSdReqDir = new File(lFullDocDir, "SD_Reqs");
        File lOutputExtractedRequirementsFile = new File(lFullSdReqDir, "full_reqs.txt");

        File lFullTmDir = new File(lFullDocDir, "TM");
        File lOutputTraceabilityMatrix = new File(lFullTmDir, "full_matrix.xlsx");

        TraceabilityManagerContext lContext = new TraceabilityManagerContext();

        lContext.setAlmTestsExtractFile(lAlmExtractFile);
        lContext.setJustificationFile(lJustificationFile);
        try {
            lContext.setCSharpMethodRegexp("VTP_*");
        } catch (InvalidSimpleRegexpException e) {
            Assert.fail("Could not create simple regexp for C# method " + e.getMessage());
        }
        lContext.setOutputExtractedRequirementsFile(lOutputExtractedRequirementsFile);
        lContext.setOutputVtpFile(lFullVtp);
        lContext.setOutputTraceabilityMatrixFile(lOutputTraceabilityMatrix);

        lContext.addRequirementPrefix("REQ-FULL");
        lContext.addSpecificationDossier(lFullSd);

        SimpleRegex lCSharpFileNameRegexp = null;
        try {
            lCSharpFileNameRegexp = new SimpleRegex("*.xml");
        } catch (InvalidSimpleRegexpException e) {
            Assert.fail("Could not create simple regexp for C# file " + e.getMessage());
        }
        FileSearchFilter lCSharpFileSearchFilter = new FileSearchFilter(lCSharpRootDirectory, true,
                lCSharpFileNameRegexp);
        try {
            lContext.addCSharpFileSearchFilter(lCSharpFileSearchFilter);
        } catch (InvalidFileSearchFilterException e) {
            Assert.fail("Could not add searct filter for C# file " + e.getMessage());
        }

        SimpleRegex lFeatureFileNameRegexp = null;
        try {
            lFeatureFileNameRegexp = new SimpleRegex("*.feature");
        } catch (InvalidSimpleRegexpException e) {
            Assert.fail("Could not create simple regexp for feature file " + e.getMessage());
        }
        FileSearchFilter lCucumberFileSearchFilter = new FileSearchFilter(lCucumberRootDirectory, true,
                lFeatureFileNameRegexp);
        try {
            lContext.addCucumberFileSearchFilter(lCucumberFileSearchFilter);
        } catch (InvalidFileSearchFilterException e) {
            Assert.fail("Could not add searct filter for feature file " + e.getMessage());
        }

        SimpleRegex lJavaFileNameRegexp = null;
        try {
            lJavaFileNameRegexp = new SimpleRegex("*.java");
        } catch (InvalidSimpleRegexpException e) {
            Assert.fail("Could not create simple regexp for java file " + e.getMessage());
        }
        FileSearchFilter lJavaFileSearchFilter = new FileSearchFilter(lJavaRootDirectory, true, lJavaFileNameRegexp);
        try {
            lContext.addJavaFileSearchFilter(lJavaFileSearchFilter);
        } catch (InvalidFileSearchFilterException e) {
            Assert.fail("Could not add searct filter for java file " + e.getMessage());
        }

        ExecutorExecutionStatus<TraceabilityManagerResultObject> lExecutionStatus = new ExecutorExecutionStatus<TraceabilityManagerResultObject>();

        TraceabilityManager lSut = new TraceabilityManager(lContext, lExecutionStatus);

        try {
            lSut.run();

            if (lExecutionStatus.getCurrentExecutionStatus() == ExecutionStatus.ENDED_SUCCESS) {
                System.out.println("Success");
            } else {
                Assert.fail("Execution failed : " + lExecutionStatus.getExecutionStatusDescription());
            }
        } catch (Exception e) {
            Assert.fail("Execution failed due to exception : " + e.getMessage());
        }

    }

}
