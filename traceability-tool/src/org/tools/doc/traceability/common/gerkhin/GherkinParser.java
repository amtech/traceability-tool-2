package org.tools.doc.traceability.common.gerkhin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.common.exceptions.FileReadingException;
import org.tools.doc.traceability.common.exceptions.InvalidGherkinContentsException;
import org.tools.doc.traceability.common.gerkhin.model.GherkinFeatureFileContents;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinBackground;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinExample;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinExamples;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinFeature;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinRule;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinScenario;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinScenarioOutline;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinScenarioTemplate;
import org.tools.doc.traceability.common.gerkhin.model.container.GherkinScenarios;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinExampleOrScenarioElement;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinExamplesOrScenariosElement;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinScenarioOutlineOrTemplateElement;
import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinStep;
import org.tools.doc.traceability.common.gerkhin.model.general.GherkinAdditionalDescription;
import org.tools.doc.traceability.common.gerkhin.model.general.GherkinComment;
import org.tools.doc.traceability.common.gerkhin.model.general.GherkinDataTable;
import org.tools.doc.traceability.common.gerkhin.model.general.GherkinDocString;
import org.tools.doc.traceability.common.gerkhin.model.step.GherkinStepAnd;
import org.tools.doc.traceability.common.gerkhin.model.step.GherkinStepBut;
import org.tools.doc.traceability.common.gerkhin.model.step.GherkinStepGiven;
import org.tools.doc.traceability.common.gerkhin.model.step.GherkinStepStar;
import org.tools.doc.traceability.common.gerkhin.model.step.GherkinStepThen;
import org.tools.doc.traceability.common.gerkhin.model.step.GherkinStepWhen;
import org.tools.doc.traceability.common.io.FileLinesReader;

/**
 * Parser for Gherkin scenario files.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinParser {

    /**
     * The file lines reader.
     */
    private final FileLinesReader fileLinesReader;

    /**
     * Instance of class converting raw lines into Gherkin lines.
     */
    private final GherkinInterpreter gerkhinInterpreter;

    /**
     * Constructor.
     */
    public GherkinParser() {
        fileLinesReader = new FileLinesReader();
        gerkhinInterpreter = new GherkinInterpreter();
    }

    /**
     * Try and parse the given Gherkin feature file.
     * 
     * @param pGherkinSourceFile the Gherkin feature file to parse.
     * @return the {@link GherkinFeatureFileContents} modeling the parsed file.
     * @throws InvalidGherkinContentsException if it was not possible to parse
     * the file because its contents was not valid.
     * @throws FileReadingException if an error occurred while reading the file.
     */
    public GherkinFeatureFileContents parseFile(final File pGherkinSourceFile) throws InvalidGherkinContentsException,
            FileReadingException {
        GherkinFeatureFileContents lGherkinFeatureFileContents = null;

        List<String> lFileLines;

        // Try and read file contents (If it fails FileReadingException is
        // thrown)
        lFileLines = fileLinesReader.readFileContents(pGherkinSourceFile);

        // Convert the raw lines into Gherkin lines
        List<GherkinLine> lGherkinLines = gerkhinInterpreter.convertRawLines(lFileLines);

        // Try and convert the Gherkin lines into a Gherkin feature (if it
        // fails,
        // InvalidGherkinContentsException is thrown)
        GherkinFeature lFeature = null;

        int lMaxLineIdx = lGherkinLines.size();
        int lLineIdx = 0;
        GherkinLine lLine;

        // Try and find the line matching a Feature
        // There is potentially comments before finding it
        List<GherkinComment> lFeatureComments = new ArrayList<GherkinComment>();

        while (lLineIdx < lMaxLineIdx) {
            lLine = lGherkinLines.get(lLineIdx);
            if (lLine.getType() == GherkinLineType.Comment) {
                GherkinComment lComment = new GherkinComment(lLineIdx + 1, lLine.getLineContents());
                lFeatureComments.add(lComment);
            } else if (lLine.getType() == GherkinLineType.Feature) {
                lFeature = new GherkinFeature(lLineIdx + 1, lLine.getLineContents());
                break;
            }
            // Switch to next line
            lLineIdx++;
        }

        // Handle error of absent Feature line
        if (lFeature == null) {
            throw new InvalidGherkinContentsException("No " + GherkinConstants.FEATURE_KEYWORD + " line found");
        } else {
            // The Feature line has been found : switch to the next line
            lLineIdx++;
        }

        lGherkinFeatureFileContents = new GherkinFeatureFileContents(pGherkinSourceFile);
        lGherkinFeatureFileContents.setFeature(lFeature);

        // Append the the potential leading comments
        for (GherkinComment lFeatureComment : lFeatureComments) {
            lGherkinFeatureFileContents.addLeadingCommentLine(lFeatureComment);
        }

        // Manage the potential additional description or comments attached to
        // the
        // Feature line
        while (lLineIdx < lMaxLineIdx) {
            lLine = lGherkinLines.get(lLineIdx);
            if (lLine.getType() == GherkinLineType.Description) {
                GherkinAdditionalDescription lAdditionalDescription = new GherkinAdditionalDescription(lLineIdx + 1,
                        lLine.getLineContents());
                lFeature.addAdditionalDescription(lAdditionalDescription);
                lLineIdx++;
            } else if (lLine.getType() == GherkinLineType.Comment) {
                GherkinComment lComment = new GherkinComment(lLineIdx + 1, lLine.getLineContents());
                lFeature.addComment(lComment);
                lLineIdx++;
            } else {
                break;
            }
        }

        // Now let's search for one of the possible elements under a Feature
        while (lLineIdx < lMaxLineIdx) {
            lLine = lGherkinLines.get(lLineIdx);

            switch (lLine.getType()) {
                case Background:
                    // Consume lines associated with the Background element
                    GherkinElementExtractionResult<GherkinBackground> lExtractedBackground = extractBackgroundFrom(
                            lGherkinLines, lLineIdx);

                    if (lExtractedBackground.wasElementExtracted()) {

                        // Check that the feature doesn't already have a
                        // Background
                        if (lFeature.getBackground() != null) {
                            throw new InvalidGherkinContentsException("Presence of a duplicated "
                                    + GherkinConstants.BACKGROUND_KEYWORD + " at line "
                                    + lLine.getNormalizedRepresentation());
                        }

                        // Add the extracted Background to the feature
                        lFeature.setBackground(lExtractedBackground.getExtractedElement());

                        lLineIdx = lExtractedBackground.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Could not extract "
                                + GherkinConstants.BACKGROUND_KEYWORD + " at line "
                                + lLine.getNormalizedRepresentation());
                    }
                    break;

                case Scenario:
                    // Consume lines associated with the Scenario element
                    GherkinElementExtractionResult<GherkinScenario> lExtractedScenario = extractScenarioFrom(
                            lGherkinLines, lLineIdx);

                    if (lExtractedScenario.wasElementExtracted()) {
                        // Add the extracted Scenario to the feature
                        lFeature.addScenario(lExtractedScenario.getExtractedElement());

                        // Update the line index
                        lLineIdx = lExtractedScenario.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Could not extract "
                                + GherkinConstants.SCENARIO_KEYWORD + " at line " + lLine.getNormalizedRepresentation());
                    }
                    break;
                case Rule:
                    // Consume lines associated with the Rule element
                    GherkinElementExtractionResult<GherkinRule> lExtractedRule = extractRuleFrom(lGherkinLines,
                            lLineIdx);

                    if (lExtractedRule.wasElementExtracted()) {
                        // Add the extracted Rule to the feature
                        lFeature.addRule(lExtractedRule.getExtractedElement());

                        // Update the line index
                        lLineIdx = lExtractedRule.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Could not extract " + GherkinConstants.RULE_KEYWORD
                                + " at line " + lLine.getNormalizedRepresentation());
                    }
                    break;

                case ScenarioOutline:
                    // Consume lines associated with the Scenario Outline
                    // element
                    GherkinElementExtractionResult<GherkinScenarioOutline> lExtractedScenarioOutline = extractScenarioOutlineFrom(
                            lGherkinLines, lLineIdx);

                    if (lExtractedScenarioOutline.wasElementExtracted()) {
                        // Add the extracted Scenario Outline to the feature
                        lFeature.addScenarioOutline(lExtractedScenarioOutline.getExtractedElement());

                        // Update the line index
                        lLineIdx = lExtractedScenarioOutline.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Could not extract "
                                + GherkinConstants.SCENARIO_OUTLINE_KEYWORD + " at line "
                                + lLine.getNormalizedRepresentation());
                    }
                    break;

                case ScenarioTemplate:
                    // Consume lines associated with the Scenario Template
                    // element
                    GherkinElementExtractionResult<GherkinScenarioTemplate> lExtractedScenarioTemplate = extractScenarioTemplateFrom(
                            lGherkinLines, lLineIdx);

                    if (lExtractedScenarioTemplate.wasElementExtracted()) {
                        // Add the extracted Scenario Template to the feature
                        lFeature.addScenarioTemplate(lExtractedScenarioTemplate.getExtractedElement());

                        // Update the line index
                        lLineIdx = lExtractedScenarioTemplate.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Could not extract "
                                + GherkinConstants.SCENARIO_OUTLINE_KEYWORD + " at line "
                                + lLine.getNormalizedRepresentation());
                    }
                    break;
                default:
                    throw new InvalidGherkinContentsException(
                            "Found a line that does not match an element expected under a "
                                    + GherkinConstants.FEATURE_KEYWORD + " element (at line "
                                    + lLine.getSourceFileLineNumber() + " : [" + lLine.getNormalizedRepresentation()
                                    + "]");
            }

            // Go to next line
            lLineIdx++;
        }

        return lGherkinFeatureFileContents;
    }

    /**
     * Start creating a Scenario element from the given index of the Gherkin
     * lines.
     * 
     * <p>
     * This function shall be called only if the line at the given index matches
     * a Scenario line.
     * </p>
     * 
     * @param pGherkinLines the Gherkin lines to read from.
     * @param pLineIdx the index in the Gherkin lines list to start reading the
     * Scenario element.
     * @return the extraction result.
     * @throws InvalidGherkinContentsException if it was not possible to parse
     * the file because its contents was not valid.
     * 
     */
    private GherkinElementExtractionResult<GherkinScenario> extractScenarioFrom(final List<GherkinLine> pGherkinLines,
            final int pLineIdx) throws InvalidGherkinContentsException {

        GherkinLine lLine = pGherkinLines.get(pLineIdx);
        GherkinLineType lType;

        // Check the line type matches a Scenario
        lType = lLine.getType();
        if (lType != GherkinLineType.Scenario) {
            throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.SCENARIO_KEYWORD
                    + " from line " + lLine.getNormalizedRepresentation());
        }

        // Create the Scenario element
        GherkinScenario lScenario = new GherkinScenario(lLine.getSourceFileLineNumber(), lLine.getLineContents());

        // Extract the Scenario sub-elements from next lines
        int lIdx = extractScenarioOrExampleFrom(pGherkinLines, pLineIdx, lScenario);

        // Create the result element
        GherkinElementExtractionResult<GherkinScenario> lExtractionResult = new GherkinElementExtractionResult<GherkinScenario>(
                true, lScenario, lIdx);

        return lExtractionResult;
    }

    /**
     * Start creating an Example element from the given index of the Gherkin
     * lines.
     * 
     * <p>
     * This function shall be called only if the line at the given index matches
     * an Example line.
     * </p>
     * 
     * @param pGherkinLines the Gherkin lines to read from.
     * @param pLineIdx the index in the Gherkin lines list to start reading the
     * Example element.
     * @return the extraction result.
     * @throws InvalidGherkinContentsException if it was not possible to parse
     * the file because its contents was not valid.
     * 
     */
    private GherkinElementExtractionResult<GherkinExample> extractExampleFrom(final List<GherkinLine> pGherkinLines,
            final int pLineIdx) throws InvalidGherkinContentsException {

        GherkinLine lLine = pGherkinLines.get(pLineIdx);
        GherkinLineType lType;

        // Check the line type matches a Example
        lType = lLine.getType();
        if (lType != GherkinLineType.Example) {
            throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.EXAMPLE_KEYWORD
                    + " from line " + lLine.getNormalizedRepresentation());
        }

        // Create the Example element
        GherkinExample lExample = new GherkinExample(lLine.getSourceFileLineNumber(), lLine.getLineContents());

        // Extract the Example sub-elements from next lines
        int lIdx = extractScenarioOrExampleFrom(pGherkinLines, pLineIdx, lExample);

        // Create the result element
        GherkinElementExtractionResult<GherkinExample> lExtractionResult = new GherkinElementExtractionResult<GherkinExample>(
                true, lExample, lIdx);

        return lExtractionResult;
    }

    /**
     * Parse the lines after a Scenario or Example line, to attach to it all the
     * elements that depends on it.
     * 
     * @param pGherkinLines the Gherkin lines to read from.
     * @param pLineIdx the index in the Gherkin lines list where the
     * Scenario/Example element was found.
     * @param pScenarioOrExample the Scenario or Element to which sub-elements
     * shall be added.
     * @return the index of the last used line.
     * @throws InvalidGherkinContentsException if it was not possible to parse
     * the file because its contents was not valid.
     */
    private int extractScenarioOrExampleFrom(final List<GherkinLine> pGherkinLines, final int pLineIdx,
            final AbstractGherkinExampleOrScenarioElement pScenarioOrExample) throws InvalidGherkinContentsException {

        GherkinLine lLine;
        int lIdx = pLineIdx;
        GherkinLineType lType;

        // Go on with next line as long as we find elements that can occur
        // "under" the Scenario element
        boolean lReadNextLine = true;

        while (lReadNextLine) {
            lIdx++;
            if (lIdx == pGherkinLines.size()) {
                break;
            }
            lLine = pGherkinLines.get(lIdx);
            lType = lLine.getType();

            switch (lType) {
                case Description:
                    // Additional description to attach to the Example/Scenario
                    // element
                    GherkinAdditionalDescription lAddDescription = new GherkinAdditionalDescription(
                            lLine.getSourceFileLineNumber(), lLine.getLineContents());
                    pScenarioOrExample.addAdditionalDescription(lAddDescription);
                    break;
                case Comment:
                    // Add the comment associated with the Example/Scenario
                    // element
                    GherkinComment lComment = new GherkinComment(lLine.getSourceFileLineNumber(),
                            lLine.getLineContents());
                    pScenarioOrExample.addComment(lComment);
                    break;
                case Given:
                    GherkinElementExtractionResult<GherkinStepGiven> lExtractedGivenResult = extractStepFrom(
                            pGherkinLines, lIdx, lType);
                    if (lExtractedGivenResult.wasElementExtracted()) {
                        // Add the Given step
                        GherkinStepGiven lGiven = lExtractedGivenResult.getExtractedElement();
                        pScenarioOrExample.addStep(lGiven);

                        // Update the last line index
                        lIdx = lExtractedGivenResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.GIVEN_KEYWORD
                                + " from line " + lLine.getNormalizedRepresentation());
                    }
                    break;
                case And:
                    GherkinElementExtractionResult<GherkinStepAnd> lExtractedAndResult = extractStepFrom(pGherkinLines,
                            lIdx, lType);
                    if (lExtractedAndResult.wasElementExtracted()) {
                        // Add the And step
                        GherkinStepAnd lAnd = lExtractedAndResult.getExtractedElement();
                        pScenarioOrExample.addStep(lAnd);

                        // Update the last line index
                        lIdx = lExtractedAndResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.AND_KEYWORD
                                + " from line " + lLine.getNormalizedRepresentation());
                    }
                    break;
                case But:
                    GherkinElementExtractionResult<GherkinStepBut> lExtractedButResult = extractStepFrom(pGherkinLines,
                            lIdx, lType);
                    if (lExtractedButResult.wasElementExtracted()) {
                        // Add the But step
                        GherkinStepBut lBut = lExtractedButResult.getExtractedElement();
                        pScenarioOrExample.addStep(lBut);

                        // Update the last line index
                        lIdx = lExtractedButResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.BUT_KEYWORD
                                + " from line " + lLine.getNormalizedRepresentation());
                    }
                    break;
                case Star:
                    GherkinElementExtractionResult<GherkinStepStar> lExtractedStarResult = extractStepFrom(
                            pGherkinLines, lIdx, lType);
                    if (lExtractedStarResult.wasElementExtracted()) {
                        // Add the Star step
                        GherkinStepStar lStar = lExtractedStarResult.getExtractedElement();
                        pScenarioOrExample.addStep(lStar);

                        // Update the last line index
                        lIdx = lExtractedStarResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.STAR_KEYWORD
                                + " from line " + lLine.getNormalizedRepresentation());
                    }
                    break;

                case Then:
                    GherkinElementExtractionResult<GherkinStepThen> lExtractedThenResult = extractStepFrom(
                            pGherkinLines, lIdx, lType);
                    if (lExtractedThenResult.wasElementExtracted()) {
                        // Add the When step
                        GherkinStepThen lWhen = lExtractedThenResult.getExtractedElement();
                        pScenarioOrExample.addStep(lWhen);

                        // Update the last line index
                        lIdx = lExtractedThenResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.THEN_KEYWORD
                                + " from line " + lLine.getNormalizedRepresentation());
                    }
                    break;

                case When:
                    GherkinElementExtractionResult<GherkinStepWhen> lExtractedWhenResult = extractStepFrom(
                            pGherkinLines, lIdx, lType);
                    if (lExtractedWhenResult.wasElementExtracted()) {
                        // Add the When step
                        GherkinStepWhen lWhen = lExtractedWhenResult.getExtractedElement();
                        pScenarioOrExample.addStep(lWhen);

                        // Update the last line index
                        lIdx = lExtractedWhenResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.WHEN_KEYWORD
                                + " from line " + lLine.getNormalizedRepresentation());
                    }
                    break;

                default:
                    lReadNextLine = false;
                    break;
            }

        }

        return lIdx - 1;
    }

    /**
     * Start creating a Scenario Outline element from the given index of the
     * Gherkin lines.
     * 
     * <p>
     * This function shall be called only if the line at the given index matches
     * a Scenario Outline line.
     * </p>
     * 
     * @param pGherkinLines the Gherkin lines to read from.
     * @param pLineIdx the index in the Gherkin lines list to start reading the
     * Scenario Outline element.
     * @return the extraction result.
     * @throws InvalidGherkinContentsException if it was not possible to parse
     * the file because its contents was not valid.
     * 
     */
    private GherkinElementExtractionResult<GherkinScenarioOutline> extractScenarioOutlineFrom(
            final List<GherkinLine> pGherkinLines, final int pLineIdx) throws InvalidGherkinContentsException {

        GherkinLine lLine = pGherkinLines.get(pLineIdx);
        GherkinLineType lType;

        // Check the line type matches a Scenario Outline
        lType = lLine.getType();
        if (lType != GherkinLineType.ScenarioOutline) {
            throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.SCENARIO_OUTLINE_KEYWORD
                    + " from line " + lLine.getNormalizedRepresentation());
        }

        // Create the Scenario Outline element
        GherkinScenarioOutline lScenarioOutline = new GherkinScenarioOutline(lLine.getSourceFileLineNumber(),
                lLine.getLineContents());

        // Extract the Scenario Outline sub-elements from next lines
        int lIdx = extractScenarioOutlineOrTemplateFrom(pGherkinLines, pLineIdx, lScenarioOutline);

        // Create the result element
        GherkinElementExtractionResult<GherkinScenarioOutline> lExtractionResult = new GherkinElementExtractionResult<GherkinScenarioOutline>(
                true, lScenarioOutline, lIdx);

        return lExtractionResult;
    }

    /**
     * Start creating a Scenario Template element from the given index of the
     * Gherkin lines.
     * 
     * <p>
     * This function shall be called only if the line at the given index matches
     * a Scenario Template line.
     * </p>
     * 
     * @param pGherkinLines the Gherkin lines to read from.
     * @param pLineIdx the index in the Gherkin lines list to start reading the
     * Scenario Template element.
     * @return the extraction result.
     * @throws InvalidGherkinContentsException if it was not possible to parse
     * the file because its contents was not valid.
     * 
     */
    private GherkinElementExtractionResult<GherkinScenarioTemplate> extractScenarioTemplateFrom(
            final List<GherkinLine> pGherkinLines, final int pLineIdx) throws InvalidGherkinContentsException {

        GherkinLine lLine = pGherkinLines.get(pLineIdx);
        GherkinLineType lType;

        // Check the line type matches a Scenario Template
        lType = lLine.getType();
        if (lType != GherkinLineType.ScenarioTemplate) {
            throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.SCENARIO_TEMPLATE_KEYWORD
                    + " from line " + lLine.getNormalizedRepresentation());
        }

        // Create the Scenario Template element
        GherkinScenarioTemplate lScenarioTemplate = new GherkinScenarioTemplate(lLine.getSourceFileLineNumber(),
                lLine.getLineContents());

        // Extract the Scenario Template sub-elements from next lines
        int lIdx = extractScenarioOutlineOrTemplateFrom(pGherkinLines, pLineIdx, lScenarioTemplate);

        // Create the result element
        GherkinElementExtractionResult<GherkinScenarioTemplate> lExtractionResult = new GherkinElementExtractionResult<GherkinScenarioTemplate>(
                true, lScenarioTemplate, lIdx);

        return lExtractionResult;
    }

    /**
     * Parse the lines after a Scenario Outline or Scenario Template line, to
     * attach to it all the elements that depends on it.
     * 
     * @param pGherkinLines the Gherkin lines to read from.
     * @param pLineIdx the index in the Gherkin lines list where the Scenario
     * Outline/Scenario Template element was found.
     * @param pScenarioExampleOTemplate the Scenario Outline or Scenario
     * Template or Element to which sub-elements shall be added.
     * @return the index of the last used line.
     * @throws InvalidGherkinContentsException if it was not possible to parse
     * the file because its contents was not valid.
     */
    private int extractScenarioOutlineOrTemplateFrom(final List<GherkinLine> pGherkinLines, final int pLineIdx,
            final AbstractGherkinScenarioOutlineOrTemplateElement pScenarioExampleOTemplate)
            throws InvalidGherkinContentsException {

        GherkinLine lLine;
        int lIdx = pLineIdx;
        GherkinLineType lType;

        // Go on with next line as long as we find elements that can occur
        // "under" the Scenario element
        boolean lReadNextLine = true;

        while (lReadNextLine) {
            lIdx++;
            if (lIdx < pGherkinLines.size()) {
                lLine = pGherkinLines.get(lIdx);
                lType = lLine.getType();
            } else {
                break;
            }

            switch (lType) {
                case Description:
                    // Additional description to attach to the Scenario Outline
                    // or Scenario Template
                    // element
                    GherkinAdditionalDescription lAddDescription = new GherkinAdditionalDescription(
                            lLine.getSourceFileLineNumber(), lLine.getLineContents());
                    pScenarioExampleOTemplate.addAdditionalDescription(lAddDescription);
                    break;
                case Comment:
                    // Add the comment associated with the Scenario Outline or
                    // Scenario Template
                    // element
                    GherkinComment lComment = new GherkinComment(lLine.getSourceFileLineNumber(),
                            lLine.getLineContents());
                    pScenarioExampleOTemplate.addComment(lComment);
                    break;
                case Given:
                    GherkinElementExtractionResult<GherkinStepGiven> lExtractedGivenResult = extractStepFrom(
                            pGherkinLines, lIdx, lType);
                    if (lExtractedGivenResult.wasElementExtracted()) {
                        // Add the Given step
                        GherkinStepGiven lGiven = lExtractedGivenResult.getExtractedElement();
                        pScenarioExampleOTemplate.addStep(lGiven);

                        // Update the last line index
                        lIdx = lExtractedGivenResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.GIVEN_KEYWORD
                                + " from line " + lLine.getNormalizedRepresentation());
                    }
                    break;
                case And:
                    GherkinElementExtractionResult<GherkinStepAnd> lExtractedAndResult = extractStepFrom(pGherkinLines,
                            lIdx, lType);
                    if (lExtractedAndResult.wasElementExtracted()) {
                        // Add the And step
                        GherkinStepAnd lAnd = lExtractedAndResult.getExtractedElement();
                        pScenarioExampleOTemplate.addStep(lAnd);

                        // Update the last line index
                        lIdx = lExtractedAndResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.AND_KEYWORD
                                + " from line " + lLine.getNormalizedRepresentation());
                    }
                    break;
                case But:
                    GherkinElementExtractionResult<GherkinStepBut> lExtractedButResult = extractStepFrom(pGherkinLines,
                            lIdx, lType);
                    if (lExtractedButResult.wasElementExtracted()) {
                        // Add the But step
                        GherkinStepBut lBut = lExtractedButResult.getExtractedElement();
                        pScenarioExampleOTemplate.addStep(lBut);

                        // Update the last line index
                        lIdx = lExtractedButResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.BUT_KEYWORD
                                + " from line " + lLine.getNormalizedRepresentation());
                    }
                    break;
                case Star:
                    GherkinElementExtractionResult<GherkinStepStar> lExtractedStarResult = extractStepFrom(
                            pGherkinLines, lIdx, lType);
                    if (lExtractedStarResult.wasElementExtracted()) {
                        // Add the Star step
                        GherkinStepStar lStar = lExtractedStarResult.getExtractedElement();
                        pScenarioExampleOTemplate.addStep(lStar);

                        // Update the last line index
                        lIdx = lExtractedStarResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.STAR_KEYWORD
                                + " from line " + lLine.getNormalizedRepresentation());
                    }
                    break;

                case Then:
                    GherkinElementExtractionResult<GherkinStepThen> lExtractedThenResult = extractStepFrom(
                            pGherkinLines, lIdx, lType);
                    if (lExtractedThenResult.wasElementExtracted()) {
                        // Add the When step
                        GherkinStepThen lWhen = lExtractedThenResult.getExtractedElement();
                        pScenarioExampleOTemplate.addStep(lWhen);

                        // Update the last line index
                        lIdx = lExtractedThenResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.THEN_KEYWORD
                                + " from line " + lLine.getNormalizedRepresentation());
                    }
                    break;

                case When:
                    GherkinElementExtractionResult<GherkinStepWhen> lExtractedWhenResult = extractStepFrom(
                            pGherkinLines, lIdx, lType);
                    if (lExtractedWhenResult.wasElementExtracted()) {
                        // Add the When step
                        GherkinStepWhen lWhen = lExtractedWhenResult.getExtractedElement();
                        pScenarioExampleOTemplate.addStep(lWhen);

                        // Update the last line index
                        lIdx = lExtractedWhenResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.WHEN_KEYWORD
                                + " from line " + lLine.getNormalizedRepresentation());
                    }
                    break;

                case Examples:
                    GherkinElementExtractionResult<GherkinExamples> lExtractedExamplesResult = extractExamplesFrom(
                            pGherkinLines, lIdx);
                    if (lExtractedExamplesResult.wasElementExtracted()) {

                        // Check there is not already an Examples or a Scenarios
                        // attached
                        if (pScenarioExampleOTemplate.alreadyHasAnExamplesOrScenariosSet()) {
                            throw new InvalidGherkinContentsException("Cannot add the "
                                    + GherkinConstants.EXAMPLES_KEYWORD + " from line "
                                    + lLine.getNormalizedRepresentation() + " as an "
                                    + GherkinConstants.EXAMPLES_KEYWORD + " or a " + GherkinConstants.SCENARIOS_KEYWORD
                                    + " is already attached to the parent "
                                    + pScenarioExampleOTemplate.getContainerKeyword());

                        }

                        // Add the Examples
                        GherkinExamples lExamples = lExtractedExamplesResult.getExtractedElement();
                        pScenarioExampleOTemplate.setExamplesElement(lExamples);

                        // Update the last line index
                        lIdx = lExtractedExamplesResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a "
                                + GherkinConstants.EXAMPLES_KEYWORD + " from line "
                                + lLine.getNormalizedRepresentation());
                    }
                    break;

                case Scenarios:

                    GherkinElementExtractionResult<GherkinScenarios> lExtractedScenariosResult = extractScenariosFrom(
                            pGherkinLines, lIdx);
                    if (lExtractedScenariosResult.wasElementExtracted()) {

                        // Check there is not already an Examples or a Scenarios
                        // attached
                        if (pScenarioExampleOTemplate.alreadyHasAnExamplesOrScenariosSet()) {
                            throw new InvalidGherkinContentsException("Cannot add the "
                                    + GherkinConstants.SCENARIOS_KEYWORD + " from line "
                                    + lLine.getNormalizedRepresentation() + " as an "
                                    + GherkinConstants.EXAMPLES_KEYWORD + " or a " + GherkinConstants.SCENARIOS_KEYWORD
                                    + " is already attached to the parent "
                                    + pScenarioExampleOTemplate.getContainerKeyword());

                        }

                        // Add the Scenarios
                        GherkinScenarios lScenarios = lExtractedScenariosResult.getExtractedElement();
                        pScenarioExampleOTemplate.setScenariosElement(lScenarios);

                        // Update the last line index
                        lIdx = lExtractedScenariosResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a "
                                + GherkinConstants.SCENARIOS_KEYWORD + " from line "
                                + lLine.getNormalizedRepresentation());
                    }
                    break;
                default:
                    lReadNextLine = false;
                    break;
            }

        }

        return lIdx - 1;
    }

    /**
     * Start creating an Examples element from the given index of the Gherkin
     * lines.
     * 
     * <p>
     * This function shall be called only if the line at the given index matches
     * a Examples line.
     * </p>
     * 
     * @param pGherkinLines the Gherkin lines to read from.
     * @param pLineIdx the index in the Gherkin lines list to start reading the
     * Examples element.
     * @return the extraction result.
     * @throws InvalidGherkinContentsException if it was not possible to parse
     * the file because its contents was not valid.
     * 
     */
    private GherkinElementExtractionResult<GherkinExamples> extractExamplesFrom(final List<GherkinLine> pGherkinLines,
            final int pLineIdx) throws InvalidGherkinContentsException {

        GherkinLine lLine = pGherkinLines.get(pLineIdx);

        GherkinLineType lType;

        // Check the line type matches a Examples
        lType = lLine.getType();
        if (lType != GherkinLineType.Examples) {
            throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.EXAMPLES_KEYWORD
                    + " from line " + lLine.getNormalizedRepresentation());
        }

        // Create the Examples element
        GherkinExamples lExamples = new GherkinExamples(lLine.getSourceFileLineNumber(), lLine.getLineContents());

        // Attach any sub-element
        int lIdx = extractExamplesOrScenariosFrom(pGherkinLines, pLineIdx, lExamples);

        // Create the result value
        GherkinElementExtractionResult<GherkinExamples> lExtractionResult = new GherkinElementExtractionResult<GherkinExamples>(
                true, lExamples, lIdx);

        return lExtractionResult;
    }

    /**
     * Start creating an Scenarios element from the given index of the Gherkin
     * lines.
     * 
     * <p>
     * This function shall be called only if the line at the given index matches
     * a Scenarios line.
     * </p>
     * 
     * @param pGherkinLines the Gherkin lines to read from.
     * @param pLineIdx the index in the Gherkin lines list to start reading the
     * Scenarios element.
     * @return the extraction result.
     * @throws InvalidGherkinContentsException if it was not possible to parse
     * the file because its contents was not valid.
     * 
     */
    private GherkinElementExtractionResult<GherkinScenarios> extractScenariosFrom(
            final List<GherkinLine> pGherkinLines, final int pLineIdx) throws InvalidGherkinContentsException {

        GherkinLine lLine = pGherkinLines.get(pLineIdx);

        GherkinLineType lType;

        // Check the line type matches a Scenarios
        lType = lLine.getType();
        if (lType != GherkinLineType.Scenarios) {
            throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.SCENARIOS_KEYWORD
                    + " from line " + lLine.getNormalizedRepresentation());
        }

        // Create the Scenarios element
        GherkinScenarios lScenarios = new GherkinScenarios(lLine.getSourceFileLineNumber(), lLine.getLineContents());

        // Attach any sub-element
        int lIdx = extractExamplesOrScenariosFrom(pGherkinLines, pLineIdx, lScenarios);

        // Create the result value
        GherkinElementExtractionResult<GherkinScenarios> lExtractionResult = new GherkinElementExtractionResult<GherkinScenarios>(
                true, lScenarios, lIdx);

        return lExtractionResult;
    }

    /**
     * Parse the lines after an Examples or a Scenarios line, to attach to it
     * all the elements that depends on it.
     * 
     * @param pGherkinLines the Gherkin lines to read from.
     * @param pLineIdx the index in the Gherkin lines list where the Examples or
     * Scenarios element was found.
     * @param pExamplesOrScenarios the Scenario or Element to which sub-elements
     * shall be added.
     * @return the index of the last used line.
     * @throws InvalidGherkinContentsException if it was not possible to parse
     * the file because its contents was not valid.
     */
    private int extractExamplesOrScenariosFrom(final List<GherkinLine> pGherkinLines, final int pLineIdx,
            final AbstractGherkinExamplesOrScenariosElement pExamplesOrScenarios)
            throws InvalidGherkinContentsException {

        GherkinLine lLine;
        int lIdx = pLineIdx;
        GherkinLineType lType;

        // Go on with next line as long as we find elements that can occur
        // "under" the Examples or Scenarios element
        boolean lReadNextLine = true;

        while (lReadNextLine) {
            lIdx++;
            if (lIdx < pGherkinLines.size()) {
                lLine = pGherkinLines.get(lIdx);
                lType = lLine.getType();
            } else {
                break;
            }

            GherkinElementExtractionResult<GherkinDataTable> lDataTableResult;
            switch (lType) {
                case Comment:
                    // Add the comment associated with the Examples element
                    GherkinComment lComment = new GherkinComment(lLine.getSourceFileLineNumber(),
                            lLine.getLineContents());
                    pExamplesOrScenarios.addComment(lComment);
                    break;
                case DataTable:
                    lDataTableResult = extractDataTableFrom(pGherkinLines, lIdx);
                    if (lDataTableResult.wasElementExtracted()) {
                        // Check there is not already a DataTable attached
                        if (pExamplesOrScenarios.hasAssociatedDataTable()) {
                            throw new InvalidGherkinContentsException("Found a  "
                                    + GherkinConstants.DATA_TABLE_DESCRIPTION + " in line "
                                    + lLine.getNormalizedRepresentation() + " while one is already attached to the "
                                    + pExamplesOrScenarios.getContainerKeyword() + " element (line "
                                    + pExamplesOrScenarios.getSourceFileLineNumber() + ")");
                        } else {
                            // Attach the DataTable to the element
                            pExamplesOrScenarios.setDataTable(lDataTableResult.getExtractedElement());

                            // Update the last read line index
                            lIdx = lDataTableResult.getLastLineIndex();
                        }
                    }
                    break;

                default:
                    lReadNextLine = false;
                    break;
            }
        }

        return lIdx - 1;
    }

    /**
     * Start creating a Rule element from the given index of the Gherkin lines.
     * 
     * <p>
     * This function shall be called only if the line at the given index matches
     * a Rule line.
     * </p>
     * 
     * @param pGherkinLines the Gherkin lines to read from.
     * @param pLineIdx the index in the Gherkin lines list to start reading the
     * Rule element.
     * @return the extraction result.
     * @throws InvalidGherkinContentsException if it was not possible to parse
     * the file because its contents was not valid.
     * 
     */
    private GherkinElementExtractionResult<GherkinRule> extractRuleFrom(final List<GherkinLine> pGherkinLines,
            final int pLineIdx) throws InvalidGherkinContentsException {

        GherkinLine lLine;
        int lIdx = pLineIdx;

        lLine = pGherkinLines.get(lIdx);

        GherkinLineType lType;

        // Check the line type matches a Rule
        lType = lLine.getType();
        if (lType != GherkinLineType.Rule) {
            throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.RULE_KEYWORD
                    + " from line " + lLine.getNormalizedRepresentation());
        }

        GherkinRule lRule = new GherkinRule(lLine.getSourceFileLineNumber(), lLine.getLineContents());

        // Go on with next line as long as we find elements that can occur
        // "under" the Rule element
        boolean lReadNextLine = true;

        while (lReadNextLine) {
            lIdx++;
            if (lIdx == pGherkinLines.size()) {
                break;
            }
            lLine = pGherkinLines.get(lIdx);
            lType = lLine.getType();

            switch (lType) {
                case Comment:
                    // Add the comment associated with the Background element
                    GherkinComment lComment = new GherkinComment(lLine.getSourceFileLineNumber(),
                            lLine.getLineContents());
                    lRule.addComment(lComment);
                    break;
                case Example:
                    GherkinElementExtractionResult<GherkinExample> lExtractedExampleResult = extractExampleFrom(
                            pGherkinLines, lIdx);
                    if (lExtractedExampleResult.wasElementExtracted()) {
                        // Add the Example step
                        GherkinExample lExample = lExtractedExampleResult.getExtractedElement();
                        lRule.addExample(lExample);

                        // Update the last line index
                        lIdx = lExtractedExampleResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.EXAMPLE_KEYWORD
                                + " from line " + lLine.getNormalizedRepresentation());
                    }
                    break;
                case Scenario:
                    GherkinElementExtractionResult<GherkinScenario> lExtractedScenarioResult = extractScenarioFrom(
                            pGherkinLines, lIdx);
                    if (lExtractedScenarioResult.wasElementExtracted()) {
                        // Add the Scenario step
                        GherkinScenario lScenario = lExtractedScenarioResult.getExtractedElement();
                        lRule.addScenario(lScenario);

                        // Update the last line index
                        lIdx = lExtractedScenarioResult.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Cannot create a "
                                + GherkinConstants.SCENARIO_KEYWORD + " from line "
                                + lLine.getNormalizedRepresentation());
                    }
                    break;
                default:
                    lReadNextLine = false;
                    break;
            }
        }

        GherkinElementExtractionResult<GherkinRule> lExtractionResult = new GherkinElementExtractionResult<GherkinRule>(
                true, lRule, lIdx - 1);

        return lExtractionResult;
    }

    /**
     * Start creating a Background element from the given index of the Gherkin
     * lines.
     * 
     * <p>
     * This function shall be called only if the line at the given index matches
     * a Background line.
     * </p>
     * 
     * @param pGherkinLines the Gherkin lines to read from.
     * @param pLineIdx the index in the Gherkin lines list to start reading the
     * Background element.
     * @return the extraction result.
     * @throws InvalidGherkinContentsException if it was not possible to parse
     * the file because its contents was not valid.
     * 
     */
    private GherkinElementExtractionResult<GherkinBackground> extractBackgroundFrom(
            final List<GherkinLine> pGherkinLines, final int pLineIdx) throws InvalidGherkinContentsException {

        GherkinLine lLine;
        int lIdx = pLineIdx;

        lLine = pGherkinLines.get(lIdx);

        GherkinLineType lType;

        // Check the line type matches a Background
        lType = lLine.getType();
        if (lType != GherkinLineType.Background) {
            throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.BACKGROUND_KEYWORD
                    + " from line " + lLine.getNormalizedRepresentation());
        }

        // Create the background element
        GherkinBackground lBackground = new GherkinBackground(lLine.getSourceFileLineNumber(), lLine.getLineContents());

        // Go on with next line as long as we find elements that can occur
        // "under" the
        // Background element
        boolean lReadNextLine = true;

        while (lReadNextLine) {
            lIdx++;
            if (lIdx == pGherkinLines.size()) {
                break;
            }
            lLine = pGherkinLines.get(lIdx);
            lType = lLine.getType();

            switch (lType) {
                case Description:
                    // Additional description to attach to the Background
                    // element
                    GherkinAdditionalDescription lAddDescription = new GherkinAdditionalDescription(
                            lLine.getSourceFileLineNumber(), lLine.getLineContents());
                    lBackground.addAdditionalDescription(lAddDescription);
                    break;
                case Comment:
                    // Add the comment associated with the Background element
                    GherkinComment lComment = new GherkinComment(lLine.getSourceFileLineNumber(),
                            lLine.getLineContents());
                    lBackground.addComment(lComment);
                    break;
                case Given:
                    // Add the Given element
                    GherkinElementExtractionResult<GherkinStepGiven> lExtractedGiven = extractStepFrom(pGherkinLines,
                            lIdx, lType);
                    if (lExtractedGiven.wasElementExtracted()) {
                        // Add the Given element to the Background
                        lBackground.addGivenStep(lExtractedGiven.getExtractedElement());

                        // Update the index of the last used line
                        lIdx = lExtractedGiven.getLastLineIndex();
                    } else {
                        throw new InvalidGherkinContentsException("Could not extract the "
                                + GherkinConstants.GIVEN_KEYWORD + " from line " + lLine.getNormalizedRepresentation());
                    }
                    break;

                default:
                    lReadNextLine = false;
                    break;
            }

        }

        GherkinElementExtractionResult<GherkinBackground> lExtractionResult = new GherkinElementExtractionResult<GherkinBackground>(
                true, lBackground, lIdx - 1);

        return lExtractionResult;
    }

    /**
     * Extract the given type of step from the given index from the Gherkin
     * lines.
     * 
     * @param pGherkinLines the Gherkin lines to read from.
     * @param pLineIdx the index in the Gherkin lines list to start reading the
     * Given element.
     * @param pStepLineType the type of the step line.
     * @param <T> the actual type of the step to create.
     * @return the extraction result.
     * @throws InvalidGherkinContentsException if it was not possible to parse
     * the file because its contents was not valid.
     * 
     */
    private <T extends AbstractGherkinStep> GherkinElementExtractionResult<T> extractStepFrom(
            final List<GherkinLine> pGherkinLines, final int pLineIdx, final GherkinLineType pStepLineType)
            throws InvalidGherkinContentsException {
        int lIdx = pLineIdx;

        // Get the line at the current index
        GherkinLine lLine = pGherkinLines.get(lIdx);

        // Check the line type matches a When
        GherkinLineType lType = lLine.getType();
        if (lType != pStepLineType) {
            throw new InvalidGherkinContentsException("Cannot create a " + pStepLineType.getLineTypeName()
                    + " from line \"" + lLine.getNormalizedRepresentation() + "\"");
        }

        // Create the step element
        AbstractGherkinStep lStep;
        switch (pStepLineType) {
            case And:
                lStep = new GherkinStepAnd(lLine.getSourceFileLineNumber(), lLine.getLineContents());
                break;
            case But:
                lStep = new GherkinStepBut(lLine.getSourceFileLineNumber(), lLine.getLineContents());
                break;
            case Given:
                lStep = new GherkinStepGiven(lLine.getSourceFileLineNumber(), lLine.getLineContents());
                break;
            case Star:
                lStep = new GherkinStepStar(lLine.getSourceFileLineNumber(), lLine.getLineContents());
                break;
            case Then:
                lStep = new GherkinStepThen(lLine.getSourceFileLineNumber(), lLine.getLineContents());
                break;
            case When:
                lStep = new GherkinStepWhen(lLine.getSourceFileLineNumber(), lLine.getLineContents());
                break;
            default:
                throw new InvalidGherkinContentsException("Cannot create a step with type "
                        + pStepLineType.getLineTypeName());
        }

        // Go on with next line as long as we find elements that can occur
        // "under" the step element
        boolean lReadNextLine = true;
        GherkinElementExtractionResult<GherkinDataTable> lDataTableResult;
        GherkinElementExtractionResult<GherkinDocString> lDocStringResult;

        while (lReadNextLine) {
            lIdx++;
            if (lIdx == pGherkinLines.size()) {
                break;
            }
            lLine = pGherkinLines.get(lIdx);
            lType = lLine.getType();

            if (lType == GherkinLineType.Comment) {
                // Add the comment associated with the Background element
                GherkinComment lComment = new GherkinComment(lLine.getSourceFileLineNumber(), lLine.getLineContents());
                lStep.addComment(lComment);
            } else if (lType == GherkinLineType.DataTable) {
                lDataTableResult = extractDataTableFrom(pGherkinLines, lIdx);
                if (lDataTableResult.wasElementExtracted()) {
                    // Check there is not already a DataTable or a DocString
                    // attached
                    if (lStep.hasAssociatedDataTable() || lStep.hasAssociatedDocString()) {
                        throw new InvalidGherkinContentsException("Found a  " + GherkinConstants.DATA_TABLE_DESCRIPTION
                                + " in line " + lLine.getNormalizedRepresentation() + " while a "
                                + GherkinConstants.DATA_TABLE_DESCRIPTION + " or a "
                                + GherkinConstants.DOC_STRING_DESCRIPTION + " is already attached to the "
                                + pStepLineType.getLineTypeName() + " element (line " + lStep.getSourceFileLineNumber()
                                + ")");
                    } else {
                        // Attach the DataTable to the element
                        lStep.setAssociatedDataTable(lDataTableResult.getExtractedElement());

                        // Update the last read line index
                        lIdx = lDataTableResult.getLastLineIndex();
                    }
                }
            } else if (lType == GherkinLineType.DocString) {
                lDocStringResult = extractDocStringFrom(pGherkinLines, lIdx);
                if (lDocStringResult.wasElementExtracted()) {
                    // Check there is not already a DataTable or a DocString
                    // attached
                    if (lStep.hasAssociatedDataTable() || lStep.hasAssociatedDocString()) {
                        throw new InvalidGherkinContentsException("Found a  " + GherkinConstants.DOC_STRING_DESCRIPTION
                                + " in line " + lLine.getNormalizedRepresentation() + " while a "
                                + GherkinConstants.DATA_TABLE_DESCRIPTION + " or a "
                                + GherkinConstants.DOC_STRING_DESCRIPTION + " is already attached to the "
                                + pStepLineType.getLineTypeName() + " element (line " + lStep.getSourceFileLineNumber()
                                + "");
                    } else {
                        // Attach the DocString to the element
                        lStep.setAssociatedDocString(lDocStringResult.getExtractedElement());

                        // Update the last read line index
                        lIdx = lDocStringResult.getLastLineIndex();
                    }
                }
            } else {
                lReadNextLine = false;
            }
        }

        // The check is OK as T derives from AbstractGherkinStep
        @SuppressWarnings("unchecked")
        GherkinElementExtractionResult<T> lExtractionResult = new GherkinElementExtractionResult<T>(true, (T) lStep,
                lIdx - 1);

        return lExtractionResult;
    }

    /**
     * Start creating a Data table element from the given index of the Gherkin
     * lines.
     * 
     * <p>
     * This function shall be called only if the line at the given index matches
     * a DataTable line.
     * </p>
     * 
     * <p>
     * It consumes all the next lines that are part of the DataTable element.
     * </p>
     * 
     * @param pGherkinLines the Gherkin lines to read from.
     * @param pLineIdx the index in the Gherkin lines list to start reading the
     * DataTable element.
     * @return the extraction result.
     * @throws InvalidGherkinContentsException if it was not possible to parse
     * the file because its contents was not valid.
     * 
     */
    private GherkinElementExtractionResult<GherkinDataTable> extractDataTableFrom(
            final List<GherkinLine> pGherkinLines, final int pLineIdx) throws InvalidGherkinContentsException {

        GherkinLine lLine;
        int lIdx = pLineIdx;

        lLine = pGherkinLines.get(lIdx);

        GherkinLineType lType;

        // Check the line type matches a DataTable
        lType = lLine.getType();
        if (lType != GherkinLineType.DataTable) {
            throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.DATA_TABLE_DESCRIPTION
                    + " from line " + lLine.getNormalizedRepresentation());
        }

        // Create the DataTable element
        GherkinDataTable lDataTable = new GherkinDataTable(lLine.getSourceFileLineNumber());

        // Go on with next line as long as we find elements that can occur
        // "under" the DataTable element
        boolean lReadNextLine = true;

        while (lReadNextLine) {
            lIdx++;
            if (lIdx == pGherkinLines.size()) {
                break;
            }
            lLine = pGherkinLines.get(lIdx);
            lType = lLine.getType();

            switch (lType) {
                case Comment:
                    // Add the comment associated with the Given element
                    GherkinComment lComment = new GherkinComment(lLine.getSourceFileLineNumber(),
                            lLine.getLineContents());
                    lDataTable.addComment(lComment);
                    break;
                case DataTable:
                    // Get the data table line contents
                    String lContents = lLine.getLineContents();
                    // Add it to the data table
                    lDataTable.addLineText(lContents);

                    // Switch to next line
                    break;

                default:
                    lReadNextLine = false;
                    break;
            }

        }

        // As we went out of the while after we detected a line that did not
        // belong to the data table, we must remove 1 to the current index to
        // refer to the index of the last read line
        GherkinElementExtractionResult<GherkinDataTable> lExtractionResult = new GherkinElementExtractionResult<GherkinDataTable>(
                true, lDataTable, lIdx - 1);

        return lExtractionResult;
    }

    /**
     * Start creating a Doc String element from the given index of the Gherkin
     * lines.
     * 
     * <p>
     * This function shall be called only if the line at the given index matches
     * a DocString line.
     * </p>
     * 
     * <p>
     * It consumes all the next lines that are part of the DocString element.
     * </p>
     * 
     * @param pGherkinLines the Gherkin lines to read from.
     * @param pLineIdx the index in the Gherkin lines list to start reading the
     * DocString element.
     * @return the extraction result.
     * @throws InvalidGherkinContentsException if it was not possible to parse
     * the file because its contents was not valid.
     * 
     */
    private GherkinElementExtractionResult<GherkinDocString> extractDocStringFrom(
            final List<GherkinLine> pGherkinLines, final int pLineIdx) throws InvalidGherkinContentsException {

        GherkinLine lLine;
        int lIdx = pLineIdx;

        lLine = pGherkinLines.get(lIdx);

        GherkinLineType lType;

        // Check the line type matches a DocString
        lType = lLine.getType();
        if (lType != GherkinLineType.DocString) {
            throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.DOC_STRING_DESCRIPTION
                    + " from line " + lLine.getNormalizedRepresentation());
        }

        // Create the DocString element
        String lActualKeyword;
        if (lLine.getLineContents().contains(GherkinConstants.DOC_STRING_KEYWORD)) {
            lActualKeyword = GherkinConstants.DOC_STRING_KEYWORD;
        } else {
            lActualKeyword = GherkinConstants.DOC_STRING_ALT_KEYWORD;
        }
        GherkinDocString lDocString = new GherkinDocString(lLine.getSourceFileLineNumber(), lActualKeyword);

        // Go on with next line as long as we find elements that can occur
        // "under" the DocString element
        boolean lReadNextLine = true;

        // We must make sure the ending DocString line is found
        boolean lEndingDocStringLineFound = false;

        while (lReadNextLine) {
            lIdx++;
            // Stop if the index is over the number of lines
            if (lIdx == pGherkinLines.size()) {
                break;
            }
            lLine = pGherkinLines.get(lIdx);
            lType = lLine.getType();

            switch (lType) {
                case Comment:
                    // Add the comment associated with the Given element
                    GherkinComment lComment = new GherkinComment(lLine.getSourceFileLineNumber(),
                            lLine.getLineContents());
                    lDocString.addComment(lComment);
                    break;
                case Description:
                    // Append the line to the doc string
                    lDocString.addLineText(lLine.getLineContents());
                    break;
                case DocString:
                    // End of the doc string bloc
                    lReadNextLine = false;
                    // Indicate that we found the ending DocString line
                    lEndingDocStringLineFound = true;
                    break;

                default:
                    lReadNextLine = false;
                    break;
            }
        }

        // We must ensure the DocString ending line was found
        if (!lEndingDocStringLineFound) {
            throw new InvalidGherkinContentsException("Cannot create a " + GherkinConstants.DOC_STRING_DESCRIPTION
                    + " from line " + lLine.getNormalizedRepresentation() + " starting at "
                    + lLine.getSourceFileLineNumber() + " as the ending " + GherkinConstants.DOC_STRING_DESCRIPTION
                    + " could not be found");
        }

        GherkinElementExtractionResult<GherkinDocString> lExtractionResult = new GherkinElementExtractionResult<GherkinDocString>(
                true, lDocString, lIdx);

        return lExtractionResult;
    }
}
