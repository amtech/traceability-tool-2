/**
 * 
 */
package org.tools.doc.traceability.covmatrixgen.model;

import java.util.List;

import org.tools.doc.traceability.common.model.Requirement;
import org.tools.doc.traceability.common.model.TestSetType;

/**
 * Models the covering of a requirement by a specific test.
 * 
 * @author Yann Leglise
 *
 */
public interface IRequirementCoveringData {

    /**
     * Indicates the type of the test.
     * 
     * @return the test type.
     */
    TestSetType getType();

    /**
     * The identifier of the test case.
     * 
     * @return the test case identifier.
     */
    String getTestCaseIdentifier();

    /**
     * The name of the test case.
     * 
     * @return the test case name.
     */
    String getTestCaseName();

    /**
     * Gives more details about the origin of the test (maybe a source file, or
     * any information helping to find where the test comes from).
     * 
     * @return the test origin details.
     */
    String getOriginDetails();

    /**
     * Get the list of the potential covered requirements.
     * 
     * @return the list of covered requirements (can be empty).
     */
    List<Requirement> getCoveredRequirementList();
}
