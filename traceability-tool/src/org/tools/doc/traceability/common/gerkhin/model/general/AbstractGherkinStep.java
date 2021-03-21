/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common superclass for Gherkin steps.
 * <p>
 * A step can contain parameters between &lt; and &gt;.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractGherkinStep extends AbstractGherkinSingleLinedElement {

    /**
     * The regular expression to find parameter values.
     * <p>
     * The placeholder is the parameter name.
     * </p>
     */
    private static final String PARAMETER_REGEXP = "<\\s*([^>][^>]*)\\s*>";

    /**
     * The pattern to match parameters.
     */
    private static final Pattern PARAMETER_PATTERN = Pattern.compile(PARAMETER_REGEXP);

    /**
     * The keyword associated with the step.
     */
    private final String stepKeyword;

    /**
     * The type of step.
     */
    private final GherkinStepType stepType;

    /**
     * The list of parameters present in the step description.
     */
    private final List<String> parameters;

    /**
     * The potential associated doc string.
     */
    private GherkinDocString associatedDocString;

    /**
     * The potential associated data table.
     */
    private GherkinDataTable associatedDataTable;

    /**
     * Constructor.
     * 
     * @param pStepKeyword the keyword associated with the step.
     * @param pSourceFileLineNumber the number of the line in the source file
     * this line corresponds to (first is 1).
     * @param pStepType the step type.
     * @param pStepText The text of the final element.
     */
    protected AbstractGherkinStep(final String pStepKeyword, final int pSourceFileLineNumber,
            final GherkinStepType pStepType, final String pStepText) {
        super(GherkinElementCategoryType.Step, pSourceFileLineNumber, pStepText);
        stepKeyword = pStepKeyword;
        stepType = pStepType;
        parameters = new ArrayList<String>();

        associatedDocString = null;
        associatedDataTable = null;

        fillParametersFromText(pStepText);
    }

    /**
     * Getter of the parameter names found in the step text.
     * 
     * @return the parameters (can be empty of no parameter was found).
     */
    public List<String> getParameters() {
        return parameters;
    }

    /**
     * Parse the step text and extract the potential parameters in it.
     * <p>
     * If found, they are added in {@link #parameters} list.
     * </p>
     * 
     * @param pStepText the step text to consider.
     */
    private void fillParametersFromText(final String pStepText) {

        if (pStepText != null) {
            Matcher lParamaterMatcher = null;
            synchronized (PARAMETER_PATTERN) {
                lParamaterMatcher = PARAMETER_PATTERN.matcher(pStepText);
            }

            String lParameter;
            while (lParamaterMatcher.find()) {
                lParameter = lParamaterMatcher.group(0);

                parameters.add(lParameter);
            }
        }
    }

    /**
     * Getter of the step type.
     * 
     * @return the stepType
     */
    public GherkinStepType getStepType() {
        return stepType;
    }

    /**
     * Getter of the potential associated doc string.
     * 
     * @return the associatedDocString (or <tt>null</tt> if none).
     */
    public GherkinDocString getAssociatedDocString() {
        return associatedDocString;
    }

    /**
     * Setter of the associated doc string.
     * 
     * @param pAssociatedDocString the associatedDocString to set
     */
    public void setAssociatedDocString(final GherkinDocString pAssociatedDocString) {
        associatedDocString = pAssociatedDocString;
    }

    /**
     * Indicates whether a DocString is associated with the step or not.
     * 
     * @return <tt>true</tt> if there is an associated DocString, <tt>false</tt>
     * otherwise.
     */
    public boolean hasAssociatedDocString() {
        return (associatedDocString != null);
    }

    /**
     * Getter of the potential associated data table.
     * 
     * @return the associatedDataTable (can be <tt>null</tt> if there is none).
     */
    public GherkinDataTable getAssociatedDataTable() {
        return associatedDataTable;
    }

    /**
     * Setter of the associated data table.
     * 
     * @param pAssociatedDataTable the associatedDataTable to set
     */
    public void setAssociatedDataTable(final GherkinDataTable pAssociatedDataTable) {
        associatedDataTable = pAssociatedDataTable;
    }

    /**
     * Indicates whether a DataTable is associated with the step or not.
     * 
     * @return <tt>true</tt> if there is an associated DataTable, <tt>false</tt>
     * otherwise.
     */
    public boolean hasAssociatedDataTable() {
        return (associatedDataTable != null);
    }

    /**
     * Computes the contents of the line describing this step.
     * 
     * @return the step line in Gherkin syntax.
     */
    public String getStepLine() {

        StringBuilder lLineSb = new StringBuilder();
        lLineSb.append(stepKeyword);
        if (!getStepText().isEmpty()) {
            lLineSb.append(" ");
            lLineSb.append(getStepText());
        }

        return lLineSb.toString();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void printOn(final PrintStream pPrintStream, final int pIndentationLevel) {

        // Add the step line
        printIndented(getStepLine(), pIndentationLevel, pPrintStream);

        // Add the potential associated comment lines
        printComments(pPrintStream, pIndentationLevel + 1);

        // Add the potential Data Table indented if any
        if (associatedDataTable != null) {
            associatedDataTable.printOn(pPrintStream, pIndentationLevel + 1);
        }

        // Add the potential Doc String indented if any
        if (associatedDocString != null) {
            associatedDocString.printOn(pPrintStream, pIndentationLevel + 1);
        }
    }
}
