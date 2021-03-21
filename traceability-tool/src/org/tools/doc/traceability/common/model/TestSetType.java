package org.tools.doc.traceability.common.model;

/**
 * Models the possible test set types.
 * 
 * 
 * @author Yann Leglise
 *
 */
public enum TestSetType {

    /**
     * Tests from HP ALM.
     */
    AlmTest("ALM tests", "ALM test", true),

    /**
     * Tests from Cucumber.
     */
    CucumberTest("Cucumber tests", "Cucumber test", false),

    /**
     * Unit tests from C#.
     */
    CSharpUnitTest("C# unit tests", "C# unit test", false),

    /**
     * Unit tests from java.
     */
    JavaUnitTest("Java unit tests", "C# unit test", false),

    ;

    /**
     * The name of the sheet associated with this kind of test coverage in the
     * destination VTP.
     */
    private final String associatedVtpSheetName;

    /**
     * The label used at HMI level (and in logs) to refer to this kind of test
     * coverage.
     */
    private final String hmiLabel;

    /**
     * Flag indicating of this kind of test is manual (<tt>true</tt>) or
     * automatic (<tt>false</tt>).
     */
    private final boolean isManual;

    /**
     * Constructor.
     * 
     * @param pAssociatedVtpSheetName the name of the sheet associated with this
     * kind of test coverage in the destination VTP.
     * @param pHmiLabel the label used at HMI level (and in logs) to refer to
     * this kind of test coverage.
     * @param pIsManual flag indicating of this kind of test is manual (
     * <tt>true</tt>) or automatic (<tt>false</tt>).
     */
    TestSetType(final String pAssociatedVtpSheetName, final String pHmiLabel, final boolean pIsManual) {
        associatedVtpSheetName = pAssociatedVtpSheetName;
        hmiLabel = pHmiLabel;
        isManual = pIsManual;
    }

    /**
     * Getter of the associated VTP sheet name.
     * 
     * @return the associatedVtpSheetName
     */
    public String getAssociatedVtpSheetName() {
        return associatedVtpSheetName;
    }

    /**
     * Getter of the HMI label.
     * 
     * @return the hmiLabel
     */
    public String getHmiLabel() {
        return hmiLabel;
    }

    /**
     * Getter of the flag indicating of this kind of test is manual or not.
     * 
     * @return <tt>true</tt> for manual test, (<tt>false</tt> for automatic
     * test.
     */
    public boolean isManual() {
        return isManual;
    }
}
