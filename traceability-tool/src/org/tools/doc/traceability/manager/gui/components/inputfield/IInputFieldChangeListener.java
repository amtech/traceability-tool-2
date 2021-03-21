/**
 * 
 */
package org.tools.doc.traceability.manager.gui.components.inputfield;

/**
 * Interface to implement by classes interested in being notified of changes
 * occurring in an InputField.
 * 
 * @author Yann Leglise
 *
 */
public interface IInputFieldChangeListener {

    /**
     * Called when the value of an input field is changed.
     * 
     * @param pInputFieldIdentifier the identifier of this input field.
     */
    void handleInputFieldChange(int pInputFieldIdentifier);

}
