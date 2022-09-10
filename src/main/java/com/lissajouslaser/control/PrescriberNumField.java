package com.lissajouslaser.control;

import com.lissajouslaser.Prescriber;

/*
 * A JavaFx TextField that can be validated to meet the
 * requirements of a prescriber number.
 */
public class PrescriberNumField extends ValidatableField {
    /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return Prescriber.validatePrescriberNumber(newValue).isEmpty();
    }
}
