package com.web.miaa.exceptions;

public class LicitacionNotFoundException extends RuntimeException {
    public LicitacionNotFoundException(String message) {
        super(message);
    }
}