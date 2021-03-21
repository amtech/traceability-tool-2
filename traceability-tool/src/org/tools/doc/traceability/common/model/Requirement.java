/**
 * 
 */
package org.tools.doc.traceability.common.model;

/**
 * Object modeling a requirement.
 * 
 * @author Yann Leglise
 *
 */
public class Requirement implements Comparable<Requirement> {

    /**
     * Requirement identifier.
     */
    private final String identifier;

    /**
     * Constructor.
     * 
     * @param pIdentifier identifier of the requirement.
     */
    public Requirement(final String pIdentifier) {
        // Make sure the identifier is never null
        if (pIdentifier != null) {
            identifier = pIdentifier;
        } else {
            identifier = "";
        }
    }

    /**
     * Checks whether this instance identifier matches the given one.
     * 
     * @param pReqText the requirement text identifier.
     * @return <tt>true</tt> if this instance matches the given identifier,
     * <tt>false</tt> otherwise.
     */
    public boolean matches(final String pReqText) {
        return identifier.equals(pReqText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return identifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final Requirement pOther) {
        return identifier.compareTo(pOther.identifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object pObj) {
        boolean lEquals = false;
        if (pObj instanceof Requirement) {
            Requirement lOtherReq = (Requirement) pObj;
            lEquals = identifier.compareTo(lOtherReq.identifier) == 0;
        }
        return lEquals;
    }

}
