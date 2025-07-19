package com.example.tictactoe;

import javafx.scene.control.*;

public class Minmax {

    public static Button getBestMove(Button[][] board) {
        int bestScore = Integer.MIN_VALUE;
        Button bestMove = null;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button btn = board[row][col];
                if (btn.getText().isEmpty()) {
                    btn.setText("O");
                    int score = minimax(board, false);
                    btn.setText("");
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = btn;
                    }
                }
            }
        }

        return bestMove;
    }

    private static int minimax(Button[][] board, boolean isMaximizing) {
        if (checkWinFor(board, "O")) return 1;
        if (checkWinFor(board, "X")) return -1;
        if (isBoardFull(board)) return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Button[] row : board) {
            for (Button btn : row) {
                if (btn.getText().isEmpty()) {
                    btn.setText(isMaximizing ? "O" : "X");
                    int score = minimax(board, !isMaximizing);
                    btn.setText("");
                    bestScore = isMaximizing
                            ? Math.max(score, bestScore)
                            : Math.min(score, bestScore);
                }
            }
        }

        return bestScore;
    }

    private static boolean checkWinFor(Button[][] board, String symbol) {
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

    private static boolean isBoardFull(Button[][] board) {
        for (Button[] row : board)
            for (Button btn : row)
                if (btn.getText().isEmpty())
                    return false;
        return true;
    }
}
