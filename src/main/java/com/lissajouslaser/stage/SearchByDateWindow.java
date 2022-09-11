package com.lissajouslaser.stage;

import com.lissajouslaser.DatabaseConnection;
import com.lissajouslaser.TransferRetrieved;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SearchByDateWindow extends SearchWindow {
    
    /**
     * Constructor.
     */
    public SearchByDateWindow(DatabaseConnection db) {
        super(db);

        // Second to root layout.
        GridPane gridPane = new GridPane();
        Util.setUpGridSpacing(gridPane);

        // Root layout.
        GridPane gridPaneMain = new GridPane();
        Util.setUpGridSpacing(gridPaneMain);
        Util.addToGrid(gridPaneMain, gridPane);

        // Stage.
        setTitle("Search by Day");
        setScene(new Scene(
                gridPaneMain, SUBWINDOW_C_WIDTH, SUBWINDOW_C_HEIGHT));

        // First Row of scene elements.
        var dateField = new DatePicker();
        dateField.setPrefWidth(DATE_PICKER_WIDTH);
        var close = new Button("Close");
        close.setPrefWidth(OK_CANCEL_BUTTON_WIDTH);
        var reverseEntryButton = new Button("↶");
        Util.addTooltip(reverseEntryButton, "Reverse entry");
        var reverseEntryInvalid = new Text();
        reverseEntryInvalid.setFont(new Font(ERROR_FONT_SIZE));
        Util.addToGrid(
                gridPane,
                new Text("Date"),
                dateField,
                close,
                reverseEntryButton,
                reverseEntryInvalid);

        // Search Results
        var tableView = new TableView<TransferRetrieved>();
        Util.createTableLayout(tableView);
        Util.addToGrid(gridPaneMain, tableView);

        // Event handlers.
        dateField.setOnAction(event -> {

            updateTableView(tableView, dateField);
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

        dateField.show();

        Util.closeOnButtonPress(close, this);
    }

        /*
     * Updates the TableView to filter entries with the chosen date.
     */
    void updateTableView(
            TableView<TransferRetrieved> tableView,
            DatePicker dateField) {
        try {
            LocalDate localDate = dateField.getValue();

            if (localDate != null) {
                List<TransferRetrieved> list = db.getTransfersByDateList(localDate);
                tableView.setItems(
                        FXCollections.observableArrayList(list));
            }
        } catch (SQLException e) {
            var errorWindow = new SqlErrorWindow(e.toString());
            errorWindow.show();
        }
    }
}
