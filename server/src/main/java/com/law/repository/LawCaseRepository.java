package com.law.repository;

import com.law.model.LawCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: nonghz
 * @Date: 2018/10/31 11:21
 * @Description:
 */
public interface LawCaseRepository extends JpaRepository<LawCase, Integer>, JpaSpecificationExecutor<LawCase> {
    @Query(value = "select * from law_case where case_type = 1 and case_name like CONCAT('%',?1,'%')", nativeQuery = true)
    public List<LawCase> findBaseLevelByName(String name);

    @Query(value = "select * from law_case where case_type = 2 and case_name like CONCAT('%',?1,'%')", nativeQuery = true)
    public List<LawCase> findSubLevelByName(String name);

    public LawCase findAllByCaseId(int id);

    @Query(value = "select * from law_case where case_type = 1", nativeQuery = true)
    public List<LawCase> findAllBaseCase();

    public LawCase findLawCaseByCaseName(String name);

    @Query(value = "select * from law_case where case_type = 2 and case_name like CONCAT('%',?1,'%') and parent_case = ?2", nativeQuery = true)
    public List<LawCase> findSubLevelByNameAndParent(String name, int parent);
}
