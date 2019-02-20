package com.law.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.law.model.LawServiceEntity;
import com.law.repository.LawServiceRepository;

@Service
public class LawServiceService {

	@Autowired
	private LawServiceRepository lawServiceRepository;
	
	public List<LawServiceEntity> findAllByServiceLevelInAndStatus(List<Integer> serviceLevel,String status) {
		return lawServiceRepository.findAllByServiceLevelInAndStatus(serviceLevel,status);
	}
	
}
