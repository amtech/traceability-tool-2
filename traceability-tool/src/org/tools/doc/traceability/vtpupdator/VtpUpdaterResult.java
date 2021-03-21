/**
 * 
 */
package org.tools.doc.traceability.vtpupdator;

import java.util.List;

import org.tools.doc.traceability.common.executor.AbtsractExecutionResultObject;
import org.tools.doc.traceability.common.model.TestSet;

/**
 * The result produced by the VTP Updater tool.
 * 
 * @author Yann Leglise
 *
 */
public class VtpUpdaterResult extends AbtsractExecutionResultObject {

    /**
     * The list of ALM covering test set.
     */
    private List<TestSet> almCoveringTestSetList;

    /**
     * The list of cucumber covering test set.
     */
    private List<TestSet> cucumberCoveringTestSetList;

    /**
     * The list of C# unit test set.
     */
    private List<TestSet> cSharpCoveringTestSetList;

    /**
     * The list of java unit test set.
     */
    private List<TestSet> javaCoveringTestSetList;

    /**
     * Constructor.
     */
    public VtpUpdaterResult() {
        almCoveringTestSetList = null;
        cucumberCoveringTestSetList = null;
        cSharpCoveringTestSetList = null;
        javaCoveringTestSetList = null;
    }

    /**
     * Getter of the ALM covering test set list.
     * 
     * @return the almCoveringTestSetList or <tt>null</tt> if there was none.
     */
    public List<TestSet> getAlmCoveringTestSetList() {
        return almCoveringTestSetList;
    }

    /**
     * Setter of the ALM covering test set list..
     * 
     * @param pAlmCoveringTestSetList the almCoveringTestSetList to set
     */
    public void setAlmCoveringTestSetList(final List<TestSet> pAlmCoveringTestSetList) {
        almCoveringTestSetList = pAlmCoveringTestSetList;
    }

    /**
     * Getter of the cucumber covering test set list.
     * 
     * @return the cucumberCoveringTestSetList or <tt>null</tt> if there was
     * none.
     */
    public List<TestSet> getCucumberCoveringTestSetList() {
        return cucumberCoveringTestSetList;
    }

    /**
     * Setter of the cucumber covering test set list..
     * 
     * @param pCucumberCoveringTestSetList the cucumberCoveringTestSetList to
     * set
     */
    public void setCucumberCoveringTestSetList(final List<TestSet> pCucumberCoveringTestSetList) {
        cucumberCoveringTestSetList = pCucumberCoveringTestSetList;
    }

    /**
     * Getter of the C# covering test set list.
     * 
     * @return the cSharpCoveringTestSetList or <tt>null</tt> if there was none.
     */
    public List<TestSet> getcSharpCoveringTestSetList() {
        return cSharpCoveringTestSetList;
    }

    /**
     * Setter of the C# covering test set list..
     * 
     * @param pCSharpCoveringTestSetList the cSharpCoveringTestSetList to set
     */
    public void setcSharpCoveringTestSetList(final List<TestSet> pCSharpCoveringTestSetList) {
        cSharpCoveringTestSetList = pCSharpCoveringTestSetList;
    }

    /**
     * Getter of the java covering test set list.
     * 
     * @return the javaCoveringTestSetList or <tt>null</tt> if there was none.
     */
    public List<TestSet> getJavaCoveringTestSetList() {
        return javaCoveringTestSetList;
    }

    /**
     * Setter of the java covering test set list..
     * 
     * @param pJavaCoveringTestSetList the javaCoveringTestSetList to set
     */
    public void setJavaCoveringTestSetList(final List<TestSet> pJavaCoveringTestSetList) {
        javaCoveringTestSetList = pJavaCoveringTestSetList;
    }

}
