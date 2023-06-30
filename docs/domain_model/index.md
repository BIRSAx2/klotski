---
title: Domain Model
layout: default
has_children: true
nav_order: 3
---

## Domain Model Diagram
Let's briefly discuss the domain model. Observing the diagram it's possible to distinguish every use case. There is a "SETTINGS" screen which will allow the user to change the resolution and the volume as he prefers. It is possible to "LOAD", "SAVE" and choose a configuration of the puzzle through the SavesManager. The GameScreen provides the interface to interact with the puzzle: through the GameState it is possible to "MOVE" a block, "UNDO" an action, get the "NEXT BEST ACTION" using KlotskiSolver, "RESTART" the puzzle from the beginning and visualize the "MOVES COUNTER".

```mermaid
classDiagram
    Player -- KlotskiGame: opens
    
    KlotskiGame -- MainMenu: Displays
    

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

