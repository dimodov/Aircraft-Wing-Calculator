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

        // GUI Elements
        Label title = new Label("Wing Calculator App");

        // Creates a label to display the results
        Label result = new Label();

        // Creates text fields for user input
        TextField nacaInput = new TextField();
        nacaInput.setPromptText("Enter your NACA code here...");

        TextField rootChord = new TextField();
        rootChord.setPromptText("Enter root chord...");
        
        // Creates a button to calculate NACA parameters
        Button calculateNacaParameters = new Button("Calculate NACA parameters");

        Button calculateNaca = new Button("Calculate");

        // Sets the action for the calculate button
        calculateNaca.setOnAction(event -> {
            String nacaCode = nacaInput.getText();
            String rootChordValue = rootChord.getText();

            // Checks if both text fields are filled and calculates the NACA parameters
            if (!nacaCode.isEmpty() && !rootChordValue.isEmpty()) {

                // Tries to parse the input values and calculate the NACA parameters
                try {
                    int profile = Integer.parseInt(nacaCode);
                    double rootChordLength = Double.parseDouble(rootChordValue);

                    double maxCamber = ACalculator.getMaxCamber(profile, rootChordLength);
                    double camberPosition = ACalculator.getCamberPosition(profile, rootChordLength);
                    double maxThickness = ACalculator.getMaximumThickness(profile, rootChordLength);

                    result.setText("Maximum Camber: " + maxCamber + "\n" +
                                   "Camber Position: " + camberPosition + "\n" +
                                   "Maximum Thickness: " + maxThickness);
                } 

                // Catches any NumberFormatException that may occur if the input values are not valid numbers
                catch (NumberFormatException e) {
                    result.setText("Invalid input. Please enter valid numbers.");
                }
            } 

            // If either text field is empty, displays an error message
            else {
                result.setText("Please fill in both fields.");
            }
        });

        // Creates a VBox layout to hold the GUI elements
        VBox whatever = new VBox(10);
        whatever.getChildren().addAll(title, calculateNacaParameters);

        // Sets the action for the calculate NACA parameters button
        calculateNacaParameters.setOnAction(event -> {

            // Clears the VBox and adds the input fields and calculate button
            whatever.getChildren().clear();
            whatever.getChildren().addAll(nacaInput, rootChord, result, calculateNaca);
        });

        // Creates a scene with the VBox layout and sets the stage
        Scene scene = new Scene(whatever, 400, 250);

        stage.setTitle("Wing Calculator App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}