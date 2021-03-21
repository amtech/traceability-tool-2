/**
 * 
 */
package org.tools.doc.traceability.common.ui;

/**
 * A panel with a titled border, containing only one inner panel.
 * 
 * @author Yann Leglise
 *
 */
public class TitleBorderedPanel extends AbstractPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 247852072486799988L;

    /**
     * The inner panel.
     */
    private final AbstractPanel innerPanel;

    /**
     * The text for the border title.
     */
    private final String titleText;

    /**
     * Constructor.
     * 
     * @param pTitleText  the text for the border title.
     * @param pInnerPanel the inner panel (the build method will be automatically
     *                    called if not null).
     */
    public TitleBorderedPanel(final String pTitleText, final AbstractPanel pInnerPanel) {
        super();
        innerPanel = pInnerPanel;
        titleText = pTitleText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doBuildPanel() {
        setBorder(createTitleBorder(titleText));
        if (innerPanel != null) {
            innerPanel.build();
            addComponent(innerPanel, 0, 0, 1, 1, 100, 100, ANCHOR_CENTER, FILL_BOTH, 1, 1, 1, 1);
        }
    }

}
