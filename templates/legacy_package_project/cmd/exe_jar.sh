#!/bin/bash
# This file show the internals of the .jar file and runs it

cd ${JAR_PATH}
pwd

# show internal table, cmd: jar tf Testpack.jar
echo "*** .jar internal table ***"
jar tf ${JAR_NAME}

# run the .jar file
echo "*** run .jar file ***"
java -jar ${JAR_NAME} 
