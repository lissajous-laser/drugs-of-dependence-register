package com.lissajouslaser.control;

import com.lissajouslaser.Transfer;

import javafx.scene.control.TextField;

public class QtyField extends TextField implements ValidatableField {
    /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return Transfer.validateQty(newValue) == null;
    }
}
