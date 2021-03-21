/**
 * 
 */
package org.tools.doc.traceability.common.filesearch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.tools.doc.traceability.common.exceptions.FileSearchException;
import org.tools.doc.traceability.common.exceptions.InvalidFileSearchFilterException;
import org.tools.doc.traceability.common.exceptions.InvalidSimpleRegexpException;
import org.tools.doc.traceability.common.sregex.SimpleRegex;
import org.tools.doc.traceability.common.test.AbstractTester;

/**
 * JUnit for {@link FileSearcher}.
 * 
 * @author Yann Leglise
 *
 */
public class FileSearcherTest extends AbstractTester {

    /**
     * Test for {@link FileSearcher#search()}.
     */
    @Test
    public void testSearch() {

        // Add the filters

        File lSearchRootDir1 = new File(getInputFileDirectory(), "search");
        File lSearchRootDir2 = new File(lSearchRootDir1, "subdir2");

        /**
         * Test directory contents:
         * 
         * <pre>
         * Directory                    Filter 1             Filter 2
         *   file0001.txt
         *   file0008.ext                  X
         *   file0009.bin
         *     subdir1
         *       file0002.txt
         *       file0003.ext              X
         *       file0010.bin
         *       subsubdir1
         *         file0004.ext            X
         *       subsubdir2
         *         file0005.txt
         *         file0011.bin
         *     subdir2
         *       file0006.ext              X
         *       file0012.bin                                  X
         *       file0013.bin                                  X
         *       subsubdir3
         *         subsubsubdir1
         *           file0007.ext          X
         * </pre>
         * 
         * 
         */

        FileSearchFilterSet lFileSearchFilterSet = new FileSearchFilterSet();
        try {
            SimpleRegex lExtSr = new SimpleRegex("*.ext");
            lFileSearchFilterSet.addFilter(lSearchRootDir1, true, lExtSr);
            SimpleRegex lBinSr = new SimpleRegex("*.bin");
            lFileSearchFilterSet.addFilter(lSearchRootDir2, false, lBinSr);
        } catch (InvalidFileSearchFilterException e) {
            Assert.fail("Could not add a filter : " + e.getMessage());
        } catch (InvalidSimpleRegexpException e) {
            Assert.fail("Could not add a filter : " + e.getMessage());
        }

        // Create the system under test
        FileSearcher lSut = new FileSearcher(lFileSearchFilterSet);

        try {
            // Search for the matching files
            List<File> lFoundFiles = lSut.search();

            Assert.assertEquals("The number of found file is not as expected", 7, lFoundFiles.size());

            // Collect the matching file names
            List<String> lMatchingFileNames = new ArrayList<String>();
            for (File lMatchingFile : lFoundFiles) {
                lMatchingFileNames.add(lMatchingFile.getName());
            }

            // Make sure we found the files we searched
            Assert.assertTrue("File file0008.ext shall have been found", lMatchingFileNames.contains("file0008.ext"));
            Assert.assertTrue("File file0003.ext shall have been found", lMatchingFileNames.contains("file0003.ext"));
            Assert.assertTrue("File file0004.ext shall have been found", lMatchingFileNames.contains("file0004.ext"));
            Assert.assertTrue("File file0006.ext shall have been found", lMatchingFileNames.contains("file0006.ext"));
            Assert.assertTrue("File file0007.ext shall have been found", lMatchingFileNames.contains("file0007.ext"));
            Assert.assertTrue("File file0012.bin shall have been found", lMatchingFileNames.contains("file0012.bin"));
            Assert.assertTrue("File file0013.bin shall have been found", lMatchingFileNames.contains("file0013.bin"));

        } catch (FileSearchException e) {
            Assert.fail("Could not perform the search normally : " + e.getMessage());
        }
    }

}
