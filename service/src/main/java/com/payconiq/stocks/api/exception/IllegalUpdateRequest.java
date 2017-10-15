package com.payconiq.stocks.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalUpdateRequest extends RuntimeException {
    public IllegalUpdateRequest(String s) {
    }
}
