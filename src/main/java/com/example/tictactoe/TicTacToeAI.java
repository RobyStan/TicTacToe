package com.example.tictactoe;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import static com.example.tictactoe.UIUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToeAI {

    private boolean xTurn;
    private boolean gameOver = false;

    private final Button[][] board = new Button[3][3];
    private final VBox root = new VBox(10);
    private final HBox buttonsBox = new HBox(10);
    private final Random random = new Random();

    private final GameModeSwitcher switcher;
    private final DifficultyLevel difficulty;
    private int moveCount = 0;

    private final boolean playerStarts;

    public TicTacToeAI(GameModeSwitcher switcher, DifficultyLevel difficulty, boolean playerStarts) {
        this.switcher = switcher;
        this.difficulty = difficulty;
        this.playerStarts = playerStarts;
        this.xTurn = true;
    }

    public Parent createContent() {
        root.setPrefSize(550, 550);
        root.setAlignment(Pos.CENTER);

        UIUtils.applyBackground(root);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button cell = createCell();
                board[row][col] = cell;
                grid.add(cell, col, row);
            }
        }

        buttonsBox.setAlignment(Pos.CENTER);

        Button playAgainBtn = createStyledButton("Play Again");
        Button changeModeBtn = createStyledButton("Change Game Mode");

        playAgainBtn.setOnAction(e -> {
            resetBoard();
            buttonsBox.setVisible(false);
        });

        changeModeBtn.setOnAction(e -> {
            if (switcher != null) {
                switcher.showMenu();
            }
        });

        buttonsBox.getChildren().addAll(playAgainBtn, changeModeBtn);
        buttonsBox.setVisible(false);

        root.getChildren().addAll(grid, buttonsBox);

        if (!playerStarts) {
            xTurn = false;
            aiMove();
            xTurn = true;
        }
        return root;
    }

    private Statistics.Starter getStarter() {
        return playerStarts ? Statistics.Starter.PLAYER : Statistics.Starter.AI;
    }

    private Button createCell() {
        Button btn = new Button();
        btn.setFont(Font.font(36));
        btn.setPrefSize(100, 100);

        btn.setOnAction(e -> {
            if (!btn.getText().isEmpty() || !xTurn || gameOver) return;

            btn.setText("X");

            if (checkWin()) {
                updateStatistics("Player (X)");
                showWinDialog("Player (X)");
                endGame();
                return;
            } else if (isBoardFull()) {
                updateStatistics("Draw!");
                showWinDialog("Draw!");
                endGame();
                return;
            }

            xTurn = false;
            aiMove();

            if (checkWin()) {
                updateStatistics("AI (O)");
                showWinDialog("AI (O)");
                endGame();
            } else if (isBoardFull()) {
                showWinDialog("Draw!");
                endGame();
            } else {
                xTurn = true;
            }
        });

        return btn;
    }

    private void aiMove() {
        switch (difficulty) {
            case EASY -> makeRandomMove();
            case MEDIUM -> {
                if (moveCount % 2 == 0) {
                    makeRandomMove();
                } else {
                    makeBestMove();
                }
                moveCount++;
            }
            case HARD -> makeBestMove();
        }
    }

    private void makeRandomMove() {
        List<Button> emptyCells = new ArrayList<>();
        for (Button[] row : board)
            for (Button btn : row)
                if (btn.getText().isEmpty())
                    emptyCells.add(btn);

        if (!emptyCells.isEmpty()) {
            Button move = emptyCells.get(random.nextInt(emptyCells.size()));
            move.setText("O");
        }
    }

    private void makeBestMove() {
        int bestScore = Integer.MIN_VALUE;
        Button bestMove = null;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button btn = board[row][col];
                if (btn.getText().isEmpty()) {
                    btn.setText("O");
                    int score = minimax(false);
                    btn.setText("");
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = btn;
                    }
                }
            }
        }

        if (bestMove != null) {
            bestMove.setText("O");
        }
    }

    private int minimax(boolean isMaximizing) {
        if (checkWinFor("O")) return 1;
        if (checkWinFor("X")) return -1;
        if (isBoardFull()) return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Button[] row : board) {
            for (Button btn : row) {
                if (btn.getText().isEmpty()) {
                    btn.setText(isMaximizing ? "O" : "X");
                    int score = minimax(!isMaximizing);
                    btn.setText("");
                    bestScore = isMaximizing
                            ? Math.max(score, bestScore)
                            : Math.min(score, bestScore);
                }
            }
        }

        return bestScore;
    }

    private boolean checkWin() {
        return checkWinFor("X") || checkWinFor("O");
    }

    private boolean checkWinFor(String symbol) {
        for (int i = 0; i < 3; i++) {
            if (symbol.equals(board[i][0].getText()) &&
                    symbol.equals(board[i][1].getText()) &&
                    symbol.equals(board[i][2].getText()))
                return true;

            if (symbol.equals(board[0][i].getText()) &&
                    symbol.equals(board[1][i].getText()) &&
                    symbol.equals(board[2][i].getText()))
                return true;
        }

        return (symbol.equals(board[0][0].getText()) &&
                symbol.equals(board[1][1].getText()) &&
                symbol.equals(board[2][2].getText())) ||
                (symbol.equals(board[0][2].getText()) &&
                        symbol.equals(board[1][1].getText()) &&
                        symbol.equals(board[2][0].getText()));
    }

    private boolean isBoardFull() {
        for (Button[] row : board)
            for (Button btn : row)
                if (btn.getText().isEmpty())
                    return false;
        return true;
    }

    private void showWinDialog(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game over");
        alert.setHeaderText(null);
        alert.setContentText(winner.equals("Draw!") ? winner : "Winner: " + winner);
        alert.showAndWait();
    }

    private void updateStatistics(String winner) {
        Statistics stats = Statistics.getInstance();
        Statistics.Starter starter = getStarter();

        switch (winner) {
            case "Draw!" -> stats.recordPVAIGame(difficulty, starter, null);
            case "Player (X)" -> stats.recordPVAIGame(difficulty, starter, "player");
            case "AI (O)" -> stats.recordPVAIGame(difficulty, starter, "ai");
        }
    }

    private void endGame() {
        gameOver = true;
        showEndButtons();
    }

    private void showEndButtons() {
        buttonsBox.setVisible(true);
    }

    private void resetBoard() {
        for (Button[] row : board)
            for (Button btn : row)
                btn.setText("");
        gameOver = false;
        moveCount = 0;
        buttonsBox.setVisible(false);

        xTurn = true;
        if (!playerStarts) {
            xTurn = false;
            aiMove();
            xTurn = true;
        }
    }

    public interface GameModeSwitcher {
        void showMenu();
    }
}
