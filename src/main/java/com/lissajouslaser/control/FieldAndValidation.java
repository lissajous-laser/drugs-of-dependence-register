package com.lissajouslaser.control;

import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * A paring of an input control and an
 * input validation message. Contstrains text field
 * so that all letters are all caps, and the rules set
 * by the ValidatableField's validate() method.
 */
public class FieldAndValidation extends HBox {
    static final int TEXT_FIELD_WIDTH = 250;
    static final int NUM_FIELD_WIDTH = 70;
    static final int ERROR_FONT_SIZE = 11;
    static final  int GAP_SPACE = 8;
    private ValidatableField field;
    private Text text;

    /**
     * Creates a paired ValidatableField and Text. Input letters
     * are constrained to all caps.
     * @param field the type of ValidatableField to be used.
     */
    public FieldAndValidation(ValidatableField field) {
        this(field, true);
    }

    /**
     * Creates a paired ValidatableField and Text.
     * field - the type of ValidatableField to be used.
     * isAllCaps - specifies if input letters are constrained to
     * all caps.
     */
    public FieldAndValidation(ValidatableField field, boolean allCaps) {
        super();

        this.field = field;

        if (field instanceof QtyField) {
            this.field.setPrefWidth(NUM_FIELD_WIDTH);
        } else {
            this.field.setPrefWidth(TEXT_FIELD_WIDTH);
        }

        text = new Text();
        text.setFont(new Font(ERROR_FONT_SIZE));

        spacingProperty().set(GAP_SPACE);
        getChildren().addAll(field, text);

        if (allCaps) {
            setFieldListenerAllCaps();
        } else {
            setFieldListener();
        }
    }

    private void setFieldListenerAllCaps() {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            String newValueUpperCase = newValue.toUpperCase();
            boolean fieldValid = field.validate(newValueUpperCase);

            if (fieldValid) {
                field.setText(newValueUpperCase);
            } else {
                field.setText(oldValue);
            }
        });
    }

    private void setFieldListener() {
        field.textProperty().addListener((oberservable, oldValue, newValue) -> {
            boolean fieldValid = field.validate(newValue);

            if (fieldValid) {
                field.setText(newValue);
            } else {
                field.setText(oldValue);
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
