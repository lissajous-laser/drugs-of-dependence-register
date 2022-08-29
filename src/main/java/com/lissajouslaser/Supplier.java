package com.lissajouslaser;

/**
 * Defines a supplier.
 */
public class Supplier {
    static final int MAX_NAME_LENGTH = 64;
    static final int MAX_ADDRESS_LENGTH = 64;
    private String name;
    private String address;

    public Supplier(String name, String address) {
        this.name = name.toLowerCase();
        this.address = address.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    /**
     * Validates the fields for adding a supplier to
     * the database.
     *
     * @return An array of error messages, with each index
     *         representing a error message for a given field.
     *         0 - name
     *         1 - address
     *         If there is no error for an associated field, the
     *         String at the associated index is null;
     */
    public String[] validate() {
        String[] errors = new String[2];
        errors[0] = validateName();
        errors[1] = validateAddress();

        return errors;
    }

    /*
     * No character requirements for supplier name.
     */
    private String validateName() {
        if (name.isEmpty()) {
            return "Name cannot be empty";
        }
        if (name.length() > MAX_NAME_LENGTH) {
            return "Name must be " + MAX_NAME_LENGTH
                    + " characters or less";
        }
        return null;
    }

    private String validateAddress() {
        if (address.isEmpty()) {
            return "Address cannot be empty";
        }
        if (address.length() > MAX_ADDRESS_LENGTH) {
            return "Address must be " + MAX_ADDRESS_LENGTH
                    + " characters or less";
        }
        if (!address.matches("[a-z0-9\\-/ ,]+")) {
            return "Address can only have characters a-z, 0-9, spaces, commas,"
                    + " /, -,";
        }
        return null;
    }
}
