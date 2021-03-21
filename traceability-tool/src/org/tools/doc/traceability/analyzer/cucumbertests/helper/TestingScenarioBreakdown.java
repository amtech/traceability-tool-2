package org.tools.doc.traceability.analyzer.cucumbertests.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Models the break down of a Scenario in parts.
 * 
 * @author Yann Leglise
 *
 */
public class TestingScenarioBreakdown {

    /**
     * The list of testing scenario parts.
     */
    private final List<TestingScenarioPart> testingScenarioParts;

    /**
     * Constructor.
     */
    public TestingScenarioBreakdown() {
        testingScenarioParts = new ArrayList<TestingScenarioPart>();
    }

    /**
     * Adds the given testing scenario part.
     * 
     * @param pPart the testing scenario part to add.
     */
    public void addPart(final TestingScenarioPart pPart) {
        testingScenarioParts.add(pPart);
    }

    /**
     * Get the list of the included testing scenario parts.
     * 
     * @return the testing scenario parts.
     */
    public List<TestingScenarioPart> getTestingScenarioParts() {
        return testingScenarioParts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder lSb = new StringBuilder();

        int lPartNum = 1;
        int lPartNumCount = testingScenarioParts.size();
        for (TestingScenarioPart lTestingScenarioPart : testingScenarioParts) {
            if (lPartNum > 1) {
                lSb.append("\n");
            }
            lSb.append("Part ");
            lSb.append(lPartNum);
            lSb.append("/");
            lSb.append(lPartNumCount);
            lSb.append(" : ");
            lSb.append(lTestingScenarioPart);

            lPartNum++;
        }

        return lSb.toString();
    }
}
