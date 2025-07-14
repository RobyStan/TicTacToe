# TicTacToe

A simple TicTacToe game with a graphical user interface built using JavaFX. The game supports two modes: Player vs Player and Player vs AI with a basic AI opponent.

## Features

- Player vs Player mode
- Player vs AI mode 
- End-of-game alerts showing the winner or a draw

---
## How to play
- When the application starts, a menu appears to select the game mode:
    - **Player vs Player**: Two players alternate turns on the same computer.
    - **Player vs AI**: Play against the computer.
- Click on any empty cell in the 3x3 grid to place your mark (X or O).
- The game alerts when someone wins or the board is full (draw).
- After a game finishes, use the Play Again button to restart the same mode, or Change Game Mode to return to the menu.

---


## ToDo List

The following features are planned or in development:

- [ ] **AI Difficulty Levels**  
  Implement three AI difficulty modes:
    - Easy: Random moves
    - Medium: Block obvious winning moves
    - Hard: Use the Minimax algorithm for optimal play

- [ ] **Current Turn Indicator**  
  Display the current player's turn in both game modes:
    - PvP: Show `"Player 1 (X)'s turn"` or `"Player 2 (O)'s turn"`
    - PvAI: Show `"Player (X)'s turn"` or `"AI (O)'s turn"` dynamically

- [ ] **Who Starts First (PvAI)**  
  Add an option before the game starts to let the user choose who makes the first move (Player or AI)

- [ ] **Custom Player Names (PvP)**  
  Allow players to enter their names before starting the Player vs Player mode, and show their names during gameplay instead of generic labels

- [ ] **+ Maybe more to come**  
  Additional features may be added based on  experimentation.