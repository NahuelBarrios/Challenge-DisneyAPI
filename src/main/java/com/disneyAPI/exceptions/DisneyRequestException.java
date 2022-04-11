package com.disneyAPI.exceptions;

import org.springframework.http.HttpStatus;

public class DisneyRequestException extends RuntimeException{
    private final String code;
    private final HttpStatus status;

    public DisneyRequestException(String message, String code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
