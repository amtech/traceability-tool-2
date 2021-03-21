/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.covering;

import org.tools.doc.traceability.manager.gui.data.tools.config.AbstractTestCoverageConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;
import org.tools.doc.traceability.manager.gui.panels.tools.CoverageMatrixGeneratorToolConfigurationPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.common.AbstractActivableCoveringTestPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.common.AbstractTraceabilityManagerToolConfigurationUpdatablePanel;
import org.tools.doc.traceability.manager.gui.panels.tools.sub.AlmTestsCoveringConfigSubPanel;

/**
 * Panel for configuring ALM tests covering.
 * 
 * @author Yann Leglise
 *
 */
public class AlmTestsCoveringConfigPanel extends AbstractActivableCoveringTestPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -491608527355311807L;

    /**
     * Constructor.
     * 
     * @param pParentPanel the parent panel.
     */
    public AlmTestsCoveringConfigPanel(final CoverageMatrixGeneratorToolConfigurationPanel pParentPanel) {
        super(pParentPanel, "ALM tests");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractTraceabilityManagerToolConfigurationUpdatablePanel createContentsPanel() {
        return new AlmTestsCoveringConfigSubPanel(getParentPanel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isActivatedInConfiguration(final TraceabilityManagerToolConfiguration pTmgtConfig) {
        return pTmgtConfig.getAlmTestsCoverageConfiguration().isActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractTestCoverageConfiguration getTestCoverageConfigurationFrom(
            final TraceabilityManagerToolConfiguration pTmgtConfig) {
        return pTmgtConfig.getAlmTestsCoverageConfiguration();
    }
}
