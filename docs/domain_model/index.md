---
title: Domain Model
layout: default
nav_order: 3
---

## Domain Model Diagram
Let's briefly discuss the domain model. Observing the diagram it's possible to distinguish every use case. When the player opens the Klotski game the main menu displays giving to the player the possibility to open "SETTINGS", to "LOAD" a game, to choose a "CONFIGURATION" or to start a "NEW" game. Then, when the game starts, through the GameState it is possible to "RESET" the game using the initial state, to "UNDO" a move, using the past states and asking for a hint using the Klotski Solver algorithm ("NEXT BEST MOVE"). Eventually it is possible to "SAVE" the game through the Saves Manager.

```mermaid
classDiagram
    Player "1"--"1" KlotskiGame: opens
    
    KlotskiGame "1"--"1" MainMenu: Displays
    

    MainMenu "1"--"1" Settings : creates
    MainMenu "1"--"1" GameScreen : creates
    MainMenu "1"--"1" LoadGame : creates
    MainMenu "1"--"1" Configuration : creates
        
    LoadGame "1"--"1"  SavesManager : uses
    Configuration "1"--"1" SavesManager :uses
    GameScreen "1"--"1" SavesManager : saves
    
    
    GameScreen "1"--"1" Board : creates
    GameScreen "1"--"1" GameState: contains
    

    GameState: Current State
    GameState: Initial State
    GameState: Past States
    GameState: Moves Counter
    
    GameState "1"--"1" KlotskiSolver: uses
    
    KlotskiSolver "1"--"1" State: contains
    
    GameState "1"--"1" State: creates
    
    Board "1"--"1"  GameState: uses
```

