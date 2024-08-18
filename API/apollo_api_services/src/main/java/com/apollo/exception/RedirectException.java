package com.apollo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedirectException extends RuntimeException{
    private String redirectUrl;

    public RedirectException(String message, String rUrl) {
        super(message);
        this.redirectUrl = rUrl;
    }
}
