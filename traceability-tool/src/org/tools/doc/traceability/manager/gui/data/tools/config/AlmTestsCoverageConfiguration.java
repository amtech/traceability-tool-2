/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.tools.config;

import java.io.File;

import org.tools.doc.traceability.common.exceptions.InvalidConfigurationException;
import org.tools.doc.traceability.manager.gui.configuration.ConfigurationManager;

/**
 * The configuration for the ALM tests coverage.
 * 
 * @author Yann Leglise
 *
 */
public class AlmTestsCoverageConfiguration extends AbstractTestCoverageConfiguration {

    /**
     * The ALM extraction file.
     */
    private File almExtractionFile;

    /**
     * Constructor.
     */
    public AlmTestsCoverageConfiguration() {
        super("ALM tests");
        almExtractionFile = null;
    }

    /**
     * Configure the ALM extraction file from the application base directory and
     * the file name.
     * 
     * @param pApplicationBaseDirectory the application base directory.
     * @param pAlmExtractFilename the name of the extracted ALM file.
     */
    public void configureAlmExtractionFile(final File pApplicationBaseDirectory, final String pAlmExtractFilename) {
        File lAlmSubDir = new File(pApplicationBaseDirectory, ConfigurationManager.getInstance()
                .getRelativePathToAlmExtrationFiles());
        almExtractionFile = new File(lAlmSubDir, pAlmExtractFilename);
    }

    /**
     * Setter of the almExtractionFile.
     * 
     * @param pAlmExtractionFile the almExtractionFile to set
     */
    public void setAlmExtractionFile(final File pAlmExtractionFile) {
        almExtractionFile = pAlmExtractionFile;
    }

    /**
     * Getter of the ALM extraction file.
     * 
     * @return the almExtractionFile
     */
    public File getAlmExtractionFile() {
        return almExtractionFile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        super.reset();
        almExtractionFile = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkIsValid() throws InvalidConfigurationException {
        if (isActive()) {
            if (almExtractionFile == null) {
                throw new InvalidConfigurationException("Define the ALM extracted file name");
            } else {
                if (!almExtractionFile.isFile()) {
                    throw new InvalidConfigurationException("Define an ALM extracted file that exists");
                }
            }
        }

    }

}
