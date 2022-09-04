package com.lissajouslaser.control;

import com.lissajouslaser.Pharmacist;

import javafx.scene.control.TextField;

/*
 * A JavaFx TextField that can be validated to meet the
 * requirements of a pharmacist registration.
 */
public class RegistrationField extends TextField implements ValidatableField {
    /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return Pharmacist.validateRegistration(newValue) == null;
    }  
}
