package com.lissajouslaser.stage;

import com.lissajouslaser.DatabaseConnection;
import com.lissajouslaser.control.SearchField;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/*
 * A window that searches transfers.
 */
abstract class SearchWindow extends Stage {
    // For the largest subwindows, e.g. Search By Drug.
    static final int SUBWINDOW_C_WIDTH = 1450;
    static final int SUBWINDOW_C_HEIGHT = 560;
    // Other parameters.
    static final int DATE_PICKER_WIDTH = 120;
    static final int TEXT_FIELD_SEARCH_WIDTH = 70;
    static final int ERROR_FONT_SIZE = 11;
    static final int GAP_SPACE = 8;
    static final int OK_CANCEL_BUTTON_WIDTH = 80;
    DatabaseConnection db;

    SearchWindow(DatabaseConnection db) {
        super();
        this.db = db;
    }

      /*
     * Updates the list in a ComboBox according to the input of
     * a TextField.
     */
    <T> void updateComboBox(
            SearchField<T> field,
            ComboBox<T> comboBox) {
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
                    var errorWindow = new SqlErrorWindow(e.toString());
                    errorWindow.show();
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
                    var errorWindow = new SqlErrorWindow(e.toString());
                    errorWindow.show();
                }
            }
        });
    }
}
