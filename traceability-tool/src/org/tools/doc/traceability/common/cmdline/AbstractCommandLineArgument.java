/**
 * 
 */
package org.tools.doc.traceability.common.cmdline;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import org.tools.doc.traceability.common.exceptions.CommandLineArgumentException;

/**
 * Common abstract class for describing the arguments of a command line tool.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractCommandLineArgument {

    /**
     * The value to return in {@link #getMaximumNumberOfParamters()} to indicate no
     * limit.
     */
    public static final int UNLIMITED_NUMBER_OF_PARAMETERS = -1;

    /**
     * The short option.
     */
    private final char shortOption;

    /**
     * The long option.
     */
    private final String longOption;

    /**
     * Whether the argument is mandatory or not.
     */
    private final boolean isMandatory;

    /**
     * Whether the argument needs some parameter(s) or not.
     */
    private final boolean needsParameters;

    /**
     * The description of the argument.
     */
    private final String description;

    /**
     * Whether the option could be found in the command line.
     */
    private boolean isPresent;

    /**
     * The list of parameters associated with this argument found in the command
     * line.
     */
    private List<String> argumentParameters;

    /**
     * Constructor.
     * 
     * @param pShortOption     the short argument value.
     * @param pLongOption      the long argument value.
     * @param pIsMandatory     whether the argument is mandatory or not.
     * @param pNeedsParameters whether the argument needs some parameter(s) or not.
     * @param pDescription     the description of the argument.
     */
    protected AbstractCommandLineArgument(final char pShortOption, final String pLongOption, final boolean pIsMandatory,
            final boolean pNeedsParameters, final String pDescription) {

        shortOption = pShortOption;
        longOption = pLongOption;
        isMandatory = pIsMandatory;
        needsParameters = pNeedsParameters;
        description = pDescription;

        isPresent = false;
        argumentParameters = new ArrayList<String>();
    }

    /**
     * Fill the argument from the given parsed command line.
     * 
     * @param pCommandLine the parsed command line.
     */
    public void fillFrom(final CommandLine pCommandLine) {
        argumentParameters.clear();

        isPresent = pCommandLine.hasOption(shortOption);
        if (isPresent) {
            String[] lArgList = pCommandLine.getOptionValues(shortOption);

            if (lArgList != null) {
                for (String lArgument : lArgList) {
                    argumentParameters.add(lArgument);
                }
            }
        }
    }

    /**
     * Checks the argument, after {@link #fillFrom(CommandLine)} has been called.
     * 
     * @param pCmdLineToolName the command line tool name.
     * @throws CommandLineArgumentException if the argument is not valid.
     */
    public void checkValidity(final String pCmdLineToolName) throws CommandLineArgumentException {
        // Check that it is present if it is mandatory
        if (isMandatory) {
            if (!isPresent) {
                throw new CommandLineArgumentException(pCmdLineToolName, "-" + shortOption, "missing argument");
            }
        }

        if (isPresent) {
            // Check that the parameters are correct if any is expected
            if (needsParameters) {
                // Ensure there is enough parameters
                int lNbParameters = argumentParameters.size();
                if (lNbParameters < getMinimumNumberOfParamters()) {
                    throw new CommandLineArgumentException(pCmdLineToolName, "-" + shortOption,
                            "not enough parameters (" + lNbParameters + " when a minimum of "
                                    + getMinimumNumberOfParamters() + " was requested)");
                }

                // Ensure there are not too much parameters
                if (getMaximumNumberOfParamters() != UNLIMITED_NUMBER_OF_PARAMETERS) {
                    if (getMaximumNumberOfParamters() < lNbParameters) {
                        throw new CommandLineArgumentException(pCmdLineToolName, "-" + shortOption,
                                "too many parameters (" + lNbParameters + " when a maximum of "
                                        + getMaximumNumberOfParamters() + " was requested)");
                    }
                }
            }
        }
    }

    /**
     * Gets the minimum number of parameters expected if the argument expects
     * arguments.
     * 
     * @return the minimum number of parameters (0 for none).
     */
    protected abstract int getMinimumNumberOfParamters();

    /**
     * Gets the maximum number of parameters expected if the argument expects
     * arguments.
     * 
     * @return the maximum number of parameters
     *         ({@link #UNLIMITED_NUMBER_OF_PARAMETERS} for no limit).
     */
    protected abstract int getMaximumNumberOfParamters();

    /**
     * Getter of the argument parameter description.
     * 
     * @return the argument parameter description
     */
    public abstract String getParameterDescription();

    /**
     * Transforms this command line argument in an {@link Option}.
     * 
     * @return the Options built from this argument.
     */
    public Option toOption() {
        Option lOption = new Option(Character.toString(shortOption), longOption, needsParameters, description);
        int lMaxNumParams = getMaximumNumberOfParamters();
        if (lMaxNumParams == UNLIMITED_NUMBER_OF_PARAMETERS) {
            lOption.setArgs(Option.UNLIMITED_VALUES);
        } else if (lMaxNumParams > 0) {
            lOption.setArgs(lMaxNumParams);
        }

        return lOption;
    }

    /**
     * Getter of the short argument value.
     * 
     * @return the short argument value.
     */
    public char getShortOption() {
        return shortOption;
    }

    /**
     * Getter of the long argument value.
     * 
     * @return the long argument value.
     */
    public String getLongOption() {
        return longOption;
    }

    /**
     * Getter of the flag indicating whether the argument is mandatory or not.
     * 
     * @return true if this argument is mandatory, false otherwise.
     */
    public boolean isMandatory() {
        return isMandatory;
    }

    /**
     * Getter of the flag indicating whether the argument needs parameters or not.
     * 
     * @return true if parameters are needed, false if no parameter is needed.
     */
    public boolean needsParameters() {
        return needsParameters;
    }

    /**
     * Getter of the argument description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter of the flag indicating whether the argument is present in the command
     * line.
     * 
     * @return true if it is present, false if it is absent.
     */
    public boolean isPresent() {
        return isPresent;
    }

    /**
     * Getter of the argument parameters provided through the command line.
     * 
     * @return the argumentParameters
     */
    public List<String> getArgumentParameters() {
        return argumentParameters;
    }

}
