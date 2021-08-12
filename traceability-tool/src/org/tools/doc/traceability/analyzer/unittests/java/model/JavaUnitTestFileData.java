/**
 * 
 */
package org.tools.doc.traceability.analyzer.unittests.java.model;

import org.tools.doc.traceability.analyzer.unittests.common.AbstractUnitTestFileData;
import org.tools.doc.traceability.common.Constants;

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
        printJavaUnitTestFileDataOn(lSb, 0);
        return lSb.toString();
    }

    /**
     * Writes a description of this instance on the given string builder.
     * 
     * @param pSb the string builder where to write.
     * @param leadingIndentLevel the leading indentation level.
     */
    public void printJavaUnitTestFileDataOn(final StringBuilder pSb, final int leadingIndentLevel) {
        for (int i = 0; i < leadingIndentLevel; i++) {
            pSb.append(Constants.LEADING_INDENTATION_TEXT);
        }
        pSb.append("Java unit test data list with ");
        pSb.append(getUnitTestDataList().size());
        pSb.append(" unit test data:");
        printUnitTestCaseDataListOn(pSb, leadingIndentLevel + 1);
    }
}
