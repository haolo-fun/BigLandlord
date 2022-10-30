package fun.haolo.bigLandlord.db.dto;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author haolo
 * @since 2022-10-30 14:42
 */
public class DepositDTO {
    @ApiModelProperty("押金单编号")
    private String depositSn;

    @ApiModelProperty("租客id")
    private Long tenantId;

    @ApiModelProperty("押金金额")
    private BigDecimal deposit;

    @ApiModelProperty("押金状态（0->未支付，1->已支付，2->已退款）")
    private Integer status;

    @ApiModelProperty("支付交易号")
    private String payId;

    @ApiModelProperty("支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    public String getDepositSn() {
        return depositSn;
    }

    public void setDepositSn(String depositSn) {
        this.depositSn = depositSn;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DepositVO{" +
                "depositSn='" + depositSn + '\'' +
                ", tenantId=" + tenantId +
                ", deposit=" + deposit +
                ", status=" + status +
                ", payId='" + payId + '\'' +
                ", payTime=" + payTime +
                ", createTime=" + createTime +
                '}';
    }
}
