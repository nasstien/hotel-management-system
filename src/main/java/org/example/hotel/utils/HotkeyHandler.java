package org.example.hotel.utils;

import javafx.scene.input.KeyEvent;
import org.example.hotel.utils.gui.Message;

import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;

import static org.example.hotel.Application.history;

public class HotkeyHandler {
    public static void handleEnterPressed(VBox root, Runnable action) {
        root.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                action.run();
            }
        });
    }

    public static void handleBackPressed(VBox root) {
        root.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.B && !history.isEmpty()) {
                history.pop();
            }
        });
    }

    public static void handleDeleteAccountPressed(VBox root, Runnable action) {
        root.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.D) {
                action.run();
            }
        });
    }

    public static void handleExitPressed(VBox root) {
        root.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.Q) {
                Message.displayConfirmDialog("Are you sure you want to exit?", Platform::exit);
            }
        });
    }
}
