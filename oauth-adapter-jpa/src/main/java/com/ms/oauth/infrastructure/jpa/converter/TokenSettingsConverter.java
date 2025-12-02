package com.ms.oauth.infrastructure.jpa.converter;

import com.ms.oauth.core.common.util.ObjectMapperUtils;
import com.ms.oauth.core.domain.client.TokenSettings;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * TokenSettings를 JSON 문자열로 변환하는 JPA Converter
 * Spring Boot의 Jackson 설정을 따릅니다.
 */
@Slf4j
@Converter
public class TokenSettingsConverter implements AttributeConverter<TokenSettings, String> {

    @Override
    public String convertToDatabaseColumn(TokenSettings attribute) {
        if (attribute == null) {
            return null;
        }

        return ObjectMapperUtils.getInstance().writeValueAsString(attribute);
    }

    @Override
    public TokenSettings convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }

        return ObjectMapperUtils.getInstance().readValue(dbData, TokenSettings.class);
    }
}
