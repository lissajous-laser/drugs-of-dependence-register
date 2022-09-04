package com.lissajouslaser.control;

import java.sql.SQLException;
import java.util.List;

import com.lissajouslaser.DatabaseConnection;
import com.lissajouslaser.Drug;

import javafx.scene.control.TextField;

/**
 * A textfield that searches Drugs in the database.
 */
public class DrugField extends TextField implements SearchField<Drug> {
    /**
     * Returns list of Pharmacists where the first part of their
     * surname matches the input text.
     */
    public List<Drug> getList(DatabaseConnection db) 
            throws SQLException {
        return db.getDrugsList(getText());

    }    
}
