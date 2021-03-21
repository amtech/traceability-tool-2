/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that converts Gherkin raw lines in {@link GherkinLine}s.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinInterpreter {

    /**
     * Pattern for one space (or tabulation).
     */
    private static final String SPACE_PATTERN = "\\s";

    /**
     * Pattern for potential spaces (or tabulations).
     */
    private static final String POTENTIAL_SPACES_PATTERN = SPACE_PATTERN + "*";

    /**
     * Pattern matching the (potential spaces) plus the colon (:) after container
     * keywords.
     */
    private static final String CONTAINER_COLON_PATTERN = POTENTIAL_SPACES_PATTERN
            + GherkinConstants.CONTAINER_COLON_KEYWORD;

    /**
     * Pattern corresponding to And lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed And text.
     * </p>
     */
    private static final String AND_LINE_PATTERN = GherkinConstants.AND_KEYWORD + SPACE_PATTERN
            + POTENTIAL_SPACES_PATTERN + "(.*)" + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to Background : lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed Background text.
     * </p>
     */
    private static final String BACKGROUND_LINE_PATTERN = GherkinConstants.BACKGROUND_KEYWORD + CONTAINER_COLON_PATTERN
            + POTENTIAL_SPACES_PATTERN + "(.*)" + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to But lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed But text.
     * </p>
     */
    private static final String BUT_LINE_PATTERN = GherkinConstants.BUT_KEYWORD + SPACE_PATTERN
            + POTENTIAL_SPACES_PATTERN + "(.*)" + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to comment lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed comment text.
     * </p>
     * <p>
     * Include the leading spaces in commments.
     * </p>
     */
    private static final String COMMENT_LINE_PATTERN = GherkinConstants.COMMENT_KEYWORD + "(.*)"
            + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to data table, for a trimmed raw line.
     * <p>
     * Group 1 contains the line contents.
     * </p>
     */
    private static final String DATA_TABLE_LINE_PATTERN = "(\\" + GherkinConstants.DATA_TABLE_KEYWORD + ".*)";

    /**
     * Pattern corresponding to documentation string, for a trimmed raw line.
     * <p>
     * No data on the line is expected but group 1 contains the text if present.
     * </p>
     */
    private static final String DOC_STRING_LINE_PATTERN = "(\"\"\"|```)" + POTENTIAL_SPACES_PATTERN + "(.*)"
            + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to Feature : lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed Feature text.
     * </p>
     */
    private static final String FEATURE_LINE_PATTERN = GherkinConstants.FEATURE_KEYWORD + CONTAINER_COLON_PATTERN
            + POTENTIAL_SPACES_PATTERN + "(.*)" + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to Example : lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed Example text.
     * </p>
     */
    private static final String EXAMPLE_LINE_PATTERN = GherkinConstants.EXAMPLE_KEYWORD + CONTAINER_COLON_PATTERN
            + POTENTIAL_SPACES_PATTERN + "(.*)" + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to Examples : lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed Examples text.
     * </p>
     */
    private static final String EXAMPLES_LINE_PATTERN = GherkinConstants.EXAMPLES_KEYWORD + CONTAINER_COLON_PATTERN
            + POTENTIAL_SPACES_PATTERN + "(.*)" + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to Given lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed Given text.
     * </p>
     */
    private static final String GIVEN_LINE_PATTERN = GherkinConstants.GIVEN_KEYWORD + SPACE_PATTERN
            + POTENTIAL_SPACES_PATTERN + "(.*)" + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to Rule : lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed Rule text.
     * </p>
     */
    private static final String RULE_LINE_PATTERN = GherkinConstants.RULE_KEYWORD + CONTAINER_COLON_PATTERN
            + POTENTIAL_SPACES_PATTERN + "(.*)" + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to Scenario : lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed Scenario text.
     * </p>
     */
    private static final String SCENARIO_LINE_PATTERN = GherkinConstants.SCENARIO_KEYWORD + CONTAINER_COLON_PATTERN
            + POTENTIAL_SPACES_PATTERN + "(.*)" + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to Scenarios : lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed Scenarios text.
     * </p>
     */
    private static final String SCENARIOS_LINE_PATTERN = GherkinConstants.SCENARIOS_KEYWORD + CONTAINER_COLON_PATTERN
            + POTENTIAL_SPACES_PATTERN + "(.*)" + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to Scenario Outline : lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed Scenario Outline text.
     * </p>
     */
    private static final String SCENARIO_OUTLINE_LINE_PATTERN = GherkinConstants.SCENARIO_OUTLINE_KEYWORD
            + CONTAINER_COLON_PATTERN + POTENTIAL_SPACES_PATTERN + "(.*)" + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to Scenario Template : lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed Scenario Template text.
     * </p>
     */
    private static final String SCENARIO_TEMPLATE_LINE_PATTERN = GherkinConstants.SCENARIO_TEMPLATE_KEYWORD
            + CONTAINER_COLON_PATTERN + POTENTIAL_SPACES_PATTERN + "(.*)" + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to * lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed star text.
     * </p>
     */
    private static final String STAR_LINE_PATTERN = "\\" + GherkinConstants.STAR_KEYWORD + SPACE_PATTERN
            + POTENTIAL_SPACES_PATTERN + "(.*)" + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to Then lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed Then text.
     * </p>
     */
    private static final String THEN_LINE_PATTERN = GherkinConstants.THEN_KEYWORD + SPACE_PATTERN
            + POTENTIAL_SPACES_PATTERN + "(.*)" + POTENTIAL_SPACES_PATTERN;

    /**
     * Pattern corresponding to When lines, for a trimmed raw line.
     * <p>
     * Group 1 contains the trimmed When text.
     * </p>
     */
    private static final String WHEN_LINE_PATTERN = GherkinConstants.WHEN_KEYWORD + SPACE_PATTERN
            + POTENTIAL_SPACES_PATTERN + "(.*)" + POTENTIAL_SPACES_PATTERN;

    /**
     * The matcher for And lines.
     */
    private final Pattern andLinePattern;

    /**
     * The matcher for But lines.
     */
    private final Pattern butLinePattern;

    /**
     * The matcher for comment lines.
     */
    private final Pattern commentLinePattern;

    /**
     * The matcher for Background : lines.
     */
    private final Pattern backgroundLinePattern;

    /**
     * The matcher for Data table lines.
     */
    private final Pattern dataTableLinePattern;

    /**
     * The matcher for Doc String lines.
     */
    private final Pattern docStringLinePattern;

    /**
     * The matcher for Feature : lines.
     */
    private final Pattern featureLinePattern;

    /**
     * The matcher for Example : lines.
     */
    private final Pattern exampleLinePattern;

    /**
     * The matcher for Examples : lines.
     */
    private final Pattern examplesLinePattern;

    /**
     * The matcher for Given lines.
     */
    private final Pattern givenLinePattern;

    /**
     * The matcher for Rule : lines.
     */
    private final Pattern ruleLinePattern;

    /**
     * The matcher for Scenario : lines.
     */
    private final Pattern scenarioLinePattern;

    /**
     * The matcher for Scenarios : lines.
     */
    private final Pattern scenariosLinePattern;

    /**
     * The matcher for Scenario Outline : lines.
     */
    private final Pattern scenarioOutlineLinePattern;

    /**
     * The matcher for Scenario Template : lines.
     */
    private final Pattern scenarioTemplateLinePattern;

    /**
     * The matcher for * lines.
     */
    private final Pattern starLinePattern;

    /**
     * The matcher for Then lines.
     */
    private final Pattern thenLinePattern;

    /**
     * The matcher for When lines.
     */
    private final Pattern whenLinePattern;

    /**
     * Constructor.
     */
    public GherkinInterpreter() {
        andLinePattern = Pattern.compile(AND_LINE_PATTERN);
        backgroundLinePattern = Pattern.compile(BACKGROUND_LINE_PATTERN);
        butLinePattern = Pattern.compile(BUT_LINE_PATTERN);
        commentLinePattern = Pattern.compile(COMMENT_LINE_PATTERN);
        dataTableLinePattern = Pattern.compile(DATA_TABLE_LINE_PATTERN);
        docStringLinePattern = Pattern.compile(DOC_STRING_LINE_PATTERN);
        featureLinePattern = Pattern.compile(FEATURE_LINE_PATTERN);
        exampleLinePattern = Pattern.compile(EXAMPLE_LINE_PATTERN);
        examplesLinePattern = Pattern.compile(EXAMPLES_LINE_PATTERN);
        givenLinePattern = Pattern.compile(GIVEN_LINE_PATTERN);
        ruleLinePattern = Pattern.compile(RULE_LINE_PATTERN);
        scenarioLinePattern = Pattern.compile(SCENARIO_LINE_PATTERN);
        scenariosLinePattern = Pattern.compile(SCENARIOS_LINE_PATTERN);
        scenarioOutlineLinePattern = Pattern.compile(SCENARIO_OUTLINE_LINE_PATTERN);
        scenarioTemplateLinePattern = Pattern.compile(SCENARIO_TEMPLATE_LINE_PATTERN);
        starLinePattern = Pattern.compile(STAR_LINE_PATTERN);
        thenLinePattern = Pattern.compile(THEN_LINE_PATTERN);
        whenLinePattern = Pattern.compile(WHEN_LINE_PATTERN);
    }

    /**
     * Converts a set of raw lines into Gherkin lines.
     * 
     * @param pRawLines the raw lines.
     * @return the equivalent Gherkin lines (without empty lines).
     */
    public List<GherkinLine> convertRawLines(final List<String> pRawLines) {
        List<GherkinLine> lGherkinLines = new ArrayList<GherkinLine>();

        GherkinLine lGherkinLine;
        String lTrimmedRawLine;

        int lSourceLineNumber = 0;

        for (String lRawLine : pRawLines) {
            lGherkinLine = null;
            lSourceLineNumber++;
            lTrimmedRawLine = lRawLine.trim();

            // Ensure the line is not empty
            if (lTrimmedRawLine.length() > 0) {

                // Try and see if this line is a step
                lGherkinLine = getStepGherkinLineFrom(lTrimmedRawLine, lSourceLineNumber);

                if (lGherkinLine == null) {
                    // If not, try containers
                    lGherkinLine = getContainerGherkinLineFrom(lTrimmedRawLine, lSourceLineNumber);

                    // If not, try other kinds
                    if (lGherkinLine == null) {
                        lGherkinLine = getOtherGherkinLineFrom(lRawLine, lSourceLineNumber);
                    }
                }
            }

            if (lGherkinLine != null) {
                lGherkinLines.add(lGherkinLine);
            }
        }

        return lGherkinLines;
    }

    /**
     * Try and see if the raw line corresponds to a step, and if it is, return the
     * corresponding Gherkin line.
     * 
     * @param trimmedRawLine    the trimmed input line.
     * @param pSourceLineNumber the number of the line in the source file.
     * @return the Gherkin line corresponding to the input line if it a step,
     *         <tt>null</tt> otherwise.
     */
    private GherkinLine getStepGherkinLineFrom(final String trimmedRawLine, final int pSourceLineNumber) {
        GherkinLine lGherkinLine = null;

        Matcher lAndLineMatcher;
        String lLineContents;

        lAndLineMatcher = andLinePattern.matcher(trimmedRawLine);
        if (lAndLineMatcher.matches()) {
            lLineContents = lAndLineMatcher.group(1);
            lGherkinLine = new GherkinLine(GherkinLineType.And, lLineContents, pSourceLineNumber);
        } else {
            Matcher lButLineMatcher = butLinePattern.matcher(trimmedRawLine);
            if (lButLineMatcher.matches()) {
                lLineContents = lButLineMatcher.group(1);
                lGherkinLine = new GherkinLine(GherkinLineType.But, lLineContents, pSourceLineNumber);
            } else {
                Matcher lGivenLineMatcher = givenLinePattern.matcher(trimmedRawLine);
                if (lGivenLineMatcher.matches()) {
                    lLineContents = lGivenLineMatcher.group(1);
                    lGherkinLine = new GherkinLine(GherkinLineType.Given, lLineContents, pSourceLineNumber);
                } else {
                    Matcher lStarLineMatcher = starLinePattern.matcher(trimmedRawLine);
                    if (lStarLineMatcher.matches()) {
                        lLineContents = lStarLineMatcher.group(1);
                        lGherkinLine = new GherkinLine(GherkinLineType.Star, lLineContents, pSourceLineNumber);
                    } else {
                        Matcher lThenLineMatcher = thenLinePattern.matcher(trimmedRawLine);
                        if (lThenLineMatcher.matches()) {
                            lLineContents = lThenLineMatcher.group(1);
                            lGherkinLine = new GherkinLine(GherkinLineType.Then, lLineContents, pSourceLineNumber);
                        } else {
                            Matcher lWhenLineMatcher = whenLinePattern.matcher(trimmedRawLine);
                            if (lWhenLineMatcher.matches()) {
                                lLineContents = lWhenLineMatcher.group(1);
                                lGherkinLine = new GherkinLine(GherkinLineType.When, lLineContents, pSourceLineNumber);
                            }
                        }
                    }
                }
            }
        }

        return lGherkinLine;
    }

    /**
     * Try and see if the raw line corresponds to a container, and if it is, return
     * the corresponding Gherkin line.
     * 
     * @param pTrimmedRawLine   the trimmed input line.
     * @param pSourceLineNumber the number of the line in the source file.
     * @return the Gherkin line corresponding to the input line if it a container,
     *         <tt>null</tt> otherwise.
     */
    private GherkinLine getContainerGherkinLineFrom(final String pTrimmedRawLine, final int pSourceLineNumber) {
        GherkinLine lGherkinLine = null;

        String lLineContents;
        Matcher lBackgroundLineMatcher;

        lBackgroundLineMatcher = backgroundLinePattern.matcher(pTrimmedRawLine);
        if (lBackgroundLineMatcher.matches()) {
            lLineContents = lBackgroundLineMatcher.group(1);
            lGherkinLine = new GherkinLine(GherkinLineType.Background, lLineContents, pSourceLineNumber);
        } else {
            Matcher lFeatureLineMatcher = featureLinePattern.matcher(pTrimmedRawLine);
            if (lFeatureLineMatcher.matches()) {
                lLineContents = lFeatureLineMatcher.group(1);
                lGherkinLine = new GherkinLine(GherkinLineType.Feature, lLineContents, pSourceLineNumber);
            } else {
                Matcher lExampleLineMatcher = exampleLinePattern.matcher(pTrimmedRawLine);
                if (lExampleLineMatcher.matches()) {
                    lLineContents = lExampleLineMatcher.group(1);
                    lGherkinLine = new GherkinLine(GherkinLineType.Example, lLineContents, pSourceLineNumber);
                } else {
                    Matcher lExamplesLineMatcher = examplesLinePattern.matcher(pTrimmedRawLine);
                    if (lExamplesLineMatcher.matches()) {
                        lLineContents = lExamplesLineMatcher.group(1);
                        lGherkinLine = new GherkinLine(GherkinLineType.Examples, lLineContents, pSourceLineNumber);
                    } else {
                        Matcher lRuleLineMatcher = ruleLinePattern.matcher(pTrimmedRawLine);
                        if (lRuleLineMatcher.matches()) {
                            lLineContents = lRuleLineMatcher.group(1);
                            lGherkinLine = new GherkinLine(GherkinLineType.Rule, lLineContents, pSourceLineNumber);
                        } else {
                            Matcher lEcenarioLineMatcher = scenarioLinePattern.matcher(pTrimmedRawLine);
                            if (lEcenarioLineMatcher.matches()) {
                                lLineContents = lEcenarioLineMatcher.group(1);
                                lGherkinLine = new GherkinLine(GherkinLineType.Scenario, lLineContents,
                                        pSourceLineNumber);
                            } else {
                                Matcher lScenariosLineMatcher = scenariosLinePattern.matcher(pTrimmedRawLine);
                                if (lScenariosLineMatcher.matches()) {
                                    lLineContents = lScenariosLineMatcher.group(1);
                                    lGherkinLine = new GherkinLine(GherkinLineType.Scenarios, lLineContents,
                                            pSourceLineNumber);
                                } else {
                                    Matcher lScenarioOutlineLineMatcher = scenarioOutlineLinePattern
                                            .matcher(pTrimmedRawLine);
                                    if (lScenarioOutlineLineMatcher.matches()) {
                                        lLineContents = lScenarioOutlineLineMatcher.group(1);
                                        lGherkinLine = new GherkinLine(GherkinLineType.ScenarioOutline, lLineContents,
                                                pSourceLineNumber);
                                    } else {
                                        Matcher lScenarioTemplateLineMatcher = scenarioTemplateLinePattern
                                                .matcher(pTrimmedRawLine);
                                        if (lScenarioTemplateLineMatcher.matches()) {
                                            lLineContents = lScenarioTemplateLineMatcher.group(1);
                                            lGherkinLine = new GherkinLine(GherkinLineType.ScenarioTemplate,
                                                    lLineContents, pSourceLineNumber);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return lGherkinLine;
    }

    /**
     * Try and see if the raw line corresponds to something else than a step or a
     * container, and if it is, return the corresponding Gherkin line.
     * 
     * @param pRawLine          the raw input line.
     * @param pSourceLineNumber the number of the line in the source file.
     * @return the Gherkin line corresponding to the input line if it something else
     *         than a step or a container, <tt>null</tt> otherwise.
     */
    private GherkinLine getOtherGherkinLineFrom(final String pRawLine, final int pSourceLineNumber) {
        GherkinLine lGherkinLine = null;

        Matcher lCommentLineMatcher;
        String lLineContents;

        String lTrimmedRawLine = pRawLine.trim();

        lCommentLineMatcher = commentLinePattern.matcher(lTrimmedRawLine);
        if (lCommentLineMatcher.matches()) {
            lLineContents = lCommentLineMatcher.group(1);
            lGherkinLine = new GherkinLine(GherkinLineType.Comment, lLineContents, pSourceLineNumber);
        } else {
            Matcher lDataTableLineMatcher = dataTableLinePattern.matcher(lTrimmedRawLine);
            if (lDataTableLineMatcher.matches()) {
                lLineContents = lDataTableLineMatcher.group(1);
                lGherkinLine = new GherkinLine(GherkinLineType.DataTable, lLineContents, pSourceLineNumber);
            } else {
                Matcher lDocStringLineMatcher = docStringLinePattern.matcher(lTrimmedRawLine);
                if (lDocStringLineMatcher.matches()) {
                    lLineContents = pRawLine;
                    lGherkinLine = new GherkinLine(GherkinLineType.DocString, lLineContents, pSourceLineNumber);
                } else {
                    lLineContents = pRawLine;
                    lGherkinLine = new GherkinLine(GherkinLineType.Description, lLineContents, pSourceLineNumber);
                }
            }
        }

        return lGherkinLine;
    }
}
