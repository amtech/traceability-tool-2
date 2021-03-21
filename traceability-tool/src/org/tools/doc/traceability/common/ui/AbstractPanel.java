package org.tools.doc.traceability.common.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.tools.doc.traceability.manager.gui.GuiConstants;

/**
 * Common class for panels.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractPanel extends JPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 321544459324851080L;

    /**
     * Constraint for center.
     */
    protected static final int ANCHOR_CENTER = GridBagConstraints.CENTER;

    /**
     * Constraint for top.
     */
    protected static final int ANCHOR_TOP = GridBagConstraints.NORTH;

    /**
     * Constraint for top right.
     */
    protected static final int ANCHOR_TOPRIGHT = GridBagConstraints.NORTHEAST;

    /**
     * Constraint for right.
     */
    protected static final int ANCHOR_RIGHT = GridBagConstraints.EAST;

    /**
     * Constraint for bottom right.
     */
    protected static final int ANCHOR_BOTTOMRIGHT = GridBagConstraints.SOUTHEAST;

    /**
     * Constraint for bottom.
     */
    protected static final int ANCHOR_BOTTOM = GridBagConstraints.SOUTH;

    /**
     * Constraint for bottom left.
     */
    protected static final int ANCHOR_BOTTOMLEFT = GridBagConstraints.SOUTHWEST;

    /**
     * Constraint for left.
     */
    protected static final int ANCHOR_LEFT = GridBagConstraints.WEST;

    /**
     * Constraint for top left.
     */
    protected static final int ANCHOR_TOPLEFT = GridBagConstraints.NORTHWEST;

    /**
     * No constraint.
     */
    protected static final int FILL_NONE = GridBagConstraints.NONE;

    /**
     * Constraint for horizontal filling.
     */
    protected static final int FILL_HORIZONTAL = GridBagConstraints.HORIZONTAL;

    /**
     * Constraint for vertical filling.
     */
    protected static final int FILL_VERTICAL = GridBagConstraints.VERTICAL;

    /**
     * Constraint for horizontal and vertical filling.
     */
    protected static final int FILL_BOTH = GridBagConstraints.BOTH;

    /**
     * The percentage occupied by labels.
     */
    protected static final int LABEL_WIDTH_PERCENTAGE = 0;

    /**
     * The percentage occupied by controls.
     */
    protected static final int CONTROL_WIDTH_PERCENTAGE = 100 - LABEL_WIDTH_PERCENTAGE;

    /**
     * The horizontal space between the panel border and inner components.
     */
    protected static final int INNER_HORIZONTAL_PADDING = 5;

    /**
     * Flag indicating whether the panel is built or not.
     */
    private boolean isBuilt;

    /**
     * Constructor.
     */
    public AbstractPanel() {
        isBuilt = false;
        setLayout(new GridBagLayout());
    }

    /**
     * Build the panel.
     */
    public final void build() {
        if (!isBuilt) {
            doBuildPanel();
            isBuilt = true;
        }
    }

    /**
     * Actually build the panel.
     */
    protected abstract void doBuildPanel();

    /**
     * Add a component to the panel.
     * 
     * @param pComponent    the component to add.
     * @param pGridX        Specifies the cell containing the leading edge of the
     *                      component's display area, where the first cell in a row
     *                      has value <code>0</code>.
     * @param pGridY        Specifies the cell at the top of the component's display
     *                      area, where the topmost cell has value <code>0</code>.
     * @param pGridWidth    Specifies the number of cells in a row for the
     *                      component's display area.
     * @param pGridHeight   Specifies the number of cells in a column for the
     *                      component's display area.
     * @param pWeightX      Specifies how to distribute extra horizontal space
     *                      (between 0 and 100 %).
     * @param pWeightY      Specifies how to distribute extra vertical space
     *                      (between 0 and 100 %).
     * @param pAnchor       Where to stick the component when it is smaller that the
     *                      available space, among
     *                      <ul>
     *                      <li><code>ANCHOR_CENTER</code>,
     *                      <li><code>ANCHOR_TOP</code>,
     *                      <li><code>ANCHOR_TOPRIGHT</code>,
     *                      <li><code>ANCHOR_RIGHT</code>,
     *                      <li><code>ANCHOR_BOTTOMRIGHT</code>,
     *                      <li><code>ANCHOR_BOTTOM</code>,
     *                      <li><code>ANCHOR_BOTTOMLEFT</code>,
     *                      <li><code>ANCHOR_LEFT</code>,
     *                      <li><code>ANCHOR_TOPLEFT</code>.
     *                      </ul>
     * @param pFill         Whether and how to resize the component when its display
     *                      area is larger than the component's requested size,
     *                      among:
     *                      <ul>
     *                      <li><code>FILL_NONE</code>: Do not resize the component.
     *                      <li><code>FILL_HORIZONTAL</code>: Make the component
     *                      wide enough to fill its display area horizontally, but
     *                      do not change its height.
     *                      <li><code>FILL_VERTICAL</code>: Make the component tall
     *                      enough to fill its display area vertically, but do not
     *                      change its width.
     *                      <li><code>FILL_BOTH</code>: Make the component fill its
     *                      display area entirely.
     *                      </ul>
     * @param pLeftInsets   the value for the left insets.
     * @param pRightInsets  the value for the right insets.
     * @param pTopInsets    the value for the top insets.
     * @param pBottomInsets the value for the bottom insets.
     */
    protected void addComponent(final JComponent pComponent, final int pGridX, final int pGridY, final int pGridWidth,
            final int pGridHeight, final int pWeightX, final int pWeightY, final int pAnchor, final int pFill,
            final int pLeftInsets, final int pRightInsets, final int pTopInsets, final int pBottomInsets) {
        Insets lInsets = new Insets(pTopInsets, pLeftInsets, pBottomInsets, pRightInsets);

        GridBagConstraints lGbc = new GridBagConstraints(pGridX, pGridY, pGridWidth, pGridHeight, pWeightX, pWeightY,
                pAnchor, pFill, lInsets, 0, 0);

        add(pComponent, lGbc);
    }

    /**
     * Create a panel border.
     * 
     * @return the created panel border.
     */
    protected Border createPanelBorder() {
        Border lBorder = BorderFactory.createMatteBorder(GuiConstants.TITLE_BORDER_LINE_WIDTH,
                GuiConstants.TITLE_BORDER_LINE_WIDTH, GuiConstants.TITLE_BORDER_LINE_WIDTH, GuiConstants.TITLE_BORDER_LINE_WIDTH,
                GuiConstants.TITLE_BORDER_COLOR);
        return lBorder;
    }

    /**
     * Create a title border.
     * 
     * @param pTitle the title label.
     * @return the created title border.
     */
    protected Border createTitleBorder(final String pTitle) {
        Border lTitleBorder = BorderFactory.createTitledBorder(createPanelBorder(), " " + pTitle + " ",
                TitledBorder.LEFT, TitledBorder.TOP, GuiConstants.TITLE_BORDER_FONT, GuiConstants.TITLE_BORDER_COLOR);

        return lTitleBorder;
    }
}
