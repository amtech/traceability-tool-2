/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin;

import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinElement;

/**
 * Class modeling the result of an attempt to extract a Gherking element from a
 * series of Gherkin lines.
 * <p>
 * It holds the extracted element, and the index of the last used index in the
 * lines.
 * </p>
 * 
 * @param <T> the actual class of the element to create.
 * @author Yann Leglise
 *
 */
public class GherkinElementExtractionResult<T extends AbstractGherkinElement> {

    /**
     * The extracted element.
     */
    private final T extractedElement;

    /**
     * The index of the last line used to create the element.
     */
    private final int lastLineIndex;

    /**
     * Whether the element was extracted or not.
     */
    private final boolean wasElementExtracted;

    /**
     * Constructor.
     * 
     * @param pWasElementExtracted <tt>true</tt> if the element could be
     * extracted, <tt>false</tt> if not.
     * @param pExtractedElement the extracted element (Can be <tt>null</tt> if
     * not created).
     * @param pLastLineIndex the index of the last line used to create the
     * element.
     */
    public GherkinElementExtractionResult(final boolean pWasElementExtracted, final T pExtractedElement,
            final int pLastLineIndex) {
        super();
        wasElementExtracted = pWasElementExtracted;
        extractedElement = pExtractedElement;
        lastLineIndex = pLastLineIndex;
    }

    /**
     * Getter of the extracted element.
     * 
     * @return the extractedElement
     */
    public T getExtractedElement() {
        return extractedElement;
    }

    /**
     * Getter of the index of the last line used to create the element.
     * 
     * @return the lastLineIndex
     */
    public int getLastLineIndex() {
        return lastLineIndex;
    }

    /**
     * Getter of the flag indicating whether the element was extracted or not.
     * 
     * @return the extractionSuccessful
     */
    public boolean wasElementExtracted() {
        return wasElementExtracted;
    }

}
