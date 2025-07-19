package com.example.tictactoe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import static com.example.tictactoe.UIUtils.*;

public class MenuController {

    private final Stage primaryStage;
    private final Main mainApp; // Pentru callback-uri

    private DifficultyLevel selectedDifficulty;
    private static final int WINDOW_WIDTH = 550;
    private static final int WINDOW_HEIGHT = 550;

    public MenuController(Stage primaryStage, Main mainApp) {
        this.primaryStage = primaryStage;
        this.mainApp = mainApp;
    }

    public void showMenu() {
        Button btnPVP = createStyledButton("Player vs Player");
        Button btnPVE = createStyledButton("Player vs AI");
        Button btnStats = createStyledButton("Statistics");

        btnPVP.setOnAction(e -> showPlayerNamesInput());
        btnPVE.setOnAction(e -> showDifficultyMenu());
        btnStats.setOnAction(e -> mainApp.switchToStatistics());

        VBox menu = new VBox(10, btnPVP, btnPVE, btnStats);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(20));
        applyBackground(menu);

        Scene scene = new Scene(menu, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe - Choose Mode");
        primaryStage.show();
    }

    private void showPlayerNamesInput() {
        Label label1 = createStyledLabel("Player 1 Name (optional):");
        TextField tfPlayer1 = new TextField();
        tfPlayer1.setPromptText("Player 1");

        Label label2 = createStyledLabel("Player 2 Name (optional):");
        TextField tfPlayer2 = new TextField();
        tfPlayer2.setPromptText("Player 2");

        Button startBtn = createStyledButton("Start PvP Game");

        startBtn.setOnAction(e -> {
            String p1Name = tfPlayer1.getText().trim();
            String p2Name = tfPlayer2.getText().trim();
            mainApp.switchToPVP(p1Name.isEmpty() ? null : p1Name, p2Name.isEmpty() ? null : p2Name);
        });

        VBox inputBox = new VBox(10, label1, tfPlayer1, label2, tfPlayer2, startBtn);
        inputBox.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(20));
        applyBackground(inputBox);

        Scene scene = new Scene(inputBox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Enter Player Names");
    }

    private void showDifficultyMenu() {
        Button easyBtn = createStyledButton("Easy");
        Button mediumBtn = createStyledButton("Medium");
        Button hardBtn = createStyledButton("Hard");

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
        difficultyMenu.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        difficultyMenu.setAlignment(Pos.CENTER);
        difficultyMenu.setPadding(new Insets(20));
        applyBackground(difficultyMenu);

        Scene scene = new Scene(difficultyMenu);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe - Choose Difficulty");
    }

    private void showStarterChoiceMenu() {
        Button playerFirstBtn = createStyledButton("Player Starts");
        Button aiFirstBtn = createStyledButton("AI Starts");

        playerFirstBtn.setOnAction(e -> mainApp.switchToPVE(selectedDifficulty, true));
        aiFirstBtn.setOnAction(e -> mainApp.switchToPVE(selectedDifficulty, false));

        VBox starterMenu = new VBox(10, playerFirstBtn, aiFirstBtn);
        starterMenu.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        starterMenu.setAlignment(Pos.CENTER);
        starterMenu.setPadding(new Insets(20));
        applyBackground(starterMenu);

        Scene scene = new Scene(starterMenu);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe - Who Starts?");
    }
}
