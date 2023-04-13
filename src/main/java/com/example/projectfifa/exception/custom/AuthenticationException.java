package com.example.projectfifa.exception.custom;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException (String message) {  // it also can be created through 'construct' option in IntelliJ
        super(message);
    }
}
