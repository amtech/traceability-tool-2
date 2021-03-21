/**
 * 
 */
package org.tools.doc.traceability.covmatrixgen.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tools.doc.traceability.common.model.Requirement;

/**
 * Models the covering of a requirement by tests.
 * 
 * @author Yann Leglise
 *
 */
public class RequirementTestCovering {

    /**
     * The internal map associating with each requirement the list of tests that
     * covers it.
     */
    private Map<Requirement, List<IRequirementCoveringData>> reqCovDataListMap;

    /**
     * Constructor.
     */
    public RequirementTestCovering() {
        reqCovDataListMap = new HashMap<Requirement, List<IRequirementCoveringData>>();
    }

    /**
     * Indicates whether this instance references at least one requirement.
     * 
     * @return <tt>true</tt> if there is at least one requirement referenced,
     * <tt>false</tt> otherwise.
     */
    public boolean containsCoveredRequirements() {
        return (reqCovDataListMap.size() > 0);
    }

    /**
     * Adds an association between the given requirement and the covering data.
     * 
     * @param pRequirement the covered requirement.
     * @param lRequirementCoveringData the covering data covering this
     * requirement.
     */
    public void addRequirementCovering(final Requirement pRequirement,
            final IRequirementCoveringData lRequirementCoveringData) {
        List<IRequirementCoveringData> lReqCovDataList;

        if (reqCovDataListMap.containsKey(pRequirement)) {
            lReqCovDataList = reqCovDataListMap.get(pRequirement);
        } else {
            lReqCovDataList = new ArrayList<IRequirementCoveringData>();
            reqCovDataListMap.put(pRequirement, lReqCovDataList);
        }

        lReqCovDataList.add(lRequirementCoveringData);
    }

    /**
     * Builds a list with all the covered requirements, sorted alphabetically.
     * 
     * @return the sorted list of covered requirements.
     */
    public List<Requirement> getSortedListOfCoveredRequirements() {
        List<Requirement> lSortedReqList = new ArrayList<Requirement>();
        lSortedReqList.addAll(reqCovDataListMap.keySet());

        Collections.sort(lSortedReqList);

        return lSortedReqList;
    }

    /**
     * Gets the list of requirement covering data for the given requirement.
     * 
     * @param pRequirement the requirement to consider.
     * @return the list of requirement covering data for the given requirement,
     * or an empty list if this requirement is not covered.
     */
    public List<IRequirementCoveringData> getRequirementCoveringDataListFor(final Requirement pRequirement) {
        List<IRequirementCoveringData> lRequirementCoveringDataList;
        if (reqCovDataListMap.containsKey(pRequirement)) {
            lRequirementCoveringDataList = reqCovDataListMap.get(pRequirement);
        } else {
            lRequirementCoveringDataList = new ArrayList<IRequirementCoveringData>();
        }
        return lRequirementCoveringDataList;
    }

    /**
     * Compute the total number of test cases found that covers at least one
     * requirements.
     * 
     * @return the number of covering test cases.
     */
    public int computeTotalTestCaseNumber() {
        Set<String> lTestCaseIdSet = new HashSet<String>();

        for (List<IRequirementCoveringData> lRcdList : reqCovDataListMap.values()) {
            for (IRequirementCoveringData lRcd : lRcdList) {
                lTestCaseIdSet.add(lRcd.getTestCaseIdentifier());
            }
        }

        return lTestCaseIdSet.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder lSb = new StringBuilder();

        Requirement lReq;
        List<IRequirementCoveringData> lReqCovDataList;
        boolean lIsFirst = true;
        for (Map.Entry<Requirement, List<IRequirementCoveringData>> lEntry : reqCovDataListMap.entrySet()) {
            if (lIsFirst) {
                lIsFirst = false;
            } else {
                lSb.append("\n");
            }
            lReq = lEntry.getKey();
            lReqCovDataList = lEntry.getValue();
            lSb.append("[");
            lSb.append(lReq);
            lSb.append("]");

            for (IRequirementCoveringData lReqCovData : lReqCovDataList) {
                lSb.append("\n\t");
                lSb.append(lReqCovData);
            }
        }

        return lSb.toString();
    }
}
