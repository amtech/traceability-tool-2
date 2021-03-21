/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.tools.config;

import org.tools.doc.traceability.common.exceptions.InvalidConfigurationException;

/**
 * An interface to allow checking the configuration of a tool.
 * 
 * @author Yann Leglise
 *
 */
public interface ICheckableToolConfiguration {

    /**
     * Checks whether the configuration is valid. This is the case if no exception
     * is thrown.
     * 
     * @throws InvalidConfigurationException if the configuration is not valid (The
     *                                       reason is in the exception message).
     */
    void checkIsValid() throws InvalidConfigurationException;

}
