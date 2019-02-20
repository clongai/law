package com.law.repository;

import com.law.model.LawPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Auther: nonghz
 * @Date: 2018/10/26 09:40
 * @Description:
 */
public interface LawPayRepository extends JpaRepository<LawPay, Integer>, JpaSpecificationExecutor<LawPay> {
    public LawPay findOneByOutTradeNo(String tradeNo);
}
