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

### New Game
```mermaid

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
```

### Save Game
```mermaid
```

### Move Blocks
```mermaid
```

### Next Best Action
```mermaid
```

### Undo Action
```mermaid
```

### Reset setup
```mermaid
```


### Moves counter
```mermaid
```
