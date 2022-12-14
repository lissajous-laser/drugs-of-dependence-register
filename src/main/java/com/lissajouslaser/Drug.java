package com.lissajouslaser;

/**
 * Defines a drug of a specific strength.
 */
public class Drug {
    static final int NAME_MAX_LENGTH = 32;
    static final int STRENGTH_MAX_LENGTH = 16;
    static final int DOSE_FORM_MAX_LENGTH = 16;
    private int id;
    private String name;
    private String strength;
    private String doseForm;

    /**
     * Constructor without id parameter. Used to insert entries to
     * database, where the primary key (id) has not yet been assigned.
     */
    public Drug(String name, String strength, String doseForm) {
        this.name = name.toUpperCase();
        this.strength = strength;
        this.doseForm = doseForm;
    }

    /**
     * Constructor with id parameter.
     */
    public Drug(int id, String name, String strength, String doseForm) {
        this.name = name.toUpperCase();
        this.id = id;
        this.strength = strength;
        this.doseForm = doseForm;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStrength() {
        return strength;
    }

    public String getDose_form() {
        return doseForm;
    }

    /**
     * Constructor.
     */
    public String[] validate() {
        final int numOfFields = 3;

        String[] errors = new String[numOfFields];
        errors[0] = validateName();
        errors[1] = validateStrength();
        errors[2] = validateDoseForm();

        return errors;
    }

    private String validateName() {
        if (name.isEmpty()) {
            return "Must fill in";
        }
        return validateName(this.name);
    }

    /**
     * Returns an empty string if the drug name is valid, otherwise
     * returns a string with an error message.
     */
    public static String validateName(String name) {
        if (name.length() > NAME_MAX_LENGTH) {
            return "Name must be " + NAME_MAX_LENGTH
                    + "characters or less";
        }
        if (!name.matches("[A-Z\\- ]*")) {
            return "Must only have letters,hyphens and spaces";
        }
        return "";
    }

    private String validateStrength() {
        if (!strength.matches("^[0-9][0-9A-Za-z/]*[A-Za-z]$")) {
            return "Must have numbers\nand units";
        }
        if (strength.isEmpty()) {
            return "Must fill in";
        }
        return validateStrength(this.strength);
    }

    /**
     * Returns an empty string if the drug strength is valid, otherwise
     * returns a string with an error message. Does not restrict
     * letters to all caps.
     */
    public static String validateStrength(String strength) {

        if (strength.length() > STRENGTH_MAX_LENGTH) {
            return "Must be " + STRENGTH_MAX_LENGTH
                    + " characters or less";
        }
        if (!strength.matches("[0-9A-Za-z./]*")) {
            return "Must have numbers and units";
        }
        return "";
    }

    private String validateDoseForm() {
        if (doseForm.isEmpty()) {
            return "Must fill in";
        }
        return validateDoseForm(this.doseForm);
    }

    /**
     * Returns an empty string if the drug dose form is valid,
     * otherwise returns a string with an error message. A dose form
     * is the type of formulation, e.g. tablet, capsule, cream, nasal
     * spray. Does not restrict letters to all caps.
     **/
    public static String validateDoseForm(String doseForm) {
        if (doseForm.length() > DOSE_FORM_MAX_LENGTH) {
            return "Must be " + DOSE_FORM_MAX_LENGTH
                    + " characters or less";
        }
        if (!doseForm.matches("[A-Za-z\\- ]*")) {
            return "Must only have letters, spaces, or hyphens";
        }
        return "";
    }

    @Override
    public String toString() {
        return name + " " + strength + " " + doseForm;
    }

}
