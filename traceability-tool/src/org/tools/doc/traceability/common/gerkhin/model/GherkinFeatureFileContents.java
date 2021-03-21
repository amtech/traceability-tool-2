/**
 * 
 */
package org.tools.doc.traceability.common.gerkhin.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.tools.doc.traceability.common.gerkhin.model.container.GherkinFeature;
import org.tools.doc.traceability.common.gerkhin.model.general.GherkinComment;

/**
 * Class modeling the contents of a Gherkin feature file.
 * <p>
 * It contains the Feature element, but it also refer to the source file.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class GherkinFeatureFileContents {

    /**
     * The Gherkin feature file from which the contents was read.
     */
    private final File gherkinFeatureSourceFile;

    /**
     * The potential comment lines occurring before the Feature.
     */
    private final List<GherkinComment> leadingCommentLines;

    /**
     * The Gherkin Feature element read from the file.
     */
    private GherkinFeature feature;

    /**
     * Constructor.
     * 
     * @param pGherkinFeatureSourceFile The Gherkin feature file from which the
     *                                  contents was read.
     */
    public GherkinFeatureFileContents(final File pGherkinFeatureSourceFile) {
        super();
        gherkinFeatureSourceFile = pGherkinFeatureSourceFile;
        leadingCommentLines = new ArrayList<GherkinComment>();
    }

    /**
     * Getter of the feature.
     * 
     * @return the feature
     */
    public GherkinFeature getFeature() {
        return feature;
    }

    /**
     * Setter of the feature.
     * 
     * @param pFeature the feature to set
     */
    public void setFeature(final GherkinFeature pFeature) {
        feature = pFeature;
    }

    /**
     * Adds a comment line appearing ahead of the Feature line.
     * 
     * @param pLeadingCommentLine the comment line to add.
     */
    public void addLeadingCommentLine(final GherkinComment pLeadingCommentLine) {
        leadingCommentLines.add(pLeadingCommentLine);
    }

    /**
     * Getter of the Gherkin Feature source file.
     * 
     * @return the gherkinFeatureSourceFile
     */
    public File getGherkinFeatureSourceFile() {
        return gherkinFeatureSourceFile;
    }

    /**
     * Getter of the leading comment lines.
     * 
     * @return the leadingCommentLines
     */
    public List<GherkinComment> getLeadingCommentLines() {
        return leadingCommentLines;
    }

    /**
     * Print the contents of this instance on the standard output.
     */
    public void printOnStdout() {
        if (gherkinFeatureSourceFile != null) {
            System.out.println("Contents of " + gherkinFeatureSourceFile.getAbsolutePath());

            // Print leading comments
            for (GherkinComment lLeadingCommentLine : leadingCommentLines) {
                lLeadingCommentLine.printOnStdout(0);
            }

            // Print the feature (if any)
            if (feature != null) {
                feature.printOnStdout(0);
            } else {
                System.out.println("No Feature");
            }
        } else {
            System.out.println("No input file defined");
        }
    }
}
