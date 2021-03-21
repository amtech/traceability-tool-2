/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.tools.execution;

/**
 * An execution status.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractExecutionStatus {

    /**
     * The associated status type.
     */
    private final ExecutionStatusType status;

    /**
     * The additional information for the status.
     */
    private final String additionalInfo;

    /**
     * constructor.
     * 
     * @param pStatus         the associated status.
     * @param pAdditionalInfo the additional information.
     */
    public AbstractExecutionStatus(final ExecutionStatusType pStatus, final String pAdditionalInfo) {
        super();
        status = pStatus;
        additionalInfo = pAdditionalInfo;
    }

    /**
     * Getter of the status.
     * 
     * @return the status
     */
    public ExecutionStatusType getStatus() {
        return status;
    }

    /**
     * Produce an HTML representation of the status suitable for being displayed on
     * HMI.
     * 
     * @return the HMI description of the satatus.
     */
    public String getHmiDescription() {
        StringBuilder lSb = new StringBuilder("<html><font color=\"");

        switch (status) {
            case EndedWithError:
                lSb.append("#FF0000");
                break;
            case EndedSuccessfully:
                lSb.append("#006600");
                break;
            case Pending:
                lSb.append("#0000CC");
                break;
            default:
                lSb.append("#000000");
                break;
        }
        lSb.append("\"><b>");
        lSb.append(status.getDisplayName());
        lSb.append("</b></font>");

        if ((additionalInfo != null) && (additionalInfo.length() > 0)) {
            lSb.append(": ");
            lSb.append(additionalInfo);
        }

        lSb.append("</html");
        return lSb.toString();
    }

}
