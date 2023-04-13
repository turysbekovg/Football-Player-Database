package com.example.projectfifa.exception.custom;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException (String message) {  // it also can be created through 'construct' option in IntelliJ
        super(message);
    }
}
