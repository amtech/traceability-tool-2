/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

/**
 * The possible categories of Gherkin elements.
 * 
 * @author Yann Leglise
 *
 */
public enum GherkinElementCategoryType {

    /**
     * Description (additional for some containers, as Feature, Background, Rule,
     * Example/Scenario, Scenario Outline/Scenario Example.
     */
    AdditionalDescription,

    /**
     * Comment.
     */
    Comment,

    /**
     * A container (all followed with :).
     */
    Container,

    /**
     * Data element (DocString, DataTable).
     */
    Data,

    /**
     * A step (And, But, Then, When, *).
     */
    Step;

}
