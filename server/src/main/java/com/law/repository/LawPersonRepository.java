package com.law.repository;

import com.law.model.LawPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LawPersonRepository extends JpaRepository<LawPerson,Integer>,JpaSpecificationExecutor<LawPerson> {

  public List<LawPerson> findAllByOrderIdAndPersonTypeOrderByPersonId(Integer orderId,String personType);

}
