package com.law.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.law.entity.LawFileRecord;

public interface LawFileRecordRepository extends JpaRepository<LawFileRecord, Integer>, JpaSpecificationExecutor<LawFileRecord> {

	List<LawFileRecord> findAllByOrderId(Integer orderId);

	LawFileRecord findOneByFileId(Integer fileId);

}
