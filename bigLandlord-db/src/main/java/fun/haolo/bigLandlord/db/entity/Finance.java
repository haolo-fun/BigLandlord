package fun.haolo.bigLandlord.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * @since 2022-11-15
 */
@TableName("bl_finance")
@ApiModel(value = "Finance对象", description = "")
public class Finance implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    private Long userId;

    @ApiModelProperty("押金")
    @TableField("deposit")
    private BigDecimal deposit;

    @ApiModelProperty("租金")
    @TableField("rent")
    private BigDecimal rent;

    @ApiModelProperty("可提现押金")
    @TableField("withdraw_deposit")
    private BigDecimal withdrawDeposit;

    @ApiModelProperty("可提现租金")
    @TableField("withdraw_rent")
    private BigDecimal withdrawRent;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    public BigDecimal getWithdrawDeposit() {
        return withdrawDeposit;
    }

    public void setWithdrawDeposit(BigDecimal withdrawDeposit) {
        this.withdrawDeposit = withdrawDeposit;
    }

    public BigDecimal getWithdrawRent() {
        return withdrawRent;
    }

    public void setWithdrawRent(BigDecimal withdrawRent) {
        this.withdrawRent = withdrawRent;
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
        return "Finance{" +
            "userId = " + userId +
            ", deposit = " + deposit +
            ", rent = " + rent +
            ", withdrawDeposit = " + withdrawDeposit +
            ", withdrawRent = " + withdrawRent +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
            ", deleted = " + deleted +
        "}";
    }
}
