package com.example.projectfifa.exception.custom;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {  // it also can be created through 'construct' option in IntelliJ
        super(message);
    }
}
