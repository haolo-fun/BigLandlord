package fun.haolo.bigLandlord.core.controller;

import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.core.service.NotifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/sendCode/{phone}")
    @ApiImplicitParam(name = "phone", value = "手机号", required = true)
    @ApiOperation(value = "发送验证码")
    public Object sendCode(@PathVariable String phone) throws Exception {
        SendSmsResponseBody sendSmsResponseBody = notifyService.sendCode(phone, 5);
        return ResponseResult.success(sendSmsResponseBody.getMessage(), sendSmsResponseBody);
    }

    @PostMapping("/sendCode/tenant/{phone}")
    @ApiImplicitParam(name = "phone", value = "手机号", required = true)
    @ApiOperation(value = "给租户发送验证码")
    public Object sandCodeToTenant(@PathVariable String phone) throws Exception {
        SendSmsResponseBody sendSmsResponseBody = notifyService.sendCodeToTenant(phone, 10);
        return ResponseResult.success(sendSmsResponseBody.getMessage(), sendSmsResponseBody);
    }
}
