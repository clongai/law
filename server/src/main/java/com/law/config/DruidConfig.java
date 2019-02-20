package com.law.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DruidConfig {

  private Logger logger = LoggerFactory.getLogger(DruidConfig.class);

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Value("${spring.datasource.type}")
  private String type;

  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Value("${spring.datasource.durid.initialSize}")
  private int initialSize;

  @Value("${spring.datasource.durid.minIdle}")
  private int minIdle;

  @Value("${spring.datasource.durid.maxActive}")
  private int maxActive;

  @Value("${spring.datasource.durid.maxWait}")
  private int maxWait;

  @Value("${spring.datasource.durid.timeBetweenEvictionRunsMillis}")
  private int timeBetweenEvictionRunsMillis;

  @Value("${spring.datasource.durid.minEvictableIdleTimeMillis}")
  private int minEvictableIdleTimeMillis;

  @Value("${spring.datasource.durid.validationQuery}")
  private String validationQuery;

  @Value("${spring.datasource.durid.testWhileIdle}")
  private boolean testWhileIdle;

  @Value("${spring.datasource.durid.testOnBorrow}")
  private boolean testOnBorrow;

  @Value("${spring.datasource.durid.testOnReturn}")
  private boolean testOnReturn;

  @Value("${spring.datasource.durid.filters}")
  private String filters;

  @Value("${spring.datasource.durid.logSlowSql}")
  private String logSlowSql;

  @Bean
  public ServletRegistrationBean druidServlet() {
    ServletRegistrationBean reg = new ServletRegistrationBean();
    reg.setServlet(new StatViewServlet());
    reg.addUrlMappings("/druid/*");
    reg.addInitParameter("logSlowSql", logSlowSql);
    return reg;
  }

  @Bean
  public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new WebStatFilter());
    filterRegistrationBean.addUrlPatterns("/*");
    filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
    filterRegistrationBean.addInitParameter("profileEnable", "true");
    return filterRegistrationBean;
  }

  @Bean
  public DataSource druidDataSource() {
    DruidDataSource datasource = new DruidDataSource();
    datasource.setUrl(dbUrl);
    datasource.setDriverClassName(driverClassName);
    datasource.setInitialSize(initialSize);
    datasource.setUsername(username);
    datasource.setPassword(password);
    datasource.setMinIdle(minIdle);
    datasource.setMaxActive(maxActive);
    datasource.setMaxWait(maxWait);
    datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
    datasource.setValidationQuery(validationQuery);
    datasource.setTestWhileIdle(testWhileIdle);
    datasource.setTestOnBorrow(testOnBorrow);
    datasource.setTestOnReturn(testOnReturn);

    datasource.setPoolPreparedStatements(true);
    datasource.setMaxPoolPreparedStatementPerConnectionSize(20);

    //超时回收
//    datasource.setRemoveAbandoned(true);
//    datasource.setRemoveAbandonedTimeout(5*60);
//    datasource.setLogAbandoned(true);

    try {
      datasource.setFilters(filters);
    } catch (SQLException e) {
      logger.error("druid configuration initialization filter", e);
    }
    return datasource;
  }

  @Bean
  public DruidStatInterceptor druidStatInterceptor(){
    return new DruidStatInterceptor();
  }

  @Bean
  public JdkRegexpMethodPointcut druidStatPointcut(){
    JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
    String patterns = "com.eb.esop.service.*";
    druidStatPointcut.setPatterns(patterns);
    return druidStatPointcut;
  }

  @Bean
  public Advisor druidStatAdvisor() {
    return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
  }
}
