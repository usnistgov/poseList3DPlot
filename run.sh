#!/bin/sh

if test ! -f ./run.sh ; then 
    cd "${0%%run.sh}";
fi

if test "x" != "x${JAVA_HOME}" ; then
    export PATH="${JAVA_HOME}/bin/:${PATH}";
fi

if test ! -f target/poseList3DPlot-1.0-SNAPSHOT.jar ; then
    mvn -version || ( echo "Please install maven." && false)
    mvn package
fi;

java -jar target/poseList3DPlot-1.0-SNAPSHOT.jar $*
