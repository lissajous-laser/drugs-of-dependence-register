package com.lissajouslaser.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AddressAreaAndValidation extends HBox {
    static final int TEXT_AREA_WIDTH = 250;
    static final int TEXT_AREA_HEIGHT = 60;
    static final int ERROR_FONT_SIZE = 11;
    static final int GAP_SPACE = 8;
    private AddressArea addressArea;
    private Text text;

    /**
     * Constructor.
     */
    public AddressAreaAndValidation() {
        super();

        addressArea = new AddressArea();
        addressArea.setPrefSize(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
        addressArea.setWrapText(true);

        text = new Text();
        text.setFont(new Font(ERROR_FONT_SIZE));

        spacingProperty().set(GAP_SPACE);
        getChildren().addAll(addressArea, text);

        addressArea.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(
                        ObservableValue<? extends String> observable,
                        String oldValue,
                        String newValue) {
                    boolean fieldValid = addressArea.validate(newValue);
    
                    if (fieldValid) {
                        addressArea.setText(newValue);
                    } else {
                        addressArea.setText(oldValue);
                    }
                }
        });
    }

    /**
     * Gets get text shown in the field.
     */
    public String getFieldText() {
        return addressArea.getText();
    }

    /**
     * Sets the text to show in the text instance variable.
     */
    public void setValidationText(String msg) {
        text.setText(msg);
    }
}
