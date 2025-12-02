package com.ms.oauth.infrastructure.jpa.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ms.oauth.core.domain.client.TokenSettings;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

/**
 * TokenSettings를 JSON 문자열로 변환하는 JPA Converter
 * Unknown properties는 무시됩니다.
 */
@Slf4j
@Converter
public class TokenSettingsConverter implements AttributeConverter<TokenSettings, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public String convertToDatabaseColumn(TokenSettings attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert TokenSettings to JSON", e);
            throw new RuntimeException("Failed to convert TokenSettings to JSON", e);
        }
    }

    @Override
    public TokenSettings convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, TokenSettings.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert JSON to TokenSettings", e);
            throw new RuntimeException("Failed to convert JSON to TokenSettings", e);
        }
    }
}
