---
title: Dependencies
layout: default
parent: Manual
nav_order: 3
---

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
  * This dependency provides support for TrueType fonts in LibGDX. The version is specified by the gdxVersion variable.
* LibGDX Core: `api "com.badlogicgames.gdx:gdx:$gdxVersion"`
  * This dependency includes the core functionality of LibGDX. The version is specified by the gdxVersion variable.
* Gson: `implementation 'com.google.code.gson:gson:2.10.1'`
  * This dependency adds Gson, a library for JSON serialization and deserialization, to the project. The version used is 2.10.1.

While the core module contains the main game logic, the desktop module contains the desktop launcher for the game. The following dependencies are defined in the desktop module:
* LibGDX LWJGL3 Backend: `implementation "com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion"`
  * This dependency provides the LWJGL3 backend for LibGDX, allowing the game to run on desktop platforms.
* LibGDX FreeType Platform: `implementation "com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-desktop"`
  * This dependency includes the native libraries for FreeType font rendering on desktop platforms.
* LibGDX Platform: `implementation "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop"`
  * This dependency includes the native libraries required for running LibGDX on desktop platforms.
* Project Core: `implementation project(':core')`
  * This dependency adds the project's core module as a dependency for the desktop module.

The android module contains the Android launcher for the game. The following dependencies are defined in the android module:

* `coreLibraryDesugaring`: Adds the core library desugaring dependency for enabling Java 8+ language features on older Android versions.
* `implementation "com.badlogicgames.gdx:gdx-backend-android:$gdxVersion"`: Adds the LibGDX Android backend dependency.
* `implementation project(':core')`: Adds the project's core module as a dependency.
* `natives`: Defines the native dependencies required for different Android architectures (armeabi-v7a, arm64-v8a, x86, x86_64).
