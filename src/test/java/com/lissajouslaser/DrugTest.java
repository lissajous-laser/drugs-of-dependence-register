package com.lissajouslaser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Drug class.
 */
public class DrugTest {
    Drug drug;

    @Test
    void correctFieldsGiveNoError() {
        drug = new Drug(
            "Jurnista",
            "16mg",
            "SR tablet"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(drug.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(0, numberOfErrors);
    }

    @Test
    void nameEmpty_givesOneError() {
        drug = new Drug(
            "",
            "16mg",
            "SR tablet"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(drug.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);        
    }

    @Test
    void nameWithInvalidChars_givesOneError() {
        drug = new Drug(
            "Jurnist@",
            "16mg",
            "SR tablet"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(drug.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);        
    }

    @Test
    void nameLongerThan16Chars_givesOneError() {
        drug = new Drug(
            "Jurnista or Hydromorphone Hydrochloride",
            "16mg",
            "SR tablet"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(drug.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);        
    }

    @Test
    void strengthEmpty_givesOneError() {
        drug = new Drug(
            "Jurnista",
            "",
            "SR tablet"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(drug.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void strengthWithInvalidChars_givesOneError() {
        drug = new Drug(
            "Jurnista",
            "1600μg",
            "SR tablet"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(drug.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void strengthLongerThan16Chars_givesOneError() {
        drug = new Drug(
            "Jurnista",
            "1600microgrammages",
            "SR tablet"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(drug.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void emptyDoseForm_givesOneError() {
        drug = new Drug(
            "Jurnista",
            "16mg",
            ""
        );
        int numberOfErrors =
                (int) Arrays
                .stream(drug.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void doseFormWithInvalidChars_givesOneError() {
        drug = new Drug(
            "Jurnista",
            "16mg",
            "SR_tablet"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(drug.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void doseFormLongerThan16Chars_givesOneError() {
        drug = new Drug(
            "Jurnista",
            "16mg",
            "sustained release tablet"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(drug.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }
}