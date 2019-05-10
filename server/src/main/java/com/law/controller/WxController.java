package com.law.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayConstants;
import com.law.common.Constant;
import com.law.entity.WxMessage;
import com.law.idworker.IdWorker;
import com.law.model.Chat;
import com.law.model.GetWxMessage;
import com.law.model.LawOrder;
import com.law.model.LawPay;
import com.law.model.LawPromoter;
import com.law.model.Message;
import com.law.model.Text;
import com.law.model.TextMessage;
import com.law.model.User;
import com.law.service.LawPromoterService;
import com.law.service.LawService;
import com.law.service.WxMessageService;
import com.law.util.SignUtil;
import com.law.util.WXMsgResponseUtil;
import com.law.util.WXPayUtil;

import sun.misc.BASE64Encoder;

@RestController
public class WxController {

	private static final Logger log = LoggerFactory.getLogger(WxController.class);
	@Value("${law.appId}")
	private String appId;

	@Value("${law.appSecret}")
	private String appSecret;

	@Autowired
	IdWorker idWorker;

	@Autowired
	IdWorker payOrderIdWorker;

	@Value("${law.upload.dir}")
	private String uploadDir;

	@Value("${law.imagesUrl}")
	private String imagesUrl;

	@Value("${law.wxpay.spbillCreateIp}")
	private String spbillCreateIp;

	@Value("${law.wxpay.key}")
	private String key;

	@Autowired
	LawService lawService;
	@Autowired
	WxMessageService wxMessageService;
	@Autowired
	LawPromoterService lawPromoterService;

	@PostMapping(value = "/getOpenId")
	public String getOpenId(@RequestBody String data) throws Exception {
		JSONObject json = JSONObject.parseObject(data);
		String code = (String) json.get("code");

		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
		String reusult = httpGet(url);
		JSONObject oppidObj = JSONObject.parseObject(reusult);
		String openid = (String) oppidObj.get("openid");
		return openid;
	}

	public String httpGet(String url) throws Exception {
		String result = "";

		// 创建httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 创建get方式请求对象
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Content-type", "application/json");
		// 通过请求对象获取响应对象
		CloseableHttpResponse response = httpClient.execute(httpGet);

		// 获取结果实体
		// 判断网络连接状态码是否正常(0--200都数正常)
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		}
		// 释放链接
		response.close();

		return result;
	}

	@RequestMapping(value = "/getAccessToken")
	public String getAccessToken() throws Exception {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
		String reusult = httpGet(url);
		JSONObject oppidObj = JSONObject.parseObject(reusult);
		String accessToken = (String) oppidObj.get("access_token");
		return accessToken;
	}

	@RequestMapping(value = "/getQCode")
	public String getQCode(@RequestBody String data) throws Exception {
		String accessToken = getAccessToken();
		JSONObject json = JSONObject.parseObject(data);
		String scene = (String) json.get("scene");

		boolean isPromoter = lawService.isPromoter(scene);
		if (!isPromoter) {
			return "noregistry";
		}

		JSONObject obj = new JSONObject();
		obj.put("scene", scene);
		obj.put("width", 290);
		String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken;

		HttpPost httpRequst = new HttpPost(url);
		InputStream is = null;
		StringEntity se = new StringEntity(obj.toString(), "utf-8");
		se.setContentType("application/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "UTF-8"));
		httpRequst.setEntity(se);
		HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				is = httpEntity.getContent();
			}
		}

		int len;
		byte[] bs = new byte[1024];
		String fileName = idWorker.nextId();
		File file = new File(uploadDir + fileName + ".jpeg");
		OutputStream os = new FileOutputStream(file);

		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		os.close();

		FileInputStream inputStream = new FileInputStream(file);
		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);
		inputStream.close();

		file.delete();

		BASE64Encoder encoder = new BASE64Encoder();
		String base64 = encoder.encode(bytes);
		base64 = base64.replaceAll("[\\s*\t\n\r]", "");
		return base64;
	}

	@RequestMapping(value = "/getBase64")
	public String getBase64(@RequestBody String data) throws Exception {
		JSONObject json = JSONObject.parseObject(data);
		String fileName = (String) json.get("fileName");
		File file = new File(imagesUrl + fileName);
		FileInputStream inputStream = new FileInputStream(file);
		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);
		inputStream.close();

		BASE64Encoder encoder = new BASE64Encoder();
		String base64 = encoder.encode(bytes);
		base64 = base64.replaceAll("[\\s*\t\n\r]", "");
		return "data:image/png;base64," + base64;

	}

	@RequestMapping(value = "/getWXPayParam")
	public Map<String, String> getWXPayParam(@RequestBody String data) throws Exception {
		JSONObject json = JSONObject.parseObject(data);
		LawPay lawPay = json.getObject("lawPay", LawPay.class);
		String status = json.getString("status");
		String termsType = json.getString("termsType");
		String openId = (String) json.get("openId");
		String out_trade_no = payOrderIdWorker.nextId();
		lawPay.setOutTradeNo(out_trade_no);
		if (status.equalsIgnoreCase("1")) {
			//绑定订单和咨询档次
			int orderId = lawPay.getOrderId();
			lawService.bindService(orderId, lawPay.getServiceId());
		}
		int feeType = lawPay.getFeeType();
		int payType = lawPay.getPayType();
		if (payType == 1) {
			LawOrder lawOrder = lawService.queryLawOrder(lawPay.getOrderId());
			if (status.equalsIgnoreCase("4")) {
				lawOrder.setStatus("5");//状态改为二审通过已支付
			} else if (status.equalsIgnoreCase("8")) {
				lawOrder.setStatus("9");//状态改为补充二审通过已付款
			}
			lawService.move2LawOrderHis(lawOrder);
			lawOrder.setDoneCode(idWorker.nextId());
			lawOrder.setDoneDate(new Timestamp(System.currentTimeMillis()));
			lawService.saveLawOrder(lawOrder);
			lawService.addPayOrder(lawPay);
			Map<String, String> map = new HashMap<>();
			map.put("flag", "post-payment success");
			return map;
		}
		String body = "咨询费";
		if (feeType == 0) {
			body = "咨询费";
		} else if (feeType == 1) {
			body = "中介费";
		}
		lawService.addPayOrder(lawPay);
		//统一下单
		Map<String, String> unifiedOrderMap = new HashMap<String, String>();
		unifiedOrderMap.put("body", body);//商品描述
		unifiedOrderMap.put("openid", openId);//openId
		unifiedOrderMap.put("trade_type", "JSAPI");//支付方式
		unifiedOrderMap.put("out_trade_no", out_trade_no);//商户订单号
		unifiedOrderMap.put("total_fee", "1");//订单总金额，单位为分
		unifiedOrderMap.put("spbill_create_ip", spbillCreateIp);//服务器ip
		if(!StringUtils.isEmpty(termsType))
		unifiedOrderMap.put("terms_type", termsType);
		unifiedOrderMap.put("notify_url", "https://law.loadpeople.com/wxpayCallback");//支付成功回调页面
		Map<String, String> resMap = lawService.unifiedOrder(unifiedOrderMap);
		String prepayId = "prepay_id=" + resMap.get("prepay_id");

		//二次签名
		Map<String, String> paySignMap = new HashMap<String, String>();
		paySignMap.put("appId", appId);
		paySignMap.put("package", prepayId);
		paySignMap.put("signType", "MD5");
		paySignMap.put("nonceStr", WXPayUtil.generateNonceStr());
		paySignMap.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));

		String paySign = WXPayUtil.generateSignature(paySignMap, key, WXPayConstants.SignType.MD5);
		paySignMap.put("paySign", paySign);

		return paySignMap;
	}

	@RequestMapping(value = "/wxpayCallback")
	public String wxpayCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("微信支付结果回调开始");
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = request.getReader();
			String line = "";
			String xmlString = null;
			StringBuilder inputString = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				inputString.append(line);
			}
			xmlString = inputString.toString();
			log.info("微信支付结果回调:" + xmlString);
			bufferedReader.close();
			Map<String, String> map = new HashMap<>();
			map = WXPayUtil.xmlToMap(xmlString);
			String return_code = null;
			return_code = map.get("result_code");
			if (return_code.equals("SUCCESS")) {
				if (com.github.wxpay.sdk.WXPayUtil.isSignatureValid(xmlString, key)) {
					String out_trade_no = map.get("out_trade_no");
					String terms_type = map.get("terms_type");
					log.info("通知微信收到支付结果:" + return_code);
					lawService.deal4PaySuccess(out_trade_no, idWorker.nextId(), terms_type);
					log.info("通知微信收到支付结果后，订单状态更新成功！");
					return WXPayUtil.responseXml(return_code);
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return WXPayUtil.responseXml("FAIL");
	}

	@RequestMapping(value = "/bindPromoter")
	public Map<String, Object> bindPromoter(@RequestBody String data) {
		JSONObject json = JSONObject.parseObject(data);
		String openId = json.getString("openId");
		String parentOpenId = json.getString("parentOpenId");
		if(StringUtils.isEmpty(parentOpenId)) {
			parentOpenId = "oBy6H5HMzrBQNvo8tsKj8MwxpRDU";
		}
		
		return lawService.bindPromoter(openId, parentOpenId);
	}

	@RequestMapping(value = "/bindPromoterPhone")
	public boolean bindPromoterPhone(@RequestBody String data) {
		JSONObject json = JSONObject.parseObject(data);
		String openId = json.getString("openId");
		String phone = json.getString("phone");
		return lawService.bindPromoterPhone(openId, phone);
	}

	@GetMapping("/wxMessage")
	public @ResponseBody String weixinProcessGetMethod(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("get请求进入");
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			log.info("get" + echostr);
			return echostr;
		}
		log.info("get   NULL");
		return null;
	}

	@PostMapping("/wxMessage")
	@ResponseBody
	public String weixinProcessPostMethod(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("post请求进入");
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		response.setCharacterEncoding("UTF-8");
		String success = "{\"success\":\"success\"}";
		JSONObject object = JSONObject.parseObject(success);
		try {
			ServletInputStream stream = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			StringBuffer buffer = new StringBuffer();
			String line = new String("");
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
			log.info("JSONObject   JSONObject" + jsonObject);
			if (jsonObject.getString("MsgType").equals("text")) { //收到的是文本消息
				//回复转人工服务
				if ("人工服务".equals(jsonObject.getString("Content"))) {
					HashMap<String, Object> resultMap = new HashMap<>();
					resultMap.put("ToUserName", jsonObject.getString("FromUserName"));
					resultMap.put("FromUserName", jsonObject.getString("ToUserName"));
					resultMap.put("CreateTime", Long.parseLong(jsonObject.getString("CreateTime")));
					resultMap.put("MsgType", "transfer_customer_service");
					String json = JSON.toJSONString(resultMap);
					JSONObject result = JSONObject.parseObject(json);
					log.info("POST   result" + result);
					return result.toString();
				}
				//保存数据到记录表
				GetWxMessage getWxMessage = JSONObject.parseObject(buffer.toString(), GetWxMessage.class);
				WxMessage chat = new WxMessage();
				chat.setContent(getWxMessage.getContent());
				chat.setCreateTime(getWxMessage.getCreateTime());
				chat.setDoneTime(new Date());
				chat.setFromUser(getWxMessage.getFromUserName());
				chat.setMsgId(getWxMessage.getMsgId());
				chat.setMsgType(getWxMessage.getMsgType());
				chat.setToUser(getWxMessage.getToUserName());
				chat.setMsgSource(Constant.WX_GET);
				wxMessageService.save(chat);
				//也回复一个文本消息
				log.info("POST" + jsonObject);
				/*TextMessage tm = new TextMessage();
				tm.setMsgtype("text");
				tm.setTouser(jsonObject.getString("FromUserName"));
				tm.setText(new Text("谢谢！"));
				WXMsgResponseUtil.customSend(JSONObject.toJSONString(tm), getAccessToken());*/
				//WXMsgResponseUtil.sendCustomerMessage(jsonObject.getString("FromUserName"),jsonObject.getString("ToUserName"),jsonObject.getString("Content"));
				return "success";
			} else if (jsonObject.getString("MsgType").equals("event")) {
				String sessionFrom = (String) jsonObject.get("SessionFrom");
				log.info("SessionFrom   SessionFrom" + sessionFrom);
				int i = sessionFrom.indexOf("+");
				String sessionFromFirst = "1";
				String appId = "*****";
				if (i > 0) {
					sessionFromFirst = sessionFrom.substring(0, i); //标志位 1 2 3 4 5 6
					log.info("SessionFrom   sessionFromFirst    " + sessionFromFirst);
					String sessionFromLast = sessionFrom.substring(i + 1); //{"appId":"","data":"test"}
					log.info("SessionFrom   sessionFromLast     " + sessionFromLast);
					if (JSONObject.parseObject(sessionFromLast).get("appId") != null) {
						appId = (String) JSONObject.parseObject(sessionFromLast).get("appId");
					}
				}
				WXMsgResponseUtil.sendFirstMessage(appId, sessionFromFirst, jsonObject.getString("FromUserName"));
				return "success";
			} else { //那就是图片的消息了

				//也回复一个图片消息
				WXMsgResponseUtil.sendCustomerImageMessage(jsonObject.getString("FromUserName"), jsonObject.getString("MediaId"));
				return "success";
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("回复异常：" + e);
		}
		return null;

	}

	@PostMapping("/sendMessage")
	public boolean sendMessage(@RequestBody String data) throws Exception {
		JSONObject json = JSONObject.parseObject(data);
		String toUser = json.getString("toUser");
		String content = json.getString("content");
		WxMessage chat = new WxMessage();
		chat.setContent(content);
		chat.setCreateTime(new Date());
		chat.setDoneTime(new Date());
		chat.setMsgType("text");
		chat.setToUser(toUser);
		chat.setMsgSource(Constant.WX_PUSH);
		wxMessageService.save(chat);
		TextMessage tm = new TextMessage();
		tm.setMsgtype("text");
		tm.setTouser(toUser);
		tm.setText(new Text(content));
		WXMsgResponseUtil.customSend(JSONObject.toJSONString(tm), getAccessToken());
		return true;
	}
	
	@RequestMapping("/getChatData")
	public List<Chat> initChatData() {
		List<Chat> sessions = new ArrayList<>();
		List<WxMessage> allChatMsgs = wxMessageService.findAll();
		if(allChatMsgs!=null&&!allChatMsgs.isEmpty()) {
			Map<String,List<WxMessage>> msgMap = new HashMap<>();
			allChatMsgs.stream().forEach(t->{
				if(t.getFromUser()!=null&&t.getMsgSource().equals(Constant.WX_GET)) {
					Optional<List<WxMessage>> optional = Optional.ofNullable(msgMap.get(t.getFromUser()));
					List<WxMessage> orElse = optional.orElse(new ArrayList<WxMessage>());
					orElse.add(t);
					msgMap.put(t.getFromUser(), orElse);
				}else if(t.getMsgSource().equals(Constant.WX_PUSH)){
					Optional<List<WxMessage>> optional = Optional.ofNullable(msgMap.get(t.getToUser()));
					List<WxMessage> orElse = optional.orElse(new ArrayList<WxMessage>());
					orElse.add(t);
					msgMap.put(t.getToUser(), orElse);
				}
			});
			Set<Entry<String, List<WxMessage>>> entrySet = msgMap.entrySet();
			Iterator<Entry<String, List<WxMessage>>> iterator = entrySet.iterator();
			while(iterator.hasNext()) {
				Entry<String, List<WxMessage>> next = iterator.next();
				String key = next.getKey();
				List<WxMessage> value = next.getValue();
				Chat chat = new Chat();
				chat.setId(key);
				LawPromoter lawPromoter = lawPromoterService.findByOpenId(key);
				User user = new User();
				user.setImg("src/components/page/chat/dist/images/2.png");
				user.setName("用户");
				if(lawPromoter!=null) {
					Optional<String> optional = Optional.ofNullable(lawPromoter.getPromoterName());
					String orElse = optional.orElse("用户");
					user.setImg(lawPromoter.getAvatarUrl());
					user.setName(orElse);
				}
				chat.setUser(user);
				value = value.stream().sorted(Comparator.comparing(WxMessage::getDoneTime)).collect(Collectors.toList());
				List<Message> messages = new ArrayList<>();
				if(value!=null&&!value.isEmpty()) {
					value.stream().forEach(val->{
						Message message = new Message();
						message.setContent(val.getContent());
						if(val.getMsgSource().equals(Constant.WX_PUSH)) {
							message.setSelf(true);
						}
						DateTime dateTime = new DateTime(val.getDoneTime().getTime());
						message.setDate(dateTime.toString());
						messages.add(message);
					});
				}
				
				chat.setMessages(messages);
				sessions.add(chat);
			}
		}
		
		
	
		
		return sessions;
	}

}