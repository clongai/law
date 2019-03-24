package com.law.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.law.entity.OrderQuote;
import com.law.repository.OrderQuoteRepository;

@Service
public class OrderQuoteService {

	@Autowired
	private OrderQuoteRepository orderQuoteRepository;

	public OrderQuote save(OrderQuote oq) {
		return orderQuoteRepository.save(oq);
	}
	
	public OrderQuote findByOrderIdAndType(Integer orderId,Integer type) {
		return orderQuoteRepository.findByOrderIdAndType(orderId,type);
	}

}
