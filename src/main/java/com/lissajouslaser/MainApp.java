package com.lissajouslaser;

import java.io.IOException;
import java.util.Arrays;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class for starting GUI application.
 */
public class MainApp extends Application {
    static final int SUBWINDOW_WIDTH = 600;
    static final int SUBWINDOW_HEIGHT = 300;
    static final int TEXT_FIELD_WIDTH = 300;
    static final int TEXT_AREA_HEIGHT = 60;
    static final int GAP_SPACE = 7;
    static final int PADDING_SPACE = 15;
    static final int ERROR_FONT_SIZE = 10;
    static final int LIST_VIEW_WIDTH = 400;
    static final int LIST_VIEW_HEIGHT = 300;
    static final int NUM_FIELD_WIDTH = 55;

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
        final int subWindowWidth = 600;
        final int subWindowHeight = 300;

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
        var addTransferButton = new Button("Add Transfer");

        mainWindowGrid.add(addPatientButton, 0, 0);
        mainWindowGrid.add(addPrescriberButton, 1, 0);
        mainWindowGrid.add(addDrugButton, 2, 0);
        mainWindowGrid.add(addPharmacistButton, 3, 0);
        mainWindowGrid.add(addTransferButton, 0, 1);

        addPrescriberButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWindowAddPrescriber();
            }
        });

        addPharmacistButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Stage addPharmacistWindow = new Stage();
                addPharmacistWindow.setTitle("Add Pharmacist");
                addPharmacistWindow.setScene(new Scene(
                        addPharmacistLayout(addPharmacistWindow),
                        subWindowWidth,
                        subWindowHeight));
                addPharmacistWindow.show();
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

        addTransferButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWindowAddTransfer();
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
        gridPane.setHgap(GAP_SPACE);
        gridPane.setVgap(GAP_SPACE);
        gridPane.setPadding(new Insets(
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE));

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Drug");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_WIDTH, SUBWINDOW_HEIGHT));

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

                System.out.println(error); ////
                if (error == null) {
                    nameField.setText(newValueUpperCase);
                } else if ("Must fill in".equals(error)) {
                    // Allow field to become empty, otherwise can't
                    // modify a field.
                    nameField.setText("");
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

                System.out.println(error); ////
                if (error == null) {
                    strengthField.setText(newValueUpperCase);
                } else if ("Must fill in".equals(error)) {
                    // Allow field to become empty, otherwise can't
                    // modify a field.
                    strengthField.setText("");
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

                System.out.println(error); ////
                if (error == null) {
                    doseFormField.setText(newValueUpperCase);
                } else if ("Must fill in".equals(error)) {
                    // Allow field to become empty, otherwise can't
                    // modify a field.
                    doseFormField.setText("");
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
                ;
                String[] errors = drug.validate();

                boolean isNoErrors = Arrays
                        .stream(errors)
                        .map(str -> str == null)
                        .reduce(true, (x, y) -> x && y);
                if (isNoErrors) {
                    if (db.addDrug(drug)) {
                        stage.close();
                    } else {
                        // In wrong text spot for now.
                        nameInvalid.setText("An error was enountered , "
                                + "entry could not complete");
                    }
                    ;
                } else {
                    nameInvalid.setText(errors[0]);
                    strengthInvalid.setText(errors[1]);
                    doseFormInvalid.setText(errors[2]);
                }
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        stage.show();
    }

    /**
     * The Add Patient window, the UI for adding
     * a patient to the database.
     */
    private void openWindowAddPatient() {

        // Root layout.
        GridPane gridPane = new GridPane();
        gridPane.setHgap(GAP_SPACE);
        gridPane.setVgap(GAP_SPACE);
        gridPane.setPadding(new Insets(
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE));

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Patient");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_WIDTH, SUBWINDOW_HEIGHT));

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
        configureFirstNameFieldListener(firstNameField);

        configureLastNameFieldListener(lastNameField);

        addressField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();
                String error = Patient.validateAddress(newValueUpperCase);

                System.out.println(error); ////
                if (error == null) {
                    addressField.setText(newValueUpperCase);
                } else if ("Must fill in".equals(error)) {
                    // Allow field to become empty, otherwise can't
                    // modify a field.
                    addressField.setText("");
                } else {
                    addressField.setText(oldValue);
                }
            }
        });

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
                        // In wrong text spot for now.
                        firstNameInvalid.setText("An error was enountered , "
                                + "entry could not complete");
                    }
                    ;
                } else {
                    firstNameInvalid.setText(errors[0]);
                    lastNameInvalid.setText(errors[1]);
                    addressInvalid.setText(errors[2]);
                }
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        stage.show();
    }

    /**
     * The Add Pharmacist window, the UI for adding
     * a pharmacist to the database.
     */
    private GridPane addPharmacistLayout(Stage window) {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(GAP_SPACE);
        gridPane.setVgap(GAP_SPACE);

        gridPane.setPadding(new Insets(
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE));

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

        configureFirstNameFieldListener(firstNameField);

        configureLastNameFieldListener(lastNameField);

        registrationField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();
                String error = Pharmacist.validateRegistration(newValueUpperCase);

                System.out.println(error); ////
                if (error == null) {
                    registrationField.setText(newValueUpperCase);
                } else if ("Must fill in".equals(error)) {
                    // Allow field to become empty, otherwise can't
                    // modify a field.
                    registrationField.setText("");
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
                        window.close();
                    } else {
                        // In wrong text spot for now.
                        firstNameInvalid.setText("An error was enountered , "
                                + "entry could not complete");
                    }
                    ;
                } else {
                    firstNameInvalid.setText(errors[0]);
                    lastNameInvalid.setText(errors[1]);
                    registrationInvalid.setText(errors[2]);
                }
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.close();
            }
        });
        return gridPane;
    }

    /**
     * The Add Prescriber window, the UI for adding
     * a prescriber to the database.
     */
    private void openWindowAddPrescriber() {

        // Root layout.
        GridPane gridPane = new GridPane();
        gridPane.setHgap(GAP_SPACE);
        gridPane.setVgap(GAP_SPACE);
        gridPane.setPadding(new Insets(
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE));

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Prescriber");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_WIDTH, SUBWINDOW_HEIGHT));

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
        configureFirstNameFieldListener(firstNameField);

        configureLastNameFieldListener(lastNameField);

        prescriberNumField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();
                String error = Prescriber.validatePrescriberNumber(newValueUpperCase);

                System.out.println(error); ////
                if (error == null) {
                    prescriberNumField.setText(newValueUpperCase);
                } else if ("Must fill in".equals(error)) {
                    // Allow field to become empty, otherwise can't
                    // modify a field.
                    prescriberNumField.setText("");
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
                        // In wrong text spot for now.
                        firstNameInvalid.setText("An error was enountered , "
                                + "entry could not complete");
                    }
                    ;
                } else {
                    // Create messages for invalid field entries.
                    firstNameInvalid.setText(errors[0]);
                    lastNameInvalid.setText(errors[1]);
                    prescriberNumInvalid.setText(errors[2]);
                }
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }

        });

        stage.show();
    }

    /**
     * The Add Transfer window, the UI for adding
     * a transfer to teh database.
     */
    private void openWindowAddTransfer() {

        // Root layout.
        GridPane gridPane = new GridPane();
        gridPane.setHgap(GAP_SPACE);
        gridPane.setVgap(GAP_SPACE);
        gridPane.setPadding(new Insets(
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE,
                PADDING_SPACE));

        // Stage.
        Stage stage = new Stage();
        stage.setTitle("Add Patient");
        stage.setScene(new Scene(
                gridPane, SUBWINDOW_WIDTH, SUBWINDOW_HEIGHT));

        // First row of scene elements.
        var agentPrompt = new Text("Agent");
        var agentField = new TextField();
        agentField.setMaxWidth(TEXT_FIELD_WIDTH);
        gridPane.add(agentPrompt, 0, 0);
        gridPane.add(agentField, 1, 0);

        // Second row of scene elements.
        var drugPrompt = new Text("Drug");
        var drugField = new TextField();
        drugField.setMaxWidth(TEXT_FIELD_WIDTH);
        gridPane.add(drugPrompt, 0, 1);
        gridPane.add(drugField, 1, 1);

        // Third row of scene elements.
        var qtyPrompt = new Text("Quantity");
        var qtyField = new TextField();
        qtyField.setMaxWidth(NUM_FIELD_WIDTH);
        gridPane.add(qtyPrompt, 0, 2);
        gridPane.add(qtyField, 1, 2);

        // Fourth row of scene elements.
        var balancePrompt = new Text("Balance");
        var balanceField = new TextField();
        balanceField.setMaxWidth(NUM_FIELD_WIDTH);
        balanceField.setEditable(false);
        gridPane.add(balancePrompt, 0, 3);
        gridPane.add(balanceField, 1, 3);

        // Buttons.
        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        buttons.setHgap(GAP_SPACE);
        buttons.getChildren().addAll(ok, cancel);
        gridPane.add(buttons, 1, 4);

        // Event handlers.
        agentField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {

                var selectAgentWindow = new Stage();

                var selectAgentScene = new Scene(
                        selectAgentLayout(agentField.getText()),
                        LIST_VIEW_WIDTH,
                        LIST_VIEW_HEIGHT);

                selectAgentWindow.setScene(selectAgentScene);

                selectAgentScene.setOnKeyPressed(event1 -> {
                    if (event1.getCode() == KeyCode.ENTER) {
                        selectAgentWindow.close();
                    }
                });

                selectAgentWindow.show();

            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        stage.show();
    }

    private HBox selectAgentLayout(String searchTerm) {
        ListView<IAgent> agentsList = new ListView<>();
        agentsList.setMinWidth(LIST_VIEW_WIDTH);

        ObservableList<IAgent> items = FXCollections.observableArrayList(
                db.getAgentAsList(searchTerm));

        agentsList.setItems(items);

        var hBox = new HBox();
        hBox.getChildren().add(agentsList);
        return hBox;
    }

    void configureFirstNameFieldListener(TextField firstNameField) {
        firstNameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();
                String error = Person.validateFirstName(newValueUpperCase);

                System.out.println(error); ////
                if (error == null) {
                    firstNameField.setText(newValueUpperCase);
                } else if ("Must fill in".equals(error)) {
                    // Allow field to become empty, otherwise can't
                    // modify a field.
                    firstNameField.setText("");
                } else {
                    firstNameField.setText(oldValue);
                }
            }
        });
    }

    void configureLastNameFieldListener(TextField lastNameField) {
        lastNameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                String newValueUpperCase = newValue.toUpperCase();
                String error = Person.validateLastName(newValueUpperCase);

                System.out.println(error); ////
                if (error == null) {
                    lastNameField.setText(newValueUpperCase);
                } else if ("Must fill in".equals(error)) {
                    // Allow field to become empty, otherwise can't
                    // modify a field.
                    lastNameField.setText("");
                } else {
                    lastNameField.setText(oldValue);
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
