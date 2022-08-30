package com.lissajouslaser;

/**
 * Defines a patient.
 */
public class Patient extends Person {
    static final int MAX_ADDRESS_LENGTH = 64;
    private String address;

    /**
     * Constructor.
     */
    public Patient(String firstName, String lastName, String address) {
        super(firstName, lastName);
        this.address = address.toUpperCase();
    }

    public String getAddress_name() {
        return address;
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
            return "Address cannot be empty";
        }
        if (address.length() > MAX_ADDRESS_LENGTH) {
            return "Address must be " + MAX_ADDRESS_LENGTH
                    + " characters or less";
        }
        if (!address.matches("[A-Z0-9\\-/ ,]+")) {
            return "Address can only have characters a-z, 0-9, spaces, commas,"
                    + " /, -,";
        }
        return null;
    }
}
