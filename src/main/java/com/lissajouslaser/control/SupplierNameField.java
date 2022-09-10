package com.lissajouslaser.control;

import com.lissajouslaser.Supplier;

/*
 * A JavaFx TextField that can be validated to meet the
 * requirements of a supplier name.
 */
public class SupplierNameField extends ValidatableField {
    /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return Supplier.validateName(newValue).isEmpty();
    }    
}
