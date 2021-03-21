/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.app.coverage;


/**
 * The configuration for coverage from ALM tests.
 * 
 * @author Yann Leglise
 *
 */
public class AlmTestsCoverage extends AbstractTestCoverage {

    /**
     * The name of the ALM extracted file.
     */
    final String almExtractedFileName;

    /**
     * Constructor.
     * 
     * @param pIsActive whether this coverage is active or not.
     * @param pAlmExtractedFileName the name of the ALM extracted file.
     */
    public AlmTestsCoverage(final boolean pIsActive, final String pAlmExtractedFileName) {
        super(pIsActive);
        almExtractedFileName = pAlmExtractedFileName;
    }

    /**
     * Getter of the name of the ALM extracted file.
     * 
     * @return the almExtractedFileName
     */
    public String getAlmExtractedFileName() {
        return almExtractedFileName;
    }

}
