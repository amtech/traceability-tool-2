/**
 * 
 */
package org.tools.doc.traceability.analyzer.almcoverage;

import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.analyzer.almcoverage.model.AlmStepTestData;
import org.tools.doc.traceability.common.executor.AbtsractExecutionResultObject;

/**
 * The result produced by the VTP Updater tool.
 * 
 * @author Yann Leglise
 *
 */
public class AlmCoverageAnalyserResult extends AbtsractExecutionResultObject {

    /**
     * The list of ALM test steps read from the input file.
     */
    private List<AlmStepTestData> almStepTestDataList;

    /**
     * Constructor.
     */
    public AlmCoverageAnalyserResult() {
        almStepTestDataList = new ArrayList<AlmStepTestData>();
    }

    /**
     * Getter of the list of ALM test steps read from the input file.
     * 
     * @return the almStepTestDataList
     */
    public List<AlmStepTestData> getAlmStepTestDataList() {
        return almStepTestDataList;
    }

    /**
     * Add an ALM step data to the list.
     * 
     * @param pAlmStepTestData the ALM step data to add.
     */
    public void addAlmStepData(final AlmStepTestData pAlmStepTestData) {
        almStepTestDataList.add(pAlmStepTestData);
    }

}
