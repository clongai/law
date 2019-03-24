package com.law.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.law.entity.WxMessage;

public interface WxMessageRepository extends JpaRepository<WxMessage, Integer>, JpaSpecificationExecutor<WxMessage> {

}
