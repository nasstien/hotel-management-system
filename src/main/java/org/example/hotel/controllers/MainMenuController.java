package org.example.hotel.controllers;

import javafx.scene.layout.VBox;
import org.example.hotel.utils.HotkeyHandler;
import org.example.hotel.utils.gui.GUI;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ToolBar;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;

import static org.example.hotel.Application.user;

public class MainMenuController {
    @FXML private VBox root;
    @FXML private GridPane gridPane;

    @FXML
    public void initialize() throws Exception {
        initializeHotkeys();

        Parent menu = GUI.loadPage(STR."toolbox/\{user.getRole().name().toLowerCase()}.fxml");
        Parent homePage = GUI.loadPage("homepage.fxml");

        ToolBar toolBar = new ToolBar();
        toolBar.getItems().add(menu);
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setPrefSize(221, 675);
        toolBar.setPadding(new Insets(120, 0, 0, 10));

        gridPane.addRow(0, toolBar, homePage);
    }

    private void initializeHotkeys() {
        HotkeyHandler.handleExitPressed(root);
    }
}
