package com.lissajouslaser;

/**
 * Interface for for classes whose instances share share analagous
 * entries in the agents table of the database.
 */
public interface IAgent {
    public String getName();
    public String getAddress();
}
