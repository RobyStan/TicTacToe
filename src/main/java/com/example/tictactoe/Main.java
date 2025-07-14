package com.example.tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application implements TicTacToe.GameModeSwitcher, TicTacToeAI.GameModeSwitcher {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showMenu();
    }

    public void showMenu() {
        Button btnPVP = new Button("Player vs Player");
        Button btnPVE = new Button("Player vs AI");

        btnPVP.setOnAction(e -> startPVPGame());
        btnPVE.setOnAction(e -> startPVAGame());

        VBox menu = new VBox(10, btnPVP, btnPVE);
        menu.setPrefSize(300, 200);
        menu.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Scene scene = new Scene(menu);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe - Choose Mode");
        primaryStage.show();
    }

    private void startPVPGame() {
        TicTacToe game = new TicTacToe(this);
        Scene scene = new Scene(game.createContent());
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe - Player vs Player");
    }

    private void startPVAGame() {
        TicTacToeAI game = new TicTacToeAI(this);
        Scene scene = new Scene(game.createContent());
        primaryStage.setScene(scene);
        primaryStage.setTitle("TicTacToe - Player vs AI");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
