package com.lissajouslaser;

import java.time.LocalDateTime;

/**
 * Defines a record of transfer of a drug to or from the
 * pharmacy.
 */
public class Transfer {
    private LocalDateTime transferDate;
    private int agentId;
    private int drugId;
    private int qtyIn;
    private int qtyOut;
    private int balance;
    private int prescriberId;
    private int pharmacistId;
    private String reference;
    private String notes;


}
