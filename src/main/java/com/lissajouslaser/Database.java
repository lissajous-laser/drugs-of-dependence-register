package com.lissajouslaser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Represents the database.
 */
public class Database {
    private final int thirdColumn = 3;
    private Connection con;
    private PreparedStatement prescriberEntry;
    private PreparedStatement pharmacistEntry;
    private PreparedStatement supplierEntry;
    private PreparedStatement drugEntry;
    private PreparedStatement transferEntry;

    /**
     * Default constructor. Connects to database and prepares
     * prepared SQL queries.
     */
    public Database() {
        System.out.println(connect());
        System.out.println(createTables());
        System.out.println(createPreparedStatements());
    }

    /**
     * Sets up connection with database.
     */
    public boolean connect() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:test.db");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /*
     * Check if one if the expected tables in the database exists. If
     * it does not exist, it is assumed the database is new and
     * generates all the tables required. Returns true if no
     * SQLException thrown, otherwise throws false.
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
                    + "    name varchar(64) NOT NULL,"
                    + "    last_name varchar(32),"
                    + "    address varchar(64) NOT NULL,"
                    + "    is_supplier boolean"
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
            prescriberEntry = con.prepareStatement(""
                    + "INSERT INTO prescribers"
                    + "    (first_name, last_name, prescriber_num)"
                    + "    VALUES (?, ?, ?);");

            pharmacistEntry = con.prepareStatement(""
                    + "INSERT INTO pharmacists"
                    + "    (first_name, last_name, registration)"
                    + "    VALUES (?, ?, ?);");

            drugEntry = con.prepareStatement(""
                    + "INSERT INTO drugs"
                    + "    (name, strength, form)"
                    + "    VALUES (?, ?, ?);");

            transferEntry = con.prepareStatement(""
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
                    + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

            return true;

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

    }

    /**
     * Adds prescriber to the database.
     */
    public boolean addPrescriber(Prescriber prescriber) {

        try {
            prescriberEntry.setString(1, prescriber.getfirstName());
            prescriberEntry.setString(2, prescriber.getlastName());
            prescriberEntry.setString(thirdColumn, prescriber.getprescriberNum());
            prescriberEntry.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Adds pharamcist to the database.
     */
    public boolean addPharmacist(Pharmacist pharmacist) {
        try {
            pharmacistEntry.setString(1, pharmacist.getFirst_name());
            pharmacistEntry.setString(2, pharmacist.getLast_name());
            pharmacistEntry.setString(thirdColumn, pharmacist.getRegistration());
            pharmacistEntry.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Adds drug to the database.
     */
    public boolean addDrug(Drug drug) {
        try {
            drugEntry.setString(1, drug.getName());
            drugEntry.setString(2, drug.getStrength());
            drugEntry.setString(thirdColumn, drug.getDose_form());
            drugEntry.execute();

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

            transferEntry.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    /**
     * Test.
     */
    public static void main(String[] args) {
        Database db = new Database();
        db.connect();
        db.createTables();
    }

    // public static void main(String[] args) {
    // dbConnector dbInterface = new dbConnector();

    // dbInterface.connect();

    // try {
    // Statement statement = dbInterface.con.createStatement();

    // String query =
    // "CREATE TABLE IF NOT EXISTS drug (id integer PRIMARY KEY,"z
    // + " trade_name char(16), generic_name char(32),"
    // + " strength char(16));";
    // statement.execute(query);

    // } catch (SQLException e) {
    // System.out.println(e);
    // System.exit(0);
    // }
    // }
}
