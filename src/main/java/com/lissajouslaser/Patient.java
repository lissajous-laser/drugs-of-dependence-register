package com.lissajouslaser;

/**
 * Defines a patient.
 */
public class Patient extends Person implements IAgent {
    static final int MAX_ADDRESS_LENGTH = 64;
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

    private String validateFirstName() {
        return validateFirstName(getFirstName());
    }

    private String validateLastName() {
        return validateLastName(getLastName());
    }

    private String validateAddress() {
        return validateAddress(this.address);
    }

    /**
     * Validates partial input of address, returns a String with a
     * description of the first reason why address is invalid.
     * Otherwise returns null.
     **/
    public static String validateAddress(String address) {
        if (address.isEmpty()) {
            return "Must fill in";
        }
        if (address.length() > MAX_ADDRESS_LENGTH) {
            return "Must be " + MAX_ADDRESS_LENGTH
                    + " characters or less";
        }
        if (!address.matches("[A-Z0-9\\-/ ,]+")) {
            return "Must be letters, numbers, spaces, or: /, -";
        }
        return null;
    }

    @Override
    public String getName() {
        return getLastName() + ", " + getFirstName();
    }

    @Override
    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getName() + " [" + getAddress() + "]";
    }
}
