package com.law;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableCaching
@Configuration
public class LawApplication {

  public static void main(String[] args) {
    SpringApplication.run(LawApplication.class, args);
  }

  @Bean
  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    //  单个数据大小
    factory.setMaxFileSize("51200MB"); // KB,MB
    /// 总上传数据大小
    factory.setMaxRequestSize("51200MB");
    return factory.createMultipartConfig();
  }
}
