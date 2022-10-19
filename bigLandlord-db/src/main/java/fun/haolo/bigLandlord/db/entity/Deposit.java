package fun.haolo.bigLandlord.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author haolo
 * @since 2022-10-18
 */
@TableName("bl_deposit")
@ApiModel(value = "Deposit对象", description = "押金表")
public class Deposit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("租客id")
    @TableField("tenant_id")
    private Long tenantId;

    @ApiModelProperty("押金金额")
    @TableField("deposit")
    private BigDecimal deposit;

    @ApiModelProperty("押金状态（0->正常，1->已退回）")
    @TableField("status")
    private Boolean status;

    @ApiModelProperty("支付交易号")
    @TableField("pay_id")
    private byte[] payId;

    @ApiModelProperty("支付时间")
    @TableField("pay_time")
    private LocalDateTime payTime;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("逻辑删除")
    @TableField("deleted")
    @TableLogic
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public byte[] getPayId() {
        return payId;
    }

    public void setPayId(byte[] payId) {
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

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Deposit{" +
            "id = " + id +
            ", tenantId = " + tenantId +
            ", deposit = " + deposit +
            ", status = " + status +
            ", payId = " + payId +
            ", payTime = " + payTime +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
            ", deleted = " + deleted +
        "}";
    }
}
