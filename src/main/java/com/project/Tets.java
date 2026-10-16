package com.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Tets extends Application {
        public void start(Stage stage) {

        Label title = new Label("My First JavaFX App");

        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter your name");

        Button button = new Button("Say Hello");

        Label result = new Label();

        button.setOnAction(event -> {
            String name = nameInput.getText();
            result.setText("Hello, " + name + "!");
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
            title,
            nameInput,
            button,
            result
        );

        Scene scene = new Scene(layout, 400, 250);

        stage.setTitle("My First Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}