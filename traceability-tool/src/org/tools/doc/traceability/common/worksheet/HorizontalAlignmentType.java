/**
 * 
 */
package org.tools.doc.traceability.common.worksheet;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * The possible horizontal alignment types in a cell.
 * 
 * @author Yann Leglise
 *
 */
public enum HorizontalAlignmentType {

    /**
     * Horizontally entered text alignment.
     */
    CENTERED(HorizontalAlignment.CENTER),
    /**
     * Horizontally justified text alignment.
     */
    JUSTIFIED(HorizontalAlignment.JUSTIFY),
    /**
     * Left text alignment.
     */
    LEFT(HorizontalAlignment.LEFT),
    /**
     * Right text alignment.
     */
    RIGHT(HorizontalAlignment.RIGHT);

    /**
     * The associated value for the alignment in the Apache POI library.
     */
    private final HorizontalAlignment apacheAssociatedValue;

    /**
     * Constructor.
     * 
     * @param pApacheAssociatedValue the associated value for the alignment in the
     *                               Apache POI library.
     */
    HorizontalAlignmentType(final HorizontalAlignment pApacheAssociatedValue) {
        apacheAssociatedValue = pApacheAssociatedValue;
    }

    /**
     * Getter of the associated value for the alignment in the Apache POI library.
     * 
     * @return the apacheAssociatedValue
     */
    public HorizontalAlignment getApacheAssociatedValue() {
        return apacheAssociatedValue;
    }

}
