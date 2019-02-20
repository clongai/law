package com.law.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.law.model.LawServiceEntity;


/**
 * @Auther: nonghz
 * @Date: 2018/10/25 11:16
 * @Description:
 */
public interface LawServiceRepository extends JpaRepository<LawServiceEntity,Integer>,JpaSpecificationExecutor<LawServiceEntity> {
    @Query(value = "select * from law_service a where a.status = 'U' order by service_fee, service_level ASC", nativeQuery = true)
    List<LawServiceEntity> findAllService();

    public LawServiceEntity getByServiceId(int serviceId);

	List<LawServiceEntity> findAllByServiceLevelAndStatus(String serviceLevel, String status);

	List<LawServiceEntity> findAllByServiceLevelInAndStatus(List<Integer> serviceLevel, String status);
}
