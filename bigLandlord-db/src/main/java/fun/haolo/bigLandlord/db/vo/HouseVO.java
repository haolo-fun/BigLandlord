package fun.haolo.bigLandlord.db.vo;

import fun.haolo.bigLandlord.db.dto.HouseDTO;

import java.util.List;

/**
 * @author haolo
 * @since 2022-11-04 12:29
 */
public class HouseVO {
    private List<HouseDTO> list;
    private Long total;

    public List<HouseDTO> getList() {
        return list;
    }

    public void setList(List<HouseDTO> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
