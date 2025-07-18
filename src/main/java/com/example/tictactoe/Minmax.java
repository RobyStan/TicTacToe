package com.example.tictactoe;

import javafx.scene.control.*;

public class Minmax {
    public static Button findBestMove(Button[][] board, String aiSymbol, String playerSymbol) {
        int bestScore = Integer.MIN_VALUE;
        Button bestMove = null;

        for (Button[] row : board) {
            for (Button btn : row) {
                if (btn.getText().isEmpty()) {
                    btn.setText(aiSymbol);
                    int score = minimax(board, false, aiSymbol, playerSymbol);
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

    private static int minimax(Button[][] board, boolean isMax, String ai, String player) {
        if (checkWin(board, ai)) return 1;
        if (checkWin(board, player)) return -1;
        if (isFull(board)) return 0;

        int best = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Button[] row : board) {
            for (Button btn : row) {
                if (btn.getText().isEmpty()) {
                    btn.setText(isMax ? ai : player);
                    int score = minimax(board, !isMax, ai, player);
                    btn.setText("");
                    best = isMax ? Math.max(score, best) : Math.min(score, best);
                }
            }
        }

        return best;
    }

    private static boolean checkWin(Button[][] board, String symbol) {
        for (int i = 0; i < 3; i++) {
            if (symbol.equals(board[i][0].getText()) &&
                    symbol.equals(board[i][1].getText()) &&
                    symbol.equals(board[i][2].getText())) return true;
            if (symbol.equals(board[0][i].getText()) &&
                    symbol.equals(board[1][i].getText()) &&
                    symbol.equals(board[2][i].getText())) return true;
        }

        return (symbol.equals(board[0][0].getText()) &&
                symbol.equals(board[1][1].getText()) &&
                symbol.equals(board[2][2].getText())) ||
                (symbol.equals(board[0][2].getText()) &&
                        symbol.equals(board[1][1].getText()) &&
                        symbol.equals(board[2][0].getText()));
    }

    private static boolean isFull(Button[][] board) {
        for (Button[] row : board)
            for (Button btn : row)
                if (btn.getText().isEmpty())
                    return false;
        return true;
    }
}
