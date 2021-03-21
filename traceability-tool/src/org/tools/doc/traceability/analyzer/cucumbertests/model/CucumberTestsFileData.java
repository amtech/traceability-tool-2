/**
 * 
 */
package org.tools.doc.traceability.analyzer.cucumbertests.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Contents of a cucumber test file.
 * 
 * @author Yann Leglise
 *
 */
public class CucumberTestsFileData {

    /**
     * The cucumber test file from which the data is extracted.
     */
    private final File sourceFile;

    /**
     * The list of contained cucumber test data elements.
     */
    private final List<CucumberTestData> automaticTestDataList;

    /**
     * Constructor.
     * 
     * @param pSourceFile the source file.
     */
    public CucumberTestsFileData(final File pSourceFile) {
        sourceFile = pSourceFile;
        automaticTestDataList = new ArrayList<CucumberTestData>();
    }

    /**
     * Getter of the source file.
     * 
     * @return the sourceFile
     */
    public File getSourceFile() {
        return sourceFile;
    }

    /**
     * Getter of the cucumber test data list.
     * 
     * @return the cucumberTestDataList
     */
    public List<CucumberTestData> getCucumberTestDataList() {
        return automaticTestDataList;
    }

    /**
     * Indicates whether there is at least one cucumber test data present for the
     * file or not.
     * 
     * @return true if there is at least one cucumber test data, false otherwise.
     */
    public boolean containsData() {
        return automaticTestDataList.size() > 0;
    }

    /**
     * Count the total number of referenced requirements.
     * 
     * @return the number of referenced requirements.
     */
    public long getReferencedRequirementCount() {
        long lReferencedReqCount = 0;

        for (CucumberTestData lAtd : automaticTestDataList) {
            lReferencedReqCount += lAtd.getCoveredRequirements().size();
        }

        return lReferencedReqCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cucumber tests file data from [");
        if (sourceFile != null) {
            sb.append(sourceFile.getAbsolutePath());
        } else {
            sb.append("<null>");
        }
        sb.append("], with ");
        long nbReqs = getReferencedRequirementCount();
        if (nbReqs > 0) {
            sb.append(nbReqs);
            sb.append(" requirement(s)");
        } else {
            sb.append("no requirement");
        }
        
        for(CucumberTestData lAtd : automaticTestDataList)
        {
            sb.append("\n");
            sb.append(lAtd);
        }
        return sb.toString();
    }

}
