package com.ms.oauth.core.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.json.JsonMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectMapperUtils {

    public static ObjectMapper INSTANCE;

    public static ObjectMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = JsonMapper.builderWithJackson2Defaults()
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .build();
        }
        return INSTANCE;
    }
}
