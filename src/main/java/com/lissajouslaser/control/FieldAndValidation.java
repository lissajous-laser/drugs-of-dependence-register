package com.lissajouslaser.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * A paring of an input control and an input validation message.
 * Contstrains text field to the rules set by the
 * ValidatableField's validate() method.
 */
public class FieldAndValidation extends HBox {
    static final int TEXT_FIELD_WIDTH = 250;
    static final int ERROR_FONT_SIZE = 11;
    static final int GAP_SPACE = 8;
    private ValidatableField field;
    private Text text;
 
    /**
     * Constructor.
     */
    public FieldAndValidation(ValidatableField field) {
        super();

        this.field = field;
        this.field.setPrefWidth(TEXT_FIELD_WIDTH);

        text = new Text();
        text.setFont(new Font(ERROR_FONT_SIZE));

        spacingProperty().set(GAP_SPACE);
        getChildren().addAll(field, text);

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

    /**
     * Gets get text shown in the field.
     */
    public String getFieldText() {
        return field.getText();
    }

    /**
     * Sets the text to show in the text instance variable.
     */
    public void setValidationText(String msg) {
        text.setText(msg);
    }
}
