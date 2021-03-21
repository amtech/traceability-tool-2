/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin;

/**
 * Constants for Gherkin.
 * 
 * <p>
 * Here is the grammar of Gherkin language:
 * 
 * <ul>
 * <li><b>File contents</b> The file can contain at any place comment lines.
 * There can also have tag lines. But other than that, the first element
 * expected is <b>Feature</b>. Spaces or tabulations can be used to indent
 * lines.
 * <ul>
 * <li>Common elements. Here are the descriptions of the elements that can
 * appear in other elements.
 * <ul>
 * <li><b>Comment</b> A comment is a a giving information, but it is not
 * actually used in the execution. It is represented by a line starting with a
 * sharp sign (<tt><b>#</b></tt>) followed by the actual comment. It is not
 * allowed to have a comment at the end of an element line (The <tt>#</tt> shall
 * be the first non-blank character of the line).
 * <li><b>Tag</b> A tag is used by unit test frameworks. It is represented by a
 * line starting with an at sign (<tt><b>@</b></tt>) followed by the tag name.
 * <li><b>[DocString]</b> They represent a handy way to pass a larger piece of
 * text to a step definition. Its value is a set of lines comprised between two
 * lines consisting of <tt><b>'''</b></tt> or <tt><b>```</b></tt>. The contents
 * includes the indentation on those lines.
 * <li><b>[DataTable]</b> They represent a handy way to pass a list of values to
 * a step definition. Its value is a set of lines starting and ending with
 * <tt><b>|</b></tt>, and that can contain more <tt><b>|</b></tt> to separate
 * the actual values.
 * <li><b>[Step]</b>. Steps are one of the following elements:
 * <ul>
 * <li><b>And</b> Replaces <tt><b>Given</b></tt>, <tt><b>Then</b></tt> or
 * <tt><b>When</b></tt> when several of them follow each others
 * <tt><b>Given</b></tt>. It is represented by a line starting with
 * <tt><b>And</b></tt> and followed by the step description.
 * <li><b>But</b> Replaces <tt><b>Given</b></tt>, <tt><b>Then</b></tt> or
 * <tt><b>When</b></tt> when several of them follow each others
 * <tt><b>Given</b></tt>. It is represented by a line starting with
 * <tt><b>But</b></tt> and followed by the step description.
 * <li><b>Given</b> Describes an initial context of the system. It is
 * represented by a line starting with <tt><b>Given</b></tt> and followed by the
 * step description.
 * <li><b>Then</b> Describes an expected outcome. It is represented by a line
 * starting with <tt><b>Then</b></tt> and followed by the step description.
 * <li><b>When</b> Describes an event or an action. It is represented by a line
 * starting with <tt><b>When</b></tt> and followed by the step description.
 * </ul>
 * They all can be followed by one of:
 * <ul>
 * <li><b>[DocString]</b>
 * <li><b>[DataTable]</b>
 * </ul>
 * </ul>
 * <li><b>Feature</b> This is the root element in a file. It is mandatory. It is
 * represented by a line starting with keyword <tt><b>Feature:</b></tt>. The
 * keyword can be followed by a description. There can be description lines just
 * after this line. They complete the description that followed the keyword.
 * Then, a given number of authorized elements can be found under it.
 * <ul>
 * <li><b>Background</b> This is an element that gathers instructions to be
 * executed for each Scenario/Example (it shall be placed before the first of
 * those). There can be only one <tt>Background:</tt> per <tt>Feature:</tt>. It
 * can only contain <tt><b>Given</b></tt> steps.
 * <ul>
 * <li><b>Given</b> (One or more)
 * </ul>
 * <li><b>Scenario</b> (or <b>Example</b>) This is a concrete example that
 * illustrates a business rule. It is represented by a line starting with
 * <tt><b>Scenario:</b></tt> (or <tt><b>Example:</b></tt>). It can contain any
 * number of steps.
 * <ul>
 * <li><b>[Step]</b> (one or more)
 * </ul>
 * <li><b>Scenario Outline</b> (or <b>Scenario Template</b>). It can be used to
 * run the same <b>Scenario</b> multiple times, with different combination of
 * values. It is represented by a line starting with
 * <tt><b>Scenario Outline:</b></tt> (or <tt><b>Scenario Template:</b></tt>). It
 * can contain any number of <b>[Step]</b>, and must contain a <b>Examples</b>
 * (or a <b>Scenarios</b>). The steps can contain parameters, delimited by
 * <tt><b>&lt; &gt;</b></tt>, that will be replaced by the values from the
 * <b>[DataTable]</b> of the <b>Examples</b> (or <b>Scenarios</b>) element
 * corresponding to the matching column name.
 * <ul>
 * <li><b>[Step]</b> (one or more)
 * <li><b>Examples</b> | <b>Scenarios</b> (exactly one)
 * </ul>
 * <li><b>Examples</b> (or <b>Scenarios</b>). This gives the data to be used in
 * a <b>Scenario Outline</b> (or <b>Scenario Template</b>). This is a line
 * starting with <tt><b>Examples:</b></tt> (or <tt><b>Scenarios:</b></tt>),
 * followed by a <b>[DataTable]</b> with the first line of it giving the name of
 * the parameters, and the next lines being the values for those parameters to
 * play for all the steps.
 * <ul>
 * <li><b>[DataTable]</b>
 * </ul>
 * </ul>
 * <li><b>Rule</b> It is used to represent a business rule, and gather one or
 * more <b>Scenario</b> (ore <b>Example</b> element(s) that belongs to it. It is
 * represented by a line starting with <tt><b>Rule:</b></tt>, that can be
 * followed by a description (or name) of this rule.
 * <ul>
 * <li><tt><b>Scenario</b></tt>|<tt><b>Example</b></tt> (one or more)
 * </ul>
 * </ul>
 * </ul>
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public final class GherkinConstants {

    /**
     * The keyword for And step.
     */
    public static final String AND_KEYWORD = "And";

    /**
     * The keyword for Backgroud container (without the :).
     */
    public static final String BACKGROUND_KEYWORD = "Background";

    /**
     * The keyword for But step.
     */
    public static final String BUT_KEYWORD = "But";

    /**
     * The keyword for comment lines.
     */
    public static final String COMMENT_KEYWORD = "#";

    /**
     * The textual description to refer to comment lines.
     */
    public static final String COMMENT_LINE_DESCRIPTION = "Comment line";

    /**
     * The colon (:) after container keywords.
     */
    public static final String CONTAINER_COLON_KEYWORD = ":";

    /**
     * The keyword for Data tables.
     */
    public static final String DATA_TABLE_KEYWORD = "|";

    /**
     * The textual description to refer to Data tables.
     */
    public static final String DATA_TABLE_DESCRIPTION = "Data Table";

    /**
     * The textual description to refer to description lines.
     */
    public static final String DESCRIPTION_LINE_DESCRIPTION = "Description line";

    /**
     * The keyword for DocString.
     */
    public static final String DOC_STRING_KEYWORD = "\"\"\"";

    /**
     * The alternative keyword for DocString.
     */
    public static final String DOC_STRING_ALT_KEYWORD = "```";

    /**
     * The textual description to refer to Doc strings.
     */
    public static final String DOC_STRING_DESCRIPTION = "Doc String";

    /**
     * The keyword for Feature: container (without the :).
     */
    public static final String FEATURE_KEYWORD = "Feature";

    /**
     * The keyword for Example: container (without the :).
     */
    public static final String EXAMPLE_KEYWORD = "Example";

    /**
     * The keyword for Examples: container (without the :).
     */
    public static final String EXAMPLES_KEYWORD = "Examples";

    /**
     * The keyword for Given step.
     */
    public static final String GIVEN_KEYWORD = "Given";

    /**
     * The keyword for Rule: container (without the :).
     */
    public static final String RULE_KEYWORD = "Rule";

    /**
     * The keyword for Scenario: container (without the :).
     */
    public static final String SCENARIO_KEYWORD = "Scenario";

    /**
     * The keyword for Scenarios: container (without the :).
     */
    public static final String SCENARIOS_KEYWORD = "Scenarios";

    /**
     * The keyword for Scenario Outline: container (without the :).
     */
    public static final String SCENARIO_OUTLINE_KEYWORD = "Scenario Outline";

    /**
     * The keyword for Scenario Template: container (without the :).
     */
    public static final String SCENARIO_TEMPLATE_KEYWORD = "Scenario Template";

    /**
     * The keyword for * (star) step.
     */
    public static final String STAR_KEYWORD = "*";

    /**
     * The textual description to refer to star (*) lines.
     */
    public static final String STAR_LINE_DESCRIPTION = "Star (*) line";

    /**
     * The keyword for Then step.
     */
    public static final String THEN_KEYWORD = "Then";

    /**
     * The keyword for When step.
     */
    public static final String WHEN_KEYWORD = "When";
}
