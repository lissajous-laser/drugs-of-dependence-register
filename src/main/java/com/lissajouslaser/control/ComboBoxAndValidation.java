package com.lissajouslaser.control;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ComboBoxAndValidation<T> extends HBox {
    static final int TEXT_AREA_WIDTH = 250;
    static final int TEXT_AREA_HEIGHT = 60;
    static final int ERROR_FONT_SIZE = 11;
    static final int GAP_SPACE = 8;
    static final int COMBO_BOX_WIDTH = 320;
    private ComboBox<T> comboBox;
    private Text text;

    /**
     * Standard constructor.
     */
    public ComboBoxAndValidation() {
        super();

        this.comboBox = new ComboBox<>();
        comboBox.setPrefWidth(COMBO_BOX_WIDTH);

        text = new Text();
        text.setFont(new Font(ERROR_FONT_SIZE));

        spacingProperty().set(GAP_SPACE);
        getChildren().addAll(comboBox, text);
    }

    /**
     * Constructor for adding a Button between the ComboBox and Text.
     */
    public ComboBoxAndValidation(Control control) {
        super();

        this.comboBox = new ComboBox<>();
        comboBox.setPrefWidth(COMBO_BOX_WIDTH);

        text = new Text();
        text.setFont(new Font(ERROR_FONT_SIZE));

        spacingProperty().set(GAP_SPACE);
        getChildren().addAll(comboBox, control, text);
    }

    public ComboBox<T> getComboBox() {
        return comboBox;
    }

    public T getComboBoxValue() {
        return comboBox.getValue();
    }

    public void setValidationText(String msg) {
        text.setText(msg);
    }

}
