package com.lissajouslaser.control;

import com.lissajouslaser.Transfer;

/*
 * A JavaFx TextField that can be validated to meet the
 * requirements of a notes section.
 */
public class NotesField extends ValidatableField {

    /**
     * Creates a TextArea with empty text content.
     */
    public NotesField() {
        super();
    }   

     /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return Transfer.validateNotes(newValue).isEmpty();
    }


}
