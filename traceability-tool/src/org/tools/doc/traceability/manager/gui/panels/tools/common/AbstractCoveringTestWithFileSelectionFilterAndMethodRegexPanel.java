/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.common;

import javax.swing.JLabel;

import org.tools.doc.traceability.common.exceptions.InvalidSimpleRegexpException;
import org.tools.doc.traceability.common.sregex.SimpleRegex;
import org.tools.doc.traceability.manager.gui.components.inputfield.InputField;
import org.tools.doc.traceability.manager.gui.data.tools.config.AbstractFileSelectionFilterAndMethodRegexpTestCoverageConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;
import org.tools.doc.traceability.manager.gui.panels.tools.CoverageMatrixGeneratorToolConfigurationPanel;

/**
 * A class for a covering tests having a file selection filter part and an input
 * field for the method regexp.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractCoveringTestWithFileSelectionFilterAndMethodRegexPanel extends
        AbstractCoveringTestWithFileSelectionFilterPanel implements IMethodNameSimpleRegexpHolderSelector {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 4550969609871176895L;

    /**
     * The text field for the method simple regexp field.
     */
    private InputField methodNameSimpleRegexpIf;

    /**
     * Constructor.
     * 
     * @param pParentPanel the parent panel.
     */
    public AbstractCoveringTestWithFileSelectionFilterAndMethodRegexPanel(
            final CoverageMatrixGeneratorToolConfigurationPanel pParentPanel) {
        super(pParentPanel);
        methodNameSimpleRegexpIf = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void completeBuildPannel(final int pNextRow) {

        JLabel lMethodNameRegexpLabel = new JLabel("Method name simple regexp");
        addComponent(lMethodNameRegexpLabel, 0, pNextRow, 1, 1, LABEL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT, FILL_NONE, 1,
                1, 1, 1);

        methodNameSimpleRegexpIf = new InputField(50, getParentPanel());
        methodNameSimpleRegexpIf.setToolTipText("<html>A regular expression to match unit test methods. <ul>"
                + "<li><tt><b>?</b></tt> is replaced by a character."
                + "<li><tt><b>*</b></tt> represents zero or more characters.</ul></html>");
        addComponent(methodNameSimpleRegexpIf, 1, pNextRow, 1, 1, LABEL_WIDTH_PERCENTAGE, 0, ANCHOR_LEFT,
                FILL_HORIZONTAL, 1, 1, 1, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateToolConfigFromChangedInputField(final TraceabilityManagerToolConfiguration pTmgtConfig,
            final int pInputFieldIdentifier) {
        super.updateToolConfigFromChangedInputField(pTmgtConfig, pInputFieldIdentifier);

        if (methodNameSimpleRegexpIf.getIdentifier() == pInputFieldIdentifier) {

            AbstractFileSelectionFilterAndMethodRegexpTestCoverageConfiguration lMethodNameSimpleRegexpHolder = getMethodNameSimpleRegexpHolderFrom(pTmgtConfig);
            SimpleRegex lMethodNameSimpleRegexp = null;

            try {
                lMethodNameSimpleRegexp = new SimpleRegex(methodNameSimpleRegexpIf.getText());
            } catch (InvalidSimpleRegexpException e) {
                lMethodNameSimpleRegexp = null;
            }
            lMethodNameSimpleRegexpHolder.setMethodNameSimpleRegexp(lMethodNameSimpleRegexp);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFromToolConfiguration(final TraceabilityManagerToolConfiguration pTmgtConfig) {
        super.updateFromToolConfiguration(pTmgtConfig);

        AbstractFileSelectionFilterAndMethodRegexpTestCoverageConfiguration lMethodNameSimpleRegexpHolder = getMethodNameSimpleRegexpHolderFrom(pTmgtConfig);
        SimpleRegex lMethodNameSimpleRegexp = lMethodNameSimpleRegexpHolder.getMethodNameSimpleRegexp();

        if (lMethodNameSimpleRegexp == null) {
            methodNameSimpleRegexpIf.setText("");
        } else {
            methodNameSimpleRegexpIf.setText(lMethodNameSimpleRegexp.getTextualRepresentation());
        }
    }
}
