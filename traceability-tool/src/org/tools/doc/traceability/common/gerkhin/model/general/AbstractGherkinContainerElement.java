/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

import org.tools.doc.traceability.common.gerkhin.GherkinConstants;

/**
 * Common superclass for Gherkin element that can contain other elements (except
 * for steps).
 * 
 * <p>
 * They are lines with a keyword followed with a colon (:).
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractGherkinContainerElement extends AbstractGherkinElement {

    /**
     * The keyword associated with the container (without the semicolon).
     */
    private final String containerKeyword;

    /**
     * The description after the container keyword.
     */
    private final String containerDescription;

    /**
     * Constructor.
     * 
     * @param pContainerKeyword The keyword associated with the container
     * (without the semicolon).
     * @param pSourceFileLineNumber the number of the line in the source file
     * this line corresponds to (first is 1).
     * @param pContainerDescription the description after the container keyword.
     */
    public AbstractGherkinContainerElement(final String pContainerKeyword, final int pSourceFileLineNumber,
            final String pContainerDescription) {
        super(GherkinElementCategoryType.Container, pSourceFileLineNumber);
        containerKeyword = pContainerKeyword;
        containerDescription = pContainerDescription;
    }

    /**
     * Getter of the keyword associated with the container element.
     * 
     * @return the container keyword.
     */
    public String getContainerKeyword() {
        return containerKeyword;
    }

    /**
     * Getter of the container description (the one after the keyword, on the
     * same line).
     * 
     * @return the containerDescription
     */
    public String getContainerDescription() {
        return containerDescription;
    }

    /**
     * Computes the contents of the line describing this container.
     * 
     * @return the container line in Gherkin syntax.
     */
    public String getContainerLine() {

        StringBuilder lLineSb = new StringBuilder();
        lLineSb.append(containerKeyword);
        lLineSb.append(GherkinConstants.CONTAINER_COLON_KEYWORD);
        if (!getContainerDescription().isEmpty()) {
            lLineSb.append(" ");
            lLineSb.append(getContainerDescription());
        }

        return lLineSb.toString();
    }

}
