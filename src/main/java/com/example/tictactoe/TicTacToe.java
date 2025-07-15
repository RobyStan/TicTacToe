package com.example.tictactoe;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.text.Font;

public class TicTacToe {
    private boolean xTurn = true;
    private boolean gameOver = false;
    private final Button[][] board = new Button[3][3];
    private final VBox root = new VBox(10);
    private final HBox buttonsBox = new HBox(10);
    private final GameModeSwitcher switcher;

    public TicTacToe(GameModeSwitcher switcher) {
        this.switcher = switcher;
    }

    public Parent createContent() {
        root.setPrefSize(500, 500);
        root.setAlignment(Pos.CENTER);

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
        Button playAgainBtn = new Button("Play Again");
        Button changeModeBtn = new Button("Change Game Mode");

        playAgainBtn.setOnAction(e -> {
            resetBoard();
            buttonsBox.getChildren().clear();
            buttonsBox.getChildren().addAll(playAgainBtn, changeModeBtn);
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
                showWinDialog(xTurn ? "X" : "O");
                gameOver = true;
                showEndButtons();
            } else if (isBoardFull()) {
                showWinDialog("Draw!");
                gameOver = true;
                showEndButtons();
            } else {
                xTurn = !xTurn;
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

    private void showWinDialog(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game over");
        alert.setHeaderText(null);
        if (winner.equals("Draw!")) {
            alert.setContentText(winner);
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
        buttonsBox.setVisible(false);
    }

    public interface GameModeSwitcher {
        void showMenu();
    }
}
