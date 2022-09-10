package com.lissajouslaser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests for Pharmacist class.
 */
public class PharmacistTest {
    Pharmacist pharmacist;


    @Test
    void correctFieldsGivesNoError() {
        pharmacist = new Pharmacist(
            "Ned",
            "Kelly",
            "PHA0001234567"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(pharmacist.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(0, numberOfErrors);
    }

    @Test
    void firstNameEmpty_GivesOneError() {
        pharmacist = new Pharmacist(
            "",
            "Kelly",
            "PHA0001234567"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(pharmacist.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void firstNameWithInvalidChars_GivesOneError() {
        pharmacist = new Pharmacist(
            "N8d",
            "Kelly",
            "PHA0001234567"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(pharmacist.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void firstNameLongerThanMaxChars_GivesOneError() {
        pharmacist = new Pharmacist(
            "Netherlanderinger Bringer Of The Singer",
            "Kelly",
            "PHA0001234567"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(pharmacist.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void secondNameEmpty_GivesOneError() {
        pharmacist = new Pharmacist(
            "Ned",
            "",
            "PHA0001234567"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(pharmacist.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void secondNameWithInvalidChars_GivesOneError() {
        pharmacist = new Pharmacist(
            "Ned",
            "Ke11y",
            "PHA0001234567"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(pharmacist.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void secondNameLongerThanMaxChars_GivesOneError() {
        pharmacist = new Pharmacist(
            "Ned",
            "Kelllllllllllllllllllllllllllllly",
            "PHA0001234567"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(pharmacist.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void pharmacistNumEmpty_GivesOneError() {
        pharmacist = new Pharmacist(
            "Ned",
            "Kelly",
            ""
        );
        int numberOfErrors =
                (int) Arrays
                .stream(pharmacist.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void pharmacistWithInvalidChars_GivesOneError() {
        pharmacist = new Pharmacist(
            "Ned",
            "Kelly",
            "PHARM00000123"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(pharmacist.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void pharmacistNumLongerThan13Chars_GivesOneError() {
        pharmacist = new Pharmacist(
            "Ned",
            "Kelly",
            "PHA00000123456789"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(pharmacist.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

}
