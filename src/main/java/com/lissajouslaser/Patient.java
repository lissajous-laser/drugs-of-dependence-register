package com.lissajouslaser;

/**
 * Defines a patient.
 */
public class Patient {
    private String firstName;
    private String lastName;
    private String address;

    /**
     * Constructor.
     */
    public Patient(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getFirst_name() {
        return firstName;
    }

    public String getLast_name() {
        return lastName;
    }

    public String getAddress_name() {
        return address;
    }
}
