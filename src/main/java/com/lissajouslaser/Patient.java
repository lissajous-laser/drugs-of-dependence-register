package com.lissajouslaser;

/**
 * Defines a patient.
 * Contains instance variables to carry data to and from a SQL
 * database, and methods for input validation. Static input
 * validation methods allow you to test indivdual inputs key by
 * key in a GUI. validate() tests the instance variables all at
 * once, and provides addional tests that cannot be performed on
 * incomplete inputs.
 */
public class Patient extends Person implements IAgent {
    private int id;
    private String address;

    /**
     * Constructor without id parameter. Used to insert entries to
     * database, where the primary key (id) has not yet been assigned.
     */
    public Patient(String firstName, String lastName, String address) {
        super(firstName, lastName);
        this.address = address.toUpperCase();
    }

    /**
     * Constructor with id parameter.
     */
    public Patient(int id, String firstName, String lastName, String address) {
        super(firstName, lastName);
        this.id = id;
        this.address = address.toUpperCase();
    }

    /**
     * Validates the fields for adding a patient to
     * the database.
     *
     * @return An array of error messages, with each index
     *         representing a error message for a given field.
     *         0 - firstName;
     *         1 - lastName;
     *         2 - address;
     *         If there is no error for an associated field, the
     *         String at the associated index is null;
     */
    public String[] validate() {
        final int numOfFields = 3;

        String[] errors = new String[numOfFields];
        errors[0] = validateFirstName();
        errors[1] = validateLastName();
        errors[2] = validateAddress();

        return errors;
    }

    private String validateAddress() {
        if (address.isEmpty()) {
            return "Must fill in";
        }
        return IAgent.validateAddress(this.address);
    }

    @Override
    public String getName() {
        return getLastName() + ", " + getFirstName();
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getName() + " [" + getAddress() + "]";
    }
}
