package fun.haolo.bigLandlord.db.vo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author haolo
 * @since 2022-11-15 16:27
 */
public class FinanceVO {

    @ApiModelProperty("押金")
    private BigDecimal deposit;

    @ApiModelProperty("租金")
    private BigDecimal rent;

    @ApiModelProperty("可提现押金")
    private BigDecimal withdrawDeposit;

    @ApiModelProperty("可提现租金")
    private BigDecimal withdrawRent;

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
}
