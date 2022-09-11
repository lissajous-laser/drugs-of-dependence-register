package com.lissajouslaser.stage;

import com.lissajouslaser.DatabaseConnection;
import com.lissajouslaser.Patient;
import com.lissajouslaser.control.AddressAreaAndValidation;
import com.lissajouslaser.control.FieldAndValidation;
import com.lissajouslaser.control.FirstNameField;
import com.lissajouslaser.control.LastNameField;
import java.sql.SQLException;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * The Add Patient window, the UI for adding
 * a patient to the database.
 */
public class AddPatientWindow extends ReferenceEntryWindow {

    /**
     * Constructor.
     */
    public AddPatientWindow(DatabaseConnection db) {
        super();

        // Root layout.
        GridPane gridPane = new GridPane();
        Util.setUpGridSpacing(gridPane);

        setTitle("Add Patient");
        setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var firstNamePrompt = new Text("First Name");
        var firstNameFieldAndValidation = new FieldAndValidation(new FirstNameField());
        Util.addToGrid(gridPane, firstNamePrompt, firstNameFieldAndValidation);

        // Second row of scene elements.
        var lastNamePrompt = new Text("Last Name");
        var lastNameFieldAndValidation = new FieldAndValidation(new LastNameField());
        Util.addToGrid(gridPane, lastNamePrompt, lastNameFieldAndValidation);

        // Third Row of scene elements.
        var addressPrompt = new Text("Address");
        var addressFieldAndValidation = new AddressAreaAndValidation();
        Util.addToGrid(gridPane, addressPrompt, addressFieldAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        Util.setUpOkCancelButtons(ok, cancel, buttons);
        Util.addToGrid(gridPane, null, buttons);

        // Event handlers.
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var patient = new Patient(
                        firstNameFieldAndValidation.getFieldText(),
                        lastNameFieldAndValidation.getFieldText(),
                        addressFieldAndValidation.getFieldText());
                ;
                String[] errors = patient.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addPatient(patient);
                    } catch (SQLException e) {
                        var errorWindow = new SqlErrorWindow(e.toString());
                        errorWindow.show();
                    }
                    close();
                } else {
                    firstNameFieldAndValidation.setValidationText(errors[0]);
                    lastNameFieldAndValidation.setValidationText(errors[1]);
                    addressFieldAndValidation.setValidationText(errors[2]);
                }
            }
        });

        Util.closeOnButtonPress(cancel, this);
    }
}
