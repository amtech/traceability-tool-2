/**
 * 
 */
package org.tools.doc.traceability.common.worksheet;

import java.awt.Color;

/**
 * Defines the characteristics of a workbook cell style.
 * 
 * @author Yann Leglise
 *
 */
public class CellStyleCharacteristics {

    /**
     * The height of the font, in points.
     */
    private final short fontHeightInPoints;

    /**
     * Whether the font is bold or not.
     */
    private final boolean isFontBold;

    /**
     * The font color.
     */
    private final Color fontColor;

    /**
     * The color of the cell background.
     */
    private final Color cellBackgroundColor;

    /**
     * The text horizontal alignment in the cell.
     */
    private final HorizontalAlignmentType horizontalAlignment;

    /**
     * The text vertical alignment in the cell.
     */
    private final VerticalAlignmentType verticalAlignment;

    /**
     * Whether the text must be wrapped in the cell or not.
     */
    private final boolean wrapText;

    /**
     * The type of border to apply to the cell.
     */
    private final CellBorderType cellBorder;

    /**
     * Constructor.
     * 
     * @param pCellBorder          The type of border to apply to the cell.
     * @param pCellBackgroundColor The color of the cell background.
     * @param pWrapText            Whether the text must be wrapped in the cell or
     *                             not.
     * @param pHorizontalAlignment The text horizontal alignment in the cell.
     * @param pVerticalAlignment   The text vertical alignment in the cell.
     * @param pFontHeightInPoints  The height of the font, in points.
     * @param pIsFontBold          Whether the font is bold or not.
     * @param pFontColor           The font color.
     */
    public CellStyleCharacteristics(final CellBorderType pCellBorder, final Color pCellBackgroundColor,
            final boolean pWrapText, final HorizontalAlignmentType pHorizontalAlignment,
            final VerticalAlignmentType pVerticalAlignment, final short pFontHeightInPoints, final boolean pIsFontBold,
            final Color pFontColor) {
        super();
        cellBorder = pCellBorder;
        cellBackgroundColor = pCellBackgroundColor;
        wrapText = pWrapText;
        horizontalAlignment = pHorizontalAlignment;
        verticalAlignment = pVerticalAlignment;
        fontHeightInPoints = pFontHeightInPoints;
        isFontBold = pIsFontBold;
        fontColor = pFontColor;
    }

    /**
     * Getter of the height of the font, in points.
     * 
     * @return the fontHeightInPoints
     */
    public short getFontHeightInPoints() {
        return fontHeightInPoints;
    }

    /**
     * Getter of the flag telling whether the font is bold or not.
     * 
     * @return the isFontBold
     */
    public boolean isFontBold() {
        return isFontBold;
    }

    /**
     * Getter of the font color.
     * 
     * @return the fontColor
     */
    public Color getFontColor() {
        return fontColor;
    }

    /**
     * Getter of the cell background color.
     * 
     * @return the cellBackgroundColor
     */
    public Color getCellBackgroundColor() {
        return cellBackgroundColor;
    }

    /**
     * Getter of the horizontal alignment.
     * 
     * @return the horizontalAlignment
     */
    public HorizontalAlignmentType getHorizontalAlignment() {
        return horizontalAlignment;
    }

    /**
     * Getter of the vertical alignment.
     * 
     * @return the verticalAlignment
     */
    public VerticalAlignmentType getVerticalAlignment() {
        return verticalAlignment;
    }

    /**
     * Getter of the flag telling whether the text must be wrapped in the cell or
     * not.
     * 
     * @return the wrapText
     */
    public boolean isWrapText() {
        return wrapText;
    }

    /**
     * Getter of the cell border type.
     * 
     * @return the cellBorder
     */
    public CellBorderType getCellBorder() {
        return cellBorder;
    }
}
