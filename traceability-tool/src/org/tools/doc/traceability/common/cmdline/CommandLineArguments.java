/**
 * 
 */
package org.tools.doc.traceability.common.cmdline;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import org.tools.doc.traceability.common.exceptions.CommandLineArgumentException;

/**
 * Class describing the possible arguments of a command line tool.
 * 
 * @author Yann Leglise
 *
 */
public class CommandLineArguments {

    /**
     * The name of the command line tool.
     */
    private final String commandLineToolName;

    /**
     * The list of possible arguments.
     */
    private final List<AbstractCommandLineArgument> arguments;

    /**
     * Flag allowing to know if the instance has been defined yet.
     */
    private boolean isDefined;

    /**
     * Constructor.
     * 
     * @param pCommandLineToolName the name of the command line tool.
     */
    public CommandLineArguments(final String pCommandLineToolName) {
        super();
        commandLineToolName = pCommandLineToolName;
        arguments = new ArrayList<AbstractCommandLineArgument>();
        isDefined = false;
    }

    /**
     * Mark the instance as defined.
     */
    public void setDefined() {
        isDefined = true;
    }

    /**
     * Whether the instance has been defined or not.
     * 
     * @return true if the instance has been defined, false otherwise.
     */
    public boolean isDefined() {
        return isDefined;
    }

    /**
     * Add a possible argument in the list.
     * 
     * @param pArgument the argument to add.
     */
    public void addArgument(final AbstractCommandLineArgument pArgument) {
        arguments.add(pArgument);
    }

    /**
     * Convert the instance in one that can be handled through Apache CLI.
     * 
     * @return the {@link Options} matching this instance.
     */
    public Options toOptions() {
        Options lOptions = new Options();

        // Iterate on arguments
        for (AbstractCommandLineArgument lCmdLineArg : arguments) {
            lOptions.addOption(lCmdLineArg.toOption());
        }

        return lOptions;
    }

    /**
     * Gets the argument matching the given short option.
     * 
     * @param pShortOption the short option the searched argument shall match.
     * @return the argument matching the short option, or null if none matched.
     */
    public AbstractCommandLineArgument getArgumentForShortOption(final char pShortOption) {
        AbstractCommandLineArgument lMatchingCmdLineArg = null;

        // Search for the matching argument
        for (AbstractCommandLineArgument lCmdLineArg : arguments) {
            if (lCmdLineArg.getShortOption() == pShortOption) {
                lMatchingCmdLineArg = lCmdLineArg;
                break;
            }
        }

        return lMatchingCmdLineArg;
    }

    /**
     * Fill the arguments from the given parsed command line and check the general
     * validity.
     * <p>
     * If no exception is thrown it means that all mandatory arguments were present,
     * and the number of provided parameters was between the minimum and the maximum
     * for the arguments that requested any.
     * </p>
     * 
     * @param pCommandLine the parsed command line.
     * @throws CommandLineArgumentException if the argument is not valid.
     */
    public void fillAndCheckFrom(final CommandLine pCommandLine) throws CommandLineArgumentException {

        // Iterate on arguments
        for (AbstractCommandLineArgument lCmdLineArg : arguments) {
            // First fill the arguments from the command line
            lCmdLineArg.fillFrom(pCommandLine);
            // Then check the validity
            lCmdLineArg.checkValidity(commandLineToolName);
        }
    }

    /**
     * Get the description of the command line tool syntax with all the arguments.
     * 
     * @return the command line tool usage description.
     */
    public String getUsage() {
        StringBuilder lSb = new StringBuilder();

        lSb.append("Syntax for ");
        lSb.append(commandLineToolName);
        lSb.append(" :\n\t");

        // Process arguments in the order they were added
        // Take the opportunity of this first loop to compute the longest long option
        // length
        int lLongestLongOptionLength = 0;

        for (AbstractCommandLineArgument lCmdLineArg : arguments) {

            if (lLongestLongOptionLength < lCmdLineArg.getLongOption().length()) {
                lLongestLongOptionLength = lCmdLineArg.getLongOption().length();
            }

            if (!lCmdLineArg.isMandatory()) {
                lSb.append("[");
            }

            lSb.append("-");
            lSb.append(lCmdLineArg.getShortOption());

            if (lCmdLineArg.needsParameters()) {
                int lMinArgs = lCmdLineArg.getMinimumNumberOfParamters();
                int lMaxArgs = lCmdLineArg.getMaximumNumberOfParamters();

                lSb.append(" {followed by ");
                lSb.append(lMinArgs);

                if (lMaxArgs == lMinArgs) {
                    lSb.append(" arg");
                } else {
                    lSb.append(" to ");

                    if (lMaxArgs == AbstractCommandLineArgument.UNLIMITED_NUMBER_OF_PARAMETERS) {
                        lSb.append("MAX");
                    } else {
                        lSb.append(lMaxArgs);
                    }

                    lSb.append(" args");
                }

                lSb.append("}");

            }

            if (!lCmdLineArg.isMandatory()) {
                lSb.append("]");
            }

            lSb.append(" ");
        }

        lSb.append("\n\nDetailed arguments:");
        // Process arguments in the order they were added
        for (AbstractCommandLineArgument lCmdLineArg : arguments) {
            lSb.append("\n\t-");
            lSb.append(lCmdLineArg.getShortOption());
            lSb.append(" | --");
            lSb.append(lCmdLineArg.getLongOption());

            // Complete with spaces to reach the longest long option length
            int lMissingSpaces = lLongestLongOptionLength - lCmdLineArg.getLongOption().length();
            for (int lMsi = 0; lMsi < lMissingSpaces; lMsi++) {
                lSb.append(' ');
            }

            lSb.append(" ( ");
            if (lCmdLineArg.isMandatory()) {
                lSb.append("Mandatory");
            } else {
                lSb.append(" Optional");
            }
            lSb.append(" argument) : ");
            lSb.append(lCmdLineArg.getDescription());
            if (lCmdLineArg.needsParameters()) {
                lSb.append(" (followed by ");
                lSb.append(lCmdLineArg.getParameterDescription());
                lSb.append(")");
            }
        }

        return lSb.toString();
    }
}
