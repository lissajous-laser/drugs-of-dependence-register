package com.lissajouslaser.control;

import com.lissajouslaser.Transfer;
import javafx.scene.control.TextField;

/*
 * A JavaFx TextField that can be validated to meet the
 * requirements of a notes section.
 */
public class NotesField extends TextField implements ValidatableField {
    /**
     * Creates a NoteField with initial text content.
     */
    public NotesField(String text) {
        super(text);
    }

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
