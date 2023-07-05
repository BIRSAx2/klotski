---
title: Sequence Diagrams
layout: default
nav_order: 5
---

# System Sequence Diagram
```mermaid
sequenceDiagram
    actor Player
    Participant Klotski Game
    participant Local file


    par Tutorial
        Player ->> Klotski Game: opens game for the 1st time
        Klotski Game ->> Player: displays the tutorial
        Klotski Game ->> Player: displays the main menu
    end


    alt New Game
        Player ->> Klotski Game: clicks on "NEW GAME"
        Klotski Game ->> Player: displays random configurations
        Klotski Game ->> Player: displays game
    end


    alt Select configuration
        Player ->> Klotski Game: clicks on "SELECT CONFIGURATION"
        Klotski Game ->> Player: displays configurations
        Player ->> Klotski Game: chooses configuration
        Klotski Game ->> Player: displays game
    end


    alt Load Game
        Player ->> Klotski Game: clicks on "LOAD GAME"
        Klotski Game ->> Local file: gets configurations
        Local file ->>  Klotski Game: returns configurations
        Player ->>  Klotski Game: chooses configuration
        Klotski Game ->> Player: displays game
    end




    loop Playing the game

        alt Save Game
            Player ->> Klotski Game: clicks on "Save"
            Klotski Game ->> Local file: saves the state of the game
            Klotski Game ->> Player : displays game
        end

        alt Move block
            Player ->> Klotski Game: makes a move
            Klotski Game ->> Player: displays game
        end

        alt Next best action
            Player ->> Klotski Game: clicks on "Next move"
            Klotski Game ->> Player: displays game
        end

        alt Undo action
            Player ->> Klotski Game: clicks on "Undo"
            Klotski Game ->> Player: displays game
                
        end

        alt Reset setup
            Player ->> Klotski Game: clicks on "Reset"
            Klotski Game ->> Player: displays game
        end

    end

    alt Settings
        Player ->> Klotski Game: clicks on "SETTINGS"
        Klotski Game ->> Player: displays game settings
        Player ->> Klotski Game: adjusts volume
    end


    alt Exit game
        Player ->> Klotski Game: clicks on "EXIT GAME"
        Klotski Game ->> Player: game shuts down
    end
```

# Internal Sequence Diagram

## Tutorial
```mermaid
sequenceDiagram
    actor User
    participant TutorialScreen
    participant MainMenuScreen
    
    User ->> TutorialScreen : open the game for the fist time
    TutorialScreen -->> User : shows the tutorial screen
    
    alt Exit the game
        User ->>+ TutorialScreen : clicks on "QUIT" button
        TutorialScreen ->> TutorialScreen : close the application
        TutorialScreen -->>- User : 
    else Skip the tutorial 
        User ->>+ TutorialScreen : clicks on "SKIP" button
        TutorialScreen ->>+ MainMenuScreen : creates new main menu
        deactivate TutorialScreen
        MainMenuScreen -->>- User : presents the main menu
    else Make the tutorial
        loop while the tutorial is completed or is skipped
            alt Click on "NEXT" button
            User ->>+ TutorialScreen : Click on "NEXT" button
            TutorialScreen -->>- User : presents next information about the game
            else Click on "BACK" button
            User ->>+ TutorialScreen : Click on "BACK" button
            TutorialScreen -->>- User : presents previous information about the game
            end
        end
        User ->> TutorialScreen : clicks on "FINISH" button
        TutorialScreen ->>+ MainMenuScreen : create main menu
        MainMenuScreen -->>- User : shows the main menu screen
    end
    
```

## Game Settings
```mermaid
sequenceDiagram
    actor User
    participant MainMenuScreen
    participant SettingsScreen


    User ->> MainMenuScreen: clicks on "SETTINGS"
    activate MainMenuScreen
    MainMenuScreen ->> SettingsScreen: displays
    SettingsScreen -->> User: renders settings
    deactivate MainMenuScreen
```

## Adjust Music/Effects Volume
```mermaid
sequenceDiagram
    actor User
    participant MainMenuScreen
    participant SettingsScreen
    participant BackToMainMenuClickListener

    User ->> MainMenuScreen: clicks on "SETTINGS"
    activate MainMenuScreen
    MainMenuScreen ->> SettingsScreen: displays
    activate SettingsScreen
    SettingsScreen ->> SettingsScreen: setupLayout
    activate SettingsScreen

    par creates settings screen
        SettingsScreen ->> SettingsScreen: makeMusicVolumeSettings
        SettingsScreen ->> SettingsScreen: makeEffectsVolumeSettings
        SettingsScreen ->> SettingsScreen: makeBackButton
    end

    activate SettingsScreen
    SettingsScreen ->> BackToMainMenuClickListener: creates
    BackToMainMenuClickListener -->> SettingsScreen: 
    deactivate SettingsScreen

    SettingsScreen -->> User: renders settings
    deactivate SettingsScreen
    deactivate MainMenuScreen
    
    alt Music volume slider
        User ->> SettingsScreen: adjusts slider
    end

    alt Effects volume slider
        User ->> SettingsScreen: adjusts slider
    end

    SettingsScreen -->> User: Volume changed

```

## Select Configuration
```mermaid
sequenceDiagram
    actor User
    participant MainMenuScreen
    participant SavesManager
    participant ConfigurationMenuScreen
    participant Level
    participant BoardPreview
    participant GameScreen

    User ->> MainMenuScreen: clicks on "CHOOSE CONFIGURATION"

    MainMenuScreen ->> ConfigurationMenuScreen: displays
    activate ConfigurationMenuScreen

    par levels screen
        ConfigurationMenuScreen ->> SavesManager: loadLevels
        activate SavesManager

        SavesManager ->> Level: fromJson

        Level -->> SavesManager: 

        SavesManager -->> ConfigurationMenuScreen: #32;
        deactivate SavesManager

        ConfigurationMenuScreen ->> SavesManager: loadCompletedLevels
        activate SavesManager

        SavesManager ->> SavesManager: getCompletedLevelsFilePath

        SavesManager -->> ConfigurationMenuScreen: 
        deactivate SavesManager
    

        ConfigurationMenuScreen ->> Level: getName
        Level -->> ConfigurationMenuScreen:  

        ConfigurationMenuScreen ->> Level: setCompleted
        Level -->> ConfigurationMenuScreen:  

        par preview
            ConfigurationMenuScreen ->> BoardPreview: creates
            activate BoardPreview
            BoardPreview ->> Level: getName
            activate Level
            Level -->> BoardPreview: level name
        end

        deactivate Level
        BoardPreview -->> ConfigurationMenuScreen: render preview
        deactivate BoardPreview

        ConfigurationMenuScreen -->> User: Render configurations
        deactivate ConfigurationMenuScreen
    end

    User ->> ConfigurationMenuScreen: selects level
    ConfigurationMenuScreen ->> GameScreen: level
    GameScreen -->> User: renders game
```

## New Game
```mermaid

sequenceDiagram
actor User
User ->> GameScreen : new
activate GameScreen
GameScreen ->> SavesManager : new
activate SavesManager
SavesManager -->> GameScreen : #32; 
deactivate SavesManager
GameScreen ->> State : fromRandomLevel
activate State
State ->> SavesManager : loadLevelsFromDefaultPath
activate SavesManager
SavesManager ->> SavesManager : loadLevels
activate SavesManager
SavesManager ->> Level : fromJson
activate Level
Level -->> SavesManager : #32; 
deactivate Level
SavesManager -->> State : #32; 
deactivate SavesManager
State -->> GameScreen : #32; 
deactivate State
GameScreen ->> Board : new
activate Board
Board ->> Level : toState
activate Level
Level ->> State : fromDefaultConfiguration
activate State
State -->> Level : #32; 
deactivate State
Level -->> Board : #32; 
deactivate Level
Board ->> GameState : new
activate GameState
GameState ->> State : clone
activate State
State ->> State : new
activate State
GameState ->> State : clone
activate State
State ->> State : new
State -->> GameState : #32; 
deactivate State
GameState ->> State : clone
activate State
State ->> State : new
activate State
State -->> State : #32; 
deactivate State
State -->> GameState : #32; 
deactivate State
GameState ->> GameState : updateTiles
activate GameState
GameState ->> GameState : createTile
activate GameState
GameState ->> Tile : new
activate Tile
Tile -->> GameState : #32; 
deactivate Tile
GameState -->> Board : #32; 
deactivate GameState
Board ->> BoardListener : new
activate BoardListener
BoardListener -->> Board : #32; 
deactivate BoardListener

Board -->> GameScreen : #32; 
deactivate Board
GameScreen ->> GameScreen : setupLayout
activate GameScreen

GameScreen ->> BackToMainMenuClickListener : new
activate BackToMainMenuClickListener
BackToMainMenuClickListener -->> GameScreen : #32; 
deactivate BackToMainMenuClickListener

deactivate GameScreen
GameScreen ->> GameScreen : setupSaveDialog
activate GameScreen

GameScreen -->> User: Render game screen
deactivate GameScreen
```

### Load Game
```mermaid
sequenceDiagram
    actor User
    participant MainMenuScreen
    participant LoadMenuScreen
    participant StartFromSaveClickListener
    participant SavesManager
    participant BackToMainMenuClickListener
    participant GameScreen
    

    User ->> MainMenuScreen : clicks on "LOAD GAME"

    MainMenuScreen ->> LoadMenuScreen : displays

    par load screen
        activate LoadMenuScreen
        LoadMenuScreen ->> StartFromSaveClickListener : creates

        StartFromSaveClickListener ->> SavesManager : creates
        SavesManager -->> StartFromSaveClickListener :  

        StartFromSaveClickListener -->> LoadMenuScreen : 

        LoadMenuScreen ->> BackToMainMenuClickListener : creates
        activate BackToMainMenuClickListener
        BackToMainMenuClickListener -->> LoadMenuScreen :  
        LoadMenuScreen -->> User: renders load menu
        deactivate LoadMenuScreen
    end

    User ->> LoadMenuScreen: selects save
    LoadMenuScreen ->> GameScreen: save
    GameScreen -->> User: renders game


```

### Exit Game
```mermaid
sequenceDiagram
    actor User
    User ->> MainMenuScreen : clicks on "EXIT GAME"
    MainMenuScreen -->> MainMenuScreen: game shuts down
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

    alt Start from random level 
        User ->> MainMenuScreen: clicks on "NEW GAME"
        MainMenuScreen -->> GameScreen: 
    end
    
    alt Choose Configuration
        User ->> MainMenuScreen: clicks on "CHOOSE CONFIGURATION"
        MainMenuScreen ->> ConfigurationScreen: 
        ConfigurationScreen -->> GameScreen: 
    end

    alt Load Save
        User ->> MainMenuScreen: clicks on "LOAD GAME"
        MainMenuScreen ->> LoadGameScreen: 
        LoadGameScreen -->> GameScreen: 
    end
    

    activate GameScreen
    GameScreen -->> User: renders game screen
    
    User ->> GameScreen : clicks on "Save"
    activate GameScreen
    GameScreen ->> SavesManager : saveState
    activate SavesManager

    SavesManager ->> State : toJson
    State -->> SavesManager : 

    SavesManager ->> SavesManager: Persist to file
    SavesManager -->> GameScreen : 

    deactivate SavesManager
    deactivate GameScreen
    
    GameScreen -->> User: renders game screen
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
    participant BoardListener
    participant GameState


    alt Start from random level 
        User ->> MainMenuScreen: clicks on "NEW GAME"
        MainMenuScreen -->> GameScreen: 
    end
    
    alt Choose Configuration
        User ->> MainMenuScreen: clicks on "CHOOSE CONFIGURATION"
        MainMenuScreen ->> ConfigurationScreen: 
        ConfigurationScreen -->> GameScreen: 
    end

    alt Load Save
        User ->> MainMenuScreen: clicks on "LOAD GAME"
        MainMenuScreen ->> LoadGameScreen: 
        LoadGameScreen -->> GameScreen: 
    end

    par render game screen
        activate GameScreen
        GameScreen ->> Board: creates
        Board ->> GameState: creates
        GameState -->> Board: 
        Board ->> BoardListener: creates
        BoardListener -->> Board: 
        Board -->> GameScreen: 
        GameScreen -->> User: renders game screen
        deactivate GameScreen
    end
        
    alt mouse movement
        User ->> BoardListener : touchDragged
        BoardListener ->> BoardListener : calculateDragDirection
    end

    alt arrow keys 
        User ->> BoardListener: key down
    end

    BoardListener ->> GameState : moveBlock
    activate GameState
    GameState ->> GameState : updateTiles
    GameState ->> GameState : createTile
    GameState -->> Board: new state
    deactivate GameState
    Board -->> GameScreen: Render game state
    GameScreen -->> User: renders game screen
```

## Next Best Action
```mermaid
sequenceDiagram
    actor User
    participant MainMenuScreen
    participant ConfigurationScreen
    participant LoadGameScreen
    participant GameScreen
    participant Board
    participant BoardListener
    participant GameState
    participant KlotskiSolver


    alt Start from random level 
        User ->> MainMenuScreen: clicks on "NEW GAME"
        MainMenuScreen -->> GameScreen: 
    end
    
    alt Choose Configuration
        User ->> MainMenuScreen: clicks on "CHOOSE CONFIGURATION"
        MainMenuScreen ->> ConfigurationScreen: 
        ConfigurationScreen -->> GameScreen: 
    end

    alt Load Save
        User ->> MainMenuScreen: clicks on "LOAD GAME"
        MainMenuScreen ->> LoadGameScreen: 
        LoadGameScreen -->> GameScreen: 
    end

    par render game screen
        activate GameScreen
        GameScreen ->> Board: creates
        Board ->> GameState: creates
        GameState -->> Board: 
        Board ->> BoardListener: creates
        BoardListener -->> Board: 
        Board -->> GameScreen: 
        GameScreen -->> User: renders game screen
        deactivate GameScreen
    end
    
    activate BoardListener


    User ->> BoardListener : NextBestMove
    BoardListener ->> Board : getState
    Board -->> BoardListener: 

    BoardListener ->> KlotskiSolver: getSolution
    KlotskiSolver -->> BoardListener: 

    BoardListener ->> GameState : moveBlock
    activate GameState
    GameState ->> GameState : updateTiles
    GameState ->> GameState : createTile
    GameState -->> Board: new state
    deactivate GameState
    Board -->> GameScreen: Render game state
    GameScreen -->> User: renders game screen
    
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
        MainMenuScreen -->> GameScreen: 
    else Choose Configuration
        User ->> MainMenuScreen: clicks on "CHOOSE CONFIGURATION"
        MainMenuScreen ->> ConfigurationScreen: 
        ConfigurationScreen -->> GameScreen: 
    else Load Save
        User ->> MainMenuScreen: clicks on "LOAD GAME"
        MainMenuScreen ->> LoadGameScreen: 
        LoadGameScreen -->> GameScreen: 
    end

    par render game screen
        activate GameScreen
        GameScreen ->> Board: creates
        Board ->> GameState: creates
        GameState -->> Board: 
        Board ->> BoardListener: creates
        BoardListener -->> Board: 
        Board -->> GameScreen: 
        GameScreen -->> User: renders game screen
        deactivate GameScreen
    end
    
    
    User ->> GameScreen : undoButton
    activate GameScreen
    GameScreen ->> Board : getGameState
    activate Board
    Board ->> GameState : undoMove
    activate GameState
    GameState -->> Board : previous state
    deactivate GameState
    Board -->> GameScreen : render previous state
    deactivate Board
    GameScreen -->> User : render game screen
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
        MainMenuScreen -->> GameScreen: 
    else Choose Configuration
        User ->> MainMenuScreen: clicks on "CHOOSE CONFIGURATION"
        MainMenuScreen ->> ConfigurationScreen: 
        ConfigurationScreen -->> GameScreen: 
    else Load Save
        User ->> MainMenuScreen: clicks on "LOAD GAME"
        MainMenuScreen ->> LoadGameScreen: 
        LoadGameScreen -->> GameScreen: 
    end

    par render game screen
        activate GameScreen
        GameScreen ->> Board: creates
        Board ->> GameState: creates
        GameState -->> Board: 
        Board ->> BoardListener: creates
        BoardListener -->> Board: 
        Board -->> GameScreen: 
        GameScreen -->> User: renders game screen
        deactivate GameScreen
    end


    User ->> GameScreen : resetButton
    activate GameScreen
    GameScreen ->> Board : getGameState
    activate Board
    Board ->> GameState : reset
    activate GameState
    GameState ->> GameState: clear all previous states
    GameState ->> GameState: set moves to 0
    GameState ->> GameState: update tiles
    GameState -->> Board : initial state
    deactivate GameState
    Board -->> GameScreen : render initial state
    deactivate Board
    GameScreen -->> User : render game screen
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
    participant BoardListener
    participant GameState
    participant State


    alt Start from random level
        User ->> MainMenuScreen: clicks on "NEW GAME"
        MainMenuScreen -->> GameScreen: 
    else Choose Configuration
        User ->> MainMenuScreen: clicks on "CHOOSE CONFIGURATION"
        MainMenuScreen ->> ConfigurationScreen: 
        ConfigurationScreen -->> GameScreen: 
    else Load Save
        User ->> MainMenuScreen: clicks on "LOAD GAME"
        MainMenuScreen ->> LoadGameScreen: 
        LoadGameScreen -->> GameScreen: 
    end

    par render game screen
        activate GameScreen
        GameScreen ->> Board: creates
        Board ->> GameState: creates
        GameState -->> Board: 
        Board ->> BoardListener: creates
        BoardListener -->> Board: 
        Board -->> GameScreen: 
        GameScreen -->> User: renders game screen
        deactivate GameScreen
    end

    alt Move blocks
        User ->>+ GameScreen : 
    else Next best action
        User ->> GameScreen : 
    else Undo action
        User ->> GameScreen : 
    else Reset game
        User ->> GameScreen : 
    end
    
    GameScreen ->> BoardListener : capture action made by the user
    alt Play best move
        BoardListener ->>+ GameState : 
    else Move block
        BoardListener ->> GameState : 
    else Undo move
        BoardListener ->> GameState : 
    end
    
    alt Move block Play best move
        GameState ->>+ State : Move block
        State ->> State : increment moves
        State -->>- GameState : 
    else Undo move
        GameState ->> GameState : undo move
    end
    GameState ->> GameState : update tiles
    GameState -->>- Board : new state
    Board -->> GameScreen : render new state
    deactivate GameScreen
    GameScreen -->> User : render game screen
```
