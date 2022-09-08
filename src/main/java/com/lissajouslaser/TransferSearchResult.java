package com.lissajouslaser;

import java.time.LocalDate;

/**
 * A container class representing transfer entries with joins
 * on all its foreign keys.
 */
public class TransferSearchResult {
    private int transferId;     // Required if entry reversed.
    private int drugId; // Required if entry reversed.
    private LocalDate date;
    private String agent;
    private String drug;
    private int qtyIn;
    private int qtyOut;
    private int balance;
    private String prescriber;
    private String reference;
    private String pharmacist;
    private String notes;

    /**
     * Constructor.
     */
    public TransferSearchResult(
            int transferId,
            int drugId,
            LocalDate date,
            String agent,
            String drug,
            int qtyIn,
            int qtyOut,
            int balance,
            String prescriber,
            String reference,
            String pharmacist,
            String notes) {
        this.transferId = transferId;
        this.drugId = drugId;
        this.date = date;
        this.agent = agent;
        this.drug = drug;
        this.qtyIn = qtyIn;
        this.qtyOut = qtyOut;
        this.balance = balance;
        this.prescriber = prescriber;
        this.reference = reference;
        this.pharmacist = pharmacist;
        this.notes = notes;
    }

    public int getTransferId() {
        return transferId;
    }

    public int getDrugId() {
        return drugId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAgent() {
        return agent;
    }

    public String getDrug() {
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

    public String getPrescriber() {
        return prescriber;
    }

    public String getReference() {
        return reference;
    }

    public String getPharmacist() {
        return pharmacist;
    }

    public String getNotes() {
        return notes;
    }
}
