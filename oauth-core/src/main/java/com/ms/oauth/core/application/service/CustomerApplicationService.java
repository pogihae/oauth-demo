package com.ms.oauth.core.application.service;

import com.ms.oauth.core.application.port.in.customer.RegisterCustomerCommand;
import com.ms.oauth.core.application.port.in.customer.RegisterCustomerUseCase;
import com.ms.oauth.core.domain.customer.Customer;
import com.ms.oauth.core.domain.customer.CustomerId;
import com.ms.oauth.core.application.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Customer Application Service
 * Use Case를 구현하고 비즈니스 흐름을 조율
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerApplicationService implements RegisterCustomerUseCase {

    private final CustomerRepository customerRepository;

    /**
     * Customer 등록
     */
    @Override
    @Transactional
    public Customer register(RegisterCustomerCommand command) {
        command.validate();

        // 이메일 중복 확인
        if (customerRepository.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + command.getEmail());
        }

        // Customer 생성
        Customer customer = Customer.create(
            command.getCustomerName(),
            command.getEmail(),
            command.getPhoneNumber()
        );

        // 저장
        return customerRepository.save(customer);
    }

    /**
     * Customer 조회
     */
    public Customer getCustomer(String customerId) {
        return customerRepository.findById(CustomerId.of(customerId))
            .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));
    }

    /**
     * Customer 정보 업데이트
     */
    @Transactional
    public Customer updateCustomer(String customerId, String customerName, String email, String phoneNumber) {
        Customer customer = getCustomer(customerId);

        // 이메일 변경 시 중복 확인
        if (!customer.getEmail().equals(email) && customerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }

        customer.updateCustomerInfo(customerName, email, phoneNumber);
        return customerRepository.save(customer);
    }

    /**
     * Customer 활성화
     */
    @Transactional
    public Customer activateCustomer(String customerId) {
        Customer customer = getCustomer(customerId);
        customer.activate();
        return customerRepository.save(customer);
    }

    /**
     * Customer 비활성화
     */
    @Transactional
    public Customer deactivateCustomer(String customerId) {
        Customer customer = getCustomer(customerId);
        customer.deactivate();
        return customerRepository.save(customer);
    }
}
