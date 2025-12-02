package com.ms.oauth.infrastructure.jpa.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Set<String>을 콤마로 구분된 문자열로 변환하는 JPA Converter
 * DB에는 "client1,client2,client3" 형태로 저장
 */
@Converter
public class StringSetConverter implements AttributeConverter<Set<String>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(Set<String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return String.join(DELIMITER, attribute);
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return new HashSet<>();
        }
        return Arrays.stream(dbData.split(DELIMITER))
                .map(String::trim)
                .collect(Collectors.toSet());
    }
}
