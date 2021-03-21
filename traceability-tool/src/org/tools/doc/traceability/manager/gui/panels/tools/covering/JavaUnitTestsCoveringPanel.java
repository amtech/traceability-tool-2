/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.covering;

import org.tools.doc.traceability.manager.gui.data.tools.config.AbstractTestCoverageConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;
import org.tools.doc.traceability.manager.gui.panels.tools.CoverageMatrixGeneratorToolConfigurationPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.common.AbstractActivableCoveringTestPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.common.AbstractTraceabilityManagerToolConfigurationUpdatablePanel;
import org.tools.doc.traceability.manager.gui.panels.tools.sub.JavaUnitTestsCoveringSubPanel;

/**
 * Panel for configuring C# unit tests covering.
 * 
 * @author Yann Leglise
 *
 */
public class JavaUnitTestsCoveringPanel extends AbstractActivableCoveringTestPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -3488223589234112620L;

    /**
     * Constructor.
     * 
     * @param pParentPanel the parent panel.
     */
    public JavaUnitTestsCoveringPanel(final CoverageMatrixGeneratorToolConfigurationPanel pParentPanel) {
        super(pParentPanel, "Java unit tests");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractTraceabilityManagerToolConfigurationUpdatablePanel createContentsPanel() {
        return new JavaUnitTestsCoveringSubPanel(getParentPanel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isActivatedInConfiguration(final TraceabilityManagerToolConfiguration pTmgtConfig) {
        return pTmgtConfig.getJavaUnitTestsCoverageConfiguration().isActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractTestCoverageConfiguration getTestCoverageConfigurationFrom(
            final TraceabilityManagerToolConfiguration pTmgtConfig) {
        return pTmgtConfig.getJavaUnitTestsCoverageConfiguration();
    }
}
