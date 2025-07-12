package com.cypherlabs.cypherdigitaltwin.modeling.scope.repository;

import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScopeRepository extends JpaRepository<Scope, String> {
}
