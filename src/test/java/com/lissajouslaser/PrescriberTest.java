package com.lissajouslaser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Prescriber class.
 */
class PrescriberTest {
    private Prescriber prescriber;

    @Test
    void correctFieldsGiveNoError() {
        prescriber = new Prescriber(
            "Banjo",
            "Patterson",
            "1234567"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(prescriber.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(0, numberOfErrors);
    }

    @Test
    void firstNameEmptyGivesOneError() {
        prescriber = new Prescriber(
            "",
            "Patterson",
            "1234567"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(prescriber.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void firstNameWithInvalidChars_GivesOneError() {
        prescriber = new Prescriber(
            "B@njo",
            "Patterson",
            "1234567"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(prescriber.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void firstNameLongerThan16Chars_GivesOneError() {
        prescriber = new Prescriber(
            "Banjo-Kazooie-the-Game",
            "Patterson",
            "1234567"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(prescriber.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void secondNameEmpty_GivesOneError() {
        prescriber = new Prescriber(
            "Banjo",
            "",
            "1234567"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(prescriber.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void secondNameWithInvalidChars_GivesOneError() {
        prescriber = new Prescriber(
            "Banjo",
            "Patter$on",
            "1234567"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(prescriber.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void secondNameLongerThan32Chars_GivesOneError() {
        prescriber = new Prescriber(
            "Banjo",
            "Pattersonofschattersonofgaffersonoffatter",
            "1234567"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(prescriber.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void prescriberNumEmpty_GivesOneError() {
        prescriber = new Prescriber(
            "Banjo",
            "Patterson",
            ""
        );
        int numberOfErrors =
                (int) Arrays
                .stream(prescriber.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void prescriberWithInvalidChars_GivesOneError() {
        prescriber = new Prescriber(
            "Banjo",
            "Patterson",
            "8eight8"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(prescriber.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void prescriberNumLongerThan7Chars_GivesOneError() {
        prescriber = new Prescriber(
            "Banjo",
            "Patterson",
            "45678901"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(prescriber.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }
}
