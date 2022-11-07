package com.verseup.accountservice.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {

    private String code;
    private String defaultMessage;

    public static ResponseMessage ok() {
        return new ResponseMessage("succeed", "Success");
    }

    public static ResponseMessage bad() {
        return new ResponseMessage("bad.request", "Bad Request");
    }

    public static ResponseMessage error() {
        return new ResponseMessage("error", "Error");
    }

    public static ResponseMessage ex() {
        return new ResponseMessage("exception", "Exception occurred");
    }

    public static ResponseMessage resourceNotFound() {
        return new ResponseMessage("resource.not.found", "Resource not found");
    }
}

