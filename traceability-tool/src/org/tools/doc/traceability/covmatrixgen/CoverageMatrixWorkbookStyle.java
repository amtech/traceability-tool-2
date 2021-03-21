/**
 * 
 */
package org.tools.doc.traceability.covmatrixgen;

import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.tools.doc.traceability.common.worksheet.AbstractWorkbookStyle;
import org.tools.doc.traceability.common.worksheet.CellStyleCharacteristics;
import org.tools.doc.traceability.common.worksheet.HorizontalAlignmentType;
import org.tools.doc.traceability.common.worksheet.VerticalAlignmentType;

/**
 * @author Yann Leglise
 *
 */
public class CoverageMatrixWorkbookStyle extends AbstractWorkbookStyle {
    /**
     * The name of the style for requirements.
     */
    private static final String REQUIREMENT_STYLE_NAME = "requirement";

    /**
     * The name of the style for coverage.
     */
    private static final String COVERAGE_STYLE_NAME = "coverage";

    /**
     * The name of the style for missing coverage.
     */
    private static final String MISSING_COVERAGE_STYLE_NAME = "missingCoverage";

    /**
     * The name of the style for headers.
     */
    private static final String HEADER_STYLE_NAME = "header";

    /**
     * Constructor.
     * 
     * @param pWorkbook the destination workbook.
     */
    public CoverageMatrixWorkbookStyle(final XSSFWorkbook pWorkbook) {
        super(pWorkbook);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillStyleCharacteristics(final Map<String, CellStyleCharacteristics> pStyleNameCharacteristicsMap) {
        CellStyleCharacteristics lRequirementCellStyleCharacteristics = getStyleCharacteristicsForValueCell(
                HorizontalAlignmentType.LEFT, VerticalAlignmentType.TOP, false);
        pStyleNameCharacteristicsMap.put(REQUIREMENT_STYLE_NAME, lRequirementCellStyleCharacteristics);

        CellStyleCharacteristics lCoverageCellStyleCharacteristics = getStyleCharacteristicsForValueCell(
                HorizontalAlignmentType.LEFT, VerticalAlignmentType.TOP, false);
        pStyleNameCharacteristicsMap.put(COVERAGE_STYLE_NAME, lCoverageCellStyleCharacteristics);

        CellStyleCharacteristics lMissingCoverageCellStyleCharacteristics = getStyleCharacteristicsForMissingReqCell(
                HorizontalAlignmentType.LEFT, VerticalAlignmentType.TOP, true);
        pStyleNameCharacteristicsMap.put(MISSING_COVERAGE_STYLE_NAME, lMissingCoverageCellStyleCharacteristics);

        CellStyleCharacteristics lHeaderCellStyleCharacteristics = getStyleCharacteristicsForHeaderCell(
                HorizontalAlignmentType.LEFT, VerticalAlignmentType.TOP, true);
        pStyleNameCharacteristicsMap.put(HEADER_STYLE_NAME, lHeaderCellStyleCharacteristics);
    }

    /**
     * Gets the style for requirement cells.
     * 
     * @return the requirement cell style.
     */
    public XSSFCellStyle getRequirementStyle() {
        return getCellStyle(REQUIREMENT_STYLE_NAME);
    }

    /**
     * Gets the style for coverage cells.
     * 
     * @return the coverage cell style.
     */
    public XSSFCellStyle getCoverageStyle() {
        return getCellStyle(COVERAGE_STYLE_NAME);
    }

    /**
     * Gets the style for missing coverage cells.
     * 
     * @return the missing coverage cell style.
     */
    public XSSFCellStyle getMissingCoverageStyle() {
        return getCellStyle(MISSING_COVERAGE_STYLE_NAME);
    }

    /**
     * Gets the style for header cells.
     * 
     * @return the heder cell style.
     */
    public XSSFCellStyle getHeaderStyle() {
        return getCellStyle(HEADER_STYLE_NAME);
    }

}
