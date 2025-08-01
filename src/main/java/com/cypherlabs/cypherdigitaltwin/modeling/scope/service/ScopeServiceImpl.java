package com.cypherlabs.cypherdigitaltwin.modeling.scope.service;

import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.exception.ScopeWithSameNameExistsException;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Location;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Scope;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.api.exception.ScopeNotFoundException;
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
    public Scope create(String name, Location location, Set<String> tags, String parentId) {
        Scope parent  = null;
        if(!StringUtils.isBlank(parentId)) {
            parent = this.repository
                    .findById(parentId)
                    .orElseThrow(() -> new ScopeNotFoundException(String.format("Parent scope '%s' not found", parentId)));
        }

        if(this.repository.existsByName(name)) {
            throw new ScopeWithSameNameExistsException(String.format("Scope with name '%s' already exists", name));
        }

        Scope scope = new Scope(ulid.nextULID(), name, location, tags, parent);


        return this.repository.save(scope);
    }

    @Override
    public Optional<Scope> get(String id) {
        return this.repository.findById(id);
    }

    @Override
    public List<Scope> getAll() {
        return this.repository.findAll();
    }
}
