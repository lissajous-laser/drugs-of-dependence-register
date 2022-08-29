package com.lissajouslaser;

/**
 * Defines a pharmacist.
 */
public class Pharmacist extends Person {
    private final int registrationLength = 13;
    private String registration;

    /**
     * Constructor.
     */
    public Pharmacist(String firstName, String lastName, String registration) {
        super(firstName, lastName);
        this.registration = registration.toLowerCase();
    }

    public String getRegistration() {
        return registration;
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
     *         String at the associated index is null;
     */
    public String[] validate() {
        final int numOfFields = 3;

        String[] errors = new String[numOfFields];
        errors[0] = validateFirstName();
        errors[1] = validateLastName();
        errors[2] = validateRegistration();

        return errors;
    }

    /*
     * Registration may be empty.
     */
    private String validateRegistration() {

        if (registration.length() != registrationLength) {
            return "Registration must be " + registrationLength
                    + " characters.";
        }
        if (!registration.matches("pha[0-9]+")) {
            return "Registration must be as printed your card.";
        }
        return null;
    }
}
