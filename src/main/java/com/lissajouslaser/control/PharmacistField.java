package com.lissajouslaser.control;

import com.lissajouslaser.DatabaseConnection;
import com.lissajouslaser.Pharmacist;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.TextField;

/**
 * A textfield that searches Pharmacists in the database.
 */
public class PharmacistField
        extends TextField
        implements SearchField<Pharmacist> {
    /**
     * Returns list of Pharmacists where the first part of their
     * surname matches the input text.
     */
    public List<Pharmacist> getList(DatabaseConnection db) 
            throws SQLException {
        return db.getPharmacistsList(getText());

    }    
}
