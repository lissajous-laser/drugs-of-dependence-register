package com.lissajouslaser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class PatientTest {
    Patient patient;
    
    @Test
    void correctFieldsGivesNoError() {
        patient = new Patient(
                "Ansel",
                "Adams",
                "23 Yosemite Drive, Upwey"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(patient.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();

        assertEquals(0, numberOfErrors);        
    }

    @Test
    void firstNameEmpty_givesOneError() {
        patient = new Patient(
                "",
                "Adams",
                "23 Yosemite Drive, Upwey"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(patient.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();

        assertEquals(1, numberOfErrors);           
    }

    @Test
    void firstNameWithInvalidChars_givesOneError() {
        patient = new Patient(
                "A|\\|sell",
                "Adams",
                "23 Yosemite Drive, Upwey"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(patient.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();

        assertEquals(1, numberOfErrors);           
    }

    @Test
    void firstNameLongerThanMaxChars_givesOneError() {
        patient = new Patient(
                "Some Guy With A Big Ass Camera Rolling Around In The Woods",
                "Adams",
                "23 Yosemite Drive, Upwey"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(patient.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();

        assertEquals(1, numberOfErrors);           
    }

    @Test
    void addressEmpty_givesOneError() {
        patient = new Patient(
                "Ansel",
                "Adams",
                ""
        );
        int numberOfErrors =
                (int) Arrays
                .stream(patient.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();

        assertEquals(1, numberOfErrors);         
    }

    @Test
    void addressWithInvalidChars_givesOneError() {
        patient = new Patient(
                "Ansel",
                "Adams",
                "23 Yosemite Drive; Upwey"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(patient.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();

        assertEquals(1, numberOfErrors);         
    }

    @Test
    void addressLongerThanMaxChars_givesOneError() {
        patient = new Patient(
                "Ansel",
                "Adams",
                "Lodgepole Pines, Lyell Fork of the Merced River,"
                + " Yosemite National Park"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(patient.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();

        assertEquals(1, numberOfErrors);         
    }

}
