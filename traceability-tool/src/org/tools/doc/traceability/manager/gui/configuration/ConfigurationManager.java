/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration;

import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.xmlbeans.XmlException;
import org.tools.doc.traceability.common.exceptions.ConfigurationErrorException;
import org.tools.doc.traceability.common.xml.XmlManager;
import org.tools.doc.traceability.manager.gui.GuiConstants;
import org.tools.doc.traceability.manager.gui.configuration.appmodel.ApplicationConfiguration;
import org.tools.doc.traceability.manager.gui.configuration.appmodel.ApplicationConfigurationSection;
import org.tools.doc.traceability.manager.gui.configuration.appmodel.GeneralConfiguration;
import org.tools.doc.traceability.manager.gui.configuration.appmodel.ProjectRelativePathConfiguration;
import org.tools.doc.traceability.manager.gui.configuration.appmodel.TraceablitiyToolsConfiguration;
import org.tools.doc.traceability.manager.gui.data.app.ApplicationData;
import org.tools.doc.traceability.manager.gui.data.app.ApplicationDataList;
import org.tools.doc.traceability.manager.gui.data.tools.data.ToolData;

/**
 * Central point for configuration.
 * <p>
 * As soon as possible in the program, a call to
 * {@link #loadFromConfigurationFile()} shall be done.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public final class ConfigurationManager {

    /**
     * The unique class instance.
     */
    private static ConfigurationManager Instance = null;

    /**
     * Get the unique class instance.
     * 
     * @return the unique class instance.
     */
    public static ConfigurationManager getInstance() {
        if (Instance == null) {
            Instance = new ConfigurationManager();
        }
        return Instance;
    }

    /**
     * The default GIT repository directory.
     */
    private File defaultGitRepositoryDirectory;

    /**
     * The default regexp for the unit tests method names.
     */
    private String defaultUnitTestMethodNameRegexp;

    /**
     * The identifier of the default ApplicationData to select.
     */
    private String defaultApplicationDataId;

    /**
     * The relative path to the directory containing the SD documents (inside a
     * project).
     */
    private String relativePathToSpecificationDocumentFiles;

    /**
     * The relative path to the directory containing the ALM extracted files
     * (inside a project).
     */
    private String relativePathToAlmExtrationFiles;

    /**
     * The relative path to the directory containing the extracted requirements
     * files (inside a project).
     */
    private String relativePathToExtractedRequirementsFiles;

    /**
     * The relative path to the directory containing the VTP documents (inside a
     * project).
     */
    private String relativePathToVtpFiles;

    /**
     * The relative path to the directory containing the Traceability Matrix
     * documents (inside a project).
     */
    private String relativePathToTraceabilityMatrixFiles;

    /**
     * The relative path from the base Git directory to the directory containing
     * justification files.
     */
    private String relativePathFromGitBaseDirectoryToJustificationFilesDirectory;

    /**
     * The tool data corresponding to the default tool.
     */
    private ToolData defaultToolData;

    /**
     * The list of application data.
     */
    private ApplicationDataList applicationDataList;

    /**
     * Constructor.
     */
    private ConfigurationManager() {
        defaultGitRepositoryDirectory = new File("c:\\");
        defaultUnitTestMethodNameRegexp = "";
        defaultApplicationDataId = null;
        defaultToolData = null;
        relativePathToSpecificationDocumentFiles = null;
        relativePathToAlmExtrationFiles = null;
        relativePathToExtractedRequirementsFiles = null;
        relativePathToVtpFiles = null;
        relativePathToTraceabilityMatrixFiles = null;
        relativePathFromGitBaseDirectoryToJustificationFilesDirectory = null;

        applicationDataList = new ApplicationDataList();
    }

    /**
     * Getter of the default GIT repository directory.
     * 
     * @return the defaultGitRepositoryDirectory
     */
    public File getDefaultGitRepositoryDirectory() {
        return defaultGitRepositoryDirectory;
    }

    /**
     * Setter of the default GIT repository directory.
     * 
     * @param pDefaultGitRepositoryDirectory the default GIT repository
     * directory to set (will be set only if exists).
     */
    public void setDefaultGitRepositoryDirectory(final File pDefaultGitRepositoryDirectory) {
        if (pDefaultGitRepositoryDirectory != null) {
            if (pDefaultGitRepositoryDirectory.isDirectory()) {
                defaultGitRepositoryDirectory = pDefaultGitRepositoryDirectory;
            }
        }
    }

    /**
     * Getter of the default ApplicationData identifier.
     * 
     * @return the defaultApplicationDataId or <tt>null</tt> if none is defined.
     */
    public String getDefaultApplicationDataId() {
        return defaultApplicationDataId;
    }

    /**
     * Getter of the default regexp for the unit tests method names.
     * 
     * @return the defaultUnitTestXmlRegexp
     */
    public String getDefaultUnitTestMethodNameRegexp() {
        return defaultUnitTestMethodNameRegexp;
    }

    /**
     * Getter of the default tool data.
     * 
     * @return the defaultToolData
     */
    public ToolData getDefaultToolData() {
        return defaultToolData;
    }

    /**
     * Setter of the default tool data.
     * 
     * @param pDefaultToolData the defaultToolData to set
     */
    public void setDefaultToolData(final ToolData pDefaultToolData) {
        defaultToolData = pDefaultToolData;
    }

    /**
     * Load the configuration data from the configuration file(s).
     * 
     * @throws ConfigurationErrorException if an error occurs.
     */
    public void loadFromConfigurationFile() throws ConfigurationErrorException {
        // Search for the config file
        File lConfigFile = locateConfigurationFile();

        // Try and validate the file against the XSD
        try {
            XmlManager.getInstance().validateXmlAgainstXsd(lConfigFile, GuiConstants.CONFIGRUATION_FILE_XSD_FILE_NAME);
        } catch (IllegalArgumentException | XmlException e) {
            throw new ConfigurationErrorException("Configuration file " + lConfigFile.getAbsolutePath()
                    + " is not valid : " + e.getMessage());
        }

        try {
            // The file is valid. Try and load it
            JAXBContext lJaxbContext = JAXBContext.newInstance(TraceablitiyToolsConfiguration.class);

            Unmarshaller lUnmarshaller = lJaxbContext.createUnmarshaller();

            TraceablitiyToolsConfiguration lTraceablitiyToolsConfiguration = (TraceablitiyToolsConfiguration) lUnmarshaller
                    .unmarshal(lConfigFile);

            if (lTraceablitiyToolsConfiguration.getVersion() != 2) {
                throw new ConfigurationErrorException("The version of the configuration file ("
                        + lTraceablitiyToolsConfiguration.getVersion() + ") is not handled (version 2 expected)");
            }

            // Use the general configuration
            GeneralConfiguration lGeneralConfig = lTraceablitiyToolsConfiguration.getGeneralConfiguration();

            // Get the Git base directory
            String lBaseGitDirectory = lGeneralConfig.getBaseGitDirectory();
            // If it contains the specific tag, replace it with the current
            // execution directory
            if (lBaseGitDirectory.contains(GuiConstants.REPLACEMENT_TAG_FOR_EXECUTION_DIRECTORY)) {
                File lExecutionDir = new File("");
                lBaseGitDirectory = lBaseGitDirectory.replace(GuiConstants.REPLACEMENT_TAG_FOR_EXECUTION_DIRECTORY,
                        lExecutionDir.getAbsolutePath());
            }

            defaultGitRepositoryDirectory = new File(lBaseGitDirectory);
            defaultUnitTestMethodNameRegexp = lGeneralConfig.getUnitTestMethodSimpleRegexp();

            ProjectRelativePathConfiguration lProjectRelativePathConfig = lGeneralConfig
                    .getProjectRelativePathConfiguration();

            relativePathToSpecificationDocumentFiles = lProjectRelativePathConfig
                    .getRelativePathToSpecificationDocumentFiles();
            relativePathToAlmExtrationFiles = lProjectRelativePathConfig.getRelativePathToAlmExtrationFiles();
            relativePathToExtractedRequirementsFiles = lProjectRelativePathConfig
                    .getRelativePathToExtractedRequirementsFiles();
            relativePathToVtpFiles = lProjectRelativePathConfig.getRelativePathToVtpFiles();
            relativePathToTraceabilityMatrixFiles = lProjectRelativePathConfig
                    .getRelativePathToTraceabilityMatrixFiles();

            relativePathFromGitBaseDirectoryToJustificationFilesDirectory = lGeneralConfig
                    .getRelativePathToJustificationFiles();

            ApplicationConfigurationSection lAppConfigSection = lTraceablitiyToolsConfiguration
                    .getApplicationConfigurationSection();

            defaultApplicationDataId = lAppConfigSection.getDefaultApplicationConfigurationIdentifier().trim();

            List<ApplicationConfiguration> lAppConfigList = lAppConfigSection.getApplicationConfigurationList();
            if (lAppConfigList != null) {

                // Iterate on application configuration
                for (ApplicationConfiguration lAppConfig : lAppConfigList) {

                    String lAppId = lAppConfig.getApplicationIdentifier();

                    // Display a warning if we find the same application
                    // configuration more than once
                    if (applicationDataList.containsElementWithApplicationIdentifier(lAppId)) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Found an application configuration with id\"" + lAppId
                                        + "\" that has already been found in the configuration file "
                                        + lConfigFile.getAbsolutePath(), "Warning", JOptionPane.WARNING_MESSAGE);
                    }

                    // Transform the JAXB class to an ApplicaitonData
                    ApplicationData lAppData = lAppConfig.toApplicationData();

                    // Add it in the list
                    applicationDataList.add(lAppData);
                }
            }

            // Check if the defined default application is valid
            if (!defaultApplicationDataId.isEmpty()) {
                ApplicationData lDefaultAppData = applicationDataList
                        .getElementMatchingApplicationIdentifier(defaultApplicationDataId);

                if (lDefaultAppData == null) {
                    JOptionPane.showMessageDialog(null, "The defined default application (" + defaultApplicationDataId
                            + ") does not match any application configuration id in " + lConfigFile.getAbsolutePath(),
                            "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (JAXBException je) {
            throw new ConfigurationErrorException("Error unmarshalling configuration file "
                    + lConfigFile.getAbsolutePath() + " : " + je.toString());
        } catch (IllegalArgumentException iae) {
            throw new ConfigurationErrorException("Error processing configuration file "
                    + lConfigFile.getAbsolutePath() + " : " + iae.toString());
        }
    }

    /**
     * Collect all the application data.
     * 
     * @return the list with all application data.
     */
    public ApplicationDataList getApplicationDataList() {
        return applicationDataList;
    }

    /**
     * Try and locate the configuration file.
     * <p>
     * Searches in The directory mentioned in environment variable
     * {@link GuiConstants#CONFIGURATION_FILE_PATH_ENV_VAR_NAME}.
     * </p>
     * <p>
     * Searched file name is the one mentioned in environment variable
     * {@link GuiConstants#CONFIGURATION_FILE_NAME_ENV_VAR_NAME}, or
     * {@link GuiConstants#DEFAULT_CONFIGURATION_FILE_NAME} if none is
     * specified.
     * </p>
     * 
     * @return the configuration file, or <tt>null</tt> if not found.
     * @throws ConfigurationErrorException if the file could not be found
     */
    private File locateConfigurationFile() throws ConfigurationErrorException {

        // Try to get the file name from theenvironment variable
        String lConfigFileName;
        String lFileNameEnvVarValue = System.getProperty(GuiConstants.CONFIGURATION_FILE_NAME_ENV_VAR_NAME);
        if (lFileNameEnvVarValue == null) {
            // Environment variable is not set, use the default value
            lConfigFileName = GuiConstants.DEFAULT_CONFIGURATION_FILE_NAME;
        } else {
            // Else use the value specified through the environment variable, if
            // valid
            if (lFileNameEnvVarValue.trim().isEmpty()) {
                throw new ConfigurationErrorException("The environment variable "
                        + GuiConstants.CONFIGURATION_FILE_NAME_ENV_VAR_NAME
                        + " is present but empty. Please remove it or set it to a not empty value.");
            }
            lConfigFileName = lFileNameEnvVarValue;
        }

        File lConfigFile = null;

        // Get the path from the environment variable
        String lEnvVarValue = System.getProperty(GuiConstants.CONFIGURATION_FILE_PATH_ENV_VAR_NAME);

        if (lEnvVarValue == null) {
            throw new ConfigurationErrorException("The environment variable "
                    + GuiConstants.CONFIGURATION_FILE_PATH_ENV_VAR_NAME
                    + " is not set. Please set it to refer to the directory "
                    + "where the configuration file can be found.");
        } else {
            File lEnvVarDefinedDir = new File(lEnvVarValue);
            if (lEnvVarDefinedDir.isDirectory()) {
                lConfigFile = new File(lEnvVarDefinedDir, lConfigFileName);
                if (!lConfigFile.isFile()) {
                    throw new ConfigurationErrorException("The environment variable "
                            + GuiConstants.CONFIGURATION_FILE_PATH_ENV_VAR_NAME + " ("
                            + lEnvVarDefinedDir.getAbsolutePath() + ") refers to a directory that do not contain file "
                            + lConfigFileName);
                }
            } else {
                throw new ConfigurationErrorException("The environment variable "
                        + GuiConstants.CONFIGURATION_FILE_PATH_ENV_VAR_NAME + " ("
                        + lEnvVarDefinedDir.getAbsolutePath() + ") refers to a not existing directory.");
            }
        }

        return lConfigFile;
    }

    /**
     * Getter of the relative path to the directory containing the SD documents
     * (inside a project).
     * 
     * @return the relativePathToSpecificationDocumentFiles
     */
    public String getRelativePathToSpecificationDocumentFiles() {
        return relativePathToSpecificationDocumentFiles;
    }

    /**
     * Getter of the relative path to the directory containing the ALM extracted
     * files (inside a project).
     * 
     * @return the relativePathToAlmExtrationFiles
     */
    public String getRelativePathToAlmExtrationFiles() {
        return relativePathToAlmExtrationFiles;
    }

    /**
     * Getter of the relative path to the directory containing the extracted
     * requirements files (inside a project).
     * 
     * @return the relativePathToExtractedRequirementsFiles
     */
    public String getRelativePathToExtractedRequirementsFiles() {
        return relativePathToExtractedRequirementsFiles;
    }

    /**
     * Getter of the relative path to the directory containing the VTP documents
     * (inside a project).
     * 
     * @return the relativePathToVtpFiles
     */
    public String getRelativePathToVtpFiles() {
        return relativePathToVtpFiles;
    }

    /**
     * Getter of the relative path to the directory containing the Traceability
     * Matrix documents (inside a project).
     * 
     * @return the relativePathToTraceabilityMatrixFiles
     */
    public String getRelativePathToTraceabilityMatrixFiles() {
        return relativePathToTraceabilityMatrixFiles;
    }

    /**
     * Getter of the relative path from Git base directory to justification
     * files directory.
     * 
     * @return the relativePathFromGitBaseDirectoryToJustificationFilesDirectory
     */
    public String getRelativePathFromGitBaseDirectoryToJustificationFilesDirectory() {
        return relativePathFromGitBaseDirectoryToJustificationFilesDirectory;
    }
}
