package com.lissajouslaser.stage;

import com.lissajouslaser.DatabaseConnection;
import com.lissajouslaser.Drug;
import com.lissajouslaser.Pharmacist;
import com.lissajouslaser.Supplier;
import com.lissajouslaser.TransferInput;
import com.lissajouslaser.control.ComboBoxAndValidation;
import com.lissajouslaser.control.DrugField;
import com.lissajouslaser.control.FieldAndValidation;
import com.lissajouslaser.control.NotesField;
import com.lissajouslaser.control.PharmacistField;
import com.lissajouslaser.control.QtyField;
import com.lissajouslaser.control.ReferenceField;
import com.lissajouslaser.control.SupplierField;
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
 * The Receive from Supplier window, the UI for adding
 * a receipt of order from supplier to the database.
 */
public class ReceiverFromSupplierWindow extends TransferEntryWindow {
    
    /**
     * Constructor.
     */
    public ReceiverFromSupplierWindow(DatabaseConnection db) {
        super(db);

        // Root layout.
        GridPane gridPane = new GridPane();
        Util.setUpGridSpacing(gridPane);

        setTitle("Receive from Supplier");
        setScene(new Scene(
                gridPane, SUBWINDOW_B_WIDTH, SUBWINDOW_B_HEIGHT));

        // First row of scene elements.
        var supplierPrompt = new Text("Supplier");
        var supplierField = new SupplierField();
        supplierField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var supplierBoxAndValidation = new ComboBoxAndValidation<Supplier>();
        Util.addToGrid(
                gridPane,
                supplierPrompt,
                supplierField,
                supplierBoxAndValidation);

        // Second row of scene elements.
        var drugPrompt = new Text("Drug");
        var drugField = new DrugField();
        drugField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var drugBoxAndValidation = new ComboBoxAndValidation<Drug>();
        Util.addToGrid(gridPane, drugPrompt, drugField, drugBoxAndValidation);

        // Third row of scene elements.
        var balancePrompt = new Text("Balance");
        var balanceField = new Text();
        Util.addToGrid(gridPane, balancePrompt, null, balanceField);

        // Fourth row of scene elements.
        var qtyPrompt = new Text("Quantity");
        var qtyFieldAndValidation = new FieldAndValidation(new QtyField());
        Util.addToGrid(gridPane, qtyPrompt, null, qtyFieldAndValidation);

        // Fifth row of scene elements.
        var pharmacistPrompt = new Text("Pharmacist");
        var pharmacistField = new PharmacistField();
        pharmacistField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var pharmacistBoxAndValidation = new
                ComboBoxAndValidation<Pharmacist>();
        Util.addToGrid(
                gridPane,
                pharmacistPrompt,
                pharmacistField,
                pharmacistBoxAndValidation);

        // Sixth row of scene elements.
        var invoicePrompt = new Text("Invoice Num");
        var invoiceFieldAndValidation = new FieldAndValidation(new ReferenceField());
        Util.addToGrid(gridPane, invoicePrompt, null, invoiceFieldAndValidation);

        // Seventh row of scene elements.
        var notesPrompt = new Text("Notes");
        var notesFieldAndValidation = new
                FieldAndValidation(new NotesField(), false);
        Util.addToGrid(
                gridPane,
                notesPrompt,
                null,
                notesFieldAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        Util.setUpOkCancelButtons(ok, cancel, buttons);
        Util.addToGrid(gridPane, null, null, buttons);

        // Event handlers.
        updateComboBox(supplierField, supplierBoxAndValidation.getComboBox());

        updateComboBox(drugField, drugBoxAndValidation.getComboBox());

        updateComboBox(pharmacistField, pharmacistBoxAndValidation.getComboBox());

        drugBoxAndValidation.getComboBox().setOnAction(event -> {
            Drug selectedDrug = drugBoxAndValidation.getComboBoxValue();

            try {
                if (selectedDrug != null) {
                    balanceField.setText(String.valueOf(
                            db.getBalance(selectedDrug.getId())));
                }
            } catch (SQLException e) {
                var errorWindow = new SqlErrorWindow(e.toString());
                errorWindow.show();
            }
        });

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                var transfer = new TransferInput(
                        supplierBoxAndValidation.getComboBoxValue(),
                        drugBoxAndValidation.getComboBoxValue(), 
                        balanceField.getText(),
                        qtyFieldAndValidation.getFieldText(),
                        null,
                        invoiceFieldAndValidation.getFieldText(),
                        notesFieldAndValidation.getFieldText(),
                        pharmacistBoxAndValidation.getComboBoxValue());
                String[] errors = transfer.validateReceiveFromSupplier();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);

                if (isNoErrors) {
                    try {
                        db.addTransfer(transfer);
                    } catch (SQLException e) {
                        var errorWindow = new SqlErrorWindow(e.toString());
                        errorWindow.show();
                    }
                    close();
                } else {
                    supplierBoxAndValidation.setValidationText(errors[0]);
                    drugBoxAndValidation.setValidationText(errors[1]);
                    qtyFieldAndValidation.setValidationText(errors[2]);
                    invoiceFieldAndValidation.setValidationText(errors[4]);
                    notesFieldAndValidation.setValidationText(errors[5]);
                    pharmacistBoxAndValidation.setValidationText(errors[6]);
                }
            }
        });

        Util.closeOnButtonPress(cancel, this);

    }
}
