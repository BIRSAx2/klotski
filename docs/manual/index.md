---
title: Getting started
layout: default
nav_order: 2
---

# Getting Started with Klotski Game

To start playing Klotski, you have multiple options to choose from. Whether you prefer downloading the pre-built JAR
file, building the game from source using Java and Gradle, or importing it into your favorite IDE, we've got you
covered. Follow the instructions below to get started with Klotski on your preferred platform.

## Requirements

- JDK 17: there are different distributions, but [Adoptium](https://adoptium.net/) should fit your needs
- For Android: [Android SDK](https://developer.android.com/tools/releases/platform-tools)
    - Set the ANDROID_HOME environment variable, or use local.properties to set the sdk.dir variable.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `desktop`: Primary desktop platform using LWJGL3.
- `android`: Android mobile platform. Needs Android SDK.

## Option 1: Download the pre-built JAR file

1. Visit the official [GitHub repository](https://github.com/BIRSAx2/klotski/releases/latest) for Klotski.
2. Navigate to the "Releases" section.
3. Download the latest release of the game, which will be available as a JAR file.
4. Once the download is complete, you can run the game by executing the JAR file using the Java Runtime Environment (
   JRE).

## Option 2: Build the game from source

- Clone the Klotski [GitHub repository](https://github.com/BIRSAx2/klotski) to your local machine using Git or download
  the source code as a ZIP archive and
  extract it.
- Ensure that you have Java Development Kit (JDK) (17 LTS) and Gradle installed on your system.
- Open a terminal or command prompt and navigate to the root directory of the Klotski project.
- Run the following command to build and run the game:
  ```bash
    ./gradlew desktop:run
  ```
- Note: In order to build the project, you will need to install the Android SDK. You can follow
  the instructions [here](https://libgdx.com/wiki/start/import-and-running#command-line)

## Option 3: Import the project into your IDE

- Ensure that you have an IDE installed on your system, such as IntelliJ IDEA, Eclipse, or Visual Studio Code.
- Clone the Klotski GitHub repository to your local machine using Git or download the source code as a ZIP archive and
  extract it.
- Open your preferred IDE and import the Klotski project as a Gradle project.
- Wait for the IDE to resolve dependencies and configure the project.
- Locate the main class file named DesktopLauncher.java in the package `dev.plagarizers.klotski.desktop` and run it as a
  Java application.
- Note: You may need to set up the run configuration in your IDE to specify the main class.
    - For IntelliJ IDEA/ Eclipse, you can follow the
      instructions [here](https://libgdx.com/wiki/start/import-and-running).
    - Please note that in order to build the project, you will need to create a `local.properties`
      file in the root directory of the project and specify the path to your Android SDK installation. You can follow
      the instructions [here](https://libgdx.com/wiki/start/import-and-running#command-line)
- The game should start running, and you can start playing Klotski within the IDE's game window.

Please refer to the game's documentation for more detailed instructions and information on how to play the game.

# Gradle

This project uses [Gradle](http://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `android:lint`: performs Android project validation.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `desktop:jar`: builds application's runnable jar, which can be found at `desktop/build/libs`.
- `desktop:run`: starts the application.
- `server:run`: runs the server application.
- `test`: runs unit tests (if any).

# Dependencies

The provided Gradle build script in the root directory defines the dependencies used in the Klotski project.
Here's a description of the dependencies:

Android Gradle Plugin:

* Dependency: `com.android.tools.build:gradle:$androidPluginVersion`
* This is the Android Gradle plugin required for building Android applications.
  JUnit Jupiter:
* Test Implementation: `org.junit.jupiter:junit-jupiter-api:5.8.1`
* Test Runtime: `org.junit.jupiter:junit-jupiter-engine:5.8.1`
* These dependencies provide the necessary libraries for running JUnit 5 tests.
  Please note that the specific versions of the dependencies ($androidPluginVersion, 5.8.1) are not provided in the
  build
  script. You would need to replace them with the actual versions you want to use.

Please note that the provided build script is focused on configuring the project structure, test settings, and code
coverage using Jacoco. The actual application dependencies, such as LibGDX, may be defined in separate modules or
subprojects within the project structure.

In the core module, the following dependencies are defined:

* LibGDX FreeType: `api "com.badlogicgames.gdx:gdx-freetype:$gdxVersion"`
    * This dependency provides support for TrueType fonts in LibGDX. The version is specified by the gdxVersion
      variable.
* LibGDX Core: `api "com.badlogicgames.gdx:gdx:$gdxVersion"`
    * This dependency includes the core functionality of LibGDX. The version is specified by the gdxVersion variable.
* Gson: `implementation 'com.google.code.gson:gson:2.10.1'`
    * This dependency adds Gson, a library for JSON serialization and deserialization, to the project. The version used
      is 2.10.1.

While the core module contains the main game logic, the desktop module contains the desktop launcher for the game. The
following dependencies are defined in the desktop module:

* LibGDX LWJGL3 Backend: `implementation "com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion"`
    * This dependency provides the LWJGL3 backend for LibGDX, allowing the game to run on desktop platforms.
* LibGDX FreeType Platform: `implementation "com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-desktop"`
    * This dependency includes the native libraries for FreeType font rendering on desktop platforms.
* LibGDX Platform: `implementation "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop"`
    * This dependency includes the native libraries required for running LibGDX on desktop platforms.
* Project Core: `implementation project(':core')`
    * This dependency adds the project's core module as a dependency for the desktop module.

The android module contains the Android launcher for the game. The following dependencies are defined in the android
module:

* `coreLibraryDesugaring`: Adds the core library desugaring dependency for enabling Java 8+ language features on older
  Android versions.
* `implementation "com.badlogicgames.gdx:gdx-backend-android:$gdxVersion"`: Adds the LibGDX Android backend dependency.
* `implementation project(':core')`: Adds the project's core module as a dependency.
* `natives`: Defines the native dependencies required for different Android architectures (armeabi-v7a, arm64-v8a, x86,
  x86_64).
