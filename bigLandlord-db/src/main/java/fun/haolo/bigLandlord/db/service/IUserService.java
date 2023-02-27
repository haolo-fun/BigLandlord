package fun.haolo.bigLandlord.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fun.haolo.bigLandlord.db.entity.User;
import fun.haolo.bigLandlord.db.param.UserParam;
import fun.haolo.bigLandlord.db.vo.UserVO;

import java.util.List;

/**
 * @Author haolo
 * @Date 2022-10-13 10:23
 * @Description
 */
public interface IUserService extends IService<User> {

    User getUserByUsername(String username);

    Long getUserIdByUsername(String username);

    List<String> getUserPermissionsByUsername(String username);

    List<String> getUserPermissionsByUser(User user);

    UserVO getListToVoByAdmin(long current, long size);

    UserVO getListByUsernameToVoByAdmin(String username);

    void resetPasswordByAdmin(String username);

    void resetPassword(String username, String password);

    void resetStatusByAdmin(String username);

    void addUserByAdmin(UserParam userParam);

    void updateUserByAdmin(UserParam userParam);
}
