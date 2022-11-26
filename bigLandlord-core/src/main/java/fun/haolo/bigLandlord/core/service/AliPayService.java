package fun.haolo.bigLandlord.core.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.*;
import fun.haolo.bigLandlord.core.dto.AliPayDTO;
import fun.haolo.bigLandlord.core.param.AliPayParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author haolo
 * @since 2022-10-26 16:19
 */
public interface AliPayService {
    /**
     * 手机网站支付接口2.0
     *
     * @param aliPayDTO
     * @return AlipayTradeWapPayResponse
     * @throws AlipayApiException
     */
    AlipayTradeWapPayResponse alipayTradeWapPay(AliPayDTO aliPayDTO) throws AlipayApiException;

    /**
     * PC网站支付接口
     * @param param
     * @return AlipayTradePagePayResponse
     * @throws AlipayApiException
     */
    AlipayTradePagePayResponse alipayTradePagePay(AliPayDTO param) throws AlipayApiException;

    /**
     * 统一收单线下交易查询
     *
     * @param trade_no 支付宝交易号
     * @return AlipayTradeQueryResponse
     * @throws AlipayApiException
     */
    AlipayTradeQueryResponse alipayTradeQuery(String trade_no) throws AlipayApiException;

    /**
     * 统一收单交易关闭接口
     *
     * @param trade_no 支付宝交易号
     * @return AlipayTradeCloseResponse
     * @throws AlipayApiException
     */
    AlipayTradeCloseResponse alipayTradeClose(String trade_no) throws AlipayApiException;

    /**
     * 统一收单交易退款接口
     *
     * @param refund_amount 退款金额。该金额不能大于订单金额
     * @param trade_no      支付宝交易号
     */
    AlipayTradeRefundResponse alipayTradeRefund(String refund_amount, String trade_no) throws AlipayApiException;

    /**
     * 统一收单交易退款查询
     *
     * @param out_request_no 退款请求号。 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的商户订单号
     * @param trade_no       支付宝交易号
     * @return AlipayTradeFastpayRefundQueryResponse
     * @throws AlipayApiException
     */
    AlipayTradeFastpayRefundQueryResponse alipayTradeFastPayRefundQuery(String out_request_no, String trade_no) throws AlipayApiException;

    /**
     * 根据押金金额付款
     *
     * @return AlipayTradeWapPayResponse
     */
    AlipayTradePagePayResponse payDepositByPC(AliPayParam param) throws AlipayApiException;

    AlipayTradeWapPayResponse payDepositByPhone(AliPayParam param) throws AlipayApiException;

    /**
     * 根据租单付款
     *
     * @return AlipayTradeWapPayResponse
     */
    AlipayTradePagePayResponse payOrderByPC(AliPayParam param) throws AlipayApiException;

    AlipayTradeWapPayResponse payOrderByPhone(AliPayParam param) throws AlipayApiException;



    /**
     * 押金退款接口（已验证退款操作是否合法）
     *
     * @param refund_amount 退款金额。该金额不能大于订单金额
     * @param deposit_sn    押金单号
     * @param username      房东用户名
     * @return AlipayTradeRefundResponse
     * @throws AlipayApiException
     */
    AlipayTradeRefundResponse alipayTradeRefund(String refund_amount, String deposit_sn, String username) throws AlipayApiException;

    /**
     * 异步回调处理器
     *
     * @param request HttpServletRequest
     * @return success or fail
     * @throws AlipayApiException
     */
    String notifyHandle(HttpServletRequest request) throws AlipayApiException;

}
