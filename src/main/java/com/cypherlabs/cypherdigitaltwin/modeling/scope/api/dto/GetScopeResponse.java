package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto;

import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Location;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Scope;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.ScopeStatus;

import java.util.Set;

public record GetScopeResponse(String id, String name, ScopeStatus status, Location location,
                               Set<String> tags, Scope parent)
        implements ScopeResponse {
}
