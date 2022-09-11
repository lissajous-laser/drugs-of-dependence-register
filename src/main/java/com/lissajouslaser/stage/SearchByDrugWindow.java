package com.lissajouslaser.stage;

import com.lissajouslaser.DatabaseConnection;
import com.lissajouslaser.Drug;
import com.lissajouslaser.SearchByDrug;
import com.lissajouslaser.TransferRetrieved;
import com.lissajouslaser.control.ComboBoxAndValidation;
import com.lissajouslaser.control.DrugField;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class SearchByDrugWindow extends SearchWindow {

    /**
     * Constructor.
     */
    public SearchByDrugWindow(DatabaseConnection db) {
        super(db);

        // Second to root layout.
        GridPane gridPane = new GridPane();
        Util.setUpGridSpacing(gridPane);

        // Root layout.
        GridPane gridPaneMain = new GridPane();
        Util.setUpGridSpacing(gridPaneMain);
        Util.addToGrid(gridPaneMain, gridPane);
;
        setTitle("Search by Drug");
        setScene(new Scene(
                gridPaneMain, SUBWINDOW_C_WIDTH, SUBWINDOW_C_HEIGHT));

        // First row of scene elements.
        var drugPrompt = new Text("Drug");
        var drugField = new DrugField();
        drugField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var drugBoxAndValidation = new ComboBoxAndValidation<Drug>();
        Util.addToGrid(gridPane, drugPrompt, drugField, drugBoxAndValidation);

        // Second row of scene elements.
        var startDateField = new DatePicker();
        startDateField.setPrefWidth(DATE_PICKER_WIDTH);
        var startDateInvalid = new Text();
        startDateInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane startDateFieldAndValidation = new FlowPane();
        startDateFieldAndValidation.setHgap(GAP_SPACE);
        startDateFieldAndValidation
                .getChildren()
                .addAll(startDateField, startDateInvalid);
        var startDatePrompt = new Text("Start Date");
        Util.addToGrid(gridPane, startDatePrompt, null, startDateFieldAndValidation);

        // Third row of scene elements.
        var endDateField = new DatePicker();
        endDateField.setPrefWidth(DATE_PICKER_WIDTH);
        var endDateInvalid = new Text();
        endDateInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane endDateFieldAndValidation = new FlowPane();
        endDateFieldAndValidation.setHgap(GAP_SPACE);
        endDateFieldAndValidation
                .getChildren()
                .addAll(endDateField, endDateInvalid);
        var endDatePrompt = new Text("End Date");
        Util.addToGrid(gridPane, endDatePrompt, null, endDateFieldAndValidation);

        // Button.
        var search = new Button("Search");
        search.setPrefWidth(OK_CANCEL_BUTTON_WIDTH);
        var close = new Button("Close");
        close.setPrefWidth(OK_CANCEL_BUTTON_WIDTH);
        var reverseEntryButton = new Button("↶");
        Util.addTooltip(reverseEntryButton, "Reverse entry");
        var reverseEntryInvalid = new Text();
        reverseEntryInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren()
                .addAll(search, close, reverseEntryButton, reverseEntryInvalid);
        Util.addToGrid(gridPane, null, null, buttons);

        // Search Results.
        var tableView = new TableView<TransferRetrieved>();
        Util.createTableLayout(tableView);
        Util.addToGrid(gridPaneMain, tableView);

        // Event handlers.
        updateComboBox(drugField, drugBoxAndValidation.getComboBox());

        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var searchByDrug = new SearchByDrug(
                        drugBoxAndValidation.getComboBoxValue(),
                        startDateField.getValue(),
                        endDateField.getValue());
                String[] errors = searchByDrug.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);

                if (isNoErrors) {
                    try {
                        List<TransferRetrieved> list = db.getTransfersByDrugList(searchByDrug);
                        tableView.setItems(
                                FXCollections.observableArrayList(list));

                    } catch (SQLException e) {
                        var errorWindow = new SqlErrorWindow(e.toString());
                        errorWindow.show();
                    }
                } else {
                    // Create messages for invalid entries.
                    drugBoxAndValidation.setValidationText(errors[0]);
                    startDateInvalid.setText(errors[1]);
                    endDateInvalid.setText(errors[2]);
                }

            }
        });

        reverseEntryButton.setOnAction(event -> {
            TransferRetrieved transferEntry =
                    tableView.getSelectionModel().getSelectedItem();

            if (transferEntry != null) {
                var reverseEntryWindow = new ReverseEntryWindow(transferEntry, db);
                reverseEntryWindow.show();
            } else {
                reverseEntryInvalid.setText("Select entry first");

            }
        });

        Util.validationTextRemoval(reverseEntryButton, reverseEntryInvalid);

        Util.closeOnButtonPress(close, this);

    }
}
