package com.lissajouslaser.control;

import javafx.beans.property.StringProperty;

/**
 * An interface for JavaFX TextInputControls that can have their
 * inputs validated. textProperty() and setText() are methods that
 * will be inherited from the JavaFX TextInputControl abstract
 * class.
 */
public interface ValidatableField {
    public boolean validate(String newValue);

    /**
     * The textual content of this TextInputControl.
     */
    public StringProperty textProperty();

    /**
     * Sets the value of the property text.
     */
    public void setText(String value);
}
