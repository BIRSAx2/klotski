---
title: Sequence Diagrams
layout: default
nav_order: 5
---
# Sequence diagram
In this page it is possible to find the system sequence diagram and the internal sequence diagram for every use case.

### Settings
```mermaid
---
title: Settings
---
sequenceDiagram
actor Player

    box Klotski Game
        participant Main Menu Screen
        participant Settings Screen
    end

    Player ->> Main Menu Screen: clicks on "SETTINGS"

    Main Menu Screen ->> Settings Screen: displays  

    Player ->> Settings Screen: adjust volume
```

### Select configuration
```mermaid
---
title: Select configuration
---
sequenceDiagram
actor Player

    box Klotski Game
        participant Main Menu Screen
        participant Levels Screen
        participant Internal file
        participant Game Screen
    end

    Player ->> Main Menu Screen: clicks on "SELECT CONFIGURATION"

    Main Menu Screen ->> Levels Screen: displays  

    Levels Screen ->> Internal file: gets configurations

    Internal file ->> Levels Screen: returns configurations

    Levels Screen ->> Player: displays configurations

    Player ->> Levels Screen: chooses configuration

    Levels Screen ->> Game Screen: game starts

    Game Screen ->> Player: displays game
```


### New game
```mermaid
---
title: New Game
---
sequenceDiagram
    Player ->> Main Menu Screen: clicks on "NEW GAME"

    Main Menu Screen ->> Internal file: gets random configuration 

    Internal file ->> Main Menu Screen: returns random configurations


    Levels Screen ->> Player: displays configurations

    Player ->> Levels Screen: chooses configuration

    Levels Screen ->> Game Screen: game starts

    Game Screen ->> Player: displays game
```


### Load Game
```mermaid
---
title: Load Game
---
sequenceDiagram
    actor Player

    box Klotski Game
        participant Main Menu Screen
        participant Local file
        participant Game Screen
        participant Levels Screen
    end

    Player ->> Main Menu Screen: clicks on "LOAD GAME"

    Main Menu Screen ->> Levels Screen: displays  

    Levels Screen ->> Local file: gets configurations

    Local file ->> Levels Screen: returns configurations

    Levels Screen ->> Player: displays configurations

    Player ->> Levels Screen: chooses configuration

    Levels Screen ->> Game Screen: game starts

    Game Screen ->> Player: displays game
```

Internal sequence diagram
```mermaid
sequenceDiagram
    participant MainMenu
    participant LoadMenuScreen
    participant StartFromSaveClickListener
    participant SavesManager
    
    MainMenu ->> LoadMenuScreen: Load Game
    LoadMenuScreen ->> StartFromSaveClickListener: Click
    StartFromSaveClickListener ->> SavesManager: loadStateByName
    SavesManager ->> SavesManager: getSaveFilePath
    SavesManager ->> SavesManager: loadStateByPath
    SavesManager ->> State: fromJson
    State -->> SavesManager: state
    
    SavesManager -->> StartFromSaveClickListener: state
    StartFromSaveClickListener ->> Level : state
    Level -->> StartFromSaveClickListener: level
    StartFromSaveClickListener ->> GameScreen: level
```

### Exit Game
```mermaid
---
title: Move blocks
---
sequenceDiagram
    actor Player

    box Klotski Game
        participant Main Menu Screen
    end


    Player ->> Main Menu Screen: clicks on "EXIT GAME"

    Main Menu Screen ->> Player: game shuts down
```


### Save game
```mermaid
---
title: Save Game
---
sequenceDiagram
    actor Player

    box Klotski Game
        participant Game Screen
        participant Saving Screen
        participant Local file
    end

    Player ->> Game Screen: clicks on "Save"

    Game Screen ->> Saving Screen: displays

    Saving Screen ->> Player: asks for a name

    Player ->> Saving Screen: insert the name

    Player ->> Saving Screen: clicks on "SAVE"

    Saving Screen ->> Local file: saves the state of the game

    Saving Screen ->> Game Screen : back to

    Game Screen ->> Player : displays game

```


### Move blocks
```mermaid
---
title: Move block
---
sequenceDiagram
    actor Player

    box Klotski Game
        participant Game Screen

    end

    Player ->> Game Screen: makes a move

    Game Screen ->> Game Screen: updates configuration

    Game Screen ->> Player: displays game
```


### Next best action
```mermaid
---
title: Next Best Move
---
sequenceDiagram
    actor Player

    box Klotski Game
        participant Game Screen
        participant Solver
    end

    Player ->> Game Screen: clicks on "Next move"

    Game Screen ->> Solver: request move

    Solver ->> Game Screen: returns move

    Game Screen ->> Game Screen: updates configuration

    Game Screen ->> Player: displays game
```


### Undo action
```mermaid
---
title: Undo action
---
sequenceDiagram
    actor Player

    box Klotski Game
        participant Game Screen
    end

    Player ->> Game Screen: clicks on "Undo"

    Game Screen ->> Game Screen: gets configuration from stack
    
    Game Screen ->> Game Screen: updates configuration

    Game Screen ->> Player: displays game
```


### Reset setup
```mermaid
---
title: Reset setup
---
sequenceDiagram
    actor Player

    box Klotski Game
        participant Game Screen
    end

    Player ->> Game Screen: clicks on "Reset"

    Game Screen ->> Game Screen: gets initial configuration
    
    Game Screen ->> Game Screen: updates configuration

    Game Screen ->> Player: displays game
```


### Moves counter
```mermaid
---
title: Moves counter
---
sequenceDiagram
    actor Player

    box Klotski Game
        participant Game Screen
    end

    Player ->> Game Screen: makes an action

    Game Screen ->> Game Screen: updates moves counter
    
    Game Screen ->> Player: displays move counter
```


























