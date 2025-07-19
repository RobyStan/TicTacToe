package com.example.tictactoe;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.util.Map;
import java.util.stream.Collectors;

import static com.example.tictactoe.UIUtils.*;
import static com.example.tictactoe.Statistics.*;

public class StatisticsView {

    private final Stage primaryStage;
    private final MenuController menuController;
    private static final int WINDOW_WIDTH = 550;
    private static final int WINDOW_HEIGHT = 550;

    public StatisticsView(Stage primaryStage, MenuController menuController) {
        this.primaryStage = primaryStage;
        this.menuController = menuController;
    }

    private String mapToString(Map<String, Integer> map) {
        return map.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(", ", "{", "}"));
    }

    public void showStatisticsPage() {
        Statistics stats = Statistics.getInstance();

        VBox statsRoot = new VBox(20);
        statsRoot.setAlignment(Pos.TOP_CENTER);
        statsRoot.setPadding(new Insets(30));
        applyBackground(statsRoot);

        Label title = createStyledLabel("TicTacToe Statistics");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.DARKSLATEBLUE);

        VBox generalBox = new VBox(10);
        applyCardStyle(generalBox);
        generalBox.getChildren().addAll(
                createStyledLabel("Total games played: " + stats.getTotalGames()),
                createStyledLabel("Player vs Player games: " + stats.getPvpGames()),
                createStyledLabel("Player vs AI games: " + stats.getPvaiGames()),
                createStyledLabel("PVP - Player 1 wins: " + stats.getPvpWinsPlayer1()),
                createStyledLabel("PVP - Player 2 wins: " + stats.getPvpWinsPlayer2()),
                createStyledLabel("PVP - Draws: " + stats.getPvpDraws())
        );

        Label pvaiTitle = createStyledLabel("PvAI - Difficulty Stats:");
        pvaiTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        pvaiTitle.setTextFill(Color.STEELBLUE);

        VBox pvaiStatsBox = new VBox(10);
        pvaiStatsBox.setPadding(new Insets(8));
        pvaiStatsBox.setAlignment(Pos.TOP_CENTER);

        VBox card = new VBox(5);
        card.setPadding(new Insets(8));
        applyCardStyle(card);

        Label playerWinsLabel = createStyledLabel("Player wins " + mapToString(convertDifficultyMapToStringKey(stats.getPvaiWinsPlayer())));
        Label aiWinsLabel = createStyledLabel("AI wins " + mapToString(convertDifficultyMapToStringKey(stats.getPvaiWinsAI())));
        Label drawsLabel = createStyledLabel("Draws " + mapToString(convertDifficultyMapToStringKey(stats.getPvaiDraws())));

        card.getChildren().addAll(playerWinsLabel, aiWinsLabel, drawsLabel);

        pvaiStatsBox.getChildren().add(card);

        Label starterStatsTitle = createStyledLabel("PvAI - Starter-Based Stats:");
        starterStatsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        starterStatsTitle.setTextFill(Color.STEELBLUE);

        VBox starterStatsBox = new VBox(10);
        starterStatsBox.setPadding(new Insets(10));
        starterStatsBox.setAlignment(Pos.TOP_CENTER);

        card = new VBox(10);
        card.setPadding(new Insets(8));
        applyCardStyle(card);

        VBox playerStartsStats = new VBox(2);
        playerStartsStats.getChildren().add(createStyledLabel("Player Starts"));
        playerStartsStats.getChildren().add(createStyledLabel("Player wins " + mapToString(stats.getPvaiWinsPlayerByStarterMap(Statistics.Starter.PLAYER))));
        playerStartsStats.getChildren().add(createStyledLabel("AI wins " + mapToString(stats.getPvaiWinsAIByStarterMap(Statistics.Starter.PLAYER))));
        playerStartsStats.getChildren().add(createStyledLabel("Draws " + mapToString(stats.getPvaiDrawsByStarterMap(Statistics.Starter.PLAYER))));

        VBox aiStartsStats = new VBox(2);
        aiStartsStats.getChildren().add(createStyledLabel("AI Starts"));
        aiStartsStats.getChildren().add(createStyledLabel("Player wins " + mapToString(stats.getPvaiWinsPlayerByStarterMap(Statistics.Starter.AI))));
        aiStartsStats.getChildren().add(createStyledLabel("AI wins " + mapToString(stats.getPvaiWinsAIByStarterMap(Statistics.Starter.AI))));
        aiStartsStats.getChildren().add(createStyledLabel("Draws " + mapToString(stats.getPvaiDrawsByStarterMap(Statistics.Starter.AI))));

        card.getChildren().addAll(playerStartsStats, aiStartsStats);

        starterStatsBox.getChildren().add(card);

        Button backBtn = createStyledButton("Back to Menu");
        backBtn.setOnAction(e -> menuController.showMenu());

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

        javafx.application.Platform.runLater(() -> scrollPane.setVvalue(0));
    }
}
