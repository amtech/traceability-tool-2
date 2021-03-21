/**
 * 
 */
package org.tools.doc.traceability.analyzer.cucumbertests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.tools.doc.traceability.analyzer.cucumbertests.helper.GherkinStepBreakdownManager;
import org.tools.doc.traceability.analyzer.cucumbertests.helper.TestingScenarioBreakdown;
import org.tools.doc.traceability.analyzer.cucumbertests.helper.TestingScenarioPart;
import org.tools.doc.traceability.analyzer.cucumbertests.model.CucumberTestData;
import org.tools.doc.traceability.analyzer.cucumbertests.model.CucumberTestsFileData;
import org.tools.doc.traceability.common.exceptions.AbstractTraceabilityException;
import org.tools.doc.traceability.common.exceptions.ExecutorExecutionException;
import org.tools.doc.traceability.common.exceptions.FileReadingException;
import org.tools.doc.traceability.common.exceptions.FileSearchException;
import org.tools.doc.traceability.common.exceptions.InvalidFileSearchFilterException;
import org.tools.doc.traceability.common.exceptions.InvalidGherkinContentsException;
import org.tools.doc.traceability.common.exceptions.InvalidParameterException;
import org.tools.doc.traceability.common.executor.AbstractExecutor;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.filesearch.FileSearchFilterSet;
import org.tools.doc.traceability.common.filesearch.FileSearcher;
import org.tools.doc.traceability.common.gerkhin.GherkinConstants;
import org.tools.doc.traceability.common.gerkhin.GherkinParser;
import org.tools.doc.traceability.common.gerkhin.model.GherkinFeatureFileContents;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinBackground;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinFeature;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinRule;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinExampleOrScenarioElement;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinExamplesOrScenariosElement;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinScenarioOutlineOrTemplateElement;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinStep;
import org.tools.doc.traceability.common.gerkhin.model.general.GherkinDataTable;
import org.tools.doc.traceability.common.gerkhin.model.general.GherkinDocString;
import org.tools.doc.traceability.common.gerkhin.model.step.GherkinStepGiven;
import org.tools.doc.traceability.common.model.Requirement;

/**
 * A tool parsing the feature files containing Cucumber tests and extracting the
 * included test and requirement coverage.
 * 
 * @author Yann Leglise
 *
 */
public class CucumberTestCoverageAnalyser extends AbstractExecutor<CucumberTestCoverageAnalyzerResult> {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LogManager.getLogger(CucumberTestCoverageAnalyser.class);

    /**
     * Gherking feature file parser.
     */
    private GherkinParser gherkinParser;

    /**
     * The utility that breaks down a list of steps into action/expected result
     * parts.
     */
    private GherkinStepBreakdownManager stepBreakdownManager;

    /**
     * The result object.
     */
    private CucumberTestCoverageAnalyzerResult resultObject;

    /**
     * The set of file search filters to select the .feature files to consider.
     */
    private final FileSearchFilterSet featureFileSearchFilterSet;

    /**
     * Constructor.
     * 
     * @param pFeatureFileSearchFilterSet the set of file search filters to
     * select the .feature files to consider.
     * @param pExecutionStatus the executor execution status.
     */
    public CucumberTestCoverageAnalyser(final FileSearchFilterSet pFeatureFileSearchFilterSet,
            final ExecutorExecutionStatus<CucumberTestCoverageAnalyzerResult> pExecutionStatus) {
        super(pExecutionStatus);
        featureFileSearchFilterSet = pFeatureFileSearchFilterSet;

        gherkinParser = new GherkinParser();
        stepBreakdownManager = new GherkinStepBreakdownManager();

        resultObject = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performTask() throws AbstractTraceabilityException {

        LOGGER.debug("Starting cucumber tests parsing");
        // First, check the provided arguments
        checkArguments();

        // Search and parse cucumber tests
        searchAndParseCucumberTests();
    }

    /**
     * Search for cucumber test files, and parse them.
     * <p>
     * Set the test result.
     * </p>
     * 
     * @throws ExecutorExecutionException if an error occurs during the
     * processing.
     */
    private void searchAndParseCucumberTests() throws ExecutorExecutionException {

        resultObject = new CucumberTestCoverageAnalyzerResult();

        double lCurrentPercentage = 5.0;
        setCurrentOperation("Searching for feature files in " + featureFileSearchFilterSet.toString(),
                lCurrentPercentage);

        // First search for the feature files under the cucumber test root
        // directory
        FileSearcher lFileSearcher = new FileSearcher(featureFileSearchFilterSet);
        List<File> lFeatureFileList;
        try {
            lFeatureFileList = lFileSearcher.search();

            double lPercentagePerFile = 70. / lFeatureFileList.size();
            // Iterate on each of them
            for (File lFeatureFile : lFeatureFileList) {
                // Process this file
                setCurrentOperation("Processing cucumber test result file " + lFeatureFile.getAbsolutePath(),
                        lCurrentPercentage);

                CucumberTestsFileData lCucumberTestsFileData = new CucumberTestsFileData(lFeatureFile);
                processFeatureFile(lCucumberTestsFileData);

                // Add it to the result object
                resultObject.addCucumberTestFileData(lCucumberTestsFileData);

                lCurrentPercentage += lPercentagePerFile;
            }

            setExecutionResult(resultObject);
        } catch (FileSearchException e) {
            LOGGER.error("Error searching file .feature files : " + e.getMessage());
            throw new ExecutorExecutionException("Error searching file .feature files : " + e.getMessage());
        }

    }

    /**
     * Process the given feature file.
     * 
     * @param pCucumberTestsFileData the cucumber test file data referring to
     * the feature file to process.
     * @throws ExecutorExecutionException if an error occurs
     */
    private void processFeatureFile(final CucumberTestsFileData pCucumberTestsFileData)
            throws ExecutorExecutionException {
        File lFeatureFile = pCucumberTestsFileData.getSourceFile();
        LOGGER.debug("Analyzing file " + lFeatureFile.getAbsolutePath() + " ...");

        try {
            // Parse the Gherkin file to get its contents
            GherkinFeatureFileContents lFeatureFileContents = gherkinParser.parseFile(lFeatureFile);

            // Extract the found scenario
            GherkinFeature lFeature = lFeatureFileContents.getFeature();

            // Fill the cucumber tests file data from the read feature
            fillCucumberTestsFileDataFrom(pCucumberTestsFileData, lFeature);

        } catch (FileReadingException fre) {
            throw new ExecutorExecutionException("Error parsing file " + lFeatureFile.getAbsolutePath() + " : "
                    + fre.getMessage());
        } catch (InvalidGherkinContentsException igce) {
            throw new ExecutorExecutionException("Invalid contents found in file " + lFeatureFile.getAbsolutePath()
                    + " : " + igce.getMessage());
        }
    }

    /**
     * Fill the cucumber tests file data from the given feature.
     * 
     * @param pAutomaticTestsFileData the cucumber test file data to fill.
     * @param pFeature the feature to get data from.
     * @throws InvalidGherkinContentsException if the contents of the Feature is
     * invalid
     */
    private void fillCucumberTestsFileDataFrom(final CucumberTestsFileData pAutomaticTestsFileData,
            final GherkinFeature pFeature) throws InvalidGherkinContentsException {

        // Get the Given steps of the potential Background element that must
        // precede any step of further scenario
        List<GherkinStepGiven> lBackgroundSteps = new ArrayList<GherkinStepGiven>();
        if (pFeature.getBackground() != null) {
            GherkinBackground lBackground = pFeature.getBackground();
            lBackgroundSteps.addAll(lBackground.getGivenSteps());
        }

        List<AbstractGherkinExampleOrScenarioElement> lExampleOrScenarioList;

        // Iterate on Rule elements
        List<GherkinRule> lRules = pFeature.getRules();
        for (GherkinRule lRule : lRules) {
            lExampleOrScenarioList = lRule.getExampleOrScenarioList();

            // Iterate on Scenario/Example elements
            for (AbstractGherkinExampleOrScenarioElement lExampleOrScenario : lExampleOrScenarioList) {
                fillCucumberTestsFileDataFrom(pAutomaticTestsFileData, pFeature, lExampleOrScenario, lBackgroundSteps);
            }
        }

        // Iterate on Scenario Outline/Scenario Template elements
        List<AbstractGherkinScenarioOutlineOrTemplateElement> lScenarioOutlineOrTemplateList = pFeature
                .getScenarioOutlineOrTemplateElements();
        for (AbstractGherkinScenarioOutlineOrTemplateElement lScenarioOutlineOrTemplate : lScenarioOutlineOrTemplateList) {
            fillCucumberTestsFileDataFrom(pAutomaticTestsFileData, pFeature, lScenarioOutlineOrTemplate,
                    lBackgroundSteps);
        }

        // Iterate on Scenario/Example elements
        List<AbstractGherkinExampleOrScenarioElement> lScenarioOrExampleList = pFeature.getExampleOrScenarioElements();
        for (AbstractGherkinExampleOrScenarioElement lExampleOrScenario : lScenarioOrExampleList) {
            fillCucumberTestsFileDataFrom(pAutomaticTestsFileData, pFeature, lExampleOrScenario, lBackgroundSteps);
        }
    }

    /**
     * Fill the cucumber tests file data from the given Scenario/Example.
     * 
     * @param pAutomaticTestsFileData the cucumber test file data to fill.
     * @param pFeature the parent Feature element.
     * @param pExampleOrScenario the Example/Scenario to analyze.
     * @param pBackgroundSteps the steps associated with the potential
     * Background element of the Scenario.
     * @throws InvalidGherkinContentsException if the contents of the Feature is
     * invalid
     */
    private void fillCucumberTestsFileDataFrom(final CucumberTestsFileData pAutomaticTestsFileData,
            final GherkinFeature pFeature, final AbstractGherkinExampleOrScenarioElement pExampleOrScenario,
            final List<GherkinStepGiven> pBackgroundSteps) throws InvalidGherkinContentsException {
        // Gather all the steps
        List<AbstractGherkinStep> lSteps = new ArrayList<AbstractGherkinStep>();

        // Gather all the potential background steps and the Scenario
        // Outline/Scenario Template steps.
        lSteps.addAll(pBackgroundSteps);
        lSteps.addAll(pExampleOrScenario.getSteps());

        // Break down the steps into parts
        TestingScenarioBreakdown lBreakdown = stepBreakdownManager.breakDownSteps(lSteps);
        List<TestingScenarioPart> lParts = lBreakdown.getTestingScenarioParts();

        // Iterate on the parts
        for (TestingScenarioPart lPart : lParts) {
            CucumberTestData lAutomaticTestData = createCucumberTestData(pFeature.getContainerDescription(),
                    pExampleOrScenario.getContainerDescription(), lPart, null);
            pAutomaticTestsFileData.getCucumberTestDataList().add(lAutomaticTestData);
        }
    }

    /**
     * Fill the cucumber tests file data from the given Scenario
     * Outline/Scenario Template.
     * 
     * @param pAutomaticTestsFileData the cucumber test file data to fill.
     * @param pFeature the parent Feature element.
     * @param pScenarioOutlineOrTemplate the Scenario Outline/Scenario Template
     * to analyze.
     * @param pBackgroundSteps the steps associated with the potential
     * Background element of the Scenario.
     * @throws InvalidGherkinContentsException if the contents of the Feature is
     * invalid
     */
    private void fillCucumberTestsFileDataFrom(final CucumberTestsFileData pAutomaticTestsFileData,
            final GherkinFeature pFeature,
            final AbstractGherkinScenarioOutlineOrTemplateElement pScenarioOutlineOrTemplate,
            final List<GherkinStepGiven> pBackgroundSteps) {
        // Gather all the steps
        List<AbstractGherkinStep> lSteps = new ArrayList<AbstractGherkinStep>();

        // Gather all the potential background steps and the Scenario
        // Outline/Scenario Template steps.
        lSteps.addAll(pBackgroundSteps);
        lSteps.addAll(pScenarioOutlineOrTemplate.getSteps());

        // Extract the description of the Examples or Scenarios
        AbstractGherkinExamplesOrScenariosElement lExamplesOrScenarios = pScenarioOutlineOrTemplate
                .getExamplesOrScenariosElement();

        StringBuilder lExamplesOrScenariosDescriptionSb = new StringBuilder(lExamplesOrScenarios.getContainerLine());
        GherkinDataTable lDataTable = lExamplesOrScenarios.getDataTable();
        for (String lTextLine : lDataTable.getTextLines()) {
            lExamplesOrScenariosDescriptionSb.append("\n\t");
            lExamplesOrScenariosDescriptionSb.append(lTextLine);
        }

        // Break down the steps into parts
        TestingScenarioBreakdown lBreakdown = stepBreakdownManager.breakDownSteps(lSteps);
        List<TestingScenarioPart> lParts = lBreakdown.getTestingScenarioParts();

        // Iterate on the parts
        for (TestingScenarioPart lPart : lParts) {
            CucumberTestData lAutomaticTestData = createCucumberTestData(pFeature.getContainerDescription(),
                    pScenarioOutlineOrTemplate.getContainerDescription(), lPart,
                    lExamplesOrScenariosDescriptionSb.toString());
            pAutomaticTestsFileData.getCucumberTestDataList().add(lAutomaticTestData);
        }
    }

    /**
     * Builds an cucumber test data from the given arguments.
     * 
     * @param pFeatureName The feature name.
     * @param pScenarioName The scenario name.
     * @param pTestingScenarioPart the testing scenario part.
     * @param pAdditionalActionText some potential additional text to complete
     * the action part (a '\n' will be automatically prepended). Can be
     * <tt>null</tt> if not needed.
     * @return the cucumber test data created from the arguments.
     */
    private CucumberTestData createCucumberTestData(final String pFeatureName, final String pScenarioName,
            final TestingScenarioPart pTestingScenarioPart, final String pAdditionalActionText) {
        // Create the action description
        StringBuilder lActionDescriptionSb = new StringBuilder();
        boolean lIsFrst = true;
        for (AbstractGherkinStep lActionStep : pTestingScenarioPart.getActionSteps()) {
            if (lIsFrst) {
                lIsFrst = false;
            } else {
                lActionDescriptionSb.append("\n");
            }
            addStepDescription(lActionStep, lActionDescriptionSb);
        }

        if (pAdditionalActionText != null) {
            lActionDescriptionSb.append("\n");
            lActionDescriptionSb.append(pAdditionalActionText);
        }

        // Create the expected result description
        StringBuilder lExpectedResultDescriptionSb = new StringBuilder();
        lIsFrst = true;
        for (AbstractGherkinStep lExpectedResultStep : pTestingScenarioPart.getExpectedResultSteps()) {
            // If not a step referencing SD requirements, just add its
            // description
            if (lIsFrst) {
                lIsFrst = false;
            } else {
                lExpectedResultDescriptionSb.append("\n");
            }
            addStepDescription(lExpectedResultStep, lExpectedResultDescriptionSb);
        }

        // Create the cucumber test data
        CucumberTestData lCucumberTestData = new CucumberTestData(pFeatureName, pScenarioName,
                pTestingScenarioPart.getPartIdentifier(), lActionDescriptionSb.toString(),
                lExpectedResultDescriptionSb.toString());

        // Add the potential covered requirements
        for (Requirement lReq : pTestingScenarioPart.getCoveredRequirements()) {
            lCucumberTestData.addCoveredRequirement(lReq);
        }

        return lCucumberTestData;
    }

    /**
     * Writes the description of the given step in the description string
     * builder.
     * 
     * @param pStep the step to consider.
     * @param pDescriptionSb the destination StringBuilder.
     */
    private void addStepDescription(final AbstractGherkinStep pStep, final StringBuilder pDescriptionSb) {
        pDescriptionSb.append(pStep.getStepLine());

        // Append the data table contents, if any
        if (pStep.hasAssociatedDataTable()) {
            GherkinDataTable lDataTable = pStep.getAssociatedDataTable();
            for (String lDataTableLine : lDataTable.getTextLines()) {
                pDescriptionSb.append("\n\t");
                pDescriptionSb.append(lDataTableLine);
            }
        }

        // Append the doc string contents, if any
        if (pStep.hasAssociatedDocString()) {
            GherkinDocString lDocString = pStep.getAssociatedDocString();
            pDescriptionSb.append("\n\t");
            pDescriptionSb.append(GherkinConstants.DOC_STRING_KEYWORD);
            for (String lDocStringLine : lDocString.getTextLines()) {
                pDescriptionSb.append("\n\t");
                pDescriptionSb.append(lDocStringLine);
            }
            pDescriptionSb.append("\n\t");
            pDescriptionSb.append(GherkinConstants.DOC_STRING_KEYWORD);
        }
    }

    /**
     * Checks the provided arguments and throw an exception if one is invalid.
     * 
     * @throws InvalidParameterException if an argument is invalid.
     */
    private void checkArguments() throws InvalidParameterException {
        setCurrentOperation("Checking arguments", 5.);
        // Ensure the feature file search filter set is defined
        if (featureFileSearchFilterSet == null) {
            throw new InvalidParameterException("CucumberTestCoverageAnalyzer.checkArguments",
                    "feature files search filter set", "null value");
        } else {
            // Ensure the set is valid
            try {
                featureFileSearchFilterSet.checkIsValid();
            } catch (InvalidFileSearchFilterException e) {
                throw new InvalidParameterException("CucumberTestCoverageAnalyzer.checkArguments",
                        "feature files search filter set", "one of the filters is invalid : " + e.getMessage());
            }
        }
    }
}
