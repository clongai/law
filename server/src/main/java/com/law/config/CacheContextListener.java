package com.law.config;


import com.law.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CacheContextListener implements CommandLineRunner {
  @Autowired
  CacheService cacheService;

  @Override
  public void run(String... args) throws Exception {
    cacheService.getSysCode();
  }
}
