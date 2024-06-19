package org.example.hotel.controllers;

import org.example.hotel.interfaces.EntityController;
import org.example.hotel.utils.gui.GUI;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.util.List;

import static org.example.hotel.Application.history;
import static org.example.hotel.Application.primaryStage;

public class ControlPanelController implements EntityController {
    private String entity;

    @FXML private Label headingLabel;
    @FXML private Button getAllButton;
    @FXML private Button getOneButton;
    @FXML private Button createButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;

    @FXML
    public void initialize(String entity) {
        this.entity = entity;

        headingLabel.setText(STR."\{this.entity}s");
        getAllButton.setText(STR."Get All \{this.entity}s");
        getOneButton.setText(STR."Get \{this.entity}");
        createButton.setText(STR."Create \{this.entity}");
        editButton.setText(STR."Edit \{this.entity}");
        deleteButton.setText(STR."Delete \{this.entity}");
    }

    @FXML
    public void handleGetAllClick() throws Exception {
        Parent root = GUI.loadPage("control-panel/get-all-page.fxml", List.of(entity));
        history.push(GUI.createScene(root));
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    public void handleGetOneClick() throws Exception {
        Parent root = GUI.loadPage("control-panel/get-one-page.fxml", List.of(entity));
        history.push(GUI.createScene(root));
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    public void handleCreateClick() throws Exception {
        Parent root = GUI.loadPage("control-panel/create-page.fxml", List.of(entity));
        history.push(GUI.createScene(root));
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    public void handleEditClick() throws Exception {
        Parent root = GUI.loadPage("control-panel/edit-page.fxml", List.of(entity));
        history.push(GUI.createScene(root));
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    public void handleDeleteClick() throws Exception {
        Parent root = GUI.loadPage("control-panel/delete-page.fxml", List.of(entity));
        history.push(GUI.createScene(root));
        primaryStage.getScene().setRoot(root);
    }
}
