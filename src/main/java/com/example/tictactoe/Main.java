package com.example.tictactoe;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.application.*;
import static com.example.tictactoe.UIUtils.*;

public class Main extends Application implements TicTacToe.GameModeSwitcher, TicTacToeAI.GameModeSwitcher {

    private Stage primaryStage;
    private DifficultyLevel selectedDifficulty;
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showMenu();
    }


    public void showMenu() {
        Button btnPVP = createStyledButton("Player vs Player");
        Button btnPVE = createStyledButton("Player vs AI");
        Button btnStats = createStyledButton("Statistics");

        btnPVP.setOnAction(e -> showPlayerNamesInput());
        btnPVE.setOnAction(e -> showDifficultyMenu());
        btnStats.setOnAction(e -> showStatisticsPage());

        VBox menu = new VBox(10, btnPVP, btnPVE, btnStats);
        menu.setPrefSize(500, 500);
        menu.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Scene scene = new Scene(menu, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe - Choose Mode");
        primaryStage.show();
    }

    private void showPlayerNamesInput() {
        Label label1 = createStyledLabel("Player 1 Name (optional):");
        TextField tfPlayer1 = new TextField();
        tfPlayer1.setPromptText("Player 1");

        Label label2 =  createStyledLabel("Player 2 Name (optional):");
        TextField tfPlayer2 = new TextField();
        tfPlayer2.setPromptText("Player 2");

        Button startBtn = createStyledButton("Start PvP Game");

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
        difficultyMenu.setPrefSize(500, 500);
        difficultyMenu.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Scene scene = new Scene(difficultyMenu);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe - Choose Difficulty");
    }

    private void showStarterChoiceMenu() {
        Button playerFirstBtn = createStyledButton("Player Starts");
        Button aiFirstBtn = createStyledButton("AI Starts");

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

    private void showStatisticsPage() {
        Statistics stats = Statistics.getInstance();

        VBox statsRoot = new VBox(20);
        statsRoot.setAlignment(Pos.TOP_CENTER);
        statsRoot.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 30;");

        Label title =  createStyledLabel("TicTacToe Statistics");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.DARKSLATEBLUE);

        VBox generalBox = new VBox(8);
        generalBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 15; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        generalBox.getChildren().addAll(
                createStyledLabel("Total games played: " + stats.getTotalGames()),
                createStyledLabel("Player vs Player games: " + stats.getPvpGames()),
                createStyledLabel("Player vs AI games: " + stats.getPvaiGames()),
                createStyledLabel("PVP - Player 1 wins: " + stats.getPvpWinsPlayer1()),
                createStyledLabel("PVP - Player 2 wins: " + stats.getPvpWinsPlayer2()),
                createStyledLabel("PVP - Draws: " + stats.getPvpDraws())
        );

        Label pvaiTitle =  createStyledLabel(" PvAI - Difficulty Stats:");
        pvaiTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        pvaiTitle.setTextFill(Color.STEELBLUE);

        VBox pvaiStatsBox = new VBox(6);
        pvaiStatsBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        for (DifficultyLevel d : DifficultyLevel.values()) {
            Label label = createStyledLabel(
                    d.name() + ": Player wins " + stats.getPvaiWinsPlayer(d)
                            + ", AI wins " + stats.getPvaiWinsAI(d)
                            + ", Draws " + stats.getPvaiDraws(d));
            pvaiStatsBox.getChildren().add(label);
        }

        Label starterStatsTitle = createStyledLabel("PvAI - Starter-Based Stats:");
        starterStatsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        starterStatsTitle.setTextFill(Color.STEELBLUE);

        VBox starterStatsBox = new VBox(6);
        starterStatsBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        for (DifficultyLevel d : DifficultyLevel.values()) {
            Label playerStartsLabel = createStyledLabel(
                    d.name() + " (Player starts): Player wins " +
                            stats.getPvaiWinsPlayerByStarter(d, Statistics.Starter.PLAYER) + ", AI wins " +
                            stats.getPvaiWinsAIByStarter(d, Statistics.Starter.PLAYER) + ", Draws " +
                            stats.getPvaiDrawsByStarter(d, Statistics.Starter.PLAYER));

            Label aiStartsLabel = createStyledLabel(
                    d.name() + " (AI starts): Player wins " +
                            stats.getPvaiWinsPlayerByStarter(d, Statistics.Starter.AI) + ", AI wins " +
                            stats.getPvaiWinsAIByStarter(d, Statistics.Starter.AI) + ", Draws " +
                            stats.getPvaiDrawsByStarter(d, Statistics.Starter.AI));

            starterStatsBox.getChildren().addAll(playerStartsLabel, aiStartsLabel);
        }

        Button backBtn = createStyledButton("Back to Menu");
        backBtn.setOnAction(e -> showMenu());

        statsRoot.getChildren().addAll(
                title,
                generalBox,
                pvaiTitle, pvaiStatsBox,
                starterStatsTitle, starterStatsBox,
                backBtn
        );

        ScrollPane scrollPane = new ScrollPane(statsRoot);
        scrollPane.setFitToWidth(true);
        scrollPane.setVvalue(0);

        Scene statsScene = new Scene(scrollPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(statsScene);
        primaryStage.setTitle("TicTacToe Statistics");

        Platform.runLater(() -> scrollPane.setVvalue(0));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
