package org.tools.doc.traceability.common.gerkhin;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.tools.doc.traceability.common.exceptions.FileReadingException;
import org.tools.doc.traceability.common.exceptions.InvalidGherkinContentsException;
import org.tools.doc.traceability.common.gerkhin.model.GherkinFeatureFileContents;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinExample;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinFeature;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinRule;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinExampleOrScenarioElement;
import org.tools.doc.traceability.common.test.AbstractTester;

/**
 * JUnit for {@link GherkinParser}.
 * @author Yann Leglise
 *
 */
public class GherkinParserTest extends AbstractTester {

    /**
     * Test {@link GherkinParser#parseFile(File)} with valid files.
     */
    @Test
    public void testParseFileValidFiles() {

        File lCucumberDirectory = new File(getInputFileDirectory(), "gherkin");
        File lSourceFile1 = new File(lCucumberDirectory, "ValidExample1.feature");

        GherkinParser lSut = new GherkinParser();

        GherkinFeatureFileContents lFeatureFileContents1 = parseValidFile(lSut, lSourceFile1);

        // Check the read feature contents
        GherkinFeature lFeature = lFeatureFileContents1.getFeature();
        Assert.assertEquals("Fight Club", lFeature.getContainerDescription());

        List<GherkinRule> lRules = lFeature.getRules();
        Assert.assertEquals("The number of parsed Rule: is incorrect", 2, lRules.size());

        GherkinRule lFirstRule = lRules.get(0);
        Assert.assertEquals("Never talk about the Fight Club", lFirstRule.getContainerDescription());

        List<AbstractGherkinExampleOrScenarioElement> lExamplesOrScenarios = lFirstRule.getExampleOrScenarioList();

        Assert.assertEquals("The number of Example/Scenario under the first Rule is not as expected", 2,
                lExamplesOrScenarios.size());

        AbstractGherkinExampleOrScenarioElement lExampleOrScenario = lExamplesOrScenarios.get(0);
        GherkinExample lExample;

        if (lExampleOrScenario instanceof GherkinExample) {
            lExample = (GherkinExample) lExampleOrScenario;

            Assert.assertEquals("Someone talks about it", lExample.getContainerDescription());

            Assert.assertEquals("The number of parsed steps on the first Example of the first Rule is not as expected",
                    4, lExample.getSteps().size());

            // TODO continue
        } else {
            Assert.fail("Element under first Rule was expected to be an Example but is a "
                    + lExampleOrScenario.getContainerKeyword());
        }

        File lSourceFile2 = new File(lCucumberDirectory, "ValidExample2.feature");

        // TODO test contents
        // GherkinFeatureFileContents lFeatureFileContents2 =
        parseValidFile(lSut, lSourceFile2);

    }

    /**
     * Test {@link GherkinParser#parseFile(File)} with valid real  file.
     */
    @Test
    public void testParseFileValidRealFile() {

        File lCucumberDirectory = new File(getInputFileDirectory(), "gherkin");
        File lSourceFile1 = new File(lCucumberDirectory, "DocumentDatasetWorkflow.feature");

        GherkinParser lSut = new GherkinParser();

        GherkinFeatureFileContents lFeatureFileContents1 = parseValidFile(lSut, lSourceFile1);

        // Check the read feature contents
        GherkinFeature lFeature = lFeatureFileContents1.getFeature();
        
        lFeature.getExampleOrScenarioElements();
    }
    
    
    /**
     * Test {@link GherkinParser#parseFile(File)} with invalid files.
     */
    @Test
    public void testParseFileInvalidFiles() {

        File lCucumberDirectory = new File(getInputFileDirectory(), "gherkin");
        File lSourceFile1 = new File(lCucumberDirectory, "InvalidExample1.feature");

        GherkinParser lSut = new GherkinParser();

        parseInvalidFile(lSut, lSourceFile1);

        File lSourceFile2 = new File(lCucumberDirectory, "InvalidExample2.feature");

        parseInvalidFile(lSut, lSourceFile2);

    }

    /**
     * Test the parsing of a valid Gherking file.
     * 
     * @param pSut the system under test
     * @param pSourceFile the Gherkin file to read.
     * @return the feature file contents.
     */
    private GherkinFeatureFileContents parseValidFile(final GherkinParser pSut, final File pSourceFile) {
        GherkinFeatureFileContents lFeatureFileContents = null;

        try {
            lFeatureFileContents = pSut.parseFile(pSourceFile);

            // Ensure the returned element is not null
            Assert.assertNotNull("The returned element shall not be null", lFeatureFileContents);

            // Ensure the associated source file is correct
            Assert.assertEquals("The Gherkin source file has not been set properly in the returned object",
                    pSourceFile, lFeatureFileContents.getGherkinFeatureSourceFile());

            GherkinFeature lFeature = lFeatureFileContents.getFeature();

            // Ensure the Feature is not null
            Assert.assertNotNull("The returned Feature shall not be null", lFeature);

            // Print the Feature contents
            // System.out.println("\n#################################################################");
            // lFeatureFileContents.printOnStdout();
        } catch (InvalidGherkinContentsException | FileReadingException e) {
            System.err.println(e);
            Assert.fail("Unexpected failure (" + e.getClass().getSimpleName() + ") :" + e.getMessage());
        }

        return lFeatureFileContents;
    }

    /**
     * Test the parsing of an invalid Gherking file.
     * 
     * @param pSut the system under test
     * @param pSourceFile the Gherkin file to read.
     */
    private void parseInvalidFile(final GherkinParser pSut, final File pSourceFile) {

        try {
            pSut.parseFile(pSourceFile);
            Assert.fail("The parsing shall not have been successful for " + pSourceFile.getName());
        } catch (InvalidGherkinContentsException e) {
            // What we expect
        } catch (FileReadingException e) {
            Assert.fail("Could not read file " + pSourceFile.getName() + " : " + e.getMessage());
        }

    }
}
