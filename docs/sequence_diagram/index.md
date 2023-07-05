---
title: Sequence Diagrams
layout: default
nav_order: 6
---

# System Sequence Diagram
```mermaid
sequenceDiagram
    actor Player
    Participant Klotski Game
    participant Local storage


    opt Tutorial
        Player ->>+ Klotski Game: opens game for the 1st time
        Klotski Game -->> Player: displays the tutorial
    end
    Klotski Game -->> Player: displays the main menu


    alt New Game
        Player ->> Klotski Game: clicks on "NEW GAME"
        Klotski Game ->> Klotski Game: gets random configuration
        Klotski Game -->> Player: displays game screen
        
    else Select configuration
        Player ->> Klotski Game: clicks on "SELECT CONFIGURATION"
        Klotski Game -->> Player: displays configurations
        Player ->> Klotski Game: chooses configuration
        Klotski Game -->> Player: displays game screen
        
    else Load Game
        Player ->> Klotski Game: clicks on "LOAD GAME"
        Klotski Game ->>+ Local storage: gets saves 
        Local storage -->>-  Klotski Game: returns saves
        Player ->>  Klotski Game: chooses save configuration
        Klotski Game -->> Player: displays game
        
    else Play Game
        loop Playing the game
            alt Save Game
                Player ->> Klotski Game: clicks on "Save"
                Klotski Game ->> Local storage: saves the state of the game
                Local storage -->> Klotski Game : 
            else Move block
                Player ->> Klotski Game: makes a move
            else Next best action
                Player ->> Klotski Game: clicks on "Next move"
            else Undo action
                Player ->> Klotski Game: clicks on "Undo"
            else Reset setup
                Player ->> Klotski Game: clicks on "Reset"
            end
            Klotski Game -->> Player : displays game screen

        end
    else Settings
        Player ->> Klotski Game: clicks on "SETTINGS"
        Klotski Game -->> Player: displays settings screen
        opt Settings actions
            Player ->> Klotski Game: adjusts volume
        end
    else Exit game
        Player ->> Klotski Game: clicks on "EXIT GAME"
        Klotski Game -->>- Player: game shuts down
    end
```

# Internal Sequence Diagram

## Tutorial
```mermaid
sequenceDiagram
    actor User
    participant TutorialScreen
    participant MainMenuScreen
    
    User ->> TutorialScreen : opens the game for the fist time
    TutorialScreen -->> User : shows the tutorial screen
    
    alt Exit the game
        User ->>+ TutorialScreen : clicks on "QUIT" button
        TutorialScreen -->>- User : game shuts down
    else Skip the tutorial 
        User ->> TutorialScreen : clicks on "SKIP" button
        TutorialScreen ->> MainMenuScreen : displays
        MainMenuScreen -->> User : 
    else Make the tutorial
        loop while the tutorial is completed or is skipped
            alt Next button
            User ->>+ TutorialScreen : click on "NEXT" button
            TutorialScreen -->>- User : displays next information
            else Back button
            User ->>+ TutorialScreen : click on "BACK" button
            TutorialScreen -->>- User : displays previous information
            end
        end
        User ->> TutorialScreen : clicks on "FINISH" button
        TutorialScreen ->> MainMenuScreen : displays
        MainMenuScreen -->> User : 
    end
    
```

## Game Settings
```mermaid
sequenceDiagram
    actor User
    participant MainMenuScreen
    participant SettingsScreen

    User ->> MainMenuScreen: clicks on "SETTINGS"
    MainMenuScreen ->>+ SettingsScreen: displays
    par setupLayout
        SettingsScreen ->> SettingsScreen: makeMusicVolumeSettings
        SettingsScreen ->> SettingsScreen: makeEffectsVolumeSettings
        SettingsScreen ->> SettingsScreen: makeBackButton
    end
    SettingsScreen -->>- User: 
    opt Go back
        User ->>+ SettingsScreen : clicks on "BACK" button
        SettingsScreen -->>- MainMenuScreen : displays
        MainMenuScreen -->> User : 
    end
```

## Adjust Music/Effects Volume
```mermaid
sequenceDiagram
    actor User
    participant MainMenuScreen
    participant SettingsScreen

    User ->> MainMenuScreen: clicks on "SETTINGS"
    MainMenuScreen ->>+ SettingsScreen: displays
    SettingsScreen ->> SettingsScreen: setupLayout
    SettingsScreen -->>- User: 
    
    alt Music volume slider
        User ->> SettingsScreen: adjusts slider
    else Effects volume slider
        User ->> SettingsScreen: adjusts slider
    end

    SettingsScreen -->> User: Volume changed

```

## Select Configuration
```mermaid
sequenceDiagram
    actor User
    participant MainMenuScreen
    participant ConfigurationMenuScreen
    participant SavesManager
    participant Level
    participant BoardPreview
    participant GameScreen

    User ->> MainMenuScreen: clicks on "CHOOSE CONFIGURATION"

    MainMenuScreen ->>+ ConfigurationMenuScreen: displays
    par levels screen
        ConfigurationMenuScreen ->>+ SavesManager: loadLevels
        SavesManager ->> Level: fromJson
        Level -->> SavesManager: 
        SavesManager -->>- ConfigurationMenuScreen: #32;
        ConfigurationMenuScreen ->>+ SavesManager: loadCompletedLevels
        SavesManager ->> SavesManager: getCompletedLevelsFilePath
        SavesManager -->>- ConfigurationMenuScreen: 

        ConfigurationMenuScreen ->> Level: getName
        Level -->> ConfigurationMenuScreen:  

        ConfigurationMenuScreen ->> Level: setCompleted
        Level -->> ConfigurationMenuScreen:  

        par preview
            ConfigurationMenuScreen ->>+ BoardPreview: creates
            BoardPreview ->>+ Level: getName
            Level -->>- BoardPreview: level name
        end
        BoardPreview -->>- ConfigurationMenuScreen: render preview
        ConfigurationMenuScreen -->>- User: render configurations
    end

    User ->> ConfigurationMenuScreen: selects level
    ConfigurationMenuScreen ->> GameScreen: displays selected level
    GameScreen -->> User: #32;
```

## New Game
```mermaid
sequenceDiagram
    actor User
    participant MainMenuScreen
    participant GameScreen
    participant SavesManager
    participant Board
    participant GameState
    participant Level
    participant State

    User ->> MainMenuScreen: clicks on "NEW GAME"
    MainMenuScreen ->>+ GameScreen: displays 


    par game screen creation

        GameScreen ->> SavesManager : creates
        SavesManager -->> GameScreen : 

        GameScreen ->>+ Board : creates
        Board ->> Level: calls
        Level ->> State: fromDefaultLevel
        State -->> Level: 
        Level -->> Board: 
        Board ->> GameState: creates
        GameState -->> Board: 
        Board -->>- GameScreen: 

        GameScreen->>GameScreen: setupLayout
        
        GameScreen->>GameScreen: setupSaveDialog

        GameScreen -->> User: 

        alt move block
            User ->> GameScreen: moves block
            GameScreen ->>+ Board: makes action
            Board -->>- GameScreen: displays updated board
            GameScreen -->> User: 
        else Undo
            User ->> GameScreen: clicks "UNDO" button
            GameScreen ->>+ Board: makes action
            Board -->>- GameScreen: displays updated board
            GameScreen -->> User: 
        else Next Best Move
            User ->> GameScreen: clicks "NEXT BEST MOVE" button
            GameScreen ->>+ Board: makes action
            Board -->>- GameScreen: displays updated board
            GameScreen -->> User: 
        else reset layout
            User ->> GameScreen: clicks "RESET" button
            GameScreen ->>+ Board: makes action
            Board -->>- GameScreen: displays updated board
            GameScreen -->> User: 
        else save
            User ->> GameScreen: clicks "SAVE" button
            GameScreen ->> GameScreen: displays save dialog
            GameScreen -->> User: 
        else back
            User->>GameScreen: clicks "BACK" button
            GameScreen-->>MainMenuScreen: displays
            MainMenuScreen -->> User: 
        end
end
```

## Load Game
```mermaid
sequenceDiagram
    actor User
    participant MainMenuScreen
    participant LoadMenuScreen
    participant SavesManager
    participant Local Storage
    participant GameScreen
    

    User ->> MainMenuScreen : clicks on "LOAD GAME"

    MainMenuScreen ->>+ LoadMenuScreen : displays

    par load screen
        LoadMenuScreen ->> LoadMenuScreen : setupLayout
        LoadMenuScreen ->> LoadMenuScreen : setupConfirmDialog
        LoadMenuScreen ->> SavesManager : creates
        SavesManager -->> LoadMenuScreen :  
    end
    
    opt Go back
        User ->> LoadMenuScreen : clicks on "BACK"
        LoadMenuScreen -->> MainMenuScreen : displays
        MainMenuScreen -->> User : 
    end

    alt Delete save
        User ->> LoadMenuScreen : selects delete
        LoadMenuScreen ->> LoadMenuScreen : displays confirm dialog
        alt Confirm
            User ->> LoadMenuScreen : confirms delete
            LoadMenuScreen ->>+ SavesManager : calls
            SavesManager ->> Local Storage : delete selected save
            Local Storage -->> SavesManager : 
            SavesManager -->>- LoadMenuScreen : displays
            LoadMenuScreen -->> User : 
        else Cancel
            User ->> LoadMenuScreen : cancel delete
            LoadMenuScreen ->> LoadMenuScreen : displays
            LoadMenuScreen -->> User : 
        end
    else Load save
        User ->> LoadMenuScreen: selects save
        LoadMenuScreen ->>+ SavesManager: calls
        SavesManager ->> Local Storage : gets selected save
        Local Storage -->> SavesManager : 
        SavesManager -->>- LoadMenuScreen : returns selected save
        LoadMenuScreen ->> GameScreen: displays game with selected save
        GameScreen -->> User : 
    end
```

## Exit Game
```mermaid
sequenceDiagram
    actor User
    User ->> MainMenuScreen : clicks on "EXIT GAME"
    MainMenuScreen ->> MainMenuScreen: game shuts down
    MainMenuScreen -->> User: #32;
```

## Save Game
```mermaid
sequenceDiagram
actor User
    actor User
    participant MainMenuScreen
    participant ConfigurationScreen
    participant LoadGameScreen
    participant GameScreen
    participant SavesManager
    participant State
    participant Local Storage

    alt Start from random level 
        User ->> MainMenuScreen: clicks on "NEW GAME"
        MainMenuScreen -->> GameScreen: 
    else Choose Configuration
        User ->> MainMenuScreen: clicks on "CHOOSE CONFIGURATION"
        MainMenuScreen ->> ConfigurationScreen: 
        ConfigurationScreen -->> GameScreen: 
    else Load save
        User ->> MainMenuScreen: clicks on "LOAD GAME"
        MainMenuScreen ->> LoadGameScreen: 
        LoadGameScreen -->> GameScreen: 
    end
    

    activate GameScreen
    GameScreen -->> User: renders game screen
    
    User ->> GameScreen : clicks on "SAVE"
    GameScreen -->> User : displays save dialog
    alt Save file
        User ->>+ GameScreen : enter the save name
        User ->> GameScreen : clicks "SAVE"
        GameScreen ->>+ SavesManager : saveState

        SavesManager ->> State : toJson
        State -->> SavesManager : 
        SavesManager ->> Local Storage: Persist save to file
        Local Storage -->> SavesManager : 
        SavesManager -->>- GameScreen : 
    else Cancel
        User ->> GameScreen : clicks "CANCEL"
    end
    
    GameScreen -->>- User: displays game screen
```

## Move Blocks
```mermaid
sequenceDiagram
    actor User
    participant MainMenuScreen
    participant ConfigurationScreen
    participant LoadGameScreen
    participant GameScreen
    participant Board
    participant GameState


    alt Start from random level 
        User ->> MainMenuScreen: clicks on "NEW GAME"
        MainMenuScreen -->> GameScreen: displays
        activate GameScreen
    else Choose Configuration
        User ->> MainMenuScreen: clicks on "CHOOSE CONFIGURATION"
        MainMenuScreen ->> ConfigurationScreen: 
        ConfigurationScreen -->> GameScreen: displays
    else Load Save
        User ->> MainMenuScreen: clicks on "LOAD GAME"
        MainMenuScreen ->> LoadGameScreen: 
        LoadGameScreen -->> GameScreen: displays
    end

    GameScreen -->>- User: #32;
        
    alt touch and drag
        User ->> GameScreen : selects and drags a block
        GameScreen ->>+ Board: touchDragged
        Board ->> Board: calculate new position

    
    else keyboard and mouse
        alt mouse 
            User ->> GameScreen: clicks a block with mouse
        else keyboard
            User ->> GameScreen: presses TAB key
        end
        User ->> GameScreen:  presses a key
        GameScreen ->> Board: moves block


    end

    Board ->> GameState: updates
    GameState -->> Board: 
    Board -->>- GameScreen: 
    GameScreen -->> User: displays updated board
```

## Next Best Move
```mermaid
sequenceDiagram
    actor User
    participant MainMenuScreen
    participant ConfigurationScreen
    participant LoadGameScreen
    participant GameScreen
    participant Board
    participant GameState
    participant KlotskiSolver


    alt Start from random level 
        User ->> MainMenuScreen: clicks on "NEW GAME"
        MainMenuScreen -->> GameScreen: displays
        activate GameScreen
    else Choose Configuration
        User ->> MainMenuScreen: clicks on "CHOOSE CONFIGURATION"
        MainMenuScreen ->> ConfigurationScreen: 
        ConfigurationScreen -->> GameScreen: displays
    else Load Save
        User ->> MainMenuScreen: clicks on "LOAD GAME"
        MainMenuScreen ->> LoadGameScreen: 
        LoadGameScreen -->> GameScreen: displays
    end

    GameScreen -->>- User: #32;

    User ->> GameScreen : clicks "NEXT MOVE" button
    GameScreen ->> Board: uses

    par next best move
        Board ->> GameState: playBestMove
        GameState ->> KlotskiSolver:  calculates next move
        KlotskiSolver -->> GameState: returns new state
        GameState ->> GameState: updates state
        GameState -->> Board: updates game state
    end

    Board -->> GameScreen: 
    GameScreen -->> User: displays updated board
```

## Undo Action
```mermaid
sequenceDiagram
    actor User
    participant MainMenuScreen
    participant ConfigurationScreen
    participant LoadGameScreen
    participant GameScreen
    participant Board
    participant GameState


    alt Start from random level 
        User ->> MainMenuScreen: clicks on "NEW GAME"
        MainMenuScreen -->>+ GameScreen: displays
    else Choose Configuration
        User ->> MainMenuScreen: clicks on "CHOOSE CONFIGURATION"
        MainMenuScreen ->> ConfigurationScreen: 
        ConfigurationScreen -->> GameScreen: displays
    else Load Save
        User ->> MainMenuScreen: clicks on "LOAD GAME"
        MainMenuScreen ->> LoadGameScreen: 
        LoadGameScreen -->> GameScreen: displays
    end
    
    GameScreen -->> User : #32;
    
    User ->> GameScreen : clicks "UNDO" button
    GameScreen ->> Board : getGameState
    Board -->> GameScreen : 
    GameScreen ->>+ GameState : undoMove
    GameState -->>- GameScreen : displays previous state
    GameScreen -->> User : displays updated game screen
    deactivate GameScreen
    
```

## Reset game
```mermaid
sequenceDiagram
    actor User
    participant MainMenuScreen
    participant ConfigurationScreen
    participant LoadGameScreen
    participant GameScreen
    participant Board
    participant GameState


    alt Start from random level
        User ->> MainMenuScreen: clicks on "NEW GAME"
        MainMenuScreen -->>+ GameScreen: displays
    else Choose Configuration
        User ->> MainMenuScreen: clicks on "CHOOSE CONFIGURATION"
        MainMenuScreen ->> ConfigurationScreen: 
        ConfigurationScreen -->> GameScreen: displays
    else Load Save
        User ->> MainMenuScreen: clicks on "LOAD GAME"
        MainMenuScreen ->> LoadGameScreen: 
        LoadGameScreen -->> GameScreen: displays
    end
    
    GameScreen -->> User : #32;


    User ->> GameScreen : clicks on "RESET" button
    GameScreen ->> Board : getGameState
    Board -->> GameScreen : 
    GameScreen ->>+ GameState : reset
    par Reset game state
        GameState ->> GameState: clear all previous states
        GameState ->> GameState: set moves to 0
        GameState ->> GameState: update tiles
    end
    GameState -->>- GameScreen : displays initial state
    GameScreen -->> User : displays updated game screen
    deactivate GameScreen
    
```

## Moves Counter
```mermaid
sequenceDiagram
    actor User
    participant MainMenuScreen
    participant ConfigurationScreen
    participant LoadGameScreen
    participant GameScreen
    participant Board
    participant GameState
    participant State


    alt Start from random level
        User ->> MainMenuScreen: clicks on "NEW GAME"
        MainMenuScreen -->>+ GameScreen: displays
    else Choose Configuration
        User ->> MainMenuScreen: clicks on "CHOOSE CONFIGURATION"
        MainMenuScreen ->> ConfigurationScreen: 
        ConfigurationScreen -->> GameScreen: displays
    else Load Save
        User ->> MainMenuScreen: clicks on "LOAD GAME"
        MainMenuScreen ->> LoadGameScreen: 
        LoadGameScreen -->> GameScreen: displays
    end

    GameScreen -->> User : #32;

    alt Move blocks
        User ->> GameScreen : 
    else Next best action
        User ->> GameScreen : 
    else Undo action
        User ->> GameScreen : 
    else Reset game
        User ->> GameScreen : 
    end
    
    GameScreen ->> Board : uses
    Board ->>+ GameState : perform action
    GameState ->> State : update moves
    State -->> GameState : 
    GameState -->>- Board : 
    Board -->> GameScreen : update game state
    GameScreen -->>- User : displays updated game screen
```
