Here are the steps to generate the tool:

1) Import the project in Eclipse
2) You might want to configure the project java compiler JDK compiler (current is configured for JavaSE-1.8)
3) Open Ant view, and drag and drop file generation/build.xml in it
4) Execute default target (traceabilitytoolgui)
5) Copy generated zip archive (in generation/output/traceabilitytoolgui/traceability-tool-gui.zip) in a folder
   and unzip it.
6) Replace the default application configuration file (i.e. config/app-config) by your own, or tune the default
   one to your specific needs. You can also add a configuration file with a different name, but then you
   need to update the launching script (traceability-tool-gui.cmd) to change the value of variable CONFIG_FILE_NAME.
7) Test your configuration by simply starting the application through the launching script.

