package com.lissajouslaser.control;

import com.lissajouslaser.DatabaseConnection;
import com.lissajouslaser.Patient;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.TextField;

/**
 * A textfield that searches Prescribers in the database.
 */
public class PatientField
        extends TextField
        implements SearchField<Patient> {
    /**
     * Returns list of Prescribers where the first part of their
     * surname matches the input text.
     */
    public List<Patient> getList(DatabaseConnection db) 
            throws SQLException {
        return db.getPatientsList(getText());
    }    
}
