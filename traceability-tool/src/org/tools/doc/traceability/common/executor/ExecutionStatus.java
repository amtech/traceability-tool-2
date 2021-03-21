/**
 * 
 */
package org.tools.doc.traceability.common.executor;

/**
 * The possible execution statuses for an executor.
 * 
 * @author Yann Leglise
 *
 */
public enum ExecutionStatus {

    /**
     * The executor is just created, and not yet started.
     */
    NOT_STARTED("Not yet started"),

    /**
     * The executor has been started, and is not finished yet.
     */
    PENDING("Pending"),

    /**
     * The executor has finished without error.
     */
    ENDED_SUCCESS("Ended successfully"),

    /**
     * The executor has finished with error(s).
     */
    ENDED_WITH_ERROR("Ended with error");

    /**
     * The textual description of the status.
     */
    private String description;

    /**
     * Constructor.
     * 
     * @param pDescription the status description.
     */
    ExecutionStatus(final String pDescription) {
        description = pDescription;
    }

    /**
     * Getter of the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

}
