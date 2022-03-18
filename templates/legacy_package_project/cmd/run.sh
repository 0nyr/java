#!/bin/bash
# simple command to run the build of the project

echo "*** running the testpack package ***"
cd "${CLASS_PATH}"
pwd
ls 

java testpack.Main
