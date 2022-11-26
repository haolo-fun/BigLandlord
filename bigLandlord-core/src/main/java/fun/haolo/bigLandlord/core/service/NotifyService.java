package fun.haolo.bigLandlord.core.service;

import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;

/**
 * @author haolo
 * @since 2022-10-26 11:17
 */
public interface NotifyService {

    SendSmsResponseBody sendCode(String mobilePhoneNo, Integer timeout) throws Exception;

    SendSmsResponseBody sendCodeToTenant(String mobilePhoneNo, Integer timeout) throws Exception;

    Boolean checkCode(String mobilePhoneNo, String code, String key);

    String creatAndSaveCode(String mobilePhoneNo, Integer timeout, String key);

}
