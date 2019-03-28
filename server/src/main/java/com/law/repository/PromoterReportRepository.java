package com.law.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.law.entity.PromoterReport;

public interface PromoterReportRepository extends JpaRepository<PromoterReport, Integer>, JpaSpecificationExecutor<PromoterReport> {



}
