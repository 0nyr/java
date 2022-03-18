#!/bin/bash
# This file compile classes, gather them into a .jar and then run it
# NB: remember to add a compilation "javac" line for every new file/subpackage in ./cmd/compile.sh

# define project path and main package name
PACKAGE_NAME="testpack"
export PACKAGE_NAME
PROJECT_PATH="/home/onyr/Documents/code/java/default_package_project"
export PROJECT_PATH
CLASS_PATH="${PROJECT_PATH}/classes"
export CLASS_PATH
COMMAND_PATH="${PROJECT_PATH}/cmd/"
export COMMAND_PATH
JAR_NAME="Testpack.jar"
export JAR_NAME
JAR_PATH="${PROJECT_PATH}/jar/"
export JAR_PATH

# WARN: export for Java tools, CLASSPATH need to be declared
CLASSPATH=${CLASS_PATH}
export CLASSPATH

# build the classes and .jar file using shell scripts
echo "*** compilation process start ***"
${COMMAND_PATH}compile.sh

echo "*** .jar creation start ***"
${COMMAND_PATH}create_jar.sh

# run if "-r" flag was used as parameter for this command

# run the java project if command launch with "-r" flag
if [ "-r" = $1 ]
then
	echo "*** run project jar: ${JAR_PATH} ***"
	cd "$JAR_PATH"
	pwd
	${COMMAND_PATH}exe_jar.sh
else
	echo "NB: to compile then run project, use the [ -r ] flag"
fi

echo "*** launch jar start ***"

