package com.law.wxpay;

import com.github.wxpay.sdk.WXPayConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @Auther: nonghz
 * @Date: 2018/10/30 10:27
 * @Description:
 */
@Component
public class LawWXPayConfig implements WXPayConfig {
    private static final Logger log = LoggerFactory.getLogger(LawWXPayConfig.class);

    @Value("${law.appId}")
    private String appId;

    @Value("${law.appSecret}")
    private String appSecret;

    @Value("${law.wxpay.mchId}")
    private String mchId;

    @Value("${law.wxpay.cretUrl}")
    private String cretUrl;

    @Value("${law.wxpay.key}")
    private String key;

    @Value("${law.wxpay.httpConnectTimeoutMs}")
    private int httpConnectTimeoutMs;

    @Value("${law.wxpay.httpConnectTimeoutMs}")
    private int httpReadTimeoutMs;

    @Override
    public String getAppID() {
        return appId;
    }

    @Override
    public String getMchID() {
        return mchId;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        try {
            File file = new File(cretUrl);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();
            return inputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("证书不存在");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("获取证书失败");
        }
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return httpConnectTimeoutMs;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return httpReadTimeoutMs;
    }
}
