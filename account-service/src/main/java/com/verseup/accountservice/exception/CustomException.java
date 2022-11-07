package com.verseup.accountservice.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private String message;
    private Object[] params;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(String message, Object... params) {
        this.message = message;
        this.params = params;
    }
}
