package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.exception;

public class ScopeWithSameNameExistsException extends RuntimeException {

    public ScopeWithSameNameExistsException(String message) {
        super(message);
    }
}
