/**
 * 
 */
package org.tools.doc.traceability.manager.gui.controller;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.tools.doc.traceability.common.exceptions.ConfigurationErrorException;
import org.tools.doc.traceability.common.exceptions.InvalidConfigurationException;
import org.tools.doc.traceability.common.exceptions.InvalidFileSearchFilterException;
import org.tools.doc.traceability.common.executor.ExecutionStatus;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.filesearch.FileSearchFilter;
import org.tools.doc.traceability.common.model.Requirement;
import org.tools.doc.traceability.common.ui.GuiAction;
import org.tools.doc.traceability.covmatrixgen.model.RequirementTestCovering;
import org.tools.doc.traceability.manager.gui.GuiConstants;
import org.tools.doc.traceability.manager.gui.configuration.ConfigurationManager;
import org.tools.doc.traceability.manager.gui.data.app.ApplicationData;
import org.tools.doc.traceability.manager.gui.data.tools.config.AlmTestsCoverageConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.config.CSharpUnitTestsCoverageConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.config.CucumberTestsCoverageConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.config.FileSelectionFilter;
import org.tools.doc.traceability.manager.gui.data.tools.config.ICheckableToolConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.config.JavaUnitTestsCoverageConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.config.TraceabilityManagerToolConfiguration;
import org.tools.doc.traceability.manager.gui.data.tools.data.ToolData;
import org.tools.doc.traceability.manager.gui.data.tools.execution.EndedSuccessfullyExecutionStatus;
import org.tools.doc.traceability.manager.gui.data.tools.execution.EndeddWithErrorExecutionStatus;
import org.tools.doc.traceability.manager.gui.data.tools.execution.PendingExecutionStatus;
import org.tools.doc.traceability.manager.gui.frame.ToolExecutionFrame;
import org.tools.doc.traceability.manager.gui.frame.TraceabilityToolFrame;
import org.tools.doc.traceability.manager.gui.manager.data.DataManager;
import org.tools.doc.traceability.manager.gui.panels.MainPanel;
import org.tools.doc.traceability.manager.processor.TraceabilityManager;
import org.tools.doc.traceability.manager.processor.TraceabilityManagerContext;
import org.tools.doc.traceability.manager.processor.TraceabilityManagerResultObject;
import org.tools.doc.traceability.reqextraction.helper.RequirementDuplicationItem;

/**
 * The controller of the GUI tool.
 * 
 * @author Yann Leglise
 *
 */
public final class TraceabilityToolController implements ActionListener {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LogManager.getLogger(TraceabilityToolController.class);

    /**
     * The unique class instance.
     */
    private static TraceabilityToolController Instance = null;

    /**
     * Getter of the unique class instance.
     * 
     * @return the unique class instance.
     */
    public static synchronized TraceabilityToolController getInstance() {
        if (Instance == null) {
            Instance = new TraceabilityToolController();
        }

        return Instance;
    }

    /**
     * Convert a string describing the requirement prefixes separated by commas
     * to a list of requirement prefixes.
     * 
     * @param pRequirementPrefixesInLine a string describing the requirement
     * prefixes separated by commas.
     * @return the list of requirement prefixes.
     */
    public static List<String> getRequirementPrefixes(final String pRequirementPrefixesInLine) {
        List<String> lReqPrefixeList = new ArrayList<String>();
        String[] lReqPrefixes = pRequirementPrefixesInLine.split(",");

        for (String lReqPrefix : lReqPrefixes) {
            lReqPrefixeList.add(lReqPrefix.trim());
        }

        return lReqPrefixeList;
    }

    /**
     * The Frame for the GUI.
     */
    private TraceabilityToolFrame toolFrame;

    /**
     * The frame for the progress dialog.
     */
    private ToolExecutionFrame toolProgressDialog;

    /**
     * The action for the selecting the GIT base directory.
     */
    private GuiAction selectGitBaseDirectoryAction;

    /**
     * The action for the exit button.
     */
    private GuiAction exitAction;

    /**
     * The action for launching the process.
     */
    private GuiAction launchProcessAction;

    /**
     * The action associated with the close button of the execution panel.
     */
    private GuiAction closeExecutionFrameAction;

    /**
     * The directory chooser.
     */
    private JFileChooser directoryChooser;

    /**
     * The current state of the GUI application.
     */
    private final TraceabilityToolState toolGuiConfiguration;

    /**
     * The configuration for the Coverage Matrix Generator tool.
     */
    private TraceabilityManagerToolConfiguration coverageMatrixGeneratorToolConfiguration;

    /**
     * The timer to update during tool work.
     */
    private Timer updateTimer;

    /**
     * The thread where the execution tool runs.
     */
    private Thread toolExecutionThread;

    /**
     * The Traceability Manager processor.
     */
    private TraceabilityManager traceabilityManager;

    /**
     * The execution result for the Coverage Matrix Generator.
     */
    private ExecutorExecutionStatus<TraceabilityManagerResultObject> traceabilityManagerResult;

    /**
     * Flag used to ignore notifications from changed widget, used during
     * initialization.
     */
    private boolean ignoreNotifications;

    /**
     * Constructor.
     */
    private TraceabilityToolController() {

        toolGuiConfiguration = new TraceabilityToolState();

        // Configure the UI
        configureUi();

        exitAction = new GuiAction("Quit", "Quit the application", KeyEvent.VK_Q);

        launchProcessAction = new GuiAction("Start", "Start the selected tool on the selected application",
                KeyEvent.VK_S);

        selectGitBaseDirectoryAction = new GuiAction("Choose", "Choose the base directory for GIT repository on disk",
                KeyEvent.VK_G);

        closeExecutionFrameAction = new GuiAction("Close", "Close the execution dialog", KeyEvent.VK_C);

        directoryChooser = null;

        toolFrame = null;
        toolProgressDialog = null;

        coverageMatrixGeneratorToolConfiguration = new TraceabilityManagerToolConfiguration();

        updateTimer = new Timer(500, this);
        toolExecutionThread = null;
        traceabilityManager = null;
        traceabilityManagerResult = null;

        ignoreNotifications = false;
    }

    /**
     * Configure the GUI.
     */
    private void configureUi() {
        UIManager.put("Panel.background", GuiConstants.PANEL_BACKGROUND_COLOR);
        UIManager.put("Button.background", GuiConstants.BUTTON_BACKGROUND_COLOR);
        UIManager.put("Button.foreground", GuiConstants.BUTTON_FOREGROUND_COLOR);
        UIManager.put("RadioButton.background", GuiConstants.PANEL_BACKGROUND_COLOR);
        UIManager.put("CheckBox.background", GuiConstants.PANEL_BACKGROUND_COLOR);
        UIManager.put("Viewport.background", GuiConstants.PANEL_BACKGROUND_COLOR);

        UIManager.put("ProgressBar.background", GuiConstants.PROGRESS_BAR_BACKGROUND_COLOR);
        UIManager.put("ProgressBar.foreground", GuiConstants.PROGRESS_BAR_FOREGROUND_COLOR);
        UIManager.put("ProgressBar.selectionBackground", GuiConstants.PROGRESS_BAR_BACKGROUND_COLOR);
        UIManager.put("ProgressBar.selectionForeground", GuiConstants.PROGRESS_BAR_FOREGROUND_COLOR);
    }

    /**
     * Start the GUI.
     */
    public void startApplication() {

        // Initialize log4j
        initializeLog4j();

        // Load configuration file
        try {
            ConfigurationManager.getInstance().loadFromConfigurationFile();

            // Set the Coverage Matrix Generator as default tool
            ConfigurationManager.getInstance().setDefaultToolData(
                    DataManager.getInstance().getCoverageMatrixGeneratorToolData());

            // Create the frame is not existing yet
            if (toolFrame == null) {
                toolFrame = new TraceabilityToolFrame();
            }

            // Create the progress dialog if not existing yet
            if (toolProgressDialog == null) {
                toolProgressDialog = new ToolExecutionFrame(toolFrame, closeExecutionFrameAction);
            }

            // Make the main frame visible
            toolFrame.setVisible(true);

            // Program a timer to update the GUI
            java.util.Timer lTimer = new java.util.Timer();
            lTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            initializeGui();
                        }
                    });
                }
            }, 200);

        } catch (ConfigurationErrorException cee) {
            System.err.println("Error launching tool : " + cee.getMessage());
            JOptionPane.showMessageDialog(null, "Error launching tool : " + cee.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(-1);
        }
    }

    /**
     * Initialize log4j properly.
     */
    private void initializeLog4j() {
        // Try and find the log4j.xml though class loader
        URL lLog4jConfigFileUrl = getClass().getClassLoader().getResource(
                org.tools.doc.traceability.common.Constants.LOGGING_CONFIGURATION_FILENAME);

        if (lLog4jConfigFileUrl == null) {
            System.err.println("Could not find "
                    + org.tools.doc.traceability.common.Constants.LOGGING_CONFIGURATION_FILENAME + " in classpath.");
        } else {
            FileInputStream lLog4JFis = null;

            try {
                lLog4JFis = new FileInputStream(lLog4jConfigFileUrl.getFile());
                ConfigurationSource source = new ConfigurationSource(lLog4JFis);
                Configurator.initialize(getClass().getClassLoader(), source);
                LOGGER.info("Logging configuration successful");
            } catch (FileNotFoundException fnfe) {
                System.err.println("Could not find " + lLog4jConfigFileUrl + " : " + fnfe.getMessage());
            } catch (IOException ioe) {
                System.err.println("Error reading " + lLog4jConfigFileUrl + " : " + ioe.getMessage());
            } finally {
                if (lLog4JFis != null) {
                    try {
                        lLog4JFis.close();
                    } catch (IOException e) {
                        LOGGER.error("Could not close input stream on " + lLog4jConfigFileUrl + " : " + e.getMessage());
                    }
                }
            }
        }

        LOGGER.debug("Starting application");
    }

    /**
     * Method called after launching the application to initialize the GUI.
     */
    private void initializeGui() {

        // Resize and center the frame
        resizeAndPositionFrame(toolFrame);

        // Initialize the current state
        toolGuiConfiguration.makeNewStateCurrent();

        // Ignore the notification while the widgets are being initialized
        ignoreNotifications = true;

        // Set the default GIT base directory
        File lGitBaseDir = ConfigurationManager.getInstance().getDefaultGitRepositoryDirectory();
        toolGuiConfiguration.setNewlySetGitBaseDirectory(lGitBaseDir);
        toolFrame.getMainPanel().getConfigurationPanel().setGitBaseRepositoryDirectory(lGitBaseDir);

        // Select the default application
        ApplicationData lAppDataToSelect = null;

        String lDefaultAppDataId = DataManager.getInstance().getDefaultApplicationDataId();

        if (lDefaultAppDataId != null) {
            lAppDataToSelect = DataManager.getInstance().getApplicationDataList()
                    .getElementMatchingApplicationIdentifier(lDefaultAppDataId);
        }
        if (lAppDataToSelect == null) {
            lAppDataToSelect = DataManager.getInstance().getApplicationDataList().getFirst();
        }
        toolGuiConfiguration.setNewlySelectedApplicationData(lAppDataToSelect);
        toolFrame.getMainPanel().getApplicationChoicePanel().setApplicationSelected(lAppDataToSelect);

        // Select the default tool
        ToolData lToolDataToSelect = ConfigurationManager.getInstance().getDefaultToolData();
        toolGuiConfiguration.setNewlySelectedToolData(lToolDataToSelect);
        toolFrame.getMainPanel().getToolChoicePanel().setToolSelected(lToolDataToSelect);

        // Make notification handling active again
        ignoreNotifications = false;

        // Handle the changes in the configuration
        handleChangedToolGuiConfiguration();
    }

    /**
     * Resize the given frame, and center it on the screen where the mouse
     * cursor currently is.
     * 
     * @param pFrame the frame to resize and position.
     */
    private void resizeAndPositionFrame(final JFrame pFrame) {
        // Center the application on the screen where the mouse currently is
        GraphicsEnvironment lGe = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] lGraphicDevices = lGe.getScreenDevices();

        Rectangle lActiveBounds = null;
        Point lMousePosition = MouseInfo.getPointerInfo().getLocation();
        for (GraphicsDevice lGd : lGraphicDevices) {
            GraphicsConfiguration[] lConfigurations = lGd.getConfigurations();
            for (GraphicsConfiguration lConfig : lConfigurations) {
                Rectangle lConfigBounds = lConfig.getBounds();

                if (lConfigBounds.contains(lMousePosition)) {
                    lActiveBounds = lConfigBounds;
                    break;
                }
            }
        }

        if (lActiveBounds == null) {
            lActiveBounds = lGe.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        }
        Point lScreenCenter = new Point(lActiveBounds.x + (lActiveBounds.width) / 2, lActiveBounds.y
                + (lActiveBounds.height) / 2);

        int lWidth = (int) (lActiveBounds.width - 2 * GuiConstants.FRAME_BORDER_TO_SCREEN_MARGIN);
        int lHeight = (int) (lActiveBounds.height - 2 * GuiConstants.FRAME_BORDER_TO_SCREEN_MARGIN);

        // Set the window be a percentage the size of the screen
        Dimension lFrameDimenson = new Dimension(lWidth, lHeight);

        pFrame.setSize(lFrameDimenson);

        // Use the actual size, as maybe the given dimensions couldn't be set
        // because of constraints
        lFrameDimenson = pFrame.getSize();

        toolFrame.setLocation(lScreenCenter.x - (int) (lFrameDimenson.width / 2), lScreenCenter.y
                - (int) (lFrameDimenson.height / 2));
    }

    /**
     * Get the directory chooser.
     * 
     * @return the directory chooser.
     */
    private JFileChooser getDirectoryChooser() {
        if (directoryChooser == null) {
            directoryChooser = new JFileChooser();
            directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            directoryChooser.setAcceptAllFileFilterUsed(false);
        }
        return directoryChooser;
    }

    /**
     * Getter of the GIT repository directory chooser.
     * 
     * @return the GIT repository directory chooser.
     */
    private JFileChooser getGitRepositoryDirectoryChooser() {
        directoryChooser = getDirectoryChooser();

        directoryChooser.setToolTipText("Select the path of the GIT repository base directory containing the projects");
        directoryChooser.setDialogTitle("Select GIT repository base directory");
        directoryChooser.setCurrentDirectory(ConfigurationManager.getInstance().getDefaultGitRepositoryDirectory());

        return directoryChooser;
    }

    /**
     * Handle an action.
     * 
     * @param pActionIdentifier the action identifier.
     */
    public void handleAction(final long pActionIdentifier) {
        if (pActionIdentifier == exitAction.getActionIdentifier()) {
            System.exit(0);
        } else if (pActionIdentifier == selectGitBaseDirectoryAction.getActionIdentifier()) {
            doSelectGitBaseDirectory();
        } else if (pActionIdentifier == launchProcessAction.getActionIdentifier()) {
            doLaunchApplication();
        } else if (pActionIdentifier == closeExecutionFrameAction.getActionIdentifier()) {
            // Hide the progress frame
            toolProgressDialog.setVisible(false);
        }
    }

    /**
     * Called when the launch application button is clicked.
     */
    private void doLaunchApplication() {
        if (toolGuiConfiguration.selectedToolMatches(DataManager.getInstance().getCoverageMatrixGeneratorToolData())) {
            launchTraceabilityManagerTool();
        }
    }

    /**
     * Launch the Traceability Manager tool.
     */
    private void launchTraceabilityManagerTool() {
        // Initialize the executor execution status
        traceabilityManagerResult = new ExecutorExecutionStatus<TraceabilityManagerResultObject>();

        TraceabilityManagerContext lContext;
        try {
            lContext = createTraceabilityManagerContext();

            // Create the executor
            traceabilityManager = new TraceabilityManager(lContext, traceabilityManagerResult);

            // Launch the tool
            launchTool(traceabilityManager, DataManager.getInstance().getCoverageMatrixGeneratorToolData());
        } catch (InvalidConfigurationException e) {
            JOptionPane.showMessageDialog(toolFrame,
                    "Could not perform the task : error creating the context (" + e.getMessage() + ")", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Create a context for the execution of the traceability manager.
     * 
     * @return context associated with the current configuration.
     * @throws InvalidConfigurationException if an error occurred while creating
     * the context
     */
    private TraceabilityManagerContext createTraceabilityManagerContext() throws InvalidConfigurationException {

        TraceabilityManagerContext lContext = new TraceabilityManagerContext();

        for (File lSdFile : coverageMatrixGeneratorToolConfiguration.getSdFiles()) {
            lContext.addSpecificationDossier(lSdFile);
        }

        // Requirement prefixes
        for (String lReqPrefix : coverageMatrixGeneratorToolConfiguration.getRequirementPrefixes()) {
            lContext.addRequirementPrefix(lReqPrefix);
        }

        // Justification file
        lContext.setJustificationFile(coverageMatrixGeneratorToolConfiguration.getJustificationFile());

        // Output extracted requirement file
        lContext.setOutputExtractedRequirementsFile(coverageMatrixGeneratorToolConfiguration
                .getOutputExtractedRequirementsFile());

        // Output traceability matrix file
        lContext.setOutputTraceabilityMatrixFile(coverageMatrixGeneratorToolConfiguration
                .getOutputTraceabilityMatrixFile());

        // Output VTP file
        lContext.setOutputVtpFile(coverageMatrixGeneratorToolConfiguration.getOutputVtpFile());

        // ALM tests
        AlmTestsCoverageConfiguration lAlmConfig = coverageMatrixGeneratorToolConfiguration
                .getAlmTestsCoverageConfiguration();
        if (lAlmConfig.isActive()) {
            lContext.setAlmTestsExtractFile(lAlmConfig.getAlmExtractionFile());
        }

        // Cucumber tests
        CucumberTestsCoverageConfiguration lCucumberConfig = coverageMatrixGeneratorToolConfiguration
                .getCucumberTestsCoverageConfiguration();
        if (lCucumberConfig.isActive()) {
            FileSearchFilter lCucumberFileSearchFilter = createFileSearchFilterFrom(lCucumberConfig
                    .getFileSelectionFilter());

            try {
                lContext.addCucumberFileSearchFilter(lCucumberFileSearchFilter);
            } catch (InvalidFileSearchFilterException e) {
                throw new InvalidConfigurationException("Error creating cucumber file search filter : "
                        + e.getMessage());
            }
        }

        // C# unit tests
        CSharpUnitTestsCoverageConfiguration lCSharpConfig = coverageMatrixGeneratorToolConfiguration
                .getcSharpUnitTestsCoverageConfiguration();

        if (lCSharpConfig.isActive()) {
            FileSearchFilter lCSharpFileSearchFilter = createFileSearchFilterFrom(lCSharpConfig
                    .getFileSelectionFilter());
            try {
                lContext.addCSharpFileSearchFilter(lCSharpFileSearchFilter);
            } catch (InvalidFileSearchFilterException e) {
                throw new InvalidConfigurationException("Error creating C# file search filter : " + e.getMessage());
            }
            lContext.setCSharpMethodRegexp(lCSharpConfig.getMethodNameSimpleRegexp());
        }

        // Java unit tests
        JavaUnitTestsCoverageConfiguration lJavaConfig = coverageMatrixGeneratorToolConfiguration
                .getJavaUnitTestsCoverageConfiguration();

        if (lJavaConfig.isActive()) {
            FileSearchFilter lJavaFileSearchFilter = createFileSearchFilterFrom(lJavaConfig.getFileSelectionFilter());

            try {
                lContext.addJavaFileSearchFilter(lJavaFileSearchFilter);
            } catch (InvalidFileSearchFilterException e) {
                throw new InvalidConfigurationException("Error creating java file search filter : " + e.getMessage());
            }
            lContext.setJavaMethodRegexp(lJavaConfig.getMethodNameSimpleRegexp());
        }

        return lContext;
    }

    /**
     * Create a FileSearchFilter from the given parameter.
     * 
     * @param pFileSelectionFilter the file selection filter.
     * @return the {@link FileSearchFilter} created from the given filter.
     */
    private FileSearchFilter createFileSearchFilterFrom(final FileSelectionFilter pFileSelectionFilter) {
        FileSearchFilter lFileSearchFilter = new FileSearchFilter(pFileSelectionFilter.getRootDirectory(),
                pFileSelectionFilter.isSearchRecursivelyInSubDirectories(),
                pFileSelectionFilter.getFileNameSimpleRegexp());

        return lFileSearchFilter;
    }

    /**
     * Launch the given tool and display the progress dialog.
     * 
     * @param pExecutor the tool executor.
     * @param pToolData the tool data.
     */
    private void launchTool(final Runnable pExecutor, final ToolData pToolData) {

        // Run it in a different thread
        toolExecutionThread = new Thread(pExecutor);

        try {
            toolExecutionThread.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(toolFrame, "Could not perform the task : " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Prevent closing the execution dialog through the close button
        closeExecutionFrameAction.setEnabled(false);

        // Empty the previous result, if any
        toolProgressDialog.setExecutionResult("");
        // Show the progress frame
        toolProgressDialog.setVisible(true);
        // Update the title
        toolProgressDialog.setTitle("Executing " + pToolData.getDisplayName() + " for "
                + toolGuiConfiguration.getCurrentlySelectedApplicationData().getDisplayName());
        toolProgressDialog.setExecutionStatus(new PendingExecutionStatus());

        // Start the update timer
        // This will call method actionPerformed method regularly
        updateTimer = new Timer(100, this);
        updateTimer.setInitialDelay(100);
        updateTimer.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(final ActionEvent pE) {
        if (pE.getSource() == updateTimer) {
            // Called by the update timer.
            // Depending on which tool is set, handle the relevant event
            if (traceabilityManager != null) {
                handleTraceabilityManagerExecution();
            }
        }
    }

    /**
     * Handles the execution of the Traceability Manager tool.
     */
    private void handleTraceabilityManagerExecution() {

        if (toolExecutionThread.isAlive()) {
            // The tool is still in execution
            if (traceabilityManagerResult != null) {
                if (traceabilityManagerResult.getCurrentExecutionStatus() == ExecutionStatus.PENDING) {
                    // Update
                    toolProgressDialog.updateFrom(traceabilityManagerResult);
                } else if ((traceabilityManagerResult.getCurrentExecutionStatus() == ExecutionStatus.ENDED_SUCCESS)
                        || (traceabilityManagerResult.getCurrentExecutionStatus() == ExecutionStatus.ENDED_WITH_ERROR)) {
                    traceabilityManager = null;

                    // Join
                    try {
                        toolExecutionThread.join();

                    } catch (InterruptedException e) {
                        // Error
                        JOptionPane.showMessageDialog(toolFrame, "Could not stop the thread", "Status",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } else {
            // Stop the timer
            updateTimer.stop();

            TraceabilityManagerResultObject lTraceabilityManagerResult = traceabilityManagerResult.getExecutionResult();

            traceabilityManagerResult.setCurrentOperation("Finished", 100);
            toolProgressDialog.updateFrom(traceabilityManagerResult);

            if (lTraceabilityManagerResult == null) {
                // No result obtained.
                // Error
                toolProgressDialog.setExecutionStatus(new EndeddWithErrorExecutionStatus("No result obtained"));
                toolProgressDialog.setExecutionResult(traceabilityManagerResult.getExecutionStatusDescription());
            } else {
                if (traceabilityManagerResult.getCurrentExecutionStatus() == ExecutionStatus.ENDED_SUCCESS) {

                    // Success
                    String lStatus = buildStatus(lTraceabilityManagerResult);

                    toolProgressDialog.setExecutionStatus(new EndedSuccessfullyExecutionStatus(null));
                    toolProgressDialog.setExecutionResult(lStatus);
                } else if (traceabilityManagerResult.getCurrentExecutionStatus() == ExecutionStatus.ENDED_WITH_ERROR) {
                    // Error
                    toolProgressDialog.setExecutionStatus(new EndeddWithErrorExecutionStatus(null));
                    toolProgressDialog.setExecutionResult(traceabilityManagerResult.getExecutionStatusDescription());
                }
            }

            // Allow to close
            closeExecutionFrameAction.setEnabled(true);
        }
    }

    /**
     * Builds an HTML report about the requirement coverage in case the
     * execution went well.
     * <p>
     * It indicates the potentially not covered requirements, or the
     * requirements that were found in double in the SD document.
     * </p>
     * 
     * @param pTraceabilityManagerResult the Traceability Manager result in
     * success.
     * @return the HTML report to display.
     */
    private String buildStatus(final TraceabilityManagerResultObject pTraceabilityManagerResult) {

        // Success

        final String lWarning = "<font color=\"red\"><b>Warning</b></font>";

        List<Requirement> lSdReqList = pTraceabilityManagerResult.getAllSdRequirements();
        int lNbReqs = lSdReqList.size();

        List<Requirement> lNotCoveredReqList = pTraceabilityManagerResult.getNotCoveredRequirementList();

        StringBuilder lNotCoveredRequirementListSb = null;
        int lNbNotCoveredReqs = lNotCoveredReqList.size();
        if (lNbNotCoveredReqs > 0) {
            lNotCoveredRequirementListSb = new StringBuilder();
            lNotCoveredRequirementListSb.append("<ol>");
            for (Requirement lReq : lNotCoveredReqList) {
                lNotCoveredRequirementListSb.append("<li><tt><b>");
                lNotCoveredRequirementListSb.append(lReq.toString());
                lNotCoveredRequirementListSb.append("</b></tt>");
            }
            lNotCoveredRequirementListSb.append("</ol>");
        }

        // Handle the possible requirement duplication found in SD
        // file
        List<RequirementDuplicationItem> lDuplicatedRequirementItems = pTraceabilityManagerResult
                .getDuplicatedRequirements();
        StringBuilder lDuplicateReqSb = null;
        int lNbDuplicatedReqs = lDuplicatedRequirementItems.size();
        if (lNbDuplicatedReqs > 0) {
            lDuplicateReqSb = new StringBuilder();
            lDuplicateReqSb.append("<p><ul>");
            for (RequirementDuplicationItem lRequirementDuplicationItem : lDuplicatedRequirementItems) {
                lDuplicateReqSb.append("<li> <tt><b>" + lRequirementDuplicationItem.getRequirement() + "</b></tt> : "
                        + lRequirementDuplicationItem.getDuplicationDescription());
            }
            lDuplicateReqSb.append("</ul></p>");
        }

        // Build the full status
        StringBuilder lStatusSb = new StringBuilder();
        lStatusSb.append("<html>");

        if (lNbReqs == 0) {
            lStatusSb.append("<br>");
            lStatusSb.append(lWarning);
            lStatusSb.append(": <font color=\"red\">No requirement found!</font>");
        } else {
            lStatusSb.append("Coverage matrix successfully updated with <b>");
            lStatusSb.append(lNbReqs);
            lStatusSb.append("</b> requirement");
            if (lNbReqs > 1) {
                lStatusSb.append("s");
            }
            lStatusSb.append(" from the SD.");

            RequirementTestCovering lReqCovering = pTraceabilityManagerResult.getRequirementTestCovering();
            int lNbCoveringTestCases = lReqCovering.computeTotalTestCaseNumber();

            lStatusSb.append("<br>VTP file successfully updated with ");
            lStatusSb.append(lNbCoveringTestCases);
            lStatusSb.append(" test cases covering the requirements.");

            if (lNbNotCoveredReqs > 0) {
                lStatusSb.append("<br>");
                lStatusSb.append(lWarning);
                lStatusSb.append(": <font color=\"red\">");
                lStatusSb.append(lNbNotCoveredReqs);
                lStatusSb.append(" requirement");
                if (lNbNotCoveredReqs > 1) {
                    lStatusSb.append("s are");
                } else {
                    lStatusSb.append(" is");
                }
                lStatusSb.append(" not covered :</font>");
                lStatusSb.append(lNotCoveredRequirementListSb.toString());
            } else {
                lStatusSb.append("<br><font color=\"#009900\"><b>All requirements are covered</b></font>");
            }

            if (lNbDuplicatedReqs > 0) {
                lStatusSb.append("<br>");
                lStatusSb.append(lWarning);
                lStatusSb.append(": <font color=\"red\">");
                lStatusSb.append(lNbDuplicatedReqs);
                lStatusSb.append(" requirement");
                if (lNbNotCoveredReqs > 1) {
                    lStatusSb.append("s are");
                } else {
                    lStatusSb.append(" is");
                }
                lStatusSb.append(" duplicated :</font>");
                lStatusSb.append(lDuplicateReqSb.toString());
            }
        }

        lStatusSb.append("</html>");

        return lStatusSb.toString();
    }

    /**
     * Called when the radio button corresponding to an application is selected.
     * 
     * @param pApplicationData the associated application data (null if none).
     */
    public void handleApplicationSelection(final ApplicationData pApplicationData) {
        if (!ignoreNotifications) {
            toolGuiConfiguration.setNewlySelectedApplicationData(pApplicationData);
            handleChangedToolGuiConfiguration();
        }
    }

    /**
     * Handle a change of the current configuration.
     */
    private void handleChangedToolGuiConfiguration() {

        ignoreNotifications = true;

        boolean lGitBaseDirChanged = toolGuiConfiguration.didGitBaseDirectoryChange();
        boolean lAppDataChanged = toolGuiConfiguration.didSelectedApplicationChange();
        boolean lToolDataChanged = toolGuiConfiguration.didSelectedToolChange();

        toolGuiConfiguration.makeNewStateCurrent();

        MainPanel lMainPanel = toolFrame.getMainPanel();

        if (lGitBaseDirChanged) {
            // Update the application availability
            DataManager.getInstance().updateApplicationAvailability(
                    toolGuiConfiguration.getCurrentlySetGitBaseDirectory());
            // Update the radio buttons of the applications accordingly
            lMainPanel.getApplicationChoicePanel().updateApplicationChoiceAvailability();
        }

        // Check if the currently selected app didn't become unavailable
        ApplicationData lCurrentlySelectedAppData = toolGuiConfiguration.getCurrentlySelectedApplicationData();
        if (lCurrentlySelectedAppData != null) {
            if (!lCurrentlySelectedAppData.isAvailable()) {
                // Clear any application selection
                lMainPanel.getApplicationChoicePanel().clearApplicationSelection();
                toolGuiConfiguration.setNewlySelectedApplicationData(null);
                toolGuiConfiguration.makeNewStateCurrent();
                lAppDataChanged = true;
            }
        } else {
            // There is no application currently selected : try and select one
            // if possible (the default one, or the first available one)
            ApplicationData lSelectedAppData = lMainPanel.getApplicationChoicePanel()
                    .tryAndSelectDefaultOrFirstApplication();

            toolGuiConfiguration.setNewlySelectedApplicationData(lSelectedAppData);
            toolGuiConfiguration.makeNewStateCurrent();
            lAppDataChanged = true;
        }

        if (!toolGuiConfiguration.isApplicationSelected()) {
            // If not at least one application is selected, simulate no selected
            // tool
            lMainPanel.getToolChoicePanel().setToolSelected(null);
            toolGuiConfiguration.setNewlySelectedToolData(null);
            toolGuiConfiguration.makeNewStateCurrent();
            lToolDataChanged = true;
        } else {
            // If one application is selected, make sure one tool is selected
            if (!toolGuiConfiguration.isToolSelected()) {
                // TODO see for genericity
                ToolData lToolData = DataManager.getInstance().getCoverageMatrixGeneratorToolData();
                lMainPanel.getToolChoicePanel().setToolSelected(lToolData);
                toolGuiConfiguration.setNewlySelectedToolData(lToolData);
                toolGuiConfiguration.makeNewStateCurrent();
                lToolDataChanged = true;
            }
        }

        if (lToolDataChanged) {
            lMainPanel.displayConfigurationPanelFor(toolGuiConfiguration.getCurrentlySelectedToolData());
            lMainPanel.getToolChoicePanel().setVisibleRadioButtonsEnabled(toolGuiConfiguration.isApplicationSelected());
        }

        if (lAppDataChanged) {
            if (toolGuiConfiguration
                    .selectedToolMatches(DataManager.getInstance().getCoverageMatrixGeneratorToolData())) {
                coverageMatrixGeneratorToolConfiguration.setCurrentApplicationData(toolGuiConfiguration
                        .getCurrentlySelectedApplicationData());
                lMainPanel.getCoverageMatrixGeneratorToolConfigurationPanel().updateFromToolConfiguration();
            }
        }

        updateLaunchingStatus();

        ignoreNotifications = false;
    }

    /**
     * Called when the radio button corresponding to a tool is selected.
     * 
     * @param pToolData the associated tool data.
     */
    public void handleToolSelection(final ToolData pToolData) {
        if (!ignoreNotifications) {
            toolGuiConfiguration.setNewlySelectedToolData(pToolData);
            handleChangedToolGuiConfiguration();
        }
    }

    /**
     * Called when the application, the tool selection or the tool configuration
     * were changed.
     */
    public void updateLaunchingStatus() {
        ICheckableToolConfiguration lToolConfig;

        if (toolGuiConfiguration.isApplicationSelected()) {
            if (toolGuiConfiguration
                    .selectedToolMatches(DataManager.getInstance().getCoverageMatrixGeneratorToolData())) {
                lToolConfig = coverageMatrixGeneratorToolConfiguration;
            } else {
                lToolConfig = null;
            }
        } else {
            lToolConfig = null;
        }

        toolFrame
                .getMainPanel()
                .getControlPanel()
                .updateTipLabel(toolGuiConfiguration.getCurrentlySetGitBaseDirectory(),
                        toolGuiConfiguration.getCurrentlySelectedApplicationData(),
                        toolGuiConfiguration.getCurrentlySelectedToolData(), lToolConfig);
    }

    /**
     * Select the Git base directory through a file chooser.
     */
    private void doSelectGitBaseDirectory() {

        int lAnswer = getGitRepositoryDirectoryChooser().showOpenDialog(toolFrame);

        if (lAnswer == JFileChooser.APPROVE_OPTION) {
            File lSelectedDir = getGitRepositoryDirectoryChooser().getSelectedFile();
            toolFrame.getMainPanel().getConfigurationPanel().setGitBaseRepositoryDirectory(lSelectedDir);

            // Set the last selected directory as current directory
            getGitRepositoryDirectoryChooser().setCurrentDirectory(lSelectedDir);
        }
    }

    /**
     * Sets the GIT base directory.
     * 
     * @param pGitBaseDirectory the GIT base directory to set.
     */
    public void setGitBaseDirectory(final File pGitBaseDirectory) {

        if (!ignoreNotifications) {
            toolGuiConfiguration.setNewlySetGitBaseDirectory(pGitBaseDirectory);

            handleChangedToolGuiConfiguration();
        }
    }

    /**
     * Getter of the exit action.
     * 
     * @return the exitAction
     */
    public Action getExitAction() {
        return exitAction;
    }

    /**
     * Getter of the select Git base directory action.
     * 
     * @return the selectGitBaseDirectoryAction
     */
    public GuiAction getSelectGitBaseDirectoryAction() {
        return selectGitBaseDirectoryAction;
    }

    /**
     * Getter of the launch process action.
     * 
     * @return the launchProcessAction
     */
    public GuiAction getLaunchProcessAction() {
        return launchProcessAction;
    }

    /**
     * Getter of the GIT base directory.
     * 
     * @return the gitBaseDirectory
     */
    public File getGitBaseDirectory() {
        return toolGuiConfiguration.getCurrentlySetGitBaseDirectory();
    }

    /**
     * Getter of the Coverage Matrix Generator Tool configuration.
     * 
     * @return the coverageMatrixGeneratorToolConfiguration
     */
    public TraceabilityManagerToolConfiguration getCoverageMatrixGeneratorToolConfiguration() {
        return coverageMatrixGeneratorToolConfiguration;
    }

}
