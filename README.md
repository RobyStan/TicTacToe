# TicTacToe

A simple TicTacToe game with a graphical user interface built using JavaFX. The game supports two modes: Player vs Player and Player vs AI with a basic AI opponent.

## Features

- Player vs Player mode
- Player vs AI mode with 3 difficulty levels: Easy, Medium, Hard 
- End-of-game alerts showing the winner or a draw
- Game statistics for PvP and PvAI matches
- Clean layout and easy-to-use interface
- Save and load game statistics from a JSON file

---
## How to play
- When the application starts, a menu appears to select the game mode:
    - **Player vs Player**: Two players alternate turns on the same computer.
    - **Player vs AI**: Play against the computer.
- Click on any empty cell in the 3x3 grid to place your mark (X or O).
- The game alerts when someone wins or the board is full (draw).
- After a game finishes, use the **Play Again** button to restart the game mode, or **Change Game Mode** to return to the menu.
- Access the **Statistics** page from the main menu to view game stats.

---


## ToDo List

The following features are planned or in development:

- [x] **AI Difficulty Levels**  
  Implement three AI difficulty modes:
    - Easy: Random moves
    - Medium: Mix of random moves and Minimax algorithm
    - Hard: Use the Minimax algorithm for optimal play

- [x] **Current Turn Indicator**  
  Display the current player's turn:
    - PvP: Show `"Player 1 (X)'s turn"` or `"Player 2 (O)'s turn"`

- [x] **Who Starts First (PvAI)**  
  Add an option before the game starts to let the user choose who makes the first move (Player or AI)

- [x] **Custom Player Names (PvP)**  
  Allow players to enter their names before starting the Player vs Player mode, and show their names during gameplay instead of generic labels

- [x] **Statistics Page**  
  - Display general and detailed game stats (PvP & PvAI), including per-difficulty results 
  - Added saving and loading of game statistics using a JSON file

- [x] **Improved UI**  
  Apply modern styling using JavaFX:
  - Consistent padding, spacing, colors
  - Organized sections using VBox and ScrollPane
  - Stylized buttons and labels
  - Structured layout with VBox and ScrollPane

- [ ] **+ Maybe more to come**  
  Additional features may be added based on  experimentation.