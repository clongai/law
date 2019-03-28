package com.law.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.law.model.LawPromoter;

/**
 * @Auther: nonghz
 * @Date: 2018/11/6 15:49
 * @Description:
 */
public interface LawPromoterRepository extends JpaRepository<LawPromoter, Integer>, JpaSpecificationExecutor<LawPromoter> {
    public LawPromoter findOneByOpenIdAndStatus(String openId, String status);

    public Integer countByParentPromoterIdAndStatus(int promoterId, String status);

    public List<LawPromoter> findAllByStatus(String status);
    public List<LawPromoter> findAllByPromoterIdIn(List<Integer> ids);

    public List<LawPromoter> findAllByParentPromoterId(int parentPromoterId);

	public LawPromoter findByOpenIdAndStatus(String openId, String u);

	public LawPromoter findByPromoterId(int parentPromoterId);

	public LawPromoter findByOpenId(String openId);

	public long countByStatus(String status);
	
	
}
