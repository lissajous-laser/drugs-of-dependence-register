package com.lissajouslaser.control;

import com.lissajouslaser.Drug;

/**
 * A JavaFx TextField that can be validated to meet the
 * requirements of a drug strength.
 */
public class StrengthField extends ValidatableField {
    /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return Drug.validateStrength(newValue).isEmpty();
    }
}
