/**
 * 
 */
package org.tools.doc.traceability.manager.processor;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import org.tools.doc.traceability.common.exceptions.InvalidFileSearchFilterException;
import org.tools.doc.traceability.common.exceptions.InvalidSimpleRegexpException;
import org.tools.doc.traceability.common.executor.ExecutionStatus;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.filesearch.FileSearchFilter;
import org.tools.doc.traceability.common.sregex.SimpleRegex;
import org.tools.doc.traceability.common.test.AbstractTester;

/**
 * JUnit for {@link TraceabilityManager}.
 * 
 * @author Yann Leglise
 *
 */
public class TraceabilityManagerTester extends AbstractTester {


	/**
	 * Test method for
	 * {@link org.tools.doc.traceability.common.executor.AbstractExecutor#run()}
	 * 
	 * * @testId "MyTestId"
	 * 
	 * @expectedResult "I expect this result
	 *   and more explanation"
	 * @coveredReqs "SD-AIM-F11-030, SD-AIM-FT2-010"
	 */
	@Test
	public void testRun() {

		File lGitRootDir = new File(getInputFileDirectory(), "git-sample");
		File lDocumentationDir = new File(lGitRootDir, "Documentation");
		File lDocVtpDir = new File(lDocumentationDir, "VTP");
		File lJustificationFile = new File(lDocVtpDir,
				"20170404-172049-ALB-ADBPROD-JUS.xlsx");

		File lJogetPluginsDir = new File(lGitRootDir, "JogetPlugins");
		File lAimToolDir = new File(lJogetPluginsDir, "aim-tool");

		File lCSharpRootDirectory = new File(lAimToolDir, "CSharp");

		File lCucumberRootDirectory = new File(lAimToolDir, "Cucumber");

		File lAlmDir = new File(lAimToolDir, "ALM_extract");
		File lAlmExtractFile = new File(lAlmDir, "VTP_AIM.xlsm");

		File lAimDocDir = new File(lAimToolDir, "Documentation");

		File lAimVtpDir = new File(lAimDocDir, "VTP");
		File lAimVtp = new File(lAimVtpDir, "20151103-111800-AIM-VTP.xlsx");

		File lAimSdDir = new File(lAimDocDir, "SD");
		File lAimSd = new File(lAimSdDir, "20201106-100700-AIM-SD.docx");

		File lAimSdReqDir = new File(lAimDocDir, "SD_Reqs");
		File lOutputExtractedRequirementsFile = new File(lAimSdReqDir,
				"aim_reqs.txt");

		File lAimTmDir = new File(lAimDocDir, "TM");
		File lOutputTraceabilityMatrix = new File(lAimTmDir, "aim_matrix.xlsx");

		TraceabilityManagerContext lContext = new TraceabilityManagerContext();

		lContext.setAlmTestsExtractFile(lAlmExtractFile);
		lContext.setJustificationFile(lJustificationFile);
		try {
			lContext.setCSharpMethodRegexp("VTP_*");
		} catch (InvalidSimpleRegexpException e) {
			Assert.fail("Could not create simple regexp for C# method "
					+ e.getMessage());
		}
		lContext.setOutputExtractedRequirementsFile(lOutputExtractedRequirementsFile);
		lContext.setOutputVtpFile(lAimVtp);
		lContext.setOutputTraceabilityMatrixFile(lOutputTraceabilityMatrix);

		lContext.addRequirementPrefix("SD-AIM");
		lContext.addSpecificationDossier(lAimSd);

		SimpleRegex lCSharpFileNameRegexp = null;
		try {
			lCSharpFileNameRegexp = new SimpleRegex("*.xml");
		} catch (InvalidSimpleRegexpException e) {
			Assert.fail("Could not create simple regexp for C# file "
					+ e.getMessage());
		}
		FileSearchFilter lCSharpFileSearchFilter = new FileSearchFilter(
				lCSharpRootDirectory, true, lCSharpFileNameRegexp);
		try {
			lContext.addCSharpFileSearchFilter(lCSharpFileSearchFilter);
		} catch (InvalidFileSearchFilterException e) {
			Assert.fail("Could not add searct filter for C# file "
					+ e.getMessage());
		}

		SimpleRegex lFeatureFileNameRegexp = null;
		try {
			lFeatureFileNameRegexp = new SimpleRegex("*.feature");
		} catch (InvalidSimpleRegexpException e) {
			Assert.fail("Could not create simple regexp for feature file "
					+ e.getMessage());
		}
		FileSearchFilter lCucumberFileSearchFilter = new FileSearchFilter(
				lCucumberRootDirectory, true, lFeatureFileNameRegexp);
		try {
			lContext.addCucumberFileSearchFilter(lCucumberFileSearchFilter);
		} catch (InvalidFileSearchFilterException e) {
			Assert.fail("Could not add searct filter for feature file "
					+ e.getMessage());
		}

		ExecutorExecutionStatus<TraceabilityManagerResultObject> lExecutionStatus = new ExecutorExecutionStatus<TraceabilityManagerResultObject>();

		TraceabilityManager lSut = new TraceabilityManager(lContext,
				lExecutionStatus);

		try {
			lSut.run();

			if (lExecutionStatus.getCurrentExecutionStatus() == ExecutionStatus.ENDED_SUCCESS) {
				System.out.println("Success");
			} else {
				Assert.fail("Execution failed : "
						+ lExecutionStatus.getExecutionStatusDescription());
			}
		} catch (Exception e) {
			Assert.fail("Execution failed due to exception : " + e.getMessage());
		}

	}

	// * The value for the airport.

	/**
	 * Checks that the transformation of an input xlsx file through template 4 complies with the expected requirements.
	 *
	 *  @testId "Import from template 4"
	 *  @expectedResult "The imported obstacle are as expected as per the input data and the mapping of template 4"
	 *  @coveredReqs "SD-OBS-BR-060, SD-OBS-BR-070"
	 */
	@Test
	void VTP_testFileImportation() throws Exception {

		File template4Mapping = getTestInputFile("MappingTemplate4.xml");
		File inputFile = getTestInputFile("input_tu.xlsx");
		File outputCsvFile = getTestInputFile("outputTemplate4.csv");

		
		IImportedObstacleModelCompletionHandler completionHandler = new ImportedObstacleModelCompletionHandler(AIRPORT,
				COUNTRY, CAPTURE_ANALYST, VERIFICATION_ANALYST);

		// Call the API
		IImportationReport importReport = ImportManager.getInstance().importFile(template4Mapping, "Template 4",
				inputFile, outputCsvFile, completionHandler);

		if (importReport.isSuccessful()) {
			List<ObstacleModel> importedModelList = importReport.getImportedObstacleModels();

			Assert.assertEquals("Not all the obstacle lines were imported", 9, importedModelList.size());

			// First model
			ObstacleModel obstacleModel1 = importedModelList.get(0);
			String modelNum = "OBST-1, line 2";
			checkObstacleModelMainValues(modelNum, obstacleModel1, // Check main values
					"M", // haccUnit
					"degrees", // hresUnit (the specified value)
					"M", // eabUnit
					"M", // vaccUnit
					"M", // vresUnit
					"M", // radiusUnit
					"M", // elevUnit
					"M", // heightUnit
					"DMS", // latLongUnit
					ObstaclesConstants.FEATURE_TYPE_POINT_VALUE, // feattype
					"UNK", // lighting
					"UNK" // marking
			);
			checkObstacleModelOtherValues(modelNum, obstacleModel1, // Check other values
					"N 33 23 27.4", // Expected latitudes
					"W 007 35 45.8", // Expected longitudes
					"196.1", // Elevations
					"2.8", // Heights
					"0.002", // hres (the one specified in input)
					"0.003" // vres (the one specified in input)
			);

			// Second model
			ObstacleModel obstacleModel2 = importedModelList.get(1);
			modelNum = "OBST-2, line 3";
			checkObstacleModelMainValues(modelNum, obstacleModel2, // Check main values
					"M", // haccUnit
					ObstaclesConstants.OUTPUT_RESOLUTION_SECONDS_VALUE, // hresUnit 
					"M", // eabUnit
					"M", // vaccUnit
					"M", // vresUnit
					"M", // radiusUnit
					"FT", // elevUnit
					"M", // heightUnit
					"DMS", // latLongUnit
					"line", // feattype
					"UNK", // lighting
					"UNK" // marking
			);
			checkObstacleModelOtherValues(modelNum, obstacleModel2, // Check other values
					"S 45 01 00" + VALUE_SEPARATOR + "S 45 00 12", // Expected latitudes
					"E 010 01 00" + VALUE_SEPARATOR + "E 010 00 55", // Expected longitudes
					"218.6" + VALUE_SEPARATOR + "111", // Elevations
					"15.0" + VALUE_SEPARATOR + "10", // Heights
					"1", // hres
					"1" // vres (we take the worst resolution)
			);

			// Third model
			ObstacleModel obstacleModel0 = importedModelList.get(2);
			modelNum = "OBST-3, line 4";
			checkObstacleModelMainValues(modelNum, obstacleModel0, // Check main values
					"FT", // haccUnit
					ObstaclesConstants.OUTPUT_RESOLUTION_SECONDS_VALUE, // hresUnit
					"FT", // eabUnit
					"FT", // vaccUnit
					"FT", // vresUnit
					"FT", // radiusUnit
					"FT", // elevUnit
					"M", // heightUnit
					"DMS", // latLongUnit
					ObstaclesConstants.FEATURE_TYPE_POLYGON_VALUE, // feattype
					"NO", // lighting
					"NO" // marking
			);
			checkObstacleModelOtherValues(modelNum, obstacleModel0, // Check other values
					"N 33 22 48.4" + VALUE_SEPARATOR + "N 33 22 55" + VALUE_SEPARATOR + "N 33 23 17.21"
							+ VALUE_SEPARATOR + "N 33 22 48.4", // Expected latitudes
					"W 007 35 53.3" + VALUE_SEPARATOR + "W 007 35 37.3" + VALUE_SEPARATOR + "W 007 35 50"
							+ VALUE_SEPARATOR + "W 007 35 53.3", // Expected longitudes
					"98.5" + VALUE_SEPARATOR + "99.5" + VALUE_SEPARATOR + "100.2" + VALUE_SEPARATOR + "98.5", // Elevations
					"2" + VALUE_SEPARATOR + "3" + VALUE_SEPARATOR + "4" + VALUE_SEPARATOR + "2", // Heights
					"1", // hres
					"0.1" // vres
			);

			// Fourth model
			ObstacleModel obstacleModel4 = importedModelList.get(3);
			modelNum = "OBST-4, line 5";
			checkObstacleModelMainValues(modelNum, obstacleModel4, // Check main values
					"FT", // haccUnit
					ObstaclesConstants.OUTPUT_RESOLUTION_DEGREES_VALUE, // hresUnit
					"FT", // eabUnit
					"FT", // vaccUnit
					"FT", // vresUnit
					"FT", // radiusUnit
					"M", // elevUnit
					"M", // heightUnit
					"DD", // latLongUnit
					"point", // feattype
					"YES", // lighting
					"YES" // marking
			);
			checkObstacleModelOtherValues(modelNum, obstacleModel4, // Check other values
					"N 33 07 24.2400", // Expected latitudes
					"E 111 06 36.00", // Expected longitudes
					"209.9", // Elevations
					"15.1", // Heights
					"0.01", // hres
					"0.1" // vres
			);

			// Fifth model
			ObstacleModel obstacleModel5 = importedModelList.get(4);
			modelNum = "OBST-5, line 6";
			checkObstacleModelMainValues(modelNum, obstacleModel5, // Check main values
					"", // haccUnit
					ObstaclesConstants.OUTPUT_RESOLUTION_SECONDS_VALUE, // hresUnit
					"", // eabUnit
					"", // vaccUnit
					"FT", // vresUnit
					"", // radiusUnit
					"M", // elevUnit
					"", // heightUnit
					"DMS", // latLongUnit
					ObstaclesConstants.FEATURE_TYPE_LINE_VALUE, // feattype
					"UNK", // lighting
					"UNK" // marking
			);

			checkObstacleModelOtherValues(modelNum, obstacleModel5, // Check other values
					"N 60 10 59.000" + VALUE_SEPARATOR + "N 60 11 00.000", // Expected latitudes
					"W 178 20 59.000" + VALUE_SEPARATOR + "W 178 21 00.000", // Expected longitudes
					"100.000001" + VALUE_SEPARATOR + "101.000001", // Elevations
					"15" + VALUE_SEPARATOR + "18", // Heights
					"0.001", // hres
					"0.000001" // vres
			);

			// Sixth model
			ObstacleModel obstacleModel6 = importedModelList.get(5);
			modelNum = "OBST-6, line 7";
			checkObstacleModelMainValues(modelNum, obstacleModel6, // Check main values
					"", // haccUnit
					ObstaclesConstants.OUTPUT_RESOLUTION_MINUTES_VALUE, // hresUnit
					"", // eabUnit
					"", // vaccUnit
					"FT", // vresUnit
					"", // radiusUnit
					"", // elevUnit
					"FT", // heightUnit 
					"DMS", // latLongUnit
					ObstaclesConstants.FEATURE_TYPE_POINT_VALUE, // feattype
					"UNK", // lighting
					"UNK" // marking
			);

			checkObstacleModelOtherValues(modelNum, obstacleModel6, // Check other values
					"N 60 15 00" , // Expected latitudes
					"W 120 15 00" , // Expected longitudes
					"", // Elevations
					"10", // Heights
					"1", // hres
					"1" // vres
			);

			// Seventh model
			ObstacleModel obstacleModel7 = importedModelList.get(6);
			modelNum = "OBST-7, line 8";
			checkObstacleModelMainValues(modelNum, obstacleModel7, // Check main values
					"", // haccUnit
					ObstaclesConstants.OUTPUT_RESOLUTION_DEGREES_VALUE, // hresUnit
					"", // eabUnit
					"", // vaccUnit
					"FT", // vresUnit
					"", // radiusUnit
					"", // elevUnit
					"FT", // heightUnit 
					"DMS", // latLongUnit
					ObstaclesConstants.FEATURE_TYPE_POINT_VALUE, // feattype
					"UNK", // lighting
					"UNK" // marking
			);

			checkObstacleModelOtherValues(modelNum, obstacleModel7, // Check other values
					"N 60 00 00" , // Expected latitudes
					"W 120 00 00" , // Expected longitudes
					"90", // Elevations
					"10", // Heights
					"1", // hres
					"1" // vres
			);
			
			// Eighth model
			ObstacleModel obstacleModel8 = importedModelList.get(7);
			modelNum = "OBST-8, line 9";
			checkObstacleModelMainValues(modelNum, obstacleModel8, // Check main values
					"", // haccUnit
					ObstaclesConstants.OUTPUT_RESOLUTION_DEGREES_VALUE, // hresUnit
					"", // eabUnit
					"", // vaccUnit
					"FT", // vresUnit
					"", // radiusUnit
					"", // elevUnit
					"FT", // heightUnit 
					"DMS", // latLongUnit
					ObstaclesConstants.FEATURE_TYPE_POINT_VALUE, // feattype
					"UNK", // lighting
					"UNK" // marking
			);

			checkObstacleModelOtherValues(modelNum, obstacleModel8, // Check other values
					"N 60 00 00" , // Expected latitudes
					"W 120 15 10.5" , // Expected longitudes
					"80", // Elevations
					"10", // Heights
					"1", // hres
					"1" // vres
			);

			// Ninth model
			ObstacleModel obstacleModel9 = importedModelList.get(8);
			modelNum = "OBST-9, line 10";
			checkObstacleModelMainValues(modelNum, obstacleModel9, // Check main values
					"", // haccUnit
					ObstaclesConstants.OUTPUT_RESOLUTION_DEGREES_VALUE, // hresUnit
					"", // eabUnit
					"", // vaccUnit
					"FT", // vresUnit
					"", // radiusUnit
					"", // elevUnit
					"FT", // heightUnit 
					"DMS", // latLongUnit
					ObstaclesConstants.FEATURE_TYPE_POINT_VALUE, // feattype
					"UNK", // lighting
					"UNK" // marking
			);

			checkObstacleModelOtherValues(modelNum, obstacleModel9, // Check other values
					"N 60 15 10.5" , // Expected latitudes
					"W 120 00 00" , // Expected longitudes
					"70", // Elevations
					"10", // Heights
					"1", // hres
					"1" // vres
			);
			
		} else {
			Assert.fail("Importation of input file " + inputFile.getAbsolutePath() + " failed : "
					+ importReport.getTextReport());
		}
	}
}
