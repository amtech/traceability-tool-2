/**
 * 
 */
package org.tools.doc.traceability.analyzer.unittests.java.model;

import org.tools.doc.traceability.analyzer.unittests.common.AbstractUnitTestFileData;

/**
 * Class containing the data extracted from a java unit test file.
 * 
 * @author Yann Leglise
 *
 */
public class JavaUnitTestFileData extends AbstractUnitTestFileData {

    /**
     * Constructor.
     */
    public JavaUnitTestFileData() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder lSb = new StringBuilder();
        lSb.append("Java unit test data list with ");
        lSb.append(getUnitTestDataList().size());
        lSb.append(" unit test data:");
        printUnitTestCaseDataListOn(lSb);
        return lSb.toString();
    }
}
