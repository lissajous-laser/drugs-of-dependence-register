package com.lissajouslaser;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    static final int NUM_FIELD_WIDTH = 55;
    static final int ERROR_FONT_SIZE = 10;
    static final int LIST_VIEW_WIDTH = 400;
    static final int LIST_VIEW_HEIGHT = 300;
    static final int COMBO_BOX_MIN_WIDTH = 200;

    DatabaseConnection db;

    @Override
    public void start(@SuppressWarnings("exports") Stage s) throws IOException {
        db = new DatabaseConnection();

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
        var nameField = new TextField();
        nameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var nameInvalid = new Text();
        nameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        gridPane.add(namePrompt, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(nameInvalid, 2, 0);

        // Second row of scene elements.
        var strengthPrompt = new Text("Strength");
        var strengthField = new TextField();
        strengthField.setMaxWidth(TEXT_FIELD_WIDTH);
        var strengthInvalid = new Text();
        strengthInvalid.setFont(new Font(ERROR_FONT_SIZE));
        gridPane.add(strengthPrompt, 0, 1);
        gridPane.add(strengthField, 1, 1);
        gridPane.add(strengthInvalid, 2, 1);

        // Third row of scene elements.
        var doseFormPrompt = new Text("Form");
        var doseFormField = new TextField();
        doseFormField.setMaxWidth(TEXT_FIELD_WIDTH);
        var doseFormInvalid = new Text();
        doseFormInvalid.setFont(new Font(ERROR_FONT_SIZE));
        gridPane.add(doseFormPrompt, 0, 2);
        gridPane.add(doseFormField, 1, 2);
        gridPane.add(doseFormInvalid, 2, 2);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
        gridPane.add(buttons, 1, 4);

        // Event handlers.
        nameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();
                String error = Drug.validateName(newValueUpperCase);

                if (error == null) {
                    nameField.setText(newValueUpperCase);
                } else {
                    nameField.setText(oldValue);
                }
            }
        });

        strengthField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();
                String error = Drug.validateStrength(newValueUpperCase);

                if (error == null) {
                    strengthField.setText(newValueUpperCase);
                } else {
                    strengthField.setText(oldValue);
                }
            }
        });

        doseFormField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();
                String error = Drug.validateDoseForm(newValueUpperCase);

                if (error == null) {
                    doseFormField.setText(newValueUpperCase);
                } else {
                    doseFormField.setText(oldValue);
                }
            }
        });

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
                    if (db.addDrug(drug)) {
                        stage.close();
                    } else {
                        openSqlErrorWindow("the entry was not completed.");
                    }
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
        var firstNameField = new TextField();
        firstNameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var firstNameInvalid = new Text();
        firstNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        gridPane.add(firstNamePrompt, 0, 0);
        gridPane.add(firstNameField, 1, 0);
        gridPane.add(firstNameInvalid, 2, 0);

        // Second row of scene elements.
        var lastNamePrompt = new Text("Last name");
        var lastNameField = new TextField();
        lastNameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var lastNameInvalid = new Text();
        lastNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        gridPane.add(lastNamePrompt, 0, 1);
        gridPane.add(lastNameField, 1, 1);
        gridPane.add(lastNameInvalid, 2, 1);

        // Third Row of scene elements.
        var addressPrompt = new Text("Address");
        var addressField = new TextArea();
        addressField.setMaxWidth(TEXT_FIELD_WIDTH);
        addressField.setMaxHeight(TEXT_AREA_HEIGHT);
        addressField.setWrapText(true);
        var addressInvalid = new Text();
        addressInvalid.setFont(new Font(ERROR_FONT_SIZE));
        gridPane.add(addressPrompt, 0, 2);
        gridPane.add(addressField, 1, 2);
        gridPane.add(addressInvalid, 2, 2);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
        gridPane.add(buttons, 1, 4);

        // Event handlers.
        constrainInputFirstNameField(firstNameField);

        constrainInputLastNameField(lastNameField);

        constrainInputAddressField(addressField);

        // Final input validation then send to database.
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
                    if (db.addPatient(patient)) {
                        stage.close();
                    } else {
                        openSqlErrorWindow("the entry was not completed.");
                    }
                    ;
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
        var firstNameField = new TextField();
        firstNameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var firstNameInvalid = new Text();
        firstNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        gridPane.add(firstNamePrompt, 0, 0);
        gridPane.add(firstNameField, 1, 0);
        gridPane.add(firstNameInvalid, 2, 0);

        // Second row of scene elements.
        var lastNamePrompt = new Text("Last name");
        var lastNameField = new TextField();
        lastNameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var lastNameInvalid = new Text();
        lastNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        gridPane.add(lastNamePrompt, 0, 1);
        gridPane.add(lastNameField, 1, 1);
        gridPane.add(lastNameInvalid, 2, 1);

        // Third Row of scene elements.
        var registrationPrompt = new Text("Registration number");
        var registrationField = new TextField();
        registrationField.setMaxWidth(TEXT_FIELD_WIDTH);
        var registrationInvalid = new Text();
        registrationInvalid.setFont(new Font(ERROR_FONT_SIZE));
        gridPane.add(registrationPrompt, 0, 2);
        gridPane.add(registrationField, 1, 2);
        gridPane.add(registrationInvalid, 2, 2);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
        gridPane.add(buttons, 1, 4);

        // EventHandlers.
        constrainInputFirstNameField(firstNameField);

        constrainInputLastNameField(lastNameField);

        registrationField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();
                String error = Pharmacist.validateRegistration(newValueUpperCase);

                if (error == null) {
                    registrationField.setText(newValueUpperCase);
                } else {
                    registrationField.setText(oldValue);
                }
            }
        });

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
                    if (db.addPharmacist(pharmacist)) {
                        stage.close();
                    } else {
                        openSqlErrorWindow("the entry was not completed.");
                    }
                    ;
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
        var firstNameField = new TextField();
        firstNameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var firstNameInvalid = new Text();
        firstNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        gridPane.add(firstNamePrompt, 0, 0);
        gridPane.add(firstNameField, 1, 0);
        gridPane.add(firstNameInvalid, 2, 0);

        // Second row of scene elements.
        var lastNamePrompt = new Text("Last name");
        var lastNameField = new TextField();
        lastNameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var lastNameInvalid = new Text();
        lastNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        gridPane.add(lastNamePrompt, 0, 1);
        gridPane.add(lastNameField, 1, 1);
        gridPane.add(lastNameInvalid, 2, 1);

        // Third row of scene elements.
        var prescriberNumPrompt = new Text("Prescriber number");
        var prescriberNumField = new TextField();
        prescriberNumField.setMaxWidth(TEXT_FIELD_WIDTH);
        var prescriberNumInvalid = new Text();
        prescriberNumInvalid.setFont(new Font(ERROR_FONT_SIZE));
        gridPane.add(prescriberNumPrompt, 0, 2);
        gridPane.add(prescriberNumField, 1, 2);
        gridPane.add(prescriberNumInvalid, 2, 2);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
        gridPane.add(buttons, 1, 4);

        // Event handlers.
        constrainInputFirstNameField(firstNameField);

        constrainInputLastNameField(lastNameField);

        prescriberNumField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();
                String error = Prescriber.validatePrescriberNumber(newValueUpperCase);

                if (error == null) {
                    prescriberNumField.setText(newValueUpperCase);
                } else {
                    prescriberNumField.setText(oldValue);
                }
            }
        });

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
                    if (db.addPrescriber(prescriber)) {
                        stage.close();
                    } else {
                        openSqlErrorWindow("the entry was not completed.");
                    }
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
        var companyNamePrompt = new Text("Company name");
        var companyNameField = new TextField();
        companyNameField.setMaxWidth(TEXT_FIELD_WIDTH);
        var companyNameInvalid = new Text();
        companyNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        gridPane.add(companyNamePrompt, 0, 0);
        gridPane.add(companyNameField, 1, 0);
        gridPane.add(companyNameInvalid, 2, 0);

        // Second Row of scene elements.
        var addressPrompt = new Text("Address");
        var addressField = new TextArea();
        addressField.setMaxWidth(TEXT_FIELD_WIDTH);
        addressField.setMaxHeight(TEXT_AREA_HEIGHT);
        addressField.setWrapText(true);
        var addressInvalid = new Text();
        addressInvalid.setFont(new Font(ERROR_FONT_SIZE));
        gridPane.add(addressPrompt, 0, 1);
        gridPane.add(addressField, 1, 1);
        gridPane.add(addressInvalid, 2, 1);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
        gridPane.add(buttons, 1, 4);

        // Event Handlers.
        companyNameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();
                // validateFirstName() and validateLastName() work the same.
                String error = Supplier.validateName(newValueUpperCase);

                if (error == null) {
                    companyNameField.setText(newValueUpperCase);
                } else {
                    companyNameField.setText(oldValue);
                }
            }
        });

        constrainInputAddressField(addressField);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var supplier = new Supplier(
                        companyNameField.getText(),
                        addressField.getText());

                String[] errors = supplier.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str == null)
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    if (db.addSupplier(supplier)) {
                        stage.close();
                    } else {
                        openSqlErrorWindow("the entry was not completed.");
                    }
                } else {
                    companyNameInvalid.setText(errors[0]);
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
        var patientField = new TextField();
        patientField.setMaxWidth(TEXT_FIELD_SEARCH_WIDTH);
        var patientComboBox = new ComboBox<Patient>();
        patientComboBox.setMinWidth(COMBO_BOX_MIN_WIDTH);
        var patientInvalid = new Text();
        gridPane.add(patientPrompt, 0, 0);
        gridPane.add(patientField, 1, 0);
        gridPane.add(patientComboBox, 2, 0);
        gridPane.add(patientInvalid, 3, 0);

        // Second row of scene elements.
        var drugPrompt = new Text("Drug");
        var drugField = new TextField();
        drugField.setMaxWidth(TEXT_FIELD_SEARCH_WIDTH);
        var drugComboBox = new ComboBox<Drug>();
        drugComboBox.setMinWidth(COMBO_BOX_MIN_WIDTH);
        var drugInvalid = new Text();
        gridPane.add(drugPrompt, 0, 1);
        gridPane.add(drugField, 1, 1);
        gridPane.add(drugComboBox, 2, 1);
        gridPane.add(drugInvalid, 3, 1);

        // Third row of scene elements.
        var balancePrompt = new Text("Balance");
        var balanceField = new Text();
        gridPane.add(balancePrompt, 0, 2);
        gridPane.add(balanceField, 2, 2);

        // Fourth row of scene elements.
        var qtyPrompt = new Text("Quantity");
        var qtyField = new TextField("");
        qtyField.setMaxWidth(NUM_FIELD_WIDTH);
        var qtyInvalid = new Text();
        gridPane.add(qtyPrompt, 0, 3);
        gridPane.add(qtyField, 2, 3);
        gridPane.add(qtyInvalid, 3, 3);

        // Fifth row of scene elements.
        var prescriberPrompt = new Text("Prescriber");
        var prescriberField = new TextField();
        prescriberField.setMaxWidth(TEXT_FIELD_SEARCH_WIDTH);
        var prescriberComboBox = new ComboBox<Prescriber>();
        prescriberComboBox.setMinWidth(COMBO_BOX_MIN_WIDTH);
        var prescriberInvalid = new Text();
        gridPane.add(prescriberPrompt, 0, 4);
        gridPane.add(prescriberField, 1, 4);
        gridPane.add(prescriberComboBox, 2, 4);
        gridPane.add(prescriberInvalid, 3, 4);

        // Sixth row of scene elements.
        var pharmacistPrompt = new Text("Pharmacist");
        var pharmacistField = new TextField();
        pharmacistField.setMaxWidth(TEXT_FIELD_SEARCH_WIDTH);
        var pharmacistComboBox = new ComboBox<Pharmacist>();
        pharmacistComboBox.setMinWidth(COMBO_BOX_MIN_WIDTH);
        var pharmacistInvalid = new Text();
        gridPane.add(pharmacistPrompt, 0, 5);
        gridPane.add(pharmacistField, 1, 5);
        gridPane.add(pharmacistComboBox, 2, 5);
        gridPane.add(pharmacistInvalid, 3, 5);

        // Seventh row of scene elements.
        var scriptNumPrompt = new Text("Script Num");
        var scriptNumField = new TextField();
        var scriptNumInvalid = new Text();
        gridPane.add(scriptNumPrompt, 0, 6);
        gridPane.add(scriptNumField, 2, 6);
        gridPane.add(scriptNumInvalid, 3, 6);

        // Eighth row of scene elements.
        var notesPrompt = new Text("Notes");
        var notesField = new TextField();
        var notesInvalid = new Text();
        gridPane.add(notesPrompt, 0, 7);
        gridPane.add(notesField, 2, 7);
        gridPane.add(notesInvalid, 3, 7);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
        gridPane.add(buttons, 2, 8);

        // Event handlers.
        patientField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                patientField.setText(newValue.toUpperCase());

                List<Patient> list = db.getPatientsList(patientField.getText());
                patientComboBox.setItems(FXCollections.observableArrayList(list));

                patientComboBox.show();
            }
        });

        drugField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                drugField.setText(newValue.toUpperCase());

                List<Drug> list = db.getDrugsList(drugField.getText());
                drugComboBox.setItems(FXCollections.observableArrayList(list));

                drugComboBox.show();
            }
        });

        prescriberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                prescriberField.setText(newValue.toUpperCase());

                List<Prescriber> list = db.getPrescribersList(prescriberField.getText());
                prescriberComboBox.setItems(FXCollections.observableArrayList(list));

                prescriberComboBox.show();
            }
        });

        pharmacistField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                pharmacistField.setText(newValue.toUpperCase());

                List<Pharmacist> list = db.getPharmacistsList(pharmacistField.getText());
                pharmacistComboBox.setItems(FXCollections.observableArrayList(list));

                pharmacistComboBox.show();
            }
        });

        drugComboBox.setOnKeyReleased(event -> {
            Drug selectedDrug = drugComboBox.getValue();

            if (selectedDrug != null) {
                balanceField.setText(String.valueOf(db.getBalance(selectedDrug.getId())));
            }
            // System.out.println(drugComboBox.getValue());
            System.out.println(drugComboBox.getSelectionModel().getSelectedIndex());
        });

        qtyField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String error = Transfer.validateQty(newValue);

                if (error == null) {
                    qtyField.setText(newValue);
                } else {
                    qtyField.setText(oldValue);
                }
            }
        });

        scriptNumField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();
                String error = Transfer.validateReference(newValueUpperCase);

                if (error == null) {
                    scriptNumField.setText(newValueUpperCase);
                } else {
                    scriptNumField.setText(oldValue);
                }
            }
        });

        notesField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String error = Transfer.validateNotes(newValue);

                if (error == null) {
                    notesField.setText(newValue);
                } else {
                    notesField.setText(oldValue);
                }
            }
        });

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
                    if (db.addSupplyEntry(transfer)) {
                        stage.close();
                    } else {
                        openSqlErrorWindow("the entry was not completed.");
                    }
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
        var supplierField = new TextField();
        supplierField.setMaxWidth(TEXT_FIELD_SEARCH_WIDTH);
        var supplierComboBox = new ComboBox<Supplier>();
        supplierComboBox.setMinWidth(COMBO_BOX_MIN_WIDTH);
        var supplierInvalid = new Text();
        gridPane.add(supplierPrompt, 0, 0);
        gridPane.add(supplierField, 1, 0);
        gridPane.add(supplierComboBox, 2, 0);
        gridPane.add(supplierInvalid, 3, 0);

        // Second row of scene elements.
        var drugPrompt = new Text("Drug");
        var drugField = new TextField();
        drugField.setMaxWidth(TEXT_FIELD_SEARCH_WIDTH);
        var drugComboBox = new ComboBox<Drug>();
        drugComboBox.setMinWidth(COMBO_BOX_MIN_WIDTH);
        var drugInvalid = new Text();
        gridPane.add(drugPrompt, 0, 1);
        gridPane.add(drugField, 1, 1);
        gridPane.add(drugComboBox, 2, 1);
        gridPane.add(drugInvalid, 3, 1);

        // Third row of scene elements.
        var balancePrompt = new Text("Balance");
        var balanceField = new Text();
        gridPane.add(balancePrompt, 0, 2);
        gridPane.add(balanceField, 2, 2);

        // Fourth row of scene elements.
        var qtyPrompt = new Text("Quantity");
        var qtyField = new TextField();
        qtyField.setMaxWidth(NUM_FIELD_WIDTH);
        var qtyInvalid = new Text();
        gridPane.add(qtyPrompt, 0, 3);
        gridPane.add(qtyField, 2, 3);
        gridPane.add(qtyInvalid, 3, 3);

        // Fifth row of scene elements.
        var pharmacistPrompt = new Text("Pharmacist");
        var pharmacistField = new TextField();
        pharmacistField.setMaxWidth(TEXT_FIELD_SEARCH_WIDTH);
        var pharmacistComboBox = new ComboBox<Pharmacist>();
        pharmacistComboBox.setMinWidth(COMBO_BOX_MIN_WIDTH);
        var pharmacistInvalid = new Text();
        gridPane.add(pharmacistPrompt, 0, 4);
        gridPane.add(pharmacistField, 1, 4);
        gridPane.add(pharmacistComboBox, 2, 4);
        gridPane.add(pharmacistInvalid, 3, 4);

        // Sixth row of scene elements.
        var invoicePrompt = new Text("Invoice Num");
        var invoiceField = new TextField();
        var invoiceInvalid = new Text();
        gridPane.add(invoicePrompt, 0, 5);
        gridPane.add(invoiceField, 2, 5);
        gridPane.add(invoiceInvalid, 3, 5);

        // Seventh row of scene elements.
        var notesPrompt = new Text("Notes");
        var notesField = new TextField();
        var notesInvalid = new Text();
        gridPane.add(notesPrompt, 0, 6);
        gridPane.add(notesField, 2, 6);
        gridPane.add(notesInvalid, 3, 6);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
        gridPane.add(buttons, 2, 7);

        // Event handlers.
        supplierField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                supplierField.setText(newValue.toUpperCase());

                List<Supplier> list = db.getSuppliersList(supplierField.getText());
                supplierComboBox.setItems(FXCollections.observableArrayList(list));

                supplierComboBox.show();
            }
        });

        drugField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                drugField.setText(newValue.toUpperCase());

                List<Drug> list = db.getDrugsList(drugField.getText());
                drugComboBox.setItems(FXCollections.observableArrayList(list));

                drugComboBox.show();
            }
        });

        pharmacistField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                pharmacistField.setText(newValue.toUpperCase());

                List<Pharmacist> list = db.getPharmacistsList(pharmacistField.getText());
                pharmacistComboBox.setItems(FXCollections.observableArrayList(list));

                pharmacistComboBox.show();
            }
        });

        drugComboBox.setOnKeyReleased(event -> {
            Drug selectedDrug = drugComboBox.getValue();

            if (selectedDrug != null) {
                balanceField.setText(String.valueOf(db.getBalance(selectedDrug.getId())));
            }
            // System.out.println(drugComboBox.getValue());
            System.out.println(drugComboBox.getSelectionModel().getSelectedIndex());
        });

        qtyField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String error = Transfer.validateQty(newValue);

                if (error == null) {
                    qtyField.setText(newValue);
                } else {
                    qtyField.setText(oldValue);
                }
            }
        });

        invoiceField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();
                String error = Transfer.validateReference(newValueUpperCase);

                if (error == null) {
                    invoiceField.setText(newValueUpperCase);
                } else {
                    invoiceField.setText(oldValue);
                }
            }
        });

        notesField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String error = Transfer.validateNotes(newValue);

                if (error == null) {
                    notesField.setText(newValue);
                } else {
                    notesField.setText(oldValue);
                }
            }
        });

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
                    if (db.addReceiveEntry(transfer)) {
                        stage.close();
                    } else {
                        openSqlErrorWindow("the entry was not completed.");
                    }
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

    /**
     * A window displaying an error message
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
     * Constrains input for a first name TextField.
     */
    private void constrainInputFirstNameField(TextField nameField) {
        nameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();

                String error = Person.validateFirstName(newValueUpperCase);

                if (error == null) {
                    nameField.setText(newValueUpperCase);
                } else {
                    nameField.setText(oldValue);
                }
            }
        });
    }

    /*
     * Constrains input for a last name TextField.
     */
    private void constrainInputLastNameField(TextField nameField) {
        nameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();

                String error = Person.validateLastName(newValueUpperCase);

                if (error == null) {
                    nameField.setText(newValueUpperCase);
                } else {
                    nameField.setText(oldValue);
                }
            }
        });
    }

    /*
     * Constrains input for an addressField TextArea.
     */
    private void constrainInputAddressField(TextArea addressField) {
        addressField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();
                String error = IAgent.validateAddress(newValueUpperCase);

                if (error == null) {
                    addressField.setText(newValueUpperCase);
                } else {
                    addressField.setText(oldValue);
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
