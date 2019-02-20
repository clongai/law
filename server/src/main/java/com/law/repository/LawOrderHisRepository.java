package com.law.repository;

import com.law.model.LawOrderHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: nonghz
 * @Date: 2018/10/29 12:04
 * @Description:
 */
public interface LawOrderHisRepository extends JpaRepository<LawOrderHis, Integer>,JpaSpecificationExecutor<LawOrderHis> {
}
