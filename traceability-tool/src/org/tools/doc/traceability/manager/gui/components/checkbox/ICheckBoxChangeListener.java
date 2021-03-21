/**
 * 
 */
package org.tools.doc.traceability.manager.gui.components.checkbox;

/**
 * Interface to implement by classes interested in being notified of changes
 * occurring in a Check box.
 * 
 * @author Yann Leglise
 *
 */
public interface ICheckBoxChangeListener {

    /**
     * Called when the value of a check box is changed.
     * 
     * @param pCheckBoxIdentifier the identifier of this check box.
     */
    void handleCheckBoxChange(int pCheckBoxIdentifier);
}
