package com.lissajouslaser.control;

import com.lissajouslaser.Person;
import javafx.scene.control.TextField;

/**
 * A JavaFx TextField that can be validated to meet the
 * requirements of a first name.
 */
public class FirstNameField extends TextField implements ValidatableField {
    /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return Person.validateFirstName(newValue) == null;
    }
}
