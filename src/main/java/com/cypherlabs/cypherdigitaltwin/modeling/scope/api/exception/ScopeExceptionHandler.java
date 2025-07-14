package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.exception;

import com.cypherlabs.cypherdigitaltwin.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.cypherlabs.cypherdigitaltwin.modeling.scope.api")
public class ScopeExceptionHandler {

    @ExceptionHandler(ScopeNotFoundException.class)
    public ResponseEntity<ErrorResponse> scopeNotFound(ScopeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), "SCOPE_NOT_FOUND"));
    }

    @ExceptionHandler(ScopeWithSameNameExistsException.class)
    public ResponseEntity<ErrorResponse> scopeWithSameNameExists(ScopeWithSameNameExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), "SCOPE_WITH_SAME_NAME_EXISTS"));
    }
}
