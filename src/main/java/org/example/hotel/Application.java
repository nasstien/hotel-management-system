package org.example.hotel;

import org.example.hotel.utils.*;
import org.example.hotel.utils.gui.GUI;
import org.example.hotel.models.Hotel;
import org.example.hotel.models.User;

import io.github.cdimascio.dotenv.Dotenv;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class Application extends javafx.application.Application {
    public static Database database;
    public static History history;
    public static Stage primaryStage;
    public static Hotel hotel;
    public static User user;

    @Override
    public void start(Stage stage) throws Exception {
        Dotenv dotenv = Dotenv.load();
        database = new Database(dotenv.get("DB_URL"), dotenv.get("DB_USER"), dotenv.get("DB_PASSWORD"));
        database.connect();

        history = new History();
        primaryStage = stage;

        Scene scene = login();
        history.push(scene);

        primaryStage = GUI.createStage(scene, GUI.WINDOW_TITLE, GUI.WINDOW_ICON, (WindowEvent e) -> database.close());
        primaryStage.show();
    }

    public static Scene login() throws Exception {
        Parent root = GUI.loadPage("login.fxml");
        return GUI.createScene(root);
    }

    public static Scene signup() throws Exception {
        Parent root = GUI.loadPage("signup.fxml");
        return GUI.createScene(root);
    }

    public static Scene mainMenu() throws Exception {
        Parent root = GUI.loadPage("main-menu.fxml");
        return GUI.createScene(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}