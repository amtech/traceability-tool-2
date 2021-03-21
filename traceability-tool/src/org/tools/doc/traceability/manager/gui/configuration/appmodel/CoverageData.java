/**
 * 
 */
package org.tools.doc.traceability.manager.gui.configuration.appmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Class representing the coverage data.
 * 
 * @author Yann Leglise
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CoverageData extends AbstractConfigurationModel {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 48580068137596518L;

    /**
     * The name of the associated justification file.
     */
    @XmlElement(name = "JustificationFileName")
    private String justificationFilename;

    /**
     * The coverage configuration for ALM tests.
     */
    @XmlElement(name = "AlmTestsCoverage")
    private AlmTestsCoverage almTestsCoverage;

    /**
     * The coverage configuration for cucumber tests.
     */
    @XmlElement(name = "CucumberTestsCoverage")
    private CucumberTestsCoverage cucumberTestsCoverage;

    /**
     * The coverage configuration for C# unit tests.
     */
    @XmlElement(name = "CSharpUnitTestsCoverage")
    private CSharpUnitTestsCoverage cSharpUnitTestsCoverage;

    /**
     * The coverage configuration for java unit tests.
     */
    @XmlElement(name = "JavaUnitTestsCoverage")
    private JavaUnitTestsCoverage javaUnitTestsCoverage;

    /**
     * Constructor.
     */
    public CoverageData() {
        justificationFilename = "";
        almTestsCoverage = null;
        cucumberTestsCoverage = null;
        cSharpUnitTestsCoverage = null;
        javaUnitTestsCoverage = null;
    }

    /**
     * Getter of the associated justification file name.
     * 
     * @return the justificationFilename
     */
    public String getJustificationFilename() {
        return justificationFilename;
    }

    /**
     * Getter of the almTestsCoverage.
     * 
     * @return the almTestsCoverage
     */
    public AlmTestsCoverage getAlmTestsCoverage() {
        return almTestsCoverage;
    }

    /**
     * Setter of the almTestsCoverage.
     * 
     * @param pAlmTestsCoverage the almTestsCoverage to set
     */
    public void setAlmTestsCoverage(final AlmTestsCoverage pAlmTestsCoverage) {
        almTestsCoverage = pAlmTestsCoverage;
    }

    /**
     * Getter of the cucumberTestsCoverage.
     * 
     * @return the cucumberTestsCoverage
     */
    public CucumberTestsCoverage getCucumberTestsCoverage() {
        return cucumberTestsCoverage;
    }

    /**
     * Setter of the cucumberTestsCoverage.
     * 
     * @param pCucumberTestsCoverage the cucumberTestsCoverage to set
     */
    public void setCucumberTestsCoverage(final CucumberTestsCoverage pCucumberTestsCoverage) {
        cucumberTestsCoverage = pCucumberTestsCoverage;
    }

    /**
     * Getter of the cSharpUnitTestsCoverage.
     * 
     * @return the cSharpUnitTestsCoverage
     */
    public CSharpUnitTestsCoverage getcSharpUnitTestsCoverage() {
        return cSharpUnitTestsCoverage;
    }

    /**
     * Setter of the cSharpUnitTestsCoverage.
     * 
     * @param pCSharpUnitTestsCoverage the cSharpUnitTestsCoverage to set
     */
    public void setcSharpUnitTestsCoverage(final CSharpUnitTestsCoverage pCSharpUnitTestsCoverage) {
        cSharpUnitTestsCoverage = pCSharpUnitTestsCoverage;
    }

    /**
     * Getter of the javaUnitTestsCoverage.
     * 
     * @return the javaUnitTestsCoverage
     */
    public JavaUnitTestsCoverage getJavaUnitTestsCoverage() {
        return javaUnitTestsCoverage;
    }

    /**
     * Setter of the javaUnitTestsCoverage.
     * 
     * @param pJavaUnitTestsCoverage the javaUnitTestsCoverage to set
     */
    public void setJavaUnitTestsCoverage(final JavaUnitTestsCoverage pJavaUnitTestsCoverage) {
        javaUnitTestsCoverage = pJavaUnitTestsCoverage;
    }

    /**
     * Setter of the justificationFilename.
     * 
     * @param pJustificationFilename the justificationFilename to set
     */
    public void setJustificationFilename(final String pJustificationFilename) {
        justificationFilename = pJustificationFilename;
    }

}
