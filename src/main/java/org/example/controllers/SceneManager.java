package org.example.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    public static void switchScene(String fxmlFile, String title) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            SceneManager.class.getResource("/views/" + fxmlFile)
                    );

            Scene scene = new Scene(loader.load());

            scene.getStylesheets().add(
                    SceneManager.class
                            .getResource("/styles/style.css")
                            .toExternalForm()
            );

            Stage stage = new Stage();

            stage.setTitle(title);

            stage.setScene(scene);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}