package com.lissajouslaser.control;

import com.lissajouslaser.Transfer;

import javafx.scene.control.TextField;

/*
 * A JavaFx TextField that can be validated to meet the
 * requirements of reference: either a script number for a supply
 * to patient or an invoice number from a received order from a
 * supplier.
 */
public class ReferenceField extends TextField implements ValidatableField {
    /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return Transfer.validateReference(newValue) == null;
    }
}
