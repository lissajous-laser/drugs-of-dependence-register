package com.lissajouslaser;

/**
 * Defines a prescriber, e.g. doctor, optometrist, dentist.
 * Contains instance variables to carry data to and from a SQL
 * database, and methods for input validation. Static input
 * validation methods allow you to test indivdual inputs key by
 * key in a GUI. validate() tests the instance variables all at
 * once, and provides addional tests that cannot be performed on
 * incomplete inputs.
 */
public class Prescriber extends Person {
    static final int PRESCRIBER_NUM_LENGTH = 7;
    private int id;
    private String prescriberNum;

    /**
     * Constructor without id parameter. Used to insert entries to
     * database, where the primary key (id) has not yet been assigned.
     */
    public Prescriber(
            String firstName,
            String lastName,
            String prescriberNum) {
        super(firstName, lastName);
        this.prescriberNum = prescriberNum.toUpperCase();
    }

    /**
     * Constructor with id parameter.
     */
    public Prescriber(
            int id,
            String firstName,
            String lastName,
            String prescriberNum) {
        super(firstName, lastName);
        this.id = id;
        this.prescriberNum = prescriberNum.toUpperCase();
    }

    public int getId() {
        return id;
    }

    public String getprescriberNum() {
        return prescriberNum;
    }

    /**
     * Validates the fields for adding a presriber to
     * the database.
     *
     * @return An array of error messages, with each index
     *         representing a error message for a given field.
     *         0 - firstName;
     *         1 - lastName;
     *         2 - prescriberNum;
     *         If there is no error for an associated field, the
     *         String at the associated index is empty;
     */
    public String[] validate() {
        final int numOfFields = 3;

        String[] errors = new String[numOfFields];
        errors[0] = validateFirstName();
        errors[1] = validateLastName();
        errors[2] = validatePrescriberNumber();

        return errors;
    }

    private String validatePrescriberNumber() {
        if (prescriberNum.length() != PRESCRIBER_NUM_LENGTH) {
            return "Must be " + PRESCRIBER_NUM_LENGTH
                    + " digits";
        }
        return validatePrescriberNumber(this.prescriberNum);
    }

    /**
     * Validates partial input of presriber number, returns a String
     * with a description of the first reason why address is invalid.
     * Otherwise returns an empty string. Permitted to be empty.
     */
    public static String validatePrescriberNumber(String prescriberNum) {
        if (!prescriberNum.isEmpty()
                && !prescriberNum.matches("[0-9]*")) {
            return "Must be in digits";
        }
        if (prescriberNum.length() > PRESCRIBER_NUM_LENGTH) {
            return "Must be " + PRESCRIBER_NUM_LENGTH
                    + " digits";
        }
        return "";
    }

    @Override
    public String toString() {
        return getLastName() + ", " + getFirstName()
                + " [" + prescriberNum + "]";
    }
}
