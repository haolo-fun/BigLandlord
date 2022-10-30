package fun.haolo.bigLandlord.db.vo;

import fun.haolo.bigLandlord.db.dto.DepositDTO;

import java.util.List;

/**
 * @author haolo
 * @since 2022-10-30 14:56
 */
public class DepositVO {
    private List<DepositDTO> list;

    private Long page;

    public List<DepositDTO> getList() {
        return list;
    }

    public void setList(List<DepositDTO> list) {
        this.list = list;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "DepositVO{" +
                "list=" + list +
                ", page=" + page +
                '}';
    }
}
