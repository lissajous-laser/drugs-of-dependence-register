package com.lissajouslaser.stage;

import com.lissajouslaser.DatabaseConnection;
import com.lissajouslaser.Patient;
import com.lissajouslaser.Pharmacist;
import com.lissajouslaser.TransferRetrieved;
import com.lissajouslaser.control.ComboBoxAndValidation;
import com.lissajouslaser.control.FieldAndValidation;
import com.lissajouslaser.control.NotesField;
import com.lissajouslaser.control.PharmacistField;
import com.lissajouslaser.control.SearchField;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ReverseEntryWindow extends Stage {
    // For Reverse Entry Window.
    static final int REVERSE_ENTRY_WINDOW_WIDTH = 900;
    static final int REVERSE_ENTRY_WINDOW_HEIGHT = 220;
    static final int REVERSE_ENTRY_TABLE_HEIGHT = 52;
    static final int TEXT_FIELD_SEARCH_WIDTH = 70;
    DatabaseConnection db;
    
    ReverseEntryWindow(TransferRetrieved transferRetrieved, DatabaseConnection db) {
        super();
        this.db = db;
        
        // Second to root layout.
        GridPane gridPane = new GridPane();
        Util.setUpGridSpacing(gridPane);

        // Root layout.
        GridPane mainGridPane = new GridPane();
        Util.setUpGridSpacing(mainGridPane);
        mainGridPane.add(gridPane, 0, 1);

        // Stage.
        setTitle("Reverse Entry");
        setScene(new Scene(
                mainGridPane,
                REVERSE_ENTRY_WINDOW_WIDTH,
                REVERSE_ENTRY_WINDOW_HEIGHT));

        // First row elements.
        TableView<TransferRetrieved> tableViewSingleDrug = new TableView<>();
        Util.createTableLayout(tableViewSingleDrug);
        // Remove balance presriber, pharmacist, and notes columns
        // for this tableview.
        final int pharmacistIdx = 8;
        final int notesIdx = 9;
        final int balanceIdx = 5;
        final int prescriberIdx = 6;
        tableViewSingleDrug.getColumns().remove(pharmacistIdx, notesIdx + 1);
        tableViewSingleDrug.getColumns().remove(balanceIdx, prescriberIdx + 1);
        tableViewSingleDrug.setPrefHeight(REVERSE_ENTRY_TABLE_HEIGHT);
        tableViewSingleDrug.getItems().add(transferRetrieved);
        mainGridPane.add(tableViewSingleDrug, 0, 0);

        // Sub-layout elements, first row.
        var pharmacistPrompt = new Text("Pharmacist");
        var pharmacistField = new PharmacistField();
        pharmacistField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var pharmacistBoxAndValidation = new ComboBoxAndValidation<Pharmacist>();
        Util.addToGrid(gridPane,
                pharmacistPrompt,
                pharmacistField,
                pharmacistBoxAndValidation);

        // Sub-layout elements, second row.
        Text notesPrompt = new Text("Notes");
        var notesFieldAndValidation = new
                FieldAndValidation(new NotesField(), false);
        Util.addToGrid(gridPane, notesPrompt, null, notesFieldAndValidation);

        // Sub-layout elements, third row.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        Util.setUpOkCancelButtons(ok, cancel, buttons);
        Util.addToGrid(gridPane, null, null, buttons);

        // Event handlers.
        updateComboBox(pharmacistField, pharmacistBoxAndValidation.getComboBox());
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    int balanceBefore = db.getBalance(transferRetrieved.getDrug().getId());

                    transferRetrieved.reverseEntry(
                            balanceBefore,
                            pharmacistBoxAndValidation.getComboBoxValue(),
                            notesFieldAndValidation.getFieldText());

                    String[] errors = transferRetrieved.validateReverseEntry();

                    boolean isNoErrors = Arrays
                            .stream(errors)
                            .map(str -> str.isEmpty())
                            .reduce(true, (x, y) -> x && y);

                    if (isNoErrors) {

                        if (transferRetrieved.getAgent() instanceof Patient) {
                            db.addTransfer(transferRetrieved);
                        }

                        close();
                    } else {
                        pharmacistBoxAndValidation.setValidationText(errors[0]);
                        notesFieldAndValidation.setValidationText(errors[1]);
                    }
                } catch (SQLException e) {
                    var errorWindow = new SqlErrorWindow(e.toString());
                    errorWindow.show();
                }

            }
        });

        Util.closeOnButtonPress(cancel, this);

    }

    /*
     * Updates the list in a ComboBox according to the input of
     * a TextField.
     */
    <T> void updateComboBox(
            SearchField<T> field,
            ComboBox<T> comboBox) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                field.setText(newValue.toUpperCase());

                try {
                    List<T> list = field.getList(db);
                    int oldListLength = comboBox.getItems().size();

                    comboBox.setItems(
                            FXCollections.observableArrayList(list));

                    if (comboBox.getItems().size() > oldListLength) {
                        comboBox.hide(); // Resets height of popup.
                    }

                    comboBox.show();
                } catch (SQLException e) {
                    var errorWindow = new SqlErrorWindow(e.toString());
                    errorWindow.show();
                }
            }
        });

        // Populates ComboBox with items when popup is shown.
        comboBox.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == true) {
                try {
                    List<T> list = field.getList(db);
                    comboBox.setItems(
                            FXCollections.observableArrayList(list));

                    comboBox.show();
                } catch (SQLException e) {
                    var errorWindow = new SqlErrorWindow(e.toString());
                    errorWindow.show();
                }
            }
        });
    }
}
