package fun.haolo.bigLandlord.db.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author haolo
 * @since 2022-10-22 22:51
 */
public class OrderDTO {
    @ApiModelProperty("租单编号")
    private String orderSn;

    @ApiModelProperty("房源id")
    private Long houseId;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("租客id")
    private Long tenantId;

    @ApiModelProperty("租客姓名")
    private String name;

    @ApiModelProperty("租期")
    private Short count;

    @ApiModelProperty("租单状态（0->未下发，1->已下发，2->已支付)")
    private Integer orderStatus;

    @ApiModelProperty("总费用")
    private BigDecimal price;

    @ApiModelProperty("支付交易号")
    private String payId;

    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getCount() {
        return count;
    }

    public void setCount(Short count) {
        this.count = count;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
        return "OrderDTO{" +
                "orderSn='" + orderSn + '\'' +
                ", address='" + address + '\'' +
                ", tenantId=" + tenantId +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", orderStatus=" + orderStatus +
                ", price=" + price +
                ", payId='" + payId + '\'' +
                ", payTime=" + payTime +
                ", createTime=" + createTime +
                '}';
    }
}
