package fun.haolo.bigLandlord.core.service;

import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;

/**
 * @author haolo
 * @since 2022-10-26 11:17
 */
public interface NotifyService {

    SendSmsResponseBody sendCode(String mobilePhoneNo) throws Exception;

    Boolean checkCode(String mobilePhoneNo,String code);

}
