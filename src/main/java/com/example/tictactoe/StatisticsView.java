package com.example.tictactoe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import static com.example.tictactoe.UIUtils.*;

public class StatisticsView {

    private final Stage primaryStage;
    private final MenuController menuController;
    private static final int WINDOW_WIDTH = 550;
    private static final int WINDOW_HEIGHT = 550;

    public StatisticsView(Stage primaryStage, MenuController menuController) {
        this.primaryStage = primaryStage;
        this.menuController = menuController;
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

        Label pvaiTitle = createStyledLabel(" PvAI - Difficulty Stats:");
        pvaiTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        pvaiTitle.setTextFill(Color.STEELBLUE);

        VBox pvaiStatsBox = new VBox(10);
        pvaiStatsBox.setPadding(new Insets(8));
        pvaiStatsBox.setAlignment(Pos.TOP_CENTER);

        VBox card = new VBox(10);
        card.setPadding(new Insets(8));
        applyCardStyle(card);

        for (DifficultyLevel d : DifficultyLevel.values()) {
            Label label = createStyledLabel(
                    d.name() + ": Player wins " + stats.getPvaiWinsPlayer(d)
                            + ", AI wins " + stats.getPvaiWinsAI(d)
                            + ", Draws " + stats.getPvaiDraws(d)
            );
            card.getChildren().add(label);
        }

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

            card.getChildren().addAll(playerStartsLabel, aiStartsLabel);
        }

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
