package com.lissajouslaser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests for Supplier class.
 */
public class SupplierTest {
    Supplier supplier;

    @Test
    void correctFieldsGivesNoError() {
        supplier = new Supplier(
            "Australian Pharmacuetical Industries",
            "250 Camberwell Rd, Camberwell VIC 3124"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(supplier.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(0, numberOfErrors);
    }

    @Test
    void nameEmpty_givesOneError() {
        supplier = new Supplier(
            "",
            "250 Camberwell Rd, Camberwell VIC 3124"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(supplier.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);        
    }

    @Test
    void nameLongerThanMaxChars_givesOneError() {
        supplier = new Supplier(
            "Australian Pharmacuetical Industries Limited But Not That Limited",
            "250 Camberwell Rd, Camberwell VIC 3124"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(supplier.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);
    }

    @Test
    void addressEmpty_givesOneError() {
        supplier = new Supplier(
            "Australian Pharmacuetical Industries Limited",
            ""
        );
        int numberOfErrors =
                (int) Arrays
                .stream(supplier.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);        
    }

    @Test
    void addressWithInvalidChars_givesOneError() {
        supplier = new Supplier(
            "Australian Pharmacuetical Industries Limited",
            "250 Camberwell Stra$$e, Camberwell VIC 3124"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(supplier.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);        
    }

    @Test
    void addressLongerThanMaxChars_givesOneError() {
        supplier = new Supplier(
            "Australian Pharmacuetical Industries Limited",
            "Unit 100/250 Camberwell-Jamberwell Street, Camberwell Victoria 3124"
        );
        int numberOfErrors =
                (int) Arrays
                .stream(supplier.validate())
                .filter(x -> x != null && !x.isEmpty())
                .count();
        assertEquals(1, numberOfErrors);        
    }
}
