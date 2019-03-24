package com.law.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.law.entity.OrderQuote;

public interface OrderQuoteRepository extends JpaRepository<OrderQuote, Integer>, JpaSpecificationExecutor<OrderQuote> {

	OrderQuote findByOrderIdAndType(Integer orderId, Integer type);

}
