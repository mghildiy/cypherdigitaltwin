package com.cypherlabs.cypherdigitaltwin.modeling.scope.api.dto;

import com.cypherlabs.cypherdigitaltwin.modeling.scope.domain.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.Set;

public record CreateScopeRequest(
        @NotBlank
        @Size(max = 100)
        String name,
        @Valid
        Location location,
        Set<@NotBlank String> tags,
        String parentId) {}
