package fun.haolo.bigLandlord.core.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import fun.haolo.bigLandlord.core.service.NotifyService;
import fun.haolo.bigLandlord.db.service.ITenantService;
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
    private RedisUtil redisUtil;

    @Autowired
    private ITenantService tenantService;

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;

    @Override
    public SendSmsResponseBody sendCode(String mobilePhoneNo, Integer timeout) throws Exception {

        String code = creatAndSaveCode(mobilePhoneNo, timeout, "AuthCode");

        return send(mobilePhoneNo, code).getBody();
    }

    @Override
    public SendSmsResponseBody sendCodeToTenant(String mobilePhoneNo, Integer timeout) throws Exception {
        tenantService.checkPhone(mobilePhoneNo);
        String code = creatAndSaveCode(mobilePhoneNo, timeout, "tenantCode");
        return send(mobilePhoneNo, code).getBody();
    }

    @Override
    public Boolean checkCode(String mobilePhoneNo, String code, String key) {
        String encodedCode = redisUtil.getCacheObject(key + ":" + mobilePhoneNo);
        return new BCryptPasswordEncoder().matches(code, encodedCode);
    }

    @Override
    public String creatAndSaveCode(String mobilePhoneNo, Integer timeout, String key) {
        // 生成6位验证码
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append((int) (Math.random() * 9));
        }

        // 验证码存入redis
        redisUtil.setCacheObject(key + ":" + mobilePhoneNo, new BCryptPasswordEncoder().encode(code), timeout, TimeUnit.MINUTES);

        return code.toString();
    }

    private SendSmsResponse send(String mobilePhoneNo, String code) throws Exception {
        // 发送
        Config config = new Config().setAccessKeyId(accessKeyId).setAccessKeySecret(accessKeySecret);
        config.endpoint = "dysmsapi.aliyuncs.com";
        Client client = new Client(config);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("haolo社区")
                .setTemplateCode("SMS_170835009")
                .setPhoneNumbers(mobilePhoneNo)
                .setTemplateParam("{\"code\":\"" + code + "\"}");
        return client.sendSms(sendSmsRequest);
    }

}
