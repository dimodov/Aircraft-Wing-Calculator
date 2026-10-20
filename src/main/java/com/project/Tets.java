package com.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Tets extends Application {

    // Declares a Stage variable to hold the main application window
    private Stage stage;

    // Created title label for the application
    private Label title = new Label("Wing Calculator App");

    // Creates a label to display the results
    private Label result = new Label();

    // Creates text fields for user input
    private TextField nacaInput = new TextField();

    private TextField rootChord = new TextField();
        
    // Creates a button to calculate NACA parameters
    private Button calculateNacaParameters = new Button("Calculate NACA parameters");

    private Button calculateNaca = new Button("Calculate");

    private Button backToMain = new Button("Back to the main menu");

    // Creates a VBox layout to hold the GUI elements
    private VBox showedBox = new VBox(10);

    //@Override 
    public void start(Stage stage) {
        this.stage = stage;

        setupInputs();
        setAnActionNaca();
        setupLayout();
        setupUI();
    }

    private void setupLayout() {
        // Adds the title and calculate button to the VBox
        showedBox.getChildren().clear();
        showedBox.getChildren().addAll(title, calculateNacaParameters);
    }

    private void setupInputs() {
        // Sets prompt text for the input fields
        nacaInput.setPromptText("Enter NACA code (e.g., 2412)");
        rootChord.setPromptText("Enter root chord length (e.g., 1.5)");

        //nacaInput.setVisible(false);
        //nacaInput.setManaged(false);

        //rootChord.setVisible(false);
        //rootChord.setManaged(false);

        //calculateNaca.setVisible(false);
        //calculateNaca.setManaged(false);

        //backToMain.setVisible(false);
        //backToMain.setManaged(false);
    }

    private void setAnActionNaca() {
        // Sets the action for the calculate NACA parameters button
        calculateNacaParameters.setOnAction(event -> {

            // Clears the VBox and adds the input fields and calculate button
            showedBox.getChildren().clear();
            showedBox.getChildren().addAll(nacaInput, rootChord, calculateNaca, backToMain, result);
        });

        backToMain.setOnAction(event -> {
            // Clears the VBox and adds the main menu elements
            showedBox.getChildren().clear();
            setupLayout();
        });

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
    }

    private void setupUI() {
        Scene scene = new Scene(showedBox, 400, 250);
        stage.setTitle("Wing Calculator App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}