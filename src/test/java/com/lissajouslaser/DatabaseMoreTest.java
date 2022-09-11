package com.lissajouslaser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DatabaseMoreTest {
    static final String TEST_DB_FILE = "_test.db";
    DatabaseConnection db;

    @BeforeEach
    void createDatabase() throws SQLException {
        db = new DatabaseConnection(TEST_DB_FILE);

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

    }

    @AfterEach
    void deleteDatabase() throws SQLException {

        File file = new File(TEST_DB_FILE);
        file.delete();
    }    

    @Test
    void gettingSupplierListWorks() throws SQLException {
        List<Supplier> agents = db.getSuppliersList("");
        assertEquals(
                "SIGMA HEALTHCARE [2125 Dandenong Rd, Clayton VIC 3168]",
                agents.get(0).toString());
    }

    @Test
    void gettingPatientListWorks() throws SQLException {
        List<Patient> agents = db.getPatientsList("");
        assertEquals(
                "BONAPARTE, NAPOLEON [38 Parisian Street, Paris]",
                agents.get(0).toString());
    }

    // Ordered by drug name.
    @Test
    void gettingDrugListWorks() throws SQLException {
        List<Drug> drugs = db.getDrugsList("");
        assertEquals(
                "NORSPAN 10mcg/hr patch",
                drugs.get(1).toString());
    }

    // Ordered by surname.
    @Test
    void gettingPrescriberListWorks() throws SQLException {
        List<Prescriber> prescribers = db.getPrescribersList("");
        assertEquals(
                "ROBERTSON, DYLAN",
                prescribers.get(1).toString());
    }

    // Ordered by surname.
    @Test
    void gettingPharmacistListWorks() throws SQLException {
        List<Pharmacist> pharmacists = db.getPharmacistsList("");
        assertEquals(
                "ABELSON, HAROLD",
                pharmacists.get(1).toString());
    }

    @Test
    void receivingStockFromSupplierWorks() throws SQLException {
        List<Pharmacist> pharmacists = db.getPharmacistsList("");
        List<Drug> drugs = db.getDrugsList("");
        List<Agent> agents = db.getAgentsList("");

        TransferInput ti = new TransferInput(
                agents.get(0),
                drugs.get(0),
                "0",
                "30",
                null,
                "INV03001",
                "",
                pharmacists.get(0));
        db.addTransfer(ti);

        TransferRetrieved tr =
                db.getTransfersByDateList(LocalDate.now()).get(0);
        
        String txDetails = tr.getAgent().toString() + tr.getDrug()
                + tr.getBalance() + tr.getReference() + tr.getPharmacist();
        
        assertEquals(
                "SIGMA HEALTHCARE [2125 Dandenong Rd, Clayton VIC 3168]"
                + "MS CONTIN 15mg SR tablet30INV03001ABBLETT, GARRY",
                txDetails);
    }

    @Test
    void supplyingToPatientWorks() throws SQLException {
        List<Pharmacist> pharmacists = db.getPharmacistsList("");
        List<Drug> drugs = db.getDrugsList("");
        List<Agent> agents = db.getAgentsList("");
        List<Prescriber> prescribers = db.getPrescribersList("");

        TransferInput receiveFromSupplier = new TransferInput(
                agents.get(0),
                drugs.get(0),
                "0",
                "30",
                null,
                "INV03001",
                "",
                pharmacists.get(0));
        db.addTransfer(receiveFromSupplier);

        TransferInput supplyToPatient = new TransferInput(
                agents.get(1),
                drugs.get(0),
                "30",
                "5",
                prescribers.get(0),
                "4444",
                "",
                pharmacists.get(1));
        db.addTransfer(supplyToPatient);

        TransferRetrieved tr =
                db.getTransfersByDateList(LocalDate.now()).get(1);

        String txDetails = tr.getAgent().toString() + tr.getDrug()
                + tr.getBalance() + tr.getReference() + tr.getPharmacist();

        assertEquals(
                "BONAPARTE, NAPOLEON [38 Parisian Street, Paris]"
                + "MS CONTIN 15mg SR tablet254444ABELSON, HAROLD",
                txDetails);
    }

}
