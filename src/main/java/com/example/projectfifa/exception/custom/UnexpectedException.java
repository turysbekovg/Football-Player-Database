package com.example.projectfifa.exception.custom;

public class UnexpectedException extends RuntimeException{
    public UnexpectedException (String message) {  // it also can be created through 'construct' option in IntelliJ
        super(message);
    }
}
