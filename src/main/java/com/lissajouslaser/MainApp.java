package com.lissajouslaser;

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
import javafx.scene.layout.HBox;
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
    static final int REVERSE_ENTRY_WINDOW_WIDTH = 932;
    static final int REVERSE_ENTRY_WINDOW_HEIGHT = 180;
    // Buttons with images.
    static final int GRAPHIC_BUTTON_WIDTH = 160;
    static final int GRAPHIC_BUTTON_HEIGHT = 190;
    // Spacing parameters for GridPane.
    static final int PADDING_SPACE = 15;
    static final int GAP_SPACE = 8;
    // Column widths for transfer table
    static final int DATE_WIDTH = 100;
    static final int AGENT_WIDTH = 400;
    static final int DRUG_WIDTH = 200;
    static final int IN_OUT_WIDTH = 50;
    static final int BALANCE_WIDTH = 80;
    static final int PRESCRIBER_WIDTH = 200;
    static final int REFERENCE_WIDTH = 100;
    static final int PHARMACIST_WIDTH = 120;
    static final int NOTES_WIDTH = 100;


    // Other parameters
    static final int OK_CANCEL_BUTTON_WIDTH = 80;
    static final int TEXT_FIELD_WIDTH = 250;
    static final int TEXT_AREA_HEIGHT = 60;
    static final int TEXT_FIELD_SEARCH_WIDTH = 70;
    static final int NUM_FIELD_WIDTH = 70;
    static final int ERROR_FONT_SIZE = 11;
    static final int COMBO_BOX_WIDTH = 320;
    static final int DATE_PICKER_WIDTH = 120;
    static final int TOOLTIP_DELAY = 400; // milliseconds

    DatabaseConnection db;

    @Override
    public void start(Stage s) {
        try {
            db = new DatabaseConnection();
        } catch (SQLException e) {
            openSqlErrorWindow(e.toString());
        }

        openMainWindow();

    }

    private void openMainWindow() {
        // Root layour.
        GridPane gridPane = new GridPane();
        setUpSpacing(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Drugs of Addiction Register");
        stage.setScene(new Scene(
        gridPane, MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT));

        var supplyToPatientButton = new Button("Supply to Patient");
        var receiveFromSupplierButton = new Button("Receive from Supplier");
        var searchByDrugButton = new Button("Search by Drug");
        var searchByDateButton = new Button("Search by Date");
        var addPatientButton = new Button("Add Patient");
        var addPrescriberButton = new Button("Add Prescriber");
        var addPharmacistButton = new Button("Add Pharmacist");
        var addDrugButton = new Button("Add Drug");
        var addSupplierButton = new Button("Add Supplier");

        addButtonImage(
                supplyToPatientButton,
                "file:SupplyToPatient.png");
        addButtonImage(
                receiveFromSupplierButton,
                "file:ReceiveFromSupplier.png");
        addButtonImage(
                searchByDateButton,
                "file:SearchByDate.png");
        addButtonImage(
                searchByDrugButton,
                "file:SearchByDrug.png");
        addButtonImage(
                addPatientButton,
                "file:Patient.png");
        addButtonImage(
                addPrescriberButton,
                "file:Doctor.png");
        addButtonImage(
                addPharmacistButton,
                "file:Pharmacist.png");
        addButtonImage(
                addDrugButton,
                "file:Drug.png");
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

        searchByDrugButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWindowSearchByDrug();
            }
        });

        searchByDateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWindowSearchByDate();
            }
        });

        stage.show();
    }

    /**
     * The Add Drug window, the UI for adding
     * a drug to the database.
     */
    private void openWindowAddDrug() {

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
        var nameField = new DrugNameField();
        nameField.setPrefWidth(TEXT_FIELD_WIDTH);
        var nameInvalid = new Text();
        nameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane nameFieldAndValidation = new FlowPane();
        nameFieldAndValidation.setHgap(GAP_SPACE);
        nameFieldAndValidation.getChildren().addAll(nameField, nameInvalid);
        addToGrid(gridPane, namePrompt, nameFieldAndValidation);

        // Second row of scene elements.
        var strengthPrompt = new Text("Strength");
        var strengthField = new StrengthField();
        strengthField.setPrefWidth(TEXT_FIELD_WIDTH);
        var strengthInvalid = new Text();
        strengthInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane strengthFieldAndValidation = new FlowPane();
        strengthFieldAndValidation.setHgap(GAP_SPACE);
        strengthFieldAndValidation
                .getChildren()
                .addAll(strengthField, strengthInvalid);
        addToGrid(gridPane, strengthPrompt, strengthFieldAndValidation);

        // Third row of scene elements.
        var doseFormPrompt = new Text("Dose Form");
        var doseFormField = new DoseFormField();
        doseFormField.setPrefWidth(TEXT_FIELD_WIDTH);
        var doseFormInvalid = new Text();
        doseFormInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane doseFormFieldAndValidation = new FlowPane();
        doseFormFieldAndValidation.setHgap(GAP_SPACE);
        doseFormFieldAndValidation
                .getChildren()
                .addAll(doseFormField, doseFormInvalid);
        addToGrid(gridPane, doseFormPrompt, doseFormFieldAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        setUpOkCancelButtons(ok, cancel, buttons);
        addToGrid(gridPane, null, buttons);

        // Event handlers.
        constrainInput(nameField);

        constrainInputLowerCase(strengthField);

        constrainInputLowerCase(doseFormField);

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
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addDrug(drug);
                    } catch (SQLException e) {
                        openSqlErrorWindow(e.toString());
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
        setUpSpacing(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Patient");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var firstNamePrompt = new Text("First Name");
        var firstNameField = new FirstNameField();
        firstNameField.setPrefWidth(TEXT_FIELD_WIDTH);
        var firstNameInvalid = new Text();
        firstNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane firstNameFieldAndValidation = new FlowPane();
        firstNameFieldAndValidation.setHgap(GAP_SPACE);
        firstNameFieldAndValidation
                .getChildren()
                .addAll(firstNameField, firstNameInvalid);
        addToGrid(gridPane, firstNamePrompt, firstNameFieldAndValidation);

        // Second row of scene elements.
        var lastNamePrompt = new Text("Last Name");
        var lastNameField = new LastNameField();
        lastNameField.setPrefWidth(TEXT_FIELD_WIDTH);
        var lastNameInvalid = new Text();
        lastNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane lastNameFieldAndValidation = new FlowPane();
        lastNameFieldAndValidation.setHgap(GAP_SPACE);
        lastNameFieldAndValidation
                .getChildren()
                .addAll(lastNameField, lastNameInvalid);
        addToGrid(gridPane, lastNamePrompt, lastNameFieldAndValidation);

        // Third Row of scene elements.
        var addressPrompt = new Text("Address");
        var addressField = new AddressArea();
        addressField.setPrefSize(TEXT_FIELD_WIDTH, TEXT_AREA_HEIGHT);
        addressField.setWrapText(true);
        var addressInvalid = new Text();
        addressInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane addressFieldAndValidation = new FlowPane();
        addressFieldAndValidation.setHgap(GAP_SPACE);
        addressFieldAndValidation
                .getChildren()
                .addAll(addressField, addressInvalid);
        addToGrid(gridPane, addressPrompt, addressFieldAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        setUpOkCancelButtons(ok, cancel, buttons);
        addToGrid(gridPane, null, buttons);

        // Event handlers.
        constrainInput(firstNameField);

        constrainInput(lastNameField);

        constrainInputLowerCase(addressField);

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
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addPatient(patient);
                    } catch (SQLException e) {
                        openSqlErrorWindow(e.toString());
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
        setUpSpacing(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Pharmacist");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var firstNamePrompt = new Text("First Name");
        var firstNameField = new FirstNameField();
        firstNameField.setPrefWidth(TEXT_FIELD_WIDTH);
        var firstNameInvalid = new Text();
        firstNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane firstNameFieldAndValidation = new FlowPane();
        firstNameFieldAndValidation.setHgap(GAP_SPACE);
        firstNameFieldAndValidation
                .getChildren()
                .addAll(firstNameField, firstNameInvalid);
        addToGrid(gridPane, firstNamePrompt, firstNameFieldAndValidation);

        // Second row of scene elements.
        var lastNamePrompt = new Text("Last Name");
        var lastNameField = new LastNameField();
        lastNameField.setPrefWidth(TEXT_FIELD_WIDTH);
        var lastNameInvalid = new Text();
        lastNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane lastNameFieldAndValidation = new FlowPane();
        lastNameFieldAndValidation.setHgap(GAP_SPACE);
        lastNameFieldAndValidation
                .getChildren()
                .addAll(lastNameField, lastNameInvalid);
        addToGrid(gridPane, lastNamePrompt, lastNameFieldAndValidation);

        // Third Row of scene elements.
        var registrationPrompt = new Text("Registration");
        var registrationField = new RegistrationField();
        registrationField.setPrefWidth(TEXT_FIELD_WIDTH);
        var registrationInvalid = new Text();
        registrationInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane registrationFieldAndValidation = new FlowPane();
        registrationFieldAndValidation.setHgap(GAP_SPACE);
        registrationFieldAndValidation
                .getChildren()
                .addAll(registrationField, registrationInvalid);
        addToGrid(gridPane, registrationPrompt, registrationFieldAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        setUpOkCancelButtons(ok, cancel, buttons);
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
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addPharmacist(pharmacist);
                    } catch (SQLException e) {
                        openSqlErrorWindow(e.toString());
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
        setUpSpacing(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Prescriber");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var firstNamePrompt = new Text("First Name");
        var firstNameField = new FirstNameField();
        firstNameField.setPrefWidth(TEXT_FIELD_WIDTH);
        var firstNameInvalid = new Text();
        firstNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane firstNameFieldAndValidation = new FlowPane();
        firstNameFieldAndValidation.setHgap(GAP_SPACE);
        firstNameFieldAndValidation
                .getChildren()
                .addAll(firstNameField, firstNameInvalid);
        addToGrid(gridPane, firstNamePrompt, firstNameFieldAndValidation);

        // Second row of scene elements.
        var lastNamePrompt = new Text("Last Name");
        var lastNameField = new LastNameField();
        lastNameField.setPrefWidth(TEXT_FIELD_WIDTH);
        var lastNameInvalid = new Text();
        lastNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane lastNameFieldAndValidation = new FlowPane();
        lastNameFieldAndValidation.setHgap(GAP_SPACE);
        lastNameFieldAndValidation
                .getChildren()
                .addAll(lastNameField, lastNameInvalid);
        addToGrid(gridPane, lastNamePrompt, lastNameFieldAndValidation);

        // Third row of scene elements.
        var prescriberNumPrompt = new Text("Prescriber\nNumber");
        var prescriberNumField = new PrescriberNumField();
        prescriberNumField.setPrefWidth(TEXT_FIELD_WIDTH);
        var prescriberNumInvalid = new Text();
        prescriberNumInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane prescriberNumFieldAndValidation = new FlowPane();
        prescriberNumFieldAndValidation.setHgap(GAP_SPACE);
        prescriberNumFieldAndValidation
                .getChildren()
                .addAll(prescriberNumField, prescriberNumInvalid);
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
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addPrescriber(prescriber);
                    } catch (SQLException e) {
                        openSqlErrorWindow(e.toString());
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
        setUpSpacing(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Supplier");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_A_WIDTH, SUBWINDOW_A_HEIGHT));

        // First row of scene elements.
        var supplierNamePrompt = new Text("Supplier Name");
        var supplierNameField = new SupplierNameField();
        supplierNameField.setPrefWidth(TEXT_FIELD_WIDTH);
        var supplierNameInvalid = new Text();
        supplierNameInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane supplierNameFieldAndValidation = new FlowPane();
        supplierNameFieldAndValidation.setHgap(GAP_SPACE);
        supplierNameFieldAndValidation
                .getChildren()
                .addAll(supplierNameField, supplierNameInvalid);
        addToGrid(gridPane, supplierNamePrompt, supplierNameFieldAndValidation);

        // Second Row of scene elements.
        var addressPrompt = new Text("Address");
        var addressField = new AddressArea();
        addressField.setPrefSize(TEXT_FIELD_WIDTH, TEXT_AREA_HEIGHT);
        addressField.setWrapText(true);
        var addressInvalid = new Text();
        addressInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane addressFieldAndValidation = new FlowPane();
        addressFieldAndValidation.setHgap(GAP_SPACE);
        addressFieldAndValidation
                .getChildren()
                .addAll(addressField, addressInvalid);
        addToGrid(gridPane, addressPrompt, addressFieldAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        setUpOkCancelButtons(ok, cancel, buttons);
        addToGrid(gridPane, null, buttons);

        // Event Handlers.
        constrainInput(supplierNameField);

        constrainInputLowerCase(addressField);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var supplier = new Supplier(
                        supplierNameField.getText(),
                        addressField.getText());

                String[] errors = supplier.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    try {
                        db.addSupplier(supplier);
                    } catch (SQLException e) {
                        openSqlErrorWindow(e.toString());
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
        var patientComboBox = new ComboBox<Patient>();
        patientComboBox.setPrefWidth(COMBO_BOX_WIDTH);
        var addPatientButton = new Button("+");
        addTooltip(addPatientButton, "Add patient");      
        var patientInvalid = new Text();
        patientInvalid.setFont(new Font(ERROR_FONT_SIZE));
        HBox patientBoxAndValidation = new HBox();
        patientBoxAndValidation.spacingProperty().set(GAP_SPACE);
        patientBoxAndValidation
                .getChildren()
                .addAll(patientComboBox, addPatientButton, patientInvalid);
        addToGrid(gridPane,
                patientPrompt,
                patientField,
                patientBoxAndValidation);

        // Second row of scene elements.
        var drugPrompt = new Text("Drug");
        var drugField = new DrugField();
        drugField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var drugComboBox = new ComboBox<Drug>();
        drugComboBox.setPrefWidth(COMBO_BOX_WIDTH);
        var drugInvalid = new Text();
        drugInvalid.setFont(new Font(ERROR_FONT_SIZE));
        HBox drugBoxAndValidation = new HBox();
        drugBoxAndValidation.spacingProperty().set(GAP_SPACE);
        drugBoxAndValidation
                .getChildren()
                .addAll(drugComboBox, drugInvalid);
        addToGrid(gridPane, drugPrompt, drugField, drugBoxAndValidation);

        // Third row of scene elements.
        var balancePrompt = new Text("Balance");
        var balanceField = new Text();
        addToGrid(gridPane, balancePrompt, null, balanceField);

        // Fourth row of scene elements.
        var qtyPrompt = new Text("Quantity");
        var qtyField = new QtyField();
        qtyField.setPrefWidth(NUM_FIELD_WIDTH);
        var qtyInvalid = new Text();
        qtyInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane qtyBoxAndValidation = new FlowPane();
        qtyBoxAndValidation.setHgap(GAP_SPACE);
        qtyBoxAndValidation
                .getChildren()
                .addAll(qtyField, qtyInvalid);
        addToGrid(gridPane, qtyPrompt, null, qtyBoxAndValidation);

        // Fifth row of scene elements.
        var prescriberPrompt = new Text("Prescriber");
        var prescriberField = new PrescriberField();
        prescriberField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var prescriberComboBox = new ComboBox<Prescriber>();
        prescriberComboBox.setPrefWidth(COMBO_BOX_WIDTH);
        var addPrescriberButton = new Button("+");
        addTooltip(addPrescriberButton, "Add prescriber");
        var prescriberInvalid = new Text();
        prescriberInvalid.setFont(new Font(ERROR_FONT_SIZE));
        HBox prescriberBoxAndValidation = new HBox();
        prescriberBoxAndValidation.spacingProperty().set(GAP_SPACE);
        prescriberBoxAndValidation.getChildren().addAll(
                prescriberComboBox,
                addPrescriberButton,
                prescriberInvalid);
        addToGrid(
                gridPane,
                prescriberPrompt,
                prescriberField,
                prescriberBoxAndValidation);

        // Sixth row of scene elements.
        var pharmacistPrompt = new Text("Pharmacist");
        var pharmacistField = new PharmacistField();
        pharmacistField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var pharmacistComboBox = new ComboBox<Pharmacist>();
        pharmacistComboBox.setPrefWidth(COMBO_BOX_WIDTH);
        var pharmacistInvalid = new Text();
        pharmacistInvalid.setFont(new Font(ERROR_FONT_SIZE));
        HBox pharmacistBoxAndValidation = new HBox();
        pharmacistBoxAndValidation.spacingProperty().set(GAP_SPACE);
        pharmacistBoxAndValidation.getChildren().addAll(
                pharmacistComboBox,
                pharmacistInvalid);
        addToGrid(gridPane,
                pharmacistPrompt,
                pharmacistField,
                pharmacistBoxAndValidation);

        // Seventh row of scene elements.
        var scriptNumPrompt = new Text("Script Num");
        var scriptNumField = new ReferenceField();
        scriptNumField.setPrefWidth(TEXT_FIELD_WIDTH);
        var scriptNumInvalid = new Text();
        scriptNumInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane scriptNumBoxAndValidation = new FlowPane();
        scriptNumBoxAndValidation.setHgap(GAP_SPACE);
        scriptNumBoxAndValidation
                .getChildren()
                .addAll(scriptNumField, scriptNumInvalid);
        addToGrid(gridPane, scriptNumPrompt, null, scriptNumBoxAndValidation);

        // Eighth row of scene elements.
        var notesPrompt = new Text("Notes");
        var notesField = new NotesField();
        notesField.setPrefWidth(TEXT_FIELD_WIDTH);
        var notesInvalid = new Text();
        notesInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane notesBoxAndValidation = new FlowPane();
        notesBoxAndValidation.setHgap(GAP_SPACE);
        notesBoxAndValidation
                .getChildren()
                .addAll(notesField, notesInvalid);
        addToGrid(
                gridPane,
                notesPrompt,
                null,
                notesBoxAndValidation);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        setUpOkCancelButtons(ok, cancel, buttons);
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
                openSqlErrorWindow(e.toString());
            }
        });

        constrainInput(qtyField);

        constrainInput(scriptNumField);

        constrainInputLowerCase(notesField);

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
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);

                if (isNoErrors) {
                    try {
                        db.addSupplyEntry(transfer);
                    } catch (SQLException e) {
                        openSqlErrorWindow(e.toString());
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

        addPatientButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWindowAddPatient();
            }
        });

        addPrescriberButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWindowAddPrescriber();
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
        var supplierComboBox = new ComboBox<Supplier>();
        supplierComboBox.setPrefWidth(COMBO_BOX_WIDTH);
        var supplierInvalid = new Text();
        supplierInvalid.setFont(new Font(ERROR_FONT_SIZE));
        HBox supplierBoxAndValidation = new HBox();
        supplierBoxAndValidation.spacingProperty().set(GAP_SPACE);
        supplierBoxAndValidation
                .getChildren()
                .addAll(supplierComboBox, supplierInvalid);
        addToGrid(
                gridPane,
                supplierPrompt,
                supplierField,
                supplierBoxAndValidation);

        // Second row of scene elements.
        var drugPrompt = new Text("Drug");
        var drugField = new DrugField();
        drugField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var drugComboBox = new ComboBox<Drug>();
        drugComboBox.setPrefWidth(COMBO_BOX_WIDTH);
        var drugInvalid = new Text();
        drugInvalid.setFont(new Font(ERROR_FONT_SIZE));
        HBox drugBoxAndValidation = new HBox();
        drugBoxAndValidation.spacingProperty().set(GAP_SPACE);
        drugBoxAndValidation
                .getChildren()
                .addAll(drugComboBox, drugInvalid);
        addToGrid(gridPane, drugPrompt, drugField, drugBoxAndValidation);

        // Third row of scene elements.
        var balancePrompt = new Text("Balance");
        var balanceField = new Text();
        addToGrid(gridPane, balancePrompt, null, balanceField);

        // Fourth row of scene elements.
        var qtyPrompt = new Text("Quantity");
        var qtyField = new QtyField();
        qtyField.setPrefWidth(NUM_FIELD_WIDTH);
        var qtyInvalid = new Text();
        qtyInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane qtyFieldAndValidation = new FlowPane();
        qtyFieldAndValidation.setHgap(GAP_SPACE);
        qtyFieldAndValidation
                .getChildren()
                .addAll(qtyField, qtyInvalid);
        addToGrid(gridPane, qtyPrompt, null, qtyFieldAndValidation);

        // Fifth row of scene elements.
        var pharmacistPrompt = new Text("Pharmacist");
        var pharmacistField = new PharmacistField();
        pharmacistField.setPrefWidth(TEXT_FIELD_SEARCH_WIDTH);
        var pharmacistComboBox = new ComboBox<Pharmacist>();
        pharmacistComboBox.setPrefWidth(COMBO_BOX_WIDTH);
        var pharmacistInvalid = new Text();
        pharmacistInvalid.setFont(new Font(ERROR_FONT_SIZE));
        HBox pharmacistBoxAndValidation = new HBox();
        pharmacistBoxAndValidation.spacingProperty().set(GAP_SPACE);
        pharmacistBoxAndValidation.getChildren().addAll(
                pharmacistComboBox,
                pharmacistInvalid);
        addToGrid(
                gridPane,
                pharmacistPrompt,
                pharmacistField,
                pharmacistBoxAndValidation);

        // Sixth row of scene elements.
        var invoicePrompt = new Text("Invoice Num");
        var invoiceField = new ReferenceField();
        invoiceField.setPrefWidth(TEXT_FIELD_WIDTH);
        var invoiceInvalid = new Text();
        invoiceInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane invoiceFieldAndValidation = new FlowPane();
        invoiceFieldAndValidation.setHgap(GAP_SPACE);
        invoiceFieldAndValidation
                .getChildren()
                .addAll(invoiceField, invoiceInvalid);
        addToGrid(gridPane, invoicePrompt, null, invoiceFieldAndValidation);

        // Seventh row of scene elements.
        var notesPrompt = new Text("Notes");
        var notesField = new NotesField();
        notesField.setPrefWidth(TEXT_FIELD_WIDTH);
        var notesInvalid = new Text();
        notesInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane notesFieldAndValidation = new FlowPane();
        notesFieldAndValidation.setHgap(GAP_SPACE);
        notesFieldAndValidation
                .getChildren()
                .addAll(notesField, notesInvalid);
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
                openSqlErrorWindow(e.toString());
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
                        .map(str -> str.isEmpty())
                        .reduce(true, (x, y) -> x && y);

                if (isNoErrors) {
                    try {
                        db.addReceiveEntry(transfer);
                    } catch (SQLException e) {
                        openSqlErrorWindow(e.toString());
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

    private void openWindowSearchByDrug() {

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
        var drugComboBox = new ComboBox<Drug>();
        drugComboBox.setPrefWidth(COMBO_BOX_WIDTH);
        var drugInvalid = new Text();
        drugInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane drugBoxAndValidation = new FlowPane();
        drugBoxAndValidation.setHgap(GAP_SPACE);
        drugBoxAndValidation
                .getChildren()
                .addAll(drugComboBox, drugInvalid);
        addToGrid(gridPane, drugPrompt, drugField, drugBoxAndValidation);

        // Second row of scene elements.
        var startDatePrompt = new Text("Start Date");
        var startDateField = new DatePicker();
        startDateField.setPrefWidth(DATE_PICKER_WIDTH);
        var startDateInvalid = new Text();
        startDateInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane startDateFieldAndValidation = new FlowPane();
        startDateFieldAndValidation.setHgap(GAP_SPACE);
        startDateFieldAndValidation
                .getChildren()
                .addAll(startDateField, startDateInvalid);
        addToGrid(gridPane, startDatePrompt, null, startDateFieldAndValidation);

        // Third row of scene elements.
        var endDatePrompt = new Text("End Date");
        var endDateField = new DatePicker();
        endDateField.setPrefWidth(DATE_PICKER_WIDTH);
        var endDateInvalid = new Text();
        endDateInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane endDateFieldAndValidation = new FlowPane();
        endDateFieldAndValidation.setHgap(GAP_SPACE);
        endDateFieldAndValidation
                .getChildren()
                .addAll(endDateField, endDateInvalid);
        addToGrid(gridPane, endDatePrompt, null, endDateFieldAndValidation);

        // Button.
        var ok = new Button("Search");
        ok.setPrefWidth(OK_CANCEL_BUTTON_WIDTH);
        var reverseEntryButton = new Button("↺");
        addTooltip(reverseEntryButton, "Reverse entry");
        var reverseEntryInvalid = new Text();
        reverseEntryInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren()
                .addAll(ok, reverseEntryButton, reverseEntryInvalid);
        addToGrid(gridPane, null, null, buttons);

        // Search Results.
        var tableView = new TableView<TransferSearchResult>();
        createTableLayout(tableView);
        addToGrid(gridPaneMain, tableView);

        // Event handlers.
        updateComboBox(drugField, drugComboBox);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var searchByDrug = new SearchByDrug(
                        drugComboBox.getValue(),
                        startDateField.getValue(),
                        endDateField.getValue());
                String[] errors = searchByDrug.validate();

                boolean isNoErrors = Arrays
                            .stream(errors)
                            .map(str -> str.isEmpty())
                            .reduce(true, (x, y) -> x && y);
                
                if (isNoErrors)
                {
                    try {
                        List<TransferSearchResult> list =
                                db.getTransfersByDrugList(searchByDrug);
                        tableView.setItems(
                                FXCollections.observableArrayList(list));

                    } catch (SQLException e) {
                        openSqlErrorWindow(e.toString());                        
                    }
                } 
                else {
                    // Create messages for invalid entries.
                    drugInvalid.setText(errors[0]);
                    startDateInvalid.setText(errors[1]);
                    endDateInvalid.setText(errors[2]);
                }
                
            }
        });

        reverseEntryButton.setOnAction(event -> {
            TransferSearchResult transferEntry =
                    tableView.getSelectionModel().getSelectedItem();
            if (transferEntry != null) {
                openWindowReverseEntry(transferEntry);
            } else {
                reverseEntryInvalid.setText("Select entry first");

            }
        });

        validationTextRemoval(reverseEntryButton, reverseEntryInvalid);

        stage.show();
    }

    private void openWindowSearchByDate() {

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
        var datePrompt = new Text("Date");
        var dateField = new DatePicker();
        dateField.setPrefWidth(DATE_PICKER_WIDTH);
        var reverseEntryButton  = new Button("↺");
        var reverseEntryTooltip = new Tooltip("Reverse entry");
        reverseEntryTooltip.setShowDelay(Duration.millis(TOOLTIP_DELAY));
        reverseEntryButton.setTooltip(reverseEntryTooltip);
        var reverseEntryInvalid = new Text();
        reverseEntryInvalid.setFont(new Font(ERROR_FONT_SIZE));
        addToGrid(
                gridPane,
                datePrompt,
                dateField,
                reverseEntryButton,
                reverseEntryInvalid);
        GridPane.setHalignment(reverseEntryButton, HPos.RIGHT);

        // Search Results
        var tableView = new TableView<TransferSearchResult>();
        createTableLayout(tableView);
        addToGrid(gridPaneMain, tableView);
        
        // Event handlers.
        dateField.setOnAction(event -> {

            updateTransferSearch(tableView, dateField);
        });

        reverseEntryButton.setOnAction(event -> {
            TransferSearchResult transferEntry =
                    tableView.getSelectionModel().getSelectedItem();
            
            if (transferEntry != null) {
                openWindowReverseEntry(transferEntry);
            } else {
                reverseEntryInvalid.setText("Select entry first");
            }
        });

        validationTextRemoval(reverseEntryButton, reverseEntryInvalid);

        stage.show();

        dateField.show();
        // Set date to today and and show list on opening.
        // dateField.setValue(LocalDate.now());
        // updateTransferSearch(tableView, dateField);
    }


    /*
     * A window to create a new entry to negate a previous entry.
     * Reasons for this are an error in the previous entry, or
     * cancellation of a prescription. Previous entries cannot not be
     * modified in a drugs of dependence register.
     */
    private void openWindowReverseEntry(
            TransferSearchResult transferEntry) {

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
        TableView<TransferSearchResult> tableViewSingleDrug = new TableView<>();
        createTableLayout(tableViewSingleDrug);
        tableViewSingleDrug.getColumns().remove(8, 10);
        tableViewSingleDrug.getColumns().remove(5, 7);
        tableViewSingleDrug.setPrefHeight(52);
        tableViewSingleDrug.getItems().add(transferEntry);
        mainGridPane.add(tableViewSingleDrug, 0, 0);

        // Sub-layout elements.
        Text notesPrompt = new Text("Notes");
        NotesField notesField = new NotesField();
        notesField.setPrefWidth(TEXT_FIELD_WIDTH);
        Text notesInvalid = new Text();
        notesInvalid.setFont(new Font(ERROR_FONT_SIZE));
        FlowPane notesFieldAndValidation = new FlowPane();
        notesFieldAndValidation.setHgap(GAP_SPACE);
        notesFieldAndValidation
                .getChildren()
                .addAll(notesField, notesInvalid);
        addToGrid(gridPane, notesPrompt, notesFieldAndValidation);

        // Second row of subelement
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        setUpOkCancelButtons(ok, cancel, buttons);
        addToGrid(gridPane, null, buttons);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    int balanceBefore = db.getBalance(transferEntry.getDrugId());
                    int balanceAfter =
                            balanceBefore
                            - transferEntry.getQtyIn()
                            + transferEntry.getQtyOut();

                    var reverseEntry = new ReverseEntry(
                        transferEntry.getTransferId(),
                        balanceAfter,
                        notesField.getText());
                    String[] errors = reverseEntry.validate();

                    boolean isNoErrors = Arrays
                            .stream(errors)
                            .map(str -> str.isEmpty())
                            .reduce(true, (x, y) -> x && y);

                    if (isNoErrors) {
                        db.reverseEntry(reverseEntry);
                        stage.close();
                    } else {
                        notesInvalid.setText(errors[0]);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    openSqlErrorWindow(e.toString());
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
    private void openSqlErrorWindow(String msg) {
        // Root layout.
        GridPane gridPane = new GridPane();
        setUpSpacing(gridPane);

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Database Error");
        stage.setScene(new Scene(
                gridPane, ERROR_WINDOW_WIDTH, ERROR_WINDOW_HEIGHT));
        var headerText = new Text("An error occurred with the database.");
        var messageArea = new TextArea(msg);
        messageArea.setWrapText(true);
        messageArea.setEditable(false);
        var ok = new Button("OK");
        ok.setPrefWidth(OK_CANCEL_BUTTON_WIDTH);
        gridPane.add(headerText, 0, 0);
        gridPane.add(messageArea, 0, 1);
        gridPane.add(ok, 0, 2);
        GridPane.setHalignment(ok, HPos.CENTER);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        stage.show();
    }

    /*
     * Creates table used to view drug transfers.
     */
    private void createTableLayout(TableView<TransferSearchResult> tableView) {

        var dateColumn =
                new TableColumn<TransferSearchResult, String>("Date");
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        dateColumn.setPrefWidth(DATE_WIDTH);

        var agentColumn =
                new TableColumn<TransferSearchResult, String>("Agent");
        agentColumn.setCellValueFactory(
                new PropertyValueFactory<>("agent"));
        agentColumn.setPrefWidth(AGENT_WIDTH);

        var drugColumn =
                new TableColumn<TransferSearchResult, String>("Drug");
        drugColumn.setCellValueFactory(
                new PropertyValueFactory<>("drug"));
        drugColumn.setPrefWidth(DRUG_WIDTH);

        var qtyInColumn =
                new TableColumn<TransferSearchResult, String>("In");
        qtyInColumn.setCellValueFactory(
                new PropertyValueFactory<>("qtyIn"));
        qtyInColumn.setPrefWidth(IN_OUT_WIDTH);
        
        var qtyOutColumn =
                new TableColumn<TransferSearchResult, String>("Out");
        qtyOutColumn.setCellValueFactory(
                new PropertyValueFactory<>("qtyOut"));
        qtyOutColumn.setPrefWidth(IN_OUT_WIDTH);
        
        var balanceColumn =
                new TableColumn<TransferSearchResult, String>("Balance");
        balanceColumn.setCellValueFactory(
                new PropertyValueFactory<>("balance"));
        balanceColumn.setPrefWidth(BALANCE_WIDTH);
        
        var prescriberColumn =
                new TableColumn<TransferSearchResult, String>("Prescriber");
        prescriberColumn.setCellValueFactory(
                new PropertyValueFactory<>("prescriber"));
        prescriberColumn.setPrefWidth(PRESCRIBER_WIDTH);
        
        var referenceColumn =
                new TableColumn<TransferSearchResult, String>("Reference");
        referenceColumn.setCellValueFactory(
                new PropertyValueFactory<>("reference"));
        referenceColumn.setPrefWidth(REFERENCE_WIDTH);

        var pharmacistColumn =
                new TableColumn<TransferSearchResult, String>("Pharmacist");
        pharmacistColumn.setCellValueFactory(
                new PropertyValueFactory<>("pharmacist"));
        pharmacistColumn.setPrefWidth(PHARMACIST_WIDTH);
        
        var notesColumn =
                new TableColumn<TransferSearchResult, String>("Notes");
                notesColumn.setCellValueFactory(
                new PropertyValueFactory<>("notes"));
        notesColumn.setPrefWidth(NOTES_WIDTH);

        tableView.getColumns().addAll(dateColumn, agentColumn, drugColumn);
        tableView.getColumns().addAll(qtyInColumn, qtyOutColumn, balanceColumn);
        tableView.getColumns().addAll(prescriberColumn, referenceColumn);
        tableView.getColumns().addAll(pharmacistColumn, notesColumn);
        tableView.setPrefSize(1420, 360);
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
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
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

    /**
     * 
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
                    comboBox.setItems(
                            FXCollections.observableArrayList(list));

                    comboBox.show();
                } catch (SQLException e) {
                    openSqlErrorWindow(e.toString());
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
                    openSqlErrorWindow(e.toString());
                }                
            }
        });
    }

    /*
     * Constrains input of a TextControl. Letters are converted to
     * all caps.
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

    /*
     * Constrains input of a TextControl. Lowercase letters are
     * retained.
     */
    private void constrainInputLowerCase(ValidatableField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                boolean fieldValid = field.validate(newValue);

                if (fieldValid) {
                    field.setText(newValue);
                } else {
                    field.setText(oldValue);
                }
            }
        });
    }

    /*
     * Updates the TableView to filter entries with the chosen date.
     */
    private void updateTransferSearch(
            TableView<TransferSearchResult> tableView,
            DatePicker dateField) {
        try {
            LocalDate localDate = dateField.getValue();

            if (localDate != null) {
                List<TransferSearchResult> list =
                        db.getTransfersByDateList(localDate);
                tableView.setItems(
                        FXCollections.observableArrayList(list));
            }
        } catch (SQLException e) {
            openSqlErrorWindow(e.toString());
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
