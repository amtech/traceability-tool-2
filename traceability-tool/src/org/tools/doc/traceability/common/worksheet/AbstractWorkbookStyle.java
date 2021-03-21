/**
 * 
 */
package org.tools.doc.traceability.common.worksheet;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.tools.doc.traceability.common.Constants;

/**
 * A common implementation to define the styles used in a workbook.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractWorkbookStyle {

    /**
     * The workbook where the styles will be appiied.
     */
    private final XSSFWorkbook workbook;

    /**
     * A map associating the style name with the celle style.
     */
    private final Map<String, XSSFCellStyle> workbookCellStyleMap;

    /**
     * Constructor.
     * 
     * @param pWorkbook the target workbook (shall not be null).
     */
    public AbstractWorkbookStyle(final XSSFWorkbook pWorkbook) {

        workbook = pWorkbook;
        workbookCellStyleMap = new HashMap<String, XSSFCellStyle>();

        Map<String, CellStyleCharacteristics> lStyleNameCharacteristicsMap = new HashMap<String, CellStyleCharacteristics>();
        fillStyleCharacteristics(lStyleNameCharacteristicsMap);

        // Iterate on the map to create the styles
        for (String lStyleName : lStyleNameCharacteristicsMap.keySet()) {

            // Get the characteristics of this style
            CellStyleCharacteristics lCellStyleCharacteristics = lStyleNameCharacteristicsMap.get(lStyleName);
            if (lCellStyleCharacteristics != null) {

                // Convert the characteristics to the style
                XSSFCellStyle lXSSFCellStyle = getCellStyleFor(lCellStyleCharacteristics);

                // Fill the final map
                workbookCellStyleMap.put(lStyleName, lXSSFCellStyle);
            }
        }
    }

    /**
     * Let the subclass define the characteristics for the needed styles.
     * 
     * @param pStyleNameCharacteristicsMap a map to fill with the name of a
     * style, and its characteristics.
     */
    protected abstract void fillStyleCharacteristics(
            Map<String, CellStyleCharacteristics> pStyleNameCharacteristicsMap);

    /**
     * Create a cell style characteristics for contents (i.e. not header).
     * 
     * @param pHorizontalAlignment the horizontal alignment.
     * @param pVerticalAlignment the vertical alignment.
     * @param pIsTextBold <tt>true</tt> if the text shall be bold,
     * <tt>false</tt> if not.
     * 
     * @return the created style.
     */
    protected CellStyleCharacteristics getStyleCharacteristicsForValueCell(
            final HorizontalAlignmentType pHorizontalAlignment, final VerticalAlignmentType pVerticalAlignment,
            final boolean pIsTextBold) {
        CellStyleCharacteristics lValueCellStyleCharacteristics = new CellStyleCharacteristics(
                Constants.WORKBOOK_CELL_BORDER, Constants.WORKBOOK_CELL_BACKGROUND_COLOR, true, pHorizontalAlignment,
                pVerticalAlignment, Constants.WORKBOOK_FONT_HEIGHT, pIsTextBold, Constants.WORKBOOK_NORMAL_TEXT_COLOR);

        return lValueCellStyleCharacteristics;
    }
    
    /**
     * Create a cell style characteristics for referencing a not covered requirement.
     * 
     * @param pHorizontalAlignment the horizontal alignment.
     * @param pVerticalAlignment the vertical alignment.
     * @param pIsTextBold <tt>true</tt> if the text shall be bold,
     * <tt>false</tt> if not.
     * 
     * @return the created style.
     */
    protected CellStyleCharacteristics getStyleCharacteristicsForMissingReqCell(
            final HorizontalAlignmentType pHorizontalAlignment, final VerticalAlignmentType pVerticalAlignment,
            final boolean pIsTextBold)
    {
        CellStyleCharacteristics lMissingReqCellStyleCharacteristics = new CellStyleCharacteristics(
                Constants.WORKBOOK_CELL_BORDER, Constants.WORKBOOK_MISSING_REQUIREMENT_CELL_BACKGROUND_COLOR, true,
                pHorizontalAlignment, pVerticalAlignment, Constants.WORKBOOK_FONT_HEIGHT, pIsTextBold,
                Constants.WORKBOOK_MISSING_REQUIREMENT_CELL_TEXT_COLOR);
        
        return lMissingReqCellStyleCharacteristics;
    }

    /**
     * Create a cell style characteristics for contents (i.e. not header).
     * 
     * @param pHorizontalAlignment the horizontal alignment.
     * @param pVerticalAlignment the vertical alignment.
     * @param pIsTextBold <tt>true</tt> if the text shall be bold,
     * <tt>false</tt> if not.
     * 
     * @return the created style.
     */
    protected CellStyleCharacteristics getStyleCharacteristicsForHeaderCell(
            final HorizontalAlignmentType pHorizontalAlignment, final VerticalAlignmentType pVerticalAlignment,
            final boolean pIsTextBold) {
        CellStyleCharacteristics lHeaderCellStyleCharacteristics = new CellStyleCharacteristics(
                Constants.WORKBOOK_CELL_BORDER, Constants.WORKBOOK_HEADER_CELL_BACKGROUND_COLOR, false,
                pHorizontalAlignment, pVerticalAlignment, Constants.WORKBOOK_FONT_HEIGHT, pIsTextBold,
                Constants.WORKBOOK_HEADER_TEXT_COLOR);

        return lHeaderCellStyleCharacteristics;
    }

    /**
     * Create a workbook cell style from the given style characteristics.
     * 
     * @param pCellStyleCharacteristics the cell style characteristics.
     * @return the associated workbook cell.
     */
    private XSSFCellStyle getCellStyleFor(final CellStyleCharacteristics pCellStyleCharacteristics) {
        XSSFCellStyle lCellStyle = workbook.createCellStyle();

        @SuppressWarnings("deprecation") // TODO check
        XSSFColor lBgFontColor = new XSSFColor(pCellStyleCharacteristics.getCellBackgroundColor());
        lCellStyle.setFillForegroundColor(lBgFontColor);
        lCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        lCellStyle.setAlignment(pCellStyleCharacteristics.getHorizontalAlignment().getApacheAssociatedValue());
        lCellStyle.setVerticalAlignment(pCellStyleCharacteristics.getVerticalAlignment().getApacheAssociatedValue());

        lCellStyle.setBorderTop(pCellStyleCharacteristics.getCellBorder().getApacheAssociatedValue());
        lCellStyle.setBorderBottom(pCellStyleCharacteristics.getCellBorder().getApacheAssociatedValue());
        lCellStyle.setBorderRight(pCellStyleCharacteristics.getCellBorder().getApacheAssociatedValue());
        lCellStyle.setBorderLeft(pCellStyleCharacteristics.getCellBorder().getApacheAssociatedValue());

        lCellStyle.setWrapText(pCellStyleCharacteristics.isWrapText());

        @SuppressWarnings("deprecation") // TODO check
        XSSFColor lFontColor = new XSSFColor(pCellStyleCharacteristics.getFontColor());
        XSSFFont lFont = (XSSFFont) workbook.createFont();
        lFont.setFontHeightInPoints(pCellStyleCharacteristics.getFontHeightInPoints());
        lFont.setColor(lFontColor);
        lFont.setBold(pCellStyleCharacteristics.isFontBold());
        lCellStyle.setFont(lFont);

        return lCellStyle;
    }

    /**
     * Getter of the style for the given name.
     * 
     * @param styleName the style name.
     * @return the cell style corresponding to the given name, or null if not
     * defined.
     */
    public XSSFCellStyle getCellStyle(final String styleName) {
        return workbookCellStyleMap.getOrDefault(styleName, null);
    }
}