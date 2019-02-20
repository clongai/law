package com.law.controller;

import com.law.model.SysUser;
import com.law.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  UserService userService;

  @PostMapping("/login")
  public SysUser login(@RequestBody SysUser sysUser){
    SysUser user = userService.getSysUser(sysUser.getUserName(),sysUser.getUserPwd());
    return user;
  }
}
