package com.lissajouslaser;

import java.time.LocalDate;

/**
 * Represents a search in the database transfers table
 * for transfers of a specific drug over a specified time frame.
 */
public class SearchByDrug {
    private Drug drug;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Constructor.
     */
    public SearchByDrug(Drug drug, LocalDate startDate, LocalDate endDate) {
        this.drug = drug;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Drug getDrug() {
        return drug;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Validates the fields for the search.
     *
     * @return An array of error messages, with each index
     *         representing a error message for a given field.
     *         0 - drug;
     *         1 - startDate;
     *         2 - endDate;
     *         If there is no error for an associated field, the
     *         String at the associated index is empty;
     */
    public String[] validate() {
        final int numOfFields = 3;

        String[] errors = new String[numOfFields];
        errors[0] = (drug == null) ? "Must select" : "";
        errors[1] = (startDate == null) ? "Must be valid" : "";
        errors[2] = (endDate == null) ? "Must be valid" : "";

        return errors;
    } 
}
