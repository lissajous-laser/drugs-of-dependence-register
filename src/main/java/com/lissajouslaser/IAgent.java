package com.lissajouslaser;

/**
 * Interface for for classes whose instances share share analagous
 * entries in the agents table of the database.
 */
public interface IAgent {
    static final int MAX_ADDRESS_LENGTH = 64;
    public String getName();

    public String getAddress();

    public int getId();

    /**
     * Validates partial input of address, returns a String with a
     * description of the first reason why address is invalid.
     * Otherwise returns null.
     **/
    static String validateAddress(String address) {
        if (address.length() > MAX_ADDRESS_LENGTH) {
            return "Must be " + MAX_ADDRESS_LENGTH
                    + " characters or less";
        }
        if (!address.isEmpty()
                && !address.matches("[A-Z0-9\\-/ ,]+")) {
            return "Must be letters, numbers, spaces, or: /, -";
        }
        return null;
    }
}
