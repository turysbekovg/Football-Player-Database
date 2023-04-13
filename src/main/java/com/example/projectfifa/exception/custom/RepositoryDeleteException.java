package com.example.projectfifa.exception.custom;

public class RepositoryDeleteException extends RuntimeException{
    public RepositoryDeleteException (String message) {  // it also can be created through 'construct' option in IntelliJ
        super(message);
    }
}
