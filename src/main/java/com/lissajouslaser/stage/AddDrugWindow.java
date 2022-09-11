package com.lissajouslaser.stage;

import com.lissajouslaser.DatabaseConnection;
import com.lissajouslaser.Drug;
import com.lissajouslaser.control.DoseFormField;
import com.lissajouslaser.control.DrugNameField;
import com.lissajouslaser.control.FieldAndValidation;
import com.lissajouslaser.control.StrengthField;
import java.sql.SQLException;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class AddDrugWindow extends ReferenceEntryWindow { 
 
    /**
     * Constructor.
     */
    public AddDrugWindow(DatabaseConnection db) {
        super();

        // Root layout.
        GridPane gridPane = new GridPane();
        Util.setUpGridSpacing(gridPane);

        setTitle("Add Drug");
        setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var namePrompt = new Text("Name");
        var nameFieldAndValidation = new FieldAndValidation(new DrugNameField());
        Util.addToGrid(gridPane, namePrompt, nameFieldAndValidation);

        // Second row of scene elements.
        var strengthPrompt = new Text("Strength");
        var strengthFieldAndValidation = new
                FieldAndValidation(new StrengthField(), false);
        Util.addToGrid(gridPane, strengthPrompt, strengthFieldAndValidation);

        // Third row of scene elements.
        var doseFormPrompt = new Text("Dose Form");
        var doseFormFieldAndValidation = new
                FieldAndValidation(new DoseFormField(), false);
        Util.addToGrid(gridPane, doseFormPrompt, doseFormFieldAndValidation);

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
                var drug = new Drug(
                        nameFieldAndValidation.getFieldText(),
                        strengthFieldAndValidation.getFieldText(),
                        doseFormFieldAndValidation.getFieldText());

                String[] errors = drug.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addDrug(drug);
                    } catch (SQLException e) {
                        var errorWindow = new SqlErrorWindow(e.toString());
                        errorWindow.show();
                    }
                    close();
                } else {
                    nameFieldAndValidation.setValidationText(errors[0]);
                    strengthFieldAndValidation.setValidationText(errors[1]);
                    doseFormFieldAndValidation.setValidationText(errors[2]);
                }
            }
        });

        Util.closeOnButtonPress(cancel, this);
    }
}
