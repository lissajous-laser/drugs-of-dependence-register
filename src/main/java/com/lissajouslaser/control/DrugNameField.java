package com.lissajouslaser.control;

import com.lissajouslaser.Drug;
import javafx.scene.control.TextField;

/**
 * A JavaFx TextField that can be validated to meet the
 * requirements of a drug name.
 */
public class DrugNameField extends TextField implements ValidatableField {
    /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return Drug.validateName(newValue).isEmpty();
    }
}
