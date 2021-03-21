/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.sub;

import org.tools.doc.traceability.manager.gui.data.tools.config.FileSelectionFilter;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;
import org.tools.doc.traceability.manager.gui.panels.tools.CoverageMatrixGeneratorToolConfigurationPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.common.AbstractCoveringTestWithFileSelectionFilterPanel;

/**
 * Sub panel for configuring cucumber tests covering.
 * 
 * @author Yann Leglise
 *
 */
public class CucumberTestsCoveringConfigSubPanel extends AbstractCoveringTestWithFileSelectionFilterPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 7031399530034025383L;

    /**
     * Constructor.
     * 
     * @param pParentPanel the parent panel.
     */
    public CucumberTestsCoveringConfigSubPanel(final CoverageMatrixGeneratorToolConfigurationPanel pParentPanel) {
        super(pParentPanel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void completeBuildPannel(final int pNextRow) {
        // Nothing more to add
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileSelectionFilter getFileSelectionFilterFrom(final TraceabilityManagerToolConfiguration pTmgtConfig) {
        return pTmgtConfig.getCucumberTestsCoverageConfiguration().getFileSelectionFilter();
    }

}
