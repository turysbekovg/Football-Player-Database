package com.example.projectfifa.exception.custom;

public class RepositoryUpdateException extends RuntimeException {
    public RepositoryUpdateException (String message) {  // it also can be created through 'construct' option in IntelliJ
        super(message);
    }

}
