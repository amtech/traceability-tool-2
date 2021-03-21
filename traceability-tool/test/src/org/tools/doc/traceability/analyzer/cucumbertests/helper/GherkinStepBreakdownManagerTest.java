package org.tools.doc.traceability.analyzer.cucumbertests.helper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.tools.doc.traceability.common.gerkhin.model.general.AbstractGherkinStep;
import org.tools.doc.traceability.common.gerkhin.model.step.GherkinStepAnd;
import org.tools.doc.traceability.common.gerkhin.model.step.GherkinStepBut;
import org.tools.doc.traceability.common.gerkhin.model.step.GherkinStepGiven;
import org.tools.doc.traceability.common.gerkhin.model.step.GherkinStepStar;
import org.tools.doc.traceability.common.gerkhin.model.step.GherkinStepThen;
import org.tools.doc.traceability.common.gerkhin.model.step.GherkinStepWhen;
import org.tools.doc.traceability.common.model.Requirement;
import org.tools.doc.traceability.common.test.AbstractTester;

/**
 * JUnit for {@link GherkinStepBreakdownManager}.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinStepBreakdownManagerTest extends AbstractTester {

    /**
     * Tests {@link GherkinStepBreakdownManager#breakDownSteps(java.util.List)}.
     */
    @Test
    public void testBreakDownSteps() {

        GherkinStepBreakdownManager lSut = new GherkinStepBreakdownManager();

        List<AbstractGherkinStep> lSteps = new ArrayList<AbstractGherkinStep>();

        lSteps.add(new GherkinStepGiven(1, "Given 1"));
        lSteps.add(new GherkinStepGiven(2, "Given 2"));
        lSteps.add(new GherkinStepAnd(3, "And 1"));
        lSteps.add(new GherkinStepBut(4, "But 1"));
        lSteps.add(new GherkinStepStar(5, "* 1"));
        lSteps.add(new GherkinStepWhen(6, "When 1"));
        lSteps.add(new GherkinStepThen(7, "Then 1"));
        lSteps.add(new GherkinStepThen(8, "Reference SD SD-REQ-01 SD-REQ-02"));
        lSteps.add(new GherkinStepAnd(9, "Reference SD \"SD-REQ-03\""));

        TestingScenarioBreakdown lTestingScenarioBreakdown = lSut.breakDownSteps(lSteps);
        Assert.assertEquals(1, lTestingScenarioBreakdown.getTestingScenarioParts().size());

        lSteps.add(new GherkinStepGiven(10, "Given 3"));
        lSteps.add(new GherkinStepGiven(11, "Given 4"));
        lSteps.add(new GherkinStepAnd(12, "And 2"));
        lSteps.add(new GherkinStepBut(13, "But 2"));
        lSteps.add(new GherkinStepStar(14, "* 3"));
        lSteps.add(new GherkinStepWhen(15, "When 2"));
        lSteps.add(new GherkinStepThen(16, "Then 3"));
        lSteps.add(new GherkinStepThen(17, "Reference  SD : \"SD-REQ-04, SD-REQ-05\""));
        lSteps.add(new GherkinStepAnd(18, "Reference  SD : \"SD-REQ-06 SD-REQ-07\""));

        lTestingScenarioBreakdown = lSut.breakDownSteps(lSteps);
        Assert.assertEquals(2, lTestingScenarioBreakdown.getTestingScenarioParts().size());

        TestingScenarioPart lFirstPart = lTestingScenarioBreakdown.getTestingScenarioParts().get(0);
        List<Requirement> lFirstPartCoveredReqs = lFirstPart.getCoveredRequirements();

        Assert.assertEquals("The covered requirements in the first part were not found", 3,
                lFirstPartCoveredReqs.size());

        boolean req1Part1Found = false;
        boolean req2Part1Found = false;
        boolean req3Part1Found = false;
        for (Requirement coveredReq : lFirstPart.getCoveredRequirements()) {
            if (coveredReq.matches("SD-REQ-01")) {
                req1Part1Found = true;
            } else if (coveredReq.matches("SD-REQ-02")) {
                req2Part1Found = true;
            } else if (coveredReq.matches("SD-REQ-03")) {
                req3Part1Found = true;
            }
        }

        Assert.assertTrue("Req SD-REQ-01 was not found for part 1", req1Part1Found);
        Assert.assertTrue("Req SD-REQ-02 was not found for part 1", req2Part1Found);
        Assert.assertTrue("Req SD-REQ-03 was not found for part 1", req3Part1Found);

        TestingScenarioPart lSecondPart = lTestingScenarioBreakdown.getTestingScenarioParts().get(1);
        List<Requirement> lSecondPartCoveredReqs = lSecondPart.getCoveredRequirements();

        Assert.assertEquals("The covered requirements in the second part were not found", 4,
                lSecondPartCoveredReqs.size());

        boolean req1Part2Found = false;
        boolean req2Part2Found = false;
        boolean req3Part2Found = false;
        boolean req4Part2Found = false;
        for (Requirement coveredReq : lSecondPart.getCoveredRequirements()) {
            if (coveredReq.matches("SD-REQ-04")) {
                req1Part2Found = true;
            } else if (coveredReq.matches("SD-REQ-05")) {
                req2Part2Found = true;
            } else if (coveredReq.matches("SD-REQ-06")) {
                req3Part2Found = true;
            } else if (coveredReq.matches("SD-REQ-07")) {
                req4Part2Found = true;
            }
        }

        Assert.assertTrue("Req SD-REQ-04 was not found for part 2", req1Part2Found);
        Assert.assertTrue("Req SD-REQ-05 was not found for part 2", req2Part2Found);
        Assert.assertTrue("Req SD-REQ-06 was not found for part 2", req3Part2Found);
        Assert.assertTrue("Req SD-REQ-07 was not found for part 2", req4Part2Found);

        System.out.println(lTestingScenarioBreakdown.toString());
    }

    /**
     * Tests {@link GherkinStepBreakdownManager#breakDownSteps(java.util.List)}
     * with only one Then step.
     */
    @Test
    public void testBreakDownStepsOnlyOneThen() {

        GherkinStepBreakdownManager lSut = new GherkinStepBreakdownManager();

        List<AbstractGherkinStep> lSteps = new ArrayList<AbstractGherkinStep>();

        lSteps.add(new GherkinStepThen(1, "Then 1"));

        TestingScenarioBreakdown lTestingScenarioBreakdown = lSut.breakDownSteps(lSteps);
        Assert.assertEquals(1, lTestingScenarioBreakdown.getTestingScenarioParts().size());

        TestingScenarioPart lPart = lTestingScenarioBreakdown.getTestingScenarioParts().get(0);
        Assert.assertEquals(0, lPart.getActionSteps().size());
        Assert.assertEquals(1, lPart.getExpectedResultSteps().size());

        System.out.println(lTestingScenarioBreakdown.toString());
    }
}
