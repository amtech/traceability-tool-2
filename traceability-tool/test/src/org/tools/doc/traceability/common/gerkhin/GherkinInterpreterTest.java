/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.tools.doc.traceability.common.test.AbstractTester;

/**
 * JUnit for {@link GherkinInterpreter}.
 * 
 * @author Yann Leglise
 *
 */
public class GherkinInterpreterTest extends AbstractTester {

    /**
     * Test method for
     * {@link org.tools.doc.traceability.common.gerkhin.GherkinInterpreter#convertRawLines(java.util.List)}.
     */
    @Test
    public void testConvertRawLines() {

        GherkinInterpreter lSut = new GherkinInterpreter();

        // Fill in the raw Gherkin lines
        List<String> lRawLines = new ArrayList<String>();

        lRawLines.add(" # This is a comment"); // 1) Comment line
        lRawLines.add(" "); // Empty line : shall be ignored
        lRawLines.add(" Feature : My feature"); // 2) Feature line
        lRawLines.add("           My feature additional description"); // 3) Description line
        lRawLines.add(" "); // Empty line : shall be ignored
        lRawLines.add(" Background : My background"); // 4) Background line
        lRawLines.add(" Given My given"); // 5) Given line
        lRawLines.add(" And My and"); // 6) And line
        lRawLines.add(" "); // Empty line : shall be ignored
        lRawLines.add(" Scenario : My scenario"); // 7) Scenario line
        lRawLines.add("            My scenario additional description"); // 8) Description line
        lRawLines.add(" "); // Empty line : shall be ignored
        lRawLines.add(" When My when"); // 9) Given line
        lRawLines.add(" But My but"); // 10) But line
        lRawLines.add(" * My star"); // 11) Star line
        lRawLines.add(" Then My then"); // 12) But line
        lRawLines.add(" "); // Empty line : shall be ignored
        lRawLines.add(" Scenario Outline : My scenario outline"); // 13) Scenario Outline line
        lRawLines.add(" Given My given <var1> things"); // 14) Given line with parameter
        lRawLines.add(" When My when <var2> things"); // 15) When line with parameter
        lRawLines.add(" Then My then <var3> things"); // 16) Then line with parameter
        lRawLines.add(" "); // Empty line : shall be ignored
        lRawLines.add(" Examples : My examples"); // 17) Examples line
        lRawLines.add(" | var1 | var2 | var3 |"); // 18) Data table line (header)
        lRawLines.add(" | val1 | val2 | val3 |"); // 19) Data table line (values)
        lRawLines.add(" "); // Empty line : shall be ignored
        lRawLines.add(" Rule : My rule"); // 20) Rule line
        lRawLines.add(" Given My given with"); // 21) Given line
        lRawLines.add(" \"\"\" start"); // 22) Doc String start line
        lRawLines.add("      Doc String Line 1"); // 23) Description line
        lRawLines.add("      Doc String Line 2"); // 24) Description line
        lRawLines.add(" \"\"\" stop"); // 25) Doc String end line

        // Test the API
        List<GherkinLine> lGherkinLines = lSut.convertRawLines(lRawLines);

        Assert.assertEquals("The expected number of lines is not as expected", 25, lGherkinLines.size());

        GherkinLine lGherkinLine = lGherkinLines.get(0);
        Assert.assertEquals("Comment line not detected", GherkinLineType.Comment, lGherkinLine.getType());

        lGherkinLine = lGherkinLines.get(1);
        Assert.assertEquals("Feature line not detected", GherkinLineType.Feature, lGherkinLine.getType());
        Assert.assertEquals("Feature line contentt wrongly extracted", "My feature", lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(2);
        Assert.assertEquals("Description line not detected", GherkinLineType.Description, lGherkinLine.getType());
        Assert.assertEquals("Description line contentt wrongly extracted", "           My feature additional description",
                lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(3);
        Assert.assertEquals("Background line not detected", GherkinLineType.Background, lGherkinLine.getType());
        Assert.assertEquals("Background line contentt wrongly extracted", "My background",
                lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(4);
        Assert.assertEquals("Given line not detected", GherkinLineType.Given, lGherkinLine.getType());
        Assert.assertEquals("Given line contentt wrongly extracted", "My given", lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(5);
        Assert.assertEquals("And line not detected", GherkinLineType.And, lGherkinLine.getType());
        Assert.assertEquals("And line contentt wrongly extracted", "My and", lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(6);
        Assert.assertEquals("Scenario line not detected", GherkinLineType.Scenario, lGherkinLine.getType());
        Assert.assertEquals("Scenario line contentt wrongly extracted", "My scenario", lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(7);
        Assert.assertEquals("Description line not detected", GherkinLineType.Description, lGherkinLine.getType());
        Assert.assertEquals("Description line contentt wrongly extracted", "            My scenario additional description",
                lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(8);
        Assert.assertEquals("When line not detected", GherkinLineType.When, lGherkinLine.getType());
        Assert.assertEquals("When line contentt wrongly extracted", "My when", lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(9);
        Assert.assertEquals("But line not detected", GherkinLineType.But, lGherkinLine.getType());
        Assert.assertEquals("But line contentt wrongly extracted", "My but", lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(10);
        Assert.assertEquals("Star line not detected", GherkinLineType.Star, lGherkinLine.getType());
        Assert.assertEquals("Star line contentt wrongly extracted", "My star", lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(11);
        Assert.assertEquals("Then line not detected", GherkinLineType.Then, lGherkinLine.getType());
        Assert.assertEquals("Then line contentt wrongly extracted", "My then", lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(12);
        Assert.assertEquals("Scenario Outline line not detected", GherkinLineType.ScenarioOutline,
                lGherkinLine.getType());
        Assert.assertEquals("Scenario Outline line contentt wrongly extracted", "My scenario outline",
                lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(13);
        Assert.assertEquals("Given line not detected", GherkinLineType.Given, lGherkinLine.getType());
        Assert.assertEquals("Given line contentt wrongly extracted", "My given <var1> things",
                lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(14);
        Assert.assertEquals("When line not detected", GherkinLineType.When, lGherkinLine.getType());
        Assert.assertEquals("When line contentt wrongly extracted", "My when <var2> things",
                lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(15);
        Assert.assertEquals("Then line not detected", GherkinLineType.Then, lGherkinLine.getType());
        Assert.assertEquals("Then line contentt wrongly extracted", "My then <var3> things",
                lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(16);
        Assert.assertEquals("Examples line not detected", GherkinLineType.Examples, lGherkinLine.getType());
        Assert.assertEquals("Examples line contentt wrongly extracted", "My examples", lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(17);
        Assert.assertEquals("Data line not detected", GherkinLineType.DataTable, lGherkinLine.getType());
        Assert.assertEquals("Data line contentt wrongly extracted", "| var1 | var2 | var3 |",
                lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(18);
        Assert.assertEquals("Data line not detected", GherkinLineType.DataTable, lGherkinLine.getType());
        Assert.assertEquals("Data line contentt wrongly extracted", "| val1 | val2 | val3 |",
                lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(19);
        Assert.assertEquals("Rule line not detected", GherkinLineType.Rule, lGherkinLine.getType());
        Assert.assertEquals("Rule line contentt wrongly extracted", "My rule", lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(20);
        Assert.assertEquals("Given line not detected", GherkinLineType.Given, lGherkinLine.getType());
        Assert.assertEquals("Given line contentt wrongly extracted", "My given with", lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(21);
        Assert.assertEquals("DocString line not detected", GherkinLineType.DocString, lGherkinLine.getType());
        Assert.assertEquals("DocString line contentt wrongly extracted", " \"\"\" start", lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(22);
        Assert.assertEquals("Description line not detected", GherkinLineType.Description, lGherkinLine.getType());
        Assert.assertEquals("Description line contentt wrongly extracted", "      Doc String Line 1",
                lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(23);
        Assert.assertEquals("Description line not detected", GherkinLineType.Description, lGherkinLine.getType());
        Assert.assertEquals("Description line contentt wrongly extracted", "      Doc String Line 2",
                lGherkinLine.getLineContents());

        lGherkinLine = lGherkinLines.get(24);
        Assert.assertEquals("DocString line not detected", GherkinLineType.DocString, lGherkinLine.getType());
        Assert.assertEquals("DocString line contentt wrongly extracted", " \"\"\" stop", lGherkinLine.getLineContents());

    }

}
