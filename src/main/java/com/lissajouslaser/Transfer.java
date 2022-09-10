package com.lissajouslaser;

import java.time.LocalDate;

/**
 * Specifies a transfer. Implementing classes need methods to
 * generate values for all columns in the transfers table of the
 * database.
 */
public interface Transfer {
    static final int NOTES_MAX_LENGTH = 32;

    public LocalDate getDate();

    public Agent getAgent();

    public Drug getDrug();

    public int getQtyIn();

    public int getQtyOut();

    public int getBalance();

    public Prescriber getPrescriber();

    public String getReference();

    public Pharmacist getPharmacist();

    public String getNotes();

    /**
     * Validates notes, No character requirements for notes. Returns a
     * String with a description of the first reason why firstName is
     * invalid. Otherwise returns an empty string.
     **/
    public static String validateNotes(String notes) {
        if (notes.length() > NOTES_MAX_LENGTH) {
            return "Notes must be " + NOTES_MAX_LENGTH
                    + " characters or less";
        }
        return "";
    }
}
