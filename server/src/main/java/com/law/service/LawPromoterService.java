package com.law.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.law.common.TableStatusEnum;
import com.law.model.LawPromoter;
import com.law.repository.LawPromoterRepository;

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

}
