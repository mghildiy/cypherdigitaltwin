package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto;

public sealed interface ScopeResponse permits CreateScopeResponse, GetScopeResponse, ScopeNotFoundResponse {
}
