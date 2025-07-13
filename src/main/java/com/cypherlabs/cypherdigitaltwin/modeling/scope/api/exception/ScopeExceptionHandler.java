package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.exception;

import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.cypherlabs.cypherdigitaltwin.modeling.scope.api")
public class ScopeExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> scopeNotFound(ScopeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), "SCOPE_NOT_FOUND"));
    }
}
