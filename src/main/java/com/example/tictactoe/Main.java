package com.example.tictactoe;

import javafx.application.*;
import javafx.stage.*;

public class Main extends Application implements TicTacToe.GameModeSwitcher, TicTacToeAI.GameModeSwitcher {

    private Stage primaryStage;
    private MenuController menuController;
    private GameStarter gameStarter;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        menuController = new MenuController(primaryStage, this);
        gameStarter = new GameStarter(primaryStage, this);
        menuController.showMenu();
    }

    public void switchToPVP(String player1, String player2) {
        gameStarter.startPVPGame(player1, player2);
    }

    public void switchToPVE(DifficultyLevel difficulty, boolean playerStarts) {
        gameStarter.startPVAGame(difficulty, playerStarts);
    }

    public void switchToStatistics() {
        StatisticsView statsView = new StatisticsView(primaryStage, menuController);
        statsView.showStatisticsPage();
    }

    @Override
    public void showMenu() {
        menuController.showMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
