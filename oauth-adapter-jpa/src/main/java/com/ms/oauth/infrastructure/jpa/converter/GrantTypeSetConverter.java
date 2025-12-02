package com.ms.oauth.infrastructure.jpa.converter;

import com.ms.oauth.core.domain.client.GrantType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Set<GrantType>을 콤마로 구분된 문자열로 변환하는 JPA Converter
 * DB에는 "AUTHORIZATION_CODE,REFRESH_TOKEN" 형태로 저장
 */
@Converter
public class GrantTypeSetConverter implements AttributeConverter<Set<GrantType>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(Set<GrantType> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.stream()
                .map(GrantType::name)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public Set<GrantType> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return new HashSet<>();
        }
        return Arrays.stream(dbData.split(DELIMITER))
                .map(String::trim)
                .map(GrantType::valueOf)
                .collect(Collectors.toSet());
    }
}
