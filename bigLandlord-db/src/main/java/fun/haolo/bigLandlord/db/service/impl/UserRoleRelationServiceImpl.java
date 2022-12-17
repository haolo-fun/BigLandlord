package fun.haolo.bigLandlord.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.haolo.bigLandlord.db.entity.UserRoleRelation;
import fun.haolo.bigLandlord.db.mapper.UserRoleRelationMapper;
import fun.haolo.bigLandlord.db.service.IRoleService;
import fun.haolo.bigLandlord.db.service.IUserRoleRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.haolo.bigLandlord.db.service.IUserService;
import fun.haolo.bigLandlord.db.vo.RoleRelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author haolo
 * @since 2022-10-13
 */
@Service
public class UserRoleRelationServiceImpl extends ServiceImpl<UserRoleRelationMapper, UserRoleRelation> implements IUserRoleRelationService {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @Override
    public List<String> getRoles(long userId) {
        QueryWrapper<UserRoleRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<UserRoleRelation> list = list(wrapper);
        ArrayList<String> strings = new ArrayList<>();
        for (UserRoleRelation roleRelation : list) {
            String name = roleService.getById(roleRelation.getRoleId()).getName();
            strings.add(name);
        }
        return strings;
    }

    @Override
    public List<RoleRelationVO> getList(String username) {
        Long userId = userService.getUserIdByUsername(username);
        return getBaseMapper().getList(userId);
    }

    @Override
    public void addRole(String username, String roleName) {
        List<RoleRelationVO> list = getList(username);
        for (RoleRelationVO roleRelationVO : list) {
            if (roleName.equals(roleRelationVO.getName()))
                throw new RuntimeException("角色已存在");
        }
        if (roleService.hasName(roleName)) throw new RuntimeException("非法角色");
        Long userId = userService.getUserIdByUsername(username);
        Integer roleId = roleService.getIdByRoleName(roleName);
        UserRoleRelation roleRelation = new UserRoleRelation();
        roleRelation.setUserId(userId);
        roleRelation.setRoleId(roleId);
        save(roleRelation);
    }

    @Override
    public void delRole(String username, String roleName) {
        if (getList(username).size() < 2) throw new RuntimeException("必须保留一个角色");
        Long userId = userService.getUserIdByUsername(username);
        Integer roleId = roleService.getIdByRoleName(roleName);
        QueryWrapper<UserRoleRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("role_id", roleId);
        remove(wrapper);
    }
}
