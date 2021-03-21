/**
 * 
 */
package org.tools.doc.traceability.manager.gui.data.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * List of {@link ApplicationData}.
 * 
 * @author Yann Leglise
 *
 */
public class ApplicationDataList implements Iterable<ApplicationData> {

    /**
     * Internal list.
     */
    private List<ApplicationData> applicationDataList;

    /**
     * Constructor.
     */
    public ApplicationDataList() {
        applicationDataList = new ArrayList<ApplicationData>();
    }

    /**
     * Adds an element.
     * 
     * @param pApplicationData the element to add.
     */
    public void add(final ApplicationData pApplicationData) {
        applicationDataList.add(pApplicationData);

        // Sort the list
        Collections.sort(applicationDataList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<ApplicationData> iterator() {
        return applicationDataList.iterator();
    }

    /**
     * Checks whether there is an element having the given application
     * identifier.
     * 
     * @param pAppId the application identifier to check.
     * @return <tt>true</tt> if an application data with the given identifier
     * could be found in the list, <tt>false</tt> if not.
     */
    public boolean containsElementWithApplicationIdentifier(final String pAppId) {
        return getElementMatchingApplicationIdentifier(pAppId) != null;
    }

    /**
     * Search for the element whose application identifier matches the one given
     * as parameter.
     * 
     * @param pAppId the application identifier to consider.
     * @return the matching ApplicationData, or <tt>null</tt> if none matched.
     */
    public ApplicationData getElementMatchingApplicationIdentifier(final String pAppId) {
        ApplicationData lMatchingApplicationData = null;

        for (ApplicationData lApplicationData : applicationDataList) {
            if (pAppId.compareTo(lApplicationData.getApplicationIdentifier()) == 0) {
                lMatchingApplicationData = lApplicationData;
                break;
            }
        }

        return lMatchingApplicationData;
    }

    /**
     * Get the first element of the list.
     * 
     * @return the first element of the list, or <tt>null</tt> if the list is
     * empty.
     */
    public ApplicationData getFirst() {
        ApplicationData lFirstElt = null;

        if (!applicationDataList.isEmpty()) {
            lFirstElt = applicationDataList.get(0);
        }

        return lFirstElt;
    }

}
