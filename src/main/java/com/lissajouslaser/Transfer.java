package com.lissajouslaser;

/**
 * Defines a record of transfer of a drug to or from the
 * pharmacy.
 * Contains instance variables to carry data to and from a SQL
 * database, and methods for input validation. Static input
 * validation methods allow you to test indivdual inputs key by
 * key in a GUI. validateSupplyToPatient() and
 * validateReceiveFromSupplier tests the instance variables all at
 * once, and provides addional tests that cannot be performed on
 * incomplete inputs.
 */
public class Transfer {
    static final int REFERENCE_MAX_LENGTH = 16;
    static final int NOTES_MAX_LENGTH = 64;
    private IAgent agent;
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
    public Transfer(
            IAgent agent,
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
        this.reference = reference;
        this.notes = notes;
        this.pharmacist = pharmacist;
    }

    public IAgent getAgent() {
        return agent;
    }

    public Drug getDrug() {
        return drug;
    }

    public int getBalanceBefore() {
        return Integer.valueOf(balanceBefore);
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
    public int getBalanceAfter() {
        if (agent instanceof Patient) {
            return getBalanceBefore() - getQty();
        } else {
            return getBalanceBefore() + getQty();
        }
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
     *         String at the associated index is null;
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
     *         String at the associated index is null;
     */
    public String[] validateReceiveFromSupplier() {
        String[] errors = new String[7];

        errors[0] = validateForeignKey(agent);
        errors[1] = validateForeignKey(drug);
        errors[2] = validateQty();
        // Index three not used.
        errors[4] = validateReference();
        errors[5] = validateNotes();
        errors[6] = validateForeignKey(pharmacist);
        return errors;
    }

    /*
     * The parameter is a IAgent, Drug, Prescriber or
     * Pharmacist.
     */
    private String validateForeignKey(Object obj) {
        if (obj == null) {
            return "Must select";
        }
        return null;
    }

    private String validateQty() {
        if (qty.isEmpty()) {
            return "Must fill in";
        }
        if (balanceBefore.isEmpty()) {
            // Assumes previous balance obtained by selecting a drug.
            return "Must select drug";
        }
        if ("0".equals(qty)) {
            return "Can't be zero";
        }
        if (getBalanceAfter() < 0) {
            return "Resulting balance can't be negative";
        }
        return validateQty(this.qty);
    }

    /**
     * Validates quantity to transfer as input text, returns a String
     * with a description of the first reason why address is invalid.
     * Otherwise returns null.
     **/
    public static String validateQty(String qty) {
        if (!qty.isEmpty()
                && !qty.matches("[0-9]+")) {
            return "Must be in digits";
        }
        return null;

    }

    private String validateReference() {
        return validateReference(this.reference);
    }

    /**
     * Validates a transfer reference, which will be an invoice
     * number for supplier orders, or a dispensing number for
     * medication supply to patients. Returns a String with a
     * description of the first reason why firstName is invalid.
     * Otherwise returns null. Allowed to be empty.
     **/
    public static String validateReference(String reference) {
        if (!reference.isEmpty()
                && !reference.matches("[A-Z0-9]+")) {
            return "Must be numbers or letters";
        }
        if (reference.length() > REFERENCE_MAX_LENGTH) {
            return "Must be " + REFERENCE_MAX_LENGTH
                    + " characters or less";
        }
        return null;
    }

    private String validateNotes() {
        return validateNotes(this.notes);
    }

    /**
     * Validates notes, No character requirements for notes. Returns a
     * String with a description of the first reason why firstName is
     * invalid. Otherwise returns null.
     **/
    public static String validateNotes(String notes) {
        if (notes.length() > NOTES_MAX_LENGTH) {
            return "Notes must be " + NOTES_MAX_LENGTH
                    + " characters or less";
        }
        return null;
    }

}
