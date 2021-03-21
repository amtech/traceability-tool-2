/**
 * 
 */
package org.tools.doc.traceability.manager.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JRadioButton;

/**
 * Constants for the GUR.
 * 
 * @author Yann Leglise
 *
 */
public final class GuiConstants {

    /**
     * The name of the tool.
     */
    public static final String APPLICATION_NAME = "Traceability tool";

    /**
     * The title of the progress dialog.
     */
    public static final String PROGRESS_DIALOG_FRAME_TITLE = "Execution progress and result";

    /**
     * The preferred frame width in pixels.
     */
    public static final int PREFERRED_WIDTH = 800;

    /**
     * The preferred frame height in pixels.
     */
    public static final int PREFERRED_HEIGHT = 600;

    /**
     * The space, in pixels, between a panel border and an inner component.
     */
    public static final int PANEL_INNER_MARGIN = 3;

    /**
     * The horizontal space, in pixels, between a components inside of a panel.
     */
    public static final int INTER_COMPONENENT_HORIZONTAL_MARGIN = 3;

    /**
     * The vertical space, in pixels, between a components inside of a panel.
     */
    public static final int INTER_COMPONENT_VERTICAL_MARGIN = 2;

    /**
     * Size, in pixels, to have between each side of the screen and the frame.
     */
    public static final int FRAME_BORDER_TO_SCREEN_MARGIN = 100;

    /**
     * The value added to the normal font size for the text of a selected radio
     * button.
     */
    public static final int SELECTED_RADIO_BUTTON_FONT_SIZE_INCREMENT = 2;

    /**
     * Just a label used to get the font.
     */
    private static final JLabel FOO_LABEL = new JLabel();

    /**
     * Just a radio button used to get the font.
     */
    private static final JRadioButton FOO_RADIO_BUTTON = new JRadioButton();

    /**
     * Get the default font for radio button.
     */
    public static final Font RADIO_BUTTON_FONT = FOO_RADIO_BUTTON.getFont();

    /**
     * The default font.
     */
    private static final Font DEFAULT_FONT = FOO_LABEL.getFont();

    /**
     * The font for the titled borders.
     */
    public static final Font TITLE_BORDER_FONT = new Font(DEFAULT_FONT.getFamily(), Font.BOLD,
            DEFAULT_FONT.getSize() + 2);

    /**
     * The width of the line for titled borders.
     */
    public static final int TITLE_BORDER_LINE_WIDTH = 2;

    /**
     * The font for the text fields.
     */
    public static final Font TEXT_FIELD_FONT = new Font("Courier", Font.BOLD, 12);

    /**
     * The name environment variable specifying the name of the Traceability
     * tool configuration file to use.
     */
    public static final String CONFIGURATION_FILE_NAME_ENV_VAR_NAME = "TraceabilityConfigFileName";

    /**
     * The name of the default Traceability tool configuration file.
     */
    public static final String DEFAULT_CONFIGURATION_FILE_NAME = "app-config.xml";

    /**
     * The name environment variable specifying the path of the Traceability
     * tool configuration file.
     */
    public static final String CONFIGURATION_FILE_PATH_ENV_VAR_NAME = "TraceabilityConfigFilePath";

    /**
     * The name of the XSD to validate configuration file.
     */
    public static final String CONFIGRUATION_FILE_XSD_FILE_NAME = "app-config.xsd";

    /**
     * The tag to insert in the Git base directoy path specified in the
     * Traceability tool configuration file to be replaced by the execution
     * directory of the tool.
     */
    public static final String REPLACEMENT_TAG_FOR_EXECUTION_DIRECTORY = "#EXECUTION_DIR#";
    
    /**
     * The name of the file to use for the tool icon.
     * <p>
     * It must be made available in the classpath.
     * </p>
     */
    public static final String TOOL_ICON_FILENAME = "traceability-tool-logo.gif";

    /**
     * The color for the border and the text of titled borders.
     */
    public static final Color TITLE_BORDER_COLOR = new Color(0, 76, 153);

    /**
     * The background color for panels.
     */
    public static final Color PANEL_BACKGROUND_COLOR = new Color(204, 255, 255);

    /**
     * The background color for buttons.
     */
    public static final Color BUTTON_BACKGROUND_COLOR = new Color(0, 76, 153);

    /**
     * The foreground color for buttons.
     */
    public static final Color BUTTON_FOREGROUND_COLOR = new Color(255, 255, 255);

    /**
     * The background color for progress bar.
     */
    public static final Color PROGRESS_BAR_BACKGROUND_COLOR = new Color(255, 255, 255);

    /**
     * The foreground color for progress bar.
     */
    public static final Color PROGRESS_BAR_FOREGROUND_COLOR = new Color(0, 76, 153);

    /**
     * The color for the selected radio button text.
     */
    public static final Color SELECTED_RADIO_BUTTON_TEXT_COLOR = new Color(0, 0, 255);

}
