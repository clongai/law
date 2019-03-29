package com.law.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.law.common.Constant;
import com.law.entity.PromoterReport;
import com.law.model.LawPromoter;
import com.law.model.PromoterParam;
import com.law.model.PublicityLaw;
import com.law.repository.PromoterReportRepository;
import com.law.util.ListUtil;

@Service
public class PromoterReportService {

	@Autowired
	LawPromoterService lawPromoterService;
	@Autowired
	PromoterReportRepository promoterReportRepository;
	@Autowired
	EntityManager entityManager;

	public void updateReportData() {
		int page = 0;
		int pageSize = 20;
		int total = 1;
		while (page < total) {
			Page<LawPromoter> pageResult = lawPromoterService.findAll(page++, pageSize);
			List<PromoterReport> list = new ArrayList<>();
			if (pageResult != null) {
				List<LawPromoter> content = pageResult.getContent();
				total = pageResult.getTotalPages();
				if (ListUtil.isNotBlank(content)) {
					content.stream().forEach(t -> {
						PromoterReport pr = new PromoterReport();
						pr.setCreateTime(t.getCreateTime());
						pr.setPromoterId(t.getPromoterId());
						pr.setPromoterName(t.getPromoterName());
						pr.setPromoterTel(t.getPromoterTel());
						pr.setState(t.getState());
						pr.setParentPromoterId(t.getParentPromoterId());
						setIncomeAndOrderNum(pr);
						list.add(pr);
					});
				}
			}
			if (ListUtil.isNotBlank(list)) {
				setParentName(list);
				promoterReportRepository.saveAll(list);
			} else {
				break;
			}
		}
	}

	private void setParentName(List<PromoterReport> list) {
		if (ListUtil.isNotBlank(list)) {
			List<Integer> ids = list.stream().map(PromoterReport::getParentPromoterId).collect(Collectors.toList());
			List<LawPromoter> romoterList = lawPromoterService.findAllByPromoterIdIn(ids);
			if (ListUtil.isNotBlank(romoterList)) {
				Map<Integer, List<LawPromoter>> map = romoterList.stream().collect(Collectors.groupingBy(LawPromoter::getPromoterId));
				list.stream().forEach(t -> {
					List<LawPromoter> lpList = map.get(t.getParentPromoterId());
					if (ListUtil.isNotBlank(lpList)) {
						t.setSuperiorName(lpList.get(0).getPromoterName());
					}
				});
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void setIncomeAndOrderNum(PromoterReport promoter) {
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append(" SELECT COUNT(DISTINCT(d.order_id)) orderNum,SUM(pay.`fee`) income FROM law_order d LEFT OUTER JOIN law_pay pay ON d.`order_id` = pay.`order_id` WHERE d.`open_id` IN ( ");
		List<String> openIdS = getOpenIdS(promoter);
		if (ListUtil.isNotBlank(openIdS)) {
			List<String> param = new ArrayList<>();
			openIdS.stream().forEach(t -> {
				param.add("'" + t + "'");
			});
			String inVal = param.stream().collect(Collectors.joining(","));
			sqlSb.append(inVal);
			promoter.setSubordinateNum(openIdS.size() - 1);//不包含自己，所以-1
		}
		sqlSb.append("   ) ");

		Query query = entityManager.createNativeQuery(sqlSb.toString());

		List<PromoterReport> list = query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(PromoterReport.class)).list();
		if (ListUtil.isNotBlank(list)) {
			PromoterReport promoterReport = list.get(0);
			if (promoterReport != null) {
				promoter.setIncome(promoterReport.getIncome());
				promoter.setOrderNum(promoterReport.getOrderNum());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getOpenIdS(PromoterReport promoter) {
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append(" SELECT DISTINCT(open_id) openId FROM law_promoter p WHERE FIND_IN_SET(p.`promoter_id`,( ");
		sqlSb.append(" SELECT SUBSTRING((SELECT getChildList(?)),3))) >0  ");

		Query query = entityManager.createNativeQuery(sqlSb.toString());
		query.setParameter(1, promoter.getPromoterId());

		List<OpenId> list = query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(OpenId.class)).list();
		if (ListUtil.isNotBlank(list)) {
			List<String> collect = list.stream().map(OpenId::getOpenId).collect(Collectors.toList());
			return collect;
		}
		return null;
	}

	public static class OpenId {
		private String openId;

		public String getOpenId() {
			return openId;
		}

		public void setOpenId(String openId) {
			this.openId = openId;
		}
	}

	@SuppressWarnings("serial")
	public Page<PromoterReport> findAll(Integer page, Integer pageSize, PromoterParam param) {
		return promoterReportRepository.findAll(new Specification<PromoterReport>() {

			@Override
			public Predicate toPredicate(Root<PromoterReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (param != null) {
					if (StringUtils.isNotBlank(param.getName())) {
						list.add(cb.like(root.get(PromoterReport.NAME).as(String.class), "%" + param.getName() + "%"));
					}
					if (StringUtils.isNotBlank(param.getSuperiorName())) {
						list.add(cb.like(root.get(PromoterReport.SUPERIOR_NAME).as(String.class), "%" + param.getSuperiorName() + "%"));
					}
					if (param.getStartTime() != null) {
						list.add(cb.greaterThanOrEqualTo(root.get(PromoterReport.CREATE_DATE).as(Date.class), param.getStartTime()));
					}
					if (param.getEndTime() != null) {
						list.add(cb.lessThanOrEqualTo(root.get(PromoterReport.CREATE_DATE).as(Date.class), param.getEndTime()));
					}
				}
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		}, PageRequest.of(page, pageSize, Sort.by(Direction.DESC, PromoterReport.INCOME)));
	}

	public Page<PublicityLaw> getPublicityLawByPage(int page, int pageSize, PromoterParam param) {

		Page<PromoterReport> promoterReportPage = findAll(page, pageSize, param);
		List<PublicityLaw> data = new ArrayList<>();
		if (promoterReportPage != null) {
			List<PromoterReport> content = promoterReportPage.getContent();
			if (ListUtil.isNotBlank(content)) {
				AtomicInteger index = new AtomicInteger(1);
				content.stream().forEach(t -> {
					PublicityLaw pl = new PublicityLaw();
					pl.setRank(page * pageSize + index.getAndIncrement());
					pl.setName(t.getPromoterName());
					pl.setPhone(t.getPromoterTel());
					pl.setId(t.getPromoterId());
					pl.setSubordinateNum(t.getSubordinateNum());
					pl.setSuperiorName(t.getSuperiorName());
					pl.setIncome(Optional.ofNullable(t.getIncome()).orElse(BigDecimal.valueOf(0)));
					pl.setOrderNum(Optional.ofNullable(t.getOrderNum()).orElse(BigInteger.valueOf(0)).intValue());
					pl.setStatus(t.getState());
					data.add(pl);
				});

			}
		}

		Page<PublicityLaw> returnPage = new PageImpl<>(data, PageRequest.of(page, pageSize), promoterReportPage == null ? 0 : promoterReportPage.getTotalElements());

		return returnPage;
	}

	@Transactional
	public boolean stopBusiSure(int id) {
		LawPromoter lawPromoter = lawPromoterService.findByPromoterId(id);
		lawPromoter.setState(lawPromoter.getState().equals(Constant.LAW_PROMOTER_STATE_STOP)?Constant.LAW_PROMOTER_STATE_OK:Constant.LAW_PROMOTER_STATE_STOP);
		Optional<PromoterReport> prOptional = promoterReportRepository.findById(id);
		PromoterReport promoterReport = prOptional.get();
		promoterReport.setState(promoterReport.getState().equals(Constant.LAW_PROMOTER_STATE_STOP)?Constant.LAW_PROMOTER_STATE_OK:Constant.LAW_PROMOTER_STATE_STOP);
		lawPromoterService.save(lawPromoter);
		promoterReportRepository.save(promoterReport);
		return true;
	}
}
