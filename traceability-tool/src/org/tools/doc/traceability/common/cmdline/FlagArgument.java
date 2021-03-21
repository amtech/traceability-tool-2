/**
 * 
 */
package org.tools.doc.traceability.common.cmdline;

/**
 * Models an argument that do not need any parameter, and whose presence set the
 * associated flag to true, and whose absence sets the associated flag to false.
 * 
 * @author Yann Leglise
 *
 */
public class FlagArgument extends AbstractCommandLineArgument {

    /**
     * Constructor.
     * 
     * @param pShortValue  the short argument value.
     * @param pLongValue   the long argument value.
     * @param pDescription the description of the argument.
     */
    public FlagArgument(final char pShortValue, final String pLongValue, final String pDescription) {
        super(pShortValue, pLongValue, false, false, pDescription);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getMinimumNumberOfParamters() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getMaximumNumberOfParamters() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getParameterDescription() {
        return null;
    }
}
