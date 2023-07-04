---
title: Sequence Diagrams
layout: default
nav_order: 5
---

# System Sequence Diagram

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

## Load Game

Trigolo

## Exit Game
Trigolo

## Save Game

```mermaid
sequenceDiagram
actor User
    actor User
    User ->> MainMenuScreen: start game
    
    alt Choose Configuration
        MainMenuScreen ->> ConfigurationScreen: 
        ConfigurationScreen ->> GameScreen: 
    end

    alt Load Save
        MainMenuScreen ->> LoadGameScreen: 
        LoadGameScreen ->> GameScreen: 
    end
    
    alt Start from random level 
        MainMenuScreen ->> GameScreen: 
    end
    activate GameScreen
    GameScreen -->> User: Render game screen
    
    User ->> GameScreen : click on save button
    activate GameScreen
    GameScreen ->> SavesManager : saveState
    activate SavesManager
    SavesManager ->> State : toJson

    activate State
    State -->> SavesManager : #32; 
    SavesManager ->> SavesManager: Persist to file
    deactivate State
    SavesManager -->> GameScreen : #32; 
    deactivate SavesManager
    deactivate GameScreen
```
## Move Blocks
# Incompleto
```mermaid
sequenceDiagram
actor User
    actor User
    User ->> MainMenuScreen: start game
    
    alt Choose Configuration
        MainMenuScreen ->> ConfigurationScreen: 
        ConfigurationScreen ->> GameScreen: 
    end

    alt Load Save
        MainMenuScreen ->> LoadGameScreen: 
        LoadGameScreen ->> GameScreen: 
    end
    
    alt Start from random level 
        MainMenuScreen ->> GameScreen: 
    end
    activate GameScreen
    GameScreen ->> Board: create
    Board ->> GameState: create
    GameState -->> Board: 
    Board ->> BoardListener: create
    BoardListener -->> Board: 
    Board -->> GameScreen: 
    GameScreen -->> User: Render game screen
    deactivate GameScreen
    
    activate BoardListener
    alt mouse movement
    User ->> BoardListener : touchDragged
    BoardListener ->> BoardListener : calculateDragDirection
    end
    alt arrow keys 
    User ->> BoardListener: key down
    end
    BoardListener ->> GameState : moveBlock
    GameState ->> State : clone
    State -->> GameState: clonedState
    activate State
    GameState ->> State: moveBlock on clonedState
    State ->> State: canMoveBlock
    State ->> State: isValidBlock
    State -->> GameState: 
    deactivate State
    activate GameState
    GameState ->> GameState : updateTiles
    GameState ->> GameState : createTile
    GameState -->> Board: new state
    Board -->> GameScreen: Render game state
    GameScreen -->> User: Render game
    

```

## Next Best Action
Mouhi
## Undo Action
Gianluca
## Reset game
Gianluca
## Moves Counter
Gianluca
