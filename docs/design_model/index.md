---
title: Design Model
layout: default
nav_order: 5
---

# Design Model

- `dev.plagarizers.klotski`: This is the root package that contains the main class KlotskiGame, which represents the Klotski game itself. It also includes other sub-packages related to the game, such as game, gui, and util.

- `dev.plagarizers.klotski.game`: This package contains classes related to the game logic. It includes classes for different types of blocks (BigBlock, Block, HorizontalBlock, SmallBlock, VerticalBlock), as well as classes for game states and solving the Klotski puzzle (KlotskiSolver, State).

  - `dev.plagarizers.klotski.game.block`: This sub-package includes classes that represent different types of blocks used in the game. It contains Block and its subclasses BigBlock, HorizontalBlock, SmallBlock, and VerticalBlock.

  - `dev.plagarizers.klotski.game.state`: This package contains classes related to game states and solving the Klotski puzzle. It includes the KlotskiSolver class for finding the minimum number of steps required to solve the puzzle, as well as the State class representing the current state of the game.

  - `dev.plagarizers.klotski.game.util:` This package includes utility classes used in the game. It contains classes for managing saves (SavesManager), working with coordinates (Coordinate), defining directions (Direction), and handling game levels (Level).

- `dev.plagarizers.klotski.gui`: This package contains classes related to the graphical user interface (GUI) of the game. It includes classes for different actors used in the GUI (Board, BoardPreview, Tile), as well as listeners (BoardListener) for user interactions.

  - `dev.plagarizers.klotski.gui.actors`: This sub-package includes classes representing different actors used in the GUI. It contains Board, BoardPreview, and Tile classes.

  - `dev.plagarizers.klotski.gui.listeners:` This sub-package includes classes for event listeners used in the GUI. It contains listeners for specific events, such as clicking buttons or interacting with the game board.

  - `dev.plagarizers.klotski.gui.screens`: This sub-package contains classes representing different screens of the game GUI. It includes classes for screens such as the main menu, game screen, game over screen, configuration menu, load menu, and settings screen.

  - `dev.plagarizers.klotski.gui.state`: This package includes classes representing the game state within the GUI. It includes the GameState class, which holds information about the current state of the game, including the selected tile and the number of moves.

These packages and their classes collectively make up the structure and functionality of the Klotski game implementation.
## Package design
![Game]({{site.baseurl}}/assets/diagrams/packages.svg)

## Model design
![Game]({{site.baseurl}}/assets/diagrams/game_package.svg)

## GUI design
![Gui]({{site.baseurl}}/assets/diagrams/gui_package.svg)

## Full design
![Full]({{site.baseurl}}/assets/diagrams/full_package.svg)
