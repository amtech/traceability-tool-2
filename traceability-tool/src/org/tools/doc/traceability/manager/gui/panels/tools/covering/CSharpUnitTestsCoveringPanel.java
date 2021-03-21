/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.covering;

import org.tools.doc.traceability.manager.gui.data.tools.config.AbstractTestCoverageConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;
import org.tools.doc.traceability.manager.gui.panels.tools.CoverageMatrixGeneratorToolConfigurationPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.common.AbstractActivableCoveringTestPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.common.AbstractTraceabilityManagerToolConfigurationUpdatablePanel;
import org.tools.doc.traceability.manager.gui.panels.tools.sub.CSharpUnitTestsCoveringSubPanel;

/**
 * Panel for configuring C# unit tests covering.
 * 
 * @author Yann Leglise
 *
 */
public class CSharpUnitTestsCoveringPanel extends AbstractActivableCoveringTestPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 2635101604343310802L;

    /**
     * Constructor.
     * 
     * @param pParentPanel parent panel.
     */
    public CSharpUnitTestsCoveringPanel(final CoverageMatrixGeneratorToolConfigurationPanel pParentPanel) {
        super(pParentPanel, "C# unit tests");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractTraceabilityManagerToolConfigurationUpdatablePanel createContentsPanel() {
        return new CSharpUnitTestsCoveringSubPanel(getParentPanel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isActivatedInConfiguration(final TraceabilityManagerToolConfiguration pTmgtConfig) {
        return pTmgtConfig.getcSharpUnitTestsCoverageConfiguration().isActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractTestCoverageConfiguration getTestCoverageConfigurationFrom(
            final TraceabilityManagerToolConfiguration pTmgtConfig) {
        return pTmgtConfig.getcSharpUnitTestsCoverageConfiguration();
    }
}
