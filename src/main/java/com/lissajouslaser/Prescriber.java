package com.lissajouslaser;

/**
 * Defines a prescriber, e.g. doctor, optometrist, dentist.
 */
public class Prescriber {
    private final int firstNameMaxLength = 16;
    private final int lastNameMaxLength = 32;
    private final int prescriberNumLength = 7;
    private String firstName;
    private String lastName;
    private String prescriberNum;

    /**
     * Constructor.
     */
    public Prescriber(
            String firstName,
            String lastName,
            String prescriberNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.prescriberNum = prescriberNum;
    }

    public String getfirstName() {
        return firstName;
    }

    public String getlastName() {
        return lastName;
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

    private String validateFirstName() {
        if (firstName.isEmpty()) {
            return "First name cannot be empty";
        }
        if (firstName.length() > firstNameMaxLength) {
            return "First name must be 16 characters or less";
        }
        if (!firstName.matches("[A-Za-z\\-]+")) {
            return "First name can only alphabetical letters or hyphens";
        }
        return null;
    }

    private String validateLastName() {
        if (lastName.isEmpty()) {
            return "Last name cannot be empty";
        }
        if (lastName.length() > lastNameMaxLength) {
            return "Last name must be 32 characters or less";
        }
        if (!lastName.matches("[A-Za-z\\-]+")) {
            return "Last name can only alphabetical letters or hyphens";
        }
        return null;
    }

    /*
     * Prescriber number may be empty.
     */
    private String validatePrescriberNumber() {
        if (prescriberNum.length() != prescriberNumLength) {
            return "Prescriber number must be 7 digits";
        }
        if (!prescriberNum.matches("[0-9]+")) {
            return "Presiber number must be in digits";
        }
        return null;
    }
}
