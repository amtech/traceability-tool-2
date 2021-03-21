/**
 * 
 */
package org.tools.doc.traceability.common.sregex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.tools.doc.traceability.common.exceptions.InvalidSimpleRegexpException;

/**
 * Models a simplified regular expression.
 * <p>
 * Only the following special character are considered:
 * <ul>
 * <li><tt><b>?</b></tt> that stands for exactly one character (any character),
 * <li><tt><b>*</b></tt> that stands for 0 or more characters.
 * </ul>
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class SimpleRegex {

    /**
     * The symbol representing exactly one character.
     */
    private static final String ONE_CHAR_SYMBOL = "?";

    /**
     * The symbol representing zero or more characters.
     */
    private static final String ZERO_OR_MORE_CHAR_SYMBOL = "*";

    /**
     * The simple regular expression value.
     */
    private final String simpleRegexValue;

    /**
     * The pattern corresponding to the simple regular expression.
     */
    private final Pattern simpleRegexPattern;

    /**
     * Constructor.
     * 
     * @param pSimpleRegexValue the simple regular expression value, with
     * {@link #ONE_CHAR_SYMBOL} representing one character, and
     * {@link #ZERO_OR_MORE_CHAR_SYMBOL} representing zero or more characters.
     * @throws InvalidSimpleRegexpException if the simple regexp was invalid
     */
    public SimpleRegex(final String pSimpleRegexValue) throws InvalidSimpleRegexpException {
        simpleRegexValue = pSimpleRegexValue;

        if (pSimpleRegexValue == null) {
            throw new InvalidSimpleRegexpException(simpleRegexValue, "null value");
        }

        // Compose the java regexp
        String lActualJavaRegexp = pSimpleRegexValue;
        // Protect the potential special characters
        lActualJavaRegexp = lActualJavaRegexp.replace(".", "\\.");
        lActualJavaRegexp = lActualJavaRegexp.replace("(", "\\(");

        // Replace the special characters by their java regexp equivalent
        lActualJavaRegexp = lActualJavaRegexp.replace(ONE_CHAR_SYMBOL, ".");
        lActualJavaRegexp = lActualJavaRegexp.replace(ZERO_OR_MORE_CHAR_SYMBOL, ".*");

        // Create the associated pattern.
        Pattern lPattern = null;
        try {
            lPattern = Pattern.compile(lActualJavaRegexp);
        } catch (PatternSyntaxException pse) {
            throw new InvalidSimpleRegexpException(simpleRegexValue, "compiling \"" + lActualJavaRegexp
                    + "\" as a pattern failed : " + pse.getDescription());
        } finally {
            simpleRegexPattern = lPattern;
        }
    }

    /**
     * Check if the given value matches the simple regexp.
     * 
     * @param pValue the value to test.
     * @return <tt>true</tt> if the value matches, <tt>false</tt> if not.
     */
    public boolean matches(final String pValue) {
        boolean lValueMatches = false;

        Matcher lMatcher = simpleRegexPattern.matcher(pValue);

        lValueMatches = lMatcher.matches();

        return lValueMatches;
    }

    /**
     * Getter of the textual representation of the simple regexp.
     * 
     * @return the simple regexp textual representation.
     */
    public String getTextualRepresentation() {
        return simpleRegexValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder lSb = new StringBuilder();
        lSb.append("[");
        lSb.append(simpleRegexValue);
        lSb.append("]");

        return lSb.toString();
    }

    /**
     * Getter of the simpleRegexValue.
     * 
     * @return the simpleRegexValue
     */
    public String getSimpleRegexValue() {
        return simpleRegexValue;
    }

    /**
     * Getter of the simpleRegexPattern.
     * 
     * @return the simpleRegexPattern
     */
    public Pattern getSimpleRegexPattern() {
        return simpleRegexPattern;
    }

}
