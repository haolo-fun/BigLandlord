package fun.haolo.bigLandlord.core.service.impl;

import cn.hutool.core.util.StrUtil;
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
import fun.haolo.bigLandlord.db.entity.House;
import fun.haolo.bigLandlord.db.entity.Order;
import fun.haolo.bigLandlord.db.exception.UnauthorizedException;
import fun.haolo.bigLandlord.db.service.*;
import fun.haolo.bigLandlord.db.utils.DepositStatusConstant;
import fun.haolo.bigLandlord.db.utils.HouseStatusConstant;
import fun.haolo.bigLandlord.db.utils.OrderStatusConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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

    // todo 加锁

    @Autowired
    private IDepositService depositService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IHouseService houseService;

    @Autowired
    private IRunningTallyService runningTallyService;

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
    //    @Value("${aliyun.pay.return_url}")
//    private String returnUrl;
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
        model.setOutRequestNo(String.valueOf(System.currentTimeMillis()));
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
    @Transactional
    public void depositRefund(String username, String depositSn, String refund_amount) throws AlipayApiException {
        Long userID = userService.getUserIdByUsername(username);
        Deposit deposit = depositService.getBySn(depositSn);
        if (!userID.equals(deposit.getUserId())) throw new UnauthorizedException("您无权操作此退款");
//        if (deposit.getPayId() == null) throw new RuntimeException("未付款怎么退款呢？");
        if (new BigDecimal(refund_amount).compareTo(deposit.getDeposit()) > 0) throw new RuntimeException("退款金额大于支付金额");

        // 更新状态
        deposit.setStatus(DepositStatusConstant.REFUND);
        depositService.updateById(deposit);

        // 更新房屋状态
        House house = houseService.getByTenantId(deposit.getTenantId());
        if (!house.getDeposit().equals(deposit.getDeposit())) throw new RuntimeException("房屋信息与押金单不一致，请联系管理员");
        house.setStatus(HouseStatusConstant.LEISURE);
        houseService.updateById(house);

        if (deposit.getPayId() == null) return;

        // 退款流水生成以及更新财务信息
        runningTallyService.saveDeposit(userID, depositSn, "-" + refund_amount);

        // 退款
        alipayTradeRefund(refund_amount, deposit.getPayId());
    }

    @Override
    @Transactional
    public void orderRefund(String username, String orderSn, String refund_amount) throws AlipayApiException {
        Long userID = userService.getUserIdByUsername(username);
        Order order = orderService.getOrderBySn(orderSn);
        if (!userID.equals(order.getUserId())) throw new UnauthorizedException("您无权操作此退款");
//        if (order.getPayId().isEmpty()) throw new RuntimeException("未付款怎么退款呢？");
        if (new BigDecimal(refund_amount).compareTo(order.getPrice()) > 0) throw new RuntimeException("退款金额大于支付金额");

        // 更新状态
        order.setOrderStatus(OrderStatusConstant.REFUND);
        orderService.updateById(order);
//        House house = houseService.getById(order.getHouseId());
//        house.setStatus(HouseStatusConstant.LEISURE);
//        houseService.updateById(house);

        if (order.getPayId() == null) return;

        // 退款流水生成以及更新财务信息
        runningTallyService.saveRent(userID, orderSn, "-" + refund_amount);

        // 退款
        alipayTradeRefund(refund_amount, order.getPayId());
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
    @Transactional
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
                String receipt_amount = params.get("receipt_amount");
                String gmt_payment = params.get("gmt_payment");//该笔交易 的买家付款时间。格式为 yyyy-MM-dd HH:mm:ss。
                DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                //字符串转LocalDateTime
                LocalDateTime payTime = LocalDateTime.parse(gmt_payment, timeDtf);
                if (StrUtil.sub(subject, 0, 7).equals("deposit")) {
                    // 押金逻辑
                    log.info("deposit handle");
                    Deposit depositBySn = depositService.getBySn(out_trade_no);
                    Deposit deposit = new Deposit();
                    deposit.setId(depositBySn.getId());
                    deposit.setStatus(DepositStatusConstant.HAVE_TO_PAY);
                    deposit.setPayId(trade_no);
                    deposit.setPayTime(payTime);
                    depositService.updateById(deposit);

                    // 生成押金流水并更新finance
                    runningTallyService.saveDeposit(depositBySn.getUserId(), out_trade_no, receipt_amount);

                    log.info("支付宝异步通知：房屋押金" + out_trade_no + ",交易号" + trade_no + "，于" + gmt_payment + "完成支付");
                } else {
                    // 租单逻辑
                    log.info("order handle");
                    Order order = new Order();
                    Order orderBySn = orderService.getOrderBySn(out_trade_no);
                    order.setId(orderBySn.getId());
                    order.setPayId(trade_no);
                    order.setPayTime(payTime);
                    order.setOrderStatus(OrderStatusConstant.HAVE_TO_PAY);
                    orderService.updateById(order);

                    // 生成租金流水并更新finance
                    runningTallyService.saveRent(orderBySn.getUserId(), out_trade_no, receipt_amount);

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
        alipayDTO.setSubject("deposit" + deposit.getDepositSn());
        alipayDTO.setQuit_url(param.getQuitUrl());
        return alipayDTO;
    }
}
