package fun.haolo.bigLandlord.db.vo;

import fun.haolo.bigLandlord.db.dto.DepositDTO;

import java.util.List;

/**
 * @author haolo
 * @since 2022-10-30 14:56
 */
public class DepositVO {
    private List<DepositDTO> list;

    private Long total;

    public List<DepositDTO> getList() {
        return list;
    }

    public void setList(List<DepositDTO> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "DepositVO{" +
                "list=" + list +
                ", total=" + total +
                '}';
    }
}
