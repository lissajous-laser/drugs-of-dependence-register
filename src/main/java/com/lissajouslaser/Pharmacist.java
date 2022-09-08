package com.lissajouslaser;

/**
 * Defines a pharmacist.
 * Contains instance variables to carry data to and from a SQL
 * database, and methods for input validation. Static input
 * validation methods allow you to test indivdual inputs key by
 * key in a GUI. validate() tests the instance variables all at
 * once, and provides addional tests that cannot be performed on
 * incomplete inputs.
 */
public class Pharmacist extends Person {
    static final int REGISTRATION_LENGTH = 13;
    private int id;
    private String registration;

    /**
     * Constructor without id parameter. Used to insert entries to
     * database, where the primary key (id) has not yet been assigned.
     */
    public Pharmacist(String firstName, String lastName, String registration) {
        super(firstName, lastName);
        this.registration = registration.toUpperCase();
    }

    /**
     * Constructor with id parameter.
     */
    public Pharmacist(
            int id,
            String firstName,
            String lastName,
            String registration) {
        super(firstName, lastName);
        this.id = id;
        this.registration = registration.toUpperCase();
    }

    public String getRegistration() {
        return registration;
    }

    public int getId() {
        return id;
    }

    /**
     * Validates the fields for adding a pharmacist to
     * the database.
     *
     * @return An array of error messages, with each index
     *         representing a error message for a given field.
     *         0 - firstName;
     *         1 - lastName;
     *         2 - registration;
     *         If there is no error for an associated field, the
     *         String at the associated index is empty;
     */
    public String[] validate() {
        final int numOfFields = 3;

        String[] errors = new String[numOfFields];
        errors[0] = validateFirstName();
        errors[1] = validateLastName();
        errors[2] = validateRegistration();

        return errors;
    }

    private String validateRegistration() {
        if (!registration.matches("PHA[0-9]{10}")) {
            return "Must be as printed\non certificate";
        }
        if (registration.length() != REGISTRATION_LENGTH) {
            return "Must be " + REGISTRATION_LENGTH
                    + "\ncharacters";
        }
        return validateRegistration(this.registration);
    }

    /**
     * Validates partial input of address, returns a String with a
     * description of the first reason why address is invalid.
     * Otherwise returns an empty string. Registration permitted to be empty. A
     * valid registration is of the form PHAxxxxxxxxxx, where x's are
     * digits.
     */
    public static String validateRegistration(String registration) {
        if (registration.length() > REGISTRATION_LENGTH) {
            return "Must be " + REGISTRATION_LENGTH
                    + " characters";
        }
        if (!registration.isEmpty()
                && !registration.matches("[PHA0-9]*")) {
            return "Must be as printed your certificate";
        }
        return "";
    }

    @Override
    public String toString() {
        return getLastName() + ", " + getFirstName()
                + " [" + registration + "]";
    }
}
