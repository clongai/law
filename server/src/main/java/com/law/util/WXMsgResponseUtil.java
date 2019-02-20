package com.law.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

/**
 * 测试客服消息发送 Title:TemplateTest Description:
 */

public class WXMsgResponseUtil {


    private static final Logger log = LoggerFactory.getLogger(WXMsgResponseUtil.class);



    private static String RES_RESULT =
            "你好，很高兴为你服务。";

    /***
     * 文档地址：https://mp.weixin.qq.com/debug/wxadoc/dev/api/custommsg/conversation.html
     * 发送的文本消息
     */
    public static JSONObject sendCustomerMessage(String touser) {
        JSONObject obj = new JSONObject();

        obj.put("touser", touser);
        obj.put("msgtype", "text");

        JSONObject text = new JSONObject();
        text.put("content", RES_RESULT);

        obj.put("text", text);

//        obj.put("ToUserName",touser);
//        obj.put("FromUserName","S______A");
//        obj.put("CreateTime", (new Date()).getTime());
//        obj.put("MsgType","transfer_customer_service");

        log.info("回复的文本:\n" + obj.toString());
        JSONObject jsonObject = HttpUtil.httpsRequest(obj);

        log.info("回复jsonObject:\n" + jsonObject);
        return jsonObject;
    }


    public static JSONObject sendCustomerMessage(String touser,String oldId,String keyWord) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);// 设置超时
        requestFactory.setReadTimeout(5000);
        RestTemplate template = new RestTemplate(requestFactory);


        JSONObject obj = new JSONObject();

        obj.put("touser", touser);
        obj.put("msgtype", "text");

        JSONObject text = new JSONObject();

        //RES_RESULT 根据index在数据库中查询
        text.put("content", RES_RESULT);

        obj.put("text", text);

        log.info("回复的文本:\n" + obj.toString());

        return JSONObject.parseObject(RES_RESULT);
    }
    
    /**
     * post请求,发送消息
     */
    public static void customSend(String json, String accessToken) {
 
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(json);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println("客服消息result：" + result);
        } catch (Exception e) {
            System.out.println("向客服发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    /***
     * 文档地址：https://mp.weixin.qq.com/debug/wxadoc/dev/api/custommsg/conversation.html
     * 发送的图片消息
     */
    public static JSONObject sendCustomerImageMessage(String touser, String mediaId) {
        JSONObject obj = new JSONObject();

        obj.put("touser", touser);
        obj.put("msgtype", "image");

        JSONObject media = new JSONObject();
        media.put("media_id", mediaId);

        obj.put("image", media);

        System.out.println("回复的图片:\n" + obj.toString());
        JSONObject jsonObject = HttpUtil.httpsRequest(obj);
        System.out.println(jsonObject);
        return jsonObject;
    }

    public static String sendFirstMessage(String appId,String index, String touser) {


        JSONObject obj = new JSONObject();

        obj.put("touser", touser);
        obj.put("msgtype", "text");


        JSONObject text = new JSONObject();

        //RES_RESULT 根据index在数据库中查询
        text.put("content", RES_RESULT);

        obj.put("text", text);

        JSONObject jsonObject = HttpUtil.httpsRequest(obj,"accessToken");

        return jsonObject.toString();
    }
}

