/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.sub;

import org.tools.doc.traceability.manager.gui.data.tools.config.AbstractFileSelectionFilterAndMethodRegexpTestCoverageConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.config.FileSelectionFilter;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;
import org.tools.doc.traceability.manager.gui.panels.tools.CoverageMatrixGeneratorToolConfigurationPanel;
import org.tools.doc.traceability.manager.gui.panels.tools.common.AbstractCoveringTestWithFileSelectionFilterAndMethodRegexPanel;

/**
 * Sub panel for configuring C# unit tests covering.
 * 
 * @author Yann Leglise
 *
 */
public class JavaUnitTestsCoveringSubPanel extends AbstractCoveringTestWithFileSelectionFilterAndMethodRegexPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 2670079667675866773L;

    /**
     * Constructor.
     * 
     * @param pParentPanel the parent panel.
     */
    public JavaUnitTestsCoveringSubPanel(final CoverageMatrixGeneratorToolConfigurationPanel pParentPanel) {
        super(pParentPanel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileSelectionFilter getFileSelectionFilterFrom(final TraceabilityManagerToolConfiguration pTmgtConfig) {
        return pTmgtConfig.getJavaUnitTestsCoverageConfiguration().getFileSelectionFilter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractFileSelectionFilterAndMethodRegexpTestCoverageConfiguration getMethodNameSimpleRegexpHolderFrom(
            final TraceabilityManagerToolConfiguration pTmgtConfig) {
        return pTmgtConfig.getJavaUnitTestsCoverageConfiguration();
    }

}
