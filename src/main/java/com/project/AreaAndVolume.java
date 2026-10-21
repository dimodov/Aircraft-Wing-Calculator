package com.project;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AreaAndVolume {
    
    public TextField nacaInput = new TextField();
    public TextField rootChord = new TextField();
    public TextField tipChord = new TextField();
    public TextField wingspan = new TextField();

    private Button calculateAreaAndVolume = new Button("Calculate Area and Volume");
    private Button calculateAandV = new Button("Calculate");

    private Label areaAndVolumeResult = new Label();

    private VBox areaAndVolumeBox = new VBox(10);

    private void setupAreaAndVolumeInputs() {
        // Sets prompt text for the input fields
        nacaInput.setPromptText("Enter NACA code (e.g., 2412)");
        rootChord.setPromptText("Enter root chord length (e.g., 1.5)");
        tipChord.setPromptText("Enter tip chord length (e.g., 1.0)");
        wingspan.setPromptText("Enter wingspan length (e.g., 10.0)");
    }

    private void setAnActionAreaAndVolume() {
        // Sets the action for the calculate area and volume button
        calculateAreaAndVolume.setOnAction(event -> {
            areaAndVolumeBox.getChildren().clear();
            setupAreaAndVolumeInputs();
        });
        calculateAandV.setOnAction(event -> {
            int profile = Integer.parseInt(nacaInput.getText());
            double rootChordLength = Double.parseDouble(rootChord.getText());
            double tipChordLength = Double.parseDouble(tipChord.getText());
            double wingSpanLength = Double.parseDouble(wingspan.getText());

            if (!nacaInput.getText().isEmpty() && !rootChord.getText().isEmpty() && !tipChord.getText().isEmpty() && !wingspan.getText().isEmpty()) {
                try {
                    profile = Integer.parseInt(nacaInput.getText());
                    rootChordLength = Double.parseDouble(rootChord.getText());
                    tipChordLength = Double.parseDouble(tipChord.getText());
                    wingSpanLength = Double.parseDouble(wingspan.getText());

                    double area = ACalculator.getArea(rootChordLength, tipChordLength, wingSpanLength);
                    double volume = ACalculator.getVolume(ACalculator.getMaximumThickness(profile, rootChordLength), rootChordLength, tipChordLength, wingSpanLength);

                    areaAndVolumeResult.setText("Wing area (one/both wings): " + area + " / " + (area * 2) + "m^2" + "\n" + "Wing volume (one/both wings): " + volume + " / " + (volume * 2) + "m^3");
                } catch (NumberFormatException e) {
                    areaAndVolumeResult.setText("Invalid input. Please enter valid numbers.");
                    return;
                }
                areaAndVolumeResult.setText("Please fill in all fields.");
                return;
            }
        });
    }

    public Parent getView() {
        setupAreaAndVolumeInputs();
        setAnActionAreaAndVolume();

        areaAndVolumeBox.getChildren().setAll(
            nacaInput,
            rootChord,
            tipChord,
            wingspan,
            calculateAandV,
            areaAndVolumeResult
        );

        return areaAndVolumeBox;
}
}