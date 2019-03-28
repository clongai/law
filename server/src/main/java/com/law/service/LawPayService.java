package com.law.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.law.common.Constant;
import com.law.model.KeyValue;
import com.law.repository.LawPayRepository;

@Service
public class LawPayService {

	@Autowired
	private LawPayRepository lawPayRepository;
	
	@Autowired
	private EntityManager entityManager;

	public BigDecimal sumFeePayOrder() {
		return lawPayRepository.sumFeePayOrder(Constant.PAY_OK);
	}

	public BigDecimal sumFeePayOrderByMonth() {
		DateTime dateTime = new DateTime();
		DateTime currentMonthOneDay = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), 1, 0, 0);
		return lawPayRepository.sumFeePayOrderByMonth(Constant.PAY_OK, new Date(currentMonthOneDay.getMillis()));
	}
	public Long countPayOrder() {
		return lawPayRepository.countPayOrder(Constant.PAY_OK);
	}
	
	public Long countPayOrderByMonth() {
		DateTime dateTime = new DateTime();
		DateTime currentMonthOneDay = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), 1, 0, 0);
		return lawPayRepository.countPayOrderByMonth(Constant.PAY_OK, new Date(currentMonthOneDay.getMillis()));
	}
	public List<KeyValue> sumFeePayOrderByMonthGrp() {
		DateTime dateTime = new DateTime();
		DateTime currentMonthOneDay = new DateTime(dateTime.getYear(), 1, 1, 0, 0);
		String sql = " SELECT DATE_FORMAT(done_date,'%m') as donetiem,sum(fee) money FROM law_pay where trade_state = ?1 and done_date >= ?2   GROUP BY  donetiem ";
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter(1, Constant.PAY_OK);
		query.setParameter(2, new Date(currentMonthOneDay.getMillis()));
		
		@SuppressWarnings("unchecked")
		List<KeyValue> list = query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(KeyValue.class)).list();
		return list;
	}
	public List<KeyValue> countPayOrderByMonthGrp() {
		DateTime dateTime = new DateTime();
		DateTime currentMonthOneDay = new DateTime(dateTime.getYear(), 1, 1, 0, 0);
		String sql = " SELECT DATE_FORMAT(done_date,'%m') as donetiem, count(distinct(order_id)) orderNum FROM law_pay where trade_state = ?1 and done_date >= ?2   GROUP BY  donetiem ";
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter(1, Constant.PAY_OK);
		query.setParameter(2, new Date(currentMonthOneDay.getMillis()));
		
		@SuppressWarnings("unchecked")
		List<KeyValue> list = query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(KeyValue.class)).list();
		return list;
	}

}
