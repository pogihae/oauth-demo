package com.ms.oauth.infrastructure.redis.repository;

import com.ms.oauth.infrastructure.redis.entity.ClientRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Client Redis Repository Interface
 * Spring Data Redis를 사용한 Redis 접근
 */
@Repository
public interface ClientRedisRepository extends CrudRepository<ClientRedisEntity, String> {
}
