## Building and running the server

Prerequesite dependencies: installing the java JDK and maven files.

MAVEN: https://maven.apache.org/download.cgi
JAVA: https://www.oracle.com/java/technologies/javase-downloads.html

Add both bin file paths to the path environment variable in one's computer.

From the Homely directory

### Builds the backend server jar using maven

`mvn clean install`

### Maven can be installed from https://maven.apache.org/

Make sure that the installed mvn is in your path before running

### Runs the backend server

` java -jar target/robot-0.0.1-SNAPSHOT.jar edu.sjsu.robot.RobotApplication`
