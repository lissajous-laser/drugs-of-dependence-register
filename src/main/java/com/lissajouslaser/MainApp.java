package com.lissajouslaser;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class for starting GUI application.
 */
public class MainApp extends Application {
    private static Stage stage;

    @Override
    public void start(@SuppressWarnings("exports") Stage s) throws IOException {
        final int addPrescriberWindowWidth = 500;
        final int addPrescriberWindowHeight = 350;
        Database db = new Database();

        s.setScene(new Scene(
                addPrescriberLayout(),
                addPrescriberWindowWidth,
                addPrescriberWindowHeight
        ));
        s.show();

    }

    private GridPane addPrescriberLayout() {

        var firstNamePrompt = new Text("First name");
        var firstNameField = new TextField();
        final int fieldWidth = 200;
        firstNameField.setMaxWidth(fieldWidth);
        var firstNameInvalid = new Text();

        var lastNamePrompt = new Text("Last name");
        var lastNameField = new TextField();
        lastNameField.setMaxWidth(fieldWidth);
        var lastNameInvalid = new Text();
        var prescriberNumPrompt = new Text("Prescriber number");
        var prescriberNumField = new TextField();
        prescriberNumField.setMaxWidth(fieldWidth);
        var prescriberNumInvalid = new Text();

        var ok = new Button("OK");
        var cancel = new Button("Cancel");
        FlowPane buttons = new FlowPane();
        final int gapSpace = 7;
        buttons.setHgap(gapSpace);
        buttons.getChildren().addAll(ok, cancel);

        ok.setOnAction(new EventHandler<ActionEvent>()  {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("did it");
            }
        });

        GridPane addPrescriberGrid = new GridPane();
        addPrescriberGrid.setHgap(gapSpace);
        addPrescriberGrid.setVgap(gapSpace);
        final int paddingSpace = 15;
        addPrescriberGrid.setPadding(new Insets(
                paddingSpace,
                paddingSpace,
                paddingSpace,
                paddingSpace
        ));
        final int fourthRow = 4;

        addPrescriberGrid.add(firstNamePrompt, 0, 0);
        addPrescriberGrid.add(firstNameField, 1, 0);
        addPrescriberGrid.add(firstNameInvalid, 2, 0);
        addPrescriberGrid.add(lastNamePrompt, 0, 1);
        addPrescriberGrid.add(lastNameField, 1, 1);
        addPrescriberGrid.add(lastNameInvalid, 2, 1);
        addPrescriberGrid.add(prescriberNumPrompt, 0, 2);
        addPrescriberGrid.add(prescriberNumField, 1, 2);
        addPrescriberGrid.add(prescriberNumInvalid, 2, 2);
        addPrescriberGrid.add(buttons, 1, fourthRow);

        return addPrescriberGrid;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
