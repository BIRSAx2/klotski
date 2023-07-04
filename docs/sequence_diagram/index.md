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


    par New Game
        Player ->> Klotski Game: clicks on "NEW GAME"
        Klotski Game ->> Player: displays random configurations
        Klotski Game ->> Player: displays game
    end


    par Select configuration
        Player ->> Klotski Game: clicks on "SELECT CONFIGURATION"
        Klotski Game ->> Player: displays configurations
        Player ->> Klotski Game: chooses configuration
        Klotski Game ->> Player: displays game
    end


    par Load Game
        Player ->> Klotski Game: clicks on "LOAD GAME"
        Klotski Game ->> Local file: gets configurations
        Local file ->>  Klotski Game: returns configurations
        Player ->>  Klotski Game: chooses configuration
        Klotski Game ->> Player: displays game
    end


    par Save Game
        Player ->> Klotski Game: clicks on "Save"
        Klotski Game ->> Local file: saves the state of the game
        Klotski Game ->> Player : displays game
    end



    loop Playing the game
        par Move block
            Player ->> Klotski Game: makes a move
            Klotski Game ->> Player: displays game
        end

        par Next best action
            Player ->> Klotski Game: clicks on "Next move"
            Klotski Game ->> Player: displays game
        end

        par Undo action
            Player ->> Klotski Game: clicks on "Undo"
            Klotski Game ->> Player: displays game
                
        end

        par Reset setup
            Player ->> Klotski Game: clicks on "Reset"
            Klotski Game ->> Player: displays game
        end
    end

    par Settings
        Player ->> Klotski Game: clicks on "SETTINGS"
        Klotski Game ->> Player: displays game settings
        Player ->> Klotski Game: adjust volume
    end


    par Exit game
        Player ->> Klotski Game: clicks on "EXIT GAME"
        Klotski Game ->> Player: game shuts down
    end
```

[//]: # (Reference: https://mermaid.js.org/syntax/sequenceDiagram.html)

# Internal Sequence Diagram

### Game Settings
```mermaid
sequenceDiagram
    actor User
    User ->> MainMenuScreen: start game
    activate MainMenuScreen
    MainMenuScreen ->> SettingsScreen: press on "SETTINGS"
    SettingsScreen -->> User: Render settings
    deactivate MainMenuScreen
```

### Adjust Music/Effects Volume
```mermaid
sequenceDiagram
    actor User
    User ->> MainMenuScreen: Start game
    activate MainMenuScreen
    MainMenuScreen ->> SettingsScreen: press on "SETTINGS"
    activate SettingsScreen
    SettingsScreen ->> SettingsScreen: setupLayout
    activate SettingsScreen
    SettingsScreen ->> SettingsScreen: makeMusicVolumeSettings
    activate SettingsScreen
    deactivate SettingsScreen
    SettingsScreen ->> SettingsScreen: makeEffectsVolumeSettings
    SettingsScreen ->> SettingsScreen: makeBackButton
    activate SettingsScreen
    SettingsScreen ->> BackToMainMenuClickListener: create
    activate BackToMainMenuClickListener
    BackToMainMenuClickListener -->> SettingsScreen: listener
    deactivate BackToMainMenuClickListener
    deactivate SettingsScreen
    SettingsScreen -->> User: Render settings;
    deactivate SettingsScreen
    deactivate MainMenuScreen
    par Music volume slider
        User ->> SettingsScreen: Adjust slider
    end
    par Effects volume slider
        User ->> SettingsScreen: Adjust slider
    end
    activate SettingsScreen
    SettingsScreen -->> User: Volume changed
    deactivate SettingsScreen
```

### Select Starting Configuration
```mermaid
sequenceDiagram
    actor User
    User ->> MainMenuScreen: start game
    activate MainMenuScreen
    MainMenuScreen ->> ConfigurationMenuScreen: press on "CHOOSE CONFIGURATION"
    deactivate MainMenuScreen
    activate ConfigurationMenuScreen
    ConfigurationMenuScreen ->> SavesManager: loadLevels
    activate SavesManager
    SavesManager ->> Level: fromJson
    activate Level
Level -->> SavesManager: #32; 
deactivate Level
SavesManager -->> ConfigurationMenuScreen: #32; 
deactivate SavesManager
ConfigurationMenuScreen ->> SavesManager: loadCompletedLevels
activate SavesManager
SavesManager ->> SavesManager: getCompletedLevelsFilePath
SavesManager -->> ConfigurationMenuScreen:  #32; 
deactivate SavesManager
ConfigurationMenuScreen ->> Level: getName
activate Level
Level -->> ConfigurationMenuScreen: #32; 
deactivate Level
ConfigurationMenuScreen ->> Level: setCompleted
activate Level
Level -->> ConfigurationMenuScreen: #32; 
deactivate Level
ConfigurationMenuScreen ->> BoardPreview: create
activate BoardPreview
BoardPreview ->> Level: getName
activate Level
Level -->> BoardPreview: level name

deactivate Level
BoardPreview -->> ConfigurationMenuScreen: render preview
deactivate BoardPreview

ConfigurationMenuScreen -->> User: Render configurations
deactivate ConfigurationMenuScreen


User ->> ConfigurationMenuScreen: Select Level
activate ConfigurationMenuScreen
ConfigurationMenuScreen ->> GameScreen: Level
GameScreen -->> User: Render board
deactivate ConfigurationMenuScreen
```

### New Game
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


GameScreen -->> GameScreen : #32; 
deactivate GameScreen
GameScreen ->> GameScreen : setupSaveDialog
activate GameScreen

GameScreen -->> GameScreen : #32; 
deactivate GameScreen
deactivate GameScreen

```

### Load Game
```mermaid
sequenceDiagram
    actor User
    User ->> LoadMenuScreen : setupLayout
    activate LoadMenuScreen
    LoadMenuScreen ->> StartFromSaveClickListener : new
    activate StartFromSaveClickListener
    StartFromSaveClickListener ->> SavesManager : new
    activate SavesManager
    SavesManager -->> StartFromSaveClickListener : #32; 
    deactivate SavesManager
    StartFromSaveClickListener -->> LoadMenuScreen : #32; 
    deactivate StartFromSaveClickListener
    LoadMenuScreen ->> BackToMainMenuClickListener : new
    activate BackToMainMenuClickListener
    BackToMainMenuClickListener -->> LoadMenuScreen : #32; 
    deactivate BackToMainMenuClickListener
    deactivate LoadMenuScreen
```

### Exit Game
```mermaid
sequenceDiagram
    actor User
    User ->> MainMenuScreen : clicks on "EXIT GAME"
    activate MainMenuScreen
    deactivate MainMenuScreen
```


