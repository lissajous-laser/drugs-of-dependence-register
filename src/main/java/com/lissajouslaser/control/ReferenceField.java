package com.lissajouslaser.control;

import com.lissajouslaser.TransferInput;

/*
 * A JavaFx TextField that can be validated to meet the
 * requirements of reference: either a script number for a supply
 * to patient or an invoice number from a received order from a
 * supplier.
 */
public class ReferenceField extends ValidatableField {
    /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return TransferInput.validateReference(newValue).isEmpty();
    }
}
