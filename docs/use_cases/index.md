---
title: Use Cases
layout: default
nav_order: 2
---

# Use Cases

![Use Cases]({{site.baseurl}}/assets/diagrams/use_case.svg)

## Description

[//]: # (Game settings)
<table>
    <tr>
        <th colspan="2"> Game settings </th>
    </tr>
    <tr>
        <td>Actors</td>
        <td> User, computer </td>
    </tr>
    <tr>
        <td>Description</td>
        <td> The user is able to set the music and the sound effects volume </td>
    </tr>
    <tr>
        <td>Data</td>
        <td> None </td>
    </tr>
    <tr>
        <td>Stimulus</td>
        <td> The "SETTINGS" button is pressed </td>
    </tr>
    <tr>
        <td>Response</td>
        <td> A page where it can be possible to modify resolution, music and effects volume is displayed </td>
    </tr>
    <tr>
        <td>Comments</td>
        <td> None </td>
    </tr>
</table>

[//]: # (Select configuration)
<table>
    <tr>
        <th colspan="2"> Select configuration </th>
    </tr>
    <tr>
        <td>Actors</td>
        <td> User </td>
    </tr>
    <tr>
        <td>Description</td>
        <td> The game provides a set of 40 levels that can be selected. The different configuration are stored in an internal file  </td>
    </tr>
    <tr>
        <td>Data</td>
        <td> The level configuration selected by the user </td>
    </tr>
    <tr>
        <td>Stimulus</td>
        <td> The "SELECT CONFIGURATION" button is pressed </td>
    </tr>
    <tr>
        <td>Response</td>
        <td> A list of the 40 levels will appear and the user can choose one </td>
    </tr>
    <tr>
        <td>Comments</td>
        <td> None </td>
    </tr>
</table>

[//]: # (New Game)
<table>
    <tr>
        <th colspan="2"> New game </th>
    </tr>
    <tr>
        <td>Actors</td>
        <td> User </td>
    </tr>
    <tr>
        <td>Description</td>
        <td> A new game starts </td>
    </tr>
    <tr>
        <td>Data</td>
        <td> None </td>
    </tr>
    <tr>
        <td>Stimulus</td>
        <td> The "NEW GAME" button is pressed </td>
    </tr>
    <tr>
        <td>Response</td>
        <td> A configuration is selected randomly between the 40 provided and the puzzle starts </td>
    </tr>
    <tr>
        <td>Comments</td>
        <td> None </td>
    </tr>
</table>

[//]: # (Load game)
<table>
    <tr>
        <th colspan="2"> Load game </th>
    </tr>
    <tr>
        <td>Actors</td>
        <td> User, local storage file  </td>
    </tr>
    <tr>
        <td>Description</td>
        <td> The user will be able to load a saved game and can delete the savings from the list  </td>
    </tr>
    <tr>
        <td>Data</td>
        <td> The state of the game that was previously saved </td>
    </tr>
    <tr>
        <td>Stimulus</td>
        <td> The "LOAD GAME" button is pressed </td>
    </tr>
    <tr>
        <td>Response</td>
        <td> The list of all the available saves will appear </td>
    </tr>
    <tr>
        <td>Comments</td>
        <td> If no game is saved, it is not possible to load a game </td>
    </tr>
</table>

[//]: # (Save game)
<table>
    <tr>
        <th colspan="2"> Save game </th>
    </tr>
    <tr>
        <td>Actors</td>
        <td> User, local storage file </td>
    </tr>
    <tr>
        <td>Description</td>
        <td> During a game the state can be saved in a local file. The saving can be named as the user wants. </td>
    </tr>
    <tr>
        <td>Data</td>
        <td> The state of the game is saved in the local file </td>
    </tr>
    <tr>
        <td>Stimulus</td>
        <td> The "SAVE GAME" button, during a game, is pressed </td>
    </tr>
    <tr>
        <td>Response</td>
        <td> The state of the game is saved in a file and named as the user like </td>
    </tr>
    <tr>
        <td>Comments</td>
        <td> The saved game will appear in the list when the user wants to load a game </td>
    </tr>
</table>

[//]: # (Exit game)
<table>
    <tr>
        <th colspan="2"> Exit game </th>
    </tr>
    <tr>
        <td>Actors</td>
        <td> User </td>
    </tr>
    <tr>
        <td>Description</td>
        <td> The puzzle can obviously be closed </td>
    </tr>
    <tr>
        <td>Data</td>
        <td> None </td>
    </tr>
    <tr>
        <td>Stimulus</td>
        <td> The "EXIT GAME" button is pressed </td>
    </tr>
    <tr>
        <td>Response</td>
        <td> The game shuts down </td>
    </tr>
    <tr>
        <td>Comments</td>
        <td> None </td>
    </tr>
</table>

[//]: # (Move blocks)
<table>
    <tr>
        <th colspan="2"> Move blocks </th>
    </tr>
    <tr>
        <td>Actors</td>
        <td> User </td>
    </tr>
    <tr>
        <td>Description</td>
        <td> The user, when starts a game, can move the blocks in order to solve the puzzle </td>
    </tr>
    <tr>
        <td>Data</td>
        <td> None </td>
    </tr>
    <tr>
        <td>Stimulus</td>
        <td> After selecting the block with the mouse it can be moved using the keyboard or the mouse </td>
    </tr>
    <tr>
        <td>Response</td>
        <td> The selected block moves and the action is saved in a stack </td>
    </tr>
    <tr>
        <td>Comments</td>
        <td> The block won't move if it is surrounded by other blocks </td>
    </tr>
</table>

[//]: # (Moves counter)
<table>
    <tr>
        <th colspan="2"> Moves counter </th>
    </tr>
    <tr>
        <td>Actors</td>
        <td> User </td>
    </tr>
    <tr>
        <td>Description</td>
        <td> The puzzle has a counter that will keep track of the moves token during the game </td>
    </tr>
    <tr>
        <td>Data</td>
        <td> None </td>
    </tr>
    <tr>
        <td>Stimulus</td>
        <td> An action is taken by the user </td>
    </tr>
    <tr>
        <td>Response</td>
        <td> The counter move will be updated </td>
    </tr>
    <tr>
        <td>Comments</td>
        <td> The counter can't be a negative number </td>
    </tr>
</table>

[//]: # (Next best action)
<table>
    <tr>
        <th colspan="2"> Next best action  </th>
    </tr>
    <tr>
        <td>Actors</td>
        <td> User </td>
    </tr>
    <tr>
        <td>Description</td>
        <td> This functionality will simplify the puzzle to the user moving a determined block </td>
    </tr>
    <tr>
        <td>Data</td>
        <td> None </td>
    </tr>
    <tr>
        <td>Stimulus</td>
        <td> The "NEXT MOVE" button is pressed during the game </td>
    </tr>
    <tr>
        <td>Response</td>
        <td> An algorithm will process the state of the game and a block will be moved in order to facilitate the puzzle to the user </td>
    </tr>
    <tr>
        <td>Comments</td>
        <td> When always clicking the "NEXT MOVE" button the puzzle will be solved </td>
    </tr>
</table>

[//]: # (Undo action)
<table>
    <tr>
        <th colspan="2"> Undo action </th>
    </tr>
    <tr>
        <td>Actors</td>
        <td> User </td>
    </tr>
    <tr>
        <td>Description</td>
        <td> The game gives the possibility to undo the move just taken </td>
    </tr>
    <tr>
        <td>Data</td>
        <td> None </td>
    </tr>
    <tr>
        <td>Stimulus</td>
        <td> The "UNDO" button is pressed during the game </td>
    </tr>
    <tr>
        <td>Response</td>
        <td> The move just taken is undone by extracting the last item on the stack </td>
    </tr>
    <tr>
        <td>Comments</td>
        <td> When always clicking the "UNDO" button the puzzle will be reset to the beginning </td>
    </tr>
</table>

[//]: # (Reset setup)
<table>
    <tr>
        <th colspan="2"> Reset setup   </th>
    </tr>
    <tr>
        <td>Actors</td>
        <td>User </td>
    </tr>
    <tr>
        <td>Description</td>
        <td>The game restarts from the beginning</td>
    </tr>
    <tr>
        <td>Data</td>
        <td> None </td>
    </tr>
    <tr>
        <td>Stimulus</td>
        <td>The "RESET" button is pressed during the game </td>
    </tr>
    <tr>
        <td>Response</td>
        <td> The game will restart from the beginning </td>
    </tr>
    <tr>
        <td>Comments</td>
        <td> None </td>
    </tr>
    <tr>
</table>
