package com.lissajouslaser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Represents the database.
 */
public class Database {
    static final int THIRD_COLUMN = 3;
    static final int FOURTH_COLUMN = 4;
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


    /**
     * Default constructor. Connects to database and prepares
     * prepared SQL queries.
     */
    public Database() {
        this("records.db");
    }

    /**
     * Constructor with specified database file.
     */
    public Database(String fileName) {
        System.out.println(connect("jdbc:sqlite:file:" + fileName));
        System.out.println(createTables());
        System.out.println(createPreparedStatements());
    }

    public Connection getConnection() {
        return con;
    }

    /**
     * Sets up connection with database.
     */
    public boolean connect(String connectionString) {
        try {
            con = DriverManager.getConnection(connectionString);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /*
     * Check if one if the expected tables in the database exists. If
     * it does not exist, it is assumed the database is new and
     * generates all the tables required. Returns true if no
     * SQLException was encountered, otherwise throws false.
     */
    private boolean createTables() {

        try {
            Statement stmt = con.createStatement();
            String createTable;

            createTable = ""
                    + "CREATE TABLE IF NOT EXISTS drugs ("
                    + "    id integer CONSTRAINT drug_id PRIMARY KEY,"
                    + "    name varchar(32) NOT NULL,"
                    + "    strength varchar(16) NOT NULL,"
                    + "    form varchar(16) NOT NULL"
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
                    + "    is_supplier boolean,"
                    + "    name varchar(64) NOT NULL,"
                    + "    last_name varchar(32),"
                    + "    address varchar(64) NOT NULL"
                    + ");";
            stmt.execute(createTable);

            createTable = ""
                    + "CREATE TABLE IF NOT EXISTS transfers ("
                    + "    id integer CONSTRAINT transfer_key PRIMARY KEY,"
                    + "    transfer_date timestamp NOT NULL,"
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

            return true;

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    private boolean createPreparedStatements() {
        try {
            prescriberInsert = con.prepareStatement(""
                    + "INSERT INTO prescribers"
                    + "    (first_name, last_name, prescriber_num)"
                    + "    VALUES (?, ?, ?);"
            );

            pharmacistInsert = con.prepareStatement(""
                    + "INSERT INTO pharmacists"
                    + "    (first_name, last_name, registration)"
                    + "    VALUES (?, ?, ?);"
            );

            drugInsert = con.prepareStatement(""
                    + "INSERT INTO drugs"
                    + "    (name, strength, form)"
                    + "    VALUES (?, ?, ?);"
            );

            agentInsert = con.prepareStatement(""
                    + "INSERT INTO agents"
                    + "    (is_supplier, name, last_name, address)"
                    + "    VALUES (?, ?, ?, ?);"
            );

            transferInsert = con.prepareStatement(""
                    + "INSERT INTO transfers ("
                    + "        transfer_date,"
                    + "        agent_id,"
                    + "        drug_id,"
                    + "        qty_in,"
                    + "        qty_out,"
                    + "        balance,"
                    + "        prescriber_id,"
                    + "        pharmacist_id,"
                    + "        reference,"
                    + "        notes"
                    + ")"
                    + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
            );

            prescriberSearch = con.prepareStatement(""
                    + "SELECT * FROM prescribers"
                    + "    WHERE last_name like ?"
                    + "    ORDER BY last_name ASC, first_name ASC;"
            );

            pharmacistSearch = con.prepareStatement(""
                    + "SELECT * FROM pharmacists"
                    + "    WHERE last_name like ?"
                    + "    ORDER BY last_name ASC, first_name ASC;"
            );

            drugSearch = con.prepareStatement(""
                    + "SELECT * FROM drugs"
                    + "    WHERE name like ?"
                    + "    ORDER BY name ASC, strength ASC, form ASC;"
            );

            // SQLite implements boolean as a 1 bit integer.
            patientSearch = con.prepareStatement(""
                    + "SELECT * FROM agents"
                    + "    WHERE is_supplier = 0 AND last_name LIKE ?"
                    + "    ORDER BY last_name ASC, name ASC;"
            );

            // SQLite implements boolean as a 1 bit integer.
            supplierSearch = con.prepareStatement(""
                    + "SELECT * FROM agents"
                    + "    WHERE is_supplier = 1 AND name LIKE ?"
                    + "    ORDER BY name ASC;"
            );
            
            return true;

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

    }

    /**
     * Adds prescriber to the database. Returns true if no SQLException
     * was encountered.
     */
    public boolean addPrescriber(Prescriber prescriber) {

        try {
            prescriberInsert.setString(1, prescriber.getFirstName());
            prescriberInsert.setString(2, prescriber.getLastName());
            prescriberInsert.setString(THIRD_COLUMN, prescriber.getprescriberNum());
            prescriberInsert.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Adds pharamcist to the database. Returns true if no SQLException
     * was encountered.
     */
    public boolean addPharmacist(Pharmacist pharmacist) {
        try {
            pharmacistInsert.setString(1, pharmacist.getFirstName());
            pharmacistInsert.setString(2, pharmacist.getLastName());
            pharmacistInsert.setString(THIRD_COLUMN, pharmacist.getRegistration());
            pharmacistInsert.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Adds drug to the database. Returns true if no SQLException
     * was encountered.
     */
    public boolean addDrug(Drug drug) {
        try {
            drugInsert.setString(1, drug.getName());
            drugInsert.setString(2, drug.getStrength());
            drugInsert.setString(THIRD_COLUMN, drug.getDose_form());
            drugInsert.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Adds a patient to the database. Patients and suppliers are added
     * to the same table in database because their keys are used in the
     * same column in the transfers table. Returns true if no SQLException
     * was encountered.
     */
    public boolean addPatient(Patient patient) {
        try {
            agentInsert.setBoolean(1, false); 
            agentInsert.setString(2, patient.getFirstName());
            agentInsert.setString(THIRD_COLUMN, patient.getLastName());
            agentInsert.setString(FOURTH_COLUMN, patient.getAddress_name());
            agentInsert.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Adds a supplier to the database. Patients and suppliers are added
     * to the same table in database because their keys are used in the
     * same column in the transfers table. Returns true if no SQLException
     * was encountered.
     */
    public boolean addSupplier(Supplier supplier) {
        try {
            agentInsert.setBoolean(1, true);
            agentInsert.setString(2, supplier.getName());
            agentInsert.setString(THIRD_COLUMN, "");
            agentInsert.setString(FOURTH_COLUMN, supplier.getAddress());
            agentInsert.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Adds a record of a drug transfer to the database.
     */
    public boolean addTransferEntry() {
        try {

            transferInsert.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Returns a ResultSet of prescribers whose last name matches
     * the search term. Returns null if there was an SQLException.
     */
    public ResultSet getPrescribers(String searchTerm) {
        try {
            prescriberSearch.setString(1, searchTerm + '%');
            prescriberSearch.execute();

            ResultSet results = prescriberSearch.getResultSet();
            return results;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Returns a ResultSet of pharmacists whose last name matches the
     * search term. Returns null if there was an SQLException.
     **/
    public ResultSet getPharmacists(String searchTerm) {
        try {
            pharmacistSearch.setString(1, searchTerm + '%');
            pharmacistSearch.execute();

            ResultSet results = pharmacistSearch.getResultSet();
            return results;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Returns a ResultSet of drugs whose name matches the search
     * term. Returns null if there was an SQLException.
     **/
    public ResultSet getDrugs(String searchTerm) {
        try {
            drugSearch.setString(1, searchTerm + '%');
            drugSearch.execute();

            ResultSet results = drugSearch.getResultSet();
            return results;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Returns a ResultSet of parients whose last name matches the
     * search term. Returns null if there was an SQLException.
     **/
    public ResultSet getPatients(String searchTerm) {
        try {
            patientSearch.setString(1, searchTerm + '%');
            patientSearch.execute();

            ResultSet results = patientSearch.getResultSet();
            return results;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Returns a ResultSet of suppliers whose last name matches the
     * search term. Returns null if there was an SQLException.
     **/
    public ResultSet getSuppliers(String searchTerm) {
        try {
            supplierSearch.setString(1, searchTerm + '%');
            supplierSearch.execute();

            ResultSet results = supplierSearch.getResultSet();
            return results;
        } catch (SQLException e) {
            return null;
        }
    }
    
}
