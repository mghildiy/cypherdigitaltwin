package com.cypherlabs.cypherdigitaltwin.modeling.scope.domain;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

public record Location(
        @DecimalMin(value = "-90.0")
        @DecimalMax(value = "90.0")
        Double latitude,
        @DecimalMin(value = "-180.0")
        @DecimalMax(value = "180.0")
        Double longitude) {}
