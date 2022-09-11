package com.lissajouslaser.stage;


import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
* A window displaying an error message. Used for
* SQLExceptions.
*/
public class SqlErrorWindow extends Stage {
    // For error message from SQLExceptions.
    static final int ERROR_WINDOW_HEIGHT = 160;
    static final int ERROR_WINDOW_WIDTH = 480;
    static final int OK_CANCEL_BUTTON_WIDTH = 80;

    /**
     * Constructor.
     */
    public SqlErrorWindow(String msg) {
        super();

        // Root layout.
        GridPane gridPane = new GridPane();
        Util.setUpGridSpacing(gridPane);

        
        setTitle("Database Error");
        setScene(new Scene(
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

        Util.closeOnButtonPress(ok, this);

    }
}
