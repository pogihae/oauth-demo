package com.ms.oauth.infrastructure.jpa.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ms.oauth.core.domain.client.ClientSettings;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

/**
 * ClientSettings를 JSON 문자열로 변환하는 JPA Converter
 * Unknown properties는 무시됩니다.
 */
@Slf4j
@Converter
public class ClientSettingsConverter implements AttributeConverter<ClientSettings, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public String convertToDatabaseColumn(ClientSettings attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert ClientSettings to JSON", e);
            throw new RuntimeException("Failed to convert ClientSettings to JSON", e);
        }
    }

    @Override
    public ClientSettings convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, ClientSettings.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert JSON to ClientSettings", e);
            throw new RuntimeException("Failed to convert JSON to ClientSettings", e);
        }
    }
}
