package fun.haolo.bigLandlord.db.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author haolo
 * @since 2022-11-04 12:36
 */
public class HouseDTO {
    @ApiModelProperty("房屋id")
    private Long id;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("面积")
    private Integer area;

    @ApiModelProperty("押金")
    private BigDecimal deposit;

    @ApiModelProperty("价格/月")
    private BigDecimal price;

    @ApiModelProperty("房屋状态（空闲->0，已租->1）")
    private Integer status;

    @ApiModelProperty("当前租客id")
    private Long tenantId;

    @ApiModelProperty("当前租客姓名")
    private String tenantName;

    @ApiModelProperty("到期日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
