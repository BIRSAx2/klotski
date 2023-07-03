---
title: Sequence Diagrams
layout: default
nav_order: 5
---
# System Sequence Diagram
```mermaid
sequenceDiagram
    actor Player
    box Klotski Game
        participant Main Menu Screen
        participant Settings Screen
        participant Levels Screen
        participant Game Screen
        participant Internal file
        participant Solver
    end
    participant Local file


    par Settings
        Player ->> Main Menu Screen: clicks on "SETTINGS"
        Main Menu Screen ->> Settings Screen: displays  
        Player ->> Settings Screen: adjust volume
    end


    par Select configuration
        Player ->> Main Menu Screen: clicks on "SELECT CONFIGURATION"
        Main Menu Screen ->> Levels Screen: displays  
        Levels Screen ->> Internal file: gets configurations
        Internal file ->> Levels Screen: returns configurations
        Levels Screen ->> Player: displays configurations
        Player ->> Levels Screen: chooses configuration
        Levels Screen ->> Game Screen: game starts
        Game Screen ->> Player: displays game
    end


    par New Game
        Player ->> Main Menu Screen: clicks on "NEW GAME"
        Main Menu Screen ->> Internal file: gets random configuration 
        Internal file ->> Main Menu Screen: returns random configurations
        Levels Screen ->> Player: displays configurations
        Player ->> Levels Screen: chooses configuration
        Levels Screen ->> Game Screen: game starts
        Game Screen ->> Player: displays game
    end


    par
        Player ->> Main Menu Screen: clicks on "LOAD GAME"
        Main Menu Screen ->> Levels Screen: displays  
        Levels Screen ->> Local file: gets configurations
        Local file ->> Levels Screen: returns configurations
        Levels Screen ->> Player: displays configurations
        Player ->> Levels Screen: chooses configuration
        Levels Screen ->> Game Screen: game starts
        Game Screen ->> Player: displays game
    end
    

    par Exit game
        Player ->> Main Menu Screen: clicks on "EXIT GAME"
        Main Menu Screen ->> Player: game shuts down
    end


    par Save Game
        Player ->> Game Screen: clicks on "Save"
        Game Screen ->> Saving Screen: displays
        Saving Screen ->> Player: asks for a name
        Player ->> Saving Screen: insert the name
        Player ->> Saving Screen: clicks on "SAVE"
        Saving Screen ->> Local file: saves the state of the game
        Saving Screen ->> Game Screen : back to
        Game Screen ->> Player : displays game
    end



    loop Playing the game
        par Move block
            Player ->> Game Screen: makes a move
            Game Screen ->> Game Screen: updates configuration
            Game Screen ->> Game Screen: moves_counter++
            Game Screen ->> Player: displays game
        end

        par Next best action
            Player ->> Game Screen: clicks on "Next move"
            Game Screen ->> Solver: request move
            Solver ->> Game Screen: returns move
            Game Screen ->> Game Screen: updates configuration
            Game Screen ->> Game Screen: moves_counter++
            Game Screen ->> Player: displays game
        end

        par Undo action
            Player ->> Game Screen: clicks on "Undo"
            Game Screen ->> Game Screen: gets configuration from stack
            Game Screen ->> Game Screen: updates configuration
            Game Screen ->> Game Screen: moves_counter--
            Game Screen ->> Player: displays game
                
        end

        par Reset setup
            Player ->> Game Screen: clicks on "Reset"
            Game Screen ->> Game Screen: gets initial configuration
            Game Screen ->> Game Screen: updates configuration
            Game Screen ->> Game Screen: moves_counter=0
            Game Screen ->> Player: displays game
        end
    end
```

# Internal sequence diagram
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

















