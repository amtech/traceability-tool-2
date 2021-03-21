package org.tools.doc.traceability.analyzer.unittests.csharp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.tools.doc.traceability.analyzer.unittests.csharp.model.CSharpUnitTestFileData;
import org.tools.doc.traceability.common.executor.AbtsractExecutionResultObject;

/**
 * The result produced by the C# unit test coverage analysis tool.
 * 
 * @author Yann Leglise
 *
 */
public class CSharpUnitTestCoverageAnalyserResult extends AbtsractExecutionResultObject {

    /**
     * A map associating to each analyzed XML file the list of extracted unit
     * test data.
     */
    private Map<File, CSharpUnitTestFileData> fileUnitTestDataMap;

    /**
     * Constructor.
     */
    public CSharpUnitTestCoverageAnalyserResult() {
        fileUnitTestDataMap = new HashMap<File, CSharpUnitTestFileData>();
    }

    /**
     * Add an association between a parsed XML file and the extracted data.
     * 
     * @param pXmlFile the XML file that has been parsed.
     * @param pUnitTestFileData the extracted data.
     * @return true if it could be added, false if the file was already there.
     */
    public boolean addResult(final File pXmlFile, final CSharpUnitTestFileData pUnitTestFileData) {
        boolean lCouldBeAdded = false;

        if (!fileUnitTestDataMap.containsKey(pXmlFile)) {
            fileUnitTestDataMap.put(pXmlFile, pUnitTestFileData);
            lCouldBeAdded = true;
        }

        return lCouldBeAdded;
    }

    /**
     * Get the map associating to each found XML file the associated read data.
     * 
     * @return the file / data map.
     */
    public Map<File, CSharpUnitTestFileData> getFileUnitTestDataMap() {
        return fileUnitTestDataMap;
    }

    /**
     * Computes the number of found tests.
     * 
     * @return the number of found tests.
     */
    public int getFoundTestCount() {
        int lTestCount = 0;

        for (Map.Entry<File, CSharpUnitTestFileData> lEntry : fileUnitTestDataMap.entrySet()) {
            CSharpUnitTestFileData lCSharpUnitTestFileData = lEntry.getValue();
            lTestCount += lCSharpUnitTestFileData.getUnitTestDataList().size();
        }
        return lTestCount;
    }
}
