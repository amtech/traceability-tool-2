/**
 * 
 */
package org.tools.doc.traceability.analyzer.justifircation.model;

import org.tools.doc.traceability.common.model.Requirement;

/**
 * Models a justification for a requirement not to be covered by a test.
 * 
 * @author Yann Leglise
 *
 */
public class NotCoveredRequirementJustification {

    /**
     * The not covered requirement.
     */
    private final Requirement requirement;

    /**
     * The justification why the requirement is not covered.
     */
    private final String justification;

    /**
     * Constructor.
     * 
     * @param pRequirement the not covered requirement.
     * @param pJustification the justification why the requirement is not
     * covered.
     */
    public NotCoveredRequirementJustification(final Requirement pRequirement, final String pJustification) {
        requirement = pRequirement;
        justification = pJustification;
    }

    /**
     * Getter of the not covered requirement.
     * 
     * @return the requirement
     */
    public Requirement getRequirement() {
        return requirement;
    }

    /**
     * Getter of the justification why the requirement is not covered..
     * 
     * @return the justification
     */
    public String getJustification() {
        return justification;
    }

    /**
     * Check whether the instance is defined, i.e. at least one field is not
     * null and empty.
     * 
     * @return <tt>true</tt> if at least one field is defined, <tt>false</tt>
     * otherwise.
     */
    public boolean isDefined() {
        boolean lIsDefined = false;

        if (requirement != null && !requirement.toString().trim().isEmpty()) {
            lIsDefined = true;
        } else if (justification != null && !justification.trim().isEmpty()) {
            lIsDefined = true;
        }

        return lIsDefined;
    }

}
