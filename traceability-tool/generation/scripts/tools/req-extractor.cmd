@echo off

rem Example :
rem req-extractor.cmd -i "C:\path to SD1\SD1.docx" "C:\path to SD2.docx" -o "C:\temp\output.txt" -r SD-ALB SD-MIN -s
rem will parse SD1.docx and SD2.docx, extract the requirements starting with SD-ALB or SD-MIN when found
rem in the first column of a table, and write them in output.txt, sorted alphabetically (because of -s flag) 

SET scriptpath=%~dp0
SET LOG4J_CONFIG_FILE=%scriptpath%config\req-extractor-log4j.xml
rem Replace backslashes with forward slashes
SET LOG4J_CONFIG_FILE=%LOG4J_CONFIG_FILE:\=/%

java -Dlog4j.configurationFile="file:///%LOG4J_CONFIG_FILE%" -cp  %scriptpath%\common-libs\*;%scriptpath%\application-libs\reqextractor.jar org.tools.doc.traceability.reqextraction.RequirementExtractorCmdLineTool %*
