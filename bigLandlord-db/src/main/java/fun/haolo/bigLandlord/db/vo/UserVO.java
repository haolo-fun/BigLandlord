package fun.haolo.bigLandlord.db.vo;

import fun.haolo.bigLandlord.db.dto.UserDTO;
import java.util.List;

/**
 * @author haolo
 * @since 2022-12-11 11:23
 */
public class UserVO {

    private List<UserDTO> list;
    private Long total;

    public List<UserDTO> getList() {
        return list;
    }

    public void setList(List<UserDTO> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
