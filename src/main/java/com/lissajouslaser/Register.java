package com.lissajouslaser;

import java.time.LocalDate;

/**
 * A container class representing transfer entries with joins
 * on all its foreign keys.
 */
public class Register {
    private LocalDate date;
    private String agentName;
    private String address;
    private String drug;
    private int qtyIn;
    private int qtyOut;
    private int balance;
    private String prescriberName;
    private String reference;
    private String pharmacistName;
    private String notes;

    /**
     * Constructor.
     */
    public Register(
            LocalDate date,
            String agentName,
            String address,
            String drug,
            int qtyIn,
            int qtyOut,
            int balance,
            String prescriberName,
            String reference,
            String pharmacistName,
            String notes) {
        this.date = date;
        this.agentName = agentName;
        this.address = address;
        this.drug = drug;
        this.qtyIn = qtyIn;
        this.qtyOut = qtyOut;
        this.balance = balance;
        this.prescriberName = prescriberName;
        this.reference = reference;
        this.pharmacistName = pharmacistName;
        this.notes = notes;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAgentName() {
        return agentName;
    }

    public String getAddress() {
        return address;
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

    public String getPrescriberName() {
        return prescriberName;
    }

    public String getReference() {
        return reference;
    }

    public String getPharmacistName() {
        return pharmacistName;
    }

    public String getNotes() {
        return notes;
    }
}
