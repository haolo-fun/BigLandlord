package fun.haolo.bigLandlord.core.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author haolo
 * @since 2022-10-26 16:29
 */
public class AliPayDTO {

    @ApiModelProperty("商户订单号。 由商家自定义，64个字符以内，仅支持字母、数字、下划线且需保证在商户端不重复。")
    private String out_trade_no;

    @ApiModelProperty("订单总金额。 单位为元，精确到小数点后两位，取值范围：[0.01,100000000] 。")
    private String total_amount;

    @ApiModelProperty("订单标题。 注意：不可使用特殊字符，如 /，=，& 等。")
    private String subject;

    @ApiModelProperty("商家和支付宝签约的产品码。默认值为 QUICK_WAP_WAY。")
    private String product_code;

    @ApiModelProperty("用户付款中途退出返回商户网站的地址")
    private String quit_url;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getQuit_url() {
        return quit_url;
    }

    public void setQuit_url(String quit_url) {
        this.quit_url = quit_url;
    }
}
