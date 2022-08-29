package com.lissajouslaser;

/**
 * Defines a supplier.
 */
public class Supplier {
    private String name;
    private String address;

    public Supplier(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
