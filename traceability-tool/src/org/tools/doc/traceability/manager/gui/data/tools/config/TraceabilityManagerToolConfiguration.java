/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.tools.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.common.exceptions.InvalidConfigurationException;
import org.tools.doc.traceability.common.exceptions.InvalidSimpleRegexpException;
import org.tools.doc.traceability.manager.gui.configuration.ConfigurationManager;
import org.tools.doc.traceability.manager.gui.controller.TraceabilityToolController;
import org.tools.doc.traceability.manager.gui.data.app.ApplicationData;

/**
 * The configuration for the Traceability Manager tool.
 * 
 * @author Yann Leglise
 *
 */
public class TraceabilityManagerToolConfiguration extends AbstractToolConfiguration {

    /**
     * The list of SD files.
     */
    private List<File> sdFiles;

    /**
     * The list of requirement prefixes.
     */
    private List<String> requirementPrefixes;

    /**
     * The justification file.
     */
    private File justificationFile;

    /**
     * The configuration for the ALM tests coverage.
     */
    private final AlmTestsCoverageConfiguration almTestsCoverageConfiguration;

    /**
     * The configuration for cucumber tests coverage.
     */
    private final CucumberTestsCoverageConfiguration cucumberTestsCoverageConfiguration;

    /**
     * The configuration for the C# unit tests coverage.
     */
    private final CSharpUnitTestsCoverageConfiguration cSharpUnitTestsCoverageConfiguration;

    /**
     * The configuration for the java unit tests coverage.
     */
    private final JavaUnitTestsCoverageConfiguration javaUnitTestsCoverageConfiguration;

    /**
     * The output extracted requirements file.
     */
    private File outputExtractedRequirementsFile;

    /**
     * The output coverage matrix file.
     */
    private File outputTraceabilityMatrixFile;

    /**
     * The output VTP file.
     */
    private File outputVtpFile;

    /**
     * Constructor.
     */
    public TraceabilityManagerToolConfiguration() {
        sdFiles = new ArrayList<File>();
        requirementPrefixes = new ArrayList<String>();
        justificationFile = null;

        almTestsCoverageConfiguration = new AlmTestsCoverageConfiguration();
        cucumberTestsCoverageConfiguration = new CucumberTestsCoverageConfiguration();
        cSharpUnitTestsCoverageConfiguration = new CSharpUnitTestsCoverageConfiguration();
        javaUnitTestsCoverageConfiguration = new JavaUnitTestsCoverageConfiguration();

        outputExtractedRequirementsFile = null;
        outputVtpFile = null;
        outputTraceabilityMatrixFile = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkIsValid() throws InvalidConfigurationException {
        if (sdFiles.isEmpty()) {
            throw new InvalidConfigurationException("Define the input SD file");
        } else {
            File lSdFile = sdFiles.get(0);
            if (lSdFile.isFile()) {

                if (requirementPrefixes.isEmpty()) {
                    throw new InvalidConfigurationException("Define the requirement prefixes");
                } else {
                    if (outputTraceabilityMatrixFile == null) {
                        throw new InvalidConfigurationException("Define the output traceability matrix file");
                    } else {
                        // If there is a justfication file defined it shall
                        // exist
                        if (justificationFile != null) {
                            if (!justificationFile.isFile()) {
                                throw new InvalidConfigurationException("Define an existing justification file");
                            }
                        }
                        if (outputTraceabilityMatrixFile.isFile()) {
                            // Make sure the target VTP exists is defined
                            if (!outputVtpFile.isFile()) {
                                throw new InvalidConfigurationException("Define an existing output VTP file");
                            } else {
                                almTestsCoverageConfiguration.checkIsValid();
                                cucumberTestsCoverageConfiguration.checkIsValid();
                                cSharpUnitTestsCoverageConfiguration.checkIsValid();
                                javaUnitTestsCoverageConfiguration.checkIsValid();
                            }
                        } else {
                            throw new InvalidConfigurationException(
                                    "Define an existing output traceability matrix file");
                        }
                    }
                }
            } else {
                throw new InvalidConfigurationException("Define an existing input SD file");
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void handleNewCurrentApplicationData(final ApplicationData pCurrentApplicationData) {
        if (pCurrentApplicationData == null) {
            sdFiles.clear();
            requirementPrefixes.clear();
            justificationFile = null;
            almTestsCoverageConfiguration.reset();
            cucumberTestsCoverageConfiguration.reset();
            cSharpUnitTestsCoverageConfiguration.reset();
            javaUnitTestsCoverageConfiguration.reset();
            outputExtractedRequirementsFile = null;
            outputTraceabilityMatrixFile = null;
            outputVtpFile = null;
        } else {
            File lGitRepoBaseDir = TraceabilityToolController.getInstance().getGitBaseDirectory();
            File lAppDir = new File(lGitRepoBaseDir, pCurrentApplicationData.getGitRelativeDirPath());
            ConfigurationManager lConfMan = ConfigurationManager.getInstance();
            File lVtpDir = new File(lAppDir, lConfMan.getRelativePathToVtpFiles());
            File lTmDir = new File(lAppDir, lConfMan.getRelativePathToTraceabilityMatrixFiles());
            File lErDir = new File(lAppDir, lConfMan.getRelativePathToExtractedRequirementsFiles());
            File lSdDir = new File(lAppDir, lConfMan.getRelativePathToSpecificationDocumentFiles());
            File lSdFile = new File(lSdDir, pCurrentApplicationData.getSdFileName());
            sdFiles.clear();
            sdFiles.add(lSdFile);

            List<String> lReqPrefixes = TraceabilityToolController.getRequirementPrefixes(pCurrentApplicationData
                    .getRequirementPrefixes());
            requirementPrefixes.clear();
            requirementPrefixes.addAll(lReqPrefixes);

            if (pCurrentApplicationData.getJustificationFileName() == null) {
                justificationFile = null;
            } else {
                File lJustificationFileDir = new File(lGitRepoBaseDir, lConfMan.getRelativePathFromGitBaseDirectoryToJustificationFilesDirectory());
                justificationFile = new File(lJustificationFileDir, pCurrentApplicationData.getJustificationFileName());
            }

            if (pCurrentApplicationData.getAlmTestsCoverage().isActive()) {
                almTestsCoverageConfiguration.setActive(true);
                almTestsCoverageConfiguration.configureAlmExtractionFile(lAppDir, pCurrentApplicationData
                        .getAlmTestsCoverage().getAlmExtractedFileName());
            } else {
                almTestsCoverageConfiguration.reset();
            }

            if (pCurrentApplicationData.getCucumberTestsCoverage().isActive()) {
                cucumberTestsCoverageConfiguration.setActive(true);
                try {
                    cucumberTestsCoverageConfiguration.configureFileSelectionFilterFrom(lAppDir,
                            pCurrentApplicationData.getCucumberTestsCoverage().getFileSelectionFiler());
                } catch (InvalidSimpleRegexpException e) {
                    cucumberTestsCoverageConfiguration
                            .setInvalidityDescription("Impossible to set file selection filter : " + e.getMessage());
                }
            } else {
                cucumberTestsCoverageConfiguration.reset();
            }

            if (pCurrentApplicationData.getcSharpUnitTestsCoverage().isActive()) {
                cSharpUnitTestsCoverageConfiguration.setActive(true);
                try {
                    cSharpUnitTestsCoverageConfiguration.configureFileSelectionFilterFrom(lAppDir,
                            pCurrentApplicationData.getcSharpUnitTestsCoverage().getFileSelectionFiler());
                } catch (InvalidSimpleRegexpException e) {
                    cSharpUnitTestsCoverageConfiguration
                            .setInvalidityDescription("Impossible to set file selection filter : " + e.getMessage());
                }

                try {
                    cSharpUnitTestsCoverageConfiguration.configureMethodNameRegexpFrom(pCurrentApplicationData
                            .getcSharpUnitTestsCoverage().getMethodNameSimpleRegexp());
                } catch (InvalidSimpleRegexpException e) {
                    cSharpUnitTestsCoverageConfiguration.setInvalidityDescription("Impossible to set method regexp : "
                            + e.getMessage());
                }
            } else {
                cSharpUnitTestsCoverageConfiguration.reset();
            }

            if (pCurrentApplicationData.getJavaUnitTestsCoverage().isActive()) {
                javaUnitTestsCoverageConfiguration.setActive(true);
                try {
                    javaUnitTestsCoverageConfiguration.configureFileSelectionFilterFrom(lAppDir,
                            pCurrentApplicationData.getJavaUnitTestsCoverage().getFileSelectionFiler());
                } catch (InvalidSimpleRegexpException e) {
                    javaUnitTestsCoverageConfiguration
                            .setInvalidityDescription("Impossible to set file selection filter : " + e.getMessage());
                }

                try {
                    javaUnitTestsCoverageConfiguration.configureMethodNameRegexpFrom(pCurrentApplicationData
                            .getJavaUnitTestsCoverage().getMethodNameSimpleRegexp());
                } catch (InvalidSimpleRegexpException e) {
                    javaUnitTestsCoverageConfiguration.setInvalidityDescription("Impossible to set method regexp : "
                            + e.getMessage());
                }
            } else {
                javaUnitTestsCoverageConfiguration.reset();
            }

            outputExtractedRequirementsFile = new File(lErDir,
                    pCurrentApplicationData.getTargetExtractedRequirementsFileName());
            outputTraceabilityMatrixFile = new File(lTmDir, pCurrentApplicationData.getTargetMatrixFileName());
            outputVtpFile = new File(lVtpDir, pCurrentApplicationData.getTargetVtpFileName());
        }

    }

    /**
     * Getter of the input SD files.
     * 
     * @return the sdFiles
     */
    public List<File> getSdFiles() {
        return sdFiles;
    }

    /**
     * Getter of the requirement prefixes.
     * 
     * @return the requirementPrefixes
     */
    public List<String> getRequirementPrefixes() {
        return requirementPrefixes;
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
     * Getter of the ALM tests coverage configuration.
     * 
     * @return the almTestsCoverageConfiguration
     */
    public AlmTestsCoverageConfiguration getAlmTestsCoverageConfiguration() {
        return almTestsCoverageConfiguration;
    }

    /**
     * Getter of the cucumber tests coverage configuration.
     * 
     * @return the cucumberTestsCoverageConfiguration
     */
    public CucumberTestsCoverageConfiguration getCucumberTestsCoverageConfiguration() {
        return cucumberTestsCoverageConfiguration;
    }

    /**
     * Getter of the C# unit tests coverage configuration.
     * 
     * @return the cSharpUnitTestsCoverageConfiguration
     */
    public CSharpUnitTestsCoverageConfiguration getcSharpUnitTestsCoverageConfiguration() {
        return cSharpUnitTestsCoverageConfiguration;
    }

    /**
     * Getter of the java unit tests coverage configuration.
     * 
     * @return the javaUnitTestsCoverageConfiguration
     */
    public JavaUnitTestsCoverageConfiguration getJavaUnitTestsCoverageConfiguration() {
        return javaUnitTestsCoverageConfiguration;
    }

    /**
     * Getter of the output VTP file.
     * 
     * @return the outputVtpFile
     */
    public File getOutputVtpFile() {
        return outputVtpFile;
    }

    /**
     * Getter of the output extracted requirements file.
     * 
     * @return the outputExtractedRequirementsFile
     */
    public File getOutputExtractedRequirementsFile() {
        return outputExtractedRequirementsFile;
    }

    /**
     * Getter of the output traceability matrix file.
     * 
     * @return the outputTraceabilityMatrixFile
     */
    public File getOutputTraceabilityMatrixFile() {
        return outputTraceabilityMatrixFile;
    }

    /**
     * Setter of the output VTP file.
     * 
     * @param pOutputVtpFile the outputVtpFile to set
     */
    public void setOutputVtpFile(final File pOutputVtpFile) {
        outputVtpFile = pOutputVtpFile;
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
     * Setter of the output extracted requirements file.
     * 
     * @param pOutputExtractedRequirementsFile the
     * outputExtractedRequirementsFile to set
     */
    public void setOutputExtractedRequirementsFile(final File pOutputExtractedRequirementsFile) {
        outputExtractedRequirementsFile = pOutputExtractedRequirementsFile;
    }

    /**
     * Setter of the output traceability matrix file.
     * 
     * @param pOutputTraceabilityMatrixFile the outputTraceabilityMatrixFile to
     * set
     */
    public void setOutputTraceabilityMatrixFile(final File pOutputTraceabilityMatrixFile) {
        outputTraceabilityMatrixFile = pOutputTraceabilityMatrixFile;
    }

    /**
     * Setter of the SD file.
     * 
     * @param pSdFile the SD file to set
     */
    public void setSdFiles(final File pSdFile) {
        sdFiles.clear();
        sdFiles.add(pSdFile);
    }

    /**
     * Setter of the requirement Prefixes.
     * 
     * @param pRequirementPrefixes the requirementPrefixes to set
     */
    public void setRequirementPrefixes(final List<String> pRequirementPrefixes) {
        requirementPrefixes.clear();
        if (pRequirementPrefixes != null) {
            requirementPrefixes.addAll(pRequirementPrefixes);
        }
    }
}
