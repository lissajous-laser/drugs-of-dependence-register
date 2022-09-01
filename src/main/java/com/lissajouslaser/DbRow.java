package com.lissajouslaser;

/*
 * Lightweight container class for repesenting a row from one of
 * the database tables of: drug, patient, pharmacist, prescriber,
 * or supplier. Used to help with selecting the appropriate
 * foreign keys for database entries into the transfers table.
 */


public class DbRow {
    private int id;
    private String description;

    public DbRow(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
