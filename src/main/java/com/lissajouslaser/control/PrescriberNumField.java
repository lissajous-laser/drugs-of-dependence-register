package com.lissajouslaser.control;

import com.lissajouslaser.Prescriber;
import javafx.scene.control.TextField;

/*
 * A JavaFx TextField that can be validated to meet the
 * requirements of a prescriber number.
 */
public class PrescriberNumField extends TextField implements ValidatableField {
    /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return Prescriber.validatePrescriberNumber(newValue).isEmpty();
    }
}
