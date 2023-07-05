---
title: Domain Model
layout: default
nav_order: 3
---

## Domain Model Diagram
Let's briefly discuss the domain model. Observing the diagram it's possible to distinguish every use case. When the player opens the Klotski game for the first time, the game shows a brief "TUTORIAL" in order to inform the user how to play. Then main menu is displayed giving to the player the possibility to open "SETTINGS", to "LOAD" a game, to choose a "CONFIGURATION" or to start a "NEW" game. Then, when the game starts, through the GameState it is possible to "RESET" the game using the initial state, to "UNDO" a move, using the past states and asking for a hint using the Klotski Solver algorithm ("NEXT BEST MOVE"). Eventually it is possible to "SAVE" the game through the Saves Manager. Every cardinality on the domain model diagram is one to one so, in order to improve the legibility, it has been omitted.

```mermaid
classDiagram
    Player -- KlotskiGame: opens
    
    KlotskiGame -- MainMenu: displays

    KlotskiGame -- Tutorial: shows
    

    MainMenu -- Settings : creates
    MainMenu -- GameScreen : creates
    MainMenu -- LoadGame : creates
    MainMenu -- Configuration : creates
        
    LoadGame --  SavesManager : uses
    Configuration -- SavesManager :uses
    GameScreen -- SavesManager : saves
    
    
    GameScreen -- Board : creates
    GameScreen -- GameState: contains
    

    GameState: Current State
    GameState: Initial State
    GameState: Past States
    GameState: Moves Counter
    
    GameState -- KlotskiSolver: uses
    
    KlotskiSolver -- State: contains
    
    GameState -- State: creates
    
    Board --  GameState: uses
```

