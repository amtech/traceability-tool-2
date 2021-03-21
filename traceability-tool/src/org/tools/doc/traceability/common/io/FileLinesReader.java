/**
 * 
 */
package org.tools.doc.traceability.common.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.tools.doc.traceability.common.exceptions.FileReadingException;

/**
 * Utility class to read the contents of an text file to return the list of
 * contained lines.
 * 
 * @author Yann Leglise
 *
 */
public class FileLinesReader {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LogManager.getLogger(FileLinesReader.class);

    /**
     * Constructor.
     */
    public FileLinesReader() {
    }

    /**
     * Read the contents of the given file and return the list of read lines.
     * 
     * @param pFile the file to read.
     * @return the list of read lines.
     * @throws FileReadingException if an error occurs.
     */
    public List<String> readFileContents(final File pFile) throws FileReadingException {
        List<String> lReadLines = new ArrayList<String>();

        // First check argument is not null

        if (pFile == null) {
            throw new FileReadingException("Parameter of readFileContents is null");
        } else {
            BufferedReader lBufReader = null;
            FileReader lFileReader = null;

            try {
                // Create the reader on the file
                lFileReader = new FileReader(pFile);
                // Use a buffered reader to read line by line
                lBufReader = new BufferedReader(lFileReader);

                String lLine = lBufReader.readLine();

                while (lLine != null) {
                    lReadLines.add(lLine);
                    lLine = lBufReader.readLine();
                }

            } catch (FileNotFoundException e) {
                throw new FileReadingException("File " + pFile.getAbsolutePath() + " does not exist");
            } catch (IOException e) {
                LOGGER.debug("Error while reading file " + pFile.getAbsolutePath() + " : " + e);
                throw new FileReadingException(
                        "Error while reading file " + pFile.getAbsolutePath() + " : " + e.getMessage());
            } finally {
                if (lBufReader != null) {
                    try {
                        lBufReader.close();
                    } catch (IOException e) {
                        // No consequence on result, so just log
                        LOGGER.warn(
                                "Error closing buffered reader on " + pFile.getAbsolutePath() + " : " + e.getMessage());
                    }
                }
                if (lFileReader != null) {
                    try {
                        lFileReader.close();
                    } catch (IOException e) {
                        // No consequence on result, so just log
                        LOGGER.warn("Error closing file reader on " + pFile.getAbsolutePath() + " : " + e.getMessage());
                    }
                }
            }
        }

        return lReadLines;
    }
}
