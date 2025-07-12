package com.cypherlabs.cypherdigitaltwin.modeling.scope.service;

import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Location;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Scope;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.repository.ScopeRepository;
import de.huxhorn.sulky.ulid.ULID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ScopeServiceImpl implements  ScopeService {
     private final ScopeRepository repository;
     private final ULID ulid;

    @Autowired
    public ScopeServiceImpl(ScopeRepository repository, ULID ulid) {
        this.repository = repository;
        this.ulid = ulid;
    }

    @Override
    @Transactional
    public void create(String name, Location location, Set<String> tags, String parentId) {
        Scope parent  = null;
        if(!StringUtils.isBlank(parentId)) {
            parent = this.repository
                    .findById(parentId)
                    .orElseThrow(() -> new IllegalStateException("Parent scope not found:"+ parentId));
        }
        Scope scope = new Scope(ulid.nextULID(), name, location, tags, parent);
        this.repository.save(scope);
    }

    @Override
    public Optional<Scope> get(String id) {
        return Optional.empty();
    }

    @Override
    public List<Scope> getAll() {
        return List.of();
    }
}
