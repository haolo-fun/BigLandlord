package fun.haolo.bigLandlord.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.haolo.bigLandlord.db.entity.Role;
import fun.haolo.bigLandlord.db.entity.User;
import fun.haolo.bigLandlord.db.entity.UserRoleRelation;
import fun.haolo.bigLandlord.db.mapper.UserMapper;
import fun.haolo.bigLandlord.db.service.IRoleService;
import fun.haolo.bigLandlord.db.service.IUserRoleRelationService;
import fun.haolo.bigLandlord.db.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author haolo
 * @Date 2022-10-13 11:03
 * @Description
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserRoleRelationService userRoleRelationService;

    @Autowired
    private IRoleService roleService;

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        //通过username查user
        userQueryWrapper.eq("username", username);
        return getOne(userQueryWrapper);
    }

    @Override
    public Long getUserIdByUsername(String username) {
        return getUserByUsername(username).getId();
    }

    @Override
    public List<String> getUserPermissionsByUsername(String username) {
        return getUserPermissionsByUser(getUserByUsername(username));
    }

    @Override
    public List<String> getUserPermissionsByUser(User user) {
        List<String> list = new ArrayList<>();
        QueryWrapper<UserRoleRelation> userRoleRelationQueryWrapper = new QueryWrapper<>();
        userRoleRelationQueryWrapper.eq("user_id", user.getId());
        for (UserRoleRelation userRoleRelation : userRoleRelationService.list(userRoleRelationQueryWrapper)) {
            Role role = roleService.getById(userRoleRelation.getRoleId());
            list.add(role.getName());
        }
        return list;
    }
}
