/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin;

/**
 * The possible types for a Gherkin line.
 * 
 * @author Yann Leglise
 *
 */
public enum GherkinLineType {

    /**
     * Line starting with And keyword, corresponding to a step describing an
     * event (as When).
     * <p>
     * This is a step item.
     * </p>
     */
    And(GherkinConstants.AND_KEYWORD, GherkinConstants.AND_KEYWORD + " ", true, false, false),

    /**
     * Line starting with Background : keyword, defining a series of steps that
     * shall be played before each Scenario/Example (or in a Rule), but only one
     * can appear per Feature or Rule.
     * <p>
     * This is a container item.
     * </p>
     */
    Background(GherkinConstants.BACKGROUND_KEYWORD, GherkinConstants.BACKGROUND_KEYWORD + ": ", false, true, false),

    /**
     * Line starting with But keyword, corresponding to a step describing an
     * event (as When).
     * <p>
     * This is a step item.
     * </p>
     */
    But(GherkinConstants.BUT_KEYWORD, GherkinConstants.BUT_KEYWORD + " ", true, false, false),

    /**
     * Line starting with # corresponding to a comment line.
     */
    Comment(GherkinConstants.COMMENT_LINE_DESCRIPTION, GherkinConstants.COMMENT_KEYWORD + " ", false, false, true),

    /**
     * Line starting with |, defining a data table.
     */
    DataTable(GherkinConstants.DATA_TABLE_DESCRIPTION, "", false, true, false),

    /**
     * Line not starting with any of the keywords, containing description only.
     */
    Description(GherkinConstants.COMMENT_LINE_DESCRIPTION, "", false, false, false),

    /**
     * Line starting with """, defining the start/end of a documentation string
     * block.
     */
    DocString(GherkinConstants.DOC_STRING_DESCRIPTION, GherkinConstants.DOC_STRING_KEYWORD + " ", false, false, false),

    /**
     * Line starting with Feature : keyword, defining a software feature.
     * <p>
     * This is a container item.
     * </p>
     */
    Feature(GherkinConstants.FEATURE_KEYWORD, GherkinConstants.FEATURE_KEYWORD + ": ", false, true, false),

    /**
     * Line starting with Example : keyword, defining a business rule.
     * <p>
     * This is a container item.
     * </p>
     */
    Example(GherkinConstants.EXAMPLE_KEYWORD, GherkinConstants.EXAMPLE_KEYWORD + ": ", false, true, false),

    /**
     * Line starting with Examples : keyword, defining a template for the values
     * used in the parent Scenario Outline.
     * <p>
     * This is a container item.
     * </p>
     */
    Examples(GherkinConstants.EXAMPLES_KEYWORD, GherkinConstants.EXAMPLES_KEYWORD + ": ", false, true, false),

    /**
     * Line starting with Given keyword, corresponding to a step describing an
     * initial context of the system.
     * <p>
     * This is a step item.
     * </p>
     */
    Given(GherkinConstants.GIVEN_KEYWORD, GherkinConstants.GIVEN_KEYWORD + " ", true, false, false),

    /**
     * Line starting with Rule : keyword, defining a business rule that shall be
     * implemented.
     * <p>
     * This is a container item.
     * </p>
     */
    Rule(GherkinConstants.RULE_KEYWORD, GherkinConstants.RULE_KEYWORD + ": ", false, true, false),

    /**
     * Line starting with Scenario : keyword, defining a business rule.
     * <p>
     * This is a container item.
     * </p>
     */
    Scenario(GherkinConstants.SCENARIO_KEYWORD, GherkinConstants.SCENARIO_KEYWORD + ": ", false, true, false),

    /**
     * Line starting with Scenarios : keyword, defining a template for the
     * values used in the parent Scenario Outline.
     * <p>
     * This is a container item.
     * </p>
     */
    Scenarios(GherkinConstants.SCENARIOS_KEYWORD, GherkinConstants.SCENARIOS_KEYWORD + ": ", false, true, false),

    /**
     * Line starting with Scenario Outline : keyword, defining a scenario that
     * can be played several times with different combination of values .
     * <p>
     * This is a container item.
     * </p>
     */
    ScenarioOutline(GherkinConstants.SCENARIO_OUTLINE_KEYWORD, GherkinConstants.SCENARIO_OUTLINE_KEYWORD + ": ", false,
            true, false),

    /**
     * Line starting with Scenario Template : keyword, defining a scenario that
     * can be played several times with different combination of values .
     * <p>
     * This is a container item.
     * </p>
     */
    ScenarioTemplate(GherkinConstants.SCENARIO_TEMPLATE_KEYWORD, GherkinConstants.SCENARIO_TEMPLATE_KEYWORD + ": ",
            false, true, false),

    /**
     * Line starting with * keyword, corresponding to a step describing an event
     * (as When).
     * <p>
     * This is a step item.
     * </p>
     */
    Star(GherkinConstants.STAR_LINE_DESCRIPTION, GherkinConstants.STAR_KEYWORD + " ", true, false, false),

    /**
     * Line starting with Then keyword, corresponding to a step describing an
     * expected outcome.
     * <p>
     * This is a step item.
     * </p>
     */
    Then(GherkinConstants.THEN_KEYWORD, GherkinConstants.THEN_KEYWORD + " ", true, false, false),

    /**
     * Line starting with When keyword, corresponding to a step describing an
     * event.
     * <p>
     * This is a step item.
     * </p>
     */
    When(GherkinConstants.WHEN_KEYWORD, GherkinConstants.WHEN_KEYWORD + " ", true, false, false),

    ;

    /**
     * The name of the line type.
     */
    private final String lineTypeName;

    /**
     * Whether this line corresponds to a step.
     */
    private final boolean isStep;

    /**
     * Whether this line corresponds to a container (i.e. with :).
     */
    private final boolean isContainer;

    /**
     * Whether the line correspond to a comment.
     */
    private final boolean isComment;

    /**
     * The representation for this line (i.e. the leading keyword, if any).
     */
    private final String representation;

    /**
     * Constructor.
     * 
     * @param pLineTypeName the name of the line type.
     * @param pRepresentation The representation for this line (i.e. the leading
     * keyword, if any).
     * @param pIsStep Whether this line corresponds to a step.
     * @param pIsContainer Whether this line corresponds to a container (i.e.
     * with :).
     * @param pIsComment Whether the line correspond to a comment.
     */
    GherkinLineType(final String pLineTypeName, final String pRepresentation, final boolean pIsStep,
            final boolean pIsContainer, final boolean pIsComment) {
        lineTypeName = pLineTypeName;
        representation = pRepresentation;
        isStep = pIsStep;
        isContainer = pIsContainer;
        isComment = pIsComment;
    }

    /**
     * Getter of the isStep.
     * 
     * @return <tt>true</tt> if this line corresponds to a step, <tt>false</tt>
     * otherwise.
     */
    public boolean isStep() {
        return isStep;
    }

    /**
     * Getter of the isContainer.
     * 
     * @return @return <tt>true</tt> if this line corresponds to a container,
     * <tt>false</tt> otherwise.
     */
    public boolean isContainer() {
        return isContainer;
    }

    /**
     * Getter of the isComment.
     * 
     * @return @return <tt>true</tt> if this line corresponds to a comment,
     * <tt>false</tt> otherwise.
     */
    public boolean isComment() {
        return isComment;
    }

    /**
     * Getter of the representation for this line (i.e. the leading keyword, if
     * any).
     * 
     * @return the representation
     */
    public String getRepresentation() {
        return representation;
    }

    /**
     * Getter of the line type name.
     * 
     * @return the line type name.
     */
    public String getLineTypeName() {
        return lineTypeName;
    }

}
