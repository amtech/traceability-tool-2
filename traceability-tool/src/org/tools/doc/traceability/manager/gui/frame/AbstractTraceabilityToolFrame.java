/**
 * 
 */
package org.tools.doc.traceability.manager.gui.frame;

import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;

import org.tools.doc.traceability.manager.gui.GuiConstants;

/**
 * Common class for the frames in the tool.
 * 
 * @author Yann
 *
 */
public abstract class AbstractTraceabilityToolFrame extends JFrame {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -6704124221863030840L;

    /**
     * Constructor.
     * 
     * @throws HeadlessException if GraphicsEnvironment.isHeadless() returns
     * true.
     */
    public AbstractTraceabilityToolFrame() throws HeadlessException {
        super();
    }

    /**
     * Constructor.
     * 
     * @param pTitle the frame title.
     * @throws HeadlessException if GraphicsEnvironment.isHeadless() returns
     * true.
     */
    public AbstractTraceabilityToolFrame(final String pTitle) throws HeadlessException {
        super(pTitle);
    }

    /**
     * Set the tool icon.
     */
    protected void setToolIcon() {
        URL lIconUrl = getClass().getClassLoader().getResource(GuiConstants.TOOL_ICON_FILENAME);
        if (lIconUrl == null) {
            String lError = "Couldn't find icon file " + GuiConstants.TOOL_ICON_FILENAME
                    + " from class loader. Make sure it is in the class path";
            System.err.println(lError);
        } else {
            Image lIcon = Toolkit.getDefaultToolkit().getImage(lIconUrl);
            setIconImage(lIcon);
        }
    }
}
