package com.ms.oauth.infrastructure.jpa.adapter;

import com.ms.oauth.core.application.port.out.ClientOutPort;
import com.ms.oauth.core.domain.client.Client;
import com.ms.oauth.core.domain.client.ClientStatus;
import com.ms.oauth.infrastructure.jpa.entity.ClientJpaEntity;
import com.ms.oauth.infrastructure.jpa.mapper.ClientMapper;
import com.ms.oauth.infrastructure.jpa.repository.ClientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Client Repository Adapter
 * Domain의 ClientRepository Port를 JPA로 구현하는 Adapter
 */
@Component
@RequiredArgsConstructor
public class ClientOutPortAdapter implements ClientOutPort {

    private final ClientJpaRepository jpaRepository;
    private final ClientMapper mapper;

    @Override
    public Client save(Client client) {
        ClientJpaEntity entity = mapper.toEntity(client);
        ClientJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Client> findById(String clientId) {
        return jpaRepository.findById(clientId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Client> findByClientName(String clientName) {
        return jpaRepository.findByClientName(clientName)
                .map(mapper::toDomain);
    }

    @Override
    public List<Client> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Client> findAllByStatus(ClientStatus status) {
        return jpaRepository.findAllByStatus(status).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByClientName(String clientName) {
        return jpaRepository.existsByClientName(clientName);
    }

    @Override
    public void delete(Client client) {
        ClientJpaEntity entity = mapper.toEntity(client);
        jpaRepository.delete(entity);
    }

    @Override
    public void deleteById(String clientId) {
        jpaRepository.deleteById(clientId);
    }
}
