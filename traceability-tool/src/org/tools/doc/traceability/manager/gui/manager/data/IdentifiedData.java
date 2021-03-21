/**
 * 
 */
package org.tools.doc.traceability.manager.gui.manager.data;

/**
 * Interface for uniquely identified data that must be displayed.
 * 
 * @author Yann Leglise
 *
 */
public interface IdentifiedData {

    /**
     * The data unique identifier.
     * 
     * @return the data identifier.
     */
    int getIdentifier();

    /**
     * The label to display for this data.
     * 
     * @return the data display name.
     */
    String getDisplayName();

    /**
     * Determines whether this instance matches the other, i.e. if they have the
     * same identifier.
     * 
     * @param pOther the other instance to compare with.
     * @return true if the instance and the other have the same identifier,
     * false otherwisde.
     */
    boolean matches(IdentifiedData pOther);
}
