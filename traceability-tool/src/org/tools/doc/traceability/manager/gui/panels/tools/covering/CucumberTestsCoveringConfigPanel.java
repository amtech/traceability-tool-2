/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.covering;

import org.tools.doc.traceability.manager.gui.data.tools.config.AbstractTestCoverageConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;
import org.tools.doc.traceability.manager.gui.panels.tools.CoverageMatrixGeneratorToolConfigurationPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.common.AbstractActivableCoveringTestPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.common.AbstractTraceabilityManagerToolConfigurationUpdatablePanel;
import org.tools.doc.traceability.manager.gui.panels.tools.sub.CucumberTestsCoveringConfigSubPanel;

/**
 * Panel for configuring cucumber tests covering.
 * 
 * @author Yann Leglise
 *
 */
public class CucumberTestsCoveringConfigPanel extends AbstractActivableCoveringTestPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 6545208255000005859L;

    /**
     * Constructor.
     * 
     * @param pParentPanel the parent panel.
     */
    public CucumberTestsCoveringConfigPanel(final CoverageMatrixGeneratorToolConfigurationPanel pParentPanel) {
        super(pParentPanel, "Cucumber tests");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractTraceabilityManagerToolConfigurationUpdatablePanel createContentsPanel() {
        return new CucumberTestsCoveringConfigSubPanel(getParentPanel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isActivatedInConfiguration(final TraceabilityManagerToolConfiguration pTmgtConfig) {
        return pTmgtConfig.getCucumberTestsCoverageConfiguration().isActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractTestCoverageConfiguration getTestCoverageConfigurationFrom(
            final TraceabilityManagerToolConfiguration pTmgtConfig) {
        return pTmgtConfig.getCucumberTestsCoverageConfiguration();
    }
}
