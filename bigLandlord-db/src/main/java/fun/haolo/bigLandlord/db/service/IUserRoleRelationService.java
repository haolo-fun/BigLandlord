package fun.haolo.bigLandlord.db.service;

import fun.haolo.bigLandlord.db.entity.UserRoleRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.haolo.bigLandlord.db.vo.RoleRelationVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author haolo
 * @since 2022-10-13
 */
public interface IUserRoleRelationService extends IService<UserRoleRelation> {

    List<String> getRoles(long userId);

    List<RoleRelationVO> getList(String username);

    void addRole(String username, String roleName);

    void delRole(String username, String roleName);

}
