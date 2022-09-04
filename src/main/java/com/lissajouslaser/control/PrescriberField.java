package com.lissajouslaser.control;

import java.sql.SQLException;
import java.util.List;

import com.lissajouslaser.DatabaseConnection;
import com.lissajouslaser.Prescriber;

import javafx.scene.control.TextField;

/**
 * A textfield that searches Prescribers in the database.
 */
public class PrescriberField
        extends TextField
        implements SearchField<Prescriber> {
    /**
     * Returns list of Prescribers where the first part of their
     * surname matches the input text.
     */
    public List<Prescriber> getList(DatabaseConnection db) 
            throws SQLException {
        return db.getPrescribersList(getText());
    }  
}
