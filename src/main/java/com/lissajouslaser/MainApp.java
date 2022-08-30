package com.lissajouslaser;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class for starting GUI application.
 */
public class MainApp extends Application {
    final int fieldWidth = 200;
    final int gapSpace = 7;
    final int paddingSpace = 15;
    final int errorMessageSize = 10;
    private static Stage stage;

    @Override
    public void start(@SuppressWarnings("exports") Stage s) throws IOException {
        final int addPrescriberWindowWidth = 500;
        final int addPrescriberWindowHeight = 350;
        Database db = new Database();

        s.setScene(new Scene(
                mainWindow(),
                addPrescriberWindowWidth,
                addPrescriberWindowHeight
        ));
        s.show();

    }

    private GridPane mainWindow() {
        final int subWindowWidth = 600;
        final int subWindowHeight = 300;

        GridPane mainWindowGrid = new GridPane();
        mainWindowGrid.setHgap(gapSpace);
        mainWindowGrid.setVgap(gapSpace);
        
        mainWindowGrid.setPadding(new Insets(
                paddingSpace,
                paddingSpace,
                paddingSpace,
                paddingSpace
        ));

        Button addPatientButton = new Button("Add Patient");
        Button addPrescriberButton = new Button("Add Prescriber");
        Button addDrugButton = new Button("Add Drug");
        Button addPharmacistButton = new Button("Add Pharmacist");

        mainWindowGrid.add(addPatientButton, 0, 0);
        mainWindowGrid.add(addPrescriberButton, 1, 0);
        mainWindowGrid.add(addDrugButton, 2, 0);
        mainWindowGrid.add(addPharmacistButton, 3, 0);

        Stage addPrescriberWindow = new Stage();
        addPrescriberWindow.setTitle("Add Prescriber");
        addPrescriberWindow.setScene(new Scene(
                addPrescriberLayout(addPrescriberWindow),
                subWindowWidth,
                subWindowHeight   
        ));

        addPrescriberButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addPrescriberWindow.show();
            } 
        });

        Stage addPharmacistWindow = new Stage();
        addPharmacistWindow.setTitle("Add Pharmacist");
        addPharmacistWindow.setScene(new Scene(
                addPharmacistLayout(addPharmacistWindow),
                subWindowWidth,
                subWindowHeight
        ));

        addPharmacistButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addPharmacistWindow.show();
            }
        });

        return mainWindowGrid;
    }

    /*
     * Layout for the Add Pharmacist window, the UI for adding
     * a pharmacist to the database.
     */
    private GridPane addPharmacistLayout(Stage window) {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(gapSpace);
        gridPane.setVgap(gapSpace);

        gridPane.setPadding(new Insets(
            paddingSpace,
            paddingSpace,
            paddingSpace,
            paddingSpace
        ));

        var firstNamePrompt = new Text("First name");
        var firstNameField = new TextField();
        firstNameField.setMaxWidth(fieldWidth);
        var firstNameInvalid = new Text();
        firstNameInvalid.setFont(new Font(errorMessageSize));
        gridPane.add(firstNamePrompt, 0, 0);
        gridPane.add(firstNameField, 1, 0);
        gridPane.add(firstNameInvalid, 2, 0);

        var lastNamePrompt = new Text("Last name");
        var lastNameField = new TextField();
        lastNameField.setMaxWidth(fieldWidth);
        var lastNameInvalid = new Text();
        lastNameInvalid.setFont(new Font(errorMessageSize));
        gridPane.add(lastNamePrompt, 0, 1);
        gridPane.add(lastNameField, 1, 1);
        gridPane.add(lastNameInvalid, 2, 1);

        var registrationPrompt = new Text("Registration number");
        var registrationField = new TextField();
        registrationField.setMaxWidth(fieldWidth);
        var registrationInvalid = new Text();
        registrationInvalid.setFont(new Font(errorMessageSize));
        gridPane.add(registrationPrompt, 0, 2);
        gridPane.add(registrationField, 1, 2);
        gridPane.add(registrationInvalid, 2, 2);

        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(gapSpace);
        buttons.getChildren().addAll(ok, cancel);
        gridPane.add(buttons, 1, 4);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var pharmacist = new Pharmacist(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    registrationField.getText()
                );;
                String[] errors = pharmacist.validate();

                firstNameInvalid.setText(errors[0]);
                lastNameInvalid.setText(errors[1]);
                registrationInvalid.setText(errors[2]);
                firstNameField.setText(firstNameField.getText().toUpperCase());
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.close();
            }
        });
        return gridPane;
    }

    /*
     * Layout for the Add Prescriber window, the UI for adding
     * a prescriber to the database.
     */
    private GridPane addPrescriberLayout(Stage window) {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(gapSpace);
        gridPane.setVgap(gapSpace);

        gridPane.setPadding(new Insets(
                paddingSpace,
                paddingSpace,
                paddingSpace,
                paddingSpace
        ));

        var firstNamePrompt = new Text("First name");
        var firstNameField = new TextField();
        firstNameField.setMaxWidth(fieldWidth);
        var firstNameInvalid = new Text();
        firstNameInvalid.setFont(new Font(errorMessageSize));
        gridPane.add(firstNamePrompt, 0, 0);
        gridPane.add(firstNameField, 1, 0);
        gridPane.add(firstNameInvalid, 2, 0);

        var lastNamePrompt = new Text("Last name");
        var lastNameField = new TextField();
        lastNameField.setMaxWidth(fieldWidth);
        var lastNameInvalid = new Text();
        lastNameInvalid.setFont(new Font(errorMessageSize));
        gridPane.add(lastNamePrompt, 0, 1);
        gridPane.add(lastNameField, 1, 1);
        gridPane.add(lastNameInvalid, 2, 1);

        var prescriberNumPrompt = new Text("Prescriber number");
        var prescriberNumField = new TextField();
        prescriberNumField.setMaxWidth(fieldWidth);
        var prescriberNumInvalid = new Text();
        prescriberNumInvalid.setFont(new Font(errorMessageSize));
        gridPane.add(prescriberNumPrompt, 0, 2);
        gridPane.add(prescriberNumField, 1, 2);
        gridPane.add(prescriberNumInvalid, 2, 2);

        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(gapSpace);
        buttons.getChildren().addAll(ok, cancel);
        gridPane.add(buttons, 1, 4);

        ok.setOnAction(new EventHandler<ActionEvent>()  {
            @Override
            public void handle(ActionEvent event) {
                var prescriber = new Prescriber(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    prescriberNumField.getText()
                );;
                String[] errors = prescriber.validate();

                // Create messages for invalid field entries.
                firstNameInvalid.setText(errors[0]);
                lastNameInvalid.setText(errors[1]);
                prescriberNumInvalid.setText(errors[2]);
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.close();
            }

        });

        return gridPane;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
