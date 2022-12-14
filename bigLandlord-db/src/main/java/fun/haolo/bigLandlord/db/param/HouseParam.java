package fun.haolo.bigLandlord.db.param;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author haolo
 * @since 2022-10-21 15:38
 */
public class HouseParam {

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

    @Override
    public String toString() {
        return "HouseParam{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", area=" + area +
                ", deposit=" + deposit +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
