package org.example.hotel.utils.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Message {
    public static void displaySuccessDialog(String message) {
        Alert alert = GUI.createAlert(Alert.AlertType.INFORMATION, "success.png", "Success", message);
        alert.showAndWait();
    }

    public static void displayErrorDialog(String message) {
        Alert alert = GUI.createAlert(Alert.AlertType.ERROR, "error.png", "Error", message);
        alert.showAndWait();
    }

    public static void displayInfoDialog(String message) {
        Alert alert = GUI.createAlert(Alert.AlertType.INFORMATION, "info.png", "Info", message);
        alert.showAndWait();
    }

    public static void displayConfirmDialog(String message, Runnable handleYesClick) {
        Alert alert = GUI.createAlert(Alert.AlertType.CONFIRMATION, "warning.png", "Confirmation", message);

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType cancelButton = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(yesButton, cancelButton);
        alert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                handleYesClick.run();
            }
        });
    }
}
