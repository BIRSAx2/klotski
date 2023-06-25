# Klotski

## Installation Instructions
1. Install JDK 17 from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.java.net/).
2. Download and install [Android Sdk](https://developer.android.com/tools/releases/platform-tools).
3. Clone this repository

Rename the `local.properties.example` file to `local.properties` and add the path to your Android SDK installation. For example, on Windows, the path might look like this:



## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `desktop`: Primary desktop platform using LWJGL3.
- `android`: Android mobile platform. Needs Android SDK.

## Gradle

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
- `headless:run`: starts the headless application. Note: if headless sources were not modified - and the application still creates `ApplicationListener` from `core` project - this task might fail due to no graphics support.
- `idea`: generates IntelliJ project data.
- `desktop:jar`: builds application's runnable jar, which can be found at `desktop/build/libs`.
- `desktop:run`: starts the application.
- `server:run`: runs the server application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
