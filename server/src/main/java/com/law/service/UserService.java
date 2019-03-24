package com.law.service;

import com.law.model.SysUser;
import com.law.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  SysUserRepository sysUserRepository;

  public SysUser getSysUser(String userName,String userPwd){
    return sysUserRepository.findOneByUserNameAndUserPwd(userName,userPwd);
  }
  
  public SysUser save(SysUser user) {
	  return sysUserRepository.save(user);
  }

}
