To Build:

Download the latest JDK (at-least 1.8+)  from http://www.oracle.com/technetwork/java/javase/downloads/index.html

( getting the bundled Netbeans is recommended but not required)

To build with netbeans simply open this directory as a project (File-> Open Project ...)  and choose 
(Run -> Build Project (poseList3DPlot) ).

To build on the command line for linux:

export JAVA_HOME=[path_to_jdk_1.8+]
sudo apt-get install maven
mvn clean package

Eclipse and Intellij also have options for importing a maven project.

To run graphical launcher:

[path_to_jdk_1.8+]/bin/java -jar target/poseList3DPlot-1.0-SNAPSHOT.jar 

or use the convenience scripts run.sh or run.bat

./run.sh

or on Windows

run.bat

Use the "File-> Open" menu option to select a csv file with xyz fields.
The plot can be rotated and moved to view from any angle/position.
Select one of the radio buttons and the bottom determine how the plot is
rotated or moved when you drag within the display area.
Buttons provide quicker access to predefined views.


Build Status can be checked here:
https://travis-ci.org/usnistgov/poseList3DPlot
