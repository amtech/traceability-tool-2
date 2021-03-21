/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration.appmodel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * Models the specification data of an application.
 * 
 * @author Yann Leglise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SpecificationData extends AbstractConfigurationModel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 9132965281639032799L;

    /**
     * The name of the associated specification document.
     */
    @XmlElement(name = "SdFileName")
    private String specificationDocumentFilename;

    /**
     * The requirement prefix.
     */
    @XmlElementWrapper(name = "RequirementPrefixes")
    @XmlElement(name = "RequirementPrefix")
    private List<String> requirementPrefixes;

    /**
     * Constructor.
     */
    public SpecificationData() {
        specificationDocumentFilename = "";
        requirementPrefixes = null;
    }

    /**
     * Getter of the specification Document File name.
     * 
     * @return the specificationDocumentFilename
     */
    public String getSpecificationDocumentFilename() {
        return specificationDocumentFilename;
    }

    /**
     * Getter of the requirementPrefixes.
     * 
     * @return the requirementPrefixes
     */
    public List<String> getRequirementPrefixes() {
        return requirementPrefixes;
    }
}
