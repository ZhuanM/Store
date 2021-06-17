package com.company.storeExceptions;

public class InsufficientGoodsException extends Exception {
    public InsufficientGoodsException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "InsufficientGoodsException [" + super.getMessage() + "]";
    }
}
