package com.lissajouslaser;

import com.lissajouslaser.stage.AddDrugWindow;
import com.lissajouslaser.stage.AddPatientWindow;
import com.lissajouslaser.stage.AddPharmacistWindow;
import com.lissajouslaser.stage.AddPrescriberWindow;
import com.lissajouslaser.stage.AddSupplierWindow;
import com.lissajouslaser.stage.ReceiverFromSupplierWindow;
import com.lissajouslaser.stage.SearchByDateWindow;
import com.lissajouslaser.stage.SearchByDrugWindow;
import com.lissajouslaser.stage.SqlErrorWindow;
import com.lissajouslaser.stage.SupplyToPatientWindow;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Class for starting GUI application.
 */
public class MainApp extends Application {
    // For smaller subwindows, e.g. Add new Patient.
    static final int SUBWINDOW_A_WIDTH = 480;
    static final int SUBWINDOW_A_HEIGHT = 200;
    // For larger subwindows, e.g. Supply to Patient.
    static final int SUBWINDOW_B_WIDTH = 660;
    static final int SUBWINDOW_B_HEIGHT = 320;
    // For the largest subwindows, e.g. Search By Drug.
    static final int SUBWINDOW_C_WIDTH = 1450;
    static final int SUBWINDOW_C_HEIGHT = 560;
    // For main window.
    static final int MAIN_WINDOW_WIDTH = 900;
    static final int MAIN_WINDOW_HEIGHT = 420;
    // For error message from SQLExceptions.
    static final int ERROR_WINDOW_HEIGHT = 160;
    static final int ERROR_WINDOW_WIDTH = 480;
    // For Reverse Entry Window.
    static final int REVERSE_ENTRY_WINDOW_WIDTH = 900;
    static final int REVERSE_ENTRY_WINDOW_HEIGHT = 220;
    // Buttons with images.
    static final int GRAPHIC_BUTTON_WIDTH = 160;
    static final int GRAPHIC_BUTTON_HEIGHT = 190;
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
    // Other parameters.
    static final int TABLE_WIDTH = 1420;
    static final int TABLE_HEIGHT = 360;
    static final int REVERSE_ENTRY_TABLE_HEIGHT = 52;
    static final int OK_CANCEL_BUTTON_WIDTH = 80;
    static final int TEXT_FIELD_SEARCH_WIDTH = 70;
    static final int ERROR_FONT_SIZE = 11;
    static final int DATE_PICKER_WIDTH = 120;
    static final int TOOLTIP_DELAY = 400; // milliseconds
    // Instance variables
    DatabaseConnection db;
    Stage supplyToPatientWindow;
    Stage receiveFromSupplierWindow;
    Stage searchByDateWindow;
    Stage searchByDrugWindow;
    Stage addPatientWindow;
    Stage addPrescriberWindow;
    Stage addPharmacistWindow;
    Stage addDrugWindow;
    Stage addSupplierWindow;
    Stage reverseEntryWindow;

    @Override
    public void start(Stage s) {
        try {
            db = new DatabaseConnection();
        } catch (SQLException e) {
            var sqlErrorWindow = new SqlErrorWindow(e.toString());
            sqlErrorWindow.show();
        }

        openMainWindow();
    }

    private void openMainWindow() {
        // Root layour.
        GridPane gridPane = new GridPane();
        setUpSpacing(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Drugs of Dependence Register");
        stage.setScene(new Scene(
                gridPane, MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT));

        var supplyToPatientButton = new Button("Supply to Patient");
        addButtonImage(
                supplyToPatientButton,
                "file:src/main/resources/SupplyToPatient.png");

        var receiveFromSupplierButton = new Button("Receive from Supplier");
        addButtonImage(
                receiveFromSupplierButton,
                "file:src/main/resources/ReceiveFromSupplier.png");

        var searchByDrugButton = new Button("Search by Drug");
        addButtonImage(
                searchByDrugButton,
                "file:src/main/resources/SearchByDrug.png");

        var searchByDateButton = new Button("Search by Date");
        addButtonImage(
                searchByDateButton,
                "file:src/main/resources/SearchByDate.png");

        var addPatientButton = new Button("Add Patient");
        addButtonImage(
                addPatientButton,
                "file:src/main/resources/Patient.png");

        var addPrescriberButton = new Button("Add Prescriber");
        addButtonImage(
                addPrescriberButton,
                "file:src/main/resources/Doctor.png");

        var addPharmacistButton = new Button("Add Pharmacist");
        addButtonImage(
                addPharmacistButton,
                "file:src/main/resources/Pharmacist.png");

        var addDrugButton = new Button("Add Drug");
        addButtonImage(
                addDrugButton,
                "file:src/main/resources/Drug.png");

        var addSupplierButton = new Button("Add Supplier");
        addButtonImage(
                addSupplierButton,
                "file:src/main/resources/Supplier.png");

        addToGrid(
                gridPane,
                supplyToPatientButton,
                receiveFromSupplierButton,
                searchByDateButton,
                searchByDrugButton);
        addToGrid(gridPane,
                addPatientButton,
                addPrescriberButton,
                addPharmacistButton,
                addDrugButton,
                addSupplierButton);

        // Event handlers.
        addPrescriberButton.setOnAction((event) -> {
            if (addPrescriberWindow == null
                    || !addPrescriberWindow.isShowing()) {
                addPrescriberWindow = new AddPrescriberWindow(db);
                addPrescriberWindow.show();
            } else {
                addPrescriberWindow.requestFocus();
            }
        });

        addPharmacistButton.setOnAction((event) -> {
            if (addPharmacistWindow == null
                    || !addPharmacistWindow.isShowing()) {
                addPharmacistWindow = new AddPharmacistWindow(db);
                addPharmacistWindow.show();
            } else {
                addPharmacistWindow.requestFocus();
            }
        });

        addPatientButton.setOnAction((event) -> {
            if (addPatientWindow == null
                    || !addPatientWindow.isShowing()) {
                addPatientWindow = new AddPatientWindow(db);
                addPatientWindow.show();
            } else {
                addPatientWindow.requestFocus();
            }
        });

        addDrugButton.setOnAction((event) -> {
            if (addDrugWindow == null
                    || !addDrugWindow.isShowing()) {
                addDrugWindow = new AddDrugWindow(db);
                addDrugWindow.show();
            } else {
                addDrugWindow.requestFocus();
            }
        });

        addSupplierButton.setOnAction((event) -> {
            if (addSupplierWindow == null
                    || !addSupplierWindow.isShowing()) {
                addSupplierWindow = new AddSupplierWindow(db);
                addSupplierWindow.show();
            } else {
                addSupplierWindow.requestFocus();
            }
        });

        supplyToPatientButton.setOnAction((event) -> {
            if (supplyToPatientWindow == null
                    || !supplyToPatientWindow.isShowing()) {
                supplyToPatientWindow = new SupplyToPatientWindow(db);
                supplyToPatientWindow.show();
            } else {
                supplyToPatientWindow.requestFocus();
            }
        });

        receiveFromSupplierButton.setOnAction((event) -> {
            if (receiveFromSupplierWindow == null
                    || !receiveFromSupplierWindow.isShowing()) {
                receiveFromSupplierWindow = new ReceiverFromSupplierWindow(db);
                receiveFromSupplierWindow.show();
            } else {
                receiveFromSupplierWindow.requestFocus();
            }
        });

        searchByDrugButton.setOnAction((event) -> {
            if (searchByDrugWindow == null
                    || !searchByDrugWindow.isShowing()) {
                searchByDrugWindow = new SearchByDrugWindow(db);
                searchByDrugWindow.show();
            } else {
                searchByDrugWindow.requestFocus();
            }
        });

        searchByDateButton.setOnAction((event) -> {
            if (searchByDateWindow == null
                    || !searchByDateWindow.isShowing()) {
                searchByDateWindow = new SearchByDateWindow(db);
                searchByDateWindow.show();
            } else {
                searchByDateWindow.requestFocus();
            }
        });

        stage.show();
    }

    /*
     * Sizes and adds image to button.
     */
    private void addButtonImage(Button button, String url) {
        Image image = new Image(url);
        ImageView imageView = new ImageView(image);
        button.setPrefSize(
                GRAPHIC_BUTTON_WIDTH, GRAPHIC_BUTTON_HEIGHT);
        button.setGraphic(imageView);
        button.setContentDisplay(ContentDisplay.TOP);
    }

    /*
     * Sets up spacing properties of gridpane layout.
     */
    private void setUpSpacing(GridPane gridPane) {
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

    /**
     * Entry point into GUI.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
