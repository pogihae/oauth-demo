package com.ms.oauth.infrastructure.jpa.entity;

import com.ms.oauth.core.domain.client.ClientSettings;
import com.ms.oauth.core.domain.client.ClientStatus;
import com.ms.oauth.core.domain.client.GrantType;
import com.ms.oauth.core.domain.client.TokenSettings;
import com.ms.oauth.infrastructure.jpa.converter.ClientSettingsConverter;
import com.ms.oauth.infrastructure.jpa.converter.GrantTypeSetConverter;
import com.ms.oauth.infrastructure.jpa.converter.StringSetConverter;
import com.ms.oauth.infrastructure.jpa.converter.TokenSettingsConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientJpaEntity extends BaseEntity {

    @Id
    @Column(nullable = false, length = 50)
    private String clientId;

    @Column(nullable = false, length = 100)
    private String clientName;

    @Column(nullable = false, length = 255)
    private String clientSecret;

    @Convert(converter = StringSetConverter.class)
    @Column(length = 1000)
    @Builder.Default
    private Set<String> redirectUris = new HashSet<>();

    @Convert(converter = GrantTypeSetConverter.class)
    @Column(length = 1000)
    @Builder.Default
    private Set<GrantType> grantTypes = new HashSet<>();

    @Convert(converter = StringSetConverter.class)
    @Column(length = 1000)
    @Builder.Default
    private Set<String> scopes = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ClientStatus status;

    /**
     * Client 설정 (JSON 저장)
     */
    @Convert(converter = ClientSettingsConverter.class)
    @Column(columnDefinition = "TEXT")
    private ClientSettings clientSettings;

    /**
     * Token 설정 (JSON 저장)
     */
    @Convert(converter = TokenSettingsConverter.class)
    @Column(columnDefinition = "TEXT")
    private TokenSettings tokenSettings;
}
