package fun.haolo.bigLandlord.db.vo;

import fun.haolo.bigLandlord.db.dto.TenantDTO;
import fun.haolo.bigLandlord.db.param.TenantParam;

import java.util.List;

/**
 * @author haolo
 * @since 2022-11-03 15:15
 */
public class TenantVO {
    private List<TenantDTO> list;
    private Long total;

    public List<TenantDTO> getList() {
        return list;
    }

    public void setList(List<TenantDTO> list) {
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
        return "TenantVO{" +
                "list=" + list +
                ", total=" + total +
                '}';
    }
}
