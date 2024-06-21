package org.example.hotel.utils;

import javafx.scene.Scene;
import java.util.Stack;

import static org.example.hotel.Application.primaryStage;

public class History {
    private static Stack<Scene> scenes;

    public History() {
        scenes = new Stack<>();
    }

    public void push(Scene scene) {
        if (primaryStage.getScene() != null) {
            scenes.push(primaryStage.getScene());
        }

        primaryStage.setScene(scene);
    }

    public void pop() {
        if (!scenes.isEmpty()) {
            Scene scene = scenes.pop();
            primaryStage.setScene(scene);
        }
    }

    public void clear() {
        if (!scenes.isEmpty()) {
            scenes.clear();
        }
    }

    public boolean isEmpty() {
        return scenes.isEmpty();
    }
}
