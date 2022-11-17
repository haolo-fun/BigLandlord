package fun.haolo.bigLandlord.db.dto;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author haolo
 * @since 2022-11-15 16:48
 */
public class RunningTallyDTO {
    @ApiModelProperty("0->押金，1->租金")
    private Short type;

    @ApiModelProperty("单号")
    private String sn;

    @ApiModelProperty("流水")
    private BigDecimal price;

    @ApiModelProperty("余额")
    private BigDecimal balance;

    @ApiModelProperty("in or out")
    private String form;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
