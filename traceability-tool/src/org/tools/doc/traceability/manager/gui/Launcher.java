/**
 * 
 */
package org.tools.doc.traceability.manager.gui;

import org.tools.doc.traceability.manager.gui.controller.TraceabilityToolController;

/**
 * The entry point for the GUI application.
 * 
 * @author Yann Leglise
 *
 */
public class Launcher {

    /**
     * Starting point.
     * 
     * @param pArgs the command line arguments.
     */
    public static void main(final String[] pArgs) {
        TraceabilityToolController.getInstance().startApplication();
    }

}
