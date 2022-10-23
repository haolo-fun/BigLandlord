package fun.haolo.bigLandlord.db.vo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author haolo
 * @since 2022-10-22 17:51
 */
public class OrderAdditionalVO {

    private Long id;

    @ApiModelProperty("内容，如管理费，水费，电费，等等")
    private String key;

    @ApiModelProperty("单价")
    private BigDecimal value;

    @ApiModelProperty("数量")
    private Short count;

    @ApiModelProperty("备注")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Short getCount() {
        return count;
    }

    public void setCount(Short count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OrderAdditionalVO{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value=" + value +
                ", count=" + count +
                ", remark='" + remark + '\'' +
                '}';
    }
}
