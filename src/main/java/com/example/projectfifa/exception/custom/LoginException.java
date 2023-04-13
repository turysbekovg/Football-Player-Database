package com.example.projectfifa.exception.custom;

public class LoginException extends RuntimeException{
    public LoginException (String message) {  // it also can be created through 'construct' option in IntelliJ
        super(message);
    }

}
