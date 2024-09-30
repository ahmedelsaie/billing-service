package com.bill.exceptions;

public class TransactionStateException extends RuntimeException {
    public TransactionStateException(String message) {
        super(message);
    }

}
