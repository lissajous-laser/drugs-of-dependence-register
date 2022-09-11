package com.lissajouslaser.stage;

import com.lissajouslaser.DatabaseConnection;
import com.lissajouslaser.Pharmacist;
import com.lissajouslaser.control.FieldAndValidation;
import com.lissajouslaser.control.FirstNameField;
import com.lissajouslaser.control.LastNameField;
import com.lissajouslaser.control.RegistrationField;
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
 * The Add Pharmacist window, the UI for adding
 * a pharmacist to the database.
 */
public class AddPharmacistWindow extends ReferenceEntryWindow {

    /**
     * Constructor.
     */
    public AddPharmacistWindow(DatabaseConnection db) {
        super();

        // Root layout.
        GridPane gridPane = new GridPane();
        Util.setUpGridSpacing(gridPane);

        setTitle("Add Pharmacist");
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
        var registrationPrompt = new Text("Registration");
        var registrationFieldAndValidation = new FieldAndValidation(new RegistrationField());
        Util.addToGrid(gridPane, registrationPrompt, registrationFieldAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        Util.setUpOkCancelButtons(ok, cancel, buttons);
        Util.addToGrid(gridPane, null, buttons);

        // EventHandlers.
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var pharmacist = new Pharmacist(
                        firstNameFieldAndValidation.getFieldText(),
                        lastNameFieldAndValidation.getFieldText(),
                        registrationFieldAndValidation.getFieldText());
                ;
                String[] errors = pharmacist.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addPharmacist(pharmacist);
                    } catch (SQLException e) {
                        var errorWindow = new SqlErrorWindow(e.toString());
                        errorWindow.show();
                    }
                    close();
                } else {
                    firstNameFieldAndValidation.setValidationText(errors[0]);
                    lastNameFieldAndValidation.setValidationText(errors[1]);
                    registrationFieldAndValidation.setValidationText(errors[2]);
                }
            }
        });

        Util.closeOnButtonPress(cancel, this);
    
    }
}
