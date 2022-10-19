package fun.haolo.bigLandlord.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
@TableName("bl_house")
@ApiModel(value = "House对象", description = "房源表")
public class House implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("房屋id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("面积")
    @TableField("area")
    private Integer area;

    @ApiModelProperty("押金")
    @TableField("deposit")
    private BigDecimal deposit;

    @ApiModelProperty("价格/月")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("房屋状态（空闲->0，已租->1）")
    @TableField("status")
    private Boolean status;

    @ApiModelProperty("当前租客id")
    @TableField("tenant_id")
    private Long tenantId;

    @ApiModelProperty("到期日期")
    @TableField("due_date")
    private LocalDate dueDate;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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
        return "House{" +
            "id = " + id +
            ", address = " + address +
            ", area = " + area +
            ", deposit = " + deposit +
            ", price = " + price +
            ", status = " + status +
            ", tenantId = " + tenantId +
            ", dueDate = " + dueDate +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
            ", deleted = " + deleted +
        "}";
    }
}
