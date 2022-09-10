package com.lissajouslaser;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Represents the database connection, and provides methods
 * for interacting with the database.
 */
public class DatabaseConnection {
    private Connection con;
    private PreparedStatement prescriberInsert;
    private PreparedStatement pharmacistInsert;
    private PreparedStatement drugInsert;
    private PreparedStatement agentInsert;
    private PreparedStatement transferInsert;
    private PreparedStatement prescriberSearch;
    private PreparedStatement pharmacistSearch;
    private PreparedStatement drugSearch;
    private PreparedStatement patientSearch;
    private PreparedStatement supplierSearch;
    private PreparedStatement agentSearch;
    private PreparedStatement balanceSearch;
    private PreparedStatement transferSearchByDrug;
    private PreparedStatement transferSearchByDate;

    /**
     * Default constructor. Connects to database and prepares
     * prepared SQL queries.
     */
    public DatabaseConnection() throws SQLException {
        this("records.db");
    }

    /**
     * Constructor with specified database file.
     */
    public DatabaseConnection(String fileName) throws SQLException {
        connect("jdbc:sqlite:" + fileName);
        createTables();
        createPreparedStatements();
    }

    public Connection getConnection() {
        return con;
    }

    /**
     * Sets up connection with database.
     */
    public void connect(String connectionString) throws SQLException {
        con = DriverManager.getConnection(connectionString);
    }

    /*
     * Check if one if the expected tables in the database exists. If
     * it does not exist, it is assumed the database is new and
     * generates all the tables required.
     */
    private void createTables() throws SQLException {
        Statement stmt = con.createStatement();
        String createTable;

        createTable = ""
                + "CREATE TABLE IF NOT EXISTS drugs ("
                + "    id integer CONSTRAINT drug_id PRIMARY KEY,"
                + "    name varchar(32) NOT NULL,"
                + "    strength varchar(16) NOT NULL,"
                + "    dose_form varchar(16) NOT NULL"
                + " );";
        stmt.execute(createTable);

        createTable = ""
                + "CREATE TABLE IF NOT EXISTS prescribers ("
                + "    id integer CONSTRAINT doctor_id PRIMARY KEY,"
                + "    first_name varchar(16) NOT NULL,"
                + "    last_name varchar(32) NOT NULL,"
                + "    prescriber_num varchar(8)"
                + " );";
        stmt.execute(createTable);

        createTable = ""
                + "CREATE TABLE IF NOT EXISTS pharmacists ("
                + "    id integer CONSTRAINT pharmacist_id PRIMARY KEY,"
                + "    first_name varchar(16) NOT NULL,"
                + "    last_name varchar(32) NOT NULL,"
                + "    registration varchar(16)"
                + ");";
        stmt.execute(createTable);

        createTable = ""
                + "CREATE TABLE IF NOT EXISTS agents ("
                + "    id integer CONSTRAINT agent_key PRIMARY KEY,"
                + "    is_supplier boolean NOT NULL,"
                + "    first_name varchar(16),"
                + "    last_name varchar(32),"
                + "    company_name varchar(64),"
                + "    address varchar(64) NOT NULL"
                + ");";
        stmt.execute(createTable);

        createTable = ""
                + "CREATE TABLE IF NOT EXISTS transfers ("
                + "    id integer CONSTRAINT transfer_key PRIMARY KEY,"
                + "    transfer_date date,"
                + "    agent_id integer REFERENCES agents (id),"
                + "    drug_id integer REFERENCES drugs (id),"
                + "    qty_in integer,"
                + "    qty_out integer,"
                + "    balance integer,"
                + "    prescriber_id integer REFERENCES prescribers (id),"
                + "    reference varchar(16),"
                + "    pharmacist_id integer REFERENCES pharmacists (id),"
                + "    notes varchar(64),"
                + "    CONSTRAINT qty_remaining_not_neg CHECK (balance >= 0)"
                + ");";
        stmt.execute(createTable);
        stmt.close();
    }

    private void createPreparedStatements() throws SQLException {
        prescriberInsert = con.prepareStatement(""
                + "INSERT INTO prescribers "
                + "    (first_name, last_name, prescriber_num) "
                + "VALUES (?, ?, ?);");

        pharmacistInsert = con.prepareStatement(""
                + "INSERT INTO pharmacists "
                + "    (first_name, last_name, registration) "
                + "VALUES (?, ?, ?);");

        drugInsert = con.prepareStatement(""
                + "INSERT INTO drugs "
                + "    (name, strength, dose_form) "
                + "VALUES (?, ?, ?);");

        agentInsert = con.prepareStatement(""
                + "INSERT INTO agents "
                + "    (is_supplier, first_name, last_name, company_name, address) "
                + "VALUES (?, ?, ?, ?, ?);");

        transferInsert = con.prepareStatement(""
                + "INSERT INTO transfers ("
                + "        transfer_date,"
                + "        agent_id,"
                + "        drug_id,"
                + "        qty_in,"
                + "        qty_out,"
                + "        balance,"
                + "        prescriber_id,"
                + "        reference,"
                + "        pharmacist_id,"
                + "        notes"
                + ")"
                + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

        prescriberSearch = con.prepareStatement(""
                + "SELECT * FROM prescribers "
                + "WHERE last_name like ? "
                + "ORDER BY last_name ASC, first_name ASC;");

        pharmacistSearch = con.prepareStatement(""
                + "SELECT * FROM pharmacists "
                + "WHERE last_name like ?"
                + "ORDER BY last_name ASC, first_name ASC;");

        drugSearch = con.prepareStatement(""
                + "SELECT * FROM drugs "
                + "WHERE name like ? "
                + "ORDER BY name ASC, strength ASC, dose_form ASC;");

        // SQLite implements boolean as a 1 bit integer.
        patientSearch = con.prepareStatement(""
                + "SELECT * FROM agents "
                + "WHERE is_supplier = 0 AND last_name LIKE ? "
                + "ORDER BY last_name ASC, first_name ASC;");

        // SQLite implements boolean as a 1 bit integer.
        supplierSearch = con.prepareStatement(""
                + "SELECT * FROM agents "
                + "WHERE is_supplier = 1 AND company_name LIKE ? "
                + "ORDER BY company_name ASC;");

        agentSearch = con.prepareStatement(""
                + "SELECT * FROM agents "
                + "WHERE company_name LIKE ? OR last_name LIKE ? "
                + "ORDER BY is_supplier DESC, last_name ASC, first_name ASC;");

        balanceSearch = con.prepareStatement(""
                + "SELECT balance FROM transfers "
                + "WHERE drug_id = ? "
                + "ORDER BY id DESC "
                + "LIMIT 1;");

        String searchTemplate = ""
                + "SELECT * "
                + "FROM transfers "
                + "LEFT JOIN agents "
                + "ON agent_id = agents.id "
                + "LEFT JOIN drugs "
                + "ON drug_id = drugs.id "
                + "LEFT JOIN prescribers "
                + "ON prescriber_id = prescribers.id "
                + "LEFT JOIN pharmacists "
                + "ON pharmacist_id = pharmacists.id "
                + "WHERE";

        transferSearchByDrug = con.prepareStatement(
                searchTemplate
                + "    drug_id = ?"
                + "    AND transfer_date >= ?"
                + "    AND transfer_date <= ?;");
        
        transferSearchByDate = con.prepareStatement(
                searchTemplate
                + "    transfer_date = ?;");
    }

    /**
     * Adds prescriber to the database.
     */
    public void addPrescriber(Prescriber prescriber)
            throws SQLException {

        prescriberInsert.setString(1, prescriber.getFirstName());
        prescriberInsert.setString(2, prescriber.getLastName());
        prescriberInsert.setString(3, prescriber.getprescriberNum());
        prescriberInsert.execute();
    }

    /**
     * Adds pharamcist to the database.
     */
    public void addPharmacist(Pharmacist pharmacist)
            throws SQLException {

        pharmacistInsert.setString(1, pharmacist.getFirstName());
        pharmacistInsert.setString(2, pharmacist.getLastName());
        pharmacistInsert.setString(3, pharmacist.getRegistration());
        pharmacistInsert.execute();
    }

    /**
     * Adds drug to the database.
     */
    public void addDrug(Drug drug) throws SQLException {
        drugInsert.setString(1, drug.getName());
        drugInsert.setString(2, drug.getStrength());
        drugInsert.setString(3, drug.getDose_form());
        drugInsert.execute();
    }

    /**
     * Adds a patient to the database. Patients and suppliers are added
     * to the same table in database because their keys are used in the
     * same column in the transfers table.
     */
    public void addPatient(Patient patient) throws SQLException {
        final int isBooleanIdx = 1;
        final int firstNameIdx = 2;
        final int lastNameIdx = 3;
        final int companyNameIdx = 4;
        final int addressIdx = 5;

        agentInsert.setBoolean(isBooleanIdx, false);
        agentInsert.setString(firstNameIdx, patient.getFirstName());
        agentInsert.setString(lastNameIdx, patient.getLastName());
        agentInsert.setString(companyNameIdx, "");
        agentInsert.setString(addressIdx, patient.getAddress());
        agentInsert.execute();
    }

    /**
     * Adds a supplier to the database. Patients and suppliers are added
     * to the same table in database because their keys are used in the
     * same column in the transfers table.
     */
    public void addSupplier(Supplier supplier) throws SQLException {
        final int isBooleanIdx = 1;
        final int firstNameIdx = 2;
        final int lastNameIdx = 3;
        final int companyNameIdx = 4;
        final int addressIdx = 5;

        agentInsert.setBoolean(isBooleanIdx, true);
        agentInsert.setString(firstNameIdx, "");
        agentInsert.setString(lastNameIdx, "");
        agentInsert.setString(companyNameIdx, supplier.getName());
        agentInsert.setString(addressIdx, supplier.getAddress());
        agentInsert.execute();
    }

    /**
     * Adds a record of a transfer of a drug.
     **/
    public void addTransfer(Transfer transfer) throws SQLException {
        final int dateIdx = 1;
        final int agentIdIdx = 2;
        final int drugIdIdx = 3;
        final int qtyInIdx = 4;
        final int qtyOutIdx = 5;
        final int balanceIdx = 6;
        final int prescriberIdIdx = 7;
        final int referenceIdx = 8;
        final int pharmacistIdIdx = 9;
        final int notesIdx = 10;

        transferInsert.setDate(dateIdx, Date.valueOf(transfer.getDate()));
        transferInsert.setInt(agentIdIdx, transfer.getAgent().getId());
        transferInsert.setInt(drugIdIdx, transfer.getDrug().getId());
        transferInsert.setInt(qtyInIdx, transfer.getQtyIn());
        transferInsert.setInt(qtyOutIdx, transfer.getQtyOut());
        transferInsert.setInt(balanceIdx, transfer.getBalance());
        if (transfer.getPrescriber() != null) {
            transferInsert.setInt(
                    prescriberIdIdx,
                    transfer.getPrescriber().getId());
        }
        transferInsert.setString(referenceIdx, transfer.getReference());
        transferInsert.setInt(
                pharmacistIdIdx,
                transfer.getPharmacist().getId());
        transferInsert.setString(notesIdx, transfer.getNotes());
        transferInsert.execute();
    }

    /**
     * Returns a ResultSet of prescribers whose last name matches
     * the search term. Returns null if there was an SQLException.
     */
    public ResultSet getPrescribers(String searchTerm)
            throws SQLException {
        prescriberSearch.setString(1, searchTerm + '%');
        prescriberSearch.execute();

        return prescriberSearch.getResultSet();
    }

    /**
     * Returns a ResultSet of pharmacists whose last name matches the
     * search term. Returns null if there was an SQLException.
     **/
    public ResultSet getPharmacists(String searchTerm)
            throws SQLException {
        pharmacistSearch.setString(1, searchTerm + '%');
        pharmacistSearch.execute();

        return pharmacistSearch.getResultSet();
    }

    /**
     * Returns a ResultSet of drugs whose name matches the search
     * term. Returns null if there was an SQLException.
     **/
    public ResultSet getDrugs(String searchTerm)
            throws SQLException {
        drugSearch.setString(1, searchTerm + '%');
        drugSearch.execute();

        return drugSearch.getResultSet();
    }

    /**
     * Returns a ResultSet of parients whose last name matches the
     * search term. Returns null if there was an SQLException.
     **/
    public ResultSet getPatients(String searchTerm)
            throws SQLException {
        patientSearch.setString(1, searchTerm + '%');
        patientSearch.execute();

        return patientSearch.getResultSet();
    }

    /**
     * Returns a ResultSet of suppliers whose last name matches the
     * search term. Returns null if there was an SQLException.
     **/
    public ResultSet getSuppliers(String searchTerm) throws SQLException {
        supplierSearch.setString(1, searchTerm + '%');
        supplierSearch.execute();

        return supplierSearch.getResultSet();
    }

    /**
     * Returns results of getSuppliers() and getPatients()
     * combined. Returns null if there was an SQLException.
     **/
    public ResultSet getAgents(String searchTerm) throws SQLException {
        agentSearch.setString(1, searchTerm + '%');
        agentSearch.setString(2, searchTerm + '%');
        agentSearch.execute();

        return agentSearch.getResultSet();
    }

    /**
     * Returns results of transfers filtered by a specific drug.
     **/
    public ResultSet getTransfersByDrug(SearchByDrug searchTerm)
            throws SQLException {
        transferSearchByDrug.setInt(1, searchTerm.getDrug().getId());
        transferSearchByDrug.setDate(
                2,
                Date.valueOf(searchTerm.getStartDate()));
        transferSearchByDrug.setDate(
                3,
                Date.valueOf(searchTerm.getEndDate()));

        transferSearchByDrug.execute();

        return transferSearchByDrug.getResultSet();
    }

    /**
     * Returns results of transfers matching a specific date.
     */
    public ResultSet getTransfersByDate(LocalDate date)
            throws SQLException {
        transferSearchByDate.setDate(1, Date.valueOf(date));

        transferSearchByDate.execute();

        return transferSearchByDate.getResultSet();
    }

    /**
     * Returns list of of agents (Patients and Suppliers) matching the
     * searchTerm. Returns null if there was an SQLException.
     **/
    public List<Agent> getAgentsList(String searchTerm)
            throws SQLException {
        final int idIdx = 1;
        final int firstNameIdx = 3;
        final int lastNameIdx = 4;
        final int companyNameIdx = 5;
        final int addressIdx = 6;

        ResultSet results = getAgents(searchTerm);
        List<Agent> resultsAsList = new ArrayList<>();

        while (results.next()) {
            if (results.getBoolean(2)) {
                // Case: supplier.
                var supplier = new Supplier(
                        results.getInt(idIdx),
                        results.getString(companyNameIdx),
                        results.getString(addressIdx));
                resultsAsList.add(supplier);
            } else {
                // Case: patient.
                var patient = new Patient(
                        results.getInt(idIdx),
                        results.getString(firstNameIdx),
                        results.getString(lastNameIdx),
                        results.getString(addressIdx));
                resultsAsList.add(patient);
            }
        }
        return resultsAsList;
    }

    /**
     * Returns list of Prescribers matching the searchTerm.
     * Returns null if there was an SQLException.
     */
    public List<Prescriber> getPrescribersList(String searchTerm)
            throws SQLException {
        final int idIdx = 1;
        final int firstNameIdx = 2;
        final int lastNameIdx = 3;
        final int prescriberNumIdx = 4;

        ResultSet results = getPrescribers(searchTerm);
        List<Prescriber> resultsAsList = new ArrayList<>();

        while (results.next()) {
            var prescriber = new Prescriber(
                    results.getInt(idIdx),
                    results.getString(firstNameIdx),
                    results.getString(lastNameIdx),
                    results.getString(prescriberNumIdx));
            resultsAsList.add(prescriber);
        }
        return resultsAsList;
    }

    /**
     * Returns a list of Pharmacists matching the searchTerm.
     * Returns null if there was an SQLException.
     */
    public List<Pharmacist> getPharmacistsList(String searchTerm)
            throws SQLException {
        final int idIdx = 1;
        final int firstNameIdx = 2;
        final int lastNameIdx = 3;
        final int registrationIdx = 4;

        ResultSet results = getPharmacists(searchTerm);
        List<Pharmacist> resultsAsList = new ArrayList<>();

        while (results.next()) {
            var pharmacist = new Pharmacist(
                    results.getInt(idIdx),
                    results.getString(firstNameIdx),
                    results.getString(lastNameIdx),
                    results.getString(registrationIdx));
            resultsAsList.add(pharmacist);
        }
        return resultsAsList;
    }

    /**
     * Returns a list of Patients matching the searchTerm.
     * Returns null if there was an SQLException.
     */
    public List<Patient> getPatientsList(String searchTerm)
            throws SQLException {
        final int idIdx = 1;
        final int firstNameIdx = 3;
        final int lastNameIdx = 4;
        final int addressIdx = 6;

        ResultSet results = getPatients(searchTerm);
        List<Patient> resultsAsList = new ArrayList<>();

        while (results.next()) {
            var patient = new Patient(
                    results.getInt(idIdx),
                    results.getString(firstNameIdx),
                    results.getString(lastNameIdx),
                    results.getString(addressIdx));
            resultsAsList.add(patient);
        }
        return resultsAsList;
    }

    /**
     * Returns a list of Suppliers matching the searchTerm.
     * Returns null if there was an SQLException.
     */
    public List<Supplier> getSuppliersList(String searchTerm)
            throws SQLException {
        final int idIdx = 1;
        final int nameIdx = 5;
        final int addressIdx = 6;

        ResultSet results = getSuppliers(searchTerm);
        List<Supplier> resultsAsList = new ArrayList<>();

        while (results.next()) {
            var supplier = new Supplier(
                    results.getInt(idIdx),
                    results.getString(nameIdx),
                    results.getString(addressIdx));
            resultsAsList.add(supplier);
        }
        return resultsAsList;
    }

    /**
     * Returns a list of Drugs matching the searchTerm.
     * Returns null if there was an SQLException.
     */
    public List<Drug> getDrugsList(String searchTerm)
            throws SQLException {
        final int idIdx = 1;
        final int nameIdx = 2;
        final int strengthIdx = 3;
        final int doseFormIdx = 4;

        ResultSet results = getDrugs(searchTerm);
        List<Drug> resultsAsList = new ArrayList<>();

        while (results.next()) {
            var drug = new Drug(
                    results.getInt(idIdx),
                    results.getString(nameIdx),
                    results.getString(strengthIdx),
                    results.getString(doseFormIdx));
            resultsAsList.add(drug);
        }
        return resultsAsList;
    }

    /**
     * Returns a list of transfers matching the drug and date range
     * of SearchByDrug.
     */
    public List<TransferRetrieved>
            getTransfersByDrugList(SearchByDrug searchByDrug)
            throws SQLException {

        ResultSet results = getTransfersByDrug(searchByDrug);

        return toListOfTransferSearchResults(results);
    }

    /**
     * Returns a list of transfers matching the date.
     */
    public List<TransferRetrieved> getTransfersByDateList(LocalDate date)
            throws SQLException {    
        ResultSet results = getTransfersByDate(date);

        return toListOfTransferSearchResults(results);
    }

    private List<TransferRetrieved> 
            toListOfTransferSearchResults(ResultSet results)
            throws SQLException {

        final int transferIdIdx = 1;
        final int dateIdx = 2;
        // agent foreign key
        // drug foreign key
        final int qtyInIdx = 5;
        final int qtyOutIdx = 6;
        final int balanceIdx = 7;
        // prescriber foreign key
        final int referenceIdx = 9;
        // pharmacist foreign key
        final int notesIdx = 11;
        final int agentIdIdx = 12;
        final int isSupplerIdx = 13;
        final int agentFirstNameIdx = 14;
        final int agentLastNameIdx = 15;
        final int companyNameIdx = 16;
        final int addressIdx = 17;
        final int drugIdIdx = 18;
        final int drugNameIdx = 19;
        final int strengthIdx = 20;
        final int doseFormIdx = 21;
        final int prescriberIdIdx = 22;
        final int prescriberFirstNameIdx = 23;
        final int prescriberLastNameIdx = 24;
        final int prescriberNumIdx = 25;
        final int pharmacistIdIdx = 26;
        final int pharmacistFirstNameIdx = 27;
        final int pharmacistLastNameIdx = 28;
        final int registrationIdx = 29;

        List<TransferRetrieved> resultsAsList = new ArrayList<>();

        while (results.next()) {
            boolean isSupplier = results.getBoolean(isSupplerIdx);

            Agent agent;
            if (isSupplier) {
                agent = new Supplier(
                        results.getInt(agentIdIdx),
                        results.getString(companyNameIdx),
                        results.getString(addressIdx));
            } else {
                agent = new Patient(
                        results.getInt(agentIdIdx),
                        results.getString(agentFirstNameIdx),
                        results.getString(agentLastNameIdx),
                        results.getString(addressIdx));
            }

            var drug = new Drug(
                    results.getInt(drugIdIdx),
                    results.getString(drugNameIdx),
                    results.getString(strengthIdx),
                    results.getString(doseFormIdx));
            
            Prescriber prescriber;
            if (isSupplier) {
                prescriber = null;
            } else {
                prescriber = new Prescriber(
                        results.getInt(prescriberIdIdx),
                        results.getString(prescriberFirstNameIdx),
                        results.getString(prescriberLastNameIdx),
                        results.getString(prescriberNumIdx));
            }

            var pharmacist = new Pharmacist(
                    results.getInt(pharmacistIdIdx),
                    results.getString(pharmacistFirstNameIdx),
                    results.getString(pharmacistLastNameIdx),
                    results.getString(registrationIdx)
            );

            var transfer = new TransferRetrieved(
                    results.getInt(transferIdIdx),
                    results.getDate(dateIdx, new GregorianCalendar()).toLocalDate(),
                    agent,
                    drug,
                    results.getInt(qtyInIdx),
                    results.getInt(qtyOutIdx),
                    results.getInt(balanceIdx),
                    prescriber,
                    results.getString(referenceIdx),
                    pharmacist,
                    results.getString(notesIdx));

            resultsAsList.add(transfer);
        }
        return resultsAsList;
    }

    /**
     * Returns current balance of a Drug with its primary key
     * as the parameter. Returns 0 if there is no record, meaning
     * drug has never been transferred.
     */
    public int getBalance(int drugId) throws SQLException {
        int balance = 0;
        balanceSearch.setInt(1, drugId);
        balanceSearch.execute();

        ResultSet results = balanceSearch.getResultSet();

        while (results.next()) {
            balance = results.getInt(1);
        }


        return balance;
    }
}
