package com.law.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.law.entity.WxMessage;
import com.law.repository.WxMessageRepository;

@Service
public class WxMessageService {

	@Autowired
	WxMessageRepository wxMessageRepository;

	public WxMessage save(WxMessage chat) {
		return wxMessageRepository.save(chat);
	}
	
	public List<WxMessage> findAll() {
		return wxMessageRepository.findAll();
	}
}
