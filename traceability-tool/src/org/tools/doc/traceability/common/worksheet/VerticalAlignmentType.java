/**
 * 
 */
package org.tools.doc.traceability.common.worksheet;

import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * The possible vertical alignment types in a cell.
 * 
 * @author Yann Leglise
 *
 */
public enum VerticalAlignmentType {

    /**
     * Vertically centered text alignment.
     */
    CENTERED(VerticalAlignment.CENTER),
    /**
     * Vertically justified text alignment.
     */
    JUSTIFIED(VerticalAlignment.JUSTIFY),
    /**
     * Top text alignment.
     */
    TOP(VerticalAlignment.TOP),
    /**
     * Bottom text alignment.
     */
    BOTTOM(VerticalAlignment.BOTTOM);

    /**
     * The associated value for the alignment in the Apache POI library.
     */
    private final VerticalAlignment apacheAssociatedValue;

    /**
     * Constructor.
     * 
     * @param pApacheAssociatedValue the associated value for the alignment in the
     *                               Apache POI library.
     */
    VerticalAlignmentType(final VerticalAlignment pApacheAssociatedValue) {
        apacheAssociatedValue = pApacheAssociatedValue;
    }

    /**
     * Getter of the associated value for the alignment in the Apache POI library.
     * 
     * @return the apacheAssociatedValue
     */
    public VerticalAlignment getApacheAssociatedValue() {
        return apacheAssociatedValue;
    }
}
