package com.example.projectfifa.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class HttpResponseException {  // I don't know what this class for, didn't explain on lecture
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
    private final LocalDateTime timeStamp;
    private final int httpStatusCode;
    private final HttpStatus httpStatus;
    private final String reason;
    private final String message;


    public HttpResponseException(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
        this.timeStamp = LocalDateTime.now();
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
    }
}
