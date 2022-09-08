package com.lissajouslaser;

public class ReverseEntry { 
    private int NOTES_MIN_LENGTH = 6;   
    private int id;            // Primary key of transfer to reverse.
    private int balanceAfter;  // New balance.
    private String notes;

    public ReverseEntry(int id, int balanceAfter, String notes) {
        this.id = id;
        this.balanceAfter = balanceAfter;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public int getBalanceAfter() {
        return balanceAfter;
    }

    public String getNotes() {
        return notes;
    }

    /**
     * Validates the fields for adding a supplier to
     * the database.
     *
     * @return An array of error messages, with each index
     *         representing a error message for a given field.
     *         0 - notes
     *         If there is no error for an associated field, the
     *         String at the associated index is empty;
     */    
    public String[] validate() {
        String[] errors = new String[1];
        errors[0] = validateNotes();

        return errors;
    }

    private String validateNotes() {
        if (notes.length() < NOTES_MIN_LENGTH) {
            return "Must be " + NOTES_MIN_LENGTH
                    + "\ncharacters or more";
        }
        return validateNotes(this.notes);
    }

    public String validateNotes(String notes) {
        return Transfer.validateNotes(notes);
    }
}
