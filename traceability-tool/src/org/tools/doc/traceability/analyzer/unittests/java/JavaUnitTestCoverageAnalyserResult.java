/**
 * 
 */
package org.tools.doc.traceability.analyzer.unittests.java;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.tools.doc.traceability.analyzer.unittests.java.model.JavaUnitTestFileData;
import org.tools.doc.traceability.common.executor.AbtsractExecutionResultObject;

/**
 * The result produced by the java test coverage analysis tool.
 * 
 * @author Yann Leglise
 *
 */
public class JavaUnitTestCoverageAnalyserResult extends AbtsractExecutionResultObject {

    /**
     * A map associating to each analyzed java file the list of extracted unit
     * test data.
     */
    private Map<File, JavaUnitTestFileData> fileUnitTestDataMap;

    /**
     * Constructor.
     */
    public JavaUnitTestCoverageAnalyserResult() {
        super();
        fileUnitTestDataMap = new HashMap<File, JavaUnitTestFileData>();
    }

    /**
     * Add an association between a parsed java file and the extracted data.
     * 
     * @param pJavaFile the java file that has been parsed.
     * @param pUnitTestFileData the extracted data.
     * @return true if it could be added, false if the file was already there.
     */
    public boolean addResult(final File pJavaFile, final JavaUnitTestFileData pUnitTestFileData) {
        boolean lCouldBeAdded = false;

        if (!fileUnitTestDataMap.containsKey(pJavaFile)) {
            fileUnitTestDataMap.put(pJavaFile, pUnitTestFileData);
            lCouldBeAdded = true;
        }

        return lCouldBeAdded;
    }

    /**
     * Get the map associating to each found java file the associated read data.
     * 
     * @return the file / data map.
     */
    public Map<File, JavaUnitTestFileData> getFileUnitTestDataMap() {
        return fileUnitTestDataMap;
    }
    
    /**
     * Computes the number of found tests.
     * 
     * @return the number of found tests.
     */
    public int getFoundTestCount() {
        int lTestCount = 0;

        for (Map.Entry<File, JavaUnitTestFileData> lEntry : fileUnitTestDataMap.entrySet()) {
            JavaUnitTestFileData lJavaUnitTestFileData = lEntry.getValue();
            lTestCount += lJavaUnitTestFileData.getUnitTestDataList().size();
        }
        return lTestCount;
    }
}
