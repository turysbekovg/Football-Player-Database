package com.example.projectfifa.exception;

import com.example.projectfifa.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice // this annotation means that this class will follow what's happening in our controllers (classes)
public class ExceptionHandling {  // this class is needed to show exception to the client

    @ExceptionHandler(NotFoundException.class) // interceptor for NotFound Except
    // if an error happens in the class that we are watching, and if this error equals to the error in the exception handler
    // then this method happens
    public ResponseEntity<HttpResponseException> NotFoundException (NotFoundException exception){
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(RepositoryCreateException.class) // interceptor for create
    public ResponseEntity<HttpResponseException> RepositoryCreateException (RepositoryCreateException exception){
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(RepositoryUpdateException.class) // interceptor for update
    public ResponseEntity<HttpResponseException> RepositoryUpdateException (RepositoryUpdateException exception){
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(RepositoryDeleteException.class) // interceptor for delete
    public ResponseEntity<HttpResponseException> RepositoryDeleteException (RepositoryDeleteException exception){
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(AlreadyExistException.class) // interceptor for Already exist
    public ResponseEntity<HttpResponseException> AlreadyExistException (AlreadyExistException exception){
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(LoginException.class) // interceptor for Login
    public ResponseEntity<HttpResponseException> LoginException (LoginException exception){
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UnexpectedException.class) // interceptor for Unexpected
    public ResponseEntity<HttpResponseException> UnexpectedException (UnexpectedException exception){
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponseException> MethodArgumentNotValidException (MethodArgumentNotValidException exception){
        if(exception.hasErrors()) {
            return createHttpResponse(BAD_REQUEST, exception.getFieldError().getDefaultMessage());
        }
        return null;
    }

    private ResponseEntity<HttpResponseException> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponseException(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }
}
