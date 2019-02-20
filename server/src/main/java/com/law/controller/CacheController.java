package com.law.controller;

import com.law.model.SysCode;
import com.law.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CacheController {

  @Autowired
  CacheService cacheService;

  @RequestMapping(value = "/getSysCode")
  public List<SysCode> getSysCode(){
    return cacheService.getSysCode();
  }

  @RequestMapping(value = "/refreshCache")
  public boolean refreshCache(){
    cacheService.clearSysCode();
    cacheService.getSysCode();
    return true;
  }

}
