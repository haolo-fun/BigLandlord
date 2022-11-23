package fun.haolo.bigLandlord.db.vo;

import fun.haolo.bigLandlord.db.dto.DepositDTO;
import fun.haolo.bigLandlord.db.dto.OrderDTO;

import java.util.List;

/**
 * @author haolo
 * @since 2022-11-22 14:53
 */
public class PaymentVO {
    private List<DepositDTO> depositList;
    private List<OrderDTO> orderList;
    private List<OrderAdditionalVO> orderAdditionalList;

    public List<DepositDTO> getDepositList() {
        return depositList;
    }

    public void setDepositList(List<DepositDTO> depositList) {
        this.depositList = depositList;
    }

    public List<OrderDTO> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderDTO> orderList) {
        this.orderList = orderList;
    }

    public List<OrderAdditionalVO> getOrderAdditionalList() {
        return orderAdditionalList;
    }

    public void setOrderAdditionalList(List<OrderAdditionalVO> orderAdditionalList) {
        this.orderAdditionalList = orderAdditionalList;
    }
}
