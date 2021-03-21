/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.tools.execution;

/**
 * The execution status type.
 * 
 * @author Yann Leglise
 *
 */
public enum ExecutionStatusType {

    /**
     * The execution is pending.
     */
    Pending("Pending"),

    /**
     * The execution is finished, but with error(s).
     */
    EndedWithError("Finished with error(s)"),

    /**
     * The execution is finished successfully.
     */
    EndedSuccessfully("Finished successfully"),;

    /**
     * The displayed status name.
     */
    private final String displayName;

    /**
     * Constructor.
     * 
     * @param aDisplayName the displayed status name.
     */
    ExecutionStatusType(final String aDisplayName) {
        this.displayName = aDisplayName;
    }

    /**
     * Getter of the display name.
     * 
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

}
