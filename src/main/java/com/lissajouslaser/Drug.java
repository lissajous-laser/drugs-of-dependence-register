package com.lissajouslaser;

/**
 * Defines a drug of a specific strength.
 */
public class Drug {
    private String name;
    private String strength;
    private String doseForm;

    /**
     * Constructor.
     */
    public Drug(String name, String strength, String doseForm) {
        this.name = name;
        this.strength = strength;
        this.doseForm = doseForm;
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

    public String[] validate() {
        final int numOfFields = 3;

        String[] errors = new String[numOfFields];
        errors[0] = validateName();
        errors[1] = validateStrength();
        errors[2] = validateDoseForm();

        return errors;
    }

    private String validateName() {
        return null;
    }

    private String validateStrength() {
        return null;
    }

    private String validateDoseForm() {
        return null;
    }

}
