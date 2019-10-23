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

During development, a simple way to run the application is to use the following command.

```
./gradlew run
```

NOTE: The database username and password must be configured. The easiest way is to set the 
`HEALTH_DB_USER` and `HEALTH_DB_PASSWORD` environment variables.

```
HEALTH_DB_USER=myuser HEALTH_DB_PASSWORD=mypass ./gradlew run
```

Alternatively, you can set these in a `config.yml` file and specify the path as the first argument.

```
./gradlew run myconfig.yml
```


# Code conventions
Checkstyle is configured to enforce some basic conventions, but we as a group can decide to relax or change these. This
will automatically be run when building the application, but if you want to run checkstyle explicitly, you can run:

```
./gradlew checkstyleMain
```
