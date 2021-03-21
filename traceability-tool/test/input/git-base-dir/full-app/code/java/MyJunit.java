/**
 * 
 */
package org.foo.test;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;


/**
 * JUnit for {@link Algorith5Test}.
 * 
 * @author Yann Leglise
 *
 */
public class Algorith5TestTester extends AbstractTester {

	/**
	 * Test method for
	 * {@link org.foo..Algorithm5#run()}.
	 * 
	 * @testId "MyTestIdLocal"
	 * 
	 * @expectedResult "I expect this result"
	 * @coveredReqs "REQ-FULL-ALG-060"
	 */
	@Test
	public void testIgnored() {

	}

	/**
	 * Test method for
	 * {@link org.foo..Algorithm5#run()}
	 * 
	 * * @testId "MyTestId"
	 * 
	 * @expectedResult "I expect this result"
	 * @coveredReqs "REQ-FULL-ALG-070, REQ-FULL-ALG-080"
	 */
	@Test
	public void VTP_testRun() {

		try {
			lSut.run();

			if (lExecutionStatus.getCurrentExecutionStatus() == ExecutionStatus.ENDED_SUCCESS) {
				System.out.println("Success");
			} else {
				Assert.fail("Execution failed : "
						+ lExecutionStatus.getExecutionStatusDescription());
			}
		} catch (Exception e) {
			Assert.fail("Execution failed due to exception : " + e.getMessage());
		}
	}
}
