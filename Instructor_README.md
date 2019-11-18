# csc540-prj1
CSC 540 - Project 1
Team Members: Jamie Gachie, Logan Willett, Jeremy Maness, Jerry Sun

# Prerequisites

* Java 8

# To build
To build the application using Gradle, run the following command which will download and cache the configured Gradle distribution, compile the source, and package the application code into a jar along with its dependencies for running as a Java application.

In addition, since this project uses the Gradle Application Plugin, a few other helpful targets are enabled:
* A zip and tarball archive are created for easy distribution which includes the compiled application code, library dependencies, and shell scripts to run the application.

* A `run` target to run the application directly from the command line. See the section below about running the app. 

```
./gradlew build
```

# To run the app

In the same directory as the .jar file, run the following command:

```
HEALTH_DB_USER=[username] HEALTH_DB_PASSWORD=[password] HEALTH_DB_URL=jdbc:oracle:thin:@localhost:49161:xe java -jar csc540-prj1-all.jar
```

(Replace the parts in brackets with the appropriate values)