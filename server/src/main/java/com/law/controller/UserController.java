package com.law.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.law.common.AjaxResult;
import com.law.common.ErrorCode;
import com.law.model.SysUser;
import com.law.service.PromoterReportService;
import com.law.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	PromoterReportService promoterReportService;

	@PostMapping("/login")
	public SysUser login(@RequestBody SysUser sysUser) {
		SysUser user = userService.getSysUser(sysUser.getUserName(), sysUser.getUserPwd());
		return user;
	}

	@PostMapping("/register")
	public AjaxResult register(@RequestBody SysUser sysUser) {
		System.err.println(sysUser);
		if (sysUser == null || StringUtils.isEmpty(sysUser.getUserName()) || StringUtils.isEmpty(sysUser.getUserPwd())) {
			return AjaxResult.fail(ErrorCode.PARAM_NULL);
		}
		sysUser.setFunction(1);
		//判断账号是否重复
		SysUser findTop1ByUserName = userService.findTop1ByUserName(sysUser.getUserName());
		if(findTop1ByUserName!=null) {
			return AjaxResult.fail(ErrorCode.WORK_EXIST);
		}
		SysUser user = userService.save(sysUser);
		return AjaxResult.success(user);
	}

}
