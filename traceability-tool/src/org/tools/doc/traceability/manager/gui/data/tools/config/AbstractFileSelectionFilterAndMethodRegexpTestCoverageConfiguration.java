/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.tools.config;

import org.tools.doc.traceability.common.exceptions.InvalidConfigurationException;
import org.tools.doc.traceability.common.exceptions.InvalidSimpleRegexpException;
import org.tools.doc.traceability.common.sregex.SimpleRegex;
import org.tools.doc.traceability.manager.gui.panels.control.ControlPanel;

/**
 * Models a tests coverage configuration that has a file selection filter and a
 * simple regexp for method names.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractFileSelectionFilterAndMethodRegexpTestCoverageConfiguration extends
        AbstractFileSelectionFilterTestCoverageConfiguration {

    /**
     * The simple regexp for method names.
     */
    private SimpleRegex methodNameSimpleRegexp;

    /**
     * Constructor.
     * 
     * @param pConfigurationContext the context description.
     * 
     */
    public AbstractFileSelectionFilterAndMethodRegexpTestCoverageConfiguration(final String pConfigurationContext) {
        super(pConfigurationContext);
        try {
            methodNameSimpleRegexp = new SimpleRegex("*");
        } catch (InvalidSimpleRegexpException e) {
            // Do nothing as we know this is correct
        }
    }

    /**
     * Configure the method name simple regexp from the given simple regexp
     * text.
     * 
     * @param pSimpleRegexp the simple regexp text.
     * @throws InvalidSimpleRegexpException if the regexp was invalid
     */
    public void configureMethodNameRegexpFrom(final String pSimpleRegexp) throws InvalidSimpleRegexpException {
        methodNameSimpleRegexp = new SimpleRegex(pSimpleRegexp);
    }

    /**
     * Setter of the methodNameSimpleRegexp.
     * 
     * @param pMethodNameSimpleRegexp the methodNameSimpleRegexp to set
     */
    public void setMethodNameSimpleRegexp(final SimpleRegex pMethodNameSimpleRegexp) {
        methodNameSimpleRegexp = pMethodNameSimpleRegexp;
    }

    /**
     * Getter of the method name simple regexp.
     * 
     * @return the methodNameSimpleRegexp
     */
    public SimpleRegex getMethodNameSimpleRegexp() {
        return methodNameSimpleRegexp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        super.reset();
        methodNameSimpleRegexp = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkIsValid() throws InvalidConfigurationException {
        super.checkIsValid();

        if (isActive()) {
            if (methodNameSimpleRegexp == null) {
                throw new InvalidConfigurationException("Define the method name simple regexp");
            } else {
                try {
                    ControlPanel.checkSimpleRegexIsValid(methodNameSimpleRegexp);
                } catch (InvalidConfigurationException ice) {
                    throw new InvalidConfigurationException(ice.getMessage() + " for the method name simple regexp of "
                            + getConfigurationContext());
                }

            }
        }
    }
}
