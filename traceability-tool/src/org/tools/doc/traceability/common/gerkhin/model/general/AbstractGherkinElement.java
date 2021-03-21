/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.common.gerkhin.GherkinConstants;

/**
 * Common superclass for all Gherkin elements.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractGherkinElement {

    /**
     * The category of the Gherkin element.
     */
    final GherkinElementCategoryType elementCategory;

    /**
     * The number of the line in the source file this line corresponds to (first
     * is 1).
     */
    private final int sourceFileLineNumber;

    /**
     * The list of attached comment lines.
     */
    private final List<GherkinComment> comments;

    /**
     * Constructor.
     * 
     * @param pElementCategory the element category.
     * @param pSourceFileLineNumber the number of the line in the source file
     * this line corresponds to (first is 1).
     */
    public AbstractGherkinElement(final GherkinElementCategoryType pElementCategory, final int pSourceFileLineNumber) {
        super();
        elementCategory = pElementCategory;
        sourceFileLineNumber = pSourceFileLineNumber;
        comments = new ArrayList<GherkinComment>();
    }

    /**
     * Getter of the element category.
     * 
     * @return the elementCategory
     */
    public GherkinElementCategoryType getElementCategory() {
        return elementCategory;
    }

    /**
     * Print the element on the standard output.
     * 
     * @param pIndentationLevel the indentation level.
     */
    public void printOnStdout(final int pIndentationLevel) {
        this.printOn(System.out, pIndentationLevel);
    }

    /**
     * Getter of the comments list.
     * 
     * @return the additionalComments
     */
    public final List<GherkinComment> getComments() {
        return comments;
    }

    /**
     * Getter of the number of the line in the source file this line corresponds
     * to (first is 1).
     * 
     * @return the sourceFileLineNumber
     */
    public int getSourceFileLineNumber() {
        return sourceFileLineNumber;
    }

    /**
     * Add an associated comment attached to the element.
     * 
     * @param pComment the comment to add.
     */
    public final void addComment(final GherkinComment pComment) {
        if (pComment != null) {
            comments.add(pComment);
        }
    }

    /**
     * Gives a representation of this instance on the steam at the given
     * indentation level.
     * 
     * <p>
     * Implementor shall call {@link #printComments(PrintStream, int)} first.
     * </p>
     * 
     * @param pPrintStream the destination print stream.
     * @param pIndentationLevel the indentation level.
     */
    protected abstract void printOn(PrintStream pPrintStream, int pIndentationLevel);

    /**
     * Print the potentially attached comments.
     * 
     * @param pPrintStream the destination print stream.
     * @param pIndentationLevel the indentation level.
     */
    protected void printComments(final PrintStream pPrintStream, final int pIndentationLevel) {
        for (GherkinComment lGherkinComment : comments) {
            printIndented(GherkinConstants.COMMENT_KEYWORD + lGherkinComment.getStepText(), pIndentationLevel,
                    pPrintStream);
        }
    }

    /**
     * Add the given contents on the stream managing the given indentation.
     * <p>
     * A new line is appended first.
     * </p>
     * 
     * @param pContents the contents to add (shall be only one line).
     * @param pIndentationLevel the indentation level (0 for none).
     * @param pPrintStream the output print stream.
     */
    protected void printIndented(final String pContents, final int pIndentationLevel, final PrintStream pPrintStream) {
        // Add a new line
        pPrintStream.append("\n");

        // Manage the indentation
        for (int lIndentation = 0; lIndentation < pIndentationLevel; lIndentation++) {
            pPrintStream.append("  ");
        }

        // Add the actual contents
        pPrintStream.append(pContents);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        ByteArrayOutputStream lBaos = new ByteArrayOutputStream();
        PrintStream lPs = new PrintStream(lBaos);

        printOn(lPs, 0);

        String lRep = lBaos.toString();

        try {
            lBaos.close();
        } catch (IOException e) {
            // Ignore
        }

        return lRep;
    }

}
