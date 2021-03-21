/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin;

/**
 * Represents a Gherkin line.
 * <p>
 * It contains the raw line itself, plus its type.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class GherkinLine {

    /**
     * The type of the Gherkin line.
     */
    private final GherkinLineType type;

    /**
     * The line contents (without the keyword if any).
     */
    private final String lineContents;

    /**
     * The number of the line in the source file this line corresponds to (first is
     * 1).
     */
    private final int sourceFileLineNumber;

    /**
     * Constructor.
     * 
     * @param pType                 the Gherkin line type.
     * @param pLineContents         The line contents (without the keyword if any).
     * @param pSourceFileLineNumber The number of the line in the source file this
     *                              line corresponds to (first is 1).
     */
    public GherkinLine(final GherkinLineType pType, final String pLineContents, final int pSourceFileLineNumber) {
        type = pType;
        lineContents = pLineContents;
        sourceFileLineNumber = pSourceFileLineNumber;
    }

    /**
     * Getter of the type.
     * 
     * @return the type
     */
    public GherkinLineType getType() {
        return type;
    }

    /**
     * Getter of the line contents (without the keyword if any)..
     * 
     * @return the lineContents
     */
    public String getLineContents() {
        return lineContents;
    }

    /**
     * Get the normalized representation of the Gherkin line.
     * 
     * @return the normalized representation of the Gherkin line.
     */
    public String getNormalizedRepresentation() {
        StringBuilder lSb = new StringBuilder();
        lSb.append(type.getRepresentation());
        lSb.append(lineContents);
        return lSb.toString();
    }

    /**
     * Getter of the number of the line in the source file this line corresponds to
     * (first is 1).
     * 
     * @return the sourceFileLineNumber
     */
    public int getSourceFileLineNumber() {
        return sourceFileLineNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder lSb = new StringBuilder("{");
        lSb.append(sourceFileLineNumber);
        lSb.append("} [");
        lSb.append(type.getRepresentation());
        lSb.append(lineContents);
        lSb.append("] ");
        return lSb.toString();
    }
}
