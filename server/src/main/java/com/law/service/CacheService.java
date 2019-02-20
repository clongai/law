package com.law.service;


import com.law.model.SysCode;
import com.law.repository.SysCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheService {

  @Autowired
  SysCodeRepository sysCodeRepository;

  @Cacheable(value = "SYS_CODE")
  public List<SysCode> getSysCode() {
    return sysCodeRepository.findAll();
  }

  @CacheEvict(value = "SYS_CODE")
  public void clearSysCode() {
  }
}
