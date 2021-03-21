package org.tools.doc.traceability.analyzer.unittests.csharp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import org.tools.doc.traceability.analyzer.unittests.common.UnitTestCaseData;
import org.tools.doc.traceability.analyzer.unittests.csharp.model.CSharpUnitTestFileData;
import org.tools.doc.traceability.common.exceptions.AbstractTraceabilityException;
import org.tools.doc.traceability.common.exceptions.ExecutorExecutionException;
import org.tools.doc.traceability.common.exceptions.FileSearchException;
import org.tools.doc.traceability.common.exceptions.InvalidFileSearchFilterException;
import org.tools.doc.traceability.common.exceptions.InvalidParameterException;
import org.tools.doc.traceability.common.executor.AbstractExecutor;
import org.tools.doc.traceability.common.executor.ExecutorExecutionStatus;
import org.tools.doc.traceability.common.filesearch.FileSearchFilterSet;
import org.tools.doc.traceability.common.filesearch.FileSearcher;
import org.tools.doc.traceability.common.model.Requirement;
import org.tools.doc.traceability.common.sregex.SimpleRegex;

/**
 * A tool parsing the xml files generated while executing unit tests with Visual
 * Studio, and extracting the contained data.
 * <p>
 * It analyzes the coverage from C# code unit tests.
 * </p>
 * 
 * @author Yann Leglise
 *
 */
public class CSharpUnitTestCoverageAnalyzer extends AbstractExecutor<CSharpUnitTestCoverageAnalyserResult> {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LogManager.getLogger(CSharpUnitTestCoverageAnalyzer.class);

    /**
     * The set of XML file search filter.
     */
    private final FileSearchFilterSet xmlFileSearchFilterSet;

    /**
     * The simple regexp to match method names.
     */
    private final SimpleRegex testNameRegexp;

    /**
     * The XML document parser.
     */
    private DocumentBuilder docBuilder;

    /**
     * The result object.
     */
    private CSharpUnitTestCoverageAnalyserResult resultObject;

    /**
     * Constructor.
     * 
     * @param pXmlFileSearchFilterSet the set of file filters to select the .xml
     * files to analyze.
     * @param pTestNameRegexp the simple regular expression that test methods
     * must comply to in order for them to be taken into account in the result.
     * @param pExecutionStatus the executor execution status.
     */
    public CSharpUnitTestCoverageAnalyzer(final FileSearchFilterSet pXmlFileSearchFilterSet,
            final SimpleRegex pTestNameRegexp,
            final ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult> pExecutionStatus) {
        super(pExecutionStatus);
        xmlFileSearchFilterSet = pXmlFileSearchFilterSet;
        testNameRegexp = pTestNameRegexp;
        docBuilder = null;
        resultObject = null;
    }

    /**
     * Constructor.
     * 
     * @param pRootTestDirectory the root of the test result directory to parse.
     * @param pXmlFilenameRegexp the regular expression to filter which XML
     * files to analyze
     * @param pTestNameRegexp the simple regular expression that test methods
     * must comply to in order for them to be taken into account in the result.
     * @param pExecutionStatus the executor execution status.
     */
    public CSharpUnitTestCoverageAnalyzer(final File pRootTestDirectory, final SimpleRegex pXmlFilenameRegexp,
            final SimpleRegex pTestNameRegexp,
            final ExecutorExecutionStatus<CSharpUnitTestCoverageAnalyserResult> pExecutionStatus) {
        super(pExecutionStatus);
        docBuilder = null;
        resultObject = null;

        FileSearchFilterSet lXmlFileSearchFilterSet = new FileSearchFilterSet();
        try {
            lXmlFileSearchFilterSet.addFilter(pRootTestDirectory, true, pXmlFilenameRegexp);
        } catch (InvalidFileSearchFilterException e) {
            LOGGER.error("Error creating the XML file search filter set : " + e.getMessage());
            lXmlFileSearchFilterSet = null;
        }

        xmlFileSearchFilterSet = lXmlFileSearchFilterSet;

        testNameRegexp = pTestNameRegexp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performTask() throws AbstractTraceabilityException {
        // First, check the provided arguments
        checkArguments();

        // Initialize the XML parser
        initializeXmlPareer();

        // Then perform the analysis
        analyzeTestResults();
    }

    /**
     * Initializes the XML parser if not done yet.
     * 
     * @throws ExecutorExecutionException if an error occurs.
     */
    protected void initializeXmlPareer() throws ExecutorExecutionException {
        if (docBuilder == null) {
            DocumentBuilderFactory lDocFactory = DocumentBuilderFactory.newInstance();
            try {
                docBuilder = lDocFactory.newDocumentBuilder();
            } catch (ParserConfigurationException pce) {
                LOGGER.error("Could not initialize the XML parser : " + pce.getMessage());
                throw new ExecutorExecutionException("Could not initialize an XML parser");
            }
        }
    }

    /**
     * Checks the provided arguments and throw an exception if one is invalid.
     * 
     * @throws InvalidParameterException if an argument is invalid.
     */
    private void checkArguments() throws InvalidParameterException {
        setCurrentOperation("Checking arguments", 5.);
        // Ensure the output file is defined
        // Check the regexp
        if (testNameRegexp == null) {
            throw new InvalidParameterException("TestCoverageAnalyzer.checkArguments", "test name regular expression",
                    "null value");
        } else {
            if (xmlFileSearchFilterSet == null) {
                throw new InvalidParameterException("TestCoverageAnalyzer.checkArguments", "XML file search filter",
                        "null");
            } else {
                if (xmlFileSearchFilterSet.isEmpty()) {
                    throw new InvalidParameterException("TestCoverageAnalyzer.checkArguments",
                            "XML file search filter", "empty");
                }
            }
        }
    }

    /**
     * Browse the test result directory in search of test result files (.xml)
     * and try to parse them to extract test informations.
     * 
     * @throws ExecutorExecutionException if an error occurs.
     */
    private void analyzeTestResults() throws ExecutorExecutionException {

        double lCurrentPercentage = 5.0;
        setCurrentOperation("Searching for XML files in " + xmlFileSearchFilterSet.toString(), lCurrentPercentage);

        // First search for the XML files
        FileSearcher lFileSearcher = new FileSearcher(xmlFileSearchFilterSet);
        List<File> lXmlFileList;
        try {
            lXmlFileList = lFileSearcher.search();
        } catch (FileSearchException e) {
            throw new ExecutorExecutionException("Error searching for .xml files : " + e.getMessage());
        }

        resultObject = new CSharpUnitTestCoverageAnalyserResult();

        double lPercentagePerFile = 70. / lXmlFileList.size();
        // Iterate on each of them
        for (File lXmlFile : lXmlFileList) {
            // Process this file
            setCurrentOperation("Processing C# unit test result file " + lXmlFile.getAbsolutePath(), lCurrentPercentage);

            processXmlFile(lXmlFile);

            lCurrentPercentage += lPercentagePerFile;
        }

        // Set the result object
        setExecutionResult(resultObject);
    }

    /**
     * Try and process the given XML file.
     * 
     * @param lXmlFile the XML file to process.
     * @throws ExecutorExecutionException if an error occurs.
     */
    /**
     * @param lXmlFile the XML file to process.
     * @throws ExecutorExecutionException if an error occurs during the
     * execution.
     */
    private void processXmlFile(final File lXmlFile) throws ExecutorExecutionException {
        LOGGER.debug("Processing C# unit test result file " + lXmlFile.getAbsolutePath());

        // Load the XML contents in memory as a DOM document
        Document lXmlDomDoc = null;
        try {
            lXmlDomDoc = docBuilder.parse(lXmlFile);
        } catch (SAXException | IOException e) {
            LOGGER.error("Could not parse " + lXmlFile.getAbsolutePath() + " : " + e.getMessage());
            lXmlDomDoc = null;
        }

        if (lXmlDomDoc != null) {

            // Ensure that the root element is "doc"
            Element lRootElement = lXmlDomDoc.getDocumentElement();

            if (lRootElement.getNodeName().toLowerCase().equals("doc")) {

                String lAssemblyName = null;
                List<UnitTestCaseData> lUnitTestDataList = null;

                // Get the nodes at level 1
                NodeList lLevel1NodeList = lRootElement.getChildNodes();
                for (int lIdx = 0; lIdx < lLevel1NodeList.getLength(); lIdx++) {
                    Node lLevel1Node = lLevel1NodeList.item(lIdx);

                    if (lLevel1Node.getNodeType() == Node.ELEMENT_NODE) {
                        Element lElement = (Element) lLevel1Node;
                        if (lElement.getNodeName().toLowerCase().equals("assembly")) {
                            // Process the assembly element to extract the name
                            // sub-node value
                            lAssemblyName = getNameFromAssemblyElement(lElement);
                        } else if (lElement.getNodeName().toLowerCase().equals("members")) {
                            // Process the members sub-nodes to extract the unit
                            // test data
                            lUnitTestDataList = getUnitTestDataFromMembersElement(lElement);
                        }
                    }
                }

                // If the assembly name was found
                if (lAssemblyName != null) {
                    // Create a file data instance
                    CSharpUnitTestFileData lUnitTestFileData = new CSharpUnitTestFileData(lAssemblyName);
                    // If some unit test data were found
                    if (lUnitTestDataList != null) {
                        // Add them to the instance
                        for (UnitTestCaseData lUnitTestData : lUnitTestDataList) {
                            lUnitTestFileData.addUnitTestData(lUnitTestData);
                        }
                    }

                    // Add the association to the result object
                    resultObject.addResult(lXmlFile, lUnitTestFileData);

                    LOGGER.debug("Unit test file " + lXmlFile.getName() + " (corresponding to assembly "
                            + lUnitTestFileData.getAssemblyName() + ") covers "
                            + lUnitTestFileData.getReferencedRequirementCount() + " requirement(s)");
                }

            } else {
                LOGGER.debug("Ignoring file " + lXmlFile.getAbsolutePath() + " as the root node is "
                        + lRootElement.getNodeName());
            }
        }
    }

    /**
     * Process the members element to extract the unit test data.
     * 
     * @param pMembersElement the member XML element.
     * @return the list of found unit test data.
     */
    private List<UnitTestCaseData> getUnitTestDataFromMembersElement(final Element pMembersElement) {
        List<UnitTestCaseData> lUnitTestDataList = new ArrayList<UnitTestCaseData>();

        NodeList lSubNodeList = pMembersElement.getChildNodes();
        for (int lNodeIdx = 0; lNodeIdx < lSubNodeList.getLength(); lNodeIdx++) {
            Node lSubNode = lSubNodeList.item(lNodeIdx);

            if (lSubNode.getNodeType() == Node.ELEMENT_NODE) {
                Element lSubElement = (Element) lSubNode;

                if (lSubElement.getNodeName().toLowerCase().equals("member")) {
                    // We found a member node
                    UnitTestCaseData lUnitTestData = extractUnitTestDataFromMemberElt(lSubElement);

                    if (lUnitTestData != null) {
                        // Add it to the list
                        lUnitTestDataList.add(lUnitTestData);
                    }
                }
            }
        }

        return lUnitTestDataList;
    }

    /**
     * Analyze the given member node, and if the name matches the test name
     * regexp create an return a {@link UnitTestCaseData} form the contained
     * information.
     * 
     * @param pMemberElement the member XML element.
     * @return the unit test data if the name of the member matched the regexp,
     * null otherwise.
     */
    private UnitTestCaseData extractUnitTestDataFromMemberElt(final Element pMemberElement) {
        UnitTestCaseData lUnitTestData = null;

        Attr lNameAttribute = pMemberElement.getAttributeNode("name");

        if (lNameAttribute != null) {
            // Get the name attribute value
            String lName = lNameAttribute.getTextContent();

            // Extract the method name (after the last dot)

            // First extract the part corresponding to the method parameters in
            // parenthesis
            int lLastOpeningParIdx = lName.lastIndexOf('(');
            String lNameWithoutParameters;
            if (lLastOpeningParIdx == -1) {
                lNameWithoutParameters = lName;
            } else {
                lNameWithoutParameters = lName.substring(0, lLastOpeningParIdx);
            }

            String lMethodName = null;
            try {
                lMethodName = lNameWithoutParameters.substring(lNameWithoutParameters.lastIndexOf('.') + 1);

                // Check if the method name matches the regexp
                if (testNameRegexp.matches(lMethodName)) {
                    // We can go on searching for the summary node
                    NodeList lSubNodeList = pMemberElement.getChildNodes();
                    for (int lSubnodeIdx = 0; lSubnodeIdx < lSubNodeList.getLength(); lSubnodeIdx++) {
                        Node lSubNode = lSubNodeList.item(lSubnodeIdx);

                        if (lSubNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element lSubElement = (Element) lSubNode;

                            if (lSubElement.getNodeName().toLowerCase().equals("summary")) {

                                // Extract the unit test data from it
                                lUnitTestData = extractUnitTestDataFromSummaryElt(lSubElement, lMethodName);
                            }
                        }
                    }
                }
            } catch (IndexOutOfBoundsException ioobe) {
                System.err.println("Fuck : " + ioobe);
            }
        }

        return lUnitTestData;
    }

    /**
     * Extract the data from the given summary element and build and return the
     * associated unit test data.
     * 
     * @param pSummaryElement the summary XML element.
     * @param pTestName the test name.
     * @return the associated {@link UnitTestCaseData}, or null if it could not
     * be created
     */
    private UnitTestCaseData extractUnitTestDataFromSummaryElt(final Element pSummaryElement, final String pTestName) {
        UnitTestCaseData lUnitTestData = null;

        StringBuilder lDescriptionSb = new StringBuilder();
        StringBuilder lExpectedResultSb = new StringBuilder();
        String lTestIdentifier = null;
        String lCoveredReqs = null;

        NodeList lSubNodeList = pSummaryElement.getChildNodes();
        for (int lSubnodeIdx = 0; lSubnodeIdx < lSubNodeList.getLength(); lSubnodeIdx++) {
            Node lSubNode = lSubNodeList.item(lSubnodeIdx);

            if (lSubNode.getNodeType() == Node.ELEMENT_NODE) {
                Element lSubElement = (Element) lSubNode;

                if (lSubElement.getNodeName().toLowerCase().equals("para")) {
                    String lParaText = lSubElement.getTextContent().trim();

                    // Split on the first found ':' character
                    int lFirstColonCharIdx = lParaText.indexOf(':');

                    if (lFirstColonCharIdx != -1) {
                        String lParaType = lParaText.substring(0, lFirstColonCharIdx);
                        String lParaTypeNormalized = lParaType.replace(" ", "").toLowerCase();

                        if (lParaTypeNormalized.startsWith("testid")) {
                            lTestIdentifier = lParaText.substring(lFirstColonCharIdx + 1, lParaText.length()).trim();
                        } else if (lParaTypeNormalized.startsWith("coveredreq")) {
                            lCoveredReqs = lParaText.substring(lFirstColonCharIdx + 1, lParaText.length()).trim();
                        } else if (lParaTypeNormalized.startsWith("expectedresult")) {
                            if (lParaText.length() > 0) {
                                if (lExpectedResultSb.length() > 0) {
                                    lExpectedResultSb.append("\n");
                                }
                                lExpectedResultSb.append(lParaText);
                            }
                        } else {
                            if (lParaText.length() > 0) {
                                if (lDescriptionSb.length() > 0) {
                                    lDescriptionSb.append('\n');
                                }
                                lDescriptionSb.append(lParaText);
                            }
                        }
                    }

                }
            } else if (lSubNode.getNodeType() == Node.TEXT_NODE) {
                Text lText = (Text) lSubNode;
                String lTrimmedText = lText.getTextContent().trim();
                if (lTrimmedText.length() > 0) {
                    if (lDescriptionSb.length() > 0) {
                        lDescriptionSb.append('\n');
                    }
                    lDescriptionSb.append(lTrimmedText);
                }
            }
        }

        if ((lTestIdentifier != null) && (lCoveredReqs != null)) {
            lUnitTestData = new UnitTestCaseData(lTestIdentifier, pTestName, lDescriptionSb.toString(),
                    lExpectedResultSb.toString());
            // Get he covered requirements
            String[] lCoveredReqElts = lCoveredReqs.split(",");
            for (int lReqEltIdx = 0; lReqEltIdx < lCoveredReqElts.length; lReqEltIdx++) {
                String lRequirementName = lCoveredReqElts[lReqEltIdx].trim();
                if (lRequirementName.length() > 0) {
                    Requirement lRequirement = new Requirement(lRequirementName);
                    lUnitTestData.addCoveredRequirement(lRequirement);
                }
            }
        }

        return lUnitTestData;
    }

    /**
     * Process the assembly element to extract the name sub-node value..
     * <p>
     * We search for the name sub-node.
     * </p>
     * 
     * @param pElement the XML element for assembly (not null).
     * @return the value of the name sub-node, or null if not found.
     */
    private String getNameFromAssemblyElement(final Element pElement) {
        String lNameSubNodeValue = null;

        NodeList lAssemblySubNodeList = pElement.getChildNodes();
        for (int lIdx = 0; lIdx < lAssemblySubNodeList.getLength(); lIdx++) {
            Node lSubNode = lAssemblySubNodeList.item(lIdx);

            if (lSubNode.getNodeType() == Node.ELEMENT_NODE) {
                Element lSubElement = (Element) lSubNode;

                if (lSubElement.getNodeName().toLowerCase().equals("name")) {
                    // We found the name node
                    lNameSubNodeValue = lSubElement.getTextContent();
                    break;
                }
            }
        }

        return lNameSubNodeValue;
    }
}
