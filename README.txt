To Build:

Download the latest JDK (at-least 1.8+)  from http://www.oracle.com/technetwork/java/javase/downloads/index.html

( getting the bundled Netbeans is recommended but not required)

To build with netbeans simply open this directory as a project (File-> Open Project ...)  and choose 
Run -> Build Project (poseList3DPlot)  or just Run -> Run Project(poseList3DPlot) 
    to build as necessary and run in one step.

To build on the command line for linux:

export JAVA_HOME=[path_to_jdk_1.8+]
sudo apt-get install maven
mvn clean package

Eclipse and Intellij also have options for importing a maven project. 
(Select the file pom.xml to import).

To run graphical launcher:

[path_to_jdk_1.8+]/bin/java -jar target/poseList3DPlot-1.0-SNAPSHOT.jar 

or use the convenience scripts run.sh or run.bat with a correct path to java set.

./run.sh

or on Windows

run.bat

Arguments are expected to be CSV filenames.

e.g.

./run.sh src/test/resources/examplePoses.csv 

Files can be passed using a command line argument or
with the "File-> Open" menu option to select a csv file with xyz fields.
The plot can be rotated and moved to view from any angle/position.
Select one of the radio buttons and the bottom determine how the plot is
rotated or moved when you drag within the display area.
Buttons provide quicker access to predefined views.


Build Status can be checked here:
https://travis-ci.org/usnistgov/poseList3DPlot

Pre-built Snapshot Jars can be downloaded from:
https://oss.sonatype.org/content/repositories/snapshots/com/github/wshackle/poseList3DPlot/1.0-SNAPSHOT/

To install snapshot ( see http://central.sonatype.org/pages/apache-maven.html ):
(A signature & passphrase required, or change the group id to use a different signature).

mvn -Prelease deploy

To install to maven central:

mvn release:clean release:prepare
( accept all defaults to questions asked in terminal) 

mvn release:perform

If one would like a native installer such as .deb/.exe then it can be built with

mvn package -P native_installer

on Ubuntu this will produce the file:

    target/bundles/poselist3dplot-1.0.deb

which can be installed with


     sudo dpkg -i target/bundles/poselist3dplot-1.0.deb

and uninstalled with

     sudo apt-get remove poselist3dplot 


