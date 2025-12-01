package com.ms.oauth.core.application.port.in.customer;

import com.ms.oauth.core.domain.customer.Customer;

/**
 * Customer 등록 Use Case (Inbound Port)
 */
public interface RegisterCustomerUseCase {
    Customer register(RegisterCustomerCommand command);
}
