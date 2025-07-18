package com.example.tictactoe;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UIUtils {

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

        // Hover effect
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
        label.setFont(Font.font("Arial", 14));
        label.setTextFill(Color.DIMGRAY);
        return label;
    }
}