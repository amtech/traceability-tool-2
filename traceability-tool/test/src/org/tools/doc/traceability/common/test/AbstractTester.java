/**
 * 
 */
package org.tools.doc.traceability.common.test;

import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

/**
 * Base class for testers.
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractTester {

    /**
     * The directory for the test.
     */
    private File testDirectory;

    /**
     * The directory containing input files.
     */
    private File inputFileDirectory;

    /**
     * The directory containing output files.
     */
    private File outputFileDirectory;

    /**
     * Constructor.
     */
    public AbstractTester() {
        // On Eclipse, the current directory will be the directory of the project
        File lCurrentDir = new File(".");

        testDirectory = new File(lCurrentDir, "test");
        inputFileDirectory = new File(testDirectory, "input");
        outputFileDirectory = new File(testDirectory, "output");

        initializeLog4j();
    }

    /**
     * Getter of the test directory.
     * 
     * @return the testDirectory
     */
    public File getTestDirectory() {
        return testDirectory;
    }

    /**
     * Getter of the input files directory.
     * 
     * @return the inputFileDirectory
     */
    public File getInputFileDirectory() {
        return inputFileDirectory;
    }

    /**
     * Getter of the outputFileDirectory.
     * 
     * @return the outputFileDirectory
     */
    public File getOutputFileDirectory() {
        return outputFileDirectory;
    }

    /**
     * Initialize Log4j (as used by Joget when using LogUtil).
     */
    private void initializeLog4j() {
        // This is the root logger provided by log4j
        ConfigurationBuilder<BuiltConfiguration> lBuilder = ConfigurationBuilderFactory.newConfigurationBuilder();
        RootLoggerComponentBuilder lRootLogger = lBuilder.newRootLogger(Level.DEBUG);

        // Define a console appender
        AppenderComponentBuilder lConsole = lBuilder.newAppender("stdout", "Console");

        // Define log pattern layout
        LayoutComponentBuilder lStandard = lBuilder.newLayout("PatternLayout");
        lStandard.addAttribute("pattern", "  %d{ISO8601} [%t] %-5p %c %x%n%m%n");

        // Add the pattern layout to the appender
        lConsole.add(lStandard);
        // Add this appender to the builder 
        lBuilder.add(lConsole);

        // Add the appender to the logger
        lRootLogger.add(lBuilder.newAppenderRef("stdout"));

        // Add the logger
        lBuilder.add(lRootLogger);

        // Activate the configuration
        Configurator.initialize(lBuilder.build());
    }
}
