package com.lissajouslaser.control;

import com.lissajouslaser.Supplier;
import javafx.scene.control.TextField;

/*
 * A JavaFx TextField that can be validated to meet the
 * requirements of a supplier name.
 */
public class SupplierNameField extends TextField implements ValidatableField {
    /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return Supplier.validateName(newValue) == null;
    }    
}
