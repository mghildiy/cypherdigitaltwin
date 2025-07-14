package com.cypherlabs.cypherdigitaltwin.common;

import java.util.List;

public record ErrorResponse(List<String> messages, String errorCode) {
    public ErrorResponse(String message, String errorCode) {
        this(List.of(message), errorCode);
    }
}
