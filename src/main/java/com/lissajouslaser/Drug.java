package com.lissajouslaser;

/**
 * Defines a drug of a specific strength.
 */
public class Drug {
    private final int nameMaxLength = 32;
    private String name;
    private String strength;
    private String doseForm;

    /**
     * Constructor.
     */
    public Drug(String name, String strength, String doseForm) {
        this.name = name.toLowerCase();
        this.strength = strength.toLowerCase();
        this.doseForm = doseForm.toLowerCase();
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
            return "Name cannot be empty";
        }
        if (name.length() > nameMaxLength) {
            return "Name must be 32 characters or less";
        }
        if (!name.matches("[a-z\\- ]+")) {
            return "Name can only contain letters, hyphens and spaces";
        }
        return null;
    }

    private String validateStrength() {
        if (strength.isEmpty()) {
            return "Strength cannot be empty";
        }
        if (strength.length() > 16) {
            return "Strength must be 16 charactes or less";
        }
        if (!strength.matches("^[0-9][0-9a-z/]+[a-z]$")) {
            return "Strength must be in digits and have units";
        }
        return null;
    }

    private String validateDoseForm() {
        if (doseForm.isEmpty()) {
            return "Dose form cannot be empty.";
        }
        if (doseForm.length() > 16) {
            return "Dose form must be 16 characters or less";
        }
        if (!doseForm.matches("[a-z\\- ]+")) {
            return "Dose form must in alphabetical letters";
        }
        return null;
    }

}
