@echo off

rem Example :
rem traceability-tool-gui.cmd 

rem Get this script directory
SET scriptpath=%~dp0

rem The path to the configuration file
SET CONFIG_FILE_PATH="%scriptpath%config"

rem The name of the configuration file
SET CONFIG_FILE_NAME=app-config.xml

SET LOG4J_CONFIG_FILE=%scriptpath%config\traceability-tool-gui-log4j.xml
rem Replace backslashes with forward slashes
SET LOG4J_CONFIG_FILE=%LOG4J_CONFIG_FILE:\=/%

start "Traceability Tool" /B javaw -Dlog4j.configurationFile="file:///%LOG4J_CONFIG_FILE%"  -DTraceabilityConfigFilePath=%CONFIG_FILE_PATH% -DTraceabilityConfigFileName=%CONFIG_FILE_NAME% -cp %scriptpath%\common-libs\*;%scriptpath%\application-libs\traceabilitytoolgui.jar org.tools.doc.traceability.manager.gui.Launcher

