package com.law.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.law.util.WebSocketServer;

@RestController
public class WebSocketController {

	@RequestMapping(value="/webSocket",consumes = "application/json")
	 public @ResponseBody Map<String,Object> pushVideoListToWeb(String data) {
		 Map<String,Object> result =new HashMap<String,Object>();
		 System.err.println(data);
		 try {
			 WebSocketServer.sendInfo("有新客户呼入,sltAccountId:");
			 result.put("operationResult", true);
		 }catch (IOException e) {
			 result.put("operationResult", true);
		 }
		 return result;
	}
}
