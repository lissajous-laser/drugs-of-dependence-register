package com.lissajouslaser.control;

import java.sql.SQLException;
import java.util.List;

import com.lissajouslaser.DatabaseConnection;

import javafx.beans.property.StringProperty;

/**
 * An interface for JavaFX TextField whose input is used to filter
 * the list of a JavaFX ComboBox. textProperty() and setText() are
 * methods that will be inherited from the JavaFX TextInputControl
 * abstract class.
 */
public interface SearchField<T> {

    public List<T> getList(DatabaseConnection db) throws SQLException;

    /**
     * The textual content of this TextInputControl.
     */
    public StringProperty textProperty();

    /**
     * Sets the value of the property text.
     */
    public void setText(String name);
}
