package com.lissajouslaser;

import java.time.LocalDate;

/**
 * Defines a record of transfer to or from the pharmacy. Used to
 * obtain transfers from the database, and to record a reverse supply.
 * For creating a transfer from user input, use TransferWrite.
 **/
public class TransferRetrieved implements Transfer {
    static final int NOTES_MAX_LENGTH = 32;
    private int transferId;     // Required if entry reversed.
    private LocalDate date;
    private Agent agent;
    private Drug drug;
    private int qtyIn;
    private int qtyOut;
    private int balance;
    private Prescriber prescriber;
    private String reference;
    private Pharmacist pharmacist;
    private String notes;

    /**
     * Constructor.
     */
    public TransferRetrieved(
            int transferId,
            LocalDate date,
            Agent agent,
            Drug drug,
            int qtyIn,
            int qtyOut,
            int balance,
            Prescriber prescriber,
            String reference,
            Pharmacist pharmacist,
            String notes) {
        this.transferId = transferId;
        this.date = date;
        this.agent = agent;
        this.drug = drug;
        this.qtyIn = qtyIn;
        this.qtyOut = qtyOut;
        this.balance = balance;
        this.prescriber = prescriber;
        this.reference = reference;
        this.notes = notes;
        this.pharmacist = pharmacist;
    }

    /**
     * Prepares instance variables so they are accurate for a reverse
     * entry, eg. swapping qtyIn and qtyOut values.
     **/
    public void reverseEntry(
            int balanceBefore,
            Pharmacist pharmacist,
            String notes) {

        int placeholder = qtyIn;
        qtyIn = qtyOut;
        qtyOut = placeholder;

        balance = balanceBefore + qtyIn - qtyOut;
        this.pharmacist = pharmacist;
        this.notes = notes;
        date = LocalDate.now();
        transferId = -1;
    }

    public int getTransferId() {
        return transferId;
    }

    /**
     * Returns date of transfer.
     */
    public LocalDate getDate() {
        if (date == null) {
            return LocalDate.now();
        }
        return date;
    }

    public Agent getAgent() {
        return agent;
    }

    public Drug getDrug() {
        return drug;
    }

    public int getQtyIn() {
        return qtyIn;
    }

    public int getQtyOut() {
        return qtyOut;
    }

    public int getBalance() {
        return balance;
    }

    public Prescriber getPrescriber() {
        return prescriber;
    }

    public String getReference() {
        return reference;
    }

    public Pharmacist getPharmacist() {
        return pharmacist;
    }

    public String getNotes() {
        return notes;
    }

    /**
     * Validates the field for reversing an entry in the database.
     *
     * @return An array of error messages, with each index
     *         representing a error message for a given field.
     *         0 - pharamcist
     *         1 - notes
     *         If there is no error for an associated field, the
     *         String at the associated index is empty;
     */    
    public String[] validateReverseEntry() {
        String[] errors = new String[2];

        errors[0] = TransferInput.validateForeignKey(pharmacist);
        // When reverseing entry, notes must not be empty.
        errors[1] = validateNotes();

        return errors;
    }

    private String validateNotes() {   
        if (notes.length() < 0) {
            return "Must fill in";
        } else {
            return Transfer.validateNotes(this.notes);
        }
    }
}
