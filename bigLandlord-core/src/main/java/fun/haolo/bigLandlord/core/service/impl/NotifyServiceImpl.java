package fun.haolo.bigLandlord.core.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import fun.haolo.bigLandlord.core.service.NotifyService;
import fun.haolo.bigLandlord.db.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author haolo
 * @since 2022-10-26 11:20
 */
@Service
public class NotifyServiceImpl implements NotifyService {

    @Autowired
    RedisUtil redisUtil;

    @Value("${aliyun.sms.accessKeyId}")
    String accessKeyId;
    @Value("${aliyun.sms.accessKeySecret}")
    String accessKeySecret;

    @Override
    public SendSmsResponseBody sendCode(String mobilePhoneNo, Integer timeout) throws Exception {

        // 生成6位验证码
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append((int) (Math.random() * 9));
        }

        // 验证码存入redis，有效期5分钟
        redisUtil.setCacheObject("AuthCode:" + mobilePhoneNo, new BCryptPasswordEncoder().encode(code), timeout, TimeUnit.MINUTES);

        // 发送
        Config config = new Config().setAccessKeyId(accessKeyId).setAccessKeySecret(accessKeySecret);
        config.endpoint = "dysmsapi.aliyuncs.com";
        Client client = new Client(config);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("haolo社区")
                .setTemplateCode("SMS_170835009")
                .setPhoneNumbers(mobilePhoneNo)
                .setTemplateParam("{\"code\":\"" + code + "\"}");
        SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
        return sendSmsResponse.getBody();
    }

    @Override
    public Boolean checkCode(String mobilePhoneNo, String code) {
        String encodedCode = redisUtil.getCacheObject("AuthCode:" + mobilePhoneNo);
        return new BCryptPasswordEncoder().matches(code, encodedCode);
    }

}
