package fun.haolo.bigLandlord.db.vo;

import fun.haolo.bigLandlord.db.dto.OrderDTO;

import java.util.List;

/**
 * @author haolo
 * @since 2022-11-08 17:08
 */
public class OrderVO {
    private List<OrderDTO> list;
    private Long total;

    public List<OrderDTO> getList() {
        return list;
    }

    public void setList(List<OrderDTO> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
