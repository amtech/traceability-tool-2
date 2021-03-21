/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration.appmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Models the configuration of the Coverage Matrix Generator tool.
 * 
 * @author Yann Leglise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CoverageMatrixConfiguration extends AbstractConfigurationModel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 42851538724804626L;

    /**
     * Whether to prepend the assembly name before the unit test name or not.
     */
    @XmlElement(name = "PrependAssemblyNameForAutomaticTests")
    private boolean prependAssemblyNameForUnitTests;

    /**
     * Whether the requirements shall be sorted alphabetically or not.
     */
    @XmlElement(name = "SortExtractedRequirements")
    private boolean sortExtractedRequirements;

    /**
     * Whether the not covered requirements shall be placed in a separated sheet in
     * the coverage matrix or not.
     */
    @XmlElement(name = "UseSeparatedSheetForNotCoveredRequirements")
    private boolean useSeparatedSheetForNotCoveredRequirements;

    /**
     * Constructor.
     */
    public CoverageMatrixConfiguration() {
        prependAssemblyNameForUnitTests = false;
        sortExtractedRequirements = false;
        useSeparatedSheetForNotCoveredRequirements = false;
    }

    /**
     * Getter of the flag indicating whether to prepend the assembly name before the
     * unit test name or not.
     * 
     * @return the prependAssemblyNameForAutomaticTests
     */
    public boolean isPrependAssemblyNameForUnitTests() {
        return prependAssemblyNameForUnitTests;
    }

    /**
     * Getter of the flag indicating whether the requirements shall be sorted
     * alphabetically or not.
     * 
     * @return the sortExtractedRequirements
     */
    public boolean isSortExtractedRequirements() {
        return sortExtractedRequirements;
    }

    /**
     * Getter of the flag indicating whether the not covered requirements shall be
     * placed in a separated sheet in the coverage matrix or not.
     * 
     * @return the useSeparatedSheetForNotCoveredRequirements
     */
    public boolean isUseSeparatedSheetForNotCoveredRequirements() {
        return useSeparatedSheetForNotCoveredRequirements;
    }

}
