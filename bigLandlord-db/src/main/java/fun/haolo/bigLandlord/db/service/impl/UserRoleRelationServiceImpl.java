package fun.haolo.bigLandlord.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.haolo.bigLandlord.db.entity.UserRoleRelation;
import fun.haolo.bigLandlord.db.mapper.UserRoleRelationMapper;
import fun.haolo.bigLandlord.db.service.IRoleService;
import fun.haolo.bigLandlord.db.service.IUserRoleRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    public List<String> getRoles(Long userId) {
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
}
