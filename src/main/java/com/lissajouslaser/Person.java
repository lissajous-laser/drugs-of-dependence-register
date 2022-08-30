package com.lissajouslaser;

/**
 * Defines a person, which is subclassed into pharmacist,
 * prescriber, and person.
 */
public abstract class Person {
    static final int FIRST_NAME_MAX_LENGTH = 16;
    static final int LAST_NAME_MAX_LENGTH = 32;
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName.toUpperCase();
        this.lastName = lastName.toUpperCase();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * Validates input of firstName, returns a String with a
     * description of the first reason why firstName is invalid.
     * Otherwise returns null.
     **/
    String validateFirstName() {
        if (firstName.isEmpty()) {
            return "Cannot be empty";
        }
        if (firstName.length() > FIRST_NAME_MAX_LENGTH) {
            return "Must be " + FIRST_NAME_MAX_LENGTH
                    + " characters or less";
        }
        if (!firstName.matches("[A-Z\\-]+")) {
            return "Can only have letters or hyphens";
        }
        return null;
    }

    /**
     * Validates input of lastName, returns a String with a
     * description of the first reason why lastName is invalid.
     * Otherwise returns null.
     **/
    String validateLastName() {
        if (lastName.isEmpty()) {
            return "Cannot be empty";
        }
        if (lastName.length() > LAST_NAME_MAX_LENGTH) {
            return "Must be " + LAST_NAME_MAX_LENGTH
                    + " characters or less";
        }
        if (!lastName.matches("[A-Z\\-]+")) {
            return "Can only have letters or hyphens";
        }
        return null;
    }
    
}
