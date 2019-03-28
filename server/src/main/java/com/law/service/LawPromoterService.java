package com.law.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.law.common.Constant;
import com.law.common.TableStatusEnum;
import com.law.model.LawPromoter;
import com.law.model.PublicityLaw;
import com.law.repository.LawPromoterRepository;
import com.law.util.ListUtil;

@Service
public class LawPromoterService {

	@Autowired
	LawPromoterRepository lawPromoterRepository;

	public LawPromoter findByOpenIdAndStatus(String openId, String u) {
		return lawPromoterRepository.findByOpenIdAndStatus(openId, u);
	}
	public LawPromoter findByOpenId(String openId) {
		return lawPromoterRepository.findByOpenId(openId);
	}

	public LawPromoter findByPromoterId(int parentPromoterId) {
		return lawPromoterRepository.findByPromoterId(parentPromoterId);
	}

	public List<LawPromoter> findAllByParentPromoterId(int promoterId) {
		return lawPromoterRepository.findAllByParentPromoterId(promoterId);
	}

	public Map<String, Object> getRelationData(String openId) {
		Map<String, Object> returnMap = new HashMap<>();
		// 获取个人信息
		LawPromoter lawPromoter = findByOpenIdAndStatus(openId, TableStatusEnum.U.getCode());
		if (lawPromoter != null) {

			// 获取推荐人信息
			LawPromoter parentLawPromoter = findByPromoterId(lawPromoter.getParentPromoterId());
			// 获取下级信息
			List<LawPromoter> childAllByParentPromoterId = findAllByParentPromoterId(lawPromoter.getPromoterId());
			returnMap.put("parent", parentLawPromoter);
			returnMap.put("child", childAllByParentPromoterId);
		}
		returnMap.put("lawPromoter", lawPromoter);

		return returnMap;
	}

	public LawPromoter save(LawPromoter lawPromoter) {
		return lawPromoterRepository.save(lawPromoter);
	}

	
	public long countByStatus(String status) {
		return lawPromoterRepository.countByStatus(status);
	}
	
	@SuppressWarnings("serial")
	public Page<LawPromoter> findAll(Integer page,Integer pageSize) {
		return lawPromoterRepository.findAll(new Specification<LawPromoter>() {
			
			@Override
			public Predicate toPredicate(Root<LawPromoter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				//list.add(e)
				list.add(cb.equal(root.get("status").as(String.class), Constant.DATA_STATUS_OK));
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		}, PageRequest.of(page,pageSize));
	}
	
	public void getPublicityLawByPage(int page,int pageSize) {
		Page<LawPromoter> lawPromoterList = findAll(page, pageSize);
		List<PublicityLaw> data = new ArrayList<>();
		if(lawPromoterList!=null) {
			List<LawPromoter> content = lawPromoterList.getContent();
			if(ListUtil.isNotBlank(content)) {
				content.stream().forEach(t->{
					PublicityLaw pl = new PublicityLaw();
					pl.setName(t.getPromoterName());
					pl.setPhone(t.getPromoterTel());
				});
				
			}
		}
	}
	
	
	public void findAllBySql() {
		StringBuilder sql = new StringBuilder();
		sql.append(" select promoter_id,parent_promoter_id ,open_id ,channel_id ,promoter_name ,promoter_tel ,identity_id ,bank_account ,promoter_level ,status ,create_time ,avatar_url ,state from law_promoter t where  ");
	}
	
	public List<LawPromoter> findAllByPromoterIdIn(List<Integer> ids){
		return lawPromoterRepository.findAllByPromoterIdIn(ids);
	}
}
