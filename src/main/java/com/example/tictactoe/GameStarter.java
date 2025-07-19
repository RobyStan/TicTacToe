package com.example.tictactoe;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameStarter {

    private final Stage primaryStage;
    private final Main mainApp;
    private static final int WINDOW_WIDTH = 550;
    private static final int WINDOW_HEIGHT = 550;

    public GameStarter(Stage primaryStage, Main mainApp) {
        this.primaryStage = primaryStage;
        this.mainApp = mainApp;
    }

    public void startPVPGame(String player1Name, String player2Name) {
        TicTacToe game = new TicTacToe(mainApp, player1Name, player2Name);
        Scene scene = new Scene(game.createContent(), WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe - Player vs Player");
    }

    public void startPVAGame(DifficultyLevel difficulty, boolean playerStarts) {
        TicTacToeAI game = new TicTacToeAI(mainApp, difficulty, playerStarts);
        Scene scene = new Scene(game.createContent(), WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe - Player vs AI - " + difficulty + (playerStarts ? " (Player starts)" : " (AI starts)"));
    }
}
