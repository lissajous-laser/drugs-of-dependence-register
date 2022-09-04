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
    private PreparedStatement supplyInsert;
    private PreparedStatement receiveInsert;
    private PreparedStatement prescriberSearch;
    private PreparedStatement pharmacistSearch;
    private PreparedStatement drugSearch;
    private PreparedStatement patientSearch;
    private PreparedStatement supplierSearch;
    private PreparedStatement agentSearch;
    private PreparedStatement balanceSearch;
    private PreparedStatement transferSearchByDrug;

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
     * generates all the tables required. Returns true if no
     * SQLException was encountered, otherwise throws false.
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
                + "    transfer_date date NOT NULL,"
                + "    agent_id integer REFERENCES agents (id),"
                + "    drug_id integer REFERENCES drugs (id),"
                + "    qty_in integer,"
                + "    qty_out integer,"
                + "    balance integer,"
                + "    prescriber_id integer REFERENCES prescribers (id),"
                + "    pharmacist_id integer REFERENCES pharmacists (id),"
                + "    reference varchar(16),"
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

            supplyInsert = con.prepareStatement(""
                    + "INSERT INTO transfers ("
                    + "        transfer_date,"
                    + "        agent_id,"
                    + "        drug_id,"
                    + "        qty_in,"
                    + "        qty_out,"
                    + "        balance,"
                    + "        prescriber_id,"
                    + "        reference,"
                    + "        notes,"
                    + "        pharmacist_id"
                    + ")"
                    + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

            receiveInsert = con.prepareStatement(""
                    + "INSERT INTO transfers ("
                    + "        transfer_date,"
                    + "        agent_id,"
                    + "        drug_id,"
                    + "        qty_in,"
                    + "        qty_out,"
                    + "        balance,"
                    + "        reference,"
                    + "        notes,"
                    + "        pharmacist_id"
                    + ")"
                    + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");

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
            
            transferSearchByDrug = con.prepareStatement(""              
                    + "SELECT "
                    + "    transfer_date as 'date',"
                    + "    trim(agents.last_name || ' ' || agents.first_name"
                    + "        || agents.company_name) AS agent,"
                    + "    agents.address,"
                    + "    drugs.name || ' ' || drugs.strength || ' '"
                    + "        || drugs.dose_form AS drug,"
                    + "    qty_in AS 'in',"
                    + "    qty_out AS out,"
                    + "    balance,"
                    + "    prescribers.first_name || ' '"
                    + "        || prescribers.last_name AS prescriber,"
                    + "    reference,"
                    + "    substr(pharmacists.first_name, 1, 1) || '. '"
                    + "        || pharmacists.last_name AS pharmacist,"
                    + "    notes "
                    + "FROM transfers "
                    + "LEFT JOIN agents "
                    + "ON agent_id = agents.id "
                    + "LEFT JOIN drugs "
                    + "ON drug_id = drugs.id "
                    + "LEFT JOIN prescribers "
                    + "ON prescriber_id = prescribers.id "
                    + "LEFT JOIN pharmacists "
                    + "ON pharmacist_id = pharmacists.id "
                    + "WHERE drugs.name LIKE ?;");
    }

    /**
     * Adds prescriber to the database. Returns true if no SQLException
     * was encountered.
     */
    public void addPrescriber(Prescriber prescriber)
            throws SQLException {

        prescriberInsert.setString(1, prescriber.getFirstName());
        prescriberInsert.setString(2, prescriber.getLastName());
        prescriberInsert.setString(3, prescriber.getprescriberNum());
        prescriberInsert.execute();
    }

    /**
     * Adds pharamcist to the database. Returns true if no SQLException
     * was encountered.
     */
    public void addPharmacist(Pharmacist pharmacist)
            throws SQLException {

        pharmacistInsert.setString(1, pharmacist.getFirstName());
        pharmacistInsert.setString(2, pharmacist.getLastName());
        pharmacistInsert.setString(3, pharmacist.getRegistration());
        pharmacistInsert.execute();
    }

    /**
     * Adds drug to the database. Returns true if no SQLException
     * was encountered.
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
     * same column in the transfers table. Returns true if no SQLException
     * was encountered.
     */
    public void addPatient(Patient patient) throws SQLException {
        int isBooleanIdx = 1;
        int firstNameIdx = 2;
        int lastNameIdx = 3;
        int companyNameIdx = 4;
        int addressIdx = 5;

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
     * same column in the transfers table. Returns true if no SQLException
     * was encountered.
     */
    public void addSupplier(Supplier supplier) throws SQLException {
        int isBooleanIdx = 1;
        int firstNameIdx = 2;
        int lastNameIdx = 3;
        int companyNameIdx = 4;
        int addressIdx = 5;

        agentInsert.setBoolean(isBooleanIdx, true);
        agentInsert.setString(firstNameIdx, "");
        agentInsert.setString(lastNameIdx, "");
        agentInsert.setString(companyNameIdx, supplier.getName());
        agentInsert.setString(addressIdx, supplier.getAddress());
        agentInsert.execute();
    }

    /**
     * Adds a record of a supply to patient to the database. Returns
     * true if no SQLException was encountered.
     **/
    public void addSupplyEntry(Transfer transfer) throws SQLException {
        int dateIdx = 1;
        int agentIdIdx = 2;
        int drugIdIdx = 3;
        int qtyInIdx = 4;
        int qtyOutIdx = 5;
        int balanceIdx = 6;
        int prescriberIdIdx = 7;
        int referenceIdx = 8;
        int notesIdx = 9;
        int pharmacistIdIdx = 10;
        var date = Date.valueOf(LocalDate.now().toString());
        int qtyIn = 0;
        int qtyOut = 0;

        if (transfer.getAgent() instanceof Supplier) {
            qtyIn = transfer.getQty();
        } else {
            qtyOut = transfer.getQty();
        }

        supplyInsert.setDate(dateIdx, date);
        supplyInsert.setInt(agentIdIdx, transfer.getAgent().getId());
        supplyInsert.setInt(drugIdIdx, transfer.getDrug().getId());
        supplyInsert.setInt(qtyInIdx, qtyIn);
        supplyInsert.setInt(qtyOutIdx, qtyOut);
        supplyInsert.setInt(balanceIdx, transfer.getBalanceAfter());
        supplyInsert.setInt(
                prescriberIdIdx,
                transfer.getPrescriber().getId());
        supplyInsert.setString(referenceIdx, transfer.getReference());
        supplyInsert.setString(notesIdx, transfer.getNotes());
        supplyInsert.setInt(
                pharmacistIdIdx,
                transfer.getPharmacist().getId());
        supplyInsert.execute();
    }

    /**
     * Adds a record of a receive from supplier to the database.
     * Returns true if no SQLException was encountered.
     **/
    public void addReceiveEntry(Transfer transfer)
            throws SQLException {
        int dateIdx = 1;
        int agentIdIdx = 2;
        int drugIdIdx = 3;
        int qtyInIdx = 4;
        int qtyOutIdx = 5;
        int balanceIdx = 6;
        int referenceIdx = 7;
        int notesIdx = 8;
        int pharmacistIdIdx = 9;
        var date = Date.valueOf(LocalDate.now().toString());
        int qtyIn = 0;
        int qtyOut = 0;

        if (transfer.getAgent() instanceof Supplier) {
            qtyIn = transfer.getQty();
        } else {
            qtyOut = transfer.getQty();
        }

        receiveInsert.setDate(dateIdx, date);
        receiveInsert.setInt(agentIdIdx, transfer.getAgent().getId());
        receiveInsert.setInt(drugIdIdx, transfer.getDrug().getId());
        receiveInsert.setInt(qtyInIdx, qtyIn);
        receiveInsert.setInt(qtyOutIdx, qtyOut);
        receiveInsert.setInt(balanceIdx, transfer.getBalanceAfter());
        receiveInsert.setString(referenceIdx, transfer.getReference());
        receiveInsert.setString(notesIdx, transfer.getNotes());
        receiveInsert.setInt(
                pharmacistIdIdx,
                transfer.getPharmacist().getId());
        receiveInsert.execute();
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
    public ResultSet getTransfersByDrug(int drugId) 
            throws SQLException {
        transferSearchByDrug.setInt(1, drugId);
        agentSearch.execute();

        return agentSearch.getResultSet();
    }

    /**
     * Returns list of of agents (Patients and Suppliers) matching the
     * searchTerm. Returns null if there was an SQLException.
     **/
    public List<Agent> getAgentsList(String searchTerm)
            throws SQLException {
        int idIdx = 1;
        int firstNameIdx = 3;
        int lastNameIdx = 4;
        int companyNameIdx = 5;
        int addressIdx = 6;

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
        int idIdx = 1;
        int firstNameIdx = 2;
        int lastNameIdx = 3;
        int prescriberNumIdx = 4;

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
        int idIdx = 1;
        int firstNameIdx = 2;
        int lastNameIdx = 3;
        int registrationIdx = 4;

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
        int idIdx = 1;
        int firstNameIdx = 3;
        int lastNameIdx = 4;
        int addressIdx = 6;

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
        int idIdx = 1;
        int nameIdx = 5;
        int addressIdx = 6;

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
        int idIdx = 1;
        int nameIdx = 2;
        int strengthIdx = 3;
        int doseFormIdx = 4;

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

    public List<Register> getTransfersByDrugList(int drugId)
            throws SQLException {
        int dateIdx = 1;
        int agentNameIdx = 2;
        int addressIdx = 3;
        int drugIdx = 4;
        int qtyInIdx = 5;
        int qtyOutIdx = 6;
        int balanceIdx = 7;
        int prescriberIdx = 8;
        int referenceIdx = 9;
        int pharmacistIdx = 10;
        int noteIdx = 11;

        ResultSet results = getTransfersByDrug(drugId);
        List<Register> resultsAsList = new ArrayList<>();

        while (results.next()) {
            var register = new Register(
                results.getDate(dateIdx, new GregorianCalendar()).toLocalDate(),
                results.getString(agentNameIdx),
                results.getString(addressIdx),
                results.getString(drugIdx),
                results.getInt(qtyInIdx),
                results.getInt(qtyOutIdx),
                results.getInt(balanceIdx),
                results.getString(prescriberIdx),
                results.getString(referenceIdx),
                results.getString(pharmacistIdx),
                results.getString(noteIdx));
            resultsAsList.add(register);
        }
        return resultsAsList;
    }

    /**
     * Returns current balance of a Drug with its primary key
     * as the parameter. Returns 0 if there is no record, and
     * returns -1 if there was an SQLException.
     */
    public int getBalance(int drugId) throws SQLException {
        int balance = 0;

        balanceSearch.setInt(1, drugId);
        balanceSearch.execute();

        ResultSet results = balanceSearch.getResultSet();

        if (results.next()) {
            balance = results.getInt(1);
        }

        return balance;
    }
}
