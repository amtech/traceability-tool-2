/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.tools.config;

import java.io.File;

import org.tools.doc.traceability.common.exceptions.InvalidConfigurationException;
import org.tools.doc.traceability.common.exceptions.InvalidSimpleRegexpException;
import org.tools.doc.traceability.manager.gui.data.app.FileSelectionFilterData;

/**
 * Models a tests coverage configuration that has a file selection filter.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractFileSelectionFilterTestCoverageConfiguration extends AbstractTestCoverageConfiguration {

    /**
     * The associated file selection filter.
     */
    private final FileSelectionFilter fileSelectionFilter;

    /**
     * If not <tt>null</tt> indicates an error occurred while configuring the
     * instance.
     */
    private String invalidityDescription;

    /**
     * Constructor.
     * 
     * @param pConfigurationContext the context description.
     * 
     */
    public AbstractFileSelectionFilterTestCoverageConfiguration(final String pConfigurationContext) {
        super(pConfigurationContext);
        fileSelectionFilter = new FileSelectionFilter();
        invalidityDescription = null;
    }

    /**
     * Configure the file selection filter from the given parameters.
     * 
     * @param pApplicationRootDirectory the application root directory (shall
     * not be <tt>null</tt>).
     * @param pFileSelectionFilterData the data for configuring the instance
     * (shall not be <tt>null</tt>).
     * @throws InvalidSimpleRegexpException if the simple regexp for the file
     * name was invalid.
     */
    public void configureFileSelectionFilterFrom(final File pApplicationRootDirectory,
            final FileSelectionFilterData pFileSelectionFilterData) throws InvalidSimpleRegexpException {
        fileSelectionFilter.configureFrom(pApplicationRootDirectory, pFileSelectionFilterData);
    }

    /**
     * Getter of the file selection filter.
     * 
     * @return the fileSelectionFilter
     */
    public FileSelectionFilter getFileSelectionFilter() {
        return fileSelectionFilter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        super.reset();
        invalidityDescription = null;
        fileSelectionFilter.reset();
    }

    /**
     * Getter of the invalidityDescription.
     * 
     * @return the invalidityDescription or <tt>null</tt> if the instance is
     * valid.
     */
    public String getInvalidityDescription() {
        return invalidityDescription;
    }

    /**
     * Setter of the invalidityDescription.
     * 
     * @param pInvalidityDescription the invalidityDescription to set (
     * <tt>null</tt> to set it valid).
     */
    public void setInvalidityDescription(final String pInvalidityDescription) {
        invalidityDescription = pInvalidityDescription;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkIsValid() throws InvalidConfigurationException {
        if (isActive()) {
            try {
                fileSelectionFilter.checkIsValid();
            } catch (InvalidConfigurationException ice) {
                throw new InvalidConfigurationException(ice.getMessage() + " for " + getConfigurationContext());
            }

            if (invalidityDescription != null) {
                throw new InvalidConfigurationException("Correct the configuration : " + invalidityDescription);
            }
        }
    }
}
