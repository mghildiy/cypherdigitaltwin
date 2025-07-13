package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto;

import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Location;

import java.util.Set;

public record CreateScopeRequest(String name, Location location, Set<String> tags, String parentId) {
}
