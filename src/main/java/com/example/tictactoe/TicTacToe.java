package com.example.tictactoe;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import static com.example.tictactoe.UIUtils.*;

public class TicTacToe {
    private boolean xTurn = true;
    private boolean gameOver = false;
    private final Button[][] board = new Button[3][3];
    private final VBox root = new VBox(10);
    private final HBox buttonsBox = new HBox(10);
    private final GameModeSwitcher switcher;
    private final Label turnLabel = createStyledLabel("");

    private final String player1Name;
    private final String player2Name;

    public TicTacToe(GameModeSwitcher switcher) {
        this(switcher, "Player 1", "Player 2");
    }

    public TicTacToe(GameModeSwitcher switcher, String player1Name, String player2Name) {
        this.switcher = switcher;
        this.player1Name = (player1Name == null || player1Name.isEmpty()) ? "Player 1" : player1Name;
        this.player2Name = (player2Name == null || player2Name.isEmpty()) ? "Player 2" : player2Name;
    }

    public Parent createContent() {
        root.setPrefSize(550, 550);
        root.setAlignment(Pos.CENTER);

        UIUtils.applyBackground(root);

        turnLabel.setText(player1Name + " (X)'s turn");
        root.getChildren().add(turnLabel);

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

        return root;
    }

    private Button createCell() {
        Button btn = new Button();
        btn.setFont(Font.font(36));
        btn.setPrefSize(100, 100);

        btn.setOnAction(e -> {
            if (!btn.getText().isEmpty() || gameOver) return;

            btn.setText(xTurn ? "X" : "O");

            if (checkWin()) {
                String winnerName = xTurn ? player1Name : player2Name;
                updateStatistics(winnerName);
                showWinDialog(winnerName);
                gameOver = true;
                showEndButtons();
            } else if (isBoardFull()) {
                updateStatistics("Draw!");
                showWinDialog("Draw!");
                gameOver = true;
                showEndButtons();
            } else {
                xTurn = !xTurn;
                turnLabel.setText(xTurn ? player1Name + " (X)'s turn" : player2Name + " (O)'s turn");
            }
        });

        return btn;
    }

    private void showEndButtons() {
        buttonsBox.setVisible(true);
    }

    private boolean isBoardFull() {
        for (Button[] row : board)
            for (Button btn : row)
                if (btn.getText().isEmpty()) return false;
        return true;
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].getText().isEmpty() &&
                    board[i][0].getText().equals(board[i][1].getText()) &&
                    board[i][1].getText().equals(board[i][2].getText()))
                return true;
        }
        for (int i = 0; i < 3; i++) {
            if (!board[0][i].getText().isEmpty() &&
                    board[0][i].getText().equals(board[1][i].getText()) &&
                    board[1][i].getText().equals(board[2][i].getText()))
                return true;
        }
        if (!board[0][0].getText().isEmpty() &&
                board[0][0].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][2].getText()))
            return true;

        return !board[0][2].getText().isEmpty() &&
                board[0][2].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][0].getText());
    }

    private void updateStatistics(String winner) {
        Statistics stats = Statistics.getInstance();
        if (winner.equals("Draw!")) {
            stats.recordPVPGame(null);
        } else if (winner.equals(player1Name)) {
            stats.recordPVPGame("player1");
        } else if (winner.equals(player2Name)) {
            stats.recordPVPGame("player2");
        }
    }

    private void showWinDialog(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game over");
        alert.setHeaderText(null);
        if (winner.equals("Draw!")) {
            alert.setContentText("It's a draw!");
        } else {
            alert.setContentText("Winner: " + winner);
        }
        alert.showAndWait();
    }

    private void resetBoard() {
        for (Button[] row : board)
            for (Button btn : row)
                btn.setText("");
        xTurn = true;
        gameOver = false;
        turnLabel.setText(player1Name + " (X)'s turn");
        buttonsBox.setVisible(false);
    }

    public interface GameModeSwitcher {
        void showMenu();
    }
}
