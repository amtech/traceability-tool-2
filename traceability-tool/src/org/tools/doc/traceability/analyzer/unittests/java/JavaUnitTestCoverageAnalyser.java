/**
 * 
 */
package org.tools.doc.traceability.analyzer.unittests.java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.tools.doc.traceability.analyzer.unittests.common.UnitTestCaseData;
import org.tools.doc.traceability.analyzer.unittests.java.model.JUnitMethodData;
import org.tools.doc.traceability.analyzer.unittests.java.model.JavaUnitTestFileData;
import org.tools.doc.traceability.common.Constants;
import org.tools.doc.traceability.common.exceptions.AbstractTraceabilityException;
import org.tools.doc.traceability.common.exceptions.ExecutorExecutionException;
import org.tools.doc.traceability.common.exceptions.FileReadingException;
import org.tools.doc.traceability.common.exceptions.FileSearchException;
import org.tools.doc.traceability.common.exceptions.InvalidFileSearchFilterException;
import org.tools.doc.traceability.common.exceptions.InvalidParameterException;
import org.tools.doc.traceability.common.executor.AbstractExecutor;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.filesearch.FileSearchFilterSet;
import org.tools.doc.traceability.common.filesearch.FileSearcher;
import org.tools.doc.traceability.common.io.FileLinesReader;
import org.tools.doc.traceability.common.model.Requirement;
import org.tools.doc.traceability.common.sregex.SimpleRegex;

/**
 * A tool parsing the java files containing Junit tests and extracting the
 * contained data.
 * <p>
 * It analyzes the coverage from java code unit tests.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class JavaUnitTestCoverageAnalyser extends AbstractExecutor<JavaUnitTestCoverageAnalyserResult> {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LogManager.getLogger(JavaUnitTestCoverageAnalyser.class);

    /**
     * The regexp to capture a JUnit method with leading javadoc.
     * <p>
     * If it matches, there are the following groups captured:
     * <ol>
     * <li>Javadco contents
     * <li>Potential "public " modifier for JUnit method
     * <li>Method name
     * <li>The rest of the class contents after the opening curly brace.
     * </ol>
     * </p>
     */
    private static final String JAVADOC_METHOD_REGEXP = //
            ".*?" // Anything (reluctant)
                    + "\\/\\*\\*" // Opening javadoc comment
                    + "(.+?)" // Contents of the javadoc comment (reluctant)
                    + "\\*\\/" // Closing of javadoc comment
                    + "[\\s\\n]*" // Further spaces and line returns
                    + "@Test" // The JUnit annotation for testing method
                    + "[\\s\\n]*" // Further spaces or line breaks
                    + "(public\\s)?\\s*void\\s\\s*([A-Za-z_][A-Za-z0-9_]*)\\(\\s*\\)[^{]*"
                    // Standard JUnit method signature
                    + "\\{" // Opening method curly brace
                    + "(.*)"; // Remaining part
    
    /**
     * Regexp to extract the useful part of a javadoc line, i.e. that gets rid
     * of leading spaces and star.
     * <p>
     * If it matches, the first group contains the actual contents.
     * </p>
     */
    private static final String JAVADOC_LINE_ACTUAL_CONTENTS_REGEXP = "[\\s*]*(.*)";

    /**
     * Regexp to match the description.
     * <p>
     * If it matches, the first group contains the description.
     * </p>
     */
    private static final String DESCRIPTION_REGEXP = "(.*)@.*";

    /**
     * Regexp to match the test identifier.
     * <p>
     * If it matches, the first group contains the test identifier.
     * </p>
     */
    private static final String TEST_ID_REGEXP = ".*@testId\\s*\"([^\"]*)\".*";

    /**
     * Regexp to match the expected result.
     * <p>
     * If it matches, the first group contains the description of the expected
     * result.
     * </p>
     */
    private static final String EXPECTED_RESULT_REGEXP = ".*@expectedResult\\s*\"([^\"]*)\".*";

    /**
     * Regexp to match the covered requirements.
     * <p>
     * If it matches, the first group contains the list of covered requirements.
     * </p>
     */
    private static final String COVERED_REQUIREMENTS_REGEXP = ".*@coveredReqs\\s*\"([^\"]*)\".*";

    /**
     * The set of java file search filter.
     */
    private final FileSearchFilterSet javaFileSearchFilterSet;

    /**
     * The textual representation of the regular expression that test method
     * shall comply to in order to be taken into account.
     */
    private final SimpleRegex testMethodNameRegexpValue;

    /**
     * The result object.
     */
    private JavaUnitTestCoverageAnalyserResult resultObject;

    /**
     * The pattern to match JUnit methods with javadoc.
     */
    private Pattern javadocMethodRegexp;

    /**
     * The pattern to capture the useful part of a javadoc line.
     */
    private Pattern javadocLineActualContentsRegexp;

    /**
     * The pattern to match JUnit description.
     */
    private Pattern descriptionPattern;

    /**
     * The pattern to match the line giving the test identifier.
     */
    private Pattern testIdentifierLinePattern;

    /**
     * The pattern to match the line giving the expected result.
     */
    private Pattern expectedResultLinePattern;

    /**
     * The pattern to match the line giving the covered requirements.
     */
    private Pattern coveredRequiermentsLinePattern;

    /**
     * The regular expression to match the list of covered requirements.
     */
    private Pattern coveredReqListPattern;

    /**
     * Constructor.
     * 
     * @param pJavaFileSearchFilterSet the list of file search filters to select
     * the java files to analyze.
     * @param pTestMethodNameRegexpValue the textual representation of the
     * regular expression that test method shall comply to in order to be taken
     * into account.
     * @param pExecutionStatus the executor execution status.
     */
    public JavaUnitTestCoverageAnalyser(final FileSearchFilterSet pJavaFileSearchFilterSet,
            final SimpleRegex pTestMethodNameRegexpValue,
            final ExecutorExecutionStatus<JavaUnitTestCoverageAnalyserResult> pExecutionStatus) {
        super(pExecutionStatus);
        javaFileSearchFilterSet = pJavaFileSearchFilterSet;
        testMethodNameRegexpValue = pTestMethodNameRegexpValue;

        resultObject = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performTask() throws AbstractTraceabilityException {

        LOGGER.info("Start");

        // First, check the provided arguments
        checkArguments();

        // Then perform the analysis
        analyzeTestResults();
    }

    /**
     * Browse the JUnit root directory in search of JUnit files and try to parse
     * them to extract test informations.
     * 
     * @throws ExecutorExecutionException if an error occurs.
     */
    private void analyzeTestResults() throws ExecutorExecutionException {

        double lCurrentPercentage = 5.0;
        setCurrentOperation("Searching for XML files in " + javaFileSearchFilterSet.toString(), lCurrentPercentage);

        javadocMethodRegexp = Pattern.compile(JAVADOC_METHOD_REGEXP, Pattern.DOTALL);
        javadocLineActualContentsRegexp = Pattern.compile(JAVADOC_LINE_ACTUAL_CONTENTS_REGEXP);
        descriptionPattern = Pattern.compile(DESCRIPTION_REGEXP, Pattern.DOTALL);
        testIdentifierLinePattern = Pattern.compile(TEST_ID_REGEXP, Pattern.DOTALL);
        expectedResultLinePattern = Pattern.compile(EXPECTED_RESULT_REGEXP, Pattern.DOTALL);
        coveredRequiermentsLinePattern = Pattern.compile(COVERED_REQUIREMENTS_REGEXP, Pattern.DOTALL);
        coveredReqListPattern = Pattern.compile(Constants.COVERED_REQUIREMENT_LIST_REGEXP);

        // First search for the XML files
        FileSearcher lFileSearcher = new FileSearcher(javaFileSearchFilterSet);
        List<File> lJavaFileList;
        try {
            lJavaFileList = lFileSearcher.search();
        } catch (FileSearchException e) {
            throw new ExecutorExecutionException("Error searching for .java files : " + e.getMessage());
        }

        resultObject = new JavaUnitTestCoverageAnalyserResult();

        double lPercentagePerFile = 70. / lJavaFileList.size();

        List<JUnitMethodData> lJUnitMethodDataList;

        // Iterate on each of them
        for (File lJavaFile : lJavaFileList) {
            // Process this file
            setCurrentOperation("Processing java unit test result file " + lJavaFile.getAbsolutePath(),
                    lCurrentPercentage);

            lJUnitMethodDataList = processJavaFile(lJavaFile);
            JavaUnitTestFileData lUnitTestFileData;
            UnitTestCaseData lUnitTestData;

            if (!lJUnitMethodDataList.isEmpty()) {
                lUnitTestFileData = new JavaUnitTestFileData();

                for (JUnitMethodData lJUnitMethodData : lJUnitMethodDataList) {
                    lUnitTestData = new UnitTestCaseData(lJUnitMethodData.getTestIdentifier(),
                            lJUnitMethodData.getMethodName(), lJUnitMethodData.getTestDescription(),
                            lJUnitMethodData.getExpectedResult());

                    for (Requirement lReq : lJUnitMethodData.getCoveredRequirements()) {
                        lUnitTestData.addCoveredRequirement(lReq);
                    }

                    lUnitTestFileData.addUnitTestData(lUnitTestData);
                }

                resultObject.addResult(lJavaFile, lUnitTestFileData);
            }

            lCurrentPercentage += lPercentagePerFile;
        }

        // Set the result object
        setExecutionResult(resultObject);
    }

    /**
     * Try and process the given java file.
     * 
     * @param pJavaFile the java file to process.
     * @return the list of JUnit method data found for this file.
     * @throws ExecutorExecutionException if an error occurs.
     */
    private List<JUnitMethodData> processJavaFile(final File pJavaFile) throws ExecutorExecutionException {
        LOGGER.debug("Processing java unit test result file " + pJavaFile.getAbsolutePath());

        List<JUnitMethodData> lJUnitMethodDataList = new ArrayList<JUnitMethodData>();

        String lJavaClassContents = getJavaClassContentsFor(pJavaFile);

        Matcher lJunitMethodMatcher = javadocMethodRegexp.matcher(lJavaClassContents);

        String lUsefulPartOfJavadocLine;
        JUnitMethodData lJunitMethodData;
        while (lJunitMethodMatcher.matches()) {
            String lJavadocContents = lJunitMethodMatcher.group(1);
            String lMethodName = lJunitMethodMatcher.group(3);
            String lRemainingClassContents = lJunitMethodMatcher.group(4);

            List<String> lCleanedJavadocLines = new ArrayList<String>();

            String[] lJavadocLines = lJavadocContents.split("\n");

            for (int i = 0; i < lJavadocLines.length; i++) {
                Matcher lUsefulJavadocLinePartMatcher = javadocLineActualContentsRegexp.matcher(lJavadocLines[i]);
                if (lUsefulJavadocLinePartMatcher.matches()) {
                    lUsefulPartOfJavadocLine = lUsefulJavadocLinePartMatcher.group(1).trim();
                    if (!lUsefulPartOfJavadocLine.isEmpty()) {
                        lCleanedJavadocLines.add(lUsefulPartOfJavadocLine);
                    }
                }
            }

            // First ensure the method matches the expected simple regexp
            if (testMethodNameRegexpValue.matches(lMethodName)) {
                // If it is, then process the method and its cleaned javadoc
                // contents.
                lJunitMethodData = processJunitMethod(pJavaFile, lMethodName, lCleanedJavadocLines);

                if (lJunitMethodData.isValid()) {
                    lJUnitMethodDataList.add(lJunitMethodData);
                } else {
                    // TODO
                }
            }

            // Eliminate the lines until we found the closing curly brace
            // matching the one corresponding to the opening of the analyzed
            // method
            String[] lRemainingLines = lRemainingClassContents.split("\n");
            int lCurlyBraceBalance = 1;
            String lRemainingLine;
            int lOpeningCb;
            int lClosingCb;
            char lChar;

            StringBuilder lNextPartToConsiderSb = new StringBuilder();
            for (int i = 0; i < lRemainingLines.length; i++) {
                lRemainingLine = lRemainingLines[i];

                if (lCurlyBraceBalance == 0) {
                    lNextPartToConsiderSb.append(lRemainingLine);
                    lNextPartToConsiderSb.append("\n");
                } else {
                    lOpeningCb = 0;
                    lClosingCb = 0;
                    for (int j = 0; j < lRemainingLine.length(); j++) {
                        lChar = lRemainingLine.charAt(j);
                        if (lChar == '{') {
                            lOpeningCb++;
                        } else if (lChar == '}') {
                            lClosingCb++;
                        }
                    }

                    lCurlyBraceBalance = lCurlyBraceBalance + lOpeningCb - lClosingCb;
                }
            }

            // Try to match with the remaining class contents
            lJunitMethodMatcher = javadocMethodRegexp.matcher(lNextPartToConsiderSb.toString());
        }

        return lJUnitMethodDataList;
    }

    /**
     * Process a JUnit method javadoc.
     * 
     * @param pJavaFile the source java file.
     * @param pMethodName the JUnit method name.
     * @param pCleanedJavadocLines the not empty lines from the javadoc, without
     * the leading blank or star characters.
     * @return the associated {@link JUnitMethodData}
     */
    private JUnitMethodData processJunitMethod(final File pJavaFile, final String pMethodName,
            final List<String> pCleanedJavadocLines) {

        JUnitMethodData lJUnitMethodData = null;

        String lTestDescription = null;
        String lTestId = null;
        String lExpectedResult = null;
        String lCoveredReqs = null;

        // Combine the cleaned contents together
        StringBuilder lContentsSb = new StringBuilder();
        boolean lIsFirst = true;
        for (String lJavadocLine : pCleanedJavadocLines) {
            if (lIsFirst) {
                lIsFirst = false;
            } else {
                lContentsSb.append("\n");
            }
            lContentsSb.append(lJavadocLine);
        }

        String lJavadocContents = lContentsSb.toString();
        Matcher lTestIdentifierLineMatcher = testIdentifierLinePattern.matcher(lJavadocContents);
        if (lTestIdentifierLineMatcher.matches()) {
            lTestId = lTestIdentifierLineMatcher.group(1);
        }

        Matcher lExpectedResultLineMatcher = expectedResultLinePattern.matcher(lJavadocContents);
        if (lExpectedResultLineMatcher.matches()) {
            lExpectedResult = lExpectedResultLineMatcher.group(1);
        }

        Matcher lCoveredReqsLineMatcher = coveredRequiermentsLinePattern.matcher(lJavadocContents);
        if (lCoveredReqsLineMatcher.matches()) {
            lCoveredReqs = lCoveredReqsLineMatcher.group(1);
        }
        ;

        Matcher lDescriptionMatcher = descriptionPattern.matcher(lJavadocContents);
        if (lDescriptionMatcher.matches()) {
            lTestDescription = lDescriptionMatcher.group(1);
        }

        lJUnitMethodData = new JUnitMethodData(pMethodName);
        lJUnitMethodData.setTestIdentifier(lTestId);
        lJUnitMethodData.setTestDescription(lTestDescription);
        lJUnitMethodData.setExpectedResult(lExpectedResult);
        boolean lContinueSearching = true;
        String lRemainingPart = lCoveredReqs;
        while (lContinueSearching) {
            if (lRemainingPart != null) {
                Matcher lReqMatcher = coveredReqListPattern.matcher(lRemainingPart);
                if (lReqMatcher.matches()) {
                    // Add the requirement located in group 1
                    Requirement lCoveredReq = new Requirement(lReqMatcher.group(1));
                    lJUnitMethodData.addCoveredRequirement(lCoveredReq);
                    // Update the remaining string in group 3
                    lRemainingPart = lReqMatcher.group(3);
                } else {
                    lContinueSearching = false;
                }
            } else {
                lContinueSearching = false;
            }
        }

        return lJUnitMethodData;
    }

    /**
     * Returns the contents of the class.
     * <p>
     * It assumes there is only one class in the file.
     * </p>
     * 
     * @param pJavaFile the java file to read.
     * @return the class contents (can be empty if none was found).
     * @throws ExecutorExecutionException if an error occurs reading the file.
     */
    private String getJavaClassContentsFor(final File pJavaFile) throws ExecutorExecutionException {

        StringBuilder lJavaClassContentsSb = new StringBuilder();

        FileLinesReader fileLinesReader = new FileLinesReader();

        // Try and read file contents (If it fails FileReadingException is
        // thrown)
        try {
            // Read all the lines
            List<String> lFileLines = fileLinesReader.readFileContents(pJavaFile);

            // Iterate and wait for the line defining the class to be found and
            // the associated class opening curly brace to be passed
            boolean lClassKeywordFound = false;
            boolean lClassOpeningCurlyBraceFound = false;

            for (String lLine : lFileLines) {
                if (lClassKeywordFound) {
                    if (lClassOpeningCurlyBraceFound) {
                        lJavaClassContentsSb.append(lLine);
                        lJavaClassContentsSb.append("\n");
                    } else {
                        if (lLine.contains("{")) {
                            lClassOpeningCurlyBraceFound = true;
                        }
                    }
                } else {
                    if (lLine.contains("class")) {
                        lClassKeywordFound = true;
                        if (lLine.contains("{")) {
                            lClassOpeningCurlyBraceFound = true;
                        }
                    }
                }
            }

        } catch (FileReadingException e) {
            LOGGER.error("Error reading file " + pJavaFile.getAbsolutePath() + " : " + e.getMessage());
        }

        return lJavaClassContentsSb.toString();
    }

    /**
     * Checks the provided arguments and throw an exception if one is invalid.
     * 
     * @throws InvalidParameterException if an argument is invalid.
     */
    private void checkArguments() throws InvalidParameterException {
        setCurrentOperation("Checking arguments", 5.);
        // Ensure list of search filters is defined
        if (javaFileSearchFilterSet == null) {
            throw new InvalidParameterException("JavaUnitTestCoverageAnalyser.checkArguments",
                    "Java file search filter set", "null value");
        } else {
            // Ensure list of search filters is valid
            try {
                javaFileSearchFilterSet.checkIsValid();

            } catch (InvalidFileSearchFilterException e) {
                throw new InvalidParameterException("JavaUnitTestCoverageAnalyser.checkArguments",
                        "Java file search filter set", "One of the filters is invalid : " + e.getMessage());
            }

            // Check the regexp for the JUnit method name
            if (testMethodNameRegexpValue == null) {
                throw new InvalidParameterException("JavaUnitTestCoverageAnalyser.checkArguments",
                        "JUnit method name regular expression", "null value");
            }
        }
    }
}
