package com.cypherlabs.cypherdigitaltwin.modeling.scope.service;

import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Location;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Scope;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ScopeService {

    void create(String name, Location location, Set<String> tags, String parent);
    Optional<Scope> get(String id);
    List<Scope> getAll();
}
