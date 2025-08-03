package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.slice;

import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Location;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Scope;
import com.cypherlabs.cypherdigitaltwin.modeling.scope.repository.ScopeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ScopeRepositoryTest extends BaseSliceTest{

    @Autowired
    ScopeRepository repository;

    @Test
    public void shouldSaveAndFindScope() {
        Scope mockScope = new Scope("123", "Scope A",
                new Location(18.0, 20.0)
                , Set.of("fake", "dummy"), null);

        Scope saved = repository.save(mockScope);

        assertNotNull(saved.getId());

        Optional<Scope> mayBeFound = repository.findById(saved.getId());
        assertTrue(mayBeFound.isPresent());
        assertTrue(mayBeFound.get().getId().equals(saved.getId()));
    }

    @Test
    public void shouldCheckByName() {
        Scope mockScope = new Scope("123", "Scope A",
                new Location(18.0, 20.0)
                , Set.of("fake", "dummy"), null);

        repository.save(mockScope);

        assertTrue(repository.existsByName("Scope A"));
        assertFalse(repository.existsByName("Scope B"));
    }
}
