package com.example.projectfifa.exception.custom;

public class RepositoryCreateException extends RuntimeException {

    public RepositoryCreateException (String message) {  // it also can be created through 'construct' option in IntelliJ
        super(message);
    }
}
