package com.lissajouslaser;

import com.lissajouslaser.control.AddressAreaAndValidation;
import com.lissajouslaser.control.ComboBoxAndValidation;
import com.lissajouslaser.control.DoseFormField;
import com.lissajouslaser.control.DrugField;
import com.lissajouslaser.control.DrugNameField;
import com.lissajouslaser.control.FieldAndAllCapValidation;
import com.lissajouslaser.control.FieldAndValidation;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    static final int TEXT_FIELD_WIDTH = 250;
    static final int TEXT_AREA_HEIGHT = 60;
    static final int TEXT_FIELD_SEARCH_WIDTH = 70;
    static final int NUM_FIELD_WIDTH = 70;
    static final int ERROR_FONT_SIZE = 11;
    static final int COMBO_BOX_WIDTH = 320;
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
            createAndOpenSqlErrorWindow(e.toString());
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
                "file:SupplyToPatient.png");

        var receiveFromSupplierButton = new Button("Receive from Supplier");
        addButtonImage(
                receiveFromSupplierButton,
                "file:ReceiveFromSupplier.png");

        var searchByDrugButton = new Button("Search by Drug");
        addButtonImage(
                searchByDrugButton,
                "file:SearchByDrug.png");

        var searchByDateButton = new Button("Search by Date");
        addButtonImage(
                searchByDateButton,
                "file:SearchByDate.png");

        var addPatientButton = new Button("Add Patient");
        addButtonImage(
                addPatientButton,
                "file:Patient.png");

        var addPrescriberButton = new Button("Add Prescriber");
        addButtonImage(
                addPrescriberButton,
                "file:Doctor.png");

        var addPharmacistButton = new Button("Add Pharmacist");
        addButtonImage(
                addPharmacistButton,
                "file:Pharmacist.png");

        var addDrugButton = new Button("Add Drug");
        addButtonImage(
                addDrugButton,
                "file:Drug.png");

        var addSupplierButton = new Button("Add Supplier");
        addButtonImage(
                addSupplierButton,
                "file:Supplier.png");

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

        addPrescriberWindow = createAddPrescriberWindow();
        addPharmacistWindow = createAddPharmacistWindow();
        addPatientWindow = createAddPatientWindow();
        addDrugWindow = createAddDrugWindow();
        addSupplierWindow = createAddSupplierWindow();
        supplyToPatientWindow = createSupplyToPatientWindow();
        receiveFromSupplierWindow = createReceiveFromSupplierWindow();
        searchByDrugWindow = createSearchByDrugWindow();
        searchByDateWindow = createSearchByDateWindow();

        // Event handlers.
        addPrescriberButton.setOnAction((event) -> {
            if (addPrescriberWindow.isShowing()) {
                addPrescriberWindow.requestFocus();
            } else {
                addPrescriberWindow.show();
            }
        });

        addPharmacistButton.setOnAction((event) -> {
            if (addPharmacistWindow.isShowing()) {
                addPharmacistWindow.requestFocus();
            } else {
                addPharmacistWindow.show();
            }
        });

        addPatientButton.setOnAction((event) -> {
            if (addPatientWindow.isShowing()) {
                addPatientWindow.requestFocus();
            } else {
                addPatientWindow.show();
            }
        });

        addDrugButton.setOnAction((event) -> {
            if (addDrugWindow.isShowing()) {
                addDrugWindow.requestFocus();
            } else {
                addDrugWindow.show();
            }
        });

        addSupplierButton.setOnAction((event) -> {
            if (addSupplierWindow.isShowing()) {
                addSupplierWindow.requestFocus();
            } else {
                addSupplierWindow.show();
            }
        });

        supplyToPatientButton.setOnAction((event) -> {
            if (supplyToPatientWindow.isShowing()) {
                supplyToPatientWindow.requestFocus();
            } else {
                supplyToPatientWindow.show();
            }
        });

        receiveFromSupplierButton.setOnAction((event) -> {
            if (receiveFromSupplierWindow.isShowing()) {
                receiveFromSupplierWindow.requestFocus();
            } else {
                receiveFromSupplierWindow.show();
            }
        });

        searchByDrugButton.setOnAction((event) -> {
            if (searchByDrugWindow.isShowing()) {
                searchByDrugWindow.requestFocus();
            } else {
                searchByDrugWindow.show();
            }
        });

        searchByDateButton.setOnAction((event) -> {
            if (searchByDateWindow.isShowing()) {
                searchByDateWindow.requestFocus();
            } else {
                searchByDateWindow.show();
            }
        });

        stage.show();
    }

    /**
     * The Add Drug window, the UI for adding
     * a drug to the database.
     */
    private Stage createAddDrugWindow() {

        // Root layout.
        GridPane gridPane = new GridPane();
        setUpSpacing(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Drug");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var namePrompt = new Text("Name");
        var nameFieldAndValidation = new FieldAndAllCapValidation(new DrugNameField());
        addToGrid(gridPane, namePrompt, nameFieldAndValidation);

        // Second row of scene elements.
        var strengthPrompt = new Text("Strength");
        var strengthFieldAndValidation = new FieldAndValidation(new StrengthField());
        addToGrid(gridPane, strengthPrompt, strengthFieldAndValidation);

        // Third row of scene elements.
        var doseFormPrompt = new Text("Dose Form");
        var doseFormFieldAndValidation = new FieldAndValidation(new DoseFormField());
        addToGrid(gridPane, doseFormPrompt, doseFormFieldAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        setUpOkCancelButtons(ok, cancel, buttons);
        addToGrid(gridPane, null, buttons);

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
                        createAndOpenSqlErrorWindow(e.toString());
                    }
                    stage.close();
                } else {
                    nameFieldAndValidation.setValidationText(errors[0]);
                    strengthFieldAndValidation.setValidationText(errors[1]);
                    doseFormFieldAndValidation.setValidationText(errors[2]);
                }
            }
        });

        closeOnButtonPress(cancel, stage);

        stage.setOnHidden((event) -> {
            // The new stage will have fields and text reset.
            addDrugWindow = createAddDrugWindow(); 
        });

        return stage;
    }

    /**
     * The Add Patient window, the UI for adding
     * a patient to the database.
     */
    private Stage createAddPatientWindow() {

        // Root layout.
        GridPane gridPane = new GridPane();
        setUpSpacing(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Patient");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var firstNamePrompt = new Text("First Name");
        var firstNameFieldAndValidation = new FieldAndAllCapValidation(new FirstNameField());
        addToGrid(gridPane, firstNamePrompt, firstNameFieldAndValidation);

        // Second row of scene elements.
        var lastNamePrompt = new Text("Last Name");
        var lastNameFieldAndValidation = new FieldAndAllCapValidation(new LastNameField());
        addToGrid(gridPane, lastNamePrompt, lastNameFieldAndValidation);

        // Third Row of scene elements.
        var addressPrompt = new Text("Address");
        var addressFieldAndValidation = new AddressAreaAndValidation();
        addToGrid(gridPane, addressPrompt, addressFieldAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        setUpOkCancelButtons(ok, cancel, buttons);
        addToGrid(gridPane, null, buttons);

        // Event handlers.
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var patient = new Patient(
                        firstNameFieldAndValidation.getFieldText(),
                        lastNameFieldAndValidation.getFieldText(),
                        addressFieldAndValidation.getFieldText());
                ;
                String[] errors = patient.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addPatient(patient);
                    } catch (SQLException e) {
                        createAndOpenSqlErrorWindow(e.toString());
                    }
                    stage.close();
                } else {
                    firstNameFieldAndValidation.setValidationText(errors[0]);
                    lastNameFieldAndValidation.setValidationText(errors[1]);
                    addressFieldAndValidation.setValidationText(errors[2]);
                }
            }
        });

        closeOnButtonPress(cancel, stage);

        stage.setOnHidden((event) -> {
            // The new stage will have fields and text reset.
            addPatientWindow = createAddPatientWindow();
        });

        return stage;
    }

    /**
     * The Add Pharmacist window, the UI for adding
     * a pharmacist to the database.
     */
    private Stage createAddPharmacistWindow() {

        // Root layout.
        GridPane gridPane = new GridPane();
        setUpSpacing(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Pharmacist");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var firstNamePrompt = new Text("First Name");
        var firstNameFieldAndValidation = new FieldAndAllCapValidation(new FirstNameField());
        addToGrid(gridPane, firstNamePrompt, firstNameFieldAndValidation);

        // Second row of scene elements.
        var lastNamePrompt = new Text("Last Name");
        var lastNameFieldAndValidation = new FieldAndAllCapValidation(new LastNameField());
        addToGrid(gridPane, lastNamePrompt, lastNameFieldAndValidation);

        // Third Row of scene elements.
        var registrationPrompt = new Text("Registration");
        var registrationFieldAndValidation = new FieldAndAllCapValidation(new RegistrationField());
        addToGrid(gridPane, registrationPrompt, registrationFieldAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        setUpOkCancelButtons(ok, cancel, buttons);
        addToGrid(gridPane, null, buttons);

        // EventHandlers.
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var pharmacist = new Pharmacist(
                        firstNameFieldAndValidation.getFieldText(),
                        lastNameFieldAndValidation.getFieldText(),
                        registrationFieldAndValidation.getFieldText());
                ;
                String[] errors = pharmacist.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addPharmacist(pharmacist);
                    } catch (SQLException e) {
                        createAndOpenSqlErrorWindow(e.toString());
                    }
                    stage.close();
                } else {
                    firstNameFieldAndValidation.setValidationText(errors[0]);
                    lastNameFieldAndValidation.setValidationText(errors[1]);
                    registrationFieldAndValidation.setValidationText(errors[2]);
                }
            }
        });

        closeOnButtonPress(cancel, stage);

        stage.setOnHidden((event) -> {
            // The new stage will have fields and text reset.
            addPharmacistWindow = createAddPharmacistWindow();
        });
    
        return stage;
    }

    /**
     * The Add Prescriber window, the UI for adding
     * a prescriber to the database.
     */
    private Stage createAddPrescriberWindow() {

        // Root layout.
        GridPane gridPane = new GridPane();
        setUpSpacing(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Prescriber");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var firstNamePrompt = new Text("First Name");
        var firstNameFieldAndValidation = new FieldAndAllCapValidation(new FirstNameField());
        addToGrid(gridPane, firstNamePrompt, firstNameFieldAndValidation);

        // Second row of scene elements.
        var lastNamePrompt = new Text("Last Name");
        var lastNameFieldAndValidation = new FieldAndAllCapValidation(new LastNameField());
        addToGrid(gridPane, lastNamePrompt, lastNameFieldAndValidation);

        // Third row of scene elements.
        var prescriberNumPrompt = new Text("Prescriber\nNumber");
        var prescriberNumFieldAndValidation = new
                FieldAndAllCapValidation(new PrescriberNumField());
        addToGrid(gridPane,
                prescriberNumPrompt,
                prescriberNumFieldAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        setUpOkCancelButtons(ok, cancel, buttons);
        addToGrid(gridPane, null, buttons);

        // Event handlers.
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var prescriber = new Prescriber(
                        firstNameFieldAndValidation.getFieldText(),
                        lastNameFieldAndValidation.getFieldText(),
                        prescriberNumFieldAndValidation.getFieldText());

                String[] errors = prescriber.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addPrescriber(prescriber);
                    } catch (SQLException e) {
                        createAndOpenSqlErrorWindow(e.toString());
                    }
                    stage.close();
                } else {
                    firstNameFieldAndValidation.setValidationText(errors[0]);
                    lastNameFieldAndValidation.setValidationText(errors[1]);
                    prescriberNumFieldAndValidation.setValidationText(errors[2]);
                }
            }
        });

        closeOnButtonPress(cancel, stage);

        stage.setOnHidden((event) -> {
            // The new stage will have fields and text reset.
            addPrescriberWindow = createAddPrescriberWindow();
        });

        return stage;
    }

    /**
     * The Add Patient window, the UI for adding
     * a supplier to the database.
     */
    private Stage createAddSupplierWindow() {

        // Root layout.
        GridPane gridPane = new GridPane();
        setUpSpacing(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Supplier");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var supplierNamePrompt = new Text("Supplier Name");
        var supplierNameFieldAndValidation = new FieldAndAllCapValidation(new SupplierNameField());
        addToGrid(gridPane, supplierNamePrompt, supplierNameFieldAndValidation);

        // Second Row of scene elements.
        var addressPrompt = new Text("Address");
        var addressFieldAndValidation = new AddressAreaAndValidation();
        addToGrid(gridPane, addressPrompt, addressFieldAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        setUpOkCancelButtons(ok, cancel, buttons);
        addToGrid(gridPane, null, buttons);

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
                        createAndOpenSqlErrorWindow(e.toString());
                    }
                    stage.close();
                } else {
                    supplierNameFieldAndValidation.setValidationText(errors[0]);
                    addressFieldAndValidation.setValidationText(errors[1]);
                }
            }
        });

        closeOnButtonPress(cancel, stage);

        stage.setOnHidden((event) -> {
            // The new stage will have fields and text reset.
            addSupplierWindow = createAddSupplierWindow();
        });

        return stage;
    }

    /*
     * The Supply to Patient window, the UI for adding
     * a supply to a patient to the database.
     */
    private Stage createSupplyToPatientWindow() {

        // Root layout.
        GridPane gridPane = new GridPane();
        setUpSpacing(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Supply to Patient");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_B_WIDTH, SUBWINDOW_B_HEIGHT));

        // First row of scene elements.
        var patientPrompt = new Text("Patient");
        var patientField = new PatientField();
        patientField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var addPatientButton = new Button("+");
        addTooltip(addPatientButton, "Add patient");
        var patientBoxAndValidation =
                new ComboBoxAndValidation<Patient>(addPatientButton);
        addToGrid(gridPane,
                patientPrompt,
                patientField,
                patientBoxAndValidation);

        // Second row of scene elements.
        var drugPrompt = new Text("Drug");
        var drugField = new DrugField();
        drugField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var drugBoxAndValidation = new ComboBoxAndValidation<Drug>();
        addToGrid(gridPane, drugPrompt, drugField, drugBoxAndValidation);

        // Third row of scene elements.
        var balancePrompt = new Text("Balance");
        var balanceField = new Text();
        addToGrid(gridPane, balancePrompt, null, balanceField);

        // Fourth row of scene elements.
        var qtyPrompt = new Text("Quantity");
        var qtyFieldAndValidation = new FieldAndValidation(new QtyField());
        addToGrid(gridPane, qtyPrompt, null, qtyFieldAndValidation);

        // Fifth row of scene elements.
        var prescriberPrompt = new Text("Prescriber");
        var prescriberField = new PrescriberField();
        prescriberField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var addPrescriberButton = new Button("+");
        addTooltip(addPrescriberButton, "Add prescriber");
        var prescriberBoxAndValidation = new
                ComboBoxAndValidation<Prescriber>(addPrescriberButton);
        addToGrid(
                gridPane,
                prescriberPrompt,
                prescriberField,
                prescriberBoxAndValidation);

        // Sixth row of scene elements.
        var pharmacistPrompt = new Text("Pharmacist");
        var pharmacistField = new PharmacistField();
        pharmacistField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var pharmacistBoxAndValidation = new
                ComboBoxAndValidation<Pharmacist>();
        addToGrid(gridPane,
                pharmacistPrompt,
                pharmacistField,
                pharmacistBoxAndValidation);

        // Seventh row of scene elements.
        var scriptNumPrompt = new Text("Script Num");
        var scriptNumFieldAndValidation = new FieldAndAllCapValidation(new ReferenceField());
        addToGrid(gridPane, scriptNumPrompt, null, scriptNumFieldAndValidation);

        // Eighth row of scene elements.
        var notesPrompt = new Text("Notes");
        var notesFieldAndValidation = new FieldAndValidation(new NotesField());
        addToGrid(
                gridPane,
                notesPrompt,
                null,
                notesFieldAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        setUpOkCancelButtons(ok, cancel, buttons);
        addToGrid(gridPane, null, null, buttons);

        // Event handlers.
        updateComboBox(patientField, patientBoxAndValidation.getComboBox());

        updateComboBox(drugField, drugBoxAndValidation.getComboBox());

        updateComboBox(prescriberField, prescriberBoxAndValidation.getComboBox());

        updateComboBox(pharmacistField, pharmacistBoxAndValidation.getComboBox());

        drugBoxAndValidation.getComboBox().setOnAction(event -> {
            Drug selectedDrug = drugBoxAndValidation.getComboBoxValue();

            try {
                if (selectedDrug != null) {
                    balanceField.setText(String.valueOf(
                            db.getBalance(selectedDrug.getId())));
                }
            } catch (SQLException e) {
                createAndOpenSqlErrorWindow(e.toString());
            }
        });

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                var transfer = new TransferInput(
                        patientBoxAndValidation.getComboBoxValue(),
                        drugBoxAndValidation.getComboBoxValue(),
                        balanceField.getText(),
                        qtyFieldAndValidation.getFieldText(),
                        prescriberBoxAndValidation.getComboBoxValue(),
                        scriptNumFieldAndValidation.getFieldText(),
                        notesFieldAndValidation.getFieldText(),
                        pharmacistBoxAndValidation.getComboBoxValue());
                String[] errors = transfer.validateSupplyToPatient();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);

                if (isNoErrors) {
                    try {
                        db.addTransfer(transfer);
                    } catch (SQLException e) {
                        createAndOpenSqlErrorWindow(e.toString());
                    }
                    stage.close();
                } else {
                    patientBoxAndValidation.setValidationText(errors[0]);
                    drugBoxAndValidation.setValidationText(errors[1]);
                    qtyFieldAndValidation.setValidationText(errors[2]);
                    prescriberBoxAndValidation.setValidationText(errors[3]);
                    scriptNumFieldAndValidation.setValidationText(errors[4]);
                    notesFieldAndValidation.setValidationText(errors[5]);
                    pharmacistBoxAndValidation.setValidationText(errors[6]);
                }
            }
        });

        addPatientButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createAddPatientWindow();
            }
        });

        addPrescriberButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createAddPrescriberWindow();
            }
        });

        closeOnButtonPress(cancel, stage);

        stage.setOnHidden((event) -> {
            // The new stage will have fields and text reset.
            supplyToPatientWindow = createSupplyToPatientWindow();
        });

        return stage;
    }

    /*
     * The Receive from Supplier window, the UI for adding
     * a receipt of order from supplier to the database.
     */
    private Stage createReceiveFromSupplierWindow() {

        // Root layout.
        GridPane gridPane = new GridPane();
        setUpSpacing(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Receive from Supplier");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_B_WIDTH, SUBWINDOW_B_HEIGHT));

        // First row of scene elements.
        var supplierPrompt = new Text("Supplier");
        var supplierField = new SupplierField();
        supplierField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var supplierBoxAndValidation = new ComboBoxAndValidation<Supplier>();
        addToGrid(
                gridPane,
                supplierPrompt,
                supplierField,
                supplierBoxAndValidation);

        // Second row of scene elements.
        var drugPrompt = new Text("Drug");
        var drugField = new DrugField();
        var drugBoxAndValidation = new ComboBoxAndValidation<Drug>();
        addToGrid(gridPane, drugPrompt, drugField, drugBoxAndValidation);

        // Third row of scene elements.
        var balancePrompt = new Text("Balance");
        var balanceField = new Text();
        addToGrid(gridPane, balancePrompt, null, balanceField);

        // Fourth row of scene elements.
        var qtyPrompt = new Text("Quantity");
        var qtyFieldAndValidation = new FieldAndValidation(new QtyField());
        addToGrid(gridPane, qtyPrompt, null, qtyFieldAndValidation);

        // Fifth row of scene elements.
        var pharmacistPrompt = new Text("Pharmacist");
        var pharmacistField = new PharmacistField();
        var pharmacistBoxAndValidation = new
                ComboBoxAndValidation<Pharmacist>();
        addToGrid(
                gridPane,
                pharmacistPrompt,
                pharmacistField,
                pharmacistBoxAndValidation);

        // Sixth row of scene elements.
        var invoicePrompt = new Text("Invoice Num");
        var invoiceFieldAndValidation = new FieldAndAllCapValidation(new ReferenceField());
        addToGrid(gridPane, invoicePrompt, null, invoiceFieldAndValidation);

        // Seventh row of scene elements.
        var notesPrompt = new Text("Notes");
        var notesFieldAndValidation = new FieldAndValidation(new NotesField());
        addToGrid(
                gridPane,
                notesPrompt,
                null,
                notesFieldAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        setUpOkCancelButtons(ok, cancel, buttons);
        addToGrid(gridPane, null, null, buttons);

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
                createAndOpenSqlErrorWindow(e.toString());
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
                        createAndOpenSqlErrorWindow(e.toString());
                    }
                    stage.close();
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

        closeOnButtonPress(cancel, stage);

        stage.setOnHidden((event) -> {
            // The new stage will have fields and text reset.
            receiveFromSupplierWindow = createReceiveFromSupplierWindow();
        });

        return stage;
    }

    private Stage createSearchByDrugWindow() {

        // Second to root layout.
        GridPane gridPane = new GridPane();
        setUpSpacing(gridPane);

        // Root layout.
        GridPane gridPaneMain = new GridPane();
        setUpSpacing(gridPaneMain);
        addToGrid(gridPaneMain, gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Search by Drug");
        stage.setScene(new Scene(
                gridPaneMain, SUBWINDOW_C_WIDTH, SUBWINDOW_C_HEIGHT));

        // First row of scene elements.
        var drugPrompt = new Text("Drug");
        var drugField = new DrugField();
        drugField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var drugBoxAndValidation = new ComboBoxAndValidation<Drug>();
        addToGrid(gridPane, drugPrompt, drugField, drugBoxAndValidation);

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
        addToGrid(gridPane, startDatePrompt, null, startDateFieldAndValidation);

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
        addToGrid(gridPane, endDatePrompt, null, endDateFieldAndValidation);

        // Button.
        var search = new Button("Search");
        search.setPrefWidth(OK_CANCEL_BUTTON_WIDTH);
        var close = new Button("Close");
        close.setPrefWidth(OK_CANCEL_BUTTON_WIDTH);
        var reverseEntryButton = new Button("↶");
        addTooltip(reverseEntryButton, "Reverse entry");
        var reverseEntryInvalid = new Text();
        reverseEntryInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren()
                .addAll(search, close, reverseEntryButton, reverseEntryInvalid);
        addToGrid(gridPane, null, null, buttons);

        // Search Results.
        var tableView = new TableView<TransferRetrieved>();
        createTableLayout(tableView);
        addToGrid(gridPaneMain, tableView);

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
                        createAndOpenSqlErrorWindow(e.toString());
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
            TransferRetrieved transferEntry = tableView.getSelectionModel().getSelectedItem();
            if (transferEntry != null) {
                createAndOpenReverseEntryWindow(transferEntry);
            } else {
                reverseEntryInvalid.setText("Select entry first");

            }
        });

        validationTextRemoval(reverseEntryButton, reverseEntryInvalid);

        close.setOnAction((event) -> stage.close());

        return stage;
    }

    private Stage createSearchByDateWindow() {

        // Second to root layout.
        GridPane gridPane = new GridPane();
        setUpSpacing(gridPane);

        // Root layout.
        GridPane gridPaneMain = new GridPane();
        setUpSpacing(gridPaneMain);
        addToGrid(gridPaneMain, gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Search by Day");
        stage.setScene(new Scene(
                gridPaneMain, SUBWINDOW_C_WIDTH, SUBWINDOW_C_HEIGHT));

        // First Row of scene elements.
        var dateField = new DatePicker();
        dateField.setPrefWidth(DATE_PICKER_WIDTH);
        var close = new Button("Close");
        close.setPrefWidth(OK_CANCEL_BUTTON_WIDTH);
        var reverseEntryButton = new Button("↶");
        var reverseEntryTooltip = new Tooltip("Reverse entry");
        reverseEntryTooltip.setShowDelay(Duration.millis(TOOLTIP_DELAY));
        reverseEntryButton.setTooltip(reverseEntryTooltip);
        var reverseEntryInvalid = new Text();
        reverseEntryInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(
                gridPane,
                new Text("Date"),
                dateField,
                close,
                reverseEntryButton,
                reverseEntryInvalid);
        GridPane.setHalignment(reverseEntryButton, HPos.RIGHT);

        // Search Results
        var tableView = new TableView<TransferRetrieved>();
        createTableLayout(tableView);
        addToGrid(gridPaneMain, tableView);

        // Event handlers.
        dateField.setOnAction(event -> {

            updateTransferSearch(tableView, dateField);
        });

        reverseEntryButton.setOnAction(event -> {
            TransferRetrieved transferRead = tableView.getSelectionModel().getSelectedItem();

            if (transferRead != null) {
                createAndOpenReverseEntryWindow(transferRead);
            } else {
                reverseEntryInvalid.setText("Select entry first");
            }
        });

        validationTextRemoval(reverseEntryButton, reverseEntryInvalid);

        dateField.show();

        closeOnButtonPress(close, stage);

        return stage;
    }

    /*
     * A window to create a new entry to negate a previous entry.
     * Reasons for this are an error in the previous entry, or
     * cancellation of a prescription. Previous entries cannot not be
     * modified in a drugs of dependence register.
     */
    private void createAndOpenReverseEntryWindow(
            TransferRetrieved transferRead) {

        // Second to root layout.
        GridPane gridPane = new GridPane();
        setUpSpacing(gridPane);

        // Root layout.
        GridPane mainGridPane = new GridPane();
        setUpSpacing(mainGridPane);
        mainGridPane.add(gridPane, 0, 1);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Reverse Entry");
        stage.setScene(new Scene(
                mainGridPane,
                REVERSE_ENTRY_WINDOW_WIDTH,
                REVERSE_ENTRY_WINDOW_HEIGHT));

        // First row elements.
        TableView<TransferRetrieved> tableViewSingleDrug = new TableView<>();
        createTableLayout(tableViewSingleDrug);
        // Remove balance presriber, pharmacist, and notes columns
        // for this tableview.
        final int pharmacistIdx = 8;
        final int notesIdx = 9;
        final int balanceIdx = 5;
        final int prescriberIdx = 6;
        tableViewSingleDrug.getColumns().remove(pharmacistIdx, notesIdx + 1);
        tableViewSingleDrug.getColumns().remove(balanceIdx, prescriberIdx + 1);
        tableViewSingleDrug.setPrefHeight(REVERSE_ENTRY_TABLE_HEIGHT);
        tableViewSingleDrug.getItems().add(transferRead);
        mainGridPane.add(tableViewSingleDrug, 0, 0);

        // Sub-layout elements, first row.
        var pharmacistPrompt = new Text("Pharmacist");
        var pharmacistField = new PharmacistField();
        pharmacistField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var pharmacistBoxAndValidation = new ComboBoxAndValidation<Pharmacist>();
        addToGrid(gridPane,
                pharmacistPrompt,
                pharmacistField,
                pharmacistBoxAndValidation);

        // Sub-layout elements, second row.
        Text notesPrompt = new Text("Notes");
        var notesFieldAndValidation = new FieldAndValidation(new NotesField());
        addToGrid(gridPane, notesPrompt, null, notesFieldAndValidation);

        // Sub-layout elements, third row.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        setUpOkCancelButtons(ok, cancel, buttons);
        addToGrid(gridPane, null, null, buttons);

        // Event handlers.
        updateComboBox(pharmacistField, pharmacistBoxAndValidation.getComboBox());

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    int balanceBefore = db.getBalance(transferRead.getDrug().getId());

                    transferRead.reverseEntry(
                            balanceBefore,
                            pharmacistBoxAndValidation.getComboBoxValue(),
                            notesFieldAndValidation.getFieldText());

                    String[] errors = transferRead.validateReverseEntry();

                    boolean isNoErrors = Arrays
                            .stream(errors)
                            .map(str -> str.isEmpty())
                            .reduce(true, (x, y) -> x && y);

                    if (isNoErrors) {

                        if (transferRead.getAgent() instanceof Patient) {
                            db.addTransfer(transferRead);
                        }

                        stage.close();
                    } else {
                        pharmacistBoxAndValidation.setValidationText(errors[0]);
                        notesFieldAndValidation.setValidationText(errors[1]);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    createAndOpenSqlErrorWindow(e.toString());
                }

            }
        });

        closeOnButtonPress(cancel, stage);

        stage.show();
    }

    /*
     * A window displaying an error message. Used for
     * SQLExceptions.
     */
    private void createAndOpenSqlErrorWindow(String msg) {
        // Root layout.
        GridPane gridPane = new GridPane();
        setUpSpacing(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Database Error");
        stage.setScene(new Scene(
                gridPane, ERROR_WINDOW_WIDTH, ERROR_WINDOW_HEIGHT));
        var messageArea = new TextArea(msg);
        messageArea.setWrapText(true);
        messageArea.setEditable(false);
        var ok = new Button("OK");
        ok.setPrefWidth(OK_CANCEL_BUTTON_WIDTH);
        var headerText = new Text("An error occurred with the database.");
        gridPane.add(headerText, 0, 0);
        gridPane.add(messageArea, 0, 1);
        gridPane.add(ok, 0, 2);
        GridPane.setHalignment(ok, HPos.CENTER);

        closeOnButtonPress(ok, stage);

        stage.showAndWait();
    }

    /*
     * Creates table used to view drug transfers.
     */
    private void createTableLayout(TableView<TransferRetrieved> tableView) {

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
     * Sets up functionality of cancel button.
     */
    private void closeOnButtonPress(Button cancel, Stage stage) {
        cancel.setOnAction((event) -> stage.close());
    }

    /*
     * Removes input validation message once focus or mouse hover is
     * no longer active on the control.
     */
    private void validationTextRemoval(Control control, Text text) {
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
    private void addTooltip(Control control, String msg) {
        var tooltip = new Tooltip(msg);
        tooltip.setShowDelay(Duration.millis(TOOLTIP_DELAY));
        control.setTooltip(tooltip);
    }

    /*
     * Sets up the layout of the OK and Cancel buttons.
     */
    private void setUpOkCancelButtons(
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
                    int oldListLength = comboBox.getItems().size();

                    comboBox.setItems(
                            FXCollections.observableArrayList(list));

                    if (comboBox.getItems().size() > oldListLength) {
                        comboBox.hide(); // Resets height of popup.
                    }

                    comboBox.show();
                } catch (SQLException e) {
                    createAndOpenSqlErrorWindow(e.toString());
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
                    createAndOpenSqlErrorWindow(e.toString());
                }
            }
        });
    }

    /*
     * Updates the TableView to filter entries with the chosen date.
     */
    private void updateTransferSearch(
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
            createAndOpenSqlErrorWindow(e.toString());
        }
    }

    /*
     * Adds listed child nodes to the GridPane on the next row.
     * Any nulls in children will cause the column to be skipped
     * over.
     */
    private void addToGrid(GridPane gridPane, Node... children) {
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
