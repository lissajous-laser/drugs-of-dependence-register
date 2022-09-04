package com.lissajouslaser.control;

import com.lissajouslaser.Person;

import javafx.scene.control.TextField;

/*
 * A JavaFx TextField that can be validated to meet the
 * requirements of a last name.
 */
public class LastNameField extends TextField implements ValidatableField {
    /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return Person.validateLastName(newValue) == null;
    }
}
