package com.lissajouslaser;

import java.time.LocalDate;

/**
 * Defines a record of transfer of a drug to or from the pharmacy.
 * This class is specifically used when a transfer is beng created
 * from user input, because it is structered to deal with input
 * validation and to map user input to database columns, for which
 * the instance variables do not all match with a database column.
 * For transfers retrieved from the database or for reverse
 * supplies, use TransferRetrieved. Static input validation
 * methods allow you to test indivdual inputs on a per-key input
 * basis. validateSupplyToPatient() and
 * validateReceiveFromSupplier() tests validates all the instance
 * variables, and provides addional tests that cannot be performed
 * on incomplete inputs.
 **/
public class TransferInput implements Transfer {
    static final int QTY_MAX_LENGTH = 5;
    static final int REFERENCE_MAX_LENGTH = 16;
    private Agent agent;
    private Drug drug;
    private String balanceBefore; // Balance before transfer.
    private String qty;           // Quantity transferred.
    private Prescriber prescriber;
    private String reference;     // Script number or invoice.
    private String notes;         // Optional notes.
    private Pharmacist pharmacist;

    /**
     * Constructor.
     */
    public TransferInput(
            Agent agent,
            Drug drug,
            String balanceBefore,
            String qty,
            Prescriber prescriber,
            String reference,
            String notes,
            Pharmacist pharmacist) {
        this.agent = agent;
        this.drug = drug;
        this.balanceBefore = balanceBefore;
        this.qty = qty;
        this.prescriber = prescriber;
        this.reference = reference.toUpperCase();
        this.notes = notes;
        this.pharmacist = pharmacist;
    }

    public LocalDate getDate() {
        return LocalDate.now();
    } 

    public Agent getAgent() {
        return agent;
    }

    public Drug getDrug() {
        return drug;
    }

    public int getBalanceBefore() {
        return Integer.valueOf(balanceBefore);
    }

    /**
     * Returns the amount of drug taken in.
     */
    public int getQtyIn() {
        if (agent instanceof Supplier) {
            return Integer.valueOf(qty);
        } else {
            return 0;
        }
    }

    /**
     * Returns the amount of drug given out.
     */
    public int getQtyOut() {
        if (agent instanceof Supplier) {
            return 0;
        } else {
            return Integer.valueOf(qty);
        }        
    }

    public int getQty() {
        return Integer.valueOf(qty);
    }

    public Prescriber getPrescriber() {
        return prescriber;
    }

    public String getReference() {
        return reference;
    }

    public String getNotes() {
        return notes;
    }

    public Pharmacist getPharmacist() {
        return pharmacist;
    }

    /**
     * Returns the new balance of the drug after the
     * transfer.
     */
    public int getBalance() {
        return getBalanceBefore() + getQtyIn() - getQtyOut();
    }

    /**
     * Validates the fields for adding a supply to a patient
     * the database.
     * drugId, agentId, prescriberId and pharmacistId are
     * assumed to be correct, because they should have been
     * searched using the database tables.
     * 
     * @return An array of error messages, with each index
     *         representing a error message for a given field.
     *         0 - agent
     *         1 - drug
     *         2 - quantity transferred
     *         3 - presriber
     *         4 - reference: script number or supplier receipt
     *         5 - notes
     *         6 - pharmacist
     *         If there is no error for an associated field, the
     *         String at the associated index is empty;
     */
    public String[] validateSupplyToPatient() {
        String[] errors = new String[7];

        errors[0] = validateForeignKey(agent);
        errors[1] = validateForeignKey(drug);
        errors[2] = validateQty();
        errors[3] = validateForeignKey(prescriber);
        errors[4] = validateReference();
        errors[5] = validateNotes();
        errors[6] = validateForeignKey(pharmacist);
        return errors;
    }

    /**
     * Validates the fields for receving an order from a
     * supplier the database.
     * drugId, agentId, prescriberId and pharmacistId are
     * assumed to be correct, because they should have been
     * searched using the database tables.
     * 
     * @return An array of error messages, with each index
     *         representing a error message for a given field.
     *         0 - agent
     *         1 - drug
     *         2 - quantity transferred
     *         3 - presriber
     *         4 - reference: script number or supplier receipt
     *         5 - notes
     *         6 - pharmacist
     *         If there is no error for an associated field, the
     *         String at the associated index is empty;
     */
    public String[] validateReceiveFromSupplier() {
        String[] errors = new String[7];

        errors[0] = validateForeignKey(agent);
        errors[1] = validateForeignKey(drug);
        errors[2] = validateQty();
        errors[3] = "";               // Index three not used.
        errors[4] = validateReference();
        errors[5] = validateNotes();
        errors[6] = validateForeignKey(pharmacist);
        return errors;
    }

    /**
     * Checks the parameter is a Agent, Drug, Prescriber or
     * Pharmacist.
     */
    public static String validateForeignKey(Object obj) {
        if (obj == null) {
            return "Must select";
        }
        return "";
    }

    private String validateQty() {
        if (qty.isEmpty()) {
            return "Must fill in";
        }
        if ("0".equals(qty)) {
            return "Can't be zero";
        }
        if (getBalance() < 0) {
            return "Resulting balance\ncan't be negative";
        }
        return validateQty(this.qty);
    }

    /**
     * Validates quantity to transfer as input text, returns a String
     * with a description of the first reason why address is invalid.
     * Otherwise returns an empty string.
     **/
    public static String validateQty(String qty) {
        if (!qty.matches("[0-9]*")) {
            return "Must be in digits";
        }
        if (qty.length() > 5) {
            return "Must be " + QTY_MAX_LENGTH
                    + " digits or less";
        }
        return "";
    }

    private String validateReference() {
        if (reference.isEmpty()) {
            return "Must fill in";
        }
        return validateReference(this.reference);
    }

    /**
     * Validates a transfer reference, which will be an invoice
     * number for supplier orders, or a dispensing number for
     * medication supply to patients. Returns a String with a
     * description of the first reason why firstName is invalid.
     * Otherwise returns an empty string. Allowed to be empty.
     **/
    public static String validateReference(String reference) {
        if (!reference.matches("[A-Z0-9]*")) {
            return "Must be numbers or letters";
        }
        if (reference.length() > REFERENCE_MAX_LENGTH) {
            return "Must be " + REFERENCE_MAX_LENGTH
                    + " characters or less";
        }
        return "";
    }

    private String validateNotes() {   
        return Transfer.validateNotes(this.notes);
    }
}
