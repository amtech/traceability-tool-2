/**
 * 
 */
package org.tools.doc.traceability.common.cmdline;

/**
 * Models a command line tool argument that takes parameters.
 * 
 * @author Yann Leglise
 *
 */
public class ParameteredArgument extends AbstractCommandLineArgument {

    /**
     * Describes the expected parameter value.
     */
    private final String parameterDescription;

    /**
     * The minimum number of expected parameters (0 for none).
     */
    private final int minimumExpectedParameters;

    /**
     * The maximum number of expected parameters
     * ({@link #UNLIMITED_NUMBER_OF_PARAMETERS} for no limit).
     */
    private final int maximumExpectedParameters;

    /**
     * Constructor.
     * 
     * @param pShortValue                the short argument value.
     * @param pLongValue                 the long argument value.
     * @param pIsMandatory               whether the argument is mandatory or not.
     * @param pArgumentDescription       the description of the argument.
     * @param pMinimumExpectedParameters the minimum number of expected parameters
     *                                   (0 for none).
     * @param pMaximumExpectedParameters the maximum number of expected parameters
     *                                   ({@link #UNLIMITED_NUMBER_OF_PARAMETERS}
     *                                   for no limit).
     * @param pParameterDescription      the description of the parameter value.
     */
    public ParameteredArgument(final char pShortValue, final String pLongValue, final boolean pIsMandatory,
            final String pArgumentDescription, final int pMinimumExpectedParameters,
            final int pMaximumExpectedParameters, final String pParameterDescription) {
        super(pShortValue, pLongValue, pIsMandatory, true, pArgumentDescription);
        minimumExpectedParameters = pMinimumExpectedParameters;
        maximumExpectedParameters = pMaximumExpectedParameters;
        parameterDescription = pParameterDescription;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getParameterDescription() {
        return parameterDescription;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getMinimumNumberOfParamters() {
        return minimumExpectedParameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getMaximumNumberOfParamters() {
        return maximumExpectedParameters;
    }

}
