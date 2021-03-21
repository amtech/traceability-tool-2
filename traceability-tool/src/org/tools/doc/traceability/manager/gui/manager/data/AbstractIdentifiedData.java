/**
 * 
 */
package org.tools.doc.traceability.manager.gui.manager.data;

/**
 * Common class for identified data.
 * <p>
 * Make it sortable through display name.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public abstract class AbstractIdentifiedData implements IdentifiedData, Comparable<AbstractIdentifiedData> {

    /**
     * Counter for data identifiers.
     */
    private static int DataIdentifier = 0;

    /**
     * Get the next available data identifier.
     * 
     * @return the next available data identifier.
     */
    private static synchronized int getNextDataIdentifier() {
        return DataIdentifier++;
    }

    /**
     * The unique identifier associated with this data.
     */
    private final int identifier;

    /**
     * The display name of the application.
     */
    private final String displayName;

    /**
     * Constructor.
     * 
     * @param pDisplayName the display name.
     */
    public AbstractIdentifiedData(final String pDisplayName) {
        super();
        identifier = getNextDataIdentifier();
        displayName = pDisplayName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIdentifier() {
        return identifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName() {
        return displayName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean matches(final IdentifiedData pOther) {
        boolean lMatch = false;
        if (pOther != null) {
            lMatch = identifier == pOther.getIdentifier();
        }
        return lMatch;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return displayName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final AbstractIdentifiedData pOther) {
        int lCompareVal = 0;

        if (pOther == null) {
            lCompareVal = 1;
        } else {
            lCompareVal = displayName.compareTo(pOther.getDisplayName());
        }

        return lCompareVal;
    }
}
