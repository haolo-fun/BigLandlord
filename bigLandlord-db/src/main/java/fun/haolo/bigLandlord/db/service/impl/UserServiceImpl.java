package fun.haolo.bigLandlord.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.haolo.bigLandlord.db.dto.UserDTO;
import fun.haolo.bigLandlord.db.entity.Role;
import fun.haolo.bigLandlord.db.entity.User;
import fun.haolo.bigLandlord.db.entity.UserRoleRelation;
import fun.haolo.bigLandlord.db.mapper.UserMapper;
import fun.haolo.bigLandlord.db.param.UserParam;
import fun.haolo.bigLandlord.db.service.IFinanceService;
import fun.haolo.bigLandlord.db.service.IRoleService;
import fun.haolo.bigLandlord.db.service.IUserRoleRelationService;
import fun.haolo.bigLandlord.db.service.IUserService;
import fun.haolo.bigLandlord.db.utils.RedisUtil;
import fun.haolo.bigLandlord.db.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IFinanceService financeService;

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

    @Override
    public UserVO getListToVoByAdmin(long current, long size) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        return getToVo(current, size, wrapper);
    }

    @Override
    public UserVO getListByUsernameToVoByAdmin(String username) {
        UserVO userVO = new UserVO();
        User user = getUserByUsername(username);
        List<UserDTO> userDTOS = new ArrayList<>();
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        userDTOS.add(userDTO);
        userVO.setList(userDTOS);
        userVO.setTotal(1L);
        return userVO;
    }

    @Override
    public void resetPasswordByAdmin(String username) {
        resetPassword(username,"123456");
    }

    @Override
    public void resetPassword(String username, String password) {
        User user = getUserByUsername(username);
        if (Objects.isNull(user)) throw new RuntimeException("不要乱来");
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        updateById(user);
    }

    @Override
    public void resetStatusByAdmin(String username) {
        User user = getUserByUsername(username);
        if (Objects.isNull(user)) throw new RuntimeException("不要乱来");
        user.setStatus(user.getStatus() > 0 ? 0 : 1);
        updateById(user);
        redisUtil.deleteObjectsByPattern("token:" + username + ":*");
    }

    @Override
    public void addUserByAdmin(UserParam userParam) {
        // 查询是否有相同用户名的用户
        User userByUsername = getUserByUsername(userParam.getUsername());
        if (Objects.nonNull(userByUsername)) {
            throw new RuntimeException("该用户名已存在");
        }
        User user = new User();
        user.setUsername(userParam.getUsername());
        user.setMobile(userParam.getMobile());
        user.setEmail(userParam.getEmail());
        user.setNickName(userParam.getNickName());
        save(user);
        //角色初始化
        Long userId = getUserIdByUsername(user.getUsername());
        UserRoleRelation userRoleRelation = new UserRoleRelation();
        userRoleRelation.setUserId(userId);
        userRoleRelation.setRoleId(2);
        userRoleRelationService.save(userRoleRelation);
        //财务初始化
        financeService.init(userId);
        //密码初始化
        resetPasswordByAdmin(user.getUsername());
    }

    @Override
    public void updateUserByAdmin(UserParam userParam) {
        if (userParam.getMobile().isEmpty()) throw new RuntimeException("手机号不能为空");
        User user = getUserByUsername(userParam.getUsername());
        if (Objects.isNull(user)) throw new RuntimeException("不要乱来");
        user.setMobile(userParam.getMobile());
        user.setEmail(userParam.getEmail());
        user.setNickName(userParam.getNickName());
        user.setUpdateTime(null);
        updateById(user);
    }


    private UserVO getToVo(long current, long size, QueryWrapper<User> wrapper) {
        UserVO userVO = new UserVO();
        Page<User> userPage = getBaseMapper().selectPage(new Page<>(current, size), wrapper);
        userVO.setTotal(userPage.getTotal());
        List<User> users = userPage.getRecords();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            userDTOS.add(userDTO);
        }
        userVO.setList(userDTOS);
        return userVO;
    }

}
