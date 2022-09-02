package com.lissajouslaser;

import java.time.LocalDateTime;

/**
 * Defines a record of transfer of a drug to or from the
 * pharmacy.
 */
public class Transfer {
    static final int REFERENCE_MAX_LENGTH = 16;
    static final int NOTES_MAX_LENGTH = 64;
    static final int THIRD_INDEX = 3;
    static final int FOURTH_INDEX = 4;
    static final int FIFTH_INDEX = 5;
    static final int SIXTH_INDEX = 6;
    static final int SEVENTH_INDEX = 7;
    static final int EIGHTH_INDEX = 8;
    static final int NINTH_INDEX = 9;
    static final int TENTH_INDEX = 10;
    private LocalDateTime transferDate;
    private int agentId;
    private boolean isSupplier;
    private int drugId;
    /*
     * qtyEntered is the amount being transferred, qtyIn, qtyOut
     * and balance are calculated once agent is found out the be a
     * supplier (qtyIn) or patient (qtyOut).
     */
    private int qtyEntered;
    private int qtyIn;
    private int qtyOut;
    private int oldBalance;
    private int newBalance;
    private int prescriberId;
    private int pharmacistId;
    private String reference;
    private String notes; // Optional notes of transfer.

    /**
     * Constructor.
     */
    public Transfer(
            LocalDateTime transferDate,
            int agentId,
            boolean isSupplier,
            int drugId,
            int qtyEntered,
            int oldBalance,
            int prescriberId,
            int pharmacistId,
            String reference,
            String notes) {
        this.transferDate = transferDate;
        this.agentId = agentId;
        this.isSupplier = isSupplier;
        this.drugId = drugId;
        this.qtyEntered = qtyEntered;
        this.oldBalance = oldBalance;
        this.prescriberId = prescriberId;
        this.pharmacistId = pharmacistId;
        this.reference = reference.toLowerCase();
        this.notes = notes;
        calculateTransfer();
    }

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    public int getAgentId() {
        return agentId;
    }

    public int getDrugId() {
        return drugId;
    }

    public int getQtyIn() {
        return qtyIn;
    }

    public int getQtyOut() {
        return qtyOut;
    }

    public int getBalance() {
        return oldBalance;
    }

    public int getPrescriberId() {
        return prescriberId;
    }

    public int getPharmacistId() {
        return pharmacistId;
    }

    public String getReference() {
        return reference;
    }

    public String getNotes() {
        return notes;
    }

    /**
     * Validates the fields for adding a transfer to
     * the database.
     * drugId, agentId, prescriberId and pharmacistId are
     * assumed to be correct, because they should have been
     * searched using the database tables.
     * 
     * @return An array of error messages, with each index
     *         representing a error message for a given field.
     *         0 - balance <- not a field, but derived from one.
     *         1 - reference
     *         2 - notes
     *         If there is no error for an associated field, the
     *         String at the associated index is null;
     */
    public String[] validate() {
        String[] errors = new String[3];

        errors[0] = validateBalance();
        errors[1] = validateReference();
        errors[2] = validateNotes();
        return errors;
    }

    /*
     * Balance of the medication in the pharmacy - cannot
     * be negative.
     */
    private String validateBalance() {
        if (newBalance < 0) {
            return "The resulting balance cannot be negative";
        }
        return null;
    }

    /**
     * Validates partial input of quantity being supplied or received,
     * returns a String with a description of the first reason why
     * address is invalid. Otherwise returns null.
     **/
    public static String validateQty(String qty) {
        if (qty.isEmpty()) {
            return "Must fill in";
        }
        if (!qty.matches("[0-9]+")) {
            return "Must be in digits";
        }
        return null;
    }

    /*
     * Validates a transfer reference, which will be an invoice
     * number for supplier orders, or a dispensing number for
     * medication supply to patients.
     */
    private String validateReference() {
        if (!reference.matches("[A-Z0-9 ]+")) {
            return "Reference must be numbers, alphabetic letters, or spaces";
        }
        if (reference.length() > REFERENCE_MAX_LENGTH) {
            return "Reference must be " + REFERENCE_MAX_LENGTH
                    + " characters or less";
        }
        return null;
    }

    /*
     * No character requirements for notes.
     */
    private String validateNotes() {
        if (notes.length() > NOTES_MAX_LENGTH) {
            return "Notes must be " + NOTES_MAX_LENGTH
                    + " characters or less";
        }
        return null;
    }

    private void calculateTransfer() {
        if (isSupplier) {
            qtyIn = qtyEntered;
            qtyOut = 0;
        } else {
            qtyOut = qtyEntered;
            qtyIn = 0;
        }
        newBalance = oldBalance + qtyIn - qtyOut;
    }

}
