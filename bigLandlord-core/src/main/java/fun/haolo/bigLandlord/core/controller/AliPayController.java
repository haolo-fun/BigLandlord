package fun.haolo.bigLandlord.core.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.core.param.AliPayParam;
import fun.haolo.bigLandlord.core.service.AliPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author haolo
 * @since 2022-10-27 11:25
 */
@RestController
@RequestMapping("/pay")
@Api(tags = "core_支付")
public class AliPayController {

    @Autowired
    private AliPayService aliPayService;

    @Autowired
    private HttpServletRequest request;

    private static final Log log = LogFactory.get();

    @PostMapping("/order/pc")
    @ApiOperation(value = "通过pc支付租单")
    public ResponseResult<String> payOrderByPC(@RequestBody AliPayParam param) throws AlipayApiException {
        AlipayTradePagePayResponse alipayTradePagePayResponse = aliPayService.payOrderByPC(param);
        return ResponseResult.success(alipayTradePagePayResponse.getBody());
    }

    @PostMapping("/order/phone")
    @ApiOperation(value = "通过手机支付租单")
    public ResponseResult<String> payOrderByPhone(@RequestBody AliPayParam param) throws AlipayApiException {
        AlipayTradeWapPayResponse alipayTradeWapPayResponse = aliPayService.payOrderByPhone(param);
        return ResponseResult.success(alipayTradeWapPayResponse.getBody());
    }

    @PostMapping("/deposit/pc")
    @ApiOperation(value = "通过pc支付押金")
    public ResponseResult<String> payDepositByPC(@RequestBody AliPayParam param) throws AlipayApiException {
        AlipayTradePagePayResponse alipayTradePagePayResponse = aliPayService.payDepositByPC(param);
        return ResponseResult.success(alipayTradePagePayResponse.getBody());
    }

    @PostMapping("/deposit/phone")
    @ApiOperation(value = "通过手机支付押金")
    public ResponseResult<String> payDepositByPhone(@RequestBody AliPayParam param) throws AlipayApiException {
        AlipayTradeWapPayResponse alipayTradeWapPayResponse = aliPayService.payDepositByPhone(param);
        return ResponseResult.success(alipayTradeWapPayResponse.getBody());
    }

    @GetMapping("/TradeQuery/{trade_no}")
    @ApiOperation(value = "交易查询")
    public ResponseResult<AlipayTradeQueryResponse> alipayTradeQuery(@PathVariable String trade_no) throws AlipayApiException {
        return ResponseResult.success(aliPayService.alipayTradeQuery(trade_no));
    }

    @PutMapping("/TradeRefund/{refund_amount}/{deposit_sn}")
    @ApiOperation(value = "退款")
    public ResponseResult<AlipayTradeRefundResponse> alipayTradeRefund(@PathVariable String refund_amount, @PathVariable String deposit_sn) throws AlipayApiException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseResult.success(aliPayService.alipayTradeRefund(refund_amount, deposit_sn, userDetails.getUsername()));
    }

    @PostMapping("/notify")
    @ApiOperation(value = "阿里异步回调接口")
    public String handleAliPayed() throws AlipayApiException {
        log.info(request.toString());
        return aliPayService.notifyHandle(request);
    }

}

