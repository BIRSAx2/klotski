---
title: Design Patterns
parent: Notes on development
layout: default
nav_order: 1
---

# Design Patterns:

## Factory Method

The `KlotskiGame` class has a `getScreen(ScreenType)` method, which acts as a factory method by creating and
returning different types of screens based on the provided `ScreenType` parameter.

## Observer

The `BoardListener` class and its interaction with the `Board` class indicate the use of the Observer pattern. The
`BoardListener` acts as an observer that listens for events or changes in the Board and responds accordingly. This
pattern
decouples the observers from the subject (`Board`) and allows for loose coupling between them.

## MVC (Model-View-Controller)

The code is structured as with the MVC pattern, where `State` and `GameState` can
be considered as models, `Board` and `Tile`, the various screen classes as views, and the various listener
classes (`BoardListener`, `StartFromSaveClickListener`, etc.) as controllers.

## Singleton

The `FontHandler` and `SoundHandler` classes are implemented as singletons, which ensures that only one instance
of each class can exist throughout the application.

# GRASP Principles:

## Information Expert

The responsibility for performing operations related to the game's state and solving the puzzle is
assigned to the State and `KlotskiSolver` classes, respectively. These classes possess the most relevant information and
expertise to handle their respective tasks.

## Creator

The responsibility of creating instances of various classes is assigned to the appropriate classes themselves.

The `KlotskiGame` class is responsible for creating instances of screens and other necessary objects within the
game. `Coordinate` class provides a static method of() to create new instances of Coordinate objects.

## Low Coupling

The code shows loose coupling between classes by using interfaces, inheritance, and method parameters instead of relying
on concrete implementations directly. This allows for flexibility and easier maintenance of the code.
With dependencies primarily flowing in one direction. For example, the `GameScreen` class depends on `KlotskiGame` and
other utility classes, but there is no strong coupling among the utility classes themselves.

## High Cohesion

The classes have high cohesion as they encapsulate related functionality and have well-defined responsibilities. Each
class focuses on specific tasks such as managing game state, handling input events, managing levels, and rendering. (
e.g., `Block`, `State`, `GameState`, etc.).

## Controller

The various screen classes (`MainMenuScreen`, `GameScreen`, etc.) can be considered as controllers, responsible
for managing user interactions and updating the game state accordingly.

## Polymorphism

Polymorphism is utilized through inheritance and method overriding. For example, the `Block` class is
extended by various block types (`BigBlock`, `SmallBlock`, `HorizontalBlock`, `VerticalBlock`), allowing different
behaviors based on the type of block.
