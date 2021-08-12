package org.tools.doc.traceability.analyzer.unittests.csharp.model;

import org.tools.doc.traceability.analyzer.unittests.common.AbstractUnitTestFileData;

/**
 * Class containing the data extracted from a C# unit test file.
 * 
 * @author Yann Leglise
 *
 */
public class CSharpUnitTestFileData extends AbstractUnitTestFileData {

    /**
     * The found name of the assembly.
     */
    private String assemblyName;

    /**
     * Constructor.
     * 
     * @param pAssemblyName the assembly name corresponding to the file.
     */
    public CSharpUnitTestFileData(final String pAssemblyName) {
        super();
        assemblyName = pAssemblyName;
    }

    /**
     * Get the assembly name.
     * 
     * @return the assembly name.
     */
    public String getAssemblyName() {
        return assemblyName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder lSb = new StringBuilder();
        lSb.append("C# Unit test data list for assembly name [");
        lSb.append(assemblyName);
        lSb.append("]  and ");
        lSb.append(getUnitTestDataList().size());
        lSb.append(" unit test data:");
        printUnitTestCaseDataListOn(lSb, 1);
        return lSb.toString();
    }
}
