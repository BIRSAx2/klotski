---
title: Use Cases
layout: default
has_children: true
nav_order: 3
---

[//]: # (Forse inserire anche i settings e i livelli negli use cases)

| Use case Name | New game |
|---------------|----------|
| Actors        |          |
| Description   |          |
| Data          |          |
| Stimulus      |          |
| Response      |          |
| Comments      |

| Use case Name | Choose configuration |
|---------------|----------------------|
| Actors        |                      |
| Description   |                      |
| Data          |                      |
| Stimulus      |                      |
| Response      |                      |
| Comments      |


| Use case Name | Load game                                              |
|--------------|--------------------------------------------------------|
| Actors       | User, log file                                         |
| Description  | The user will be able to load a saved game             |
| Data         | The state of the game that was saved                   |
| Stimulus     | The "LOAD GAME" button is pressed                      |
| Response     | The list of all the avaiable saves will appear         |
| Comments     | If no game is saved, it is not possible to load a game |

| Use case Name | Save game                                                                  |
|---------------|----------------------------------------------------------------------------|
| Actors        | User, file                                                                 |
| Description   | During a game the stat can be saved in a file                              |
| Data          | The state of the game is saved in the log file                             |
| Stimulus      | The "SAVE GAME" button, during a game, is pressed                          |
| Response      | The state of the game is saved in a file                                   |
| Comments      | The saved game will appear inn the list when the user wants to load a game |

| Use case Name | Exit game                          |
|---------------|------------------------------------|
| Actors        | User                               |
| Description   | The puzzle can obviously be closed |
| Data          | None                               |
| Stimulus      | The "EXIT GAME" button is pressed  |
| Response      | The game shuts down.               |
| Comments      | None                               |


| Use case Name | Move blocks                                                                              |
|---------------|------------------------------------------------------------------------------------------|
| Actors        | User                                                                                     |
| Description   | The user, when starts a game, can move the blocks in order to solve the puzzle           |
| Data          | None                                                                                     |
| Stimulus      | After selecting the block with the mouse it can be moved using the keyboard or the mouse |
| Response      | The selected block moves                                                                 |
| Comments      | The block won't move if it is surrounded by other blocks                                 |


| Use case Name | Moves counter                                                                   |
|---------------|---------------------------------------------------------------------------------|
| Actors        | User                                                                            |
| Description   | The puzzle has a counter that will keep track of the moves token during the game |
| Data          | None                                                                            |
| Stimulus      | An action is taken by the user                                                  |
| Response      | The counter move will be updated                                                |
| Comments      | The counter can't be a negtaive number                                          |

| Use case Name | Next best action                                                                                                        |
|---------------|-------------------------------------------------------------------------------------------------------------------------|
| Actors        | User                                                                                                                    |
| Description   | This functionality will simplify the puzzle to the user moving a determined block                                       |
| Data          | None                                                                                                                    |
| Stimulus      | The "NEXT MOVE" button is pressed during the game                                                                       |
| Response      | An algorithm will process the state of the game and a block will be moved in order to facilitate the puzzle to the user |
| Comments      | When always clicking the "NEXT MOVE" button the puzzle will be solved                                                   |

| Use case Name | Undo action                                                                         |
|---------------|-------------------------------------------------------------------------------------|
| Actors        | User                                                                                |
| Description   | The game gives the possibility to undo the move just taken                          |
| Data          | None                                                                                |
| Stimulus      | The "UNDO" button is pressed during the game                                                        |
| Response      | The move just taken is undone                                                       |
| Comments      | When always clicking the "UNDO" button the puzzle will be resetted to the beginning |

| Use case Name | Reset setup                                   |
|---------------|-----------------------------------------------|
| Actors        | User                                          |
| Description   | The game                                      |
| Data          | None                                          |
| Stimulus      | The "RESET" button is pressed during the game |
| Response      | The game will restart from the beginning      |
| Comments      | None                                          |


