package com.playbook.java8;

import java.time.LocalDate;

/**
 * A bank / card transaction. Amounts are in minor units (paise) as a long — never double for money.
 *
 * @param type "CREDIT" or "DEBIT"
 */
public record Transaction(long id, String account, String type, long amountPaise, String merchant, LocalDate date) {

    public boolean isDebit() {
        return "DEBIT".equals(type);
    }

    /** Signed amount: credits add to the balance, debits subtract. */
    public long signedPaise() {
        return isDebit() ? -amountPaise : amountPaise;
    }
}
