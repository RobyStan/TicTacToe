package com.example.tictactoe;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

public class UIUtils {

    private static final String BACKGROUND_STYLE = "-fx-background-color: #66D0E3;";

    public static Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-background-color: #4285F4;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10 20;" +
                        "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-background-color: #3367D6;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10 20;" +
                        "-fx-cursor: hand;"
        ));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-background-color: #4285F4;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10 20;" +
                        "-fx-cursor: hand;"
        ));

        return button;
    }

    public static Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial",  FontWeight.BOLD,14));
        label.setTextFill(Color.DIMGRAY);
        return label;
    }

    public static void applyBackground(Region root) {
        root.setStyle(BACKGROUND_STYLE);
    }

    public static void applyCardStyle(Region box) {
        box.setStyle("-fx-background-color: #5FDEA5; -fx-padding: 15; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
    }
}