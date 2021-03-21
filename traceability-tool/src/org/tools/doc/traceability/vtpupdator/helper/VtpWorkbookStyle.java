/**
 * 
 */
package org.tools.doc.traceability.vtpupdator.helper;

import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.tools.doc.traceability.common.worksheet.AbstractWorkbookStyle;
import org.tools.doc.traceability.common.worksheet.CellStyleCharacteristics;
import org.tools.doc.traceability.common.worksheet.HorizontalAlignmentType;
import org.tools.doc.traceability.common.worksheet.VerticalAlignmentType;

/**
 * The workbook style for the VTP.
 * 
 * @author Yann Leglise
 *
 */
public class VtpWorkbookStyle extends AbstractWorkbookStyle {

    /**
     * The name of the normal style.
     */
    private static final String NORMAL_STYLE_NAME = "normal";

    /**
     * The name of the description style.
     */
    private static final String DESCRIPTION_STYLE_NAME = "description";

    /**
     * The name of the style for headers.
     */
    private static final String HEADER_STYLE_NAME = "header";

    /**
     * Constructor.
     * 
     * @param pWorkbook the target workbook.
     */
    public VtpWorkbookStyle(final XSSFWorkbook pWorkbook) {
        super(pWorkbook);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillStyleCharacteristics(final Map<String, CellStyleCharacteristics> pStyleNameCharacteristicsMap) {

        CellStyleCharacteristics lNormalCellStyleCharacteristics = getStyleCharacteristicsForValueCell(
                HorizontalAlignmentType.LEFT, VerticalAlignmentType.TOP, false);
        pStyleNameCharacteristicsMap.put(NORMAL_STYLE_NAME, lNormalCellStyleCharacteristics);

        CellStyleCharacteristics lDescriptionCellStyleCharacteristics = getStyleCharacteristicsForValueCell(
                HorizontalAlignmentType.LEFT, VerticalAlignmentType.TOP, false);
        pStyleNameCharacteristicsMap.put(DESCRIPTION_STYLE_NAME, lDescriptionCellStyleCharacteristics);

        CellStyleCharacteristics lHeaderCellStyleCharacteristics = getStyleCharacteristicsForHeaderCell(
                HorizontalAlignmentType.LEFT, VerticalAlignmentType.TOP, true);
        pStyleNameCharacteristicsMap.put(HEADER_STYLE_NAME, lHeaderCellStyleCharacteristics);
    }

    /**
     * Gets the style for normal cells.
     * 
     * @return the normal cell style.
     */
    public XSSFCellStyle getNormalStyle() {
        return getCellStyle(NORMAL_STYLE_NAME);
    }

    /**
     * Gets the style for description cells.
     * 
     * @return the description cell style.
     */
    public XSSFCellStyle getDescriptionStyle() {
        return getCellStyle(DESCRIPTION_STYLE_NAME);
    }

    /**
     * Gets the style for header cells.
     * 
     * @return the header cell style.
     */
    public XSSFCellStyle getHeaderStyle() {
        return getCellStyle(HEADER_STYLE_NAME);
    }

}
