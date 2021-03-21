/**
 * 
 */
package org.tools.doc.traceability;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.tools.doc.traceability.analyzer.cucumbertests.CucumberTestCoverageAnalyserTest;
import org.tools.doc.traceability.analyzer.cucumbertests.helper.GherkinStepBreakdownManagerTest;
import org.tools.doc.traceability.analyzer.unittests.java.JavaUnitTestCoverageAnalyserTest;
import org.tools.doc.traceability.common.filesearch.FileSearcherTest;
import org.tools.doc.traceability.common.gerkhin.GherkinInterpreterTest;
import org.tools.doc.traceability.common.gerkhin.GherkinParserTest;
import org.tools.doc.traceability.gui.configuration.TraceabilityToolConfigurationFileLoadingTest;
import org.tools.doc.traceability.manager.processor.TraceabilityManagerTester;
import org.tools.doc.traceability.testcoverage.UnitTestCoverageAnalyzerTest;

/**
 * Class to play all the JUnit tests in one go.
 * 
 * @author Yann Leglise
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ GherkinStepBreakdownManagerTest.class, CucumberTestCoverageAnalyserTest.class,
    JavaUnitTestCoverageAnalyserTest.class, FileSearcherTest.class, GherkinInterpreterTest.class,
    GherkinParserTest.class, TraceabilityToolConfigurationFileLoadingTest.class, TraceabilityManagerTester.class,
    UnitTestCoverageAnalyzerTest.class })
public class AllTests {

}
