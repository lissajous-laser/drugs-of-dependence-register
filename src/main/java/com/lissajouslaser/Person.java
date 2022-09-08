package com.lissajouslaser;

/**
 * Defines a person, which is subclassed into pharmacist,
 * prescriber, and person.
 */
public abstract class Person {
    static final int FIRST_NAME_MAX_LENGTH = 32;
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

    String validateFirstName() {
        if (firstName.isEmpty()) {
            return "Must fill in";
        }
        return validateFirstName(this.firstName);

    }

    /**
     * Validates partial input of firstName, returns a String with a
     * description of the first reason why firstName is invalid.
     * Otherwise returns an empty String.
     **/
    public static String validateFirstName(String firstName) {
        if (firstName.length() > FIRST_NAME_MAX_LENGTH) {
            return "Must be " + FIRST_NAME_MAX_LENGTH
                    + " characters or less";
        }
        if (!firstName.matches("[A-Z\\-' ]*")) {
            return "Can only have letters or hyphens";
        }
        return "";
    }

    String validateLastName() {
        if (lastName.isEmpty()) {
            return "Must fill in";
        }
        return validateLastName(this.lastName);
    }

    /**
     * Validates partial input of lastName, returns a String with a
     * description of the first reason why lastName is invalid.
     * Otherwise returns an empty string.
     **/
    public static String validateLastName(String lastName) {
        if (lastName.length() > LAST_NAME_MAX_LENGTH) {
            return "Must be " + LAST_NAME_MAX_LENGTH
                    + " characters or less";
        }
        if (!lastName.matches("[A-Z\\-' ]*")) {
            return "Can only have letters or hyphens";
        }
        return "";
    }

}
