/**
 * 
 */
package org.tools.doc.traceability.analyzer.cucumbertests;

import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.analyzer.cucumbertests.model.CucumberTestsFileData;
import org.tools.doc.traceability.common.executor.AbtsractExecutionResultObject;

/**
 * The result produced by the cucumber test coverage analysis tool.
 * 
 * @author Yann Leglise
 *
 */
public class CucumberTestCoverageAnalyzerResult extends AbtsractExecutionResultObject {

    /**
     * The list of cucumber tests file data.
     */
    private final List<CucumberTestsFileData> cucumberTestsFileDataList;

    /**
     * Constructor.
     */
    public CucumberTestCoverageAnalyzerResult() {
        cucumberTestsFileDataList = new ArrayList<CucumberTestsFileData>();
    }

    /**
     * Add a parsed cucumber test file data to the list.
     * 
     * @param pCucumberTestsFileData the cucumber tests file data to add.
     */
    public void addCucumberTestFileData(final CucumberTestsFileData pCucumberTestsFileData) {

        cucumberTestsFileDataList.add(pCucumberTestsFileData);
    }

    /**
     * Getter of the cucumber tests file data list.
     * 
     * @return the cucumberTestsFileDataList
     */
    public List<CucumberTestsFileData> getCucumberTestsFileDataList() {
        return cucumberTestsFileDataList;
    }

}
