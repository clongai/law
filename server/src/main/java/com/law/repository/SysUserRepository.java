package com.law.repository;

import com.law.model.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysUserRepository extends JpaRepository<SysUser,Integer> {

  public SysUser findOneByUserNameAndUserPwd(String userName,String userPwd);


  public List<SysUser> getAllByServiceLevelLike(String serviceLevel);
}
