package com.lissajouslaser;

/**
 * Defines a prescriber, e.g. doctor, optometrist, dentist.
 */
public class Prescriber extends Person {
    static final int prescriberNumLength = 7;
    private String prescriberNum;

    /**
     * Constructor.
     */
    public Prescriber(
            String firstName,
            String lastName,
            String prescriberNum) {
        super(firstName, lastName);
        this.prescriberNum = prescriberNum.toLowerCase();
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
     *         String at the associated index is null;
     */
    public String[] validate() {
        final int numOfFields = 3;

        String[] errors = new String[numOfFields];
        errors[0] = validateFirstName();
        errors[1] = validateLastName();
        errors[2] = validatePrescriberNumber();

        return errors;
    }

    /*
     * Prescriber number may be empty.
     */
    private String validatePrescriberNumber() {
        if (prescriberNum.length() != prescriberNumLength) {
            return "Prescriber number must be " + prescriberNumLength
                    + " digits";
        }
        if (!prescriberNum.matches("[0-9]+")) {
            return "Presiber number must be in digits";
        }
        return null;
    }
}
