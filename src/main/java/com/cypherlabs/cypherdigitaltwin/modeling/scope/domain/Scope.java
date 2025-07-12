package com.cypherlabs.cypherdigitaltwin.modeling.scope.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "scopes")
public class Scope {

    @Id
    @Column(length = 26, updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Convert(converter = LocationConverter.class)
    private Location location;

    @ElementCollection
    @CollectionTable(name = "scope_tags", joinColumns = @JoinColumn(name = "scope_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScopeStatus status = ScopeStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_scope_id")
    private Scope parent;

    protected Scope() {
    }

    public Scope(String id, String name, Location location, Set<String> tags, Scope parent) {
        this.id = id;
        this.name = name;
        this.location = location;
        if (tags != null) this.tags = tags;
        this.parent = parent;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public Location getLocation() { return location; }

    public Set<String> getTags() { return tags; }

    public ScopeStatus getStatus() { return status; }

    public Scope getParent() { return parent; }

    public void setStatus(ScopeStatus status) { this.status = status; }
}

