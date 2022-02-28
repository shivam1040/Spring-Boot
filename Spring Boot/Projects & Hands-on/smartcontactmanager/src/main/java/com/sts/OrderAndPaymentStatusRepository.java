package com.sts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAndPaymentStatusRepository extends JpaRepository<OrderAndPaymentStatus, Integer> {
	public OrderAndPaymentStatus findByOrderId(String orderId);
}
