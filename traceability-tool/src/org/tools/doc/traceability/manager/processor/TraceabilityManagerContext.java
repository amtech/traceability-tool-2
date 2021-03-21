/**
 * 
 */
package org.tools.doc.traceability.manager.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.tools.doc.traceability.common.exceptions.InvalidFileSearchFilterException;
import org.tools.doc.traceability.common.exceptions.InvalidSimpleRegexpException;
import org.tools.doc.traceability.common.exceptions.InvalidTraceabilityManagerContextException;
import org.tools.doc.traceability.common.filesearch.FileSearchFilter;
import org.tools.doc.traceability.common.filesearch.FileSearchFilterSet;
import org.tools.doc.traceability.common.sregex.SimpleRegex;

/**
 * Models a context to pass to the Traceability manager to give all the
 * information to be able to work.
 * 
 * @author Yann Leglise
 *
 */
public class TraceabilityManagerContext {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LogManager.getLogger(TraceabilityManagerContext.class);

    /**
     * The list of SD files containing the requirements to check.
     */
    private final List<File> sdFileList;

    /**
     * The list of prefixes that the requirements to handle start with.
     */
    private final List<String> requirementPrefixList;

    /**
     * The set of file search filter to match files to analyze for cucumber
     * tests (i.e. <tt>.feature</tt> files).
     * <p>
     * Can be empty if there is no cucumber tests.
     * </p>
     */
    private final FileSearchFilterSet cucumberFileSearchFilterSet;

    /**
     * The set of file search filter to match files to analyze for C# unit tests
     * (i.e. <tt>.xml</tt> files).
     * <p>
     * Can be empty if there is no C# unit tests.
     * </p>
     */
    private final FileSearchFilterSet cSharpFileSearchFilterSet;

    /**
     * The simple regular expression for C# unit test methods to consider.
     * <p>
     * It is initialized to match all methods by default.
     * </p>
     */
    private SimpleRegex cSharpMethodRegexp;

    /**
     * The set of file search filter to match files to analyze for java unit
     * tests (i.e. <tt>.java</tt> JUnit files).
     * <p>
     * Can be empty if there is no java unit tests.
     * </p>
     */
    private final FileSearchFilterSet javaFileSearchFilterSet;

    /**
     * The simple regular expression for java unit test methods to consider.
     * <p>
     * It is initialized to match all methods by default.
     * </p>
     */
    private SimpleRegex javaMethodRegexp;

    /**
     * The justification file.
     */
    private File justificationFile;

    /**
     * The ALM texts extraction file.
     */
    private File almTestsExtractFile;

    /**
     * The output file where the list of requirements extracted from SD files
     * shall be written.
     */
    private File outputExtractedRequirementsFile;

    /**
     * The output file where all the tests shall be gathered (VTP file).
     */
    private File outputVtpFile;

    /**
     * The output file where the traceability matrix must be written.
     */
    private File outputTraceabilityMatrixFile;

    /**
     * Constructor.
     */
    public TraceabilityManagerContext() {
        sdFileList = new ArrayList<File>();
        requirementPrefixList = new ArrayList<String>();
        justificationFile = null;
        almTestsExtractFile = null;
        outputExtractedRequirementsFile = null;
        outputVtpFile = null;
        outputTraceabilityMatrixFile = null;
        try {
            cSharpMethodRegexp = new SimpleRegex("*");
        } catch (InvalidSimpleRegexpException e) {
            LOGGER.error("Could not create regexp for C# method names : " + e.getMessage());
            cSharpMethodRegexp = null;
        }

        try {
            javaMethodRegexp = new SimpleRegex("*");
        } catch (InvalidSimpleRegexpException e) {
            LOGGER.error("Could not create regexp for java method names : " + e.getMessage());
            javaMethodRegexp = null;
        }

        cucumberFileSearchFilterSet = new FileSearchFilterSet();
        cSharpFileSearchFilterSet = new FileSearchFilterSet();
        javaFileSearchFilterSet = new FileSearchFilterSet();
    }

    /**
     * Add a new specification dossier file to the context.
     * 
     * @param pSdFile the SD file containing requirements.
     */
    public void addSpecificationDossier(final File pSdFile) {
        sdFileList.add(pSdFile);
    }

    /**
     * Add a requirement prefix.
     * 
     * @param pRequirementPrefix a prefix that the requirements to handle can
     * start with.
     */
    public void addRequirementPrefix(final String pRequirementPrefix) {
        requirementPrefixList.add(pRequirementPrefix);
    }

    /**
     * Getter of the list of SD files containing the requirements to check.
     * 
     * @return the sdFileList
     */
    public List<File> getSdFileList() {
        return sdFileList;
    }

    /**
     * Getter of the list of prefixes that the requirements to handle start
     * with.
     * 
     * @return the requirementPrefixList
     */
    public List<String> getRequirementPrefixList() {
        return requirementPrefixList;
    }

    /**
     * Getter of the list of cucumber file search filter.
     * 
     * @return the cucumber file search filter, which is empty if there is no
     * cucumber tests.
     */
    public FileSearchFilterSet getCucumberFileSearchFilterList() {
        return cucumberFileSearchFilterSet;
    }

    /**
     * Add a cucumber file search filter to match cucumber tests (i.e.
     * <tt>.feature</tt> files).
     * 
     * @param pCucumberFileSearchFilter the cucumberFileSearchFilter to add
     * @throws InvalidFileSearchFilterException if the filter is invalid
     */
    public void addCucumberFileSearchFilter(final FileSearchFilter pCucumberFileSearchFilter)
            throws InvalidFileSearchFilterException {
        if (pCucumberFileSearchFilter == null) {
            LOGGER.warn("Trying to add a null cucumber file search filter");
        } else {
            cucumberFileSearchFilterSet.addFilter(pCucumberFileSearchFilter);
        }
    }

    /**
     * Getter of the list file search filter to match files to analyze for C#
     * unit tests..
     * 
     * @return the C# file search filter set, which can be empty if there is no
     * C# unit tests.
     */
    public FileSearchFilterSet getCSharpFileSearchFilterList() {
        return cSharpFileSearchFilterSet;
    }

    /**
     * Add a file search filter to match files to analyze for C# unit tests
     * (i.e. <tt>.xml</tt> files).
     * 
     * @param pCSharpFileSearchFilter the cSharpFileSearchFilter to add
     * @throws InvalidFileSearchFilterException if the filter is invalid.
     */
    public void addCSharpFileSearchFilter(final FileSearchFilter pCSharpFileSearchFilter)
            throws InvalidFileSearchFilterException {
        if (pCSharpFileSearchFilter == null) {
            LOGGER.warn("Trying to add a null C# unit test file search filter");
        } else {
            cSharpFileSearchFilterSet.addFilter(pCSharpFileSearchFilter);
        }
    }

    /**
     * Sets the simple regular expression to match the C# unit test methods to
     * consider.
     * 
     * @param pSimpleRegexpValue the simple regular expression value (see.
     * {@link SimpleRegex} for details).
     * @throws InvalidSimpleRegexpException if the value for the simple regular
     * expression was not valid.
     */
    public void setCSharpMethodRegexp(final String pSimpleRegexpValue) throws InvalidSimpleRegexpException {
        cSharpMethodRegexp = new SimpleRegex(pSimpleRegexpValue);
    }

    /**
     * Sets the simple regular to match the C# unit test methods to consider.
     * 
     * @param pSimpleRegexp the simple regular expression.
     */
    public void setCSharpMethodRegexp(final SimpleRegex pSimpleRegexp) {
        cSharpMethodRegexp = pSimpleRegexp;
    }

    /**
     * Getter of the C# method simple regexp.
     * 
     * @return the cSharpMethodRegexp
     */
    public SimpleRegex getcSharpMethodRegexp() {
        return cSharpMethodRegexp;
    }

    /**
     * Getter of the list of file search filter to match files to analyze for
     * java unit tests.
     * 
     * @return the java file search filter set, which can be empty if there is
     * no java unit tests.
     */
    public FileSearchFilterSet getJavaFileSearchFilterList() {
        return javaFileSearchFilterSet;
    }

    /**
     * Getter of the java method regexp.
     * 
     * @return the javaMethodRegexp
     */
    public SimpleRegex getJavaMethodRegexp() {
        return javaMethodRegexp;
    }

    /**
     * Sets the simple regular expression to match the java unit test methods to
     * consider.
     * 
     * @param pSimpleRegexpValue the simple regular expression value (see.
     * {@link SimpleRegex} for details).
     * @throws InvalidSimpleRegexpException if the value for the simple regular
     * expression was not valid.
     */
    public void setJavaMethodRegexp(final String pSimpleRegexpValue) throws InvalidSimpleRegexpException {
        javaMethodRegexp = new SimpleRegex(pSimpleRegexpValue);
    }

    /**
     * Sets the simple regular expression to match the java unit test methods to
     * consider.
     * 
     * @param pSimpleRegexp the simple regular expression to set.
     */
    public void setJavaMethodRegexp(final SimpleRegex pSimpleRegexp) {
        javaMethodRegexp = pSimpleRegexp;
    }

    /**
     * Add a file search filter to match files to analyze for java unit tests
     * (i.e. <tt>.java</tt> JUnit files).
     * 
     * @param pJavaFileSearchFilter the javaFileSearchFilter to add
     * @throws InvalidFileSearchFilterException if the filter is invalid.
     */
    public void addJavaFileSearchFilter(final FileSearchFilter pJavaFileSearchFilter)
            throws InvalidFileSearchFilterException {
        if (pJavaFileSearchFilter == null) {
            LOGGER.warn("Trying to add a null C# unit test file search filter");
        } else {
            javaFileSearchFilterSet.addFilter(pJavaFileSearchFilter);
        }
    }

    /**
     * Getter of the justification file.
     * 
     * @return the justificationFile
     */
    public File getJustificationFile() {
        return justificationFile;
    }

    /**
     * Setter of the justification file.
     * 
     * @param pJustificationFile the justificationFile to set
     */
    public void setJustificationFile(final File pJustificationFile) {
        justificationFile = pJustificationFile;
    }

    /**
     * Getter of the output file where the list of requirements extracted from
     * SD files shall be written..
     * 
     * @return the outputExtractedRequirementsFile
     */
    public File getOutputExtractedRequirementsFile() {
        return outputExtractedRequirementsFile;
    }

    /**
     * Setter of the output file where the list of requirements extracted from
     * SD files shall be written..
     * 
     * @param pOutputExtractedRequirementsFile the
     * outputExtractedRequirementsFile to set
     */
    public void setOutputExtractedRequirementsFile(final File pOutputExtractedRequirementsFile) {
        outputExtractedRequirementsFile = pOutputExtractedRequirementsFile;
    }

    /**
     * Getter of the output file where all the tests shall be gathered (VTP
     * file)..
     * 
     * @return the outputVtpFile
     */
    public File getOutputVtpFile() {
        return outputVtpFile;
    }

    /**
     * Setter of the output file where all the tests shall be gathered (VTP
     * file)..
     * 
     * @param pOutputVtpFile the outputVtpFile to set
     */
    public void setOutputVtpFile(final File pOutputVtpFile) {
        outputVtpFile = pOutputVtpFile;
    }

    /**
     * Getter of the output file where the traceability matrix must be written..
     * 
     * @return the outputTraceabilityMatrixFile
     */
    public File getOutputTraceabilityMatrixFile() {
        return outputTraceabilityMatrixFile;
    }

    /**
     * Setter of the output file where the traceability matrix must be written..
     * 
     * @param pOutputTraceabilityMatrixFile the outputTraceabilityMatrixFile to
     * set
     */
    public void setOutputTraceabilityMatrixFile(final File pOutputTraceabilityMatrixFile) {
        outputTraceabilityMatrixFile = pOutputTraceabilityMatrixFile;
    }

    /**
     * Make sure the context is valid.
     * 
     * <p>
     * If no exception is thrown, then we are sure that :
     * <ol>
     * <li>There is at least one SD file, and all the referenced SD files exist,
     * <li>The folder containing the output extracted requirements file does
     * exist,
     * <li>The output VTP file does exist,
     * <li>The folder containing the output traceability matrix file does exist,
     * </ol>
     * </p>
     * 
     * @throws InvalidTraceabilityManagerContextException if the context is not
     * valid.
     */
    public void checkValidity() throws InvalidTraceabilityManagerContextException {

        // Ensure there is at least one input SD file, and those that are
        // referenced exist
        boolean lOneSdFileExists = false;

        for (File lSdFile : sdFileList) {
            if (lSdFile.isFile()) {
                lOneSdFileExists = true;
            } else {
                throw new InvalidTraceabilityManagerContextException("SD file lits",
                        "Contains one not existing file : " + lSdFile.getAbsolutePath());
            }
        }
        if (!lOneSdFileExists) {
            throw new InvalidTraceabilityManagerContextException("SD file lits", "list is empty");
        }

        // Check that if the output extracted requirements file is specified,
        // its parent folder exists
        if (outputExtractedRequirementsFile != null) {
            if (!outputExtractedRequirementsFile.getParentFile().isDirectory()) {
                throw new InvalidTraceabilityManagerContextException("Output extracted requirements file",
                        "the file parent's folder does not exist : "
                                + outputExtractedRequirementsFile.getParentFile().getAbsolutePath());
            }
        }

        // Make sure the output VTP file exists
        if (outputVtpFile == null) {
            throw new InvalidTraceabilityManagerContextException("Output VTP file", "null value");
        } else {
            if (!outputVtpFile.isFile()) {
                throw new InvalidTraceabilityManagerContextException("Output VTP file",
                        "does not exist or is not a file : " + outputVtpFile.getAbsolutePath());
            }
        }

        // Check that if the output traceability matrix file is specified, its
        // parent folder exists
        if (outputTraceabilityMatrixFile == null) {
            throw new InvalidTraceabilityManagerContextException("Output traceability matrix file", "null value");
        } else {
            if (!outputTraceabilityMatrixFile.getParentFile().isDirectory()) {
                throw new InvalidTraceabilityManagerContextException("Output traceability matrix file",
                        "the file parent's folder does not exist : "
                                + outputTraceabilityMatrixFile.getParentFile().getAbsolutePath());
            }
        }

        // Check that if a justification file is specified then it extist
        if (justificationFile != null) {
            if (!justificationFile.isFile()) {
                throw new InvalidTraceabilityManagerContextException("Justification file", "does not exist : "
                        + justificationFile.getAbsolutePath());
            }
        }
    }

    /**
     * Getter of the ALM extraction file..
     * 
     * @return the almTestsExtractFile or <tt>null</tt> if none.
     */
    public File getAlmTestsExtractFile() {
        return almTestsExtractFile;
    }

    /**
     * Setter of the ALM extraction file..
     * 
     * @param pAlmTestsExtractFile the almTestsExtractFile to set
     */
    public void setAlmTestsExtractFile(final File pAlmTestsExtractFile) {
        almTestsExtractFile = pAlmTestsExtractFile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder lSb = new StringBuilder();

        lSb.append("Traceability Manager context :");
        lSb.append("\n\tRequirement prefixes:");
        for (String lReqPrefix : requirementPrefixList) {
            lSb.append("\n\t\t");
            lSb.append(lReqPrefix);
        }
        lSb.append("\n\tSD Files:");
        for (File lSdFile : sdFileList) {
            lSb.append("\n\t\t");
            lSb.append(lSdFile.getAbsolutePath());
        }

        lSb.append("\n\tJustification file: ");
        if (justificationFile == null) {
            lSb.append("None");
        } else {
            lSb.append(justificationFile.getAbsolutePath());
        }

        lSb.append("\n\tALM tests coverage: ");
        if (almTestsExtractFile == null) {
            lSb.append("Not activated");
        } else {
            lSb.append("Activated with file ");
            lSb.append(almTestsExtractFile.getAbsolutePath());
        }

        lSb.append("\n\tCucumber tests coverage: ");
        if (cucumberFileSearchFilterSet.isEmpty()) {
            lSb.append("Not activated");
        } else {
            lSb.append("Activated with file search filter ");
            lSb.append(cucumberFileSearchFilterSet.toString());
        }

        lSb.append("\n\tC# unit tests coverage: ");
        if (cSharpFileSearchFilterSet.isEmpty()) {
            lSb.append("Not activated");
        } else {
            lSb.append("Activated with file search filter ");
            lSb.append(cSharpFileSearchFilterSet.toString());
            lSb.append(" and method regexp ");
            lSb.append(cSharpMethodRegexp);
        }

        lSb.append("\n\tJava unit tests coverage: ");
        if (javaFileSearchFilterSet.isEmpty()) {
            lSb.append("Not activated");
        } else {
            lSb.append("Activated with file search filter ");
            lSb.append(javaFileSearchFilterSet.toString());
            lSb.append(" and method regexp ");
            lSb.append(javaMethodRegexp);
        }
        
        lSb.append("\n\tExtracted req file: ");
        if (outputExtractedRequirementsFile == null) {
            lSb.append("Not defined");
        } else {
            lSb.append(outputExtractedRequirementsFile.getAbsolutePath());
        }

        lSb.append("\n\tVTP file: ");
        if (outputVtpFile == null) {
            lSb.append("Not defined");
        } else {
            lSb.append(outputVtpFile.getAbsolutePath());
        }

        lSb.append("\n\tTraceability matrix file: ");
        if (outputTraceabilityMatrixFile == null) {
            lSb.append("Not defined");
        } else {
            lSb.append(outputTraceabilityMatrixFile.getAbsolutePath());
        }

        return lSb.toString();
    }
}
