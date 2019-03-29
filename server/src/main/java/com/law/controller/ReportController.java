package com.law.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.law.common.AjaxResult;
import com.law.common.ErrorCode;
import com.law.model.PromoterParam;
import com.law.model.PublicityLaw;
import com.law.service.PromoterReportService;

@Controller
public class ReportController {

	@Autowired
	PromoterReportService promoterReportService;

	@RequestMapping("/getPublicityLawByPage")
	@ResponseBody
	public AjaxResult getPublicityLawByPage(@RequestBody String data) {
		JSONObject requestParam = JSONObject.parseObject(data);
		PromoterParam param = new PromoterParam();
		int page = requestParam.getIntValue("page");
		int pageSize = requestParam.getIntValue("pageSize");
		String name = requestParam.getString("name");
		Date startDate = requestParam.getDate("startDate");
		Date endDate = requestParam.getDate("endDate");
		System.err.println(startDate);
		System.err.println(endDate);
		String superiorName = requestParam.getString("superiorName");
		param.setName(name);
		param.setSuperiorName(superiorName);
		param.setStartTime(startDate);
		param.setEndTime(endDate);
		System.err.println(param);
		System.err.println("page=" + page + "    pageSize=" + pageSize);
		Page<PublicityLaw> returnPageData = promoterReportService.getPublicityLawByPage(page, pageSize, param);
		System.err.println(returnPageData);
		return AjaxResult.success(returnPageData);
	}

	@SuppressWarnings("unused")
	@RequestMapping("/stopBusiSure")
	@ResponseBody
	public AjaxResult stopBusiSure(@RequestBody String data) {
		JSONObject requestParam = JSONObject.parseObject(data);
		Integer id = requestParam.getIntValue("id");
		if (id == null)
			return AjaxResult.error(ErrorCode.PARAM_NULL);
		boolean stopBusiSure = promoterReportService.stopBusiSure(id);
		return AjaxResult.success(stopBusiSure);
	}

	@RequestMapping("/updateReportData")
	@ResponseBody
	public void test() {
		promoterReportService.updateReportData();
	}

}
