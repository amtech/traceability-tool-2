/**
 * 
 */
package org.tools.doc.traceability.common.worksheet;

import org.apache.poi.ss.usermodel.BorderStyle;

/**
 * The possible cell border styles.
 * 
 * @author Yann Leglise
 *
 */
public enum CellBorderType {

    /**
     * No cell border.
     */
    NONE(BorderStyle.NONE),
    /**
     * Thin cell border.
     */
    THIN(BorderStyle.THIN),
    /**
     * Medium cell border.
     */
    MEDIUM(BorderStyle.MEDIUM),
    /**
     * Thick cell border.
     */
    THICK(BorderStyle.THICK);

    /**
     * The associated value for the border style in the Apache POI library.
     */
    private final BorderStyle apacheAssociatedValue;

    /**
     * Constructor.
     * 
     * @param anApacheAssociatedValue the associated value for the border style in
     *                                the Apache POI library.
     */
    CellBorderType(final BorderStyle anApacheAssociatedValue) {
        apacheAssociatedValue = anApacheAssociatedValue;
    }

    /**
     * Getter of the associated value for the border style in the Apache POI
     * library.
     * 
     * @return the apacheAssociatedValue
     */
    public BorderStyle getApacheAssociatedValue() {
        return apacheAssociatedValue;
    }
}
