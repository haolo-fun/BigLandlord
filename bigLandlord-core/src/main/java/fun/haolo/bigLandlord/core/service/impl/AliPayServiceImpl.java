package fun.haolo.bigLandlord.core.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import fun.haolo.bigLandlord.core.dto.AliPayDTO;
import fun.haolo.bigLandlord.core.param.AliPayParam;
import fun.haolo.bigLandlord.core.service.AliPayService;
import fun.haolo.bigLandlord.db.entity.Deposit;
import fun.haolo.bigLandlord.db.entity.Order;
import fun.haolo.bigLandlord.db.exception.UnauthorizedException;
import fun.haolo.bigLandlord.db.param.PaymentParam;
import fun.haolo.bigLandlord.db.service.IDepositService;
import fun.haolo.bigLandlord.db.service.IOrderService;
import fun.haolo.bigLandlord.db.service.IUserService;
import fun.haolo.bigLandlord.db.utils.OrderStatusConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haolo
 * @since 2022-10-26 16:31
 */
@Service
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    private IDepositService depositService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @Value("${aliyun.pay.app_id}")
    private String appId;
    @Value("${aliyun.pay.merchant_private_key}")
    private String privateKey;
    @Value("${aliyun.pay.alipay_public_key}")
    private String publicKey;
    @Value("${aliyun.pay.sellerId}")
    private String sellerId;
    @Value("${aliyun.pay.notify_url}")
    private String notifyUrl;
    @Value("${aliyun.pay.return_url}")
    private String returnUrl;
    @Value("${aliyun.pay.sign_type}")
    private String signType;
    @Value("${aliyun.pay.charset}")
    private String charset;
    @Value("${aliyun.pay.time_out}")
    private String timeOut;
    @Value("${aliyun.pay.gatewayUrl}")
    private String serverUrl;

    private static final Log log = LogFactory.get();

    @Override
    public AlipayTradeWapPayResponse alipayTradeWapPay(AliPayDTO param) throws AlipayApiException {
        AlipayConfig alipayConfig = initAlipayConfig();
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        //商户订单号
        model.setOutTradeNo(param.getOut_trade_no());
        //订单总金额
        model.setTotalAmount(param.getTotal_amount());
        //订单标题
        model.setSubject(param.getSubject());
        //商家和支付宝签约的产品码
        model.setProductCode("QUICK_WAP_WAY");
        //卖家支付宝用户ID
        model.setSellerId(sellerId);
        model.setQuitUrl(param.getQuit_url());
        request.setBizModel(model);
        //异步通知
        request.setNotifyUrl(notifyUrl);
        AlipayTradeWapPayResponse response = alipayClient.pageExecute(request, "GET");
//        System.out.println(response.getBody());
        if (!response.isSuccess()) {
            throw new RuntimeException("支付接口调用失败，原因：" + response.getSubCode() + ":" + response.getSubMsg());
        }
        return response;
    }

    @Override
    public AlipayTradePagePayResponse alipayTradePagePay(AliPayDTO param) throws AlipayApiException {
        AlipayConfig alipayConfig = initAlipayConfig();
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        //商户订单号
        model.setOutTradeNo(param.getOut_trade_no());
        //订单总金额
        model.setTotalAmount(param.getTotal_amount());
        //订单标题
        model.setSubject(param.getSubject());
        //商家和支付宝签约的产品码
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        //卖家支付宝用户ID
        request.setBizModel(model);
        //异步通知
        request.setNotifyUrl(notifyUrl);
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "GET");
        if (!response.isSuccess()) {
            throw new RuntimeException("支付接口调用失败，原因：" + response.getSubCode() + ":" + response.getSubMsg());
        }
        return response;
    }

    @Override
    public AlipayTradeQueryResponse alipayTradeQuery(String trade_no) throws AlipayApiException {
        AlipayConfig alipayConfig = initAlipayConfig();
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setTradeNo(trade_no);
        request.setBizModel(model);
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException("交易查询接口调用失败，原因：" + response.getSubCode() + ":" + response.getSubMsg());
        }
    }

    @Override
    public AlipayTradeCloseResponse alipayTradeClose(String trade_no) throws AlipayApiException {
        AlipayConfig alipayConfig = initAlipayConfig();
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel model = new AlipayTradeCloseModel();
        model.setTradeNo(trade_no);
        request.setBizModel(model);
        AlipayTradeCloseResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException("交易关闭接口调用失败，原因：" + response.getSubCode() + ":" + response.getSubMsg());
        }
    }

    @Override
    public AlipayTradeRefundResponse alipayTradeRefund(String refund_amount, String trade_no) throws AlipayApiException {
        AlipayConfig alipayConfig = initAlipayConfig();
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setRefundAmount(refund_amount);
        model.setTradeNo(trade_no);
        request.setBizModel(model);
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException("退款接口调用失败，原因：" + response.getSubCode() + ":" + response.getSubMsg());
        }
    }

    @Override
    public AlipayTradeFastpayRefundQueryResponse alipayTradeFastPayRefundQuery(String out_request_no, String trade_no) throws AlipayApiException {
        AlipayConfig alipayConfig = initAlipayConfig();
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setOutRequestNo(out_request_no);
        model.setTradeNo(trade_no);
        request.setBizModel(model);
        AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException("退款查询接口调用失败，原因：" + response.getSubCode() + ":" + response.getSubMsg());
        }
    }


    @Override
    public AlipayTradePagePayResponse payDepositByPC(AliPayParam param) throws AlipayApiException {
        return alipayTradePagePay(initDepositAlipayDTO(param));
    }

    @Override
    public AlipayTradeWapPayResponse payDepositByPhone(AliPayParam param) throws AlipayApiException {
        return alipayTradeWapPay(initDepositAlipayDTO(param));
    }

    @Override
    public AlipayTradePagePayResponse payOrderByPC(AliPayParam param) throws AlipayApiException {
        return alipayTradePagePay(initOrderAlipayDTO(param));
    }

    @Override
    public AlipayTradeWapPayResponse payOrderByPhone(AliPayParam param) throws AlipayApiException {
        return alipayTradeWapPay(initOrderAlipayDTO(param));
    }

    @Override
    public AlipayTradeRefundResponse alipayTradeRefund(String refund_amount, String deposit_sn, String username) throws AlipayApiException {
        Deposit deposit = depositService.getBySn(deposit_sn);
        Long userId = userService.getUserIdByUsername(username);
        if (deposit.getUserId().equals(userId)) {
            return alipayTradeRefund(refund_amount, deposit.getPayId());
        }
        throw new UnauthorizedException("无法操作此单退款");
    }

    @Override
    public String notifyHandle(HttpServletRequest request) throws AlipayApiException {
        log.info("alipay: " + request.getParameterMap());
        // 预处理支付宝发来的请求
        HashMap<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            params.put(name, valueStr);
        }

        // 验签
        log.info("v2params: " + params);
        boolean signVerified = AlipaySignature.rsaCheckV1(params, publicKey, charset, signType);

        if (signVerified) {
            // 执行验签成功后的逻辑
            log.info("signVerified true");
            String trade_status = params.get("trade_status");
            if (trade_status.equals("TRADE_SUCCESS")) {
                log.info("trade_status TRADE_SUCCESS");
                String trade_no = params.get("trade_no");//支付宝交易号
                String out_trade_no = params.get("out_trade_no");//商户订单号
                String subject = params.get("subject");
                String gmt_payment = params.get("gmt_payment");//该笔交易 的买家付款时间。格式为 yyyy-MM-dd HH:mm:ss。
                DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                //字符串转LocalDateTime
                LocalDateTime payTime = LocalDateTime.parse(gmt_payment, timeDtf);
                if (subject.equals("deposit")) {
                    // 押金逻辑
                    Deposit depositBySn = depositService.getBySn(out_trade_no);
                    Deposit deposit = new Deposit();
                    deposit.setId(depositBySn.getId());
                    deposit.setPayId(trade_no);
                    deposit.setPayTime(payTime);
                    depositService.updateById(deposit);
                    log.info("支付宝异步通知：房屋押金" + out_trade_no + ",交易号" + trade_no + "，于" + gmt_payment + "完成支付");
                } else {
                    // 租单逻辑
                    Order order = new Order();
                    order.setId(orderService.getOrderIdBySn(out_trade_no));
                    order.setPayId(trade_no);
                    order.setPayTime(payTime);
                    order.setOrderStatus(OrderStatusConstant.HAVE_TO_PAY);
                    orderService.updateById(order);
                    log.info("支付宝异步通知：房屋租单" + out_trade_no + ",交易号" + trade_no + "，于" + gmt_payment + "完成支付");
                }
            }
            return "success";
        }
        log.info("验签失败：" + params.get("subject"));
        return "fail";
    }

    private AlipayConfig initAlipayConfig() {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(serverUrl);
        alipayConfig.setAppId(appId);
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(publicKey);
        alipayConfig.setCharset(charset);
        alipayConfig.setSignType(signType);
        return alipayConfig;
    }

    private AliPayDTO initOrderAlipayDTO(AliPayParam param) {
        Order order = orderService.getOrderBySn(param.getSn());
        AliPayDTO alipayDTO = new AliPayDTO();
        alipayDTO.setOut_trade_no(order.getOrderSn());
        alipayDTO.setTotal_amount(order.getPrice().toString());
        alipayDTO.setSubject("rent" + order.getOrderSn());
        alipayDTO.setQuit_url(param.getQuitUrl());
        return alipayDTO;
    }

    private AliPayDTO initDepositAlipayDTO(AliPayParam param) {
        Deposit deposit = depositService.getBySn(param.getSn());
        AliPayDTO alipayDTO = new AliPayDTO();
        alipayDTO.setOut_trade_no(deposit.getDepositSn());
        alipayDTO.setTotal_amount(deposit.getDeposit().toString());
        alipayDTO.setSubject("deposit");
        alipayDTO.setQuit_url(param.getQuitUrl());
        return alipayDTO;
    }
}
