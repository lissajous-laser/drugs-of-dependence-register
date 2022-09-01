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
     * Validates partial input of firstName, returns a String with a
     * description of the first reason why firstName is invalid.
     * Otherwise returns null.
     **/
    public static String validateFirstName(String firstName) {
        if (firstName.isEmpty()) {
            return "Must fill in";
        }
        if (firstName.length() > FIRST_NAME_MAX_LENGTH) {
            return "Must be " + FIRST_NAME_MAX_LENGTH
                    + " characters or less";
        }
        if (!firstName.matches("[A-Z\\- ]+")) {
            return "Can only have letters or hyphens";
        }
        return null;
    }

    /**
     * Validates partial input of lastName, returns a String with a
     * description of the first reason why lastName is invalid.
     * Otherwise returns null.
     **/
    public static String validateLastName(String lastName) {
        if (lastName.isEmpty()) {
            return "Must fill in";
        }
        if (lastName.length() > LAST_NAME_MAX_LENGTH) {
            return "Must be " + LAST_NAME_MAX_LENGTH
                    + " characters or less";
        }
        if (!lastName.matches("[A-Z\\- ]+")) {
            return "Can only have letters or hyphens";
        }
        return null;
    }
    
}
