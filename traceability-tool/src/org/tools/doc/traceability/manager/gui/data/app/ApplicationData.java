/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.app;

import java.io.File;

import org.tools.doc.traceability.manager.gui.data.app.coverage.AlmTestsCoverage;
import org.tools.doc.traceability.manager.gui.data.app.coverage.CSharpUnitTestsCoverage;
import org.tools.doc.traceability.manager.gui.data.app.coverage.CucumberTestsCoverage;
import org.tools.doc.traceability.manager.gui.data.app.coverage.JavaUnitTestsCoverage;
import org.tools.doc.traceability.manager.gui.manager.data.AbstractIdentifiedData;

/**
 * Characteristics of an application.
 * 
 * @author Yann Leglise
 *
 */
public class ApplicationData extends AbstractIdentifiedData {

    /**
     * The relative path in the GIT repository for this application directory.
     */
    private final String gitRelativeDirPath;

    /**
     * The application identifier.
     */
    private final String applicationIdentifier;

    /**
     * The name of the SD file.
     */
    private final String sdFileName;

    /**
     * The name of the target VTP file.
     */
    private final String targetVtpFileName;

    /**
     * The name of the target matrix file.
     */
    private final String targetMatrixFileName;

    /**
     * The name of the target extracted requirement file.
     */
    private final String targetExtractedRequirementsFileName;

    /**
     * The name of the justification file (located in [repository base
     * directory]/documentation/VTP).
     */
    private final String justificationFileName;

    /**
     * The list of requirement prefixes, separated with comma.
     */
    private final String requirementPrefixes;

    /**
     * Flag indicating whether the application is available given the specified
     * GIT repository directory.
     */
    private boolean isAvailable;

    /**
     * The ALM tests coverage.
     */
    private final AlmTestsCoverage almTestsCoverage;

    /**
     * The cucumber tests coverage.
     */
    private final CucumberTestsCoverage cucumberTestsCoverage;

    /**
     * The C# unit tests coverage.
     */
    private final CSharpUnitTestsCoverage cSharpUnitTestsCoverage;

    /**
     * The java unit tests coverage.
     */
    private final JavaUnitTestsCoverage javaUnitTestsCoverage;

    /**
     * Constructor.
     * 
     * @param pApplicationIdenifier the application identifier.
     * @param pDisplayName the display name.
     * @param pGitRelativeDirPath The relative path in the GIT repository for
     * this application.
     * @param pSdFileName the SD file name
     * @param pRequirementPrefixes the list of requirements, separated with
     * commas.
     * @param pJustificationFileName the name of the justification file to use
     * (if not null, will be searched into [application base
     * directory]/documentation/VTP).
     * @param pAlmTestsCoverage the ALM tests coverage.
     * @param pCucumberTestsCoverage the cucumber tests coverage.
     * @param pCSharpUnitTestsCoverage the C# unit tests coverage.
     * @param pJavaUnitTestsCoverage the java unit tests coverage.
     * @param pTargetVtpFileName name of the target VTP file.
     * @param pTargetMatrixFileName the target matrix file.
     * @param pTargetExtractedRequirementsFileName the target extracted
     * requirements file.
     */
    public ApplicationData(final String pApplicationIdenifier, final String pDisplayName,
            final String pGitRelativeDirPath, final String pSdFileName, final String pRequirementPrefixes,
            final String pJustificationFileName, final AlmTestsCoverage pAlmTestsCoverage,
            final CucumberTestsCoverage pCucumberTestsCoverage, final CSharpUnitTestsCoverage pCSharpUnitTestsCoverage,
            final JavaUnitTestsCoverage pJavaUnitTestsCoverage, final String pTargetVtpFileName,
            final String pTargetMatrixFileName, final String pTargetExtractedRequirementsFileName) {
        super(pDisplayName);
        applicationIdentifier = pApplicationIdenifier;
        gitRelativeDirPath = pGitRelativeDirPath;
        sdFileName = pSdFileName;
        requirementPrefixes = pRequirementPrefixes;
        almTestsCoverage = pAlmTestsCoverage;
        cucumberTestsCoverage = pCucumberTestsCoverage;
        cSharpUnitTestsCoverage = pCSharpUnitTestsCoverage;
        javaUnitTestsCoverage = pJavaUnitTestsCoverage;
        justificationFileName = pJustificationFileName;
        targetVtpFileName = pTargetVtpFileName;
        targetMatrixFileName = pTargetMatrixFileName;
        targetExtractedRequirementsFileName = pTargetExtractedRequirementsFileName;
        isAvailable = false;
    }

    /**
     * Getter of the application identifier.
     * 
     * @return the applicationIdenifier
     */
    public String getApplicationIdentifier() {
        return applicationIdentifier;
    }

    /**
     * Update the {@link #isAvailable} flag depending on the parameters.
     * 
     * @param pGitBaseDirectory the GIT base directory.
     */
    public void updateAvailability(final File pGitBaseDirectory) {
        File lAppDir = new File(pGitBaseDirectory, gitRelativeDirPath);
        isAvailable = lAppDir.isDirectory();
    }

    /**
     * Getter of the flag indicating whether the application is available given
     * the specified GIT repository directory.
     * 
     * @return the isAvailable
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Getter of the target VTP file name.
     * 
     * @return the targetVtpFileName
     */
    public String getTargetVtpFileName() {
        return targetVtpFileName;
    }

    /**
     * Getter of the target matrix file name.
     * 
     * @return the targetMatrixFileName
     */
    public String getTargetMatrixFileName() {
        return targetMatrixFileName;
    }

    /**
     * Getter of the targetExtractedRequirementsFileName.
     * 
     * @return the targetExtractedRequirementsFileName
     */
    public String getTargetExtractedRequirementsFileName() {
        return targetExtractedRequirementsFileName;
    }

    /**
     * Getter of the sdFileName.
     * 
     * @return the sdFileName
     */
    public String getSdFileName() {
        return sdFileName;
    }

    /**
     * Getter of the name of the justification file (located in [repository base
     * directory]/documentation/VTP).
     * 
     * @return the justificationFileName
     */
    public String getJustificationFileName() {
        return justificationFileName;
    }

    /**
     * Getter of the list of requirement prefixes, separated with comma.
     * 
     * @return the requirementPrefixes
     */
    public String getRequirementPrefixes() {
        return requirementPrefixes;
    }

    /**
     * Getter of the almTestsCoverage.
     * 
     * @return the almTestsCoverage
     */
    public AlmTestsCoverage getAlmTestsCoverage() {
        return almTestsCoverage;
    }

    /**
     * Getter of the cucumberTestsCoverage.
     * 
     * @return the cucumberTestsCoverage
     */
    public CucumberTestsCoverage getCucumberTestsCoverage() {
        return cucumberTestsCoverage;
    }

    /**
     * Getter of the cSharpUnitTestsCoverage.
     * 
     * @return the cSharpUnitTestsCoverage
     */
    public CSharpUnitTestsCoverage getcSharpUnitTestsCoverage() {
        return cSharpUnitTestsCoverage;
    }

    /**
     * Getter of the javaUnitTestsCoverage.
     * 
     * @return the javaUnitTestsCoverage
     */
    public JavaUnitTestsCoverage getJavaUnitTestsCoverage() {
        return javaUnitTestsCoverage;
    }

    /**
     * Getter of the gitRelativeDirPath.
     * @return the gitRelativeDirPath
     */
    public String getGitRelativeDirPath() {
        return gitRelativeDirPath;
    }
}
