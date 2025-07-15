package com.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application implements TicTacToe.GameModeSwitcher, TicTacToeAI.GameModeSwitcher {

    private Stage primaryStage;
    private DifficultyLevel selectedDifficulty;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showMenu();
    }

    public void showMenu() {
        Button btnPVP = new Button("Player vs Player");
        Button btnPVE = new Button("Player vs AI");

        btnPVP.setOnAction(e -> showPlayerNamesInput());
        btnPVE.setOnAction(e -> showDifficultyMenu());

        VBox menu = new VBox(10, btnPVP, btnPVE);
        menu.setPrefSize(500, 500);
        menu.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Scene scene = new Scene(menu);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe - Choose Mode");
        primaryStage.show();
    }

    private void showPlayerNamesInput() {
        Label label1 = new Label("Player 1 Name (optional):");
        TextField tfPlayer1 = new TextField();
        tfPlayer1.setPromptText("Player 1");

        Label label2 = new Label("Player 2 Name (optional):");
        TextField tfPlayer2 = new TextField();
        tfPlayer2.setPromptText("Player 2");

        Button startBtn = new Button("Start PvP Game");

        startBtn.setOnAction(e -> {
            String p1Name = tfPlayer1.getText().trim();
            String p2Name = tfPlayer2.getText().trim();

            startPVPGame(p1Name.isEmpty() ? null : p1Name, p2Name.isEmpty() ? null : p2Name);
        });

        VBox inputBox = new VBox(10, label1, tfPlayer1, label2, tfPlayer2, startBtn);
        inputBox.setPrefSize(500, 500);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(inputBox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Enter Player Names");
    }

    private void showDifficultyMenu() {
        Button easyBtn = new Button("Easy");
        Button mediumBtn = new Button("Medium");
        Button hardBtn = new Button("Hard");

        easyBtn.setOnAction(e -> {
            selectedDifficulty = DifficultyLevel.EASY;
            showStarterChoiceMenu();
        });
        mediumBtn.setOnAction(e -> {
            selectedDifficulty = DifficultyLevel.MEDIUM;
            showStarterChoiceMenu();
        });
        hardBtn.setOnAction(e -> {
            selectedDifficulty = DifficultyLevel.HARD;
            showStarterChoiceMenu();
        });

        VBox difficultyMenu = new VBox(10, easyBtn, mediumBtn, hardBtn);
        difficultyMenu.setPrefSize(500, 500);
        difficultyMenu.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Scene scene = new Scene(difficultyMenu);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe - Choose Difficulty");
    }

    private void showStarterChoiceMenu() {
        Button playerFirstBtn = new Button("Player Starts");
        Button aiFirstBtn = new Button("AI Starts");

        playerFirstBtn.setOnAction(e -> startPVAGame(selectedDifficulty, true));
        aiFirstBtn.setOnAction(e -> startPVAGame(selectedDifficulty, false));

        VBox starterMenu = new VBox(10, playerFirstBtn, aiFirstBtn);
        starterMenu.setPrefSize(500, 500);
        starterMenu.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Scene scene = new Scene(starterMenu);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe - Who Starts?");
    }

    private void startPVPGame(String player1Name, String player2Name) {
        TicTacToe game = new TicTacToe(this, player1Name, player2Name);
        Scene scene = new Scene(game.createContent());
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe - Player vs Player");
    }

    private void startPVAGame(DifficultyLevel difficulty, boolean playerStarts) {
        TicTacToeAI game = new TicTacToeAI(this, difficulty, playerStarts);
        Scene scene = new Scene(game.createContent());
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe - Player vs AI - " + difficulty + (playerStarts ? " (Player starts)" : " (AI starts)"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
