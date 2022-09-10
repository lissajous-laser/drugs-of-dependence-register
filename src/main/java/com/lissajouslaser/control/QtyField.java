package com.lissajouslaser.control;

import com.lissajouslaser.TransferInput;

public class QtyField extends ValidatableField {
    /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return TransferInput.validateQty(newValue).isEmpty();
    }
}
