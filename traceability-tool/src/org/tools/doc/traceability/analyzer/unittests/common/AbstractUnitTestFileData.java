/**
 * 
 */
package org.tools.doc.traceability.analyzer.unittests.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Class modeling the contents of a unit test file.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractUnitTestFileData {

    /**
     * The list of unit test data found in the file.
     */
    private final List<UnitTestCaseData> unitTestDataList;

    /**
     * Constructor.
     */
    public AbstractUnitTestFileData() {
        unitTestDataList = new ArrayList<UnitTestCaseData>();
    }

    /**
     * Add a unit test data.
     * 
     * @param pUnitTestData the unit test data to add.
     */
    public final void addUnitTestData(final UnitTestCaseData pUnitTestData) {
        if (pUnitTestData != null) {
            unitTestDataList.add(pUnitTestData);
        }
    }

    /**
     * Get the list of associated unit test data.
     * 
     * @return the unit test data list.
     */
    public final List<UnitTestCaseData> getUnitTestDataList() {
        return unitTestDataList;
    }

    /**
     * Indicates whether there is at least one unit test data present for the
     * file or not.
     * 
     * @return true if there is at least one unit test data, false otherwise.
     */
    public boolean containsData() {
        return unitTestDataList.size() > 0;
    }

    /**
     * Count the total number of referenced requirements.
     * 
     * @return the number of referenced requirements.
     */
    public long getReferencedRequirementCount() {
        long lReferencedReqCount = 0;

        for (UnitTestCaseData lUtd : unitTestDataList) {
            lReferencedReqCount += lUtd.getCoveredRequirements().size();
        }

        return lReferencedReqCount;
    }

    /**
     * Print the list of unit test data list on the given string builder.
     * 
     * @param pSringBuilder the string builder where to print the unit test data
     * list.
     */
    protected void printUnitTestCaseDataListOn(final StringBuilder pSringBuilder) {
        int lIdx = 1;
        for (UnitTestCaseData lUtd : unitTestDataList) {
            pSringBuilder.append("[");
            pSringBuilder.append(lIdx++);
            pSringBuilder.append("] : ");
            pSringBuilder.append(lUtd);
            pSringBuilder.append('\n');
        }
    }
}
