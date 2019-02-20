package com.law.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.law.entity.LawOrderExt;

public interface LawOrderExtRepository extends JpaRepository<LawOrderExt, Integer>, JpaSpecificationExecutor<LawOrderExt> {

	LawOrderExt findByOrderId(Integer orderId);

}
