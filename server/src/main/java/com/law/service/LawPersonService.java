package com.law.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.law.model.LawPerson;
import com.law.repository.LawPersonRepository;

@Service	
public class LawPersonService {

	@Autowired
	private LawPersonRepository lawPersonRepository;

	public List<LawPerson> findAllByOrderIdAndPersonTypeOrderByPersonId(Integer orderId, String personType) {
		return lawPersonRepository.findAllByOrderIdAndPersonTypeOrderByPersonId(orderId, personType);
	}
	
	
}
