package com.law.repository;

import com.law.model.LawOrdSerRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: nonghz
 * @Date: 2018/11/6 10:54
 * @Description:
 */
public interface LawOrdSerRelRepository extends JpaRepository<LawOrdSerRel, Integer>, JpaSpecificationExecutor<LawOrdSerRel> {
}
