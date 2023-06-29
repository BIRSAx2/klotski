---
title: Requirements
layout: default
parent: Manual
nav_order: 1
---

## Requirements
It is possible to split the requirements into two sub-categories: functional and non-functional.

### Functional requirements
The functional requirements are the ones which represents a functionality in the program. In this case, the functional requirements are:
* Implementation of an interface that allows the user to move blocks
* Possibility to select different configurations of the puzzle before starting the game
* "Reset" function which allows the player to restart the puzzle from the beginning
* "Undo" function which allows the player to undo the action that was taken until the beginning of the puzzle is reached
* "Next best move" function which allows the player to be helped by an algorithm in order to resolve the puzzle
* Implementation of a "action counter" that keeps track of the total number of the moves taken by the user
* Possibility to save and load the game


### Non-functional requirements
The non-functional requirements are more abstract and do not represent an actual functionality of the system; they usually represent a property ora a constraint of the system. There are three types of non-functional requirements: **product** requirement (i.e. performance, security), **organizational** requirements (i.e. the programming language used) and **external** requirements (i.e. ethical code or law). In the KLOTSKI project, the non-functional requirements are:
* System requirements:
  * User-friendly UI
* Organizational requirements:
  * The project has to be implemented using Java 17
  * The tests will be conducted using JUnit
  * Utilize Grandle tool to build the system
  * Android SDK has to be installed and set
* External requirements:
  * The project has to be submitted before July 5th

