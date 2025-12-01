package com.ms.oauth.core.application.port.out;

import com.ms.oauth.core.domain.customer.Customer;
import com.ms.oauth.core.domain.customer.CustomerId;
import com.ms.oauth.core.domain.customer.CustomerStatus;

import java.util.List;
import java.util.Optional;

/**
 * Customer Repository Port (Outbound Port)
 * Infrastructure Layer에서 구현됨
 */
public interface CustomerRepository {

    /**
     * Customer 저장
     */
    Customer save(Customer customer);

    /**
     * Customer 조회 by ID
     */
    Optional<Customer> findById(CustomerId customerId);

    /**
     * Customer 조회 by Email
     */
    Optional<Customer> findByEmail(String email);

    /**
     * 모든 Customer 조회
     */
    List<Customer> findAll();

    /**
     * 활성 Customer 조회
     */
    List<Customer> findAllByStatus(CustomerStatus status);

    /**
     * Customer 존재 여부 확인
     */
    boolean existsById(CustomerId customerId);

    /**
     * Email 존재 여부 확인
     */
    boolean existsByEmail(String email);

    /**
     * Customer 삭제
     */
    void delete(Customer customer);

    /**
     * Customer 삭제 by ID
     */
    void deleteById(CustomerId customerId);
}
