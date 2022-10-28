package fun.haolo.bigLandlord.core.controller;

import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.core.service.NotifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haolo
 * @since 2022-10-26 15:17
 */
@RestController
@RequestMapping("/notify")
@Api(tags = "core_系统通知")
public class NotifyController {

    @Autowired
    private NotifyService notifyService;

    @PostMapping("/sendCode")
    @ApiImplicitParam(name = "phone", value = "手机号", required = true)
    @ApiOperation(value = "发送验证码")
    public Object sendCode(@RequestBody String phone) throws Exception {
        SendSmsResponseBody sendSmsResponseBody = notifyService.sendCode(phone);
        return ResponseResult.success(sendSmsResponseBody.getMessage(), sendSmsResponseBody);
    }
}
