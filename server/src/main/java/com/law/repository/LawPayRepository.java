package com.law.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.law.model.KeyValue;
import com.law.model.LawPay;

/**
 * @Auther: nonghz
 * @Date: 2018/10/26 09:40
 * @Description:
 */
public interface LawPayRepository extends JpaRepository<LawPay, Integer>, JpaSpecificationExecutor<LawPay> {
    public LawPay findOneByOutTradeNo(String tradeNo);
    
    @Query(value = "select sum(fee) from law_pay where trade_state = ? ", nativeQuery = true)
    BigDecimal sumFeePayOrder(Integer tradeState);
    
    @Query(value = "select count(distinct(order_id)) from law_pay where trade_state = ? ", nativeQuery = true)
    Long countPayOrder(Integer tradeState);
    
    @Query(value = "select sum(fee) from law_pay where trade_state = ?1 and done_date >= ?2 ", nativeQuery = true)
    BigDecimal sumFeePayOrderByMonth(Integer tradeState,Date doenDate);
    
    @Query(value = "select count(distinct(order_id)) from law_pay where trade_state = ?1 and done_date >= ?2 ", nativeQuery = true)
    Long countPayOrderByMonth(Integer tradeState,Date doenDate);
    
    
  /*  @Query(value = " SELECT DATE_FORMAT(done_date,'%Y-%m') as doneDate,sum(fee) fee FROM law_pay where trade_state = ?1 and done_date >= ?2   GROUP BY  doneDate ", nativeQuery = true)
    List<LawPay> sumFeePayOrderByMonthGrp(Integer tradeState,Date doenDate);*/
}
