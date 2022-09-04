package com.lissajouslaser;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.lissajouslaser.control.AddressArea;
import com.lissajouslaser.control.DoseFormField;
import com.lissajouslaser.control.DrugField;
import com.lissajouslaser.control.DrugNameField;
import com.lissajouslaser.control.FirstNameField;
import com.lissajouslaser.control.LastNameField;
import com.lissajouslaser.control.NotesField;
import com.lissajouslaser.control.PatientField;
import com.lissajouslaser.control.PharmacistField;
import com.lissajouslaser.control.PrescriberField;
import com.lissajouslaser.control.PrescriberNumField;
import com.lissajouslaser.control.QtyField;
import com.lissajouslaser.control.ReferenceField;
import com.lissajouslaser.control.RegistrationField;
import com.lissajouslaser.control.SearchField;
import com.lissajouslaser.control.StrengthField;
import com.lissajouslaser.control.SupplierField;
import com.lissajouslaser.control.SupplierNameField;
import com.lissajouslaser.control.ValidatableField;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class for starting GUI application.
 */
public class MainApp extends Application {
    static final int SUBWINDOW_A_WIDTH = 540;
    static final int SUBWINDOW_A_HEIGHT = 240;
    static final int SUBWINDOW_B_WIDTH = 640;
    static final int SUBWINDOW_B_HEIGHT = 320;
    static final int ERROR_WINDOW_HEIGHT = 120;
    static final int ERROR_WINDOW_WIDTH = 180;
    static final int PADDING_SPACE = 15;
    static final int GAP_SPACE = 7;
    static final int TEXT_FIELD_WIDTH = 300;
    static final int TEXT_AREA_HEIGHT = 60;
    static final int TEXT_FIELD_SEARCH_WIDTH = 70;
    static final int NUM_FIELD_WIDTH = 60;
    static final int ERROR_FONT_SIZE = 10;
    static final int LIST_VIEW_WIDTH = 400;
    static final int LIST_VIEW_HEIGHT = 300;
    static final int COMBO_BOX_MIN_WIDTH = 200;

    DatabaseConnection db;

    @Override
    public void start(Stage s) {
        try {
            db = new DatabaseConnection();
        } catch (SQLException e) {
            openSqlErrorWindow("could not start database.");
        }

        final int mainWindowWidth = 600;
        final int mainWindowHeight = 350;

        s.setScene(new Scene(
                mainWindow(),
                mainWindowWidth,
                mainWindowHeight));
        s.show();
    }

    private GridPane mainWindow() {
        GridPane mainWindowGrid = new GridPane();
        mainWindowGrid.setHgap(GAP_SPACE);
        mainWindowGrid.setVgap(GAP_SPACE);

        mainWindowGrid.setPadding(new Insets(
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE));

        var addPatientButton = new Button("Add Patient");
        var addPrescriberButton = new Button("Add Prescriber");
        var addDrugButton = new Button("Add Drug");
        var addPharmacistButton = new Button("Add Pharmacist");
        var supplyToPatientButton = new Button("Supply to Patient");
        var receiveFromSupplierButton = new Button("Receive from Supplier");
        var addSupplierButton = new Button("Add supplier");

        mainWindowGrid.add(addPatientButton, 0, 0);
        mainWindowGrid.add(addPrescriberButton, 1, 0);
        mainWindowGrid.add(addDrugButton, 2, 0);
        mainWindowGrid.add(addPharmacistButton, 3, 0);
        mainWindowGrid.add(supplyToPatientButton, 0, 1);
        mainWindowGrid.add(receiveFromSupplierButton, 1, 1);
        mainWindowGrid.add(addSupplierButton, 2, 1);

        addPrescriberButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWindowAddPrescriber();
            }
        });

        addPharmacistButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWindowAddPharmacist();
            }
        });

        addPatientButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWindowAddPatient();
            }
        });

        addDrugButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWindowAddDrug();
            }
        });

        supplyToPatientButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWindowSupplyToPatient();
            }
        });

        receiveFromSupplierButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWindowReceiveFromSupplier();
            }
        });

        receiveFromSupplierButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWindowReceiveFromSupplier();
            }
        });

        addSupplierButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWindowAddSupplier();
            }
        });

        return mainWindowGrid;
    }

    /**
     * The Add Drug window, the UI for adding
     * a drug to the database.
     */
    private void openWindowAddDrug() {

        // Root layout.
        GridPane gridPane = new GridPane();
        setUpLayout(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Drug");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var namePrompt = new Text("Name");
        var nameField = new DrugNameField();
        nameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var nameInvalid = new Text();
        nameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(gridPane, namePrompt, nameField, nameInvalid);

        // Second row of scene elements.
        var strengthPrompt = new Text("Strength");
        var strengthField = new StrengthField();
        strengthField.setMaxWidth(TEXT_FIELD_WIDTH);
        var strengthInvalid = new Text();
        strengthInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(gridPane, strengthPrompt, strengthField, strengthInvalid);

        // Third row of scene elements.
        var doseFormPrompt = new Text("Form");
        var doseFormField = new DoseFormField();
        doseFormField.setMaxWidth(TEXT_FIELD_WIDTH);
        var doseFormInvalid = new Text();
        doseFormInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(gridPane, doseFormPrompt, doseFormField, doseFormInvalid);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
        addToGrid(gridPane, null, buttons);

        // Event handlers.
        constrainInput(nameField);

        constrainInput(strengthField);

        constrainInput(doseFormField);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var drug = new Drug(
                        nameField.getText(),
                        strengthField.getText(),
                        doseFormField.getText());

                String[] errors = drug.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str == null)
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addDrug(drug);
                    } catch (SQLException e) {
                        openSqlErrorWindow("the entry was not completed.");
                    }
                    stage.close();
                } else {
                    nameInvalid.setText(errors[0]);
                    strengthInvalid.setText(errors[1]);
                    doseFormInvalid.setText(errors[2]);
                }
            }
        });

        closeOnButtonPress(cancel, stage);

        stage.show();
    }

    /**
     * The Add Patient window, the UI for adding
     * a patient to the database.
     */
    private void openWindowAddPatient() {

        // Root layout.
        GridPane gridPane = new GridPane();
        setUpLayout(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Patient");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var firstNamePrompt = new Text("First name");
        var firstNameField = new FirstNameField();
        firstNameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var firstNameInvalid = new Text();
        firstNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(gridPane, firstNamePrompt, firstNameField, firstNameInvalid);

        // Second row of scene elements.
        var lastNamePrompt = new Text("Last name");
        var lastNameField = new LastNameField();
        lastNameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var lastNameInvalid = new Text();
        lastNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(gridPane, lastNamePrompt, lastNameField, lastNameInvalid);

        // Third Row of scene elements.
        var addressPrompt = new Text("Address");
        var addressField = new AddressArea();
        addressField.setMaxWidth(TEXT_FIELD_WIDTH);
        addressField.setMaxHeight(TEXT_AREA_HEIGHT);
        addressField.setWrapText(true);
        var addressInvalid = new Text();
        addressInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(gridPane, addressPrompt, addressField, addressInvalid);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
        addToGrid(gridPane, null, buttons);

        // Event handlers.
        constrainInput(firstNameField);

        constrainInput(lastNameField);

        constrainInput(addressField);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var patient = new Patient(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        addressField.getText());
                ;
                String[] errors = patient.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str == null)
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addPatient(patient);
                    } catch (SQLException e) {
                        openSqlErrorWindow("the entry was not completed.");                       
                    }
                    stage.close();
                } else {
                    firstNameInvalid.setText(errors[0]);
                    lastNameInvalid.setText(errors[1]);
                    addressInvalid.setText(errors[2]);
                }
            }
        });

        closeOnButtonPress(cancel, stage);

        stage.show();
    }

    /**
     * The Add Pharmacist window, the UI for adding
     * a pharmacist to the database.
     */
    private void openWindowAddPharmacist() {

        // Root layout.
        GridPane gridPane = new GridPane();
        setUpLayout(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Pharmacist");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var firstNamePrompt = new Text("First name");
        var firstNameField = new FirstNameField();
        firstNameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var firstNameInvalid = new Text();
        firstNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(gridPane, firstNamePrompt, firstNameField, firstNameInvalid);

        // Second row of scene elements.
        var lastNamePrompt = new Text("Last name");
        var lastNameField = new LastNameField();
        lastNameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var lastNameInvalid = new Text();
        lastNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(gridPane, lastNamePrompt, lastNameField, lastNameInvalid);

        // Third Row of scene elements.
        var registrationPrompt = new Text("Registration number");
        var registrationField = new RegistrationField();
        registrationField.setMaxWidth(TEXT_FIELD_WIDTH);
        var registrationInvalid = new Text();
        registrationInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(
                gridPane,
                registrationPrompt,
                registrationField,
                registrationInvalid);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
        addToGrid(gridPane, null, buttons);

        // EventHandlers.
        constrainInput(firstNameField);

        constrainInput(lastNameField);

        constrainInput(registrationField);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var pharmacist = new Pharmacist(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        registrationField.getText());
                ;
                String[] errors = pharmacist.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str == null)
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addPharmacist(pharmacist);
                    } catch (SQLException e) {
                        openSqlErrorWindow("the entry was not completed.");
                    }
                    stage.close();
                } else {
                    firstNameInvalid.setText(errors[0]);
                    lastNameInvalid.setText(errors[1]);
                    registrationInvalid.setText(errors[2]);
                }
            }
        });

        closeOnButtonPress(cancel, stage);

        stage.show();
    }

    /**
     * The Add Prescriber window, the UI for adding
     * a prescriber to the database.
     */
    private void openWindowAddPrescriber() {

        // Root layout.
        GridPane gridPane = new GridPane();
        setUpLayout(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Prescriber");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var firstNamePrompt = new Text("First name");
        var firstNameField = new FirstNameField();
        firstNameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var firstNameInvalid = new Text();
        firstNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(gridPane, firstNamePrompt, firstNameField, firstNameInvalid);

        // Second row of scene elements.
        var lastNamePrompt = new Text("Last name");
        var lastNameField = new LastNameField();
        lastNameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var lastNameInvalid = new Text();
        lastNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(gridPane, lastNamePrompt, lastNameField, lastNameInvalid);

        // Third row of scene elements.
        var prescriberNumPrompt = new Text("Prescriber number");
        var prescriberNumField = new PrescriberNumField();
        prescriberNumField.setMaxWidth(TEXT_FIELD_WIDTH);
        var prescriberNumInvalid = new Text();
        prescriberNumInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(
                gridPane,
                prescriberNumPrompt,
                prescriberNumField,
                prescriberNumInvalid);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
        addToGrid(gridPane, null, buttons);

        // Event handlers.
        constrainInput(firstNameField);

        constrainInput(lastNameField);

        constrainInput(prescriberNumField);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var prescriber = new Prescriber(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        prescriberNumField.getText());
                ;
                String[] errors = prescriber.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str == null)
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addPrescriber(prescriber);
                    } catch (SQLException e) {
                        openSqlErrorWindow("the entry was not completed.");
                    }
                    stage.close();
                } else {
                    // Create messages for invalid field entries.
                    firstNameInvalid.setText(errors[0]);
                    lastNameInvalid.setText(errors[1]);
                    prescriberNumInvalid.setText(errors[2]);
                }
            }
        });

        closeOnButtonPress(cancel, stage);

        stage.show();
    }

    /**
     * The Add Patient window, the UI for adding
     * a supplier to the database.
     */
    private void openWindowAddSupplier() {

        // Root layout.
        GridPane gridPane = new GridPane();
        setUpLayout(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Supplier");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var supplierNamePrompt = new Text("Supplier name");
        var supplierNameField = new SupplierNameField();
        supplierNameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var supplierNameInvalid = new Text();
        supplierNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(
                gridPane,
                supplierNamePrompt,
                supplierNameField,
                supplierNameInvalid);

        // Second Row of scene elements.
        var addressPrompt = new Text("Address");
        var addressField = new AddressArea();
        addressField.setMaxWidth(TEXT_FIELD_WIDTH);
        addressField.setMaxHeight(TEXT_AREA_HEIGHT);
        addressField.setWrapText(true);
        var addressInvalid = new Text();
        addressInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(gridPane, addressPrompt, addressField, addressInvalid);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
        addToGrid(gridPane, null, buttons);

        // Event Handlers.
        constrainInput(supplierNameField);

        constrainInput(addressField);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var supplier = new Supplier(
                        supplierNameField.getText(),
                        addressField.getText());

                String[] errors = supplier.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str == null)
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addSupplier(supplier);
                    } catch (SQLException e) {
                        openSqlErrorWindow("the entry was not completed.");
                    }
                    stage.close();
                } else {
                    supplierNameInvalid.setText(errors[0]);
                    addressInvalid.setText(errors[1]);
                }
            }
        });        

        closeOnButtonPress(cancel, stage);

        stage.show();
    }

    /*
     * The Supply to Patient window, the UI for adding
     * a supply to a patient to the database.
     */
    private void openWindowSupplyToPatient() {

        // Root layout.
        GridPane gridPane = new GridPane();
        setUpLayout(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Supply to Patient");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_B_WIDTH, SUBWINDOW_B_HEIGHT));

        // First row of scene elements.
        var patientPrompt = new Text("Patient");
        var patientField = new PatientField();
        patientField.setMaxWidth(TEXT_FIELD_SEARCH_WIDTH);
        var patientComboBox = new ComboBox<Patient>();
        patientComboBox.setMinWidth(COMBO_BOX_MIN_WIDTH);
        var patientInvalid = new Text();
        addToGrid(
            gridPane,
            patientPrompt,
            patientField,
            patientComboBox,
            patientInvalid);


        // Second row of scene elements.
        var drugPrompt = new Text("Drug");
        var drugField = new DrugField();
        drugField.setMaxWidth(TEXT_FIELD_SEARCH_WIDTH);
        var drugComboBox = new ComboBox<Drug>();
        drugComboBox.setMinWidth(COMBO_BOX_MIN_WIDTH);
        var drugInvalid = new Text();
        addToGrid(gridPane, drugPrompt, drugField, drugComboBox, drugInvalid);

        // Third row of scene elements.
        var balancePrompt = new Text("Balance");
        var balanceField = new Text();
        addToGrid(gridPane, balancePrompt, null, balanceField);

        // Fourth row of scene elements.
        var qtyPrompt = new Text("Quantity");
        var qtyField = new QtyField();
        qtyField.setMaxWidth(NUM_FIELD_WIDTH);
        var qtyInvalid = new Text();
        addToGrid(gridPane, qtyPrompt, null, qtyField, qtyInvalid);

        // Fifth row of scene elements.
        var prescriberPrompt = new Text("Prescriber");
        var prescriberField = new PrescriberField();
        prescriberField.setMaxWidth(TEXT_FIELD_SEARCH_WIDTH);
        var prescriberComboBox = new ComboBox<Prescriber>();
        prescriberComboBox.setMinWidth(COMBO_BOX_MIN_WIDTH);
        var prescriberInvalid = new Text();
        addToGrid(
                gridPane,
                prescriberPrompt,
                prescriberField,
                prescriberComboBox,
                prescriberInvalid);

        // Sixth row of scene elements.
        var pharmacistPrompt = new Text("Pharmacist");
        var pharmacistField = new PharmacistField();
        pharmacistField.setMaxWidth(TEXT_FIELD_SEARCH_WIDTH);
        var pharmacistComboBox = new ComboBox<Pharmacist>();
        pharmacistComboBox.setMinWidth(COMBO_BOX_MIN_WIDTH);
        var pharmacistInvalid = new Text();
        addToGrid(
                gridPane,
                pharmacistPrompt,
                pharmacistField,
                pharmacistComboBox,
                pharmacistInvalid);

        // Seventh row of scene elements.
        var scriptNumPrompt = new Text("Script Num");
        var scriptNumField = new ReferenceField();
        var scriptNumInvalid = new Text();
        addToGrid(gridPane,
                scriptNumPrompt,
                null,
                scriptNumField,
                scriptNumInvalid);

        // Eighth row of scene elements.
        var notesPrompt = new Text("Notes");
        var notesField = new NotesField();
        var notesInvalid = new Text();
        addToGrid(gridPane, notesPrompt, null, notesField, notesInvalid);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
        addToGrid(gridPane, null, null, buttons);

        // Event handlers.
        updateComboBox(patientField, patientComboBox);

        updateComboBox(drugField, drugComboBox);

        updateComboBox(prescriberField, prescriberComboBox);

        updateComboBox(pharmacistField, pharmacistComboBox);

        drugComboBox.setOnAction(event -> {
            Drug selectedDrug = drugComboBox.getValue();

            try {
                if (selectedDrug != null) {
                    balanceField.setText(String.valueOf(
                            db.getBalance(selectedDrug.getId())));
                }
            } catch (SQLException e) {
                openSqlErrorWindow("data could not be retrieved.");
            }
        });

        constrainInput(qtyField);

        constrainInput(scriptNumField);

        constrainInput(notesField);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                var transfer = new Transfer(
                        patientComboBox.getValue(),
                        drugComboBox.getValue(),
                        balanceField.getText(),
                        qtyField.getText(),
                        prescriberComboBox.getValue(),
                        scriptNumField.getText(),
                        notesField.getText(),
                        pharmacistComboBox.getValue());
                String[] errors = transfer.validateSupplyToPatient();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str == null)
                        .reduce(true, (x, y) -> x && y);

                if (isNoErrors) {
                    try {
                        db.addSupplyEntry(transfer);
                    } catch (SQLException e) {
                        openSqlErrorWindow("the entry was not completed.");
                    }
                    stage.close();
                } else {
                    // Create messages for invalid entries.
                    patientInvalid.setText(errors[0]);
                    drugInvalid.setText(errors[1]);
                    qtyInvalid.setText(errors[2]);
                    prescriberInvalid.setText(errors[3]);
                    scriptNumInvalid.setText(errors[4]);
                    notesInvalid.setText(errors[5]);
                    pharmacistInvalid.setText(errors[6]);
                }
            }
        });

        closeOnButtonPress(cancel, stage);

        stage.show();
    }

    /*
     * The Receive from Supplier window, the UI for adding
     * a receipt of order from supplier to the database.
     */
    private void openWindowReceiveFromSupplier() {

        // Root layout.
        GridPane gridPane = new GridPane();
        setUpLayout(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Receive from Supplier");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_B_WIDTH, SUBWINDOW_B_HEIGHT));

        // First row of scene elements.
        var supplierPrompt = new Text("Supplier");
        var supplierField = new SupplierField();
        supplierField.setMaxWidth(TEXT_FIELD_SEARCH_WIDTH);
        var supplierComboBox = new ComboBox<Supplier>();
        supplierComboBox.setMinWidth(COMBO_BOX_MIN_WIDTH);
        var supplierInvalid = new Text();
        addToGrid(
                gridPane,
                supplierPrompt,
                supplierField,
                supplierComboBox,
                supplierInvalid);

        // Second row of scene elements.
        var drugPrompt = new Text("Drug");
        var drugField = new DrugField();
        drugField.setMaxWidth(TEXT_FIELD_SEARCH_WIDTH);
        var drugComboBox = new ComboBox<Drug>();
        drugComboBox.setMinWidth(COMBO_BOX_MIN_WIDTH);
        var drugInvalid = new Text();
        addToGrid(gridPane, drugPrompt, drugField, drugComboBox, drugInvalid);

        // Third row of scene elements.
        var balancePrompt = new Text("Balance");
        var balanceField = new Text();
        addToGrid(gridPane, balancePrompt, null, balanceField);

        // Fourth row of scene elements.
        var qtyPrompt = new Text("Quantity");
        var qtyField = new QtyField();
        qtyField.setMaxWidth(NUM_FIELD_WIDTH);
        var qtyInvalid = new Text();
        addToGrid(gridPane, qtyPrompt, null, qtyField, qtyInvalid);

        // Fifth row of scene elements.
        var pharmacistPrompt = new Text("Pharmacist");
        var pharmacistField = new PharmacistField();
        pharmacistField.setMaxWidth(TEXT_FIELD_SEARCH_WIDTH);
        var pharmacistComboBox = new ComboBox<Pharmacist>();
        pharmacistComboBox.setMinWidth(COMBO_BOX_MIN_WIDTH);
        var pharmacistInvalid = new Text();
        addToGrid(
                gridPane,
                pharmacistPrompt,
                pharmacistField,
                pharmacistComboBox,
                pharmacistInvalid);

        // Sixth row of scene elements.
        var invoicePrompt = new Text("Invoice Num");
        var invoiceField = new ReferenceField();
        var invoiceInvalid = new Text();
        addToGrid(gridPane, invoicePrompt, null, invoiceField, invoiceInvalid);

        // Seventh row of scene elements.
        var notesPrompt = new Text("Notes");
        var notesField = new NotesField();
        var notesInvalid = new Text();
        addToGrid(gridPane, notesPrompt, null, notesField, notesInvalid);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
        addToGrid(gridPane, null, null, buttons);

        // Event handlers.
        updateComboBox(supplierField, supplierComboBox);

        updateComboBox(drugField, drugComboBox);

        updateComboBox(pharmacistField, pharmacistComboBox);

        drugComboBox.setOnAction(event -> {
            Drug selectedDrug = drugComboBox.getValue();

            try {
                if (selectedDrug != null) {
                    balanceField.setText(String.valueOf(
                            db.getBalance(selectedDrug.getId())));
                }
            } catch (SQLException e) {
                openSqlErrorWindow("data could not be retrieved.");
            }
        });

        constrainInput(qtyField);

        constrainInput(invoiceField);

        constrainInput(notesField);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                var transfer = new Transfer(
                        supplierComboBox.getValue(),
                        drugComboBox.getValue(),
                        balanceField.getText(),
                        qtyField.getText(),
                        null,
                        invoiceField.getText(),
                        notesField.getText(),
                        pharmacistComboBox.getValue());
                String[] errors = transfer.validateReceiveFromSupplier();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str == null)
                        .reduce(true, (x, y) -> x && y);

                if (isNoErrors) {
                    try {
                        db.addReceiveEntry(transfer);
                    } catch (SQLException e) {
                        openSqlErrorWindow("the entry was not completed.");
                    }
                    stage.close();
                } else {
                    // Create messages for invalid entries.
                    supplierInvalid.setText(errors[0]);
                    drugInvalid.setText(errors[1]);
                    qtyInvalid.setText(errors[2]);
                    invoiceInvalid.setText(errors[4]);
                    notesInvalid.setText(errors[5]);
                    pharmacistInvalid.setText(errors[6]);
                }
            }
        });

        closeOnButtonPress(cancel, stage);

        stage.show();
    }

    /*
     * Adds listed child nodes to the GridPane on the next row.
     * Any nulls in children will cause the column to be skipped
     * over.
     */
    private void addToGrid(GridPane gridPane, Node ... children) {
        int rowIndex = gridPane.getRowCount();

        for (int i = 0; i < children.length; i++) {
            Node child = children[i];
            if (child != null) {
                gridPane.add(child, i, rowIndex);
            }
        }
    }

    /*
     * A window displaying an error message. Used for
     * SQLExceptions.
     */
    private void openSqlErrorWindow(String msg) {
        // Root layout.
        GridPane gridPane = new GridPane();
        setUpLayout(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Database Error");
        stage.setScene(new Scene(
                gridPane, ERROR_WINDOW_WIDTH, ERROR_WINDOW_HEIGHT));

        var messageText = new Text(msg);
        var ok = new Button("An error occurred with the database,\n"
                + msg);
        gridPane.add(messageText, 0, 0);
        gridPane.add(ok, 0, 1);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        stage.show();
    }

    /*
     * Sets up functionality of cancel button.
     */
    private void closeOnButtonPress(Button cancel, Stage stage) {
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
    }

    /*
     * Sets up spacing properties of gridpane layout.
     */
    private void setUpLayout(GridPane gridPane) {
        gridPane.setHgap(GAP_SPACE);
        gridPane.setVgap(GAP_SPACE);
        gridPane.setPadding(new Insets(
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE));
    }

    /*
     * Updates the list in a ComboBox according to the input of
     * a TextField.
     */
    private <T> void updateComboBox(SearchField<T> field, ComboBox<T> comboBox) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                    field.setText(newValue.toUpperCase());

                try {
                    List<T> list = field.getList(db);
                    comboBox.setItems(
                            FXCollections.observableArrayList(list));

                    comboBox.show();
                } catch (SQLException e) {
                    openSqlErrorWindow("data could not be retrieved.");
                }
            }
        });
    }

    /*
     * Constrains input of a TextControl.
     */
    private void constrainInput(ValidatableField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();

                boolean fieldValid = field.validate(newValueUpperCase);

                if (fieldValid) {
                    field.setText(newValueUpperCase);
                } else {
                    field.setText(oldValue);
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
