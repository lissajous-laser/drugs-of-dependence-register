package com.lissajouslaser.control;

import java.sql.SQLException;
import java.util.List;

import com.lissajouslaser.DatabaseConnection;
import com.lissajouslaser.Supplier;

import javafx.scene.control.TextField;

/**
 * A textfield that searches Drugs in the database.
 */
public class SupplierField
        extends TextField
        implements SearchField<Supplier> {
    /**
     * Returns list of Suppliers where the first part of their
     * surname matches the input text.
     */
    public List<Supplier> getList(DatabaseConnection db) 
            throws SQLException {
        return db.getSuppliersList(getText());

    }    
}
