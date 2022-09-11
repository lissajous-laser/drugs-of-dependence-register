package com.lissajouslaser.stage;

import com.lissajouslaser.TransferRetrieved;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
 * Utility class for boilerplate code in the package.
 */
class Util {
    // For error message from SQLExceptions.
    static final int ERROR_WINDOW_HEIGHT = 160;
    static final int ERROR_WINDOW_WIDTH = 480;
    // Spacing parameters for GridPane.
    static final int PADDING_SPACE = 15;
    static final int GAP_SPACE = 8;
    // Column widths for transfer table.
    static final int DATE_WIDTH = 100;
    static final int AGENT_WIDTH = 360;
    static final int DRUG_WIDTH = 200;
    static final int IN_OUT_WIDTH = 50;
    static final int BALANCE_WIDTH = 80;
    static final int PRESCRIBER_WIDTH = 180;
    static final int REFERENCE_WIDTH = 100;
    static final int PHARMACIST_WIDTH = 180;
    static final int NOTES_WIDTH = 100;
    // Table.
    static final int TABLE_WIDTH = 1420;
    static final int TABLE_HEIGHT = 360;
    // Other parameters.
    static final int OK_CANCEL_BUTTON_WIDTH = 80;
    static final int TOOLTIP_DELAY = 400; // milliseconds

    /*
     * Sets up functionality of cancel button.
     */
    static void closeOnButtonPress(Button cancel, Stage stage) {
        cancel.setOnAction((event) -> stage.close());
    }

    /*
     * Removes input validation message once focus or mouse hover is
     * no longer active on the control.
     */
    static void validationTextRemoval(Control control, Text text) {
        control.focusedProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue == false) {
                        text.setText("");
                    }
                });

        control.hoverProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue == false) {
                        text.setText("");
                    }
                });
    }

    /*
     * Gives a control a tooltip message.
     */
    static void addTooltip(Control control, String msg) {
        var tooltip = new Tooltip(msg);
        tooltip.setShowDelay(Duration.millis(TOOLTIP_DELAY));
        control.setTooltip(tooltip);
    }

    /*
     * Sets up the layout of the OK and Cancel buttons.
     */
    static void setUpOkCancelButtons(
            Button ok,
            Button cancel,
            FlowPane buttons) {
        ok.setPrefWidth(OK_CANCEL_BUTTON_WIDTH);
        cancel.setPrefWidth(OK_CANCEL_BUTTON_WIDTH);
        cancel.cancelButtonProperty().setValue(true);
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
    }

    /*
     * Sets up spacing properties of gridpane layout.
     */
    static void setUpGridSpacing(GridPane gridPane) {
        gridPane.setHgap(GAP_SPACE);
        gridPane.setVgap(GAP_SPACE);
        gridPane.setPadding(new Insets(
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE));
    }

    /*
     * Adds listed child nodes to the GridPane on the next row.
     * Any nulls in children will cause the column to be skipped
     * over.
     */
    static void addToGrid(GridPane gridPane, Node... children) {
        int rowIndex = gridPane.getRowCount();

        for (int i = 0; i < children.length; i++) {
            Node child = children[i];
            if (child != null) {
                gridPane.add(child, i, rowIndex);
            }
        }
    }

    /*
     * Creates table used to view drug transfers.
     */
    static void createTableLayout(TableView<TransferRetrieved> tableView) {

        var dateColumn = new TableColumn<TransferRetrieved, String>("Date");
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        dateColumn.setPrefWidth(DATE_WIDTH);

        var agentColumn = new TableColumn<TransferRetrieved, String>("Agent");
        agentColumn.setCellValueFactory(
                new PropertyValueFactory<>("agent"));
        agentColumn.setPrefWidth(AGENT_WIDTH);

        var drugColumn = new TableColumn<TransferRetrieved, String>("Drug");
        drugColumn.setCellValueFactory(
                new PropertyValueFactory<>("drug"));
        drugColumn.setPrefWidth(DRUG_WIDTH);

        var qtyInColumn = new TableColumn<TransferRetrieved, String>("In");
        qtyInColumn.setCellValueFactory(
                new PropertyValueFactory<>("qtyIn"));
        qtyInColumn.setPrefWidth(IN_OUT_WIDTH);

        var qtyOutColumn = new TableColumn<TransferRetrieved, String>("Out");
        qtyOutColumn.setCellValueFactory(
                new PropertyValueFactory<>("qtyOut"));
        qtyOutColumn.setPrefWidth(IN_OUT_WIDTH);

        var balanceColumn = new TableColumn<TransferRetrieved, String>("Balance");
        balanceColumn.setCellValueFactory(
                new PropertyValueFactory<>("balance"));
        balanceColumn.setPrefWidth(BALANCE_WIDTH);

        var prescriberColumn = new TableColumn<TransferRetrieved, String>("Prescriber");
        prescriberColumn.setCellValueFactory(
                new PropertyValueFactory<>("prescriber"));
        prescriberColumn.setPrefWidth(PRESCRIBER_WIDTH);

        var referenceColumn = new TableColumn<TransferRetrieved, String>("Reference");
        referenceColumn.setCellValueFactory(
                new PropertyValueFactory<>("reference"));
        referenceColumn.setPrefWidth(REFERENCE_WIDTH);

        var pharmacistColumn = new TableColumn<TransferRetrieved, String>("Pharmacist");
        pharmacistColumn.setCellValueFactory(
                new PropertyValueFactory<>("pharmacist"));
        pharmacistColumn.setPrefWidth(PHARMACIST_WIDTH);

        var notesColumn = new TableColumn<TransferRetrieved, String>("Notes");
        notesColumn.setCellValueFactory(
                new PropertyValueFactory<>("notes"));
        notesColumn.setPrefWidth(NOTES_WIDTH);

        tableView.getColumns().addAll(dateColumn, agentColumn, drugColumn);
        tableView.getColumns().addAll(qtyInColumn, qtyOutColumn, balanceColumn);
        tableView.getColumns().addAll(prescriberColumn, referenceColumn);
        tableView.getColumns().addAll(pharmacistColumn, notesColumn);
        tableView.setPrefSize(TABLE_WIDTH, TABLE_HEIGHT);
    }

}
