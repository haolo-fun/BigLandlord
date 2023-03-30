package fun.haolo.bigLandlord.db.dto;

/**
 * @author haolo
 * @since 2023-03-04 14:40
 */
public class SurrenderDTO {
    private Long depositSn;
    private Long orderSn;

    public Long getDepositSn() {
        return depositSn;
    }

    public void setDepositSn(Long depositSn) {
        this.depositSn = depositSn;
    }

    public Long getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(Long orderSn) {
        this.orderSn = orderSn;
    }

    @Override
    public String toString() {
        return "SurrenderDTO{" +
                "depositSn=" + depositSn +
                ", orderSn=" + orderSn +
                '}';
    }
}
