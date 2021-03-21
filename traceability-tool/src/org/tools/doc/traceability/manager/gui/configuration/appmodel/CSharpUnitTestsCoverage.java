/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration.appmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Coverage for C# unit tests.
 * 
 * @author Yann Leglise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CSharpUnitTestsCoverage extends AbstractConfigurationModel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 6327864770515277782L;

    /**
     * Whether the coverage is active or not.
     */
    @XmlAttribute(name = "isActive")
    private boolean isActive;

    /**
     * The file selection filter.
     */
    @XmlElement(name = "FileSelectionFilter")
    private FileSelectionFilter fileSelectionFilter;

    /**
     * The simple regular expression for method names.
     */
    @XmlElement(name = "MethodSimpleRegexp")
    private String methodNameSimpleRegexp;

    /**
     * Constructor.
     */
    public CSharpUnitTestsCoverage() {
        isActive = false;
        fileSelectionFilter = null;
        methodNameSimpleRegexp = "";
    }

    /**
     * Getter of the isActive.
     * 
     * @return the isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Setter of the isActive.
     * 
     * @param pIsActive the isActive to set
     */
    public void setActive(final boolean pIsActive) {
        isActive = pIsActive;
    }

    /**
     * Getter of the fileSelectionFilter.
     * 
     * @return the fileSelectionFilter
     */
    public FileSelectionFilter getFileSelectionFilter() {
        return fileSelectionFilter;
    }

    /**
     * Setter of the fileSelectionFilter.
     * 
     * @param pFileSelectionFilter the fileSelectionFilter to set
     */
    public void setFileSelectionFilter(final FileSelectionFilter pFileSelectionFilter) {
        fileSelectionFilter = pFileSelectionFilter;
    }

    /**
     * Getter of the methodNameSimpleRegexp.
     * 
     * @return the methodNameSimpleRegexp
     */
    public String getMethodNameSimpleRegexp() {
        return methodNameSimpleRegexp;
    }

    /**
     * Setter of the methodNameSimpleRegexp.
     * 
     * @param pMethodNameSimpleRegexp the methodNameSimpleRegexp to set
     */
    public void setMethodNameSimpleRegexp(final String pMethodNameSimpleRegexp) {
        methodNameSimpleRegexp = pMethodNameSimpleRegexp;
    }

}
