#!/bin/bash
# This file is used to compile the java testpack package into the /build directory
# NB: this file should be inside the package source folder on same level as Main.java
# NB: use chmod -x <compil.sh> on this file to be able to run it
# NB: The project itself is nested inside a package
# WARN: Do NOT put spaces after = in a bash script file like this !!!

# move to /test_package/ src file
echo "*** move to src directory ***"
cd "${PROJECT_PATH}/src/${PACKAGE_NAME}"
pwd
ls

# compilation process
echo "*** compilation start ***"
echo "*** compile files to ${CLASS_PATH}"

#javac -d "${CLASS_PATH}" ./display/LabelSprite.java
#echo "--> ./display/LabelSprite.java compiled"
#javac -d "${CLASS_PATH}" ./Main.java
#echo "--> ./Main.java compiled"
javac -d "${CLASS_PATH}" ./*

echo "*** compilation done ***"

