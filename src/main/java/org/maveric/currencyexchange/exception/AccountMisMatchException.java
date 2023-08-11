package org.maveric.currencyexchange.exception;

public class AccountMisMatchException extends RuntimeException {

    public AccountMisMatchException(String message) {
        super(message);
    }
}
