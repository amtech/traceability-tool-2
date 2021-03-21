/**
 * 
 */
package org.tools.doc.traceability.analyzer.justifircation;

import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.analyzer.justifircation.model.NotCoveredRequirementJustification;
import org.tools.doc.traceability.common.executor.AbtsractExecutionResultObject;

/**
 * The result produced by the Justification file analyzer tool.
 * 
 * @author Yann Leglise
 *
 */
public class JustificationFileAnalyzerResult extends AbtsractExecutionResultObject {

    /**
     * The list of not covered requirement justification found in the
     * justification file.
     */
    private final List<NotCoveredRequirementJustification> notCoveredRequirementJustificationList;

    /**
     * Constructor.
     */
    public JustificationFileAnalyzerResult() {
        notCoveredRequirementJustificationList = new ArrayList<NotCoveredRequirementJustification>();
    }

    /**
     * Getter of the not covered requirement justification list.
     * 
     * @return the requirementJustificationList
     */
    public List<NotCoveredRequirementJustification> getNotCoveredRequirementJustificationList() {
        return notCoveredRequirementJustificationList;
    }

    /**
     * Add a not covered requirement justification to the result.
     * 
     * @param pNotCoveredRequirementJustification the not covered requirement
     * justification to add.
     */
    public void addNotCoveredRequirementJustification(
            final NotCoveredRequirementJustification pNotCoveredRequirementJustification) {
        notCoveredRequirementJustificationList.add(pNotCoveredRequirementJustification);
    }
}
