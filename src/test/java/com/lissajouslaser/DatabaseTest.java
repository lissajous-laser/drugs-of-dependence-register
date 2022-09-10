package com.lissajouslaser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Database class.
 */
public class DatabaseTest {
    static final String TEST_DB_FILE = "_test.db";
    DatabaseConnection db;

    @BeforeEach
    void createDatabase() throws SQLException {
        db = new DatabaseConnection(TEST_DB_FILE);
    }

    @AfterEach
    void deleteDatabase() throws SQLException {
        db.getConnection().close();

        File file = new File(TEST_DB_FILE);
        file.delete();
    }

    @Test
    void addingPrescriberWorks() throws SQLException {
        Prescriber prescriber = new Prescriber(
                "William",
                "Wallace",
                "3917689");
        db.addPrescriber(prescriber);

        Statement statement = db.getConnection().createStatement();
        statement.execute("SELECT * FROM prescribers;");
        
        ResultSet results = statement.getResultSet();
        String firstName = results.getString(2);
        String lastName = results.getString(3);
        String prescriberNum = results.getString(4);

        assertEquals(
                "WILLIAMWALLACE3917689",
                firstName + lastName + prescriberNum
        );
    }

    @Test
    void addingSecondPrescriberWorks() throws SQLException {
        Prescriber prescriber1 = new Prescriber(
                "William",
                "Wallace",
                "3917689");
        db.addPrescriber(prescriber1);

        Prescriber prescriber2 = new Prescriber(
                "Ada",
                "Lovelace",
                "2638312"
        );
        db.addPrescriber(prescriber2);

        Statement statement = db.getConnection().createStatement();
        statement.execute("SELECT * FROM prescribers ORDER BY id DESC;");

        ResultSet results =  statement.getResultSet();
        String firstName = results.getString(2);
        String lastName = results.getString(3);
        String prescriberNum = results.getString(4);

        assertEquals(
                "ADALOVELACE2638312",
                firstName + lastName + prescriberNum
        );
    }

    @Test
    void addingPharmacistWorks() throws SQLException {
        Pharmacist pharmacist = new Pharmacist(
                "Florence",
                "Nightingale",
                "PHA0000073561");
        db.addPharmacist(pharmacist);

        Statement statement = db.getConnection().createStatement();
        statement.execute("SELECT * FROM pharmacists;");
        
        ResultSet results = statement.getResultSet();
        String firstName = results.getString(2);
        String lastName = results.getString(3);
        String registration = results.getString(4);

        assertEquals(
                "FLORENCENIGHTINGALEPHA0000073561",
                firstName + lastName + registration
        );
    }

    @Test
    void addingSecondPharmacistWorks() throws SQLException {
        Pharmacist pharmacist1 = new Pharmacist(
                "Florence",
                "Nightingale",
                "PHA0000073561");
        db.addPharmacist(pharmacist1);

        Pharmacist pharmacist2 = new Pharmacist(
                "Jimi",
                "Hendrix",
                "PHA0000387126");
        db.addPharmacist(pharmacist2);

        Statement statement = db.getConnection().createStatement();
        statement.execute("SELECT * FROM pharmacists ORDER BY id DESC;");

        ResultSet results = statement.getResultSet();
        String firstName = results.getString(2);
        String lastName = results.getString(3);
        String registration = results.getString(4);

        assertEquals(
                "JIMIHENDRIXPHA0000387126",
                firstName + lastName + registration
        );
    }

    @Test
    void addingDrugWorks() throws SQLException {
        Drug drug1 = new Drug(
                "Norspan",
                "10mcg/hr",
                "patch"
        );
        db.addDrug(drug1);

        Statement statement = db.getConnection().createStatement();
        statement.execute("SELECT * FROM drugs;");

        ResultSet results = statement.getResultSet();
        String name = results.getString(2);
        String strength = results.getString(3);
        String doseForm = results.getString(4);

        assertEquals(
                "NORSPAN10mcg/hrpatch",
                name + strength + doseForm
        );
    }

    @Test
    void addingSecondDrugWorks() throws SQLException {
        Drug drug1 = new Drug(
                "Norspan",
                "10mcg/hr",
                "patch"
        );
        db.addDrug(drug1);


        Drug drug2 = new Drug(
                "MS Contin",
                "15mg",
                "SR tablet"
        );
        db.addDrug(drug2);

        Statement statement = db.getConnection().createStatement();
        statement.execute("SELECT * FROM drugs ORDER BY id DESC;");

        ResultSet results = statement.getResultSet();
        String name = results.getString(2);
        String strength = results.getString(3);
        String doseForm = results.getString(4);

        assertEquals(
                "MS CONTIN15mgSR tablet",
                name + strength + doseForm
        );        
    }

    @Test
    void addingPatientWorks() throws SQLException {
        Patient patient1 = new Patient(
                "Napoleon",
                "Bonaparte",
                "38 Parisian Street, Armadale"
        );
        db.addPatient(patient1);

        Statement statement = db.getConnection().createStatement();
        statement.execute("SELECT * FROM agents;");
        
        ResultSet results = statement.getResultSet();

        var stringBuilder = new StringBuilder();
        while (results.next()) {
            stringBuilder.append(results.getBoolean(2));
            stringBuilder.append(results.getString(3));
            stringBuilder.append(results.getString(4));
            stringBuilder.append(results.getString(5));
            stringBuilder.append(results.getString(6));                
        }

        assertEquals(
                "falseNAPOLEONBONAPARTE38 Parisian Street, Armadale",
                stringBuilder.substring(0));
    }

    @Test
    void addingSupplierWorks() throws SQLException {
        Supplier supplier1 = new Supplier(
                "Sigma Healthcare",
                "2125 Dandenong Rd, Clayton VIC 3168"
        );
        db.addSupplier(supplier1);

        Statement statement = db.getConnection().createStatement();
        statement.execute("SELECT * FROM agents;");
        
        ResultSet results = statement.getResultSet();

        var stringBuilder = new StringBuilder();
        while (results.next()) {
            stringBuilder.append(results.getBoolean(2));
            stringBuilder.append(results.getString(3));
            stringBuilder.append(results.getString(4));
            stringBuilder.append(results.getString(5));
            stringBuilder.append(results.getString(6));                
        }    

        assertEquals(
                "trueSIGMA HEALTHCARE2125 Dandenong Rd, Clayton VIC 3168",
                stringBuilder.substring(0));
    }

    @Test
    void addingPatientAndSupplierWorks() throws SQLException {
        Patient patient1 = new Patient(
                "Napoleon",
                "Bonaparte",
                "38 Parisian Street, Armadale"
        );
        db.addPatient(patient1);

        Supplier supplier1 = new Supplier(
                "Sigma Healthcare",
                "2125 Dandenong Rd, Clayton VIC 3168"
        );
        db.addSupplier(supplier1);
        
        Statement statement = db.getConnection().createStatement();
        statement.execute("SELECT * FROM agents ORDER BY id ASC;");
        
        ResultSet results = statement.getResultSet();

        var stringBuilder = new StringBuilder();
        while (results.next()) {
            stringBuilder.append(results.getBoolean(2));
            stringBuilder.append(results.getString(3));
            stringBuilder.append(results.getString(4));
            stringBuilder.append(results.getString(5));
            stringBuilder.append(results.getString(6));                
        }       

        assertEquals(
                "falseNAPOLEONBONAPARTE38 Parisian Street, Armadale"
                + "trueSIGMA HEALTHCARE2125 Dandenong Rd, Clayton VIC 3168",
                stringBuilder.substring(0));        
    }

    @Test
    void searchingPrescribersWorks() throws SQLException {
        Prescriber prescriber1 = new Prescriber(
                "Dylan",
                "Robertson",
                "3917689");
        db.addPrescriber(prescriber1);

        Prescriber prescriber2 = new Prescriber(
                "Dennis",
                "Ritchie",
                "2638312"
        );
        db.addPrescriber(prescriber2);

        Prescriber prescriber3 = new Prescriber(
                "Maria",
                "Robinson",
                "2638312"
        );
        db.addPrescriber(prescriber3);

        ResultSet results = db.getPrescribers("Rob");
        
        var stringBuilder = new StringBuilder();
        while (results.next()) {
            stringBuilder.append(results.getString(2));
            stringBuilder.append(results.getString(3));
            stringBuilder.append(results.getString(4));
        }

        assertEquals(
                "DYLANROBERTSON3917689MARIAROBINSON2638312",
                stringBuilder.substring(0)
        );
    }

    @Test
    void searchingPharmacistsWorks() throws SQLException {
        Pharmacist pharmacist1 = new Pharmacist(
                "Harold",
                "Abelson",
                "PHA0000073561");
        db.addPharmacist(pharmacist1);

        Pharmacist pharmacist2 = new Pharmacist(
                "Garry",
                "Abblett",
                "PHA0000387126");
        db.addPharmacist(pharmacist2);
        
        Pharmacist pharmacist3 = new Pharmacist(
                "Robert",
                "Atkins",
                "PHA0000371269");
        db.addPharmacist(pharmacist3);

        Pharmacist pharmacist4 = new Pharmacist(
                "Tony",
                "Abbott",
                "PHA0000038024");
        db.addPharmacist(pharmacist4);

        ResultSet results = db.getPharmacists("Abb");
        
        var stringBuilder = new StringBuilder();
        while (results.next()) {
            stringBuilder.append(results.getString(2));
            stringBuilder.append(results.getString(3));
            stringBuilder.append(results.getString(4));
        }

        assertEquals(
                "GARRYABBLETTPHA0000387126TONYABBOTTPHA0000038024",
                stringBuilder.substring(0)
        );       
    }

    @Test
    void searchingDrugsWorks() throws SQLException {
        Drug drug1 = new Drug(
                "Norspan",
                "10mcg/hr",
                "patch"
        );
        db.addDrug(drug1);


        Drug drug2 = new Drug(
                "MS Contin",
                "15mg",
                "SR tablet"
        );
        db.addDrug(drug2);

        Drug drug3 = new Drug(
                "MS Mono",
                "30mg",
                "SR tablet"
        );
        db.addDrug(drug3);

        Drug drug4 = new Drug(
                "MS Contin",
                "30mg",
                "SR tablet"
        );
        db.addDrug(drug4);

        ResultSet results = db.getDrugs("MS C");
        
        var stringBuilder = new StringBuilder();
        while (results.next()) {
            stringBuilder.append(results.getString(2));
            stringBuilder.append(results.getString(3));
            stringBuilder.append(results.getString(4));
        }

        assertEquals(
                "MS CONTIN15mgSR tabletMS CONTIN30mgSR tablet",
                stringBuilder.substring(0)
        );
    }

    @Test
    void searchingPatientsWorks() throws SQLException {
        Patient patient1 = new Patient(
                "Napoleon",
                "Bonaparte",
                "38 Parisian Street, Paris"
        );
        db.addPatient(patient1);

        Supplier supplier1 = new Supplier(
                "Sigma Healthcare",
                "2125 Dandenong Rd, Clayton VIC 3168"
        );
        db.addSupplier(supplier1);

        Patient patient2 = new Patient(
                "Joseph",
                "Bonanno",
                "1 Swanston Street, Melbourne"
        );
        db.addPatient(patient2);
        
        ResultSet results = db.getPatients("Bona");

        var stringBuilder = new StringBuilder();
        while (results.next()) {
            stringBuilder.append(results.getString(3));
            stringBuilder.append(results.getString(4));
            stringBuilder.append(results.getString(5));
            stringBuilder.append(results.getString(6));
        }

        assertEquals(
                "JOSEPHBONANNO1 Swanston Street, Melbourne"
                + "NAPOLEONBONAPARTE38 Parisian Street, Paris",
                stringBuilder.substring(0)
        );
    }

    @Test
    void searchingSuppliersWorks() throws SQLException {
        Patient patient1 = new Patient(
                "Sigma",
                "Dygma",
                "3 Awsome Street, Best Hill"
        );
        db.addPatient(patient1);

        Supplier supplier1 = new Supplier(
                "Sigma Healthcare",
                "2125 Dandenong Rd, Clayton VIC 3168"
        );
        db.addSupplier(supplier1);

        ResultSet results = db.getSuppliers("Sigma");

        var stringBuilder = new StringBuilder();
        while (results.next()) {
            stringBuilder.append(results.getString(3));
            stringBuilder.append(results.getString(4));
            stringBuilder.append(results.getString(5));
            stringBuilder.append(results.getString(6));
        }

        assertEquals(
                "SIGMA HEALTHCARE2125 Dandenong Rd, Clayton VIC 3168",
                stringBuilder.substring(0)
        );
    }
}
