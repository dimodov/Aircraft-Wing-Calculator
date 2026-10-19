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

        Label title = new Label("Get NACA parameters");

        TextField nacaInput = new TextField();
        nacaInput.setPromptText("Enter your NACA code here...");

        TextField rootChord = new TextField();
        rootChord.setPromptText("Enter root chord...");

        Button button = new Button("Calculate");

        Label result = new Label();

        button.setOnAction(event -> {
            result.setText("Maximum camber: " + ACalculator.getMaxCamber(Integer.parseInt(nacaInput.getText()), Double.parseDouble(rootChord.getText())) + "m" + "\n" +
                           "Camber position at: " + ACalculator.getCamberPosition(Integer.parseInt(nacaInput.getText()), Double.parseDouble(rootChord.getText())) + "m" + "\n" +
                           "Maximum thickness: " + ACalculator.getMaximumThickness(Integer.parseInt(nacaInput.getText()), Double.parseDouble(rootChord.getText())));
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
            title,
            nacaInput,
            rootChord,
            button,
            result
        );

        Scene scene = new Scene(layout, 400, 250);

        stage.setTitle("Wing Calculator App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}