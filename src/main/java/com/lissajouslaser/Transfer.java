package com.lissajouslaser;

import java.time.LocalDateTime;

/**
 * Defines a record of transfer of a drug to or from the
 * pharmacy.
 */
public class Transfer {
    private LocalDateTime transferDate;
    private int agentId;
    private int drugId;
    private int qtyIn;
    private int qtyOut;
    private int balance;
    private int prescriberId;
    private int pharmacistId;
    private String reference;
    private String notes;

    public Transfer(LocalDateTime transferDate, int agentId, int drugId, int qtyIn, int qtyOut, int balance,
            int prescriberId, int pharmacistId, String reference, String notes) {
        this.transferDate = transferDate;
        this.agentId = agentId;
        this.drugId = drugId;
        this.qtyIn = qtyIn;
        this.qtyOut = qtyOut;
        this.balance = balance;
        this.prescriberId = prescriberId;
        this.pharmacistId = pharmacistId;
        this.reference = reference;
        this.notes = notes;
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
        return balance;
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
}
