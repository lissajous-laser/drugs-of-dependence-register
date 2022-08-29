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
        this.address = address.toLowerCase();
    }

    public String getAddress_name() {
        return address;
    }

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
        if (!address.matches("[a-z0-9\\-/ ,]")) {
            return "Address can only have characters a-z, 0-9, spaces, commas,"
                    + " /, -,";
        }
        return null;
    }
}
