package com.company.storeExceptions;

public class CashRegistryException extends Exception {
    public static String message() {
        return "Cash Registry is occupied by someone else!";
    }
}