package com.ms.oauth.infrastructure.jpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA 설정
 * JPA Auditing을 활성화하여 BaseEntity의 createdAt, updatedAt을 자동으로 관리
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
