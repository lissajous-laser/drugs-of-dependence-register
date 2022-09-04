package com.lissajouslaser.control;

import com.lissajouslaser.Agent;

import javafx.scene.control.TextArea;

/*
 * A JavaFx TextArea that can be validated to meet the
 * requirements of an address.
 */
public class AddressArea extends TextArea implements ValidatableField {
    
    /**
     * Returns whether the text in the field meets validation
     * requirements.
     */
    public boolean validate(String newValue) {
        return Agent.validateAddress(newValue) == null;
    }
}
