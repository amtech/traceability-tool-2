/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model.general;

/**
 * Common superclass for Gherkin container that only have a DataTable
 * sub-element.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractGherkinContainerWithDataTableElement extends AbstractGherkinContainerElement {

    /**
     * The associated data table.
     */
    private GherkinDataTable dataTable;

    /**
     * Constructor.
     * 
     * @param pContainerKeyword The keyword associated with the container
     * (without the semicolon).
     * @param pSourceFileLineNumber the number of the line in the source file
     * this line corresponds to (first is 1).
     * @param pContainerDescription the description after the container keyword.
     */
    public AbstractGherkinContainerWithDataTableElement(final String pContainerKeyword,
            final int pSourceFileLineNumber, final String pContainerDescription) {
        super(pContainerKeyword, pSourceFileLineNumber, pContainerDescription);

        dataTable = null;
    }

    /**
     * Getter of the dataTable.
     * 
     * @return the dataTable
     */
    public GherkinDataTable getDataTable() {
        return dataTable;
    }

    /**
     * Setter of the dataTable.
     * 
     * @param pDataTable the dataTable to set
     */
    public void setDataTable(final GherkinDataTable pDataTable) {
        dataTable = pDataTable;
    }

    /**
     * Indicates whether a DataTable is associated with the element or not.
     * 
     * @return <tt>true</tt> if there is an associated DataTable, <tt>false</tt>
     * otherwise.
     */
    public boolean hasAssociatedDataTable() {
        return (dataTable != null);
    }

}
