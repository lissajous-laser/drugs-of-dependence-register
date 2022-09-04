package com.lissajouslaser.control;

import com.lissajouslaser.Transfer;
import javafx.scene.control.TextField;

/*
 * A JavaFx TextField that can be validated to meet the
 * requirements of a notes section.
 */
public class NotesField extends TextField implements ValidatableField {
     /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return Transfer.validateNotes(newValue) == null;
    }   
}
