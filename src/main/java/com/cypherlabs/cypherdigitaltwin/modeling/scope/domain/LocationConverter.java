package com.cypherlabs.cypherdigitaltwin.modeling.scope.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

@Converter(autoApply = true)
public class LocationConverter implements AttributeConverter<Location, String> {
    @Override
    public String convertToDatabaseColumn(Location location) {
        return location == null ? null : location.latitude() + "," + location.longitude();
    }

    @Override
    public Location convertToEntityAttribute(String dbValue) {
        if(StringUtils.isBlank(dbValue)) {
            return null;
        } else {
            String[] parts = dbValue.split(",");
            return new Location(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
        }
    }
}
