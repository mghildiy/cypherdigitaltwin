package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto;

import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.ScopeStatus;

public record CreateScopeResponse(String id, ScopeStatus status) {
}
