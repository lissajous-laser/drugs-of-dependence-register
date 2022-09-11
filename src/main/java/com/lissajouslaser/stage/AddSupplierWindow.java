package com.lissajouslaser.stage;

import com.lissajouslaser.DatabaseConnection;
import com.lissajouslaser.Supplier;
import com.lissajouslaser.control.AddressAreaAndValidation;
import com.lissajouslaser.control.FieldAndValidation;
import com.lissajouslaser.control.SupplierNameField;
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
 * The Add Supplier window, the UI for adding
 * a supplier to the database.
 */
public class AddSupplierWindow extends ReferenceEntryWindow {
    
    /**
     * Constructor.
     */
    public AddSupplierWindow(DatabaseConnection db) {

        // Root layout.
        GridPane gridPane = new GridPane();
        Util.setUpGridSpacing(gridPane);

        setTitle("Add Supplier");
        setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var supplierNamePrompt = new Text("Supplier Name");
        var supplierNameFieldAndValidation = new FieldAndValidation(new SupplierNameField());
        Util.addToGrid(gridPane, supplierNamePrompt, supplierNameFieldAndValidation);

        // Second Row of scene elements.
        var addressPrompt = new Text("Address");
        var addressFieldAndValidation = new AddressAreaAndValidation();
        Util.addToGrid(gridPane, addressPrompt, addressFieldAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        Util.setUpOkCancelButtons(ok, cancel, buttons);
        Util.addToGrid(gridPane, null, buttons);

        // Event Handlers.
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var supplier = new Supplier(
                        supplierNameFieldAndValidation.getFieldText(),
                        addressFieldAndValidation.getFieldText());

                String[] errors = supplier.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addSupplier(supplier);
                    } catch (SQLException e) {
                        var errorWindow = new SqlErrorWindow(e.toString());
                        errorWindow.show();
                    }
                    close();
                } else {
                    supplierNameFieldAndValidation.setValidationText(errors[0]);
                    addressFieldAndValidation.setValidationText(errors[1]);
                }
            }
        });

        Util.closeOnButtonPress(cancel, this);

    }
}
