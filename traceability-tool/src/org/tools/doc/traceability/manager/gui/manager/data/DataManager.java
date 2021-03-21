/**
 * 
 */
package org.tools.doc.traceability.manager.gui.manager.data;

import java.io.File;

import org.tools.doc.traceability.manager.gui.configuration.ConfigurationManager;
import org.tools.doc.traceability.manager.gui.data.app.ApplicationData;
import org.tools.doc.traceability.manager.gui.data.app.ApplicationDataList;
import org.tools.doc.traceability.manager.gui.data.tools.data.ToolData;

/**
 * Central source for application and tool data.
 * 
 * @author Yann Leglise
 *
 */
public final class DataManager {

    /**
     * They unique class instance.
     */
    private static DataManager Instance = null;

    /**
     * Getter of the unique class instance.
     * 
     * @return the unique class instance.
     */
    public static synchronized DataManager getInstance() {
        if (Instance == null) {
            Instance = new DataManager();
        }

        return Instance;
    }

    /**
     * The list of application data.
     */
    private final ApplicationDataList applicationDataList;

    /**
     * The identifier of the default ApplicationData to select.
     */
    private String defaultApplicationDataId;

    /**
     * The tool data for ALM Coverage Alalyzer.
     */
    private final ToolData almCoverageAnalyzerToolData;

    /**
     * The tool data for Coverage Matrix Generator.
     */
    private final ToolData coverageMatrixGeneratorToolData;

    /**
     * Constructor.
     */
    private DataManager() {

        ConfigurationManager lConfMan = ConfigurationManager.getInstance();

        // Get the application data list
        applicationDataList = lConfMan.getApplicationDataList();

        // Get the default application data identifier
        defaultApplicationDataId = lConfMan.getDefaultApplicationDataId();

        // Initialize tool data
        almCoverageAnalyzerToolData = new ToolData("ALM Coverage Analyzer");
        coverageMatrixGeneratorToolData = new ToolData("Coverage Matrix Generator");
    }

    /**
     * Getter of the default ApplicationData identifier.
     * 
     * @return the defaultApplicationDataId
     */
    public String getDefaultApplicationDataId() {
        return defaultApplicationDataId;
    }

    /**
     * Getter of the application data list.
     * 
     * @return the applicationDataList
     */
    public ApplicationDataList getApplicationDataList() {
        return applicationDataList;
    }

    /**
     * Getter of the ALM Coverage Analyzer Tool data.
     * 
     * @return the vtpUpdaterToolData
     */
    public ToolData getAlmCoverageAnalyzerToolData() {
        return almCoverageAnalyzerToolData;
    }

    /**
     * Getter of the Coverage Matrix Generator Tool data.
     * 
     * @return the coverageMatrixGeneratorToolData
     */
    public ToolData getCoverageMatrixGeneratorToolData() {
        return coverageMatrixGeneratorToolData;
    }

    /**
     * Update the availablilty of each application depending on the parameters.
     * 
     * @param pGitBaseDirectory the GIT base directory.
     * intermediate level is present or not.
     */
    public void updateApplicationAvailability(final File pGitBaseDirectory) {

        // Iterate
        for (ApplicationData lAppData : applicationDataList) {
            lAppData.updateAvailability(pGitBaseDirectory);
        }
    }

}
