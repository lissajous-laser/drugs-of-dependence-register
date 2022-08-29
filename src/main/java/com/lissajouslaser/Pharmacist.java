package com.lissajouslaser;

/**
 * Defines a pharmacist.
 */
public class Pharmacist {
    private final int firstNameMaxLength = 16;
    private final int lastNameMaxLength = 32;
    private String firstName;
    private String lastName;
    private String registration;

    /**
     * Constructor.
     */
    public Pharmacist(String firstName, String lastName, String registration) {
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        this.registration = registration.toLowerCase();
    }

    public String getFirst_name() {
        return firstName;
    }

    public String getLast_name() {
        return lastName;
    }

    public String getRegistration() {
        return registration;
    }

    public String[] validate() {
        final int numOfFields = 3;

        String[] errors = new String[numOfFields];
        errors[0] = validateFirstName();
        errors[1] = validateLastName();
        errors[2] = validateRegistration();

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
     * Registration may be empty.
     */
    private String validateRegistration() {
        int registrationLength = 13;

        if (registration.length() != registrationLength) {
            return "Registration must be 13 characters.";
        }
        if (!registration.matches("pha[0-9]+")) {
            return "Registration must be as printed your card.";
        }
        return null;
    }
}
