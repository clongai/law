package com.law.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.law.entity.LawFileRecord;
import com.law.repository.LawFileRecordRepository;

@Service
public class LawFileRecordService {

	@Autowired
	private LawFileRecordRepository lawFileRecordRepository;
	
	public LawFileRecord save(LawFileRecord lawFile) {
		return lawFileRecordRepository.save(lawFile);
	}

	public List<LawFileRecord> findAllByOrderId(Integer orderId) {
		
		return lawFileRecordRepository.findAllByOrderId(orderId);
	}

	public LawFileRecord findOneById(Integer fileId) {
		return lawFileRecordRepository.findOneByFileId(fileId);
	}
}
