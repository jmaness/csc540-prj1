# csc540-prj1
CSC 540 - Project 1

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

NOTE: Because of https://github.com/gradle/gradle/issues/1251, there is a problem using the Console with the Gradle Daemon.
You can work around this by configuring your IDE to not use Gradle when running `edu.ncsu.csc540.health.HealthApp`.

# Option 1. Run configuration in an IDE
1. Go to File -> Settings.
2. Under "Build, Execution, and Deployment" -> Build Tools -> Gradle, change "Build and run using" to IntelliJ IDEA instead of Gradle.

Create a Run configuration with the following values:
* Main Class: `edu.ncsu.csc540.health.HealthApp`, 
* Environment variables: `HEALTH_DB_USER=<your unity id>` and `HEALTH_DB_PASSWORD=<your numeric unity id>` 

# Option 2. Use `run_app.sh`
```
HEALTH_DB_USER=your_unity_id HEALTH_DB_PASSWORD=your_pass ./run_app.sh
```


1. Run the build 
```
./gradlew build
```
2. Extract build/distributions/csc540-prj1.zip to a temporary directory



# Code conventions
Checkstyle is configured to enforce some basic conventions, but we as a group can decide to relax or change these. This
will automatically be run when building the application, but if you want to run checkstyle explicitly, you can run:

```
./gradlew checkstyleMain
```
