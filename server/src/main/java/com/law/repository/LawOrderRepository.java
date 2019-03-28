package com.law.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.law.model.LawOrder;

public interface LawOrderRepository extends JpaRepository<LawOrder,Integer>,JpaSpecificationExecutor<LawOrder> {

  public LawOrder findOneByOrderId(int orderId);

  public List<LawOrder> findAllByOpenIdOrderByOrderIdDesc(String openId);

  public Integer countByStatus(String status);

  @Query(value = "select sum(involving_money) from law_order where status = 5 and open_id = ?1 and to_char(done_date,'yyyyMM') = ?2", nativeQuery = true)
  BigDecimal countInvolvingMoneyByOpenId(String openId, String mon);
  
  @Query(value = "select sum(involving_money) from law_order ", nativeQuery = true)
  BigDecimal countInvolvingMoney();
}
