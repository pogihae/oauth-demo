package com.ms.oauth.core.domain.customer;

import com.ms.oauth.core.domain.common.validation.ValidEmail;
import com.ms.oauth.core.domain.common.validation.ValidName;
import com.ms.oauth.core.domain.common.validation.ValidPhoneNumber;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Customer Aggregate Root
 * 독립적인 고객 엔티티
 * Account, Client와는 ID를 통한 참조 관계만 유지
 */
@Entity
@Table(name = "customers", indexes = {
    @Index(name = "idx_customer_email", columnList = "email")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @EmbeddedId
    private CustomerId id;

    @ValidName
    @Column(nullable = false, length = 100)
    private String customerName;

    @ValidEmail
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @ValidPhoneNumber
    @Column(length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CustomerStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Customer(CustomerId id, String customerName, String email, String phoneNumber) {
        this.id = id;
        this.customerName = customerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = CustomerStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public static Customer create(String customerName, String email, String phoneNumber) {
        return new Customer(CustomerId.generate(), customerName, email, phoneNumber);
    }

    /**
     * Customer 정보 업데이트
     */
    public void updateCustomerInfo(
        @ValidName String customerName,
        @ValidEmail String email,
        @ValidPhoneNumber String phoneNumber
    ) {
        this.customerName = customerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Customer 활성화
     */
    public void activate() {
        if (this.status == CustomerStatus.ACTIVE) {
            throw new IllegalStateException("Customer is already active");
        }
        this.status = CustomerStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Customer 비활성화
     */
    public void deactivate() {
        if (this.status == CustomerStatus.INACTIVE) {
            throw new IllegalStateException("Customer is already inactive");
        }
        this.status = CustomerStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return this.status == CustomerStatus.ACTIVE;
    }
}
