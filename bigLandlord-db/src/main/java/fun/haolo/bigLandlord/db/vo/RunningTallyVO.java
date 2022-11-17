package fun.haolo.bigLandlord.db.vo;

import fun.haolo.bigLandlord.db.dto.RunningTallyDTO;

import java.util.List;

/**
 * @author haolo
 * @since 2022-11-15 17:25
 */
public class RunningTallyVO {
    private List<RunningTallyDTO> list;
    private Long total;

    public List<RunningTallyDTO> getList() {
        return list;
    }

    public void setList(List<RunningTallyDTO> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
