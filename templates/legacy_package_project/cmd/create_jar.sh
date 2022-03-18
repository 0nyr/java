#!/bin/bash
# simple command to build the jar (Java Archive) file

# More info about .jar files: https://docs.oracle.com/javase/tutorial/deployment/jar/index.html  
# tuto on .jar creation: https://www.jitendrazaa.com/blog/java/create-executable-jar-file-of-classes-in-package/
# example of commands

# view table of content of .jar file: jar tf <jar-file-path>
# run the .jar file: java -jar <jar-file>

cd "${PROJECT_PATH}"

# jar {ctxui}[vfm0Me] [jar-file] [manifest-file] [entry-point] [-C dir] files ...
# WARN: the order of the arguments must follow the order of the flags !
jar cfmv0 ${JAR_PATH}Testpack.jar ${COMMAND_PATH}manifest.txt -C classes . res

# make the new .jar file executable
cd "${JAR_PATH}"
chmod 0705 Testpack.jar
