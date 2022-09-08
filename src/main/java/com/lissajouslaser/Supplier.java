package com.lissajouslaser;

/**
 * Defines a supplier.
 */
public class Supplier implements Agent {
    static final int MAX_NAME_LENGTH = 64;
    static final int MAX_ADDRESS_LENGTH = 64;
    private int id;
    private String name;
    private String address;

    /**
     * Constructor without id parameter. Used to insert entries to
     * database, where the primary key (id) has not yet been assigned.
     */
    public Supplier(String name, String address) {
        this.name = name.toUpperCase();
        this.address = address;
    }

    /**
     * Constructor with id parameter.
     */
    public Supplier(int id, String name, String address) {
        this.id = id;
        this.name = name.toUpperCase();
        this.address = address;
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
     *         String at the associated index is empty;
     */
    public String[] validate() {
        String[] errors = new String[2];
        errors[0] = validateName();
        errors[1] = validateAddress();

        return errors;
    }

    private String validateName() {
        if (name.isEmpty()) {
            return "Must fill in";
        }
        return validateName(this.name);
    }

    /**
     * Validates partial input of address, returns a String with a
     * description of the first reason why address is invalid.
     * Otherwise returns an empty string.
     */
    public static String validateName(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            return "Name must be " + MAX_NAME_LENGTH
                    + " characters or less";
        }
        return "";
    }

    private String validateAddress() {
        if (address.isEmpty()) {
            return "Must fill in";
        }
        return Agent.validateAddress(this.address);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return name + " [" + address + "]";
    }
}
