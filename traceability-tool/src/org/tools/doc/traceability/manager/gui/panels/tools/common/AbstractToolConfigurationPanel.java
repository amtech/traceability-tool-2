/**
 * 
 */
package org.tools.doc.traceability.manager.gui.panels.tools.common;

import org.tools.doc.traceability.common.ui.AbstractPanel;

/**
 * Common class for tool configuration panels.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractToolConfigurationPanel extends AbstractPanel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 3937442575938712222L;

    /**
     * Constructor.
     */
    public AbstractToolConfigurationPanel() {
        super();
    }

    /**
     * Update the HMI fields from the tool configuration.
     */
    public abstract void updateFromToolConfiguration();
}
